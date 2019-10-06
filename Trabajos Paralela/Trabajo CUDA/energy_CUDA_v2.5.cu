/*
 * Simulacion simplificada de bombardeo de particulas de alta energia
 *
 * Computacion Paralela (Grado en Informatica)
 * 2017/2018
 *
 * (c) 2018 Arturo Gonzalez Escribano
 */
#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<cuda.h>
#include<cputils.h>

#define PI	3.14159f
#define UMBRAL	0.001f

/* Estructura para almacenar los datos de una tormenta de particulas */
typedef struct {
	int size;
	int *posval;
} Storm;

__global__ void relajacionCopia(float* layerGPU,float* layerCopyGPU,int layer_size){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-1) return;
	layerCopyGPU[idGlobal] = layerGPU[idGlobal];
}

__global__ void relajacionActualiza(float* layerGPU,float* layerCopyGPU,int layer_size){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-1) return;
	if(idGlobal != 0 && idGlobal != layer_size-1)
		layerGPU[idGlobal] = ( layerCopyGPU[idGlobal-1] + layerCopyGPU[idGlobal] + layerCopyGPU[idGlobal+1] ) / 3;
}

__global__ void copiaAtmp(float *layerGPU,float *tmp,int layer_size,int *pos,float *ini,float *fin){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-1) return;
	if(idGlobal != 0 && idGlobal != layer_size-1){
		tmp[idGlobal-1] = layerGPU[idGlobal];
		pos[idGlobal-1] = idGlobal;
	}
	if(idGlobal == 0){
		ini[0] = layerGPU[0];
		fin[0] = layerGPU[layer_size-1];

		if(tmp[0] <= ini[0])
			tmp[0] = 0.0f;

		if(tmp[layer_size-3] <= fin[0])
			tmp[layer_size-3] = 0.0f;
	}

	if(idGlobal > 0 && idGlobal < layer_size-3){
		if(tmp[idGlobal] == tmp[idGlobal+1]){
			tmp[idGlobal] = 0.0f;
			tmp[idGlobal+1] = 0.0f;
		}
		if(tmp[idGlobal] == tmp[idGlobal-1]){
			tmp[idGlobal] = 0.0f;
			tmp[idGlobal-1] = 0.0f;
		}
	}


}

__global__ void reduccion(float *layerGPU,float *maximosGPU,int i,int layer_size,float *tmp,int tam,int *pos){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > (tam/2)-1) return;
		if(tmp[idGlobal] < tmp[idGlobal+(tam/2)]){
			tmp[idGlobal] = tmp[idGlobal+(tam/2)];
			pos[idGlobal] = pos[idGlobal+(tam/2)];
		}else if(tmp[idGlobal] == tmp[idGlobal+(tam/2)] && pos[idGlobal] > pos[idGlobal+(tam/2)]){
			pos[idGlobal] = pos[idGlobal+(tam/2)];
		}

	if(tam%2 != 0 && idGlobal == 0 && tmp[0] < tmp[tam-1]){
		tmp[0] = tmp[tam-1];
		pos[0] = pos[tam-1];
	}
}

__global__ void copiaAarray(float *maximosGPU,int *posicionesGPU,int i,float *tmp,int *pos,float *ini,float *fin,int layer_size,float *layerGPU){
	maximosGPU[i] = tmp[0];
	posicionesGPU[i] = pos[0];
	if(tmp[0] == 0.000000){
		posicionesGPU[i] = 0;
	}
}

__global__ void vaciarTemporales(float *tmp,int *pos,float *ini,float *fin,int layer_size){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-3) return;
	tmp[idGlobal] = 0;
	pos[idGlobal] = idGlobal;
	if(idGlobal == 0){
		ini[0] = 0.0f;
		fin[0] = 0.0f;
	}
}

/* ESTA FUNCION PUEDE SER MODIFICADA */
/* Funcion para actualizar una posicion de la capa */
__global__ void actualiza( float *layer, int pos, float energia,int layer_size ) {
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-1) return;
	int distancia = pos - idGlobal;
	if ( distancia < 0 ) distancia = - distancia;

	distancia = distancia + 1;

	float atenuacion = sqrtf( (float)distancia );

	float energia_k = energia / atenuacion;

	if ( energia_k >= UMBRAL || energia_k <= -UMBRAL )
		layer[idGlobal] = layer[idGlobal] + energia_k;
}


/* FUNCIONES AUXILIARES: No se utilizan dentro de la medida de tiempo, dejar como estan */
/* Funcion de DEBUG: Imprimir el estado de la capa */
void debug_print(int layer_size, float *layer, int *posiciones, float *maximos, int num_storms ) {
	int i,k;
	if ( layer_size <= 35 ) {
		/* Recorrer capa */
		for( k=0; k<layer_size; k++ ) {
			/* Escribir valor del punto */
			printf("%10.4f |", layer[k] );

			/* Calcular el numero de caracteres normalizado con el maximo a 60 */
			int ticks = (int)( 60 * layer[k] / maximos[num_storms-1] );

			/* Escribir todos los caracteres menos el ultimo */
			for (i=0; i<ticks-1; i++ ) printf("o");

			/* Para maximos locales escribir ultimo caracter especial */
			if ( k>0 && k<layer_size-1 && layer[k] > layer[k-1] && layer[k] > layer[k+1] )
				printf("x");
			else
				printf("o");

			/* Si el punto es uno de los maximos especiales, annadir marca */
			for (i=0; i<num_storms; i++) 
				if ( posiciones[i] == k ) printf(" M%d", i );

			/* Fin de linea */
			printf("\n");
		}
	}
}

/*
 * Funcion: Lectura de fichero con datos de tormenta de particulas
 */
Storm read_storm_file( char *fname ) {
	FILE *fstorm = cp_abrir_fichero( fname );
	if ( fstorm == NULL ) {
		fprintf(stderr,"Error: Opening storm file %s\n", fname );
		exit( EXIT_FAILURE );
	}

	Storm storm;	
	int ok = fscanf(fstorm, "%d", &(storm.size) );
	if ( ok != 1 ) {
		fprintf(stderr,"Error: Reading size of storm file %s\n", fname );
		exit( EXIT_FAILURE );
	}

	storm.posval = (int *)malloc( sizeof(int) * storm.size * 2 );
	if ( storm.posval == NULL ) {
		fprintf(stderr,"Error: Allocating memory for storm file %s, with size %d\n", fname, storm.size );
		exit( EXIT_FAILURE );
	}
	
	int elem;
	for ( elem=0; elem<storm.size; elem++ ) {
		ok = fscanf(fstorm, "%d %d\n", 
					&(storm.posval[elem*2]),
					&(storm.posval[elem*2+1]) );
		if ( ok != 2 ) {
			fprintf(stderr,"Error: Reading element %d in storm file %s\n", elem, fname );
			exit( EXIT_FAILURE );
		}
	}
	fclose( fstorm );

	return storm;
}

/*
 * PROGRAMA PRINCIPAL
 */
int main(int argc, char *argv[]) {
	int i,j,k;

	/* 1.1. Leer argumentos */
	if (argc<3) {
		fprintf(stderr,"Usage: %s <size> <storm_1_file> [ <storm_i_file> ] ... \n", argv[0] );
		exit( EXIT_FAILURE );
	}

	int layer_size = atoi( argv[1] );
	int num_storms = argc-2;
	Storm storms[ num_storms ];

	/* 1.2. Leer datos de storms */
	for( i=2; i<argc; i++ ) 
		storms[i-2] = read_storm_file( argv[i] );

	/* 1.3. Inicializar maximos a cero */
	float maximos[ num_storms ];
	int posiciones[ num_storms ];
	for (i=0; i<num_storms; i++) {
		maximos[i] = 0.0f;
		posiciones[i] = 0;
	}

	/* 2. Inicia medida de tiempo */
	cudaSetDevice(0);
	cudaDeviceSynchronize();
	double ttotal = cp_Wtime();

	/* COMIENZO: No optimizar/paralelizar el main por encima de este punto */

	/* 3. Reservar memoria para las capas e inicializar a cero */
	float *layer = (float *)malloc( sizeof(float) * layer_size );
	float *layer_copy = (float *)malloc( sizeof(float) * layer_size );
	if ( layer == NULL || layer_copy == NULL ) {
		fprintf(stderr,"Error: Allocating the layer memory\n");
		exit( EXIT_FAILURE );
	}
	for( k=0; k<layer_size; k++ ) layer[k] = 0.0f;
	for( k=0; k<layer_size; k++ ) layer_copy[k] = 0.0f;

	float *layerGPU;
	float *layerCopyGPU;
	float *maximosGPU;
	int *posicionesGPU;
	float* ini;
	float* fin;

	cudaError_t errorLayerGPU = cudaMalloc(&layerGPU,sizeof(float)*layer_size);
	cudaError_t errorLayerCopyGPU = cudaMalloc(&layerCopyGPU,sizeof(float)*layer_size);
	cudaError_t errorMaxGPU = cudaMalloc(&maximosGPU,sizeof(float)*num_storms);
	cudaError_t errorPosGPU = cudaMalloc(&posicionesGPU,sizeof(int)*num_storms);

	cudaError_t errorIni = cudaMalloc(&ini,sizeof(float));
	cudaError_t errorFin = cudaMalloc(&fin,sizeof(float));

	dim3 numThreads(8,32);
	int threads_per_block=256;

	int numBlocks;

	if(layer_size <= threads_per_block){
		numBlocks = 1;
	}else{
		numBlocks = layer_size/threads_per_block;
		if(layer_size%threads_per_block != 0)
			numBlocks++;
	}

	float *tmpGPU;
	int *posGPU;

	cudaError_t errorTmp = cudaMalloc(&tmpGPU,sizeof(float)*layer_size-2);
	cudaError_t errorPos = cudaMalloc(&posGPU,sizeof(int)*layer_size-2);

	float energia;
	int posicion;

	for( i=0; i<num_storms; i++) {

		//¿Como dividir los indices?
		//¿Como usar el storms en el device?

		for( j=0; j<storms[i].size; j++ ) {
			energia = (float)storms[i].posval[j*2+1] / 1000;
			posicion = storms[i].posval[j*2];
			actualiza<<<numBlocks,numThreads>>>(layerGPU,posicion,energia,layer_size);			
		}

		relajacionCopia<<<numBlocks,numThreads>>>(layerGPU,layerCopyGPU,layer_size);
		
		relajacionActualiza<<<numBlocks,numThreads>>>(layerGPU,layerCopyGPU,layer_size);

		copiaAtmp<<<numBlocks,numThreads>>>(layerGPU,tmpGPU,layer_size,posGPU,ini,fin);

		for(int tam = layer_size-2; tam > 1; tam = tam/2){
			reduccion<<<numBlocks,numThreads>>>(layerGPU,maximosGPU,i,layer_size,tmpGPU,tam,posGPU);
		}

		copiaAarray<<<1,1>>>(maximosGPU,posicionesGPU,i,tmpGPU,posGPU,ini,fin,layer_size,layerGPU);

		vaciarTemporales<<<numBlocks,numThreads>>>(tmpGPU,posGPU,ini,fin,layer_size);

	}

	cudaError_t errorHostCpyLayer = cudaMemcpy(layer,layerGPU,sizeof(float)*layer_size,cudaMemcpyDeviceToHost);
	cudaError_t errorHostCpyMax = cudaMemcpy(maximos,maximosGPU,sizeof(float)*num_storms,cudaMemcpyDeviceToHost);
	cudaError_t errorHostCpyPos = cudaMemcpy(posiciones,posicionesGPU,sizeof(int)*num_storms,cudaMemcpyDeviceToHost);

	cudaError_t freeLayer = cudaFree(layerGPU);
	cudaError_t freeLayerCopy = cudaFree(layerCopyGPU);
	cudaError_t freeMax = cudaFree(maximosGPU);
	cudaError_t freePos = cudaFree(posicionesGPU);

	cudaError_t freeTmp = cudaFree(tmpGPU);
	cudaError_t freePosTmp = cudaFree(posGPU);
	cudaError_t freeIni = cudaFree(ini);
	cudaError_t freeFin = cudaFree(fin);

	/* FINAL: No optimizar/paralelizar por debajo de este punto */

	/* 6. Final de medida de tiempo */
	cudaDeviceSynchronize();
	ttotal = cp_Wtime() - ttotal;

	/* 7. DEBUG: Dibujar resultado (Solo para capas con hasta 35 puntos) */
	#ifdef DEBUG
	debug_print( layer_size, layer, posiciones, maximos, num_storms );
	#endif

	/* 8. Salida de resultados para tablon */
	printf("\n");
	/* 8.1. Tiempo total de la computacion */
	printf("Time: %lf\n", ttotal );
	/* 8.2. Escribir los maximos */
	printf("Result:");
	for (i=0; i<num_storms; i++)
		printf(" %d %f", posiciones[i], maximos[i] );
	printf("\n");

	/* 9. Liberar recursos */	
	for( i=0; i<argc-2; i++ )
		free( storms[i].posval );

	/* 10. Final correcto */
	return 0;
}

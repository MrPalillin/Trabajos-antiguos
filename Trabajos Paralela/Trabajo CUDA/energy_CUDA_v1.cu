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

__global__ void kernel1(float *layerGPU,float *layerCopyGPU,int layer_size){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-1) return;
	layerCopyGPU[idGlobal] = layerGPU[idGlobal];
}

__global__ void kernel2(float *layerGPU,float *layerCopyGPU,int layer_size){
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	if(idGlobal > layer_size-1) return;
	if(idGlobal != 0 && idGlobal != layer_size-1)
		layerGPU[idGlobal] = ( layerCopyGPU[idGlobal-1] + layerCopyGPU[idGlobal] + layerCopyGPU[idGlobal+1] ) / 3;
}


/* ESTA FUNCION PUEDE SER MODIFICADA */
/* Funcion para actualizar una posicion de la capa */
__global__ void actualiza( float *layerGPU, int pos, float energia,int layer_size ) {
	int idGlobal = threadIdx.x+(blockDim.x*threadIdx.y)+(blockDim.x*blockDim.y*blockIdx.x);
	/* 1. Calcular valor absoluto de la distancia entre el
		punto de impacto y el punto k de la capa */
	int distancia = pos - idGlobal;
	if ( distancia < 0 ) distancia = - distancia;

	/* 2. El punto de impacto tiene distancia 1 */
	distancia = distancia + 1;

	/* 3. Raiz cuadrada de la distancia */
	//float atenuacion = (float)distancia*distancia;
	//float atenuacion = (float)distancia / PI;
	float atenuacion = sqrtf( (float)distancia );

	/* 4. Calcular energia atenuada */
	float energia_k = energia / atenuacion;

	/* 5. No sumar si el valor absoluto es menor que umbral */
	if ( energia_k >= UMBRAL || energia_k <= -UMBRAL )
		layerGPU[idGlobal] = layerGPU[idGlobal] + energia_k;
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

	cudaError_t errorLayerGPU = cudaMalloc(&layerGPU,sizeof(float)*layer_size);
	cudaError_t errorLayerCopyGPU = cudaMalloc(&layerCopyGPU,sizeof(float)*layer_size);

	dim3 numThreads(16,16);

	int numBlocks;

	if(layer_size <= 256){
		numBlocks = 1;
	}else{
		numBlocks = layer_size/256;
		if(layer_size%256 != 0)
			numBlocks++;
	}

	/* 4. Fase de bombardeos */
	for( i=0; i<num_storms; i++) {

		/* 4.1. Suma energia de impactos */
		/* Para cada particula */

		cudaError_t errorDevCpyLayer = cudaMemcpy(layerGPU,layer,sizeof(float)*layer_size,cudaMemcpyHostToDevice);
		cudaError_t errorDevCpyLayerCopy = cudaMemcpy(layerCopyGPU,layer_copy,sizeof(float)*layer_size,cudaMemcpyHostToDevice);


		for( j=0; j<storms[i].size; j++ ) {
			/* Energia de impacto (en milesimas) */
			float energia = (float)storms[i].posval[j*2+1] / 1000;
			/* Posicion de impacto */
			int posicion = storms[i].posval[j*2];

			actualiza<<<numBlocks,numThreads>>>(layerGPU,posicion,energia,layer_size);

			/* Para cada posicion de la capa */
		//	for( k=0; k<layer_size; k++ ) {
		//		/* Actualizar posicion */
		//		actualiza( layer, k, posicion, energia );
		//	}
		}

		/* 4.2. Relajacion entre tormentas de particulas */
		/* 4.2.1. Copiar valores a capa auxiliar */
		//for( k=0; k<layer_size; k++ ) 
		//	layer_copy[k] = layer[k];

		/* 4.2.2. Actualizar capa, menos los extremos, usando valores del array auxiliar */
		//for( k=1; k<layer_size-1; k++ )
		//	layer[k] = ( layer_copy[k-1] + layer_copy[k] + layer_copy[k+1] ) / 3;

		kernel1<<<numBlocks,numThreads>>>(layerGPU,layerCopyGPU,layer_size);

		kernel2<<<numBlocks,numThreads>>>(layerGPU,layerCopyGPU,layer_size);

		cudaError_t errorHostCpyLayer = cudaMemcpy(layer,layerGPU,sizeof(float)*layer_size,cudaMemcpyDeviceToHost);
		cudaError_t errorHostCpyLayerCopy = cudaMemcpy(layer_copy,layerCopyGPU,sizeof(float)*layer_size,cudaMemcpyDeviceToHost);

		/* 4.3. Localizar maximo */
		for( k=1; k<layer_size-1; k++ ) {
			/* Comprobar solo maximos locales */
			if ( layer[k] > layer[k-1] && layer[k] > layer[k+1] ) {
				if ( layer[k] > maximos[i] ) {
					maximos[i] = layer[k];
					posiciones[i] = k;
				}
			}
		}
	}

	cudaError_t freeLayer = cudaFree(layerGPU);
	cudaError_t freeLayerCopy = cudaFree(layerCopyGPU);

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

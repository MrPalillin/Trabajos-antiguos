/**
 * Computacion Paralela (Curso 15-16)
 *
 * Colocacion de antenas
 * Version secuencial
 *
 * @author Mulero Lorenzo, Marcos
 */


// Includes generales
#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <mpi.h>

// Include para las utilidades de computación paralela
#include "cputils.h"


/**
 * Estructura antena
 */
typedef struct {
	int y;
	int x;
} Antena;


/**
 * Macro para acceder a las posiciones del mapa
 */
#define m(y,x) mapa[ (y * cols) + x ]

/**
 * Definiciones de constantes para MPI
 */
#define MASTER 0
#define FROM_MASTER 1
#define SLAVES 2


/**
 * Funcion de ayuda para imprimir el mapa
 */
void print_mapa(int * mapa, int rows, int cols, Antena * a){


	if(rows > 50 || cols > 30){
		printf("Mapa muy grande para imprimir\n");
		return;
	};

	#define ANSI_COLOR_RED     "\x1b[31m"
	#define ANSI_COLOR_GREEN   "\x1b[32m"
	#define ANSI_COLOR_RESET   "\x1b[0m"

	printf("Mapa [%d,%d]\n",rows,cols);
	for(int i=0; i<rows; i++){
		for(int j=0; j<cols; j++){

			int val = m(i,j);

			if(val == 0){
				if(a != NULL && a->x == j && a->y == i){
					printf( ANSI_COLOR_RED "   A"  ANSI_COLOR_RESET);
				} else { 
					printf( ANSI_COLOR_GREEN "   A"  ANSI_COLOR_RESET);
				}
			} else {
				printf("%4d",val);
			}
		}
		printf("\n");
	}
	printf("\n");
}



/**
 * Distancia de una antena a un punto (y,x)
 * @note Es el cuadrado de la distancia para tener mas carga
 */
int manhattan(Antena a, int y, int x){

	int dist = abs(a.x - x) + abs(a.y - y);
	return dist * dist;
}



/**
 * Actualizar el mapa con la nueva antena
 */
void actualizar(int * mapa, int rows, int cols, Antena antena, int taskid){

	for(int i=0; i<rows; i++){
		for(int j=0; j<cols; j++){

			int nuevadist = manhattan(antena,(rows*taskid)+i,j);

			if(nuevadist < m(i,j)){
				m(i,j) = nuevadist;
			}
		} // j
	} // i
}



/**
 * Calcular la distancia maxima en el mapa
 */
int calcular_max(int * mapa, int rows, int cols){

	int max = 0;
	
	for(int i=0; i<rows; i++){
		for(int j=0; j<cols; j++){

			if(m(i,j)>max){
				max = m(i,j);			
			}
		} // j
	} // i
	return max;
}



/**
 * Calcular la posicion de la nueva antena
 */
Antena nueva_antena(int * mapa, int rows, int cols, int min, int taskid){

	for(int i=0; i<rows; i++){
		for(int j=0; j<cols; j++){

			if(m(i,j)==min){

				Antena antena = {(rows*taskid)+i,j};
				return antena;
			}

		} // j
	} // i
}



/**
 * Identifica la antena con el valor max
 */
int identifica_antena (int * mapaMax, int numtasks, int max){
	for (int i; i<numtasks; i++){
		if(mapaMax[i] == max){
			return i;
		}
	}
}

/**
 * Funcion principal
 */
int main(int nargs, char ** vargs){

	int taskid, numtasks, averow, extra, max, section_size, nuevas = 0;
	int * mapa;
	int * mapa_max;
	int * section;
	Antena * antenas;
	double tiempo;
	MPI_Status status;

	// ANADIDO DESPUES DE LA ENTREGA
	int max_local, id_antena;
	Antena antena;
	int define_antena[2];

	MPI_Init(&nargs, &vargs);
	MPI_Comm_rank(MPI_COMM_WORLD, &taskid);
	MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
	
	//
	// 1. LEER DATOS DE ENTRADA
	//

	if (taskid == MASTER){
	
		// Comprobar número de argumentos
		if(nargs < 7){
			fprintf(stderr,"Uso: %s rows cols distMax nAntenas x0 y0 [x1 y1, ...]\n",vargs[0]);
			return -1;
		}
	}

	// Leer los argumentos de entrada
	int rows = atoi(vargs[1]);
	int cols = atoi(vargs[2]);
	int distMax = atoi(vargs[3]);
	int nAntenas = atoi(vargs[4]);
	
	if(taskid == MASTER){
		if(nAntenas<1 || nargs != (nAntenas*2+5)){
			fprintf(stderr,"Error en la lista de antenas\n");
			return -1;
		}
	

	// Mensaje
	printf("Calculando el número de antenas necesarias para cubrir un mapa de"
		   " (%d x %d)\ncon una distancia máxima no superior a %d "
		   "y con %d antenas iniciales\n\n",rows,cols,distMax,nAntenas);
	}
	
	// Reservar memoria para las antenas
	antenas = malloc(sizeof(Antena) * (size_t) nAntenas);
	if(!antenas){
		fprintf(stderr,"Error al reservar memoria para las antenas inicales\n");
		return -1;
	}	
	
	// Leer antenas
	for(int i=0; i<nAntenas; i++){
		antenas[i].x = atoi(vargs[5+i*2]);
		antenas[i].y = atoi(vargs[6+i*2]);

		if(antenas[i].y<0 || antenas[i].y>=rows || antenas[i].x<0 || antenas[i].x>=cols ){
			fprintf(stderr,"Antena #%d está fuera del mapa\n",i);
			return -1;
		}
	}

	
	//
	// 2. INICIACIÓN
	//
	
	if(taskid == MASTER){

		// Medir el tiempo
		tiempo = cp_Wtime();

		// Crear el mapa
		mapa = malloc((size_t) (rows*cols) * sizeof(int) );
	

		// Iniciar el mapa con el valor MAX INT
		for(int i=0; i<(rows*cols); i++){
			mapa[i] = INT_MAX;
		}

		// Colocar las antenas iniciales
		for(int i=0; i<nAntenas; i++){
			actualizar(mapa,rows,cols,antenas[i],0);
		}
	}

	// Debug
#ifdef DEBUG
	print_mapa(mapa,rows,cols,NULL);
#endif


	averow = rows/numtasks;
	extra = rows%numtasks;
	section_size = averow*cols;
	section = malloc ((size_t)(section_size) * sizeof(int));

	//
	// 3. CALCULO DE LAS NUEVAS ANTENAS
	//

	// Contador de antenas
	if (taskid == MASTER){
		mapa_max = malloc(numtasks*sizeof(int));
	}

	MPI_Scatter(mapa, section_size, MPI_INT, section, section_size, MPI_INT, MASTER, MPI_COMM_WORLD);

	while(1){

		max_local = calcular_max(section, averow, cols);
		
		if (taskid == MASTER) {
			mapa_max[0] = max_local;
			for (int k=1; k<numtasks; k++) {
				MPI_Recv(&mapa_max[k], FROM_MASTER, MPI_INT, k, SLAVES, MPI_COMM_WORLD, &status);
			}
		}else{
			MPI_Send(&max_local, FROM_MASTER, MPI_INT, MASTER, SLAVES, MPI_COMM_WORLD);
		}

		MPI_Reduce(&max_local, &max, FROM_MASTER, MPI_INT, MPI_MAX, MASTER, MPI_COMM_WORLD);
		MPI_Bcast(&max, FROM_MASTER, MPI_INT, MASTER, MPI_COMM_WORLD);
		

		// Salimos del bucle
		if (max <= distMax) break;	
		
		// Incrementamos el contador
		nuevas++;
		
		// Calculo de la nueva antena y actualizacion del mapa
		//antena = nueva_antena(mapa, rows, cols, max, taskid);
		//actualizar(mapa,rows,cols,antena,taskid);


		// ANADIDO DESPUES DE LA ENTREGA DE LA PRACTICA
		
		// Busqueda del maximo entre los procesos creados
		if (taskid == MASTER){
			id_antena = identifica_antena (mapa_max, numtasks, max);

			for (int i=1;i<numtasks;i++){
				MPI_Send(&id_antena, FROM_MASTER, MPI_INT, i, FROM_MASTER, MPI_COMM_WORLD);
			}
		}else{
			MPI_Recv(&id_antena, FROM_MASTER, MPI_INT, MASTER, FROM_MASTER, MPI_COMM_WORLD, &status);
		}

		// El proceso maximo crea y envia la nueva antena
		if (taskid == id_antena){
			antena = nueva_antena (section, averow, cols, max, id_antena);
			define_antena[0] = antena.x;
			define_antena[1] = antena.y;

			for (int k=0; k<numtasks; k++){
				if (k != id_antena){
					MPI_Send(&define_antena, FROM_MASTER, MPI_2INT, k, FROM_MASTER, MPI_COMM_WORLD);
				}
			}
		}else{
			MPI_Recv(&define_antena, FROM_MASTER, MPI_2INT, id_antena, FROM_MASTER, MPI_COMM_WORLD, &status);
			antena.x = define_antena[0];
			antena.y = define_antena[1];
		}

		// Actualizamos todas las secciones
		if (taskid == MASTER){
			for (int i=(rows-extra); i<rows; i++){
				for (int j=0; j<cols; j++){
					int nuevadist = manhattan (antena, i, j);
					
					if (nuevadist < m(i,j)){
						m(i,j) = nuevadist;
					}
				}
			}
		}

		actualizar (section, averow, cols, antena, taskid);

	}

	// Debug
#ifdef DEBUG
	print_mapa(mapa,rows,cols,NULL);
#endif

	//
	// 4. MOSTRAR RESULTADOS
	//
	
	if(taskid == MASTER){
		// tiempo
		tiempo = cp_Wtime() - tiempo;	
	
		// Salida
		printf("Result: %d\n",nuevas);
		printf("Time: %f\n",tiempo);
	}

	MPI_Finalize();
	return 0;
}




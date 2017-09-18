/*Daniel de Vicente Garrote 12424547Q
Adrian Sanchez Fernandez 71166366L*/

#include<stdio.h>
#include<stdlib.h>
#include<semaphore.h>
#include<pthread.h>
#include<string.h>
#include<unistd.h>
#include<signal.h>
#include<assert.h>


sem_t espaciobuffer1;								//Se crean los semaforos
sem_t datobuffer1;
sem_t espaciobuffer2;
sem_t datobuffer2;


pthread_t productor;
pthread_t consumidor1;
pthread_t consumidor2;


int numPal=1;
int TamBuffer1;
char **buffer1;
char buffer2[5][100];


void *extraccion(void *arg);
void cuenta_palabras(FILE *fichero);
void *comprobacion(void *arg);
void *escribir(void *arg);
int palindromo(char *palabra);


void cuenta_palabras(FILE *fichero){
	int c;
	while((c =fgetc(fichero)) != EOF) {				//Esto cuenta las palabras del fichero
		if(c == '\n'){
		   numPal++;
		}
	}
	fclose(fichero);
}


int main(int argc,char *argv[]){
	
	if(argc<3){
		printf("Faltan parametros\n");
		exit(1);
		
	}else if(argc>3){
		printf("Demasiados parametros\n");			//Se comprueba si el numero de argumentos es el justo
		exit(1);
	}
	
	char *fichero=argv[2];
	TamBuffer1=atoi(argv[1]);						//Lectura del tamaño del buffer1 como primer argumento
	buffer1=(char**)malloc(TamBuffer1*sizeof(char*));
	int bucle;
	for(bucle=0;bucle<TamBuffer1;bucle++)
		buffer1[bucle]=(char*)malloc(11*sizeof(char));
	
	sem_init(&espaciobuffer1,0,TamBuffer1);			//Inicializacion de los semaforos
	sem_init(&datobuffer1,0,0);
	sem_init(&espaciobuffer2,0,5);
	sem_init(&datobuffer2,0,0);
	
	FILE *fich=fopen((char*)argv[2],"r");			//Lectura del nombre del fichero como segundo argumento
	cuenta_palabras(fich);
	
	pthread_create(&productor,NULL,extraccion,(void*)fichero);
	pthread_create(&consumidor1,NULL,comprobacion,NULL);
	pthread_create(&consumidor2,NULL,escribir,NULL);
	
	pthread_join(productor,NULL);					//Se crean los hilos y se lanzan a la vez
	pthread_join(consumidor1,NULL);
	pthread_join(consumidor2,NULL);
	
	sem_destroy(&espaciobuffer1);					//Una vez que terminan los hilos se destruyen los semaforos
    sem_destroy(&datobuffer1);
    sem_destroy(&espaciobuffer2);
    sem_destroy(&datobuffer2);
	
	return 0;										//Final del programa
}


void *extraccion(void *arg){
	
	char *fichero=(void*)arg;
	int sig_buffer1=0;
	char recoger[11];
	FILE *leer=fopen(fichero,"r");
	fflush(stdout);
	
	if (leer == NULL) {
			fprintf(stderr, "ERROR al abrir el fichero\n");		//Se comprueba si el fichero existe
			exit(1);
		}
	
	while(feof(leer)==0){							//Mientras no haya llegado al final del fichero irea leyendo
		sem_wait(&espaciobuffer1);
		fscanf(leer,"%s\n",recoger);
		strcpy(buffer1[sig_buffer1],recoger);
		sem_post(&datobuffer1);
		sig_buffer1=(sig_buffer1+1)%TamBuffer1;
	}
	
	fclose(leer);
	pthread_exit(NULL);
}


int palindromo (char *palabra) {
	
	int esPalindromo = 1; // significa que sí lo es
	int i,j;
	j=strlen(palabra)-1;
	for (i=0; i<strlen(palabra)/2 && esPalindromo; i++, j--) {
		if (*(palabra+i)!=*(palabra+j)) {
			esPalindromo = 0; // No es palíndromo
		}
	}
	return esPalindromo;
}


void *comprobacion(void *arg){
	
	int dato1=0,sig_buffer2=0;
	char pal[11];
	int cont1=1;
	int resultado;
	while(cont1<numPal){						//Si aun no se han leido todas las palabras sigue funcionando
		sem_wait(&datobuffer1);
		strcpy(pal,buffer1[dato1]);
		sem_post(&espaciobuffer1);
		dato1=(dato1+1)%TamBuffer1;
		resultado=palindromo(pal);
		sem_wait(&espaciobuffer2);
		
		if(resultado==1) {
			sprintf(buffer2[sig_buffer2],"La palabra %s si es palindromo\n",pal);
			sem_post(&datobuffer2);
		}
		
		else {
			sprintf(buffer2[sig_buffer2],"La palabra %s no es palindromo\n",pal);
			sem_post(&datobuffer2);
		}										//Va a uno o a tro dependiendo de si es palindromo o no
		
		sig_buffer2=(sig_buffer2+1)%5;
		cont1++;
	}
	
	pthread_exit(NULL);
}


void *escribir(void *arg){
	
	int dato2=0;
	char salida[100];
	int cont2=1;
	FILE *escribir=fopen("Resultados.txt","w");
	
	if(escribir==NULL){
		fprintf(stderr,"ERROR al escribir en el fichero\n");		//Comprueba si se puede escribir en el fichero
		exit(1);
	}
	
	while(cont2<numPal){
		sem_wait(&datobuffer2);
		strcpy(salida,buffer2[dato2]);
		fprintf(escribir,"%s\n",salida);
		fflush(escribir);
		sem_post(&espaciobuffer2);
		dato2=(dato2+1)%5;
		cont2++;
	}
	
	fclose(escribir);
	pthread_exit(NULL);
}
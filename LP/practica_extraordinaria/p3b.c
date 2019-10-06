#include "lex.yy.c"
#include <stdlib.h>

int preanalisis; 

/**
*cadena: Contiene los nodos tal cual en en programa, con los arcos que los relacionan
*lista: Lista de todos los nodos existentes
*hijos: Numero de hijos de cada nodo, ordenados por el indice
*listaHijos: Lista de nodos hijo distintos para cada nodo
*padre: Nombre del nodo padre de cada nodo, ordenado por el indice
*longIds: Indica si un nodo de lista es de tipo longId o no
*longPadre: Indica si un nodo padre del nodo es de tipo longId o no
*tam: Entero con el numero de nodos en lista
*len: Entero con el numero de cadenas en cadena
*/

char** cadena;
char** lista;
char*** listaHijos;
int* hijos;
char** padre;
int* longIds;
int* longPadre;
int tam;
int len;

/**
*Añade a cadena el elemento en text, y aumenta len en uno.
*/
void encadena(char* text){
	cadena[len] = malloc(strlen(text)*sizeof(char));
	strncpy(cadena[len],text,strlen(text)*sizeof(char));
	len++;
}

/**
*Devuelve 0 o 1 si no existe el hijo del nodo o si existe
*/
int existeHijo(char* desc,int anc){

	for(int i = 0; i < hijos[anc]; i++){

		if(strcmp(desc,listaHijos[anc][i]) == 0){

			return 1;

		}

	}
	return 0;

}

/**
*Devuelve la posición de un nodo en la lista en base a su nombre, devuelve un entero positivo o -1 si no lo encuentra
*/
int getPos(char* c){

	for(int j = 0;j < tam; j ++){

		if(strcmp(lista[j],c) == 0){

			return j;

		}

	}

	return -1;

}


/**
*Determina a partir de cadena las relaciones entre los nodos, y als registra en los arrays de padre e hijos.
*/
void calculaHijos(){

	for(int i = 1; i < len-1; i++){

		if(strcmp(cadena[i],"--") == 0){

			if(strcmp(cadena[i-1],cadena[i+1]) != 0){

				int desc = getPos(cadena[i+1]);
				int anc = getPos(cadena[i-1]);

				if(existeHijo(cadena[i+1],anc) == 0){

					padre[desc] = malloc(strlen(cadena[i-1])*sizeof(char));
					strncpy(padre[desc],cadena[i-1],strlen(cadena[i-1])*sizeof(char));

					int num = hijos[anc];

					listaHijos[anc][num] = malloc(strlen(cadena[i+1])*sizeof(char));
					strncpy(listaHijos[anc][num],cadena[i+1],strlen(cadena[i+1])*sizeof(char));

					hijos[anc]++;

					if(longIds[getPos(padre[desc])] == 1){
						longPadre[desc] = 1;
					}

			}

			}else{

				int elem = getPos(cadena[i-1]);

					if(existeHijo(cadena[i-1],getPos(cadena[i-1])) == 0){

					/*padre[elem] = malloc(strlen(cadena[i-1])*sizeof(char));
					strncpy(padre[elem],cadena[i-1],strlen(cadena[i-1])*sizeof(char));*/

					int num = hijos[elem];

					listaHijos[elem][num] = malloc(strlen(cadena[i-1])*sizeof(char));
					strncpy(listaHijos[elem][num],cadena[i+1],strlen(cadena[i-1])*sizeof(char));

					hijos[elem]++;

				}	

			}

		}

	}

}


/**
*Comprueba con el nombre que el nodo no haya sido añadido previamente, y si no esta lo añade.
*/
void creaNodo(char* text){
	int existe = 0;
	int i;
	for(i = 0;i < tam ; i++){
		if(strcmp(lista[i],text) == 0){
			existe = 1;
		}
	}
	if(existe == 0){
		lista[tam] = malloc(strlen(text)*sizeof(char));
		strncpy(lista[tam],text,strlen(text)*sizeof(char));
		tam++;
	}
}

/**
*Parea el token de entrada.
*/
void parea (int token) {
	if (preanalisis == token) {
		preanalisis = yylex();
	} else {
		printf ("Componente léxico inesperado en %s\n", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*F -> graph id { L }
*/
void F(){
	if(preanalisis == GRAPH){
		parea(GRAPH);
		parea(ID);
		parea('{');
		L();
		parea('}');
	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*L -> C B
*/
void L(){
	if(preanalisis == ID || preanalisis == LONGID){
		C();
		B();
	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*B -> ; C B | epsilon
*/
void B(){
	if(preanalisis == ';'){
		parea(';');
		C();
		B();
	}else if(preanalisis == '}'){

	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*C -> N D
*/
void C(){
	if(preanalisis == ID || preanalisis == LONGID){
		N();
		D();
	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*D -> E | epsilon
*/
void D(){
	if(preanalisis == EDGE){
		E();
	}else if(preanalisis == '}' || preanalisis == ';'){
		
	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*N -> id | longId
*/
void N(){
	if(preanalisis == ID){
		creaNodo(yytext);
		encadena(yytext);
		parea(ID);
	}else if(preanalisis == LONGID){
		longIds[tam] = 1;
		creaNodo(yytext);
		encadena(yytext);
		parea(LONGID);
	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*E -> edge N D
*/
void E(){
	if(preanalisis == EDGE){
		encadena(yytext);
		parea(EDGE);
		N();
		D();
	}else{
		printf ("Error sintáctico en %s\n ", yytext);
		exit (EXIT_FAILURE);
	}
}

/**
*argv[1] contiene el nombre del fichero
*/
main(int argc,char *argv[]){

	/**
	*Inicializacion de elementos
	*/
	tam = 0;
	len = 0;
	lista = malloc(1000*sizeof(char*));
	cadena = malloc(1000*sizeof(char*));
	longIds = malloc(1000*sizeof(int));
	longPadre = malloc(1000*sizeof(int));

	/**
	*Compribacion de argumentos de entrada
	*/
	if(argc == 1){
		printf("Falta el nombre del archivo\n");
	}else if(argc > 2){
		printf("Sobran argumentos\n");
	}else{

		/**
		*Lectura del fichero
		*/
		yyin = fopen(argv[1],"r");

		/**
		*Analisis sintáctico
		*/

		preanalisis = yylex();
		F();

		/**
		*Creacion del array de numero de hijos
		*/
		hijos = malloc(tam*sizeof(int));

		for(int j = 0; j < tam; j++){
			hijos[j] = 0;
		}

		/**
		*Creacion del array de hijos
		*/
		listaHijos = malloc(tam*sizeof(char**));

		for(int j = 0; j < tam; j++){
			listaHijos[j] = malloc(1000*sizeof(char*));
		}

		/**
		*Creacion de la matriz de nodos padre
		*/
		padre = malloc(tam*sizeof(char*));

		/**
		*Creacion de la matriz de enteros
		*/
		int** matriz = malloc(tam*sizeof(int*));
		for(int i=0;i<tam;i++){
			matriz[i] = malloc(tam*sizeof(int));
		}

		/**
		*Funcion para calcular los hijos
		*/
		calculaHijos();

		/**
		*Apertura de nodos.txt
		*/
		FILE *nodos = fopen("nodos.txt","w");

		/**
		*La escritura es diferente si cada nodo o su padre son de tipo ID o LONGID
		*/
		for(int j = 0; j < tam; j++){

			if(padre[j] != NULL){

				if(longIds[j] == 1 && longPadre[j] == 1){
					//printf("%d 	%s 	%d 	%s\n",j,lista[j],hijos[j],padre[j]);
					fprintf(nodos,"%d 	%s 	%d 	%s\n",j,lista[j],hijos[j],padre[j]);
				}else if(longIds[j] == 0 && longPadre[j] == 1){
					//printf("%d 	\"%s\" 	%d 	%s\n",j,lista[j],hijos[j],padre[j]);
					fprintf(nodos,"%d 	\"%s\" 	%d 	%s\n",j,lista[j],hijos[j],padre[j]);
				}else if(longIds[j] == 1 && longPadre[j] == 0){
					//printf("%d 	%s 	%d 	\"%s\"\n",j,lista[j],hijos[j],padre[j]);
					fprintf(nodos,"%d 	%s 	%d 	\"%s\"\n",j,lista[j],hijos[j],padre[j]);
				}else{
					//printf("%d 	\"%s\" 	%d 	\"%s\"\n",j,lista[j],hijos[j],padre[j]);
					fprintf(nodos,"%d 	\"%s\" 	%d 	\"%s\"\n",j,lista[j],hijos[j],padre[j]);
				}

			}else{

				if(longIds[j] == 1){
					//printf("%d 	%s 	%d\n",j,lista[j],hijos[j]);
					fprintf(nodos,"%d 	%s 	%d\n",j,lista[j],hijos[j]);
				}else{
					//printf("%d 	\"%s\" 	%d\n",j,lista[j],hijos[j]);
					fprintf(nodos,"%d 	\"%s\" 	%d\n",j,lista[j],hijos[j]);
				}

			}

		}

		/**
		*Rellenar la matriz de enteros a partir de las relaciones entre nodos
		*/
		/*for(int i=0;i<tam;i++){
			if(padre[i] != NULL){
				matriz[i][getPos(padre[i])] = 1;
			}
		}*/
		for(int i = 0; i < tam; i++){
			for(int j = 0; j < hijos[i]; j++){
				//printf("%s --- %s\n",lista[i],listaHijos[i][j]);
				int pos = getPos(listaHijos[i][j]);
				if(pos != -1){
					matriz[i][pos] = 1;
				}
			}
		}

		/**
		*Apertura de arcos.txt
		*/
		FILE *arcos = fopen("arcos.txt","w");

		/**
		*La escritua tambien varia en funcion de si es ID o LONGID
		*/
		for(int i = 0;i<tam;i++){
			for(int j = 0;j < tam; j++){
				//printf("%d ",matriz[j][i]);
				fprintf(arcos,"%d ",matriz[i][j]);
			}
			//printf("\n");
			fprintf(arcos,"\n");
		}

		if (preanalisis == 0){ // yylex() devuelve 0 en el fin de fichero
			printf ("OK\n");
			
		}else{
			printf ("Sobra algo\n");
		}
	}
}

/**
 * Jonathan Ríos Girón
 * Daniel de Vicente Garrote
 **/
//Hay que terminar lo de ficheros de este programa
import java.util.Scanner;
import java.io.*;
public class dandevi {
    public static void menu(float punt,float record,Scanner escaner, int tablero[][], int tableroInicial[][]) throws FileNotFoundException {
    	File puntuaciones=new File("Escritorio/Puntuaciones.txt");
        System.out.print("1. Nueva partida\n2. Puntuaciones\n3. Borrar puntuaciones\n0. Salir\nElija una de estas opciones: ");
        int menu = escaner.nextInt();
        switch (menu) {		//Este switch permite elegir las diferentes opciones,vuelve a pedir si hay una eleccion incorrecta
            case 1:		//en el caso 1 inicia el juego(minimo y gopes recordados se utilizan en el propio juego)
                int golpesrecordados=0;
                int minimo=7;
                boolean volver=false;
                juego(record, punt, escaner, tablero, tableroInicial,volver,golpesrecordados,puntuaciones,minimo);
                break;
            case 3:
                System.out.print("¿Borrar puntuaciones?\n1:Si\n2:No\n");
                int selborrar=escaner.nextInt();
                switch(selborrar){
                case 1:		//Si el jugador elige que si se ponen las puntuaciones a 1
                	System.out.print("Borrado\n");
                	break;
                case 2:
                	break;
                default:
                	System.out.print("Opcion incorrecta,vuelva a introducir\n");
                	selborrar=escaner.nextInt();
                }
            	menu(punt,record,escaner,tablero,tableroInicial);
                break;
            case 2:
                mejorespuntuaciones(escaner,puntuaciones,punt,record);	//Muestra las mejores puntuaciones
                break;
            case 0:
                System.exit(0);		//Sale del programa
                break;
            default:
                System.out.print("Introduce una opcion correcta\n");
                menu(record, record, escaner,tablero, tableroInicial);		//Vuelve a menu a pedir de nuevo
                break;
        }
    }
              
    public static void juego(float record,float punt,Scanner escaner, int tablero[][],int[][] tableroInicial,boolean volver,int golpesrecordados,File puntuaciones,int minimo) throws FileNotFoundException{
        int golpes;
        if(volver==true){		//Sirve para mantener el numero de golpes dados si se recomienza o miran las puntuaciones
        	golpes=golpesrecordados;
        	volver=false;
        }else{
        	golpes=0;		//Si se reinicia se pone a cero
        }
        pintaTablero(tablero, golpes,minimo, punt);		//muestra el tablero en pantalla
        while (!tableroVacio(tablero)) {

            System.out.print("Elija las coordenadas a pulsar: ");
            int posicionX = escaner.nextInt() - 1;
            int posicionY = escaner.nextInt() - 1;
            while(posicionX<-1 || posicionX>=6 || posicionY<-3 || posicionY>=6 || posicionY==-1 || posicionY==-2){		//Se comprueba si los valores estan dentro de rango,si no estan los vuelve a pedir
            	System.out.print("Posicion inválida,vuelva a intentarlo\n");
                System.out.print("Elija las coordenadas a pulsar: ");
                posicionX=escaner.nextInt()-1;
                posicionY=escaner.nextInt()-1;
            }
                if(posicionX==-1){		//Si el jugador pone 0 en la primera coordenada,puede elegir una opcion especial
                	if(posicionY==0 || posicionY==1){
                		tablero=rellenar(tableroInicial);
                		juego(record,punt,escaner,tablero,tableroInicial,volver,golpesrecordados,puntuaciones,minimo);//e coge el tablero copiado al principio y se vuelve a juego
                	}else if(posicionY==2){
                		golpesrecordados=golpes;
                		mejorespuntuaciones(escaner, puntuaciones, punt, record);	//Guarda el numero de golpes para que al volver a llamar no se reinicie
                		volver=true;
                		juego(record,punt,escaner,tablero,tableroInicial,true,golpesrecordados, puntuaciones,minimo);
                	}else if(posicionY==3){
                		System.out.print("Es un tablero fijo,no se puede cambiar de nivel\n");		//Permite cambiar el nivel(solo en tablero aleatorio)
                		golpesrecordados=golpes;
                		volver=true;
                		juego(record,punt,escaner,tablero,tableroInicial,true,golpesrecordados, puntuaciones,minimo);
                	}else if(posicionY==-3){		//Esta opcion cierra el programa
                		System.exit(0);
                	}
                }
            restaPosicion(tablero, posicionX, posicionY);
            golpes++;		//Cada vez que se golpea una posicion se resta esa y las adyacentes,y se aumenta el nº de golpes a uno,luegose vuelve al tablero
            pintaTablero(tablero, golpes,minimo,punt);
        }
        	escribirrecord(puntuaciones, punt, record);		//Si se supera el record,se sobreescribe y pide al jugador si quiere otra partida
	        System.out.print("Juego terminado. ¿Desea echar otra?\n");
	        System.out.print("1:Si\n2:No\n");
	        int sel=escaner.nextInt();
	        switch(sel){		//un caso reinicia el juego,otro devuelve al menu y si esta fuera de rango vuelve a pedir
	        	case 1:{
	        		tablero=rellenar(tableroInicial);
	        		juego(record,punt,escaner,tablero,tableroInicial,volver,golpesrecordados, puntuaciones,minimo);
	        	}case 2:{
	        		menu(punt,record,escaner,tablero,tableroInicial);
	        	}default:{
	        		System.out.print("Error,vuelva a elegir\n(1:Si\n2:No\n");
	        		sel=escaner.nextInt();
	        	}
	        }
	    }
    public static int[][]rellenar(int tableroInicial[][]){
    	int relleno[][]=new int[6][6];
    	for(int i=0;i<6;i++){
    		for(int j=0;j<6;j++){
    			relleno[i][j]=tableroInicial[i][j];//Copia todo el tablero entero para el recomienzo
    		}
    	}
    	return relleno;
    }
    
    public static void mejorespuntuaciones(Scanner escaner,File puntuaciones,float punt,float record) throws FileNotFoundException{
    	System.out.print("            MEJORES PUNTUACIONES\n\n");		//Muestra las mejores puntuaciones guardadas en un fichero
    	mostrarpuntuacion(puntuaciones,punt,record);
    	System.out.print("PRESIONE UNA TECLA PARA CONTINUAR\n\n");
    	String salto=escaner.nextLine();
    	salto=escaner.nextLine();	//Permite que la pantalla de mejores puntuaciones se quede fija hasta que el jugador presione enter,luego vuelve al menu
    }
   
    public static void pintaTablero(int tablero[][], int golpes,int minimo,float punt) {
        System.out.print("\n\nRecomenzar ( 0 1 ) – Nuevo ( 0 2 ) – Calificación ( 0 3 ) – Cambiar nivel ( 0 4 ) – Salir (0 -2)\n\n");

        for (int numeroColumna = 1; numeroColumna <= tablero[0].length; numeroColumna++) {
            if (numeroColumna == 1) {
                System.out.print("   |" + "\t" + numeroColumna);
            } else {
                System.out.print("\t" + numeroColumna);
            }
        }
        System.out.println();
        for (int rayaArriba = 1; rayaArriba <= tablero[0].length + 2; rayaArriba++) {
            if (rayaArriba == 1) {
                System.out.print("___|_");
            } else {
                System.out.print("____");
            }
        }
        System.out.println();
        System.out.println("   |");
        for (int ii = 0; ii < tablero.length; ii++) {
            for (int jj = 0; jj < tablero[0].length; jj++) {
                if (jj == 0) {
                    System.out.print(ii + 1 + "  |" + "\t" + tablero[ii][jj]);
                } else {
                    System.out.print("\t" + tablero[ii][jj]);
                }
            }
            System.out.println();
        }
        for (int rayaAbajo = 1; rayaAbajo <= tablero[0].length + 2; rayaAbajo++) {
            if (rayaAbajo == 1) {
                System.out.print("___|_");
            } else {
                System.out.print("____");
            }
        }

        //System.out.println("\n\nNivel de juego: " + "( " + dificultad + "golpes)\t\t Puntuación en el nivel: " + puntuacion);
        
        	punt=(golpes/minimo);//Muestra la puntuacion segun el nº de golpes
        	
        System.out.println("\n\nGolpes realizados: " + golpes + "	Puntuacion:"+punt+"\n");

    }

    public static boolean tableroVacio(int tablero[][]) {
        int cont = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {
                    cont++;
                }		//Determina si el tablero esta vacio,si todos los huecos del tablero estan vacios devuelve true y avisa de que el juego esta terminado,si no devuelve false
            }
        }
        if(cont==36){
        	return true;
        }else{
        return false;
        }
    }

    public static void sumaPosicion(int tablero[][], int posicionX, int posicionY) {
        pintaSuma(tablero, posicionX, posicionY);
        if (posicionX + 1 < tablero.length) { // Arriba
            pintaSuma(tablero, posicionX + 1, posicionY);
        }
        if (posicionX - 1 >= 0) { // Abajo
            pintaSuma(tablero, posicionX - 1, posicionY);
        }
        if (posicionY + 1 < tablero[0].length) { // Derecha
            pintaSuma(tablero, posicionX, posicionY + 1);
        }
        if (posicionY - 1 >= 0) { // Izquierda
            pintaSuma(tablero, posicionX, posicionY - 1);
        }
    }

    public static void pintaSuma(int tablero[][], int posicionX, int posicionY) {
        if (tablero[posicionX][posicionY] == 3) {
            tablero[posicionX][posicionY] = 0;
        } else {
            tablero[posicionX][posicionY]++;
        }
    }

    public static void restaPosicion(int tablero[][], int posicionX, int posicionY) {
        pintaResta(tablero, posicionX, posicionY);
        if (posicionX + 1 < tablero.length) { // Arriba
            pintaResta(tablero, posicionX + 1, posicionY);
        }
        if (posicionX - 1 >= 0) { // Abajo
            pintaResta(tablero, posicionX - 1, posicionY);
        }
        if (posicionY + 1 < tablero[0].length) { // Derecha
            pintaResta(tablero, posicionX, posicionY + 1);
        }
        if (posicionY - 1 >= 0) { // Izquierda
            pintaResta(tablero, posicionX, posicionY - 1);
        }
    }

    public static void pintaResta(int tablero[][], int posicionX, int posicionY) {
        if (tablero[posicionX][posicionY] == 0) {
            tablero[posicionX][posicionY] = 3;
        } else {
            tablero[posicionX][posicionY]--;
        }
    }
//Sumaposicion y pintasuma aumentanen uno cad posicion y las adyacentes de posiciones aleatorias,si valia 3 lo pone a cero(solo sirve si es un tablero aleatorio)
//Restaposicion y pintaresta restan uno a la posicion y a las adyacentes,si valia cero lo pone a tres

    public static void main(String[] args){
        System.out.println("Bienvenido al juego\n");
        Scanner escaner = new Scanner(System.in);
        float punt=0,record=0;
        int tablero[][] = {{3,0,0,0,0,0},{0,1,2,0,0,0},{0,2,1,2,0,0},{0,0,2,1,2,0},{0,0,0,2,1,1},{0,0,0,0,1,0}};
        int tableroInicial[][] = {{3,0,0,0,0,0},{0,1,2,0,0,0},{0,2,1,2,0,0},{0,0,2,1,2,0},{0,0,0,2,1,1},{0,0,0,0,1,0}};
        menu(punt,record,escaner, tablero, tableroInicial);

    }
    public static void escribirrecord(File puntuaciones,float punt,float record){
    	try {
			FileWriter escribir=new FileWriter(puntuaciones);
			FileReader leer=new FileReader(puntuaciones);
			if((punt+record)/2>record){
				System.out.print("Enhorabuena,has conseguido un nuevo record de "+(punt+record)/2);
				String puntmax=Float.toString((punt+record)/2);
				escribir.write((int) ((record+punt)/2));
				escribir.close();
				leer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void mostrarpuntuacion(File puntuaciones,float punt,float record){
    	try {
    		Scanner leer=new Scanner(puntuaciones);
    		if(puntuaciones.exists()==false){
    			puntuaciones.createNewFile();
    		}
			int maxima=leer.read();
			System.out.print("Puntuacion maxima: "+maxima+"\n");
			leer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		}
    }

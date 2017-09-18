/**
 * Jonathan Ríos Girón
 * Daniel de Vicente Garrote
 **/

import java.util.Scanner;

public class Proyecto {

    public static void menu(Scanner escaner, int fila, int columna, int tablero[][], int tableroInicial[][]) {

        System.out.print(
                "1. Nueva partida\n2. Puntuaciones\n3. Borrar puntuaciones\n0. Salir\nElija una de estas opciones: ");
        int menu = escaner.nextInt();
        switch (menu) {		//Este switch permite elegir las opciones del menu y comprueba si es una opcion elegible
            case 1:
                System.out.print(
                        "\nElija su dificultad\n1. Tutorial(3 golpes)\n2. Muy facil(6 golpes)\n3. Bastante facil(9 golpes)\n4. Facil(12 golpes)\n5. Normal(15 golpes)\n6. Dificil(18 golpes)\n7. Muy dificil(21 golpes)\n8. Extremo(24 golpes)\n9. Extremo+(27 golpes)\n0. Salir\nElija una de estas opciones: ");
                int opcion = escaner.nextInt();
                while (opcion < 1 || opcion > 10) {
                    System.out.print(
                            "\nError, elija una de estas opciones:\n1. Tutorial(3 golpes)\n2. Muy facil(6 golpes)\n3. Bastante facil(9 golpes)\n4. Facil(12 golpes)\n5. Normal(15 golpes)\n6. Dificil(18 golpes)\n7. Muy dificil(21 golpes)\n8. Extremo(24 golpes)\n9. Extremo+(27 golpes)\n0. Salir\n\nElija una de estas opciones: \n");
                    opcion = escaner.nextInt();
                }
                int dificultad = opcion * 3;
                tablero = generacionTableroJuego(fila, columna, dificultad);
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[0].length; j++) {
                        tableroInicial[i][j] = tablero[i][j];		//Se hace copia del tablero una vez generado
                    }
                }
                juego(escaner, fila, columna, tablero, tableroInicial, dificultad);
                System.out.println("Tu puntuacion ha sido: ");
                break;
            case 2:
                System.out.print("2");
                break;
            case 3:
                System.out.print("3");
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.print("Introduce una opcion correcta");
                menu(escaner, fila, columna, tablero, tableroInicial);
                break;
        }
    }

    public static void juego(Scanner escaner, int fila, int columna, int tablero[][], int tableroInicial[][], int dificultad) {
        int golpes = 0;
        int opcion;
        pintaTablero(tablero, golpes);
        while (!tableroVacio(tablero, fila, columna)) {
            System.out.print("Elija las coordenadas a pulsar: ");
            int posicionX = escaner.nextInt() - 1;
            int posicionY = escaner.nextInt() - 1;
            if (posicionX >= 0 && posicionX <=7 && posicionY >= 0 && posicionY <= 7) {
                restaPosicion(tablero, posicionX, posicionY);
                golpes++;
                pintaTablero(tablero, golpes);
            }
            if (posicionX == -1) {
                switch (posicionY) {
                    case 0:
                        golpes = 0;
                        tablero = copiaMatriz(tableroInicial);
                        pintaTablero(tablero, golpes);
                        break;
                    case 1:
                        golpes = 0;
                        tablero = generacionTableroJuego(fila, columna, dificultad);
                        tableroInicial = copiaMatriz(tablero);
                        pintaTablero(tablero, golpes);
                        break;
                    case 2:
                        // Mostrar puntuaciones
                        break;
                    case 3:
                        golpes = 0;
                        System.out.print("Nueva dificultad: ");
                        opcion = escaner.nextInt();
                        dificultad = opcion * 3;
                        tablero = generacionTableroJuego(fila, columna, dificultad);
                        tableroInicial = copiaMatriz(tablero);
                        pintaTablero(tablero, golpes);
                        break;
                    case -3:
                        System.exit(0);
                        break;
                }
            }
        }
    }

    public static int [][] copiaMatriz (int tablero [][]){
        int tableroCopiado [][] = new int [tablero.length][tablero[0].length];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tableroCopiado [i][j] = tablero[i][j];
            }
        }
        return tableroCopiado;
    }

    public static void pintaTablero(int tablero[][], int golpes) {

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
        System.out.println("\n\nGolpes realizados: " + golpes + "\n");

    }

    public static boolean tableroVacio(int tablero[][], int fila, int columna) {
        int cont = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 0) {
                    cont++;
                }
            }
        }
        return (cont == fila * columna);
    }

    public static int [][] generacionTableroJuego(int fila, int columna, int dificultad) {
        int tablero [][] = new int [fila][columna];
        for (int i = 0; i < dificultad; i++) {
            sumaPosicion(tablero, generaNumeroAleatorio(0, tablero.length - 1),
                    generaNumeroAleatorio(0, tablero[0].length - 1));
        }
        return tablero;
    }

    public static int generaNumeroAleatorio(int inicio, int fin) {
        return (int) Math.floor(Math.random() * (inicio - (fin + 1)) + (fin + 1));
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

    public static void main(String[] args) {
        System.out.println("Bienvenido al juego\n");
        Scanner escaner = new Scanner(System.in);
        int fila = 6;
        int columna = 6;
        int tablero[][] = new int[fila][columna];
        int tableroInicial[][] = new int[fila][columna];
        menu(escaner, fila, columna, tablero, tableroInicial);

    }
}

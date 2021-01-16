%tablero=(x,e,o,e,e,e,x,o,x)
%
%x e o
%e e e
%x o x

linea(1,4,7).
linea(2,5,8).
linea(3,6,9).

linea(1,2,3).
linea(4,5,6).
linea(7,8,9).

linea(1,5,9).
linea(3,5,7).

%Identifica una casilla del tablero como vacia("")

vacio(Casilla,Tablero):-
    argumento(Casilla,Tablero,Valor),
    Valor=e.

%Identifica una casilla del tablero como cruz(x)

cruz(Casilla,Tablero):-
    argumento(Casilla,Tablero,Valor),
    Valor=x.

%Identifica una casilla del tablero como cara(o)

cara(Casilla,Tablero):-
    argumento(Casilla,Tablero,Valor),
    Valor=o.

%Detecta una amenaza del tipo (e,x,x)

amenaza([X,Y,Z],B,R):-
    vacio(X,B),
    cruz(Y,B),
    cruz(Z,B),
    R is X,
    !.

%Detecta una amenaza del tipo (x,e,x)

amenaza([X,Y,Z],B,R):-
    cruz(X,B),
    vacio(Y,B),
    cruz(Z,B),
    R is Y,
    !.

%Detecta una amenaza del tipo (x,x,e)

amenaza([X,Y,Z],B,R):-
    cruz(X,B),
    cruz(Y,B),
    vacio(Z,B),
    R is Z,
    !.

% Comprueba si el elemento actual es de un tipo de pieza determinada o
% es vacio

argumento(Posicion,Lista,X):-
    Posicion = 1,
    arg(1,Lista,X),
    !.

% Desplaza un elemento a la derecha en el array hasta llegar al
% correspondiente(Pos = 1)

argumento(Posicion,Lista,X):-
    Posicion > 1,
    arg(2,Lista,Y),
    Pos is Posicion-1,
    argumento(Pos,Y,X).

movimiento_forzoso(Tablero,Casilla):-
    linea(X,Y,Z),
    amenaza([X,Y,Z],Tablero,Casilla).





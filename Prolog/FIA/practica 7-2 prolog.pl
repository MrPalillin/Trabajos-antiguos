%numero de lados de una figura

triangulo(3).
cuadrado(4).
pantagono(5).
hexagono(6).

%X es la base

triangulo_eq(X,Y,Z):-
    X=Y,
    X=Z.

cuadrado(X,Y):-
    X=Y.

area_cuadrado(X,Y):-
    Area=X*Y.



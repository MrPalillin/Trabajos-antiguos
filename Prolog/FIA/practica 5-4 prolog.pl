gusta(juan,comida).
comida(manzanas).
comida(pollo).
comida(Y):-
    come(X,Y),
    vivo(X).
come(guillermo,cacahuetes).
vivo(guillermo).
come(susana,cacahuetes).


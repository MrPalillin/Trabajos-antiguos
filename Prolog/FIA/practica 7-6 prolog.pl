enlace(a,b,3).
enlace(a,c,2).
enlace(c,d,4).
enlace(c,e,5).
enlace(e,f,8).
enlace(f,g,10).

ruta(X,Y,D):-enlace(X,Y,D).

ruta(X,X,D):-
    D is 0.

ruta(X,Y,D):-
    enlace(X,Z,D2),
    ruta(Z,Y,D3),!,
    D is D3+D2.




jefe(asesor,contable).
jefe(tesorero,asesor).
jefe(calidad,tesorero).
jefe(operario1,reparto1).
jefe(operario2,reparto2).
jefe(ventas,operario1).
jefe(ventas,operario2).
jefe(calidad,ventas).
jefe(comercial,vendedor1).
jefe(comercial,vendedor2).
jefe(calidad,comercial).
jefe(director,calidad).

depende_de(X,Y):-
    jefe(X,Y),!.

depende_de(X,Y):-
    jefe(Z,Y),
    depende_de(X,Z),!.

niveles(X,X,N):-
    N is 0,!.

niveles(X,Y,N):-
    jefe(X,Y),
    N is 1,!.

niveles(X,Y,N):-
    jefe(Z,Y),
    niveles(X,Z,N2),!,
    N is N2+1.

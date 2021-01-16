suma([X],X).

suma([],0).

suma([X|Xs],N):-
    suma(Xs,N2),!,
    N is N2+X.

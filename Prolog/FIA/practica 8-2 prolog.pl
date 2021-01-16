longitud([],0).

longitud([_],1).

longitud([_|Xs],L):-
        longitud(Xs,L2),!,
        L is L2+1.


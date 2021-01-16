borrar(X,[X|Xs],Xs).

borrar(X,[Y|Ys],[Y|Zs]):-
    borrar(X,Ys,Zs).

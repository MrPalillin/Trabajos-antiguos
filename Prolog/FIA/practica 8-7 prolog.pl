sublista(X,[X|_],[]).

sublista(Y,[X,Y|_],[X]).

pertenece(X,[X|Xs]).

pertenece(X,[Y|Ys]):-pertenece(X,Ys).

sublista(X,[Y|Ys],Z):-
    pertenece(Y,[X|Xs]),
    sublista()

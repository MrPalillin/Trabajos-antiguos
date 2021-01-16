orden_creciente([X,Y]):-
    X<Y.

orden_creciente([X,Y|Xs]):-
    X<Y,
    orden_creciente([Y|Xs]).

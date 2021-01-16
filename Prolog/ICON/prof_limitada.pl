solve_pmax(true,_):-
    !.

solve_pmax((A,B),Prf):-
    !,
    solve_pmax(A,Prf),
    solve_pmax(B,Prf).

solve_pmax(A,Prf):-
    Prf > 0,
    clause(A,B),
    Prf1 is Prf-1,
    solve_pmax(B,Prf1).


valor(w1,1).
conectado(w2,w1).
conectado(w3,w2).

valor(W,X):-
    conectado(W,W1),
    valor(W1,X).

%METAINTERPRETE
:-op(40, xfy, &).
:-op(50, xfy, --->).
solve(true):- !.
solve((A,B)) :- !, solve(A), solve(B).
solve(A) :- clause(A, B), solve(B).

%BASE DE CONOCIMIENTO:
valor(w1, 1).
conectado(w2, w1).
conectado(w3, w2).
valor(W,X):-
    conectado(W,V),
    valor(V,X).
%CONSULTA:
%solve_proof(valor(W,V), (valor(W,V):-B)).

solve_pmax(true,_):-!.
solve_pmax((A,B),Prf):-!,
    NPrf is Prf-1,
    solve_pmax(A,NPrf),
    solve_pmax(B,NPrf).

solve_pmax(A,Prf):-
    Prf>0,
    clause(A,B),
    solve_pmax(B,Prf).


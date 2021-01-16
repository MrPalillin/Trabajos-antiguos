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

builtin(A is B).
builtin(A > B).
builtin(A < B).
builtin(A = B).
builtin(A =:= B).
builtin(A =< B).
builtin(A >= B).
builtin(functor(T, F, N)).
builtin(read(X)).
builtin(write(X)).
%CONSULTA:
%solve_proof(valor(W,V), (valor(W,V):-B)).

%solve_pmax(true,_):-!.
%solve_pmax((A,B),Prf):-!,
    %NPrf is Prf-1,
%    solve_pmax(A,NPrf),
%    solve_pmax(B,NPrf).

%solve_pmax(A,Prf):-
%    Prf>0,
%    clause(A,B),
%    solve_pmax(B,Prf).

solve_proof(true,true):-!.

solve_proof((A,B),(ProofA,ProofB)):-
    !,
    solve_proof(A,ProofA),
    solve_proof(B,ProofB).

solve_proof(A,(A:-builtin)):-
    builtin(A),
    !,
    A.

solve_proof(A,(A:-Proof)):-
    clause(A,B),
    solve_proof(B,Proof).

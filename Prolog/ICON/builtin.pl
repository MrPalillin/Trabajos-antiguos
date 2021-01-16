builtin(A is B).
builtin(A = B).
builtin(A >= B).
builtin(read(X)).
builtin(A > B).
builtin(A =:= B).
builtin(functor(T,F,N)).
builtin(write(X)).
builtin(A < B).
builti(A =< B).

solve(true):-
    !.

solve((A,B)):-
    solve(A),
    solve(B).

solve(A):-
    builtin(A),
    !,
    A.

solve(A):-
    clause(A,B),
    solve(B).

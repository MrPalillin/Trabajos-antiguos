builtin(A is B).
builtin(A = B).
builtin(A >= B).
builtin(read(X)).
builtin(A > B).
builtin(A < B).
builtin(A =:= B).
builtin(A =< B).
builtin(functor(T, F, N)).
builtin(write(X)).

solve(true,true):-
    !.

solve((A,B),(Pa,Pb)):-
    !,
    solve(A,Pa),
    solve(B,Pb).

solve(A,(A:-builtin)):-
    builtin(A),
    !,
    A.

solve(A,(A:-P)):-
    clause(A,B),
    solve(B,P).

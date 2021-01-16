solve_traza(true,_):-!.
solve_traza((A,B),Prf):-!,
    solve_traza(A,Prf),
    solve_traza(B,Prf).
solve_traza(A,Prf):-
    clause(A,B),
    Prf1 is Prf+1,
    solve_traza(B,Prf1),
    tab(Prf1*5), write(Prf1), write(' '), write(A),nl.
solve_traza(A):-!,
    solve_traza(A,0).

solve_traza_nivel(A):-
    !,
    solve_traza(A, 0).

solve(true, _):-!.
solve((A, B), Deep) :-!,
    Deep2 is Deep + 1,
    solve(A, Deep2),
    solve(B, Deep2).

solve(A, Deep):-
    clause(A,B),
    tab(Deep * 4), write(Deep), write(' '),  write(A), nl,
solve(B, Deep).

valor(w1,1).
conectado(w2,w1).
conectado(w3,w2).
valor(W,X):-conectado(W,V), valor(V,X).



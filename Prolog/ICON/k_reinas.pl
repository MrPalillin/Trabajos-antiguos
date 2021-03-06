diagReinas(_,[],_):- true.

diagReinas(Reina, [C|L], Col):-
    (Reina + Col) =\= C,
    (Reina - Col) =\= C,
    diagReinas(Reina, L, Col+1).

diagsOK([]):- !.

diagsOK([P|F]):-
    diagReinas(P, F, 1),
    diagsOK(F).

reinas(N, Res):-
    numlist(1, N, Base),
    permutation(Res, Base),
    diagsOK(Res).

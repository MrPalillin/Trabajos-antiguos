f(0,Y):-Y is 0.

f(1,Y):-Y is 1.

f(N,Y):-
    N1 is N-1,
    f(N1,A),
    N2 is N-2,
    f(N2,B),
    R1 is 3*A,
    R2 is 2*B,
    Y is R1+R2,
    !.

s(0,Y):- Y is 0,
    !.

s(1,Y):- Y is 1,
    !.

s(N,Y):-
    f(N,F),
    N1 is N-1,
    s(N1,Y1),
    Y is Y1+F.

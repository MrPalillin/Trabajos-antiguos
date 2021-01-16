fac(0,1).
fac(N,F):-
    N>0,N1 is N-1,fac(N1,F1),F is N*F1.

comb(M,N,R):-
    fac(M,X),
    fac(N,V),
    W is M-N,
    fac(W,Ws),
    Y is V*Ws,
    R is X/Y.


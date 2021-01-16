mueve(q0,a,[z],q0,[a|z]).
mueve(q0,a,[a|H],q0,[a|[a|H]]).
mueve(q0,b,[a|H],q1,H).
mueve(q1,b,[a|H],q1,H).
mueve(q1,[],[z],qf,[z]).

transita(q1,[],z,qf,[z]):-!.
transita(Qi,[X|Y],R,Qf,T):-
    X\=[],
    mueve(Qi,X,R,P,S),
    transita(P,Y,S,Qf,T).

acepta(X,Resultado):-
    transita(q0,X,[z],Q,_),
    Q=qf,
    Resultado is 1,
    !.


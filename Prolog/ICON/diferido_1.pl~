:-op(40,xfy,&).
:-op(50,xfy,--->).

light(L) & ok(L) & live(L)--->lit(L).
connected_to(W,W1) & live(W1)--->live(W).

true--->live(outside).
true--->s1(down).
true--->s2(up).
true--->s3(on).
true--->ok(_).
true--->connected_to(w5,outside).
true--->connected_to(p1,w3).
true--->connected_to(p2,w6).
true--->connected_to(l1,w0).
true--->connected_to(l2,w4).
true--->light(l1).
true--->light(l2).

ok(s2)& up(s2)--->connected_to(w0,w1).
ok(s1)& down(s1)--->connected_to(w2,w3).
ok(s3)& s3(on)--->connected_to(w4,w3).
ok(cb1)--->connected_to(w3,w5).
ok(cb2)--->connected_to(w6,w5).

solve(true):-!.
solve(A&B):-!,
    solve(A),
    solve(B).
solve(A):-
    clause(B,A),
    solve(B).

%dsolve(true,D,D):-
%    !.

%dsolve((A & B),D1,D2):-
%    !,
%    dsolve(A,D1,D3),
%    dsolve(B,D3,D2).

%dsolve(A,D,[A|D]):-
%    delay(A).

%dsolve(A,D1,D2):-
%    clause(A,B),
%    dsolve(B,D1,D2).

dsolve(true, D, D ):-!.
dsolve((A & B), D1, D2) :- !, dsolve(A, D1, DA), dsolve(B, D1, DB), union(DA, DB, D2).
dsolve(A, D1, D2) :- (B ---> A),  dsolve(B, D1, D2).
dsolve(A, D1, D2) :-!, A = ok(_), union([A], D1, D2).

dedo(indice):-magia(activada).
luce(X):-
    dedo(X),
    magia(activada).
delay(magia(activada)).











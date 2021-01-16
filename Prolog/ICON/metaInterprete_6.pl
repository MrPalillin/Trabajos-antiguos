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
    (B--->A),
    solve(B).

solve_traza(true):-
    !.

solve_traza((A & B)):-
    !,
    solve_traza(A),
    solve_traza(B).

solve_traza(A):-
    write('Call: '),
    write(A),
    nl,
    (B--->A),
    solve_traza(B),
    write('Exit: '),
    write(A),
    nl.

solve_traza_nivel(A):-
    solve(A,0).



solve_traza_nivel((A&B),Prf):-
    Prf1 is Prf + 1,
    solve_traza(A,Prf1),
    solve_traza(B,Prf1).


solve_traza_nivel(A,Prf):-
    (B--->A),
    tab(Prf*4),
    write(Prf),
    write('   '),
    write(A),
    nl,
    solve(B,Prf).










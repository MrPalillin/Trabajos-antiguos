q(X):-
    p(X),
    X1 is X+1,
    p(X1).

p(X):-
    0 =:= X mod 2.

%q(2)
%
%p(2)
%
%0 is 2 mod 2
%
%True
%
%X1 is 2+1
%
%X1 is 3
%
%p(3)
%
%0 is 3 mod 2
%
%False
%
%False.

insertar(X,[Y|Ys],[X,Y|Ys]).

insertar(X,[Ys],[Ys,X]).

insertar(X,[Y|Ys],[Y|Zs]):-
    insertar(X,Ys,Zs).

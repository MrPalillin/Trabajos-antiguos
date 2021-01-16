libro_de_familia(
    esposo(nombre(antonio,garcia,fernandez),profesion(arquitecto),salario(30000)),
    esposa(nombre(ana,ruiz,lopez),profesion(docente),salario(12000)),
    domicilio(sevilla)).

libro_de_familia(
    esposo(nombre(luis,alvarez,garcia),profesion(arquitecto),salario(40000)),
    esposa(nombre(ana,romero,soler),profesion(sus_labores),salario(0)),
    domicilio(sevilla)).

libro_de_familia(
    esposo(nombre(bernardo,bueno,martinez),profesion(docente),salario(12000)),
    esposa(nombre(laura,rodriguez,millan),profesion(medico),salario(25000)),
    domicilio(cuenca)).

libro_de_familia(
    esposo(nombre(miguel,gonzalez,ruiz),profesion(empresario),salario(40000)),
    esposa(nombre(belen,salguero,cuevas),profesion(sus_labores),salario(0)),
    domicilio(dos_hermanas)).

ingresos_familia(N1,N2,I):-
    libro_de_familia(esposo(nombre(N1,_,_),profesion(_),salario(S1)),esposa(nombre(N2,_,_),profesion(_),salario(S2)),_),
    I is S1+S2.

trabaja_de(T,N,A1,A2):-
    libro_de_familia(esposo(nombre(N,A1,A2),profesion(T),_),_,_);
    libro_de_familia(_,esposa(nombre(N,A1,A2),profesion(T),_),_).

vive_en(C,N,A1,A2):-
    libro_de_familia(esposo(nombre(N,A1,A2),_,_),_,domicilio(C));
    libro_de_familia(_,esposa(nombre(N,A1,A2),_,_),domicilio(C)).

no_trabajan(N,A1,A2):-
    libro_de_familia(esposo(nombre(N,A1,A2),_,salario(0)),_,_);
    libro_de_familia(_,esposa(nombre(N,A1,A2),_,salario(0)),_).




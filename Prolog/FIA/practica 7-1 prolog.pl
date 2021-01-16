%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Programa restaurante  %
%%%%%%%%%%%%%%%%%%%%%%%%%%%

% menu

entrada(paella).
entrada(gazpacho).
entrada(consome).

carne(filete_de_cerdo).
carne(pollo_asado).

pescado(trucha).
pescado(bacalao).

postre(flan).
postre(nueces_con_miel).
postre(naranja).

bebida(agua).
bebida(cerveza).
bebida(vino).

% Valor calorico de una ración

calorias(paella, 200).
calorias(gazpacho, 150).
calorias(consome, 300).
calorias(filete_de_cerdo, 400).
calorias(pollo_asado, 280).
calorias(trucha, 160).
calorias(bacalao, 300).
calorias(flan, 200).
calorias(nueces_con_miel, 500).
calorias(naranja, 50).

calorias(agua,10).
calorias(cerveza,100).
calorias(vino,40).

% plato_principal(P) P es un plato principal si es carne o pescado

plato_principal(P):- carne(P).
plato_principal(P):- pescado(P).

% comida(Entrada, Principal, Postre)

comida(Entrada, Principal, Postre):-
        entrada(Entrada),
        plato_principal(Principal),
        postre(Postre).

comida_con_bebida(Entrada,Principal,Postre,Bebida):-
        entrada(Entrada),
        plato_principal(Principal),
        postre(Postre),
        bebida(Bebida).

% Valor calorico de una comida

valor(Entrada, Principal, Postre, Valor):-
        calorias(Entrada, X),
        calorias(Principal, Y),
        calorias(Postre, Z),
        sumar(X, Y, Z, Valor).

valor_con_bebida(Entrada,Principal,Postre,Bebida,Valor):-
        calorias(Entrada, X),
        calorias(Principal, Y),
        calorias(Postre, Z),
        calorias(Bebida,A),
        sumar4(X, Y, Z, A, Valor).

% comida_equilibrada(Entrada, Principal, Postre)

comida_equilibrada(Entrada, Principal, Postre):-
        comida(Entrada, Principal, Postre),
        valor(Entrada, Principal, Postre, Valor),
        menor(Valor, 600).

comida_eq_con_bebida(Entrada, Principal, Postre,Bebida):-
        comida_con_bebida(Entrada, Principal, Postre,Bebida),
        valor_con_bebida(Entrada, Principal, Postre,Bebida, Valor),
        menor(Valor, 600).



% Conceptos auxiliares

sumar(X, Y, Z, Res):-
        Res is X + Y + Z.             % El predicado "is" se satisface si Res se puede unificar
                                      % con el resultado de evaluar la expresión X + Y + Z

sumar4(X,Y,Z,A,Res):-
        Res is X+Y+Z+A.
menor(X, Y):-
        X < Y.                        % "menor" numérico

dif(X, Y):-
        X =\= Y.                      % desigualdad numérica



tiene_consome(E,P,Po):-
        E = consome.

comida_eq_con_naranja(E,P,Po):-
        comida_equilibrada(E,P,Po),
        Po = naranja.

comida_con_500_cal(E,P,Po):-
        valor(E,P,Po,500).
















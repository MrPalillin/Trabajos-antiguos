hermanos(luis,alberto).
hermanos(alberto,luis).
hermanas(maria,luisa).
hermanas(luisa,maria).

conyuge(dani,marisa).
conyuge(marisa,dani).

conyuge(guille,diana).
conyuge(diana,guille).

hermanos(dani,guille).
hermanos(guille,dani).


hijo(dani,luis).
hijo(dani,alberto).
hija(guille,maria).
hija(guille,luisa).

conyuge(blas,pepi).
conyuge(pepi,blas).

hijo(blas,dani).
hijo(pepi,dani).
hijo(blas,guille).
hijo(pepi,guille).

mujer(maria).
mujer(luisa).
mujer(pepi).
mujer(diana).

hombre(dani).
hombre(blas).
hombre(guille).
hombre(luis).
hombre(alberto).


es_hijo_de(X,Y):-hijo(Y,X),hombre(X).

es_hija_de(X,Y):-hija(Y,X),mujer(X).

es_madre_de(X,Y):-(hija(Y,X);hijo(Y,X)),mujer(X).

es_padre_de(X,Y):-(hija(Y,X);hijo(Y,X)),hombre(X).

es_abuelo_de(X,Y):-(hijo(X,Z);hija(X,Z)),(hijo(Z,Y);hija(Z,Y)),hombre(X).

es_abuela_de(X,Y):-(hijo(X,Z);hija(X,Z)),(hijo(Z,Y);hija(Z,Y)),mujer(X).

es_primo_de(X,Y):-(es_hijo_de(X,Z);es_hija_de(X,Z)),(es_hijo_de(Y,W);es_hija_de(Y,W)),hermanos(W,Z).

es_prima_de(X,Y):-(es_hijo_de(X,Z);es_hija_de(X,Z)),(es_hijo_de(Y,W);es_hija_de(Y,W)),(hermanos(W,Z);hermanas(W,Z)).

es_nieto_de(X,Y):-(es_abuelo_de(Y,X);es_abuela_de(Y,X)),hombre(X).

es_nieta_de(X,Y):-(es_abuelo_de(Y,X);es_abuela_de(Y,X)),mujer(X).

es_tio_de(X,Y):-(hijo(Z,Y);hija(Z,Y)),hermanos(Z,X),hombre(X).

es_tia_de(X,Y):-(hijo(Z,Y);hija(Z,Y)),(hermanos(Z,X);hermanas(Z,X)),mujer(X).

es_sobrino_de(X,Y):-(es_tio_de(Y,X);es_tia_de(Y,X)),hombre(X).

es_sobrina_de(X,Y):-(es_tio_de(Y,X);es_tia_de(Y,X)),mujer(X).

% Detecta,hijos,hijas,abuelos,abuelas,primos,primas,hermanos,hermanas,nietos
% y nietas





encima_de(coche,tortuga).
encima_de(bombilla,coche).
encima_de(cafe,semaforo).
encima_de(lupa,cafe).
encima_de(balanza,martillo).
derecha_de(llave,tortuga).
derecha_de(semaforo,llave).
derecha_de(martillo,semaforo).
derecha_de(balanza,cafe).

derecha(X,Y):-derecha_de(X,Y).

derecha(X,Y):-derecha_de(X,Z),
              derecha(Z,Y).

encima(X,Y):-encima_de(X,Y).

encima(X,Y):-encima_de(X,Z),
             encima_de(Z,Y).


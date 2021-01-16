(deftemplate oav
	(slot objeto)
	(slot atributo)
	(slot valor)
)

(deffacts hechosPoolMackworth
	(oav 
		(objeto outside)
		(atributo corriente)
		(valor true)
	)
	(oav
		(objeto w5)
		(atributo conectadoA)
		(valor outside)
	)
;Bombillas
	;l1
	(oav
		(objeto l1)
		(atributo bombilla)
		(valor true)
	)
	(oav
		(objeto l1)
		(atributo ok)
		(valor true)
	)
	(oav
		(objeto l1)
		(atributo conectadoA)
		(valor w0)
	)	
	;l2
	(oav
		(objeto l2)
		(atributo bombilla)
		(valor true)
	)
	(oav
		(objeto l2)
		(atributo ok)
		(valor true)
	)
	(oav
		(objeto l2)
		(atributo conectadoA)
		(valor w4)
	)	
;Fusibles
	;cb1
	(oav
		(objeto cb1)
		(atributo ok)
		(valor true)
	)	
	;cb2
	(oav
		(objeto cb2)
		(atributo ok)
		(valor true)
	)	
;Enchufes	
	;p1
	(oav
		(objeto p1)
		(atributo conectadoA)
		(valor w3)
	)	
	(oav
		(objeto p1)
		(atributo ok)
		(valor true)
	)
	;p2	
	(oav
		(objeto p2)
		(atributo conectadoA)
		(valor w6)
	)	
	(oav
		(objeto p2)
		(atributo ok)
		(valor true)
	)
;Conmutadores
	;s1
	(oav
		(objeto s1)
		(atributo posicion)
		(valor abajo)
	)
	(oav
		(objeto s1)
		(atributo ok)
		(valor true)
	)
	;s2
	(oav
		(objeto s2)
		(atributo posicion)
		(valor arriba)
	)
	(oav
		(objeto s2)
		(atributo ok)
		(valor true)
	)
	;s3
	(oav
		(objeto s3)
		(atributo posicion)
		(valor arriba)
	)
	(oav
		(objeto s3)
		(atributo ok)
		(valor true)
	)
)

;Reglas de conexiones:
(defrule conexion_w0-w1
	(oav (objeto s2) (atributo ok) (valor true))
	(oav (objeto s2) (atributo posicion) (valor arriba))
	=>
	(assert (oav (objeto w0) (atributo conectadoA) (valor w1)) )
)
(defrule conexion_w0-w2
	(oav (objeto s2) (atributo ok) (valor true))
	(oav (objeto s2) (atributo posicion) (valor abajo))
	=>
	(assert (oav (objeto w0) (atributo conectadoA) (valor w2)) )
)
(defrule conexion_w1-w3
	(oav (objeto s1) (atributo ok) (valor true))
	(oav (objeto s1) (atributo posicion) (valor arriba))
	=>
	(assert (oav (objeto w1) (atributo conectadoA) (valor w3)) )
)
(defrule conexion_w2-w3
	(oav (objeto s1) (atributo ok) (valor true))
	(oav (objeto s1) (atributo posicion) (valor abajo))
	=>
	(assert (oav (objeto w2) (atributo conectadoA) (valor w3)) )
)

(defrule conexion_w4-w3
	(oav (objeto s3) (atributo ok) (valor true))
	(oav (objeto s3) (atributo posicion) (valor arriba))
	=>
	(assert (oav (objeto w4) (atributo conectadoA) (valor w3)) )
)

(defrule conexion_w3-w5
	(oav (objeto cb1) (atributo ok) (valor true))
	=>
	(assert (oav (objeto w3) (atributo conectadoA) (valor w5)) )
)
(defrule conexion_w6-w5
	(oav (objeto cb2) (atributo ok) (valor true))
	=>
	(assert (oav (objeto w6) (atributo conectadoA) (valor w5)) )
)

;Regla flujo de corriente por conexiones:
(defrule corriente
	(oav (objeto ?destino) (atributo conectadoA) (valor ?origen))
	(oav (objeto ?origen) (atributo corriente) (valor true))
	=>
	(assert (oav (objeto ?destino) (atributo corriente) (valor true)))
)

;Regla de bombillas que lucen:
(defrule luce
	(oav (objeto ?bombilla) (atributo bombilla) (valor true))
	(oav (objeto ?bombilla) (atributo ok) (valor true))
	(oav (objeto ?bombilla) (atributo corriente) (valor true))
	=>
	(assert (oav (objeto ?bombilla) (atributo luce) (valor true)))
)

;Garantizar la semantica univaluada:
(defrule garantizar-semantica
        (declare (salience 10000))
        ?f1 <- (oav (objeto ?objeto) (atributo ?atributo))
        ?f2 <- (oav (objeto ?objeto) (atributo ?atributo))
        (test (neq ?f1 ?f2))
        =>
        (retract ?f2)
)

;FIRE    1 conexion_w4-w3: f-20,f-19
;==> f-21    (oav (objeto w4) (atributo conectadoA) (valor w3))
;FIRE    2 conexion_w0-w1: f-18,f-17
;==> f-22    (oav (objeto w0) (atributo conectadoA) (valor w1))
;FIRE    3 conexion_w2-w3: f-16,f-15
;==> f-23    (oav (objeto w2) (atributo conectadoA) (valor w3))
;FIRE    4 conexion_w6-w5: f-10
;==> f-24    (oav (objeto w6) (atributo conectadoA) (valor w5))
;FIRE    5 conexion_w3-w5: f-9
;==> f-25    (oav (objeto w3) (atributo conectadoA) (valor w5))
;FIRE    6 corriente: f-2,f-1
;==> f-26    (oav (objeto w5) (atributo corriente) (valor true))
;==> Activation 0      corriente: f-25,f-26
;==> Activation 0      corriente: f-24,f-26
;FIRE    7 corriente: f-24,f-26
;==> f-27    (oav (objeto w6) (atributo corriente) (valor true))
;==> Activation 0      corriente: f-13,f-27
;FIRE    8 corriente: f-13,f-27
;==> f-28    (oav (objeto p2) (atributo corriente) (valor true))
;FIRE    9 corriente: f-25,f-26
;==> f-29    (oav (objeto w3) (atributo corriente) (valor true))
;==> Activation 0      corriente: f-23,f-29
;==> Activation 0      corriente: f-21,f-29
;==> Activation 0      corriente: f-11,f-29
;FIRE   10 corriente: f-11,f-29
;==> f-30    (oav (objeto p1) (atributo corriente) (valor true))
;FIRE   11 corriente: f-21,f-29
;==> f-31    (oav (objeto w4) (atributo corriente) (valor true))
;==> Activation 0      corriente: f-8,f-31
;FIRE   12 corriente: f-8,f-31
;==> f-32    (oav (objeto l2) (atributo corriente) (valor true))
;==> Activation 0      luce: f-6,f-7,f-32
;FIRE   13 luce: f-6,f-7,f-32
;==> f-33    (oav (objeto l2) (atributo luce) (valor true))
;FIRE   14 corriente: f-23,f-29
;==> f-34    (oav (objeto w2) (atributo corriente) (valor true))
;<== Focus MAIN
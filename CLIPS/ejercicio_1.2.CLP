(deffacts hechos-iniciales (hecho 1 ) (hecho 2 ) (hecho 3 ) (hecho 4 ) (hecho 5 ) (hecho 6 ))

(defrule regla-1 "hechos 1-2-3" (hecho 1) (hecho 2) (hecho 3)
=> (assert (regla disparada 1)))

(defrule regla-2 "hechos 3-2" (hecho 3) (hecho 2)
=> (assert (regla disparada 2)))

(defrule regla-3 "hecho 2-6-4" (hecho 2) (hecho 6) (hecho 4)
=>(assert (regla disparada 3)))

(defrule regla-4 "hecho 6-4-3-2" (hecho 6) (hecho 4) (hecho 3) (hecho 2)
=> (assert (regla disparada 4)))

;Depth: 3=>4=>1=>2
;Breath: 2=>1=>4=>3
;Simplicity: 2=>1=>3=>4
;Comlexity: 4=>1=>3=>2
;LEX: 4=>3=>1=>2
;MEA: 4=>2=>3=>1

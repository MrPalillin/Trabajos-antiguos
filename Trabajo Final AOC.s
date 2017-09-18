#Daniel de Vicente Garrote(dandevi)
#Daniel Crespo Martinez(dancres)

.data

Pregunta:	.asciiz "Escriba la fecha en la que calcular: \n"

De:		.asciiz " de "

Dias:		#Tamaño de cada dia=11 mas terminador
		.asciiz "Domingo, "
		.space 2
		.asciiz "Lunes, "
		.space 4
		.asciiz "Martes, "
		.space 3
		.asciiz "Miercoles, "
		.space 0
		.asciiz "Jueves, "
		.space 3
		.asciiz "Viernes, "
		.space 2
		.asciiz "Sabado, "
		.space 3


Meses:		#Tamaño de cada mes=10 mas terminador

		.asciiz	"Enero"
		.space 5
		.asciiz	"Febrero"
		.space 3
		.asciiz	"Marzo"
		.space 5
		.asciiz	"Abril"
		.space 5
		.asciiz	"Mayo"
		.space 6
		.asciiz	"Junio"
		.space 5
		.asciiz	"Julio"
		.space 5
		.asciiz	"Agosto"
		.space 4
		.asciiz	"Septiembre"
		.space 0
		.asciiz	"Octubre"
		.space 3
		.asciiz	"Noviembre"
		.space 1
		.asciiz	"Diciembre"
		.space 1

EDia:	.asciiz "Error, ese día es incorrecto \n"
EMes:	.asciiz "Error,ese mes es incorrecto \n"
Eyear:	.asciiz "Error,ese year es negativo \n"

Dato:		.space 32

.text
.globl __start
__start:			#'/'=47 en ascii
la $a0,Pregunta
li $v0 4
syscall
li $a1 20
la $a0,Dato			#$a0 Direccion de la cadena
li $v0 8
syscall
jal CadenaAFecha

Fin:
li $v0 10
syscall

ErrorDia:
la $a0,EDia
li $v0 4
syscall
j Fin

ErrorMes:
la $a0,EMes
li $v0 4
syscall
j Fin

Erroryear:
la $a0,Eyear
li $v0 4
syscall
j Fin


	CadenaAFecha:
	
	Dia:
	lb $t0,0($a0)			#$t0 Dia caracter a caracter
	beq $t0,47,PasoMes
	addi $t0,$t0,-48
	blt $t0,0,ErrorDia
	bge $t0,10,ErrorDia
	mul $t1,$t1,10
	add $t1,$t1,$t0			#$t1 Dia
	addi $a0,$a0,1
	j Dia
	
	PasoMes:
	add $s1,$zero,$t1		#$s1 Dia aparte
	add $t1,$zero,$zero
	addi $a0,$a0,1
	
	Mes:
	lb $t0,0($a0)			#$s0 Mes caracter a caracter
	beq $t0,47,Pasoyear
	addi $t0,$t0,-48
	blt $t0,0,ErrorMes
	bge $t0,10,ErrorMes
	mul $t1,$t1,10
	add $t1,$t1,$t0			#$t1 Mes
	addi $a0,$a0,1
	j Mes
	
	Pasoyear:
	add $s2,$zero,$t1		#$s2 Mes aparte
	add $t1,$zero,$zero
	addi $a0,$a0,1
	
	year:
	lb $t0,0($a0)			#$s0 year caracter a caracter
	beq $t0,10,Paso1
	addi $t0,$t0,-48
	blt $t0,0,Erroryear
	mul $t1,$t1,10
	add $t1,$t1,$t0			#$t1 year
	addi $a0,$a0,1
	j year

Paso1:
add $s3,$zero,$t1			#$s3 year aparte
add $t1,$zero,$zero
jal ValidarFecha
	
	jr $ra
	
	ValidarFecha:
	addi $t0,$zero,4
	bgt $s2,12,ErrorMes
	blt $s2,1,ErrorMes
	div $s3,$t0
	mfhi $t0			#$t0 Comprueba si es bisiesto		"1,3,5,7,8,10,12"=31	"4,6,9,11"=30	Febrero=29 o 28 si es bisiesto
	beq $s2,1,Valida31
	beq $s2,2,ValidaFebrero
	beq $s2,3,Valida31
	beq $s2,4,Valida30
	beq $s2,5,Valida31
	beq $s2,6,Valida30
	beq $s2,7,Valida31
	beq $s2,8,Valida31
	beq $s2,9,Valida30
	beq $s2,10,Valida31
	beq $s2,11,Valida30
	beq $s2,12,Valida31
	
	Valida31:
	bgt $s1,31,ErrorDia
	j Paso2
	
	Valida30:
	bgt $s1,30,ErrorDia
	j Paso2
	
	ValidaFebrero:
	beqz $t0,ValidaBisiesto
	bgt $s1,29,ErrorDia
	j Paso2
	
	ValidaBisiesto:
	bgt $s1,28,ErrorDia
	j Paso2
	
Paso2:

jal DiaSemana

	DiaSemana:
	div $t0,$s3,100		#Paso 1:Siglo
	bge $t0,22,Resta4
	bge $t0,21,Resta2
	bge $t0,20,Cero
	bge $t0,19,Suma1
	bge $t0,18,Suma3
	
	Resta4:
	li $t0,-4
	j yearSemana
	
	Resta2:
	li $t0,-2
	j yearSemana
	
	Cero:
	li $t0,0
	j yearSemana
	
	Suma1:
	li $t0,1
	j yearSemana
	
	Suma3:
	li $t0,3
	j yearSemana			#A en $t0
	
	yearSemana:			#Paso 2:year
	li $t6,100
	div $s3,$t6
	mfhi $t1
	li $t6,4
	div $t1,$t6
	mflo $t2
	add $t1,$t1,$t2		#B en $t1
	li $t2,0
	
	li $t6,100
	div $s3,$t6		#Paso 3:Bisiesto(enero y febrero)
	mfhi $t7
	beqz $t7,MesSemana
	li $t6,4
	div $s3,$t6
	mfhi $t7
	bne $t7,$zero,MesSemana
	bgt $s2,2,MesSemana
	li $t2,-1		#C en $t2
	
	MesSemana:			#Paso 4:Mes
	li $t3,-1
	
	seq $t3,$s2,1
	mul $t3,$t3,6
	beq $t3,6,ResultadoMes
	
	seq $t3,$s2,2
	mul $t3,$t3,2
	beq $t3,2,ResultadoMes
	
	seq $t3,$s2,3
	mul $t3,$t3,2
	beq $t3,2,ResultadoMes
	
	seq $t3,$s2,4
	mul $t3,$t3,5
	beq $t3,5,ResultadoMes
	
	beq $s2,5,MesMayo			#Debido a que aqui el $t2 debe dar cero,el procedimiento en mayo es un poco diferente
	
	seq $t3,$s2,6
	mul $t3,$t3,3
	beq $t3,3,ResultadoMes
	
	seq $t3,$s2,7
	mul $t3,$t3,5
	beq $t3,5,ResultadoMes
	
	seq $t3,$s2,8
	mul $t3,$t3,1
	beq $t3,1,ResultadoMes
	
	seq $t3,$s2,9
	mul $t3,$t3,4
	beq $t3,4,ResultadoMes
	
	seq $t3,$s2,10
	mul $t3,$t3,6
	beq $t3,6,ResultadoMes
	
	seq $t3,$s2,11
	mul $t3,$t3,2
	beq $t3,2,ResultadoMes
	
	seq $t3,$s2,12
	mul $t3,$t3,4		#Mes en $t3
	j ResultadoMes
	MesMayo:
	li $t3,0
	
	ResultadoMes:		#Paso 5:Dia
	add $t4,$t4,$s1		#Dia en $t4

	add $t6,$zero,$t0
	add $t6,$t6,$t1
	add $t6,$t6,$t2
	add $t6,$t6,$t3
	add $t6,$t6,$t4
	add $t6,$t6,$t5
	add $s4,$zero,$t6	#Dia de la semana en $s4
	
	bge $s4,7,Resta7
	j Paso3
	
	Resta7:
	addi $s4,$s4,-7
	bge $s4,7,Resta7
	blt $s4,7,Paso3
	
	jr $ra
	
Paso3:				#Dia de la semana terminado en $s4
add $t0,$zero,$zero
add $t1,$zero,$zero
add $t2,$zero,$zero
add $t3,$zero,$zero
add $t4,$zero,$zero
jal Imprimir

	Imprimir:
	la $s5,Dias		#Tabla de dias de la semana
	mul $t0,$s4,12
	add $a0,$s5,$t0
	li $v0 4
	syscall
	
	add $a0,$zero,$s1
	li $v0 1
	syscall
	
	la $a0,De
	li $v0 4
	syscall
	
	la $s5,Meses		#Tabla de meses
	mul $t0,$s2,11
	addi $t0,$t0,-11
	add $a0,$s5,$t0
	li $v0 4
	syscall
	
	la $a0,De
	li $v0 4
	syscall
	
	add $a0,$zero,$s3
	li $v0 1
	syscall
	j Fin
	
	jr $ra

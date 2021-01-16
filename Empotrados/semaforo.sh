#! /bin/ash

echo Ejecucion del script del semaforo
/usr/local/bin/gpio mode 0 out
/usr/local/bin/gpio mode 1 out
/usr/local/bin/gpio mode 2 out
/usr/local/bin/gpio mode 3 out
/usr/local/bin/gpio mode 4 out
/usr/local/bin/gpio mode 8 in
while [ true ];
do
/usr/local/bin/gpio write 2 1
/usr/local/bin/gpio write 3 1
while [ $(/usr/local/bin/gpio read 8) == 1 ];
do
sleep 0.1
done
/usr/local/bin/gpio write 2 0 
/usr/local/bin/gpio write 1 1
sleep 1
/usr/local/bin/gpio write 1 0
/usr/local/bin/gpio write 0 1
sleep 1
/usr/local/bin/gpio write 3 0
/usr/local/bin/gpio write 4 1
sleep 3

/usr/local/bin/gpio write 0 0
/usr/local/bin/gpio write 4 0
sleep 0.4
/usr/local/bin/gpio write 1 1
/usr/local/bin/gpio write 4 1
sleep 0.4
/usr/local/bin/gpio write 1 0
/usr/local/bin/gpio write 4 0
sleep 0.4
/usr/local/bin/gpio write 1 1
/usr/local/bin/gpio write 4 1
sleep 0.4
/usr/local/bin/gpio write 1 0
/usr/local/bin/gpio write 4 0
sleep 0.4
/usr/local/bin/gpio write 1 1
/usr/local/bin/gpio write 4 1
sleep 0.4
/usr/local/bin/gpio write 1 0
/usr/local/bin/gpio write 4 0
sleep 0.4
/usr/local/bin/gpio write 1 1
/usr/local/bin/gpio write 4 1

/usr/local/bin/gpio write 1 0
/usr/local/bin/gpio write 0 1
/usr/local/bin/gpio write 4 0
/usr/local/bin/gpio write 3 1
sleep 1
/usr/local/bin/gpio write 0 0
/usr/local/bin/gpio write 2 1
done 

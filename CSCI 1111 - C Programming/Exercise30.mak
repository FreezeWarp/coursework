Exercise30.exe:	Exercise30.o Exercise30Puts.o
	gcc -o Exercise30 Exercise30.o Exercise30Puts.o
Exercise30.o:	Exercise30.c
	gcc -c Exercise30.c
Exercise30Puts.o:	Exercise30Puts.c
	gcc -c Exercise30Puts.c
clean:
	rm -r Exercise30.exe Exercise30.o Exercise30Puts.o

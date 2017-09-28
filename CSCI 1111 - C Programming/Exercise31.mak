Exercise31.exe:	Exercise31.o Exercise31Functions.o
	gcc -o Exercise31 Exercise31.o Exercise31Functions.o
Exercise31.o:	Exercise31.c
	gcc -c Exercise31.c
Exercise31Functions.o:	Exercise31Functions.c
	gcc -c Exercise31Functions.c
clean:
	rm -r Exercise31.exe Exercise31.o Exercise31Functions.o

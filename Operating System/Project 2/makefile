CC = gcc
CFLAGS = -Wall -Wextra -pthread
LIBS = -lpthread

all: appserver appserver-coarse

appserver: appserver.c Bank.c
	$(CC) $(CFLAGS) appserver.c Bank.c -o appserver $(LIBS)

appserver-coarse: appserver-coarse.c Bank.c
	$(CC) $(CFLAGS) appserver-coarse.c Bank.c -o appserver-coarse $(LIBS)

clean:
	rm -f appserver appserver-coarse




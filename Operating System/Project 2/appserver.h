#ifndef APPSERVER_H
#define APPSERVER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/time.h>

// Constants
#define BUFFER_SIZE 1024

// Structures
typedef struct {
    int id;
    char buffer[BUFFER_SIZE];
} request_t;

// Functions
void *process_request(void *arg);
void handle_check(request_t *req);
void handle_trans(request_t *req);

#endif // APPSERVER_H



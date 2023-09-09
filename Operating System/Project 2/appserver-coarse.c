#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>
#include <sys/time.h>
#include "Bank.h"
#include <stdbool.h>
#include <stdarg.h>

typedef struct trans {
   int acc_id;
   int amount;
} trans;

typedef struct request {
   struct request *next;
   int request_id;
   int check_acc_id;
   struct trans *transactions;
   int num_trans;
   struct timeval starttime, endtime;
} request;

typedef struct queue {
   struct request *head, *tail;
   int num_jobs;
} queue;

// typedef struct {
//     Account accounts[1000];
//     pthread_mutex_t bank_mutex; // Coarse-grained mutex for the entire bank
// } Bank;


queue request_queue;
pthread_mutex_t queue_mutex;
pthread_cond_t queue_cond;

bool stop_working = false;
int num_threads;
pthread_t *threads;
FILE *output_file;
pthread_mutex_t *account_mutexes;
int num_accounts;


void free_request(request *req) {
   if (req->transactions) {
       free(req->transactions);
   }
   free(req);
}

void handle_end_request() {
    pthread_mutex_lock(&queue_mutex);
    stop_working = true;
    pthread_cond_broadcast(&queue_cond); // Wake up all waiting threads
    pthread_mutex_unlock(&queue_mutex);

    int i;
    for (i = 0; i < num_threads; i++) {
        pthread_join(threads[i], NULL);
    }

    while (request_queue.head != NULL) {
        request *req = request_queue.head;
        request_queue.head = request_queue.head->next;
        free_request(req);
    }

    pthread_mutex_destroy(&queue_mutex);
    pthread_cond_destroy(&queue_cond);
    free(threads);

    for (i = 0; i < num_accounts; i++) {
        pthread_mutex_destroy(&account_mutexes[i]);
    }

    free(account_mutexes);

    exit(0);
}

void lock_account(int acc_id) {
    pthread_mutex_lock(&account_mutexes[acc_id - 1]);
}

void unlock_account(int acc_id) {
    pthread_mutex_unlock(&account_mutexes[acc_id - 1]);
}

void lock_accounts_sorted(int acc_id1, int acc_id2) {
    if (acc_id1 < acc_id2) {
        lock_account(acc_id1);
        lock_account(acc_id2);
    } else {
        lock_account(acc_id2);
        lock_account(acc_id1);
    }
}

void unlock_accounts_sorted(int acc_id1, int acc_id2) {
    unlock_account(acc_id1);
    unlock_account(acc_id2);
}

// void *process_request(void *output_file) {

//     pthread_mutex_lock(&bank->accounts[account_id].mutex);
//     worker_thread(output_file);
//     pthread_mutex_unlock(&bank->accounts[account_id].mutex);
// }

void *worker_thread(void *output_file) {
   request *cur_request;
   int success;
   int balance;

   while (1) {
       pthread_mutex_lock(&queue_mutex);

       while (request_queue.num_jobs == 0 && !stop_working) {
           pthread_cond_wait(&queue_cond, &queue_mutex);
       }

       if (stop_working) {
           pthread_mutex_unlock(&queue_mutex);
           break;
       }

        if (request_queue.head == NULL) { // Add this check
            pthread_mutex_unlock(&queue_mutex);
            continue;
        }

        cur_request = request_queue.head;
        request_queue.head = request_queue.head->next;
        request_queue.num_jobs--;

        pthread_mutex_unlock(&queue_mutex);   

        fflush(output_file);

        if (cur_request->check_acc_id != 0) { // CHECK request
            printf("Processing CHECK request ID = %d\n", cur_request->request_id);
            lock_account(cur_request->check_acc_id);
            balance = read_account(cur_request->check_acc_id);
            unlock_account(cur_request->check_acc_id);

            gettimeofday(&cur_request->endtime, NULL);
            flockfile(output_file);
            fprintf(output_file, "%d BAL %d TIME %ld.%06ld %ld.%06ld\n", cur_request->request_id, balance, cur_request->starttime.tv_sec, cur_request->starttime.tv_usec, cur_request->endtime.tv_sec, cur_request->endtime.tv_usec);
            funlockfile(output_file);
            printf("Finished CHECK request ID = %d\n", cur_request->request_id); // Add this line

        } else { // TRANS request
            printf("Processing TRANS request ID = %d\n", cur_request->request_id);
            
            success = 1;
            int i;

            // Lock the mutexes in a sorted manner
            for (i = 0; i < cur_request->num_trans; i++) {
                pthread_mutex_lock(&account_mutexes[cur_request->transactions[i].acc_id - 1]);
            }

            for (i = 0; i < cur_request->num_trans; i++) {
                int acc_id = cur_request->transactions[i].acc_id;
                int amount = cur_request->transactions[i].amount;
                int current_balance = read_account(acc_id);

                if (current_balance - amount >= 0) {
                    write_account(acc_id, current_balance - amount);
                    success = 1;
                } else {
                    success = 0;
                    break;
                }   
            }
            
            // Unlock the mutexes in a sorted manner
            for (i = 0; i < cur_request->num_trans; i++) {
                pthread_mutex_unlock(&account_mutexes[cur_request->transactions[i].acc_id - 1]);
            }

            gettimeofday(&cur_request->endtime, NULL); // Get endtime here
            flockfile(output_file);
            fprintf(output_file, "%d %s TIME %ld.%06ld %ld.%06ld\n", cur_request->request_id, success ? "OK" : "ISF", cur_request->starttime.tv_sec, cur_request->starttime.tv_usec, cur_request->endtime.tv_sec, cur_request->endtime.tv_usec);
            funlockfile(output_file);
            printf("Finished TRANS request ID = %d\n", cur_request->request_id); // Add this line
        }

        free(cur_request->transactions);
        free(cur_request);
   }

   return NULL;
}

int main(int argc, char *argv[]) {
    if (argc != 4) {
        fprintf(stderr, "Usage: %s <# of worker threads> <# of accounts> <output file>\n", argv[0]);
        exit(1);
    }

    num_threads = atoi(argv[1]);
    threads = (pthread_t *)malloc(sizeof(pthread_t) * num_threads);
    num_accounts = atoi(argv[2]);

    // Initialize output_file
    output_file = fopen(argv[3], "a");

    if(output_file == NULL) {
        perror("Error opening file");
        exit(1);
    }

    initialize_accounts(num_accounts);

    // Initialize account mutexes
    int i;
    account_mutexes = (pthread_mutex_t *)malloc(sizeof(pthread_mutex_t) * num_accounts);
    for (i = 0; i < num_accounts; i++) {
        pthread_mutex_init(&account_mutexes[i], NULL);
    }

    pthread_mutex_init(&queue_mutex, NULL);
    pthread_cond_init(&queue_cond, NULL);

    request_queue.head = NULL;
    request_queue.tail = NULL;
    request_queue.num_jobs = 0;

    for (i = 0; i < num_threads; i++) {
        pthread_create(&threads[i], NULL, worker_thread, NULL);
    }

    char input[1024];
    int request_id = 1;
    printf("> ");

    while (fgets(input, sizeof(input), stdin)) {
        char *token = strtok(input, " \n");
        request *new_request = (request *)malloc(sizeof(request));
        new_request->next = NULL;
        new_request->request_id = request_id++;

        if (strcmp(token, "CHECK") == 0) {
            new_request->check_acc_id = atoi(strtok(NULL, " \n"));
            new_request->num_trans = 0;
            new_request->transactions = NULL;
            printf("Parsed CHECK request: account ID = %d\n", new_request->check_acc_id);
        } else if (strcmp(token, "TRANS") == 0) {
            new_request->check_acc_id = 0;
            int num_trans = 0;
            trans *transactions = (trans *)malloc(10 * sizeof(trans));

            while ((token = strtok(NULL, " \n")) != NULL) {
                transactions[num_trans].acc_id = atoi(token);
                transactions[num_trans].amount = atoi(strtok(NULL, " \n"));
                num_trans++;
            }

            new_request->num_trans = num_trans;
            new_request->transactions = transactions;
            printf("Parsed TRANS request with %d transactions\n", new_request->num_trans);

        } else if (strcmp(token, "END") == 0) {
            handle_end_request();
        } else {
            fprintf(stderr, "Invalid input: %s\n", token);
            free(new_request);
            continue;
        }

        gettimeofday(&new_request->starttime, NULL);
        printf("< ID %d\n", new_request->request_id);

        pthread_mutex_lock(&queue_mutex);

        if (request_queue.tail == NULL) {
            request_queue.head = new_request;
            request_queue.tail = new_request;
        } else {
            request_queue.tail->next = new_request;
            request_queue.tail = new_request;
        }
        request_queue.num_jobs++;

        pthread_mutex_unlock(&queue_mutex);
        pthread_cond_broadcast(&queue_cond);
        printf("> ");
    }

    for (i = 0; i < num_threads; i++) {
        pthread_join(threads[i], NULL);
    }

   // Add the cleanup code here in the main function
    for (i = 0; i < num_accounts; i++) {
        pthread_mutex_destroy(&account_mutexes[i]);
    }
    free(account_mutexes);

    pthread_mutex_destroy(&queue_mutex);
    pthread_cond_destroy(&queue_cond);

    fclose(output_file);

    return 0;
}
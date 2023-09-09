#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


int main(int argc, char **argv) {
    char *prompt = "308sh> ";
    int i;

    // check for -p option
    for (i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-p") == 0 && i+1 < argc) {
            prompt = argv[i+1];
            break;
        }
    }

	// start shell loop
	while (1) {
	    // print prompt and read input
	    printf("%s", prompt);
	    char input[1024];
	    if (fgets(input, sizeof(input), stdin) == NULL) {
		// end of file (ctrl-d)
		break;
	    }
	    // remove newline from input
	    input[strcspn(input, "\n")] = '\0';

	    // remove ampersand from input if present
	    char *amp = strchr(input, '&');
	    if(amp != NULL){
	        if (amp == input + strlen(input) - 1) {
		    // remove ampersand from end of input
		    *amp = '\0';
		    amp = NULL;
	         } else {
		    // invalid use of ampersand
		    fprintf(stderr, "Invalid use of ampersand\n");
		    continue;
	        }
	    }

	    // process input command
	    if (strcmp(input, "exit") == 0) {
		// exit the shell
		break;
	    } else if (strcmp(input, "pid") == 0) {
		// print the process ID of the shell
		printf("Shell PID: %d\n", getpid());
	    } else if (strcmp(input, "ppid") == 0) {
		// print the process ID of the parent process
		printf("Parent PID: %d\n", getppid());
	    } else if (strncmp(input, "cd", 2) == 0) {
		// change working directory
		char *dir = input + 3; // skip "cd "
		if (dir[0] == '\0') {
		    // no argument, change to home directory
		    chdir(getenv("HOME"));
		} else {
		    // change to specified directory
		    if (chdir(dir) == -1) {
		        perror("cd");
		    }
		}
	    } else if (strcmp(input, "pwd") == 0) {
		// print working directory
		char cwd[1024];
		if (getcwd(cwd, sizeof(cwd)) != NULL) {
		    printf("%s\n", cwd);
		} else {
		    perror("pwd");
		}
	    } else {
		// spawn child process to execute program command
		pid_t pid = fork();
		if (pid == -1) {
		    perror("fork");
		} else if (pid == 0) {
		    // child process
		    char *args[1024];
		    int argc = 0;
		    char *arg = strtok(input, " ");
		    while (arg != NULL) {

		        int len = strlen(arg); 
		        while (len > 0 && isspace(arg[len-1])) { 
			   arg[--len] ='\0';
			}

		        args[argc++] = arg;
		        arg = strtok(NULL, " ");
		    }
		    args[argc] = NULL;
		    printf("[%d] %s\n", getpid(), args[0]);
		    execvp(args[0], args);
			
   	 	    // If execvp fails, print the error message and exit
   	 	    fprintf(stderr, "Cannot exec %s: No such file or directory\n", args[0]);
		    //perror(args[0]); 
		    exit(EXIT_FAILURE);
		} else {
		    // parent process
		    //int status;
		    if (amp == NULL) { 
		        // foreground command
			int status;
		        if (waitpid(pid, &status, 0) == -1) {
		            perror("waitpid");
		        } else {
		            printf("[%d] %s Exit %d\n", pid, input, WEXITSTATUS(status));
		        }
		    } else {
		        // background command
		        printf("[%d] %s\n", pid, input);
		        fflush(stdout); // flush output before returning to prompt
		        printf("%s", prompt);
			continue;
		    }
		}
	    }
	}

	return 0;

}








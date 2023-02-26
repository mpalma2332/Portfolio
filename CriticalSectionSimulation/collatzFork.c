
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>


void collatz(int n){
	if(n > 0){
		printf("%d", n);
	}
	while(n != 1){
		if(n == 0 || n < 0){
			break;
		}
		else if(n % 2 == 0){
			n = n / 2;
			printf(", %d", n);
		}
		else{
			n = 3 * n + 1;
			printf(", %d", n);
		}
	}
	printf("\n");
}

int main() {
	int n;
	pid_t pid;
	do {
		printf("Enter a positive integer (non-positive to quit): ");
		scanf("%d", &n);
		pid = fork();
		if(pid < 0){
			printf("failed to create child");
			return 1;
		}
		// this is the child process
		else if(pid == 0){
			collatz(n);
		}
		else{
			wait(NULL);
		}

	}
	while(n > 0 && pid != 0);

	return 0;
}

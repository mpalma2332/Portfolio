/*
 ============================================================================
 Name        : collatz.c
 Author      : Michael Palma
 Description : Collatz Conjecture
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
}

int main() {
	int n;
	do {
		printf("Enter a positive integer (non-positive to quit): ");
		scanf("%d", &n);
		collatz(n);
		printf("\n");
	}
	while(n > 0);

	return 0;
}

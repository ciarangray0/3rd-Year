#include <stdio.h>

int main(int arg, char* argc[]){
    int intVar; //integer
    int *intPtr; //integer pointer
    long longVar; //long int
    double *doublePtr; //double pointer
    char **charPtrPtr; //pointer of a char pointer

    //printing sizes using sizeof()
    printf("Size of int: %lu bytes\n", sizeof(intVar));
    printf("Size of int*: %lu bytes\n", sizeof(intPtr));
    printf("Size of long: %lu bytes\n", sizeof(longVar));
    printf("Size of double*: %lu bytes\n", sizeof(doublePtr));
    printf("Size of char**: %lu bytes\n", sizeof(charPtrPtr));
  return 0;
}
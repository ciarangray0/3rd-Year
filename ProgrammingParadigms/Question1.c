#include <stdio.h>

int main(int arg, char* argc[]){
    int intVar;
    int *intPtr;
    long longVar;
    double *doublePtr;
    char **charPtrPtr;

    //printing sizes using sizeof()
    printf("Size of int: %lu bytes\n", sizeof(intVar));
    printf("Size of int*: %lu bytes\n", sizeof(intPtr));
    printf("Size of long: %lu bytes\n", sizeof(longVar));
    printf("Size of double*: %lu bytes\n", sizeof(doublePtr));
    printf("Size of char**: %lu bytes\n", sizeof(charPtrPtr));
    //int vs int*: The size of int depends on the system architecture (commonly 4 bytes on most modern systems). However, the size of a pointer (int*) is based on the system's memory architecture. For example, on a 64-bit system, pointers are usually 8 bytes regardless of the data type they point to, while on a 32-bit system, they are 4 bytes.
    //long: Similar to int, the size of long varies by system architecture. On many systems, long can be 4 or 8 bytes. The actual size is platform-specific.
    //Pointer Types (double*, char**): All pointers (like int*, double*, char**) generally have the same size because they hold memory addresses. The size of these pointers is independent of the type of data they point to (whether it's an int, double, or char). Therefore, the size of double* or char** should be the same as int* on a given architecture (e.g., 8 bytes on a 64-bit system).
  return 0;
}
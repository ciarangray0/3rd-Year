#include <Kernel/sys/_types/_size_t.h>
#ifndef CT331_ASSIGNMENT_LINKED_LIST
#define CT331_ASSIGNMENT_LINKED_LIST

typedef struct listElementStruct listElement;

//functions for printing elements depending on what data type they are
void printString(void* data);
void printInt(void* data);
void printChar(void* data);
void printFloat(void* data);
void printDouble(void* data);

//Creates a new linked list element with given content of size
//Returns a pointer to the element
listElement* createEl(void* data, size_t size, void (*pf)(void*));

//Prints out each element in the list
void traverse(listElement* start);

//Inserts a new element after the given el
//Returns the pointer to the new element
listElement* insertAfter(listElement* after, void* data, size_t size, void (*pf)(void*));

//Delete the element after the given el
void deleteAfter(listElement* after);

//returns length of the list
int length(listElement* list);

void push(listElement** list, void* data, size_t size, void (*pf)(void*));

void* getData(listElement* element);

listElement* pop(listElement** list);

void enqueue(listElement** list, void* data, size_t size, void (*pf)(void*));

listElement* dequeue(listElement* list);

#endif
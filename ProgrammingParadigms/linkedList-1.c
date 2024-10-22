#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "linkedList-1.h"

typedef struct listElementStruct{
  void* data;
  size_t size;
  struct listElementStruct* next;
  void (*pf)(void* data);
} listElement;

void printString(void* data) { //print a string 
  printf("%s\n", (char*)data);
}

void printInt(void* data) { //print an integer
  printf("%d\n", *(int*)data);
}

void printChar(void* data) { //print a character
  printf("%c\n", *(char*)data);
}

void printFloat(void* data) {
    printf("%f\n", *(float*)data);  //print a float
}

void printDouble(void* data) {
    printf("%lf\n", *(double*)data);  //print a double
}


//Creates a new linked list element with given content of size
//Returns a pointer to the element
listElement* createEl(void* data, size_t size, void (*pf)(void*)){
  listElement* e = malloc(sizeof(listElement));
  if(e == NULL){
    //malloc has had an error
    return NULL; //return NULL to indicate an error.
  }
  void* dataPointer = malloc(size); //just allocate the ammount of memory given in the size parameter
  if(dataPointer == NULL){
    //malloc has had an error
    free(e); //release the previously allocated memory
    return NULL; //return NULL to indicate an error.
  }
  memcpy(dataPointer, data, size); //use memcpy instead of strcpy
  e->data = dataPointer;
  e->size = size;
  e->next = NULL;
  e->pf = pf; //set the elements correct print function
  return e;
}

void* getData(listElement* element) {
  return element->data;
}

//Prints out each element in the list
void traverse(listElement* start){
  listElement* current = start;
  while(current != NULL){
    current->pf(current->data); //call the element's print function
    current = current->next;
  }
}

//Inserts a new element after the given el
//Returns the pointer to the new element
listElement* insertAfter(listElement* el, void* data, size_t size, void (*pf)(void*)){
  listElement* newEl = createEl(data, size, pf);
  listElement* next = el->next;
  newEl->next = next;
  el->next = newEl;
  return newEl;
}

int length(listElement* list) {
  int counter = 0; //to count number of elements
  listElement* current = list;
  while(current!= NULL){ //loop until theres no elements
    current = current->next;
    counter++; //update counter
  }
  return counter;
}

void push(listElement** list, void* data, size_t size, void (*pf)(void*)) {
  listElement* newElement = createEl(data, size, pf);
  if(!newElement) {
    return;
  }
  newElement->next = *list;
  *list = newElement;
}

listElement* pop(listElement** list) {
     if (*list == NULL) {
        return NULL; //if the list is empty, return NULL
    }
  listElement* newHead = (*list)->next;
  listElement* temp = *list;
  *list = newHead;
    return temp;
}


//Delete the element after the given el
void deleteAfter(listElement* after){
  listElement* delete = after->next;
  listElement* newNext = delete->next;
  after->next = newNext;
  //need to free the memory because we used malloc
  free(delete->data);
  free(delete);
}


void enqueue(listElement** list, void* data, size_t size, void (*pf)(void*)) {
  listElement* newElement = createEl(data, size, pf); //create new element
    if (newElement == NULL) {
        return; //handle element creation failure
    }
    if (*list == NULL) {
        *list = newElement; // if the list is empty, set the new element as the head of the list
        return;
    }
    newElement->next = *list; //else, set the new elelements next pointer to the old head of the list
    *list = newElement; //set new element to head of the list
}

listElement* dequeue(listElement* list) {
    if (list == NULL) {
        return NULL; //if the list is empty, return NULL
    }

    //if there's only one element in the list
    if (list->next == NULL) {
        listElement* temp = list; //store the only element
        free(list->data); //free the data of the last element
        free(list); //free the last element
        return temp; //return the only element
    }

    //traverse to the second-to-last element
    listElement* current = list;
    while (current->next->next != NULL) {
        current = current->next; //move to the second-to-last element
    }

    //remove the last element
    listElement* temp = current->next; //store the last element
    current->next = NULL; //set the second-to-last element's next to NULL
    return temp; //return the dequeued element
}

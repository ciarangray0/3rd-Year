#include <stdio.h>
#include "tests.h"
#include "linkedList-1.h"

void runTests(){
  printf("Tests running...\n");
  listElement* l = createEl("Test String (1).", 30, printString);
  //printf("%s\n%p\n", l->data, l->next);
  //Test create and traverse
  traverse(l);
  printf("\n");

  //Test insert after
  listElement* l2 = insertAfter(l, "another string (2)", 30, printString);
  
  double testDouble1 = 225.356; //here i must pass the address of a double, because c does not allow casting from double to void*
  insertAfter(l2, &testDouble1, sizeof(double), printDouble);
  traverse(l);
  printf("\n");

//test length
  printf("\nSize of list: %d", length(l));

   printf("\n");
   printf("\nDeleting after the second element");
   printf("\n");
  // Test delete after
  deleteAfter(l2);
  traverse(l);

   printf("\n");
   printf("\n");
   printf("testing stack \n");
   printf("\n");
   listElement* stack = createEl("test stack element (1).", 30, printString);
  //test pushing a new value to head of the list
  push(&stack, "c", sizeof(char), printChar);
  traverse(stack);
  printf("\n");
  printf("\n");
  
  int testInt1 = 3; //here i must pass the address of an int, because c does not allow casting from int to void*
  push(&stack, &testInt1, sizeof(int), printInt);
  traverse(stack);
  printf("\n");
  printf("\n");

  pop(&stack);
  printf("popping list element\n");
  printf("\n");
  printf("\n");
  traverse(stack);
  printf("\n");
  printf("\n");

  //create the queue
   printf("testing queue \n");
  listElement* queue = createEl("first queue string", 30, printString);
  traverse(queue);
  printf("\n");
  printf("\n");
  
  int testInt2 = 10; //here i must pass the address of an int, because c does not allow casting from int to void*
  enqueue(&queue, &testInt2, sizeof(int), printInt);
  traverse(queue);
  
  printf("\n");
  printf("\n");
  float testFloat = 3.5f; //here i must pass the address of a float, because c does not allow casting from float to void*
  enqueue(&queue, &testFloat, sizeof(float), printFloat);
  traverse(queue);

  printf("\n");
  printf("\n");
  
  dequeue(queue);
  printf("dequeuing list element\n");
  printf("\n");
  traverse(queue);
  printf("\n");

  printf("\nTests complete.\n");
}
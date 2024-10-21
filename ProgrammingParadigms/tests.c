#include <stdio.h>
#include "tests.h"
#include "linkedList-1.h"

void runTests(){
  printf("Tests running...\n");
  listElement* l = createEl("Test String (1).", 30);
  //printf("%s\n%p\n", l->data, l->next);
  //Test create and traverse
  traverse(l);
  printf("\n");

  //Test insert after
  listElement* l2 = insertAfter(l, "another string (2)", 30);
  insertAfter(l2, "a final string (3)", 30);
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
   listElement* stack = createEl("test stack element (1).", 30);
  //test pushing a new value to head of the list
  push(&stack, "pushing onto stack (2)", 30);
  traverse(stack);
  printf("\n");
  printf("\n");
  
  push(&stack, "pushing onto stack (3)", 30);
  traverse(stack);
  printf("\n");
  printf("\n");

  listElement* poppedElement = pop(&stack);
  printf("popping list element: %p", getData(poppedElement));
  printf("\n");
  printf("\n");
  traverse(stack);
  printf("\n");
  printf("\n");

  //create the queue
   printf("testing queue \n");
  listElement* queue = createEl("first queue string", 30);
  traverse(queue);
  printf("\n");
  printf("\n");
  
  enqueue(&queue, "second queue string", 30);
  traverse(queue);
  
  printf("\n");
  printf("\n");

  enqueue(&queue, "third queue string", 30);
  traverse(queue);

  printf("\n");
  printf("\n");

  listElement* dequeuedElement = dequeue(queue);
  printf("dequeuing list element: %p\n", getData(dequeuedElement));

  printf("\n");
  traverse(queue);
  printf("\n");

  printf("\nTests complete.\n");
}
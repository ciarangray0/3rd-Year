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
  //test pushing a new value to head of the list
  push(&l, "new head of list", 30);
  traverse(l);

  printf("\n");
  listElement* poppedElement = pop(&l);

  printf("popping list element: %s", getData(poppedElement));

  printf("\n");
  traverse(l);
  printf("\n");
  printf("\n");

  //create the queue
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
  printf("dequeuing list element: %s\n", getData(dequeuedElement));

  printf("\n");
  traverse(queue);
  printf("\n");

  printf("\nTests complete.\n");
}
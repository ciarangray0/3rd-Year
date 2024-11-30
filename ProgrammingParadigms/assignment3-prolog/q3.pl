isNotElementInList(_, []). %base case, if the list is empty, the element is not in it, true
isNotElementInList(Element, [Head | Tail]) :- 
Element \= Head, isNotElementInList(Element, Tail). %if the element is not equal to the head, recursively call and check for the tail

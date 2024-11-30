insertInOrder(El, [], [El]). % base case, inserting an elemnt into an empty list results in the element

insertInOrder(El, [Head|Tail], [El, Head| Tail]) :- El < Head. % if the head is biggeer than the element, insert the element before the head

insertInOrder(El, [Head|Tail], [Head | NewList]) :- El > Head, insertInOrder(El, Tail, NewList). %if the element is bigger than the head, recurively call the function for the tail of the list
mergeLists([], [], [], []). %base case, if all lists are empty result is an empty list

mergeLists([Head1 | Tail1], L2, L3, [Head1 | FinalList]) :- 
    mergeLists(Tail1, L2, L3, FinalList). %add head of first list to the result, and recursively call for the tail

mergeLists([], [Head2 | Tail2], L3, [Head2 | FinalList]) :- 
    mergeLists([], Tail2, L3, FinalList). %if first list is empty, add head of list 2 to result and recursively call for the tail

mergeLists([], [], [Head3 | Tail3], [Head3 | FinalList]) :- 
    mergeLists([], [], Tail3, FinalList). %if first and second list is empty, add head of list 3 to result and recursively call for the tail

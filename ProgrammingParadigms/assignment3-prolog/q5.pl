%fact that if concatenating 2 lists, and the first list is empty, the result is the second list
conc([], L2, L2). &base case

conc([H1|T1], L2, [H1 | L3]):- conc(T1, L2, L3). %for concatenating the lists, add the head of the first list to the result and recurively call for the tail

%for reversing a list
reverseList([], []). %base case, if the list is empty, the result will be another empty list

reverseList([Head | Tail], Reverse):- 
reverseList(Tail, ReverseTail), conc(ReverseTail, [Head], Reverse). %concatenate the head of the list with the reverselist, and recurively call reverse for the tail
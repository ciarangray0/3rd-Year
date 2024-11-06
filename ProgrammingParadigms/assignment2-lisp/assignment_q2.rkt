(define (ins_beg el1 l2)
  (cons el1 l2)) ;add the element to the front of the list(cons already does this)

(define (ins_end el1 l2)
  (append l2 (list el1))) ;add the singular element to a list and append it to the end of the list

(define (count_top_level list)
  (if (null? list)
      0                           ;return count of 0 if the list is empty(base case)
      (+ 1 (count_top_level (cdr list)))))  ;increment the count and recurse on the cdr of the list

(define (count_instances list item)
  (if(null? list) ;if list is null, return 0 (base case)
  0
  (+ (if (equal? (car list) item) ;if the item is equal to the first element of the list, add 1
         1
         0) ;add 0
     (count_instances (cdr list) item)))) ;recursively call the end of the list

(define (count_instances_tr_helper item list)
  (count_instances_tr item list 0))  ;start with count 0 and call the function

(define (count_instances_tr item list count)
  (if (null? list)                       ;if list is empty, return the count passed into function(starts with 0)(base case)
      count                            
      (count_instances_tr item (cdr list) (if (equal? (car list) item) ;recurive call with cdr of list, check if count is equal to the first element
                                                 (+ count 1)            ;increment count if matched
                                                 count))))              ;otherwise, keep count the same

  
(define (count_instances_deep list item)
  (if (null? list) ;check if list is empty (base case)
      0 ;if the list is empty, return 0
      (if (list? (car list)) ;check if the first element is a list
          (+ (count_instances_deep (car list) item) ;pass in the sub list to the function
             (count_instances_deep (cdr list) item)) ;continue with the rest of the list
          (if (equal? (car list) item) ;check if the first element matches the item
              (+ 1 (count_instances_deep (cdr list) item)) ;if it is equal, increment count and continue
              (count_instances_deep (cdr list) item))))) ;if not, continue with the rest of the list


;;testing functions
  ;;test ins_beg
  (display "ins_beg tests:\n")
  (display (ins_beg 'a '(b c d))) ;expected: (a b c d)
  (newline)
  (display (ins_beg '(a b) '(b c d))) ;expected: ((a b) b c d)
  (newline)

  ;;test ins_end
  (display "\nins_end tests:\n")
  (display (ins_end 'a '(b c d))) ;expected: (b c d a)
  (newline)
  (display (ins_end '(a b) '(b c d))) ;expected: (b c d (a b))
  (newline)

  ;;test count_top_level function
  (display "\ncount_top_level tests:\n")
  (display (count_top_level '(a b c))) ;expected: 3
  (newline)
  (display (count_top_level '())) ;expected: 0
  (newline)

  ;;test count_instances function
  (display "\ncount_instances tests:\n")
  (display (count_instances '(a b a c a) 'a)) ;expected: 3
  (newline)
  (display (count_instances '(b c d) 'a)) ;expected: 0
  (newline)

  ;;test count_instances_tr function
  (display "\ncount_instances_tr tests:\n")
  (display (count_instances_tr_helper 'a '(a b a c a))) ;expected: 3
  (newline)
  (display (count_instances_tr_helper 'x '(b c d))) ;expected: 0
  (newline)

  ;;test count_instances_deep function
  (display "\ncount_instances_deep tests:\n")
  (display (count_instances_deep '((a b) c (a d)) 'a)) ;expected: 2
  (newline)
  (display (count_instances_deep '(b (c a) d (a (b a))) 'a)) ;expected: 3
  (newline)
  (display (count_instances_deep '(b c d) 'a)) ;expected: 0
  (newline)

        

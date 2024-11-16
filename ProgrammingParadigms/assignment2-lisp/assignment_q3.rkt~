(define (traverse tree)
  (if (null? tree) ;first check if tree is empty
      '() ;return empty tree if empty
      (append(traverse (car tree)) ;traverse the left side of the tree
      (list (cadr tree)) ;visit the root node
      (traverse (caddr tree))))) ;traverse the right subtree

(define (search_tree tree value)
  (if (null? tree)
      #f ;if list is empty, return false
      (if (equal? (cadr tree) value) ;check if the value is equal to the node and return true is so, otherwise continue
          #t
          (if (< value (cadr tree)) ;check if the value is less then the current node
              (search_tree (car tree) value) ;if it is, search the left subtree
              (search_tree (caddr tree) value))))) ;if not, search the right subtree

(define (insert tree value)
  (if (null? tree) ;check if tree is empty
      (list '() value '()) ;(base case) create a new node with empty subtrees
      (if (equal? (cadr tree) value) ;check if the value is equal to the node
          tree ;value already exists, return the tree unchanged
          (if (< value (cadr tree)) ;check if the value is smaller then the current node
              (list (insert (car tree) value) (cadr tree) (caddr tree)) ;insert in left subtree
              (list (car tree) (cadr tree) (insert (caddr tree) value)))))) ;insert in right subtree


(define (helper_insert_items tree items)
  (if (null? items)
      tree ;if theres no items, return the tree(base case)
      (helper_insert_items (insert tree (car items)) (cdr items)))) ;insert the item into the tree and recurively call the function with the rest of the items

(define (insert_items items)
  (helper_insert_items '() items)) ;call the helper function and start with an empty tree

(define (tree_sort items)
  (traverse (insert_items items)))

;comparison functions for the higher order tree_sort algorithm
(define (ascending x y)
  (< x y))

(define (descending x y)
  (> x y))

(define (ascending-by-last-digit x y)
  (if (equal? (modulo x 10) (modulo y 10)) ;if the last digit are equal, just sort by the full number
      (< x y)
      ( < (modulo x 10) (modulo y 10)))) ;else, sort by the last digit by removing the left-side digit using modulo

(define (insert_by_custom_order tree value comparator)
  (if (null? tree) ; Check if the tree is empty
      (list '() value '()) ; Create a new node with empty subtrees
      (if (equal? (cadr tree) value) ; If the value is equal to the node
          tree ; Return the tree unchanged
          (if (comparator value (cadr tree)) ; Use the comparator to decide
              (list (insert_by_custom_order (car tree) value comparator) (cadr tree) (caddr tree)) ; Insert in left subtree
              (list (car tree) (cadr tree) (insert_by_custom_order (caddr tree) value comparator)))))) ; Insert in right subtree


(define (helper_hlts tree items comparator) ; helper function for higher_level_tree_sort
  (if (null? items)  ; if there are no items, return the tree (base case)
      tree
      (helper_hlts (insert_by_custom_order tree (car items) comparator) (cdr items) comparator))) ; insert the item and call recursively

; Higher-level tree sort function
(define (higher_level_tree_sort items comparator)
  (traverse (helper_hlts '() items comparator))) ; pass comparator to the helper function












;; Test values
(define test-list '(34 7 23 32 5 42 1 12 8))

;; Testing ascending order
(define (test-ascending)
  (display "Ascending order: ")
  (display (higher_level_tree_sort test-list ascending))
  (newline))

;; Testing descending order
(define (test-descending)
  (display "Descending order: ")
  (display (higher_level_tree_sort test-list descending))
  (newline))

;; Testing ascending order based on last digit
(define (test-ascending-by-last-digit)
  (display "Ascending by last digit: ")
  (display (higher_level_tree_sort test-list ascending-by-last-digit))
  (newline))


  (test-ascending)
  (test-descending)
  (test-ascending-by-last-digit)







  
      
      
      
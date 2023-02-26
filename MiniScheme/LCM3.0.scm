(define n (read))
(newline)
(define myLCM (read))
(newline)
;(do ((i 0 (+ i 1)))
 ;   ((>= i 10)
  ;   (void))
  ;(begin
   ; ;; Perform some operations on i
    ;(display i)
    (;newline)))
     ;;; Define a, b, and temp variables
;(define a 10)
;(define b 5)
;(define temp 0)

;; Iterate until b is equal to 0
;(do ((b 5 (modulo a b)))
 ;   ((eqv? b 0)
  ;   a))

     
(do ((x '(1 2 3 4 5) (cdr x))  
    (sum 0 (+ sum (car x))))
    ((null? x)
     sum))
(do ((i 1 (+ i 1))
     (define next (read))
     (define a lcm)
     (define b next))
     ((< i n))
  (do ((b next (modulo a b)))
    (eqv? b 0)
    a)
(define myGcd a)
(* lcm (lcm (quotient next myGcd)))

(write lcm)
     


(define add (lambda (x y) (+ x y)))
(write(add 2 3))
(newline)

(define factorial
  (lambda (n)
    (if (eqv? n 0)
        1
        (* n (factorial(- n 1))))))

(write(factorial 5))
                    
                   
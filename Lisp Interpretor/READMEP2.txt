Jigar Chandra 
chandra.67@osu.edu
-------------------------------------------------------------------------
There is just one source file for the entire interpreter (TokenGenerator.java).

This file has to be compiled first using the following command:
javac TokenGenerator.java

Once the source code is compiled, you can directly run it using the following command:
java TokenGenerator

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
NOTE:
============================================================================================================================================================================================

1) Whenever there are unclosed brackets in the s-expression, the interpreter assumes that the s-expression is incomplete and 
   waits for the remaining part of the s-expression to be input.
Example: Suppose you want to enter "(1 2) " and you enter "(1 2 " the interpreter will wait till you enter the remaining ")" 

.........................................................................

4) When you want to enter multiple lines of input, do not copy and paste the entire thing at once. Instead, copy one line at a time and enter.
Example:
Suppose you want to enter
(DEFUN F (A B)
(COND
((EQ A 0) (PLUS B 1))
((EQ B 0) (F (MINUS2 A 1) 1))
(T (F(MINUS2 A 1) (F A (MINUS2 B 1))))
))

Then do not copy paste the entire input at once. Instead, do it one line at a time as shown below
(DEFUN F (A B) *carriage return*
(COND *carriage return*
.
.
.

At any point of time, if there is any error due to an illegal S-Expression , the interpretor outputs "Error " / " Illegal Expression "

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Part 2:
============================================================================================================================================================================================
Validation for Special Forms:
=============================
========
1) COND:
========
a) COND cannot have empty set of boolean expressions. For example,  (COND) will give an error.
b) Each boolean expression needs to have a condition and a predicate. For example, both (COND T) and (COND (T)) will give an error.

2) DEFUN:
=========
a) DEFUN is allowed only at the outermost level. It won't be allowed at any lower level. In case of nested DEFUN, the interpreter will 
   give an error while the defined function is called and the interpreter tries to execute it. In case DEFUN is inside a COND, 
   the interpreter will immediately give an error, since the interpreter tries to evaluate the COND statement 
   immediately, instead of just adding the definition to the Definition List.

b) The interpreter does not allow the user to define a function with name as a constant (integers, T and NIL) or any of the built-in functions.
   For example, the user would not be able to define a new function named CAR, CDR, etc.

c) The interpreter does not allow the user to define a function with parameters as constants, T and NIL.
   For example, the interpreter would not allow the user to define the following function: (DEFUN MYADD (T NIL) NIL), or (DEFUN F (1) T)

d) In case the function does not accept any parameters, the user has to write () in place of parameters in the function definition,
   else the interpreter will give an error.
   For example, (DEFUN F T) will give an error. (DEFUN F () T) does not give an error.

e) Moreover, both function name and function parameters cannot be a list. Note that there can be a list of parameters but each 
   parameter needs to be an atom. For example, both (DEFUN (F) (X) T) and (DEFUN F ((X)) T) will give an error.

f) In case the function definition does not contain a body, the interpreter will give an error. For example, (DEFUN F (X)) will give an error.


=========
3) QUOTE:
=========
QUOTE cannot have empty set of arguments. It also cannot take more than one s-expression as arguments. 
It needs to have exactly one s-expression as argument. For example, both (QUOTE) and (QUOTE 1 2) will give an error.


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Other Validation Cases Handled:
============================================================================================================================================================================================
1) Invalid Number of Parameters (both for user-defined and built-in functions

2) EQ will give an error if non-atomic s-expressions are passed to it as arguments.

3) Functions PLUS, MINUS, TIMES, QUOTIENT, REMAINDER, LESS, GREATER will give an error if non-integer s-expressions are passed to it as arguments.

4) CAR and CDR will give an error if atomic s-expressions are passed to it as arguments.

5) Parameter bindings for only the current function are checked. Bindings of the outside function will not be checked.
Consider the following example:
(DEFUN G (Y) (COND (T X)))
(DEFUN F (X) (G X))
(F 3)
Will give an error 

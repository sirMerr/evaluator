# evaluator
This project convert an infix expression to a postfix expression and then evaluate the postfix expression. 
The input to the evaluator is a queue containing the infix expression and the output is a queue with the result 
as the only entry. It uses its own Queue and Stack, no Java Collections.

The infix expression is made up of floating point numbers and four operators. The operators are 
- add(`+`), 
- subtract(`-`), 
- multiply(`*`) and 
- division(`/`). 

It also implements parenthesis `()`. 

If the infix queue is not properly formatted then throw a named exception and uses parameterized testing (no user interface
just unit testing).

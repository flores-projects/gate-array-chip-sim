GATE ARRAY CHIP SIMULATOR
Written by Jose M Flores

This program was created as a project for my COSC 321 class at Eastern Michigan University. It takes user input consisting of connections between a set number of inputs, NOR gates on a simulated chip, and outputs. It will then calculate a table listing all possible combinations of those inputs and their respective outputs.

HOW TO GENERATE YOUR OWN WIRING LIST
The format goes as so:
[LHS]>[RHS]

This statement tells the program to connect LHS to RHS.

In LHS, you can have a lowercase letter a-z to represent a variable, or a gate, represented by Gxy, where x is a number 0-9
for the horizontal position on the array, and y is a number 0-9 for the vertical position.

In RHS, you can have a gate input, GxyIz. x and y are the same idea as above, and z is a number that is either 0 or 1,
which tells the program which input you wish to connect to, or you can have Mx, which represents an output.
X is a number 1 - 9 that represents the output number if you wish to have more than one output.

Line breaks tell the program that you are done with one connection and to move on to the next.

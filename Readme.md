To run correctly the Logipix solver, please read the instructions below:

1 - The Input must be a file in txt format belonging to the folder InputFiles. The first two lines give the width and height of the grid, respectively. The following lines must contain the clues of each cell line by line, using 0 for the empty cells.


2 - The program uses the JavaFX 11. First, it needs to have Java 11 installed in the computer, as well as JavaFX 11 runtime (https://gluonhq.com/products/javafx/). 

3 - Change the command prompt's directory to the project's and add an environment variable pointing to the lib directory of the runtime:
	For Linux/Mac: export PATH_TO_FX=path/to/javafx-sdk-11.0.2/lib
	For Windows: set PATH_TO_FX="path\to\javafx-sdk-11.0.2\lib"

4 - Compile the classes Cell, BrokenLine, Position, Pair, Logipix normally. For the GameInterface, use the following command:
	For Linux/Mac: javac --module-path $PATH_TO_FX --add-modules=javafx.controls GameInterface.java
	For Windows: javac --module-path %PATH_TO_FX% --add-modules=javafx.controls HelloFX.java

5 - Finally, run the program using:
	For Linux/Mac: java --module-path $PATH_TO_FX --add-modules=javafx.controls GameInterface
	For Windows: java --module-path %PATH_TO_FX% --add-modules=javafx.controls GameInterface

6 - To change the input file, in the line 25 from GameInterface.java change name of the file to the desired one. 
	Example: logipix.initialize(titulo="InputFiles/Man.txt");

For more information: https://openjfx.io/openjfx-docs/

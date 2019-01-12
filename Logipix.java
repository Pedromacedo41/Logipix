import java.util.*;
import java.io.File;
import java.io.FileNotFoundException; 
import java.io.IOException;

public class Logipix{

	File input;
	Cell[][] GameGrid;
	int sizeX, sizeY;
	ArrayList<LinkedList<Cell>> brokenLines;

	public static void main(String[] args) throws FileNotFoundException {
		Logipix logipix = new Logipix();
		logipix.initialize("InputFiles/LogiX.txt");
		logipix.print();
	}

	public void initialize(String name){
		brokenLines = new ArrayList<>();
		read(name);

	}
	private void read(String name){
		try {
	        input = new File(name);
	        Scanner readInput = new Scanner(input);
	      	this.sizeX= readInput.nextInt();
	      	this.sizeY= readInput.nextInt();
	      	GameGrid = new Cell[sizeY][sizeX];
	   		for (int i=0;i<sizeY;i++) {
	   			for (int j=0;j<sizeX; j++) {
	   				int s = readInput.nextInt();
	   				GameGrid[i][j]= new Cell(i,j,s);	

	   			}
	   		}

   		}catch (IOException e) {
        	System.err.println("Error while opening file");
            e.getMessage();
        }
    }

     public void print(){
    	System.out.print("+");
    	for (int j=0;j<sizeX; j++) {
		 		System.out.print("----+");			
		   	}
		   	System.out.print("\n");

	    for (int i=0;i<sizeY;i++) {
	    	System.out.print("|");
		   	for (int j=0;j<sizeX; j++) {
		   		if(GameGrid[i][j].clue!=0) System.out.print(" "+ GameGrid[i][j].clue+ "  |");	
		   		else System.out.print("    |");
		   		
		   	}
		   	System.out.print("\n");
		   	System.out.print("+");
		   	for (int j=0;j<sizeX; j++) {
		 		System.out.print("----+");			
		   	}
		   	System.out.print("\n");
		 }
    }

    public void example(){

    }

    private void modify(Cell voisin1, Cell voisin2){

    }
}
import java.util.*;
import java.io.File;import java.io.File;
import java.io.FileWriter;
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
		logipix.example();
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
		 printBrokenLines();
    }

    private void printBrokenLines(){
    	for (int i=0; i < brokenLines.size(); i++ ) {
    		LinkedList<Cell> line = brokenLines.get(i);
    	}
    }

    private void printLine(LinkedList<Cell> line){
    	Position pos; 
    	Cell[] Line = (Cell[]) line.toArray();
    	for (int i=0; i < (Line.length-1) ; i++) {    		
    		if(Line[i].x != Line[i+1].x){
    			if(Line[i].x > Line[i+1].x) pos= Position.LEFT;
    			else pos = Position.RIGHT;
    		}else{
    			if(Line[i].y > Line[i+1].y) pos= Position.UP;
    			else pos = Position.DOWN;
    		}
    	}




    }

    public void example(){
    	LinkedList<Cell> a = new LinkedList<>();
    	a.add(GameGrid[0][0]);
    	a.add(GameGrid[0][1]);
    	brokenLines.add(a);

    	LinkedList<Cell> b = new LinkedList<>();
    	b.add(GameGrid[8][0]);
    	b.add(GameGrid[8][1]);
    	b.add(GameGrid[8][2]);
    	b.add(GameGrid[7][2]);
    	brokenLines.add(b);

    	LinkedList<Cell> c = new LinkedList<>();
    	c.add(GameGrid[5][4]);
    	c.add(GameGrid[5][5]);
    	c.add(GameGrid[6][5]);
    	c.add(GameGrid[6][4]);
    	c.add(GameGrid[6][3]);
    	c.add(GameGrid[7][3]);
    	brokenLines.add(c);
    	print();
    }
}
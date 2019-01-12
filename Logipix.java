import java.util.*;
import java.io.File;import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException; 
import java.io.IOException;
 //Oi ollar
public class Logipix{

	File input;
	Cell[][] GameGrid;
	int sizeX, sizeY;
	ArrayList<ArrayList<Cell>> brokenLines;

	public static void main(String[] args) throws FileNotFoundException {
		Logipix logipix = new Logipix();
		logipix.initialize("InputFiles/LogiX.txt");
		logipix.print();
		logipix.example();
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
	    	int[] aux = new int[sizeX];
	    	System.out.print("| ");
		   	for (int j=0;j<sizeX; j++) {
		   		if(GameGrid[i][j].linked){
		   			
		   			//one voisin
		   			if((GameGrid[i][j].pos1==Position.EMPTY || GameGrid[i][j].pos2==Position.EMPTY)) {
		   				if(GameGrid[i][j].clue==1){
		   					System.out.print(GameGrid[i][j].clue+ "X | ");
		   				}else{
		   					if(GameGrid[i][j].pos1==Position.LEFT || GameGrid[i][j].pos2==Position.LEFT)
		   						System.out.print(GameGrid[i][j].clue+ "  | ");
		   					if(GameGrid[i][j].pos1==Position.RIGHT || GameGrid[i][j].pos2==Position.RIGHT)
		   						System.out.print(GameGrid[i][j].clue+ "XXXX");
		   					if(GameGrid[i][j].pos1==Position.UP || GameGrid[i][j].pos2==Position.UP)
		   						System.out.print(GameGrid[i][j].clue+ "X | ");
		   					if(GameGrid[i][j].pos1==Position.DOWN || GameGrid[i][j].pos2==Position.DOWN){
		   						System.out.print(GameGrid[i][j].clue+ "X | "); aux[j]=1;
		   					}
		   				}
		   			}

		   			//two voisins
		   			if((GameGrid[i][j].pos1==Position.DOWN && GameGrid[i][j].pos2==Position.RIGHT) 
		   				|| (GameGrid[i][j].pos2==Position.DOWN && GameGrid[i][j].pos1==Position.RIGHT)){
		   				System.out.print("XXXXX");
		   				aux[j]=1;
		   			}
		   			if((GameGrid[i][j].pos1==Position.DOWN && GameGrid[i][j].pos2==Position.UP) 
		   				|| (GameGrid[i][j].pos2==Position.DOWN && GameGrid[i][j].pos1==Position.UP)){
		   				System.out.print(" X | ");
		   				aux[j]=1;
		   			}
		   			if((GameGrid[i][j].pos1==Position.DOWN && GameGrid[i][j].pos2==Position.LEFT) 
		   				|| (GameGrid[i][j].pos2==Position.DOWN && GameGrid[i][j].pos1==Position.LEFT)){
		   				System.out.print("XX | ");
		   				aux[j]=1;
		   			}
		   			if((GameGrid[i][j].pos1==Position.RIGHT && GameGrid[i][j].pos2==Position.LEFT) 
		   				|| (GameGrid[i][j].pos2==Position.DOWN && GameGrid[i][j].pos1==Position.LEFT)){
		   				System.out.print("XXXXX");
		   			}
		   			if((GameGrid[i][j].pos1==Position.UP && GameGrid[i][j].pos2==Position.RIGHT) 
		   				|| (GameGrid[i][j].pos2==Position.UP && GameGrid[i][j].pos1==Position.RIGHT)){
		   				System.out.print(" XXXX");
		   			}
		   			if((GameGrid[i][j].pos1==Position.UP && GameGrid[i][j].pos2==Position.LEFT) 
		   				|| (GameGrid[i][j].pos2==Position.UP && GameGrid[i][j].pos1==Position.LEFT)){
		   				System.out.print("XX | ");
		   			}
		   		}else{
		   			if(GameGrid[i][j].clue!=0) System.out.print(GameGrid[i][j].clue+ "  | ");	
		   			else System.out.print("   | ");
		   		}  		
		   	}
		   	System.out.print("\n");
		   	System.out.print("+");
		   	for (int j=0;j<sizeX; j++) {
		   		if(aux[j]==0) System.out.print("----+");	
		   		else System.out.print("  X +");	
		   	}
		   	System.out.print("\n");
		 }
    }

    private void addBrokenLine(ArrayList<Cell> line){
    	for (int i=0; i < (line.size()-1) ; i++) {
    	    line.get(i).linked = true;    		
    		if(line.get(i).y != line.get(i+1).y){
    			if(line.get(i).y > line.get(i+1).y){
    				line.get(i).pos1 = Position.LEFT;
    				line.get(i+1).pos2 = Position.RIGHT;
    			} 
    			else{
    				line.get(i).pos1 = Position.RIGHT;
    				line.get(i+1).pos2 = Position.LEFT;
    			} 
    		}else{
    			if(line.get(i).x > line.get(i+1).x){
    				line.get(i).pos1= Position.UP;
    				line.get(i+1).pos2= Position.DOWN;
    			} 
    			else{
    				line.get(i).pos1 = Position.DOWN;
    				line.get(i+1).pos2 = Position.UP;
    			} 
    		}
    	}
    	line.get(line.size()-1).linked = true;
    }

    public void example(){
    	ArrayList<Cell> a = new ArrayList<>();
    	a.add(GameGrid[0][0]);
    	a.add(GameGrid[0][1]);
    	addBrokenLine(a);
    	brokenLines.add(a);

    	ArrayList<Cell> b = new ArrayList<>();
    	b.add(GameGrid[8][0]);
    	b.add(GameGrid[8][1]);
    	b.add(GameGrid[8][2]);
    	b.add(GameGrid[7][2]);
    	addBrokenLine(b);
    	brokenLines.add(b);

    	ArrayList<Cell> c = new ArrayList<>();
    	c.add(GameGrid[4][5]);
    	c.add(GameGrid[5][5]);
    	c.add(GameGrid[5][6]);
    	c.add(GameGrid[4][6]);
    	c.add(GameGrid[3][6]);
    	c.add(GameGrid[3][7]);
    	addBrokenLine(c);
    	brokenLines.add(c);

    	ArrayList<Cell> d = new ArrayList<>();
    	d.add(GameGrid[0][10]);
    	addBrokenLine(d);
    	brokenLines.add(d);


    }
}
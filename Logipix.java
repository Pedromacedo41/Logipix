import java.util.*;
import java.io.File;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException; 
import java.io.IOException;

public class Logipix{

	File input;
	Cell[][] GameGrid;
	int sizeX, sizeY;
	PriorityQueue<Cell> OrderedCells;
	BrokenLine LastBrokenLine;

	public void initialize(String name){
		OrderedCells = new PriorityQueue<>(new mycomparator2());
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
	   				OrderedCells.add(GameGrid[i][j]);
	   			}
	   		}

   		}catch (IOException e) {
        	System.err.println("Error while opening file");
            e.getMessage();
        }
    }

    public void addBrokenLine(ArrayList<Cell> line){
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

    public void addBrokenLine2(BrokenLine brokenline){
    	Cell temp; Position temp2;
    	brokenline.init.linked = true;
    	brokenline.init.pos1 = brokenline.order.pop();
    	while((temp2=brokenline.order.pop())!=null) {
    	    if(temp2==Position.UP) {}
    	    if(temp2==Position.DOWN){}
    	    if(temp2==Position.LEFT){}
    	    if(temp2==Position.RIGHT){}
    	}
    	
    }

    public void removebrokenLine(ArrayList<Cell> line){
    	for (int i=0; i < (line.size()) ; i++) {
    	    line.get(i).linked = false; 
    	    line.get(i).pos1= Position.EMPTY;   		
    	    line.get(i).pos2= Position.EMPTY;   		
    		
    	}
    }

    public void example(){
    	ArrayList<Cell> a = new ArrayList<>();
    	a.add(GameGrid[0][0]);
    	a.add(GameGrid[0][1]);
    	addBrokenLine(a);

    	ArrayList<Cell> b = new ArrayList<>();
    	b.add(GameGrid[8][0]);
    	b.add(GameGrid[8][1]);
    	b.add(GameGrid[8][2]);
    	b.add(GameGrid[7][2]);
    	addBrokenLine(b);

    	ArrayList<Cell> c = new ArrayList<>();
    	c.add(GameGrid[4][5]);
    	c.add(GameGrid[5][5]);
    	c.add(GameGrid[5][6]);
    	c.add(GameGrid[4][6]);
    	c.add(GameGrid[3][6]);
    	c.add(GameGrid[3][7]);
    	addBrokenLine(c);

    	ArrayList<Cell> d = new ArrayList<>();
    	d.add(GameGrid[0][10]);
    	addBrokenLine(d);
    }

    private boolean compareBrokenLines(ArrayList<Cell> a1, ArrayList<Cell> a2){
    	for (int i=0; i < a1.size() ; i++ ) {
    		if(a1.get(i)!=a2.get(i)){
    			return false;
    		}
    	}
    	return true;
    }

    public ArrayList<ArrayList<Cell>> AllPaths(Cell cell){
    	return recursive(cell,cell.clue-1, cell.clue);
    }

    private ArrayList<ArrayList<Cell>> recursive(Cell cell, int n, int a){
    	ArrayList<Cell> temp = diponible_voisins(cell,n);
    	ArrayList<ArrayList<Cell>> allbrokenLines2, allbrokenLines= new ArrayList<>();
    	Cell d;
    	for(int i=0; i < temp.size(); i++){
    		d= temp.get(i); 
    		if(n==1){
    			if(d.clue==a){
    				System.out.println("Cell("+d.x+","+d.y+")");
    				ArrayList<Cell> neww = new ArrayList<>();
    				neww.add(d); 
    				neww.add(cell);
    				allbrokenLines.add(neww);
    				return allbrokenLines;
    			}

    		}else{
    			allbrokenLines2= recursive(d, n-1,a);
	    		for(int j=0; j< allbrokenLines2.size(); j++){
	    			allbrokenLines2.get(j).add(cell);
	    		}
	    		allbrokenLines.addAll(allbrokenLines2);
    		}
    		d.temp =false;
    	}
    	cell.temp=false;
    	return allbrokenLines;
    }

    public ArrayList<Cell> diponible_voisins(Cell cell, int n){
    	ArrayList<Cell> disponible = new ArrayList<>();
    	if((cell.y+1) < sizeX){
    		if(GameGrid[cell.x][cell.y+1].linked==false && GameGrid[cell.x][cell.y+1].temp==false){
    			if(n==1) disponible.add(GameGrid[cell.x][cell.y+1]);
    			else{ if(GameGrid[cell.x][cell.y+1].clue==0) disponible.add(GameGrid[cell.x][cell.y+1]); }
    		}
    	}
    	if((cell.x+1) < sizeY){
    		if(GameGrid[cell.x+1][cell.y].linked==false && GameGrid[cell.x+1][cell.y].temp==false){
    			if(n==1) disponible.add(GameGrid[cell.x+1][cell.y]);
    			else{ if(GameGrid[cell.x+1][cell.y].clue==0) disponible.add(GameGrid[cell.x+1][cell.y]); }
    		}
    	}
    	if((cell.x-1) >= 0){
    		if(GameGrid[cell.x-1][cell.y].linked==false && GameGrid[cell.x-1][cell.y].temp==false){
    			if(n==1) disponible.add(GameGrid[cell.x-1][cell.y]);
    			else{ if(GameGrid[cell.x-1][cell.y].clue==0) disponible.add(GameGrid[cell.x-1][cell.y]); }
    		}
    	}
    	if((cell.y-1) >= 0){
    		if(GameGrid[cell.x][cell.y-1].linked==false && GameGrid[cell.x][cell.y-1].temp==false){
    			if(n==1) disponible.add(GameGrid[cell.x][cell.y-1]);
    			else{ if(GameGrid[cell.x][cell.y-1].clue==0) disponible.add(GameGrid[cell.x][cell.y-1]); }
    		}
    	}

    	return disponible;
    }
 }

 /*
    private void Backtracking(){
    	Cell former; 
    	while((former= OrderedCells.poll())!=null){
    		if(former.linked==false){
    			boolean newPathfounded = false;
	    		//BrokenLine newBrokenLine = NewPath(former);
	    		if(newBrokenLine==null){
	    			removebrokenLine2(LastBrokenLine);
	    			OrderedCells.add(former);
	    		}else{
	    			//addBrokenLine2(newBrokenLine);
	    			//LastBrokenLine= newBrokenLine;
	    		}
    		}
    	}
    }


*/

class mycomparator2 implements Comparator<Cell>
{
   @Override
   public int compare(Cell a, Cell b)
   {
      return b.clue - a.clue;
   }
}
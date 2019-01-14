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
	ArrayList<Cell> LastBrokenLine;
	int Counter=0;
	Cell Last;

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
	   				if(GameGrid[i][j].clue!=0)	Counter++;
	   				OrderedCells.add(GameGrid[i][j]);
	   			}
	   		}

   		}catch (IOException e) {
        	System.err.println("Error while opening file");
            e.getMessage();
        }
    }

    public void addBrokenLine(BrokenLine brokenline){
    	Cell temp =  brokenline.init;
    	Position temp2;
    	temp.linked = true;
    	temp.pos1 = brokenline.order.get(0);
    	for(int i=0; i< brokenline.order.size(); i++) {
    		temp2=brokenline.order.get(i);
    	    if(temp2==Position.UP) {
    	    	temp = GameGrid[temp.x-1][temp.y];
    	    	temp.linked=true;
    	    	temp.pos2=Position.DOWN;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
    	    }
    	    if(temp2==Position.DOWN){temp = GameGrid[temp.x+1][temp.y];
    	    	temp.linked=true;
    	    	temp.pos2=Position.UP;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
    	    }
    	    if(temp2==Position.LEFT){
    	    	temp = GameGrid[temp.x][temp.y-1];
    	    	temp.linked=true;
    	    	temp.pos2=Position.RIGHT;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
    	    }
    	    if(temp2==Position.RIGHT){
    	    	temp = GameGrid[temp.x][temp.y+1];
    	    	temp.linked=true;
    	    	temp.pos2=Position.LEFT;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
    	    }
    	}
    	
    }

    private void addBrokenLine2(ArrayList<Cell> line){
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

    public void removebrokenLine(BrokenLine brokenline){
    	Cell temp =  brokenline.init;
    	Position temp2;
    	temp.linked = false;
    	temp.pos1 = Position.EMPTY;
    	for(int i=0; i< brokenline.order.size(); i++) {
    		temp2=brokenline.order.get(i);
    	    if(temp2==Position.UP) {
    	    	temp = GameGrid[temp.x-1][temp.y];
    	    	temp.linked=false;
    	    	temp.pos2=Position.EMPTY;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	    if(temp2==Position.DOWN){temp = GameGrid[temp.x+1][temp.y];
    	    	temp.linked=false;
    	    	temp.pos2=Position.UP;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	    if(temp2==Position.LEFT){
    	    	temp = GameGrid[temp.x][temp.y-1];
    	    	temp.linked=false;
    	    	temp.pos2=Position.EMPTY;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	    if(temp2==Position.RIGHT){
    	    	temp = GameGrid[temp.x][temp.y+1];
    	    	temp.linked=false;
    	    	temp.pos2=Position.EMPTY;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	}
    	
    }

    public void removebrokenLine2(ArrayList<Cell> line){
    	for (int i=0; i < (line.size()-1) ; i++) {
    	    line.get(i).linked = false;    		
    		line.get(i).pos1 = Position.EMPTY;
    		line.get(i+1).pos2 = Position.EMPTY;
    	}
    	line.get(line.size()-1).linked = false;
	}

    public void example(int i){

    	ArrayList<Position> order1 = new ArrayList<>();
    	order1.add(Position.RIGHT);
    	order1.add(Position.RIGHT);
    	order1.add(Position.UP);
    	BrokenLine example = new BrokenLine(GameGrid[8][0],order1);
    	//addBrokenLine(example);
 
    	ArrayList<ArrayList<Cell>> t = AllPaths(GameGrid[0][1]);
    	System.out.println(t.size());

    	if(i<t.size()){
    		addBrokenLine2((LastBrokenLine=t.get(i)));
    	} 

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
    	ArrayList<ArrayList<Cell>> allbrokenLines = new ArrayList<>();
    	ArrayList<Cell> current = new ArrayList<>();
    	current.add(cell);
       	recursive(cell,cell.clue-1, cell.clue, current, allbrokenLines);
    	return allbrokenLines;
    }

    private void recursive(Cell cell, int n, int a, ArrayList<Cell> current, ArrayList<ArrayList<Cell>> allbrokenLines){
    	cell.temp=true;
    	ArrayList<Cell> temp = diponible_voisins(cell,n);
    	Cell d;
    	current.add(cell);
    	for(int i=0; i < temp.size(); i++){
    		d= temp.get(i); d.temp = true;
    		if(n==1){
    			if(d.clue==a){
    				current.add(d);
    				allbrokenLines.add(current);
    				current.remove(0);
    			}
    		}else{
    			ArrayList<Cell> current2 = new ArrayList<>();
    			current2.addAll(current); 
    			recursive(d, n-1,a, current2, allbrokenLines);
    		}
    		d.temp =false;
    	}
    	cell.temp=false;
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


    public void Backtracking(){
    	Cell former; 
    	while(this.Counter!=0){
    		former= OrderedCells.peek();
    		if(former.linked==false){
    			if(former.Counter!=0){
    				if(former.allbrokenlines.size()<former.Counter){
    					removebrokenLine2(LastBrokenLine);
    					OrderedCells.add(Last);
    				}else{
    					addBrokenLine2(LastBrokenLine = former.allbrokenlines.get(former.Counter));
    					former.Counter++; Counter-=2; Last = former; OrderedCells.poll();
    					break;
    				}
    			}else{
    				former.allbrokenlines = AllPaths(former);
    				if(former.allbrokenlines.size()==0){
    					removebrokenLine2(LastBrokenLine);
    					OrderedCells.add(Last);
    				}else{
    					addBrokenLine2(LastBrokenLine = former.allbrokenlines.get(former.Counter));
    					former.Counter++; Counter-=2; Last = former; OrderedCells.poll();
    					break;
    				}
    			}
    		}
    	}
    }

 }



class mycomparator2 implements Comparator<Cell>
{
   @Override
   public int compare(Cell a, Cell b)
   {
      return b.clue - a.clue;
   }
}
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
	LinkedList<Cell> orderedCells;
	ArrayList<Cell> OrderedCells;
    Stack<ArrayList<Cell>> LastBrokenLine;
	Stack<BrokenLine> LastBrokenLine2;

	int Counter=0, MemCounter;

	public void initialize(String name){
		OrderedCells = new ArrayList<>();
        LastBrokenLine = new Stack<>();
        LastBrokenLine2 = new Stack<>();
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
	   				if(GameGrid[i][j].clue!=0){
	   					Counter++;
	   					OrderedCells.add(GameGrid[i][j]);
	   				}
	   			}
	   		}
	   		MemCounter= Counter;
	   		Collections.sort(OrderedCells, new mycomparator2());
	   		orderedCells = new LinkedList<>();
	   		orderedCells.addAll(OrderedCells);

   		}catch (IOException e) {
        	System.err.println("Error while opening file");
            e.getMessage();
        }
    }

    public void addBrokenLine(BrokenLine brokenline){ //Dado uma lista de comandos, marcam as celulas
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

    private void addBrokenLine2(ArrayList<Cell> line){ //Create the links between the paths
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

    public void removebrokenLine(BrokenLine brokenline){ //See it later
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

    public void removebrokenLine2(ArrayList<Cell> line){ //Removing a broken line 
    	for (int i=0; i < (line.size()-1) ; i++) {
    	    line.get(i).linked = false;    		
    		line.get(i).pos1 = Position.EMPTY;
    		line.get(i+1).pos2 = Position.EMPTY;
    	}
    	line.get(line.size()-1).linked = false;
	}

    public void example(int i){

    	/*
    	ArrayList<Position> order1 = new ArrayList<>();
    	order1.add(Position.RIGHT);
    	order1.add(Position.RIGHT);
    	order1.add(Position.UP);
    	BrokenLine example = new BrokenLine(GameGrid[8][0],order1);
    	//addBrokenLine(example);*/

    	ArrayList<Cell> b = new ArrayList<>();
    	b.add(GameGrid[8][0]);
    	b.add(GameGrid[8][1]);
    	b.add(GameGrid[8][2]);
    	b.add(GameGrid[7][2]);
    	addBrokenLine2(b);
		removebrokenLine2(b);
 
    	ArrayList<ArrayList<Cell>> t = AllPaths(GameGrid[4][5], false);
    	System.out.println(t.size());
        cases_obligatories(GameGrid[4][5]);

    	if(i<t.size()){
    		addBrokenLine2((LastBrokenLine.push(t.get(i))));
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

    private void cases_obligatories(Cell cell){
          ArrayList<ArrayList<Cell>> t=  AllPaths(cell,true);
          int size = t.size(), lenght = t.get(0).size();
          HashMap<Pair,Integer> casas = new HashMap<>();
          for(int i=0; i < size; i++){
                for(int j=0; j< lenght; j++){
                      Pair<Integer, Integer> p1 = new Pair<Integer,Integer>(t.get(i).get(j).x,t.get(i).get(j).y);
                      if(casas.containsKey(p1))  casas.put(p1, casas.get(p1)+1);
                      else casas.put(p1,1);
                }
          }

        Set set = casas.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry)iterator.next();
            if((int) mentry.getValue()==size){
                Pair p2 =(Pair)mentry.getKey();
                if(GameGrid[(int) p2.getFirst()][(int) p2.getSecond()].clue==0) 
                    GameGrid[(int) p2.getFirst()][(int) p2.getSecond()].toujours_ocuppe = true;
            }
        }
    }


    public ArrayList<ArrayList<Cell>> AllPaths(Cell cell,boolean keep_linked){
    	ArrayList<ArrayList<Cell>> allbrokenLines = new ArrayList<>();
    	ArrayList<Cell> current = new ArrayList<>();
       	recursive(cell,cell.clue-1, cell.clue, current, allbrokenLines,keep_linked);
    	return allbrokenLines;
    }

    private void recursive(Cell cell, int n, int destiny, ArrayList<Cell> current, ArrayList<ArrayList<Cell>> allbrokenLines,boolean keep_linked){
    	cell.temp=true;
    	ArrayList<Cell> temp = disponible_voisins(cell,n,keep_linked);
    	ArrayList<Cell> current2 = new ArrayList<>();
    	if(n==0){
    		if(cell.clue==destiny){
    			current2.addAll(current); 
    			current2.add(cell);
    			allbrokenLines.add(current2);
    		}
    	}else{
	    	current2.addAll(current); 
	    	current2.add(cell);
    		for(int i=0; i < temp.size(); i++){
	    		recursive(temp.get(i), n-1,destiny, current2, allbrokenLines,keep_linked);
    		}
    	}
    	cell.temp=false;
    }

    public ArrayList<BrokenLine> AllPaths2(Cell cell){
        ArrayList<ArrayList<Position>> allbrokenLines = recursive2(cell,cell.clue-1, cell.clue, Position.EMPTY);
        ArrayList<BrokenLine> AllBrokenLines = new ArrayList<>();
        for(int i=0; i< allbrokenLines.size(); i++) {
            allbrokenLines.get(i).remove(0);
            AllBrokenLines.add(new BrokenLine(cell, allbrokenLines.get(i)));
        }
        return AllBrokenLines;
    }

    private ArrayList<ArrayList<Position>> recursive2(Cell cell, int n, int destiny, Position Lastrelative){
        cell.temp=true;
        cell.especial=true;
        ArrayList<ArrayList<Position>> lines = new ArrayList<>();
        if(n==0){
            cell.temp=false;
            if(cell.clue==destiny){
                ArrayList<Position> last = new ArrayList<>();
                last.add(Lastrelative);
                lines.add((last));
                return lines;
            }
        }else{
            ArrayList<Cell> temp = disponible_voisins(cell,n);

            for(int i=0; i < temp.size(); i++){
                Cell ff = temp.get(i);
                Position relative = Position.EMPTY;
                if(ff.x> cell.x) relative= Position.DOWN;
                if(ff.x< cell.x) relative= Position.UP;
                if(ff.y< cell.y) relative= Position.LEFT;
                if(ff.y> cell.y) relative= Position.RIGHT;

                int k;
                ArrayList<ArrayList<Position>> intermediaire;
                if(ff.savedPaths.containsKey(k=Cell.hashPair(n-1,destiny))){
                    intermediaire = ff.savedPaths.get(k);
                }else{
                    intermediaire= recursive2(ff,n-1,destiny, relative);
                    //ff.savedPaths.put(k,intermediaire);
                }
                if(intermediaire.size()>0) lines.addAll(intermediaire);
            }
            if(lines.size()>0){
                for(int j=0; j < lines.size(); j++){
                    lines.get(j).add(0,Lastrelative);
                }
            }
        }
        cell.temp=false;
        return lines;
    }



    public ArrayList<Cell> disponible_voisins(Cell cell, int n, boolean keep_linked){
    	ArrayList<Cell> disponible = new ArrayList<>();
    	if((cell.y+1) < sizeX){
    		if((GameGrid[cell.x][cell.y+1].linked==false || keep_linked)  && GameGrid[cell.x][cell.y+1].temp==false
                && GameGrid[cell.x][cell.y+1].toujours_ocuppe==false){
        			if(n==1) disponible.add(GameGrid[cell.x][cell.y+1]);
        			else{ if(GameGrid[cell.x][cell.y+1].clue==0) disponible.add(GameGrid[cell.x][cell.y+1]); }
    		}
    	}
    	if((cell.x+1) < sizeY){
    		if((GameGrid[cell.x+1][cell.y].linked==false || keep_linked) && GameGrid[cell.x+1][cell.y].temp==false
                && GameGrid[cell.x+1][cell.y].toujours_ocuppe==false){
        			if(n==1) disponible.add(GameGrid[cell.x+1][cell.y]);
        			else{ if(GameGrid[cell.x+1][cell.y].clue==0) disponible.add(GameGrid[cell.x+1][cell.y]); }
    		}
    	}
    	if((cell.x-1) >= 0){
    		if((GameGrid[cell.x-1][cell.y].linked==false || keep_linked) && GameGrid[cell.x-1][cell.y].temp==false
                && GameGrid[cell.x-1][cell.y].toujours_ocuppe==false){
        			if(n==1) disponible.add(GameGrid[cell.x-1][cell.y]);
        			else{ if(GameGrid[cell.x-1][cell.y].clue==0) disponible.add(GameGrid[cell.x-1][cell.y]); }
    		}
    	}
    	if((cell.y-1) >= 0){
    		if((GameGrid[cell.x][cell.y-1].linked==false || keep_linked) && GameGrid[cell.x][cell.y-1].temp==false
                && GameGrid[cell.x][cell.y-1].toujours_ocuppe==false){
        			if(n==1) disponible.add(GameGrid[cell.x][cell.y-1]);
        			else{ if(GameGrid[cell.x][cell.y-1].clue==0) disponible.add(GameGrid[cell.x][cell.y-1]); }
    		}
    	}

    	return disponible;
    }


    public void Backtracking(){
    	Cell former;
        LastBrokenLine = new Stack<ArrayList<Cell>>();
    	while(orderedCells.size()!=0){
    		former= orderedCells.peek();
               // System.out.println("Former clue " + former.clue +" Former counter "+ former.Counter);
    			if(former.clue==1){
    				former.linked=true;
    				orderedCells.poll(); 
    			}else{
    				if(former.Counter!=0){
	    				if((former.allbrokenlines.size()-1)<former.Counter){
	    					removebrokenLine2(LastBrokenLine.peek());

	    					former.Counter=0;
                            //System.out.println("PASSEI AQUI CARAIO ");
                            LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1).Counter = 0;

                            //System.out.println(LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1).clue + " oi");
                            orderedCells.addFirst(LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1));
	    				    orderedCells.addFirst(LastBrokenLine.peek().get(0));
	    					
	    					//System.out.println("aqui2:"+former.x+","+former.y);
                            LastBrokenLine.pop();
	    				}else{
	    					addBrokenLine2(LastBrokenLine.push(former.allbrokenlines.get(former.Counter)));
	    					former.Counter++; 
	    					orderedCells.poll(); 
	    					orderedCells.remove(LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1));
	    					//System.out.println("aqui0:"+former.allbrokenlines.size());
	    				}
	    			}else{
	    				former.allbrokenlines = AllPaths(former,false);
                        //System.out.println("Former clue = " + former.clue + " tamanho = " + former.allbrokenlines.size());
	    				if(former.allbrokenlines.size()==0){
	    					removebrokenLine2(LastBrokenLine.peek());
                            orderedCells.addFirst(LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1));
	    					orderedCells.addFirst(LastBrokenLine.peek().get(0));
	    					
                            //System.out.println("passei por aqui");
                            LastBrokenLine.pop();
	    				}else{
	    					addBrokenLine2(LastBrokenLine.push(former.allbrokenlines.get(former.Counter)));
	    					former.Counter++; 
	    					orderedCells.poll(); 
	    					orderedCells.remove(LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1));
	    					//System.out.println("ll"+former.allbrokenlines.size()+"("+former.x+","+former.y+")");
	    				}
	    			}
    			}
    	}
    }

 }
//consertar os pares 


class mycomparator2 implements Comparator<Cell>
{
   @Override
   public int compare(Cell a, Cell b)
   {
      return a.clue - b.clue;
   }
}
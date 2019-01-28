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
	Stack<BrokenLine> Last_brokenLine;
    ArrayList<Cell> noVoisins;
    HashSet<Pair>  interrupting_Cells;

	int Counter=0, MemCounter;

	public void initialize(String name){
		OrderedCells = new ArrayList<>();
        Last_brokenLine = new Stack<>();
        interrupting_Cells = new HashSet<>();
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
        temp.currentBrokenLine.setFirst(brokenline.init.x);
        temp.currentBrokenLine.setSecond(brokenline.init.y);

    	temp.pos1 = brokenline.order.get(0);
    	for(int i=0; i< brokenline.order.size(); i++) {
    		temp2=brokenline.order.get(i);
    	    if(temp2==Position.UP) {
    	    	temp = GameGrid[temp.x-1][temp.y];
    	    	temp.linked=true;
    	    	temp.pos2=Position.DOWN;
                temp.currentBrokenLine.setFirst(brokenline.init.x);
                temp.currentBrokenLine.setSecond(brokenline.init.y);

    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
                if(brokenline.order.size()-1==i) brokenline.last=temp;
    	    }
    	    if(temp2==Position.DOWN){temp = GameGrid[temp.x+1][temp.y];
    	    	temp.linked=true;
    	    	temp.pos2=Position.UP;
                temp.currentBrokenLine.setFirst(brokenline.init.x);
                temp.currentBrokenLine.setSecond(brokenline.init.y);

    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
                if(brokenline.order.size()-1==i) brokenline.last=temp;
    	    }
    	    if(temp2==Position.LEFT){
    	    	temp = GameGrid[temp.x][temp.y-1];
    	    	temp.linked=true;
    	    	temp.pos2=Position.RIGHT;
                temp.currentBrokenLine.setFirst(brokenline.init.x);
                temp.currentBrokenLine.setSecond(brokenline.init.y); 

    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
                if(brokenline.order.size()-1==i) brokenline.last=temp;
    	    }
    	    if(temp2==Position.RIGHT){
    	    	temp = GameGrid[temp.x][temp.y+1];
    	    	temp.linked=true;
    	    	temp.pos2=Position.LEFT;
                temp.currentBrokenLine.setFirst(brokenline.init.x);
                temp.currentBrokenLine.setSecond(brokenline.init.y); 

    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = brokenline.order.get(i+1);
                if(brokenline.order.size()-1==i) brokenline.last=temp;
    	    }
    	}
    	
    }
    public ArrayList<Cell> BrokenLinesCells(BrokenLine brokenline){ //Dado uma lista de comandos, marcam as celulas
        Cell temp =  brokenline.init;
        ArrayList<Cell> list = new ArrayList<>();
        list.add(temp);
        for(int i=0; i< brokenline.order.size(); i++) {
            Position temp2=brokenline.order.get(i);
            if(temp2==Position.UP) {
                temp = GameGrid[temp.x-1][temp.y];
                list.add(temp);
            }
            if(temp2==Position.DOWN){temp = GameGrid[temp.x+1][temp.y];
                list.add(temp);
            }
            if(temp2==Position.LEFT){
                temp = GameGrid[temp.x][temp.y-1];
                list.add(temp);
            }
            if(temp2==Position.RIGHT){
                temp = GameGrid[temp.x][temp.y+1];
                list.add(temp);
            }
        }
        return list;
        
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

    public void findDeadEnds(LinkedList<Cell> cells){
        int lastClue = 2;
        for (int i = MemCounter-1; lastClue != 1 && i > 0; i--){
            Cell x = cells.get(i);
            lastClue = x.clue;
            ArrayList<BrokenLine> t  = AllPaths(x,false);
            if (t.size() == 1){
                i++;
                cells.remove(x);
                
                cells.addFirst(x);
            
            }
        }
    }

    public void example(int i){
        ArrayList<BrokenLine> t = AllPaths(GameGrid[11][0],false);

    	if(i<t.size()){
    		addBrokenLine((Last_brokenLine.push(t.get(i))));
            ArrayList<BrokenLine> t2 = AllPaths(GameGrid[9][2],false);
            System.out.println(t2.size());  
    	} 
    }

    public void cases_obligatories(Cell cell){
          ArrayList<BrokenLine> t=  AllPaths(cell, true);
          int size = t.size(), lenght = t.get(0).order.size()+1;
          HashMap<Pair,Integer> casas = new HashMap<>();
          for(int i=0; i < size; i++){
                ArrayList<Cell> t2 = BrokenLinesCells(t.get(i));
                for(int j=0; j< lenght; j++){
                      Pair<Integer, Integer> p1 = new Pair<Integer,Integer>(t2.get(j).x,t2.get(j).y);
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
                GameGrid[(int) p2.getFirst()][(int) p2.getSecond()].toujours_ocuppe = true;
            }
        }
    }

    private ArrayList<Cell> fill_noVoisins(Cell cell){
        ArrayList<Cell> disponible = new ArrayList<>();
        if((cell.y+1) < sizeX){
            if(GameGrid[cell.x][cell.y+1].linked==true && GameGrid[cell.x][cell.y+1].clue==0) disponible.add(GameGrid[cell.x][cell.y+1]); 
        }
        if((cell.x+1) < sizeY){
            if(GameGrid[cell.x+1][cell.y].linked==true && GameGrid[cell.x+1][cell.y].clue==0) disponible.add(GameGrid[cell.x+1][cell.y]); 
        }
        if((cell.x-1) >= 0){
            if(GameGrid[cell.x-1][cell.y].linked==true && GameGrid[cell.x-1][cell.y].clue==0) disponible.add(GameGrid[cell.x-1][cell.y]); 
        }
        if((cell.y-1) >= 0){
            if(GameGrid[cell.x][cell.y-1].linked==true && GameGrid[cell.x][cell.y-1].clue==0) disponible.add(GameGrid[cell.x-1][cell.y]); 
        }
        return disponible;
    }

    public ArrayList<Cell> cells_to_removeLine(){
        ArrayList<Cell> arr = new ArrayList<>();
        for(int i=0; i < noVoisins.size(); i++){
            arr.add(GameGrid[noVoisins.get(i).currentBrokenLine.getFirst()][noVoisins.get(i).currentBrokenLine.getSecond()]);
        }
        return arr;
    }

    private void addInterruptionCells(Cell cell){
        ArrayList<Cell> interrupting_voisins = fill_noVoisins(cell);
        for(int i=0; i< interrupting_Cells.size(); i++){
            Cell temp= interrupting_voisins.get(i);
            Pair<Integer, Integer> p1 = new Pair<Integer,Integer>(temp.x,temp.y);
            interrupting_Cells.add(p1);
        }
    }

    public ArrayList<BrokenLine> AllPaths(Cell cell, boolean keep_linked){
        ArrayList<ArrayList<Position>> allbrokenLines = recursive(cell,cell.clue-1, cell.clue, Position.EMPTY,keep_linked);
        ArrayList<BrokenLine> AllBrokenLines = new ArrayList<>();
        for(int i=0; i< allbrokenLines.size(); i++) {
            allbrokenLines.get(i).remove(0);
            AllBrokenLines.add(new BrokenLine(cell, allbrokenLines.get(i)));
        }
        return AllBrokenLines;
    }

    private ArrayList<ArrayList<Position>> recursive(Cell cell, int n, int destiny, Position Lastrelative, boolean keep_linked){
        cell.temp=true;
        ArrayList<ArrayList<Position>> lines = new ArrayList<>();
        addInterruptionCells(cell);
        if(n==0){
            cell.temp=false;
            if(cell.clue==destiny){
                ArrayList<Position> last = new ArrayList<>();
                last.add(Lastrelative);
                lines.add((last));
                return lines;
            }
        }else{
            ArrayList<Cell> temp = disponible_voisins(cell,n,keep_linked);
            for(int i=0; i < temp.size(); i++){
                Cell ff = temp.get(i);
                Position relative = Position.EMPTY;
                if(ff.x> cell.x) relative= Position.DOWN;
                if(ff.x< cell.x) relative= Position.UP;
                if(ff.y< cell.y) relative= Position.LEFT;
                if(ff.y> cell.y) relative= Position.RIGHT;
                ArrayList<ArrayList<Position>> intermediaire = recursive(ff,n-1,destiny, relative, keep_linked);
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
        Last_brokenLine = new Stack<>();
        findDeadEnds(orderedCells);
    	while(orderedCells.size()!=0){
    		former= orderedCells.peek();
               // System.out.println("Former clue " + former.clue +" Former counter "+ former.Counter);
    			if(former.clue==1){
    				former.linked=true;
    				orderedCells.poll(); 
    			}else{
    				if(former.Counter!=0){
	    				if((former.allbrokenlines.size()-1)<former.Counter){
	    					removebrokenLine(Last_brokenLine.peek());

	    					former.Counter=0;
                            //System.out.println("PASSEI AQUI CARAIO ");
                            Last_brokenLine.peek().last.Counter = 0;

                            //System.out.println(LastBrokenLine.peek().get(LastBrokenLine.peek().size()-1).clue + " oi");
                            orderedCells.addFirst(Last_brokenLine.peek().last);
	    				    orderedCells.addFirst(Last_brokenLine.peek().init);
	    					
	    					//System.out.println("aqui2:"+former.x+","+former.y);
                            Last_brokenLine.pop();
	    				}else{
	    					addBrokenLine(Last_brokenLine.push(former.allbrokenlines.get(former.Counter)));
	    					former.Counter++; 
	    					orderedCells.poll(); 
	    					orderedCells.remove(Last_brokenLine.peek().last);
	    					//System.out.println("aqui0:"+former.allbrokenlines.size());
	    				}
	    			}else{
	    				former.allbrokenlines = AllPaths(former,false);
                        //System.out.println("Former clue = " + former.clue + " tamanho = " + former.allbrokenlines.size());
	    				if(former.allbrokenlines.size()==0){
	    					removebrokenLine(Last_brokenLine.peek());
                            orderedCells.addFirst(Last_brokenLine.peek().last);
	    					orderedCells.addFirst(Last_brokenLine.peek().init);
	    					
                            //System.out.println("passei por aqui");
                            Last_brokenLine.pop();
	    				}else{
	    					addBrokenLine(Last_brokenLine.push(former.allbrokenlines.get(former.Counter)));
	    					former.Counter++; 
	    					orderedCells.poll(); 
	    					orderedCells.remove(Last_brokenLine.peek().last);
	    					//System.out.println("ll"+former.allbrokenlines.size()+"("+former.x+","+former.y+")");
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
      return a.clue - b.clue;
   }
}
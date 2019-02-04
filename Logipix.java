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
    public ArrayList<Cell> BrokenLinesCells(BrokenLine brokenline){ 
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
        temp.currentBrokenLine = new Pair(0,0);
    	temp.pos1 = Position.EMPTY;
    	for(int i=0; i< brokenline.order.size(); i++) {
    		temp2=brokenline.order.get(i);
    	    if(temp2==Position.UP) {
    	    	temp = GameGrid[temp.x-1][temp.y];
    	    	temp.linked=false;
                temp.currentBrokenLine = new Pair (0,0);
    	    	temp.pos2=Position.EMPTY;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	    if(temp2==Position.DOWN){temp = GameGrid[temp.x+1][temp.y];
    	    	temp.linked=false;
                temp.currentBrokenLine = new Pair (0,0);
    	    	temp.pos2=Position.UP;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	    if(temp2==Position.LEFT){
    	    	temp = GameGrid[temp.x][temp.y-1];
    	    	temp.linked=false;
                temp.currentBrokenLine = new Pair (0,0);
    	    	temp.pos2=Position.EMPTY;
    	    	if(i<(brokenline.order.size()-1)) temp.pos1 = Position.EMPTY;;
    	    }
    	    if(temp2==Position.RIGHT){
    	    	temp = GameGrid[temp.x][temp.y+1];
    	    	temp.linked=false;
                temp.currentBrokenLine = new Pair (0,0);
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

    public void example(){
        ArrayList<BrokenLine> t = AllPaths(GameGrid[11][0],false);
    	addBrokenLine((Last_brokenLine.push(t.get(0))));
        interrupting_Cells = new HashSet<>();

        ArrayList<BrokenLine> t5 = AllPaths(GameGrid[10][5],false);
        addBrokenLine((Last_brokenLine.push(t5.get(0))));
        interrupting_Cells = new HashSet<>();

        ArrayList<BrokenLine> t6 = AllPaths(GameGrid[11][6],false);
        addBrokenLine((Last_brokenLine.push(t6.get(22))));
        interrupting_Cells = new HashSet<>();

        ArrayList<BrokenLine> t2 = AllPaths(GameGrid[9][2],false);
        //tentativa que vai falhar
        System.out.println(t2.size());
        if(t2.size()==0){
            ArrayList<Cell> cellstoremove= cells_to_removeLine();
            System.out.println(cellstoremove.size());
            for(int i =0; i < cellstoremove.size(); i++){
                System.out.println("Cell("+cellstoremove.get(i).x+","+cellstoremove.get(i).y+")");
            }
        }
    

    }

    public ArrayList<Cell> cases_obligatories(Cell cell){
          ArrayList<Cell> arr = new ArrayList<>();
          HashSet<Pair> origins_brokenline = new HashSet<>();

          ArrayList<BrokenLine> t=  AllPaths(cell, true);
          int size = t.size(), length = cell.clue; //length = cell.clue ?
          HashMap<Pair,Integer> casas = new HashMap<>();
          for(int i=0; i < size; i++){
                ArrayList<Cell> t2 = BrokenLinesCells(t.get(i));
                for(int j=0; j< length; j++){
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
                Cell temp = GameGrid[(int) p2.getFirst()][(int) p2.getSecond()];
                temp.toujours_ocuppe = true;
                cell.mandatory_cases.add(temp);
                
                if(temp.linked){
                    Cell origin = GameGrid[temp.currentBrokenLine.getFirst()][temp.currentBrokenLine.getSecond()];
                    origins_brokenline.add(new Pair <Integer,Integer>(origin.x,origin.y));

                }
            }
        }

        Iterator<Pair> j = origins_brokenline.iterator();
        while (j.hasNext()) {
            Pair s = j.next();
            Cell temp = GameGrid[(int) s.getFirst()][(int) s.getSecond()];
            arr.add(temp);
        }
        Collections.sort(arr, new mycomparator2());
        //interrupting_Cells = new HashSet<>();
        return arr;
    }

    private ArrayList<Cell> fill_noVoisins(Cell cell,int clue, int n){
        ArrayList<Cell> disponible = new ArrayList<>();
        if((cell.y+1) < sizeX){
            if(GameGrid[cell.x][cell.y+1].linked==true && (GameGrid[cell.x][cell.y+1].clue==0 || (GameGrid[cell.x][cell.y+1].clue== clue && n ==1))) disponible.add(GameGrid[cell.x][cell.y+1]);
        }
        if((cell.x+1) < sizeY){
            if(GameGrid[cell.x+1][cell.y].linked==true && (GameGrid[cell.x+1][cell.y].clue==0|| (GameGrid[cell.x+1][cell.y].clue== clue && n == 1))) disponible.add(GameGrid[cell.x+1][cell.y]); 
        }
        if((cell.x-1) >= 0){
            if(GameGrid[cell.x-1][cell.y].linked==true && (GameGrid[cell.x-1][cell.y].clue==0|| (GameGrid[cell.x-1][cell.y].clue== clue && n == 1))) disponible.add(GameGrid[cell.x-1][cell.y]); 
        }
        if((cell.y-1) >= 0){
            if(GameGrid[cell.x][cell.y-1].linked==true && (GameGrid[cell.x][cell.y-1].clue==0|| (GameGrid[cell.x][cell.y-1].clue== clue && n == 1))) disponible.add(GameGrid[cell.x][cell.y-1]); 
        }
        return disponible;
    }

    public ArrayList<Cell> cells_to_removeLine(){
        ArrayList<Cell> arr = new ArrayList<>();
        HashSet<Pair> origins_brokenline = new HashSet<>();
        Iterator<Pair> i = interrupting_Cells.iterator();
        while (i.hasNext()) {
            Pair s = i.next();
            Cell temp = GameGrid[(int) s.getFirst()][(int) s.getSecond()];
            Cell origin = GameGrid[temp.currentBrokenLine.getFirst()][temp.currentBrokenLine.getSecond()];
            origins_brokenline.add(new Pair <Integer,Integer>(origin.x,origin.y));
        }

        Iterator<Pair> j = origins_brokenline.iterator();
        while (j.hasNext()) {
            Pair s = j.next();
            Cell temp = GameGrid[(int) s.getFirst()][(int) s.getSecond()];
            arr.add(temp);
        }
        Collections.sort(arr, new mycomparator2());
        interrupting_Cells = new HashSet<>();
        return arr;
    }

    private void addInterruptionCells(Cell cell, int clue, int n){
        ArrayList<Cell> interrupting_voisins = fill_noVoisins(cell,clue,n);
        for(int i=0; i< interrupting_voisins.size(); i++){
            Cell temp= interrupting_voisins.get(i);
            interrupting_Cells.add( new Pair<Integer,Integer>(temp.x,temp.y));
        }
    }

    public ArrayList<BrokenLine> AllPaths(Cell cell, boolean keep_linked){
        ArrayList<LinkedList<Position>> allbrokenLines = recursive(cell,cell.clue, cell.clue-1, cell.clue, Position.EMPTY,keep_linked);
        ArrayList<BrokenLine> AllBrokenLines = new ArrayList<>();
        for(int i=0; i< allbrokenLines.size(); i++) {
            allbrokenLines.get(i).removeFirst();

            AllBrokenLines.add(new BrokenLine(cell, allbrokenLines.get(i)));
        }
        return AllBrokenLines;
    }

    private ArrayList<LinkedList<Position>> recursive(Cell cell, int clue, int n, int destiny, Position Lastrelative, boolean keep_linked){
        cell.temp=true;
        ArrayList<LinkedList<Position>> lines = new ArrayList<>();
        addInterruptionCells(cell,clue, n);
        if(n==0){
            cell.temp=false;
            if(cell.clue==destiny){
                LinkedList<Position> last = new LinkedList<>();
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
                ArrayList<LinkedList<Position>> intermediaire = recursive(ff,clue, n-1,destiny, relative, keep_linked);
               //System.out.println(intermediaire.size());
                if(intermediaire.size()>0) lines.addAll(intermediaire);
            }
            if(lines.size()>0){
                for(int j=0; j < lines.size(); j++){
                    lines.get(j).addFirst(Lastrelative);
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

    public void Backtracking2(){
        Cell former;
        Last_brokenLine = new Stack<>();
       // findDeadEnds(orderedCells);
        while(orderedCells.size()!=0){
            interrupting_Cells = new HashSet<>();
                

            System.out.println("Tamanho da pilha: "+ Last_brokenLine.size());
            former= orderedCells.peek();

            if (former.linked) orderedCells.poll();
            else{
            
               // System.out.println("Former clue " + former.clue +" Former counter "+ former.Counter);
            System.out.println("Estou aqui!!! Com celula " + former.x + "," + former.y + " clue " + former.clue +" e counter " + former.Counter);
                if(former.clue==1){
                    former.linked=true;
                    orderedCells.poll(); 
                }else{
                    if(former.Counter!=0){
                        if((former.allbrokenlines.size()-1)<former.Counter){ //We need to check the things (Really?) 
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
                        former.status_changing(false);
                        former.allbrokenlines = AllPaths(former,false);
                        former.status_changing(true);


                        //System.out.println("Former clue = " + former.clue + " tamanho = " + former.allbrokenlines.size());
                        if(former.allbrokenlines.size()==0){ //We need to check the things
                            if (former.mandatory_cases.size() == 0){
                                if (former.toujours_ocuppe){
                                    orderedCells.poll();

                                }  
                                else{
                               // System.out.println("Estou aqui!!! Com celula " + former.x + "," + former.y + " clue " + former.clue);
                                //ArrayList<Cell> cases1 = cases_obligatories(former);
                                //System.out.println("Passei pelo cases_obligatoires, que tem tamanho "+  cases1.size());
                                
                                ArrayList<Cell> cases2 = cells_to_removeLine();
                                System.out.println("Passei pelo cells_to_removeLine, que tem tamanho " +cases2.size());
                                //if (former.x == 0 && former.y == 1 ){
                                  //  break;
                                  // } 
                                int reg = 0;
                                //if (cases1.size() == 0) reg++;
                                if (cases2.size() == 0) reg++;
                                while (reg != 1){
                                    removebrokenLine(Last_brokenLine.peek());
                                    orderedCells.addFirst(Last_brokenLine.peek().last);
                                    orderedCells.addFirst(Last_brokenLine.peek().init);
                                    //if (cases1.size() != 0 && (Last_brokenLine.peek().last == cases1.get(0) || Last_brokenLine.peek().init == cases1.get(0))){
                                      //  reg++;
                                   // }
                                    if ( cases2.size() != 0 && (Last_brokenLine.peek().last == cases2.get(0) || Last_brokenLine.peek().init == cases2.get(0))){
                                        reg++;
                                    }
                                    if (reg != 1){
                                        Last_brokenLine.peek().last.Counter = 0;
                                        Last_brokenLine.peek().init.Counter = 0;
                                    } 
                                    Last_brokenLine.pop();
                                    //System.out.println("Tamanho da pilha: "+ Last_brokenLine.size());
                                    }

                                }


                            } else{
                                
                                ArrayList<Cell> cases2 = cells_to_removeLine();
                                System.out.println("Passei pelo cells_to_removeLine, que tem tamanho " +cases2.size());
                               // orderedCells.poll();

                                int reg = 0;

                                while (reg != 1){
                                    removebrokenLine(Last_brokenLine.peek());
                                    orderedCells.addFirst(Last_brokenLine.peek().last);
                                    orderedCells.addFirst(Last_brokenLine.peek().init);
                                    if (Last_brokenLine.peek().last == cases2.get(former.Counter_removeLine) || Last_brokenLine.peek().init == cases2.get(former.Counter_removeLine)){
                                        reg++;
                                        former.Counter_removeLine++;
                                    }
                                    if (reg != 1){
                                        Last_brokenLine.peek().last.Counter = 0;
                                        Last_brokenLine.peek().init.Counter = 0;
                                    } 
                                    Last_brokenLine.pop();
                                    //System.out.println("Tamanho da pilha: "+ Last_brokenLine.size());

                                }
                               // orderedCells.addFirst(former);

                            }

                           /* removebrokenLine(Last_brokenLine.peek());
                            orderedCells.addFirst(Last_brokenLine.peek().last);
                            orderedCells.addFirst(Last_brokenLine.peek().init);
                            
                            //System.out.println("passei por aqui");
                            Last_brokenLine.pop(); */
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


    public void Backtracking(){
    	Cell former;
        Last_brokenLine = new Stack<>();
        //findDeadEnds(orderedCells);
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

class mycomparator3 implements Comparator<Cell>
{
   @Override
   public int compare(Cell a, Cell b)
   {
      return b.clue - a.clue;
   }
}
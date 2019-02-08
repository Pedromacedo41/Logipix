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
    Cell irreversible_point=null;
    Stack<Cell> removedCells;
    int a=0;

	int Counter=0, MemCounter;

	public void initialize(String name){
		OrderedCells = new ArrayList<>();
        Last_brokenLine = new Stack<>();
        interrupting_Cells = new HashSet<>();
        removedCells = new Stack();
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
        temp.pos2= Position.EMPTY;
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
    	temp.pos2 = Position.EMPTY;
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

    public ArrayList<Cell> complete_mandatory_links(Cell cell){
          ArrayList<Cell> arr = new ArrayList<>();
          HashSet<Pair> origins_brokenline = new HashSet<>();
          Cell final_destiny =null;

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
                if(temp!=cell){
                    if(temp.mandatory_1!=null){
                        if(temp.mandatory_2==null) temp.mandatory_2 = cell;

                    }else{
                       temp.mandatory_1 = cell; 
                    }
                    if(temp.clue!=0) {
                        final_destiny = temp;
                    }
                }
                if(temp.linked){
                    Cell origin = GameGrid[temp.currentBrokenLine.getFirst()][temp.currentBrokenLine.getSecond()];
                    origins_brokenline.add(new Pair <Integer,Integer>(origin.x,origin.y));

                }
            }
        }
        set = casas.entrySet();
        iterator = set.iterator();
        if(final_destiny!=null){
            while(iterator.hasNext()){
                Map.Entry mentry = (Map.Entry)iterator.next();
                if((int) mentry.getValue()==size){
                    Pair p2 =(Pair)mentry.getKey();
                    Cell temp = GameGrid[(int) p2.getFirst()][(int) p2.getSecond()];
                    if(temp!=final_destiny){
                        if(temp.mandatory_1!=null){
                            if(temp.mandatory_2==null) temp.mandatory_2 = final_destiny;

                        }else{
                           temp.mandatory_1 = final_destiny; 
                        }
                    }
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
        Collections.sort(arr, new mycomparator3());
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
        interrupting_Cells = new HashSet<>();
        ArrayList<LinkedList<Position>> allbrokenLines = recursive(cell,cell, cell.clue, cell.clue-1, cell.clue, Position.EMPTY,keep_linked);
        ArrayList<BrokenLine> AllBrokenLines = new ArrayList<>();
        for(int i=0; i< allbrokenLines.size(); i++) {
            allbrokenLines.get(i).removeFirst();

            AllBrokenLines.add(new BrokenLine(cell, allbrokenLines.get(i)));
        }
        return AllBrokenLines;
    }

    private ArrayList<LinkedList<Position>> recursive(Cell cell, Cell origin, int clue, int n, int destiny, Position Lastrelative, boolean keep_linked){
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
            ArrayList<Cell> temp = disponible_voisins(cell,origin,n,keep_linked);
            for(int i=0; i < temp.size(); i++){
                Cell ff = temp.get(i);
                Position relative = Position.EMPTY;
                if(ff.x> cell.x) relative= Position.DOWN;
                if(ff.x< cell.x) relative= Position.UP;
                if(ff.y< cell.y) relative= Position.LEFT;
                if(ff.y> cell.y) relative= Position.RIGHT;
                ArrayList<LinkedList<Position>> intermediaire = recursive(ff,origin,clue, n-1,destiny, relative, keep_linked);
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

    public ArrayList<Cell> disponible_voisins(Cell cell, Cell origin, int n, boolean keep_linked){
    	ArrayList<Cell> disponible = new ArrayList<>();
    	if((cell.y+1) < sizeX){
            Cell aux = GameGrid[cell.x][cell.y+1];
    		fill(aux,origin,n,disponible,keep_linked);
    	}
    	if((cell.x+1) < sizeY){
            Cell aux = GameGrid[cell.x+1][cell.y];
    		fill(aux,origin,n,disponible,keep_linked);
    	}
    	if((cell.x-1) >= 0){
            Cell aux = GameGrid[cell.x-1][cell.y];
    		fill(aux,origin,n,disponible,keep_linked);
    	}
    	if((cell.y-1) >= 0){
            Cell aux = GameGrid[cell.x][cell.y-1];
    		fill(aux,origin,n,disponible,keep_linked);
    	}
    	return disponible;
    }

    private void fill(Cell aux,Cell origin, int n, ArrayList<Cell> disponible, boolean keep_linked){
        if((aux.linked==false || keep_linked) && aux.temp==false){
                if((aux.mandatory_1==null && aux.mandatory_2==null)){     //caso padarao
                    if(n==1) disponible.add(aux);
                    else{ if(aux.clue==0) disponible.add(aux); }
                }else{    //celula ligada de alguma forma 
                    if(aux.mandatory_1!=null && aux.mandatory_2!=null){            // se a potencial vizinha ja tem as duas pontas, verifica se a origem pode ser uma delas, aceitando
                        if(aux.mandatory_1==origin ||  aux.mandatory_1==origin){
                            if(n==1) disponible.add(aux);
                            else{ if(aux.clue==0) disponible.add(aux); }    
                        }
                    }else{   // aceita o vizinho se a celula esta ligada com uma celula de clue compativel ou se o destino nao coincidir com origin
                        if(aux.mandatory_1!=null && aux.mandatory_1.clue==origin.clue){
                            if(n==1) disponible.add(aux);
                            else{ if(aux.clue==0) disponible.add(aux); }  
                        }
                        if(aux.mandatory_2!=null && aux.mandatory_2.clue==origin.clue){
                            if(n==1) disponible.add(aux);
                            else{ if(aux.clue==0) disponible.add(aux); }  
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
            //if(former.clue==4) break;
               // System.out.println("Former clue " + former.clue +" Former counter "+ former.Counter);
                if(former.clue==1){
                    former.linked=true;
                    orderedCells.poll(); 
                }else{
                    if(former.Counter!=0){
                        if((former.allbrokenlines.size()-1)<former.Counter){
                            removebrokenLine(Last_brokenLine.peek()); a++;

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
                            removebrokenLine(Last_brokenLine.peek()); a++;
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
        System.out.println("a= "+a);
    }

    private void GetBack(Cell former){
        ArrayList<Cell> z = cells_to_removeLine();
        if(z.size()==0){
            removebrokenLine(Last_brokenLine.peek()); 
            orderedCells.addFirst(Last_brokenLine.peek().last);
            orderedCells.addFirst(Last_brokenLine.peek().init);
            Last_brokenLine.pop();
        }else{
           Backtrack_Return(z.get(0),true);
            
             
            
        }   
    }

    public void Backtracking2(){
        Cell former;
        Last_brokenLine = new Stack<>();
        while(orderedCells.size()!=0){
            former= orderedCells.peek();
                if(former.clue==1){
                    former.linked=true;
                    irreversible_point = orderedCells.poll(); 
                    irreversible_point.Counter_entering_order = orderedCells.size()+1;
                }else{
                    if(former.Counter!=0){
                        if((former.allbrokenlines.size()-1)<former.Counter){
                            removebrokenLine(Last_brokenLine.peek()); a++;
                            former.Counter=0;
                            Last_brokenLine.peek().last.Counter = 0;
                            orderedCells.addFirst(Last_brokenLine.peek().last);
                            orderedCells.addFirst(Last_brokenLine.peek().init);

                            Last_brokenLine.pop();
                        }else{
                            BrokenLine new_brokenLine = former.allbrokenlines.get(former.Counter);
                            former.Counter++; 
                            update_irreversive_point(former,new_brokenLine);
                            
                            addBrokenLine(Last_brokenLine.push(new_brokenLine));

                            former.Counter_entering_order = orderedCells.size();
                            orderedCells.poll();   

                            Last_brokenLine.peek().last.Counter_entering_order = orderedCells.size(); 
                            orderedCells.remove(Last_brokenLine.peek().last);
                        }
                    }else{
                        former.allbrokenlines = AllPaths(former,false);
                        if(former.allbrokenlines.size()==0){
                            Cell a;
                            Backtrack_Return(a=complete_mandatory_links(former).get(0), false);
                            //System.out.println(a.x+","+a.y);
                            former =  orderedCells.peek();
                            former.allbrokenlines = AllPaths(former,false);
                            complete_mandatory_links(former);
                            //System.out.println("tamanho dos caminhos == " +former.allbrokenlines.size());
                            BrokenLine new_brokenLine = former.allbrokenlines.get(0);        
                            //addBrokenLine(Last_brokenLine.push(new_brokenLine));
                            removebrokenLine(new_brokenLine);

                            former.allbrokenlines = AllPaths(former,false);
                            //System.out.println("tamanho dos caminhos == " +former.allbrokenlines.size());
                            //addBrokenLine(former.allbrokenlines.get(0));
                            GameGrid[13][19].allbrokenlines = AllPaths(GameGrid[13][19],false);
                            //System.out.println("tamanho dos caminhos == " +GameGrid[13][19].allbrokenlines.size());
                            //addBrokenLine(GameGrid[13][19].allbrokenlines.get(0));
                            complete_mandatory_links(GameGrid[14][17]);
                            complete_mandatory_links(GameGrid[13][19]);
                            complete_mandatory_links(GameGrid[15][19]);

                            Backtrack_Return(GameGrid[15][19], false);

                            GameGrid[15][19].allbrokenlines = AllPaths(GameGrid[15][19],false);
                            System.out.println("tamanho dos caminhos == " +GameGrid[15][19].allbrokenlines.size());
                            addBrokenLine(GameGrid[15][19].allbrokenlines.get(0));

                             //GetBack(former);
                            break;
                            //a++;
                        }else{
                            BrokenLine new_brokenLine = former.allbrokenlines.get(former.Counter);
                            former.Counter++; 
                            update_irreversive_point(former,new_brokenLine);

                            addBrokenLine(Last_brokenLine.push(new_brokenLine));

                            former.Counter_entering_order = orderedCells.size();
                            orderedCells.poll();

                            Last_brokenLine.peek().last.Counter_entering_order = orderedCells.size();
                            orderedCells.remove(Last_brokenLine.peek().last);

                        }
                    }
                }
        }
        //GetBack(irreversible_point);
        System.out.println("a= "+a);
        System.out.println("Irreversible Point= "+ irreversible_point.x+ ","+ irreversible_point.y);
        //System.out.println(GameGrid[10][0].mandatory_2.x+ "," + GameGrid[10][0].mandatory_2.y);
         //System.out.println(irreversible_point.allbrokenlines.size());
         //Backtrack_Return(irreversible_point,true);
         
         //irreversible_point= Last_brokenLine.peek().init;
         //System.out.println("NextPoint= "+ irreversible_point.x+ ","+ irreversible_point.y);
         //System.out.println(irreversible_point.allbrokenlines.size());
         //System.out.println(irreversible_point.Counter);
         //orderedCells.poll();
         //System.out.println("NextPoint= "+ orderedCells.peek().x+ ","+ orderedCells.peek().y);
         //System.out.println(AllPaths(orderedCells.peek(),false).size());
    }

    private void update_irreversive_point(Cell former, BrokenLine new_brokenLine){
        if(former.Counter==former.allbrokenlines.size()){
           if(Last_brokenLine.size()==0){
                new_brokenLine.is_turning_point = true;
                irreversible_point = former;
                //complete_mandatory_links(former);
           }else{
                if(Last_brokenLine.peek().is_turning_point==true){
                    new_brokenLine.is_turning_point = true;
                    irreversible_point = former;
                    //complete_mandatory_links(former);
                }
           }
        }
    }


    public void Backtrack_Return(Cell destin, boolean keep_destin){
        BrokenLine removedBL;
        while(Last_brokenLine.peek().init != destin && Last_brokenLine.peek().last != destin){
            removedBL = Last_brokenLine.pop();
            removebrokenLine(removedBL);
            removedBL.last.Counter=0;
            removedBL.init.Counter=0;
            orderedCells.addFirst(removedBL.last);
            orderedCells.addFirst(removedBL.init);
        }
        removedBL = Last_brokenLine.pop();
        removebrokenLine(removedBL);

        if(removedBL.init == destin){
            removedBL.last.Counter=0;
            orderedCells.addFirst(removedBL.last);
            orderedCells.addFirst(removedBL.init);
            if(keep_destin==false) removedBL.init.Counter=0;
        }else{
            removedBL.init.Counter=0;
            orderedCells.addFirst(removedBL.init);
            orderedCells.addFirst(removedBL.last);
            if(keep_destin==false) removedBL.last.Counter=0;
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

    return a.Counter_entering_order - b.Counter_entering_order;
      
   }
}
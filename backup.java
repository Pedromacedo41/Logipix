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
        if(temp.size()==0) fill_noVoisins(cell);
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

       public void removebrokenLine2(ArrayList<Cell> line){ //Removing a broken line 
        for (int i=0; i < (line.size()-1) ; i++) {
            line.get(i).linked = false;         
            line.get(i).pos1 = Position.EMPTY;
            line.get(i+1).pos2 = Position.EMPTY;
        }
        line.get(line.size()-1).linked = false;
    }

      private void addBrokenLine2(ArrayList<Cell> line){ //Create the links between the paths
        for (int i=0; i < (line.size()-1) ; i++) {
            line.get(i).linked = true;   
            line.get(i).currentBrokenLine.setFirst(line.get(0).x);
            line.get(i).currentBrokenLine.setSecond(line.get(0).y);         
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


      private void cases_obligatories(Cell cell){
          ArrayList<BrokenLine> t=  AllPaths(cell);
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

    
    private void cases_obligatories(Cell cell){
          ArrayList<BrokenLine> t=  AllPaths(cell, true);
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
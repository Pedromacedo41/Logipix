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
                            addBrokenLine(GameGrid[15][19].allbrokenlines.get(2));




if(z.size()==0){
                removebrokenLine(Last_brokenLine.peek()); 
                orderedCells.addFirst(Last_brokenLine.peek().last);
                orderedCells.addFirst(Last_brokenLine.peek().init);
                Last_brokenLine.pop();
            }else{
               Backtrack_Return(z.get(0),true);  



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
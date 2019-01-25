import java.util.*;

public class Cell{
	int x,y;
	int clue;
	int Counter=0;
	boolean temp= false;
	boolean linked = false;
	boolean toujours_ocuppe = false;
	Pair<Integer,Integer> currentBrokenLine = new Pair(0,0);
	
	ArrayList<ArrayList<Cell>> allbrokenlines;
	Position pos1 = Position.EMPTY, pos2 = Position.EMPTY;

	Cell(int x, int y, int clue){
		this.x = x;
		this.y = y;
		this.clue = clue;
		allbrokenlines = new ArrayList<>();
	}
	
}
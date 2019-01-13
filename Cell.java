import java.util.*;

public class Cell{
	int x,y;
	int clue;
	boolean temp= false;
	boolean linked = false;
	HashSet<ArrayList<Cell>> pastBrokenLines;
	Position pos1 = Position.EMPTY, pos2 = Position.EMPTY;
	Cell(int x, int y, int clue){
		this.x = x;
		this.y = y;
		this.clue = clue;
	}
}
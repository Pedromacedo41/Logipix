import java.util.*;

public class Cell{
	int x,y;
	int clue;
	int Counter=0;
	int previous_priority=0;
	boolean temp= false;
	boolean linked = false;
	boolean is_irreversible=false;
	Pair<Integer,Integer> currentBrokenLine = new Pair(0,0);
	Cell currentBrokenLineTail = null;
	Cell mandatory_1=null, mandatory_2=null;
	int Counter_entering_order=0;
	HashSet<Pair> tested_returns;
	HashSet<Cell> forbidden_destinies;

	ArrayList<BrokenLine> allbrokenlines;
	Position pos1 = Position.EMPTY, pos2 = Position.EMPTY;

	Cell(int x, int y, int clue){
		this.x = x;
		this.y = y;
		this.clue = clue;
		allbrokenlines = new ArrayList<>();
		tested_returns = new HashSet<>();
		forbidden_destinies = new HashSet<>();
	}

	public int hashCode() {
        return (new Pair(x,y)).hashCode();
    }
	
}
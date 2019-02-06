import java.util.*;

public class Cell{
	int x,y;
	int clue;
	int Counter=0;
	boolean temp= false;
	boolean linked = false;
	boolean toujours_ocuppe = false;
	Pair<Integer,Integer> currentBrokenLine = new Pair(0,0);
	ArrayList<Cell> mandatory_cases;
	Cell origin;
	int Counter_removeLine;


	
	ArrayList<BrokenLine> allbrokenlines;
	Position pos1 = Position.EMPTY, pos2 = Position.EMPTY;

	Cell(int x, int y, int clue){
		this.x = x;
		this.y = y;
		this.clue = clue;
		allbrokenlines = new ArrayList<>();
		mandatory_cases = new ArrayList<>();
		origin = null;
	}

	public void status_changing (boolean b){
		for (Cell x : mandatory_cases){
			x.toujours_ocuppe = b;
		}

	}

	public int hashCode() {
        return (new Pair(x,y)).hashCode();
    }
	
}
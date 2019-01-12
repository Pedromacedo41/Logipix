public class Cell{
	int x,y;
	int clue;
	boolean blank;
	boolean linked = false;
	Position pos1 = Position.EMPTY, pos2 = Position.EMPTY;
	Cell(int x, int y, int clue){
		this.x = x;
		this.y = y;
		this.clue = clue;
		blank = (clue==0);
	}
}
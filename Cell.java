public class Cell{
	int x,y;
	int clue;
	boolean blank;
	boolean linked;
	Position position;
	Cell(int x, int y, int clue){
		this.x = x;
		this.y = y;
		this.clue = clue;
		blank = (clue==0);
	}
}
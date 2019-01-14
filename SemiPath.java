import java.util.*;

public class SemiPath{
	int size;
	int destiny;
	LinkedList<Position> Path;

	public SemiPath(int size, int destiny, LinkedList<Position> Path){
		this.size=size;
		this.destiny=destiny;
		this.Path=Path;
	}
}
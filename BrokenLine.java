import java.util.*;

public class BrokenLine{
	Cell init, last;
	ArrayList<Position> order;
	boolean is_turning_point=false;
	BrokenLine(Cell init, LinkedList<Position> order){
		this.init=init;
		this.order = new ArrayList<Position>();
		this.order.addAll(order);
	}
}
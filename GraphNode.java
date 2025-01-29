
public class GraphNode {
	private int name;
	private boolean mark;

	
	public GraphNode(int name) {
		this.name = name;
		this.mark = false;
	}

	public void mark(boolean mark) { // marks node
		this.mark = mark;
	}
	
	public boolean isMarked() { // returns mark
		return this.mark; 
	}
	
	public int getName() { // returns name
		return this.name; 
	}
}


public class GraphEdge {
	private GraphNode u; // start node
	private GraphNode v;
	private int type;
	private String label;
	
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}
	
	public GraphNode firstEndpoint() { // returns start node of edge
		return this.u;
	}
	
	public GraphNode secondEndpoint() { // returns end node of edge
		return this.v;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setype(int type) {
		this.type = type;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}

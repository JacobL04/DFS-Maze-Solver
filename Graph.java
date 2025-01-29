import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {
	// Create an adjacency matrix (faster time complexity)
	private GraphEdge[][] adjMatrix;
	private List<GraphNode> nodes;
	
	public Graph(int n) {
		adjMatrix = new GraphEdge[n][n]; // creating an empty graph with n nodes and no edges
		nodes = new ArrayList<>();

		for(int i = 0; i < n; i++){ // names of the nodes are 0, 1, . . . , n-1
			nodes.add(new GraphNode(i));
		}
	}
	
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException { // inserts an undirected edge between nodeu and nodev
		int u = nodeu.getName();
		int v = nodev.getName();

		if(u >= adjMatrix.length || v >= adjMatrix.length){ // checks if nodes are in bound
			throw new GraphException("Node is out of bounds");
		}

		GraphEdge edge = new GraphEdge(nodeu, nodev, type, label); // undirected graph two way edge
		adjMatrix[u][v] = edge;
		adjMatrix[v][u] = edge;
	}

	@Override
	public GraphNode getNode(int u) throws GraphException {
		try{
			return nodes.get(u); // gets the node from the list
		}
		catch(IndexOutOfBoundsException e){
			throw new GraphException("Node doesn't exists");
		}
	}

	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {    
        int uIndex = u.getName();  
        ArrayList<GraphEdge> incidentEdges = new ArrayList<>();
        
        for(int i = 0; i < adjMatrix.length; i++){ // iterates through the adjacency matrix to find incident edge
            if(adjMatrix[uIndex][i] != null){
                incidentEdges.add(adjMatrix[uIndex][i]);
            }
        }

        if(incidentEdges.isEmpty()){ // return null if no incident edges are found
            return null;
        }
        
        return incidentEdges.iterator();
	}

	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		int uIndex = u.getName();
		int vIndex = v.getName();

		if(uIndex >= adjMatrix.length){
			throw new GraphException("Node" + u.getName() +" does not exist.");
		}
		else if(vIndex >= adjMatrix.length){
			throw new GraphException("Node" + v.getName() +" does not exist.");
		}

		if(adjMatrix[uIndex][vIndex] == null){ // checks if they have edges
        	throw new GraphException("No edge exists between the specified nodes.");
    	}

		return adjMatrix[uIndex][vIndex]; // returns the edge if it exists
	}

	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {		
		int uIndex = u.getName();
		int vIndex = v.getName();

		if(uIndex >= adjMatrix.length) {
			throw new GraphException("Node" + u.getName() +" does not exist.");
		}
		else if(vIndex >= adjMatrix.length){
			throw new GraphException("Node" + v.getName() +" does not exist.");
		}

		if(adjMatrix[uIndex][vIndex] != null){ // true if there's an edge between u and v
			return true;
		}

		else {
			return false;
		}
	}
}
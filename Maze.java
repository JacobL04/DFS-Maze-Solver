import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Maze {
	private Graph G; // graph G
	private int startNode;
	private int endNode;
	private int coins; // total coins
	

	public Maze(String inputFile) throws MazeException {
		try{ // tries to read the inputFile
			FileReader file = new FileReader(inputFile);
			BufferedReader buffer = new BufferedReader(file);
			readInput(buffer);
		}

		catch(IOException e){
			throw new MazeException("Error while reading file");
		}

		catch(Exception e){
			throw new MazeException("Can't read maze file");
		}
	}

	public Graph getGraph() { // returns the graph (getter)
		return this.G;
	}

	public Iterator<GraphNode> solve() { // calls DFS to solve the maze
		try{
			return DFS(coins, G.getNode(startNode));
		}

		catch(Exception e){
			return null;
		}
	}

	private Iterator<GraphNode> DFS(int k, GraphNode currentNode) throws GraphException { // depth first search algorithm
		List<GraphNode> pathList = new ArrayList<>(); // stores paths in list
	
		if(DFSRecursive(k, currentNode, pathList)){
			return pathList.iterator(); // returns the path
		}
		
		else {
			return null;
		}
	}
	
	private boolean DFSRecursive(int k, GraphNode currentNode, List<GraphNode> pathList) throws GraphException { // DFS helper method, using pseudocode from lecture notes
		currentNode.mark(true); // mark current node
		pathList.add(currentNode);
	
		if(currentNode.getName() == endNode){ // checks if we reached exit node
			return true;
		}
	
		Iterator<GraphEdge> edges = getGraph().incidentEdges(currentNode);
		while(edges.hasNext()){ // for every incident edge (u, v)
			GraphEdge edge = edges.next();
			GraphNode adjacentNode;
			if(edge.firstEndpoint() == currentNode){
				adjacentNode = edge.secondEndpoint();
			}

			else {
				adjacentNode = edge.firstEndpoint();
			}
	
			if(!edge.getLabel().equals("discovery") && !edge.getLabel().equals("back")){ // if the edge has not been labelled as "discovery" or "back"
				if(!adjacentNode.isMarked() && (edge.getLabel().equals("corridor") || (edge.getLabel().equals("door") && k >= edge.getType()))){ // if the adjacentNode is unmarked
					edge.setLabel("discovery"); // label edge as "discovery"
	
					if(DFSRecursive(k - edge.getType(), adjacentNode, pathList)){
						return true;  // true if the path is found
					}

					else {
						edge.setLabel("back"); // label edge as "back"
					}
				}
			}
		}
	
		currentNode.mark(false); // backtracks; unmark current node and remove it from path list
		pathList.remove(pathList.size() - 1);
		return false;  // false if no path found
	}

	private void readInput(BufferedReader inputReader) throws IOException, GraphException { // reads and parses the input maze file
		int S = Integer.parseInt(inputReader.readLine());  // scale factor
		int A = Integer.parseInt(inputReader.readLine());  // width of the maze
		int L = Integer.parseInt(inputReader.readLine());  // length of the maze
		coins = Integer.parseInt(inputReader.readLine());  // number of coins
		
		String[] maze = new String[2 * L - 1];
		for(int i = 0; i < maze.length; i++){
			maze[i] = inputReader.readLine();
		}
		
		int area = A * L;
		G = new Graph(area); // sets graph with area of (A * L)

		for(int row = 0; row < L; row++){ // iterates through entire maze nodes
			for(int col = 0; col < A; col++){
				int currentNode = row * A + col;
				char currentCell = maze[2 * row].charAt(2 * col);
	
				// finds the start and end nodes
				if(currentCell == 's'){
					startNode = currentNode;
				}

				else if(currentCell == 'x'){
					endNode = currentNode;
				}
	
				// handles corridors (o), start (s) and exit (x)
				if(currentCell == 'o' || currentCell == 's' || currentCell == 'x'){
					if(col + 1 < A && maze[2 * row].charAt(2 * col + 1) != 'w'){ // checks adjacent rooms
						int neighborNode = row * A + (col + 1);
						char edgeChar = maze[2 * row].charAt(2 * col + 1);  // horizontal edge
	
						// inserts edge
						if(edgeChar == 'c'){
							insertEdge(currentNode, neighborNode, 0, "corridor");
						}
						
						else if (Character.isDigit(edgeChar)){
							int coinTax = Character.getNumericValue(edgeChar);
							insertEdge(currentNode, neighborNode, coinTax, "door");
						}
					}
	
					if(row + 1 < L && maze[2 * row + 1].charAt(2 * col) != 'w'){ // checks adjacent rooms
						int neighborNode = (row + 1) * A + col;
						char edgeChar = maze[2 * row + 1].charAt(2 * col);  // vertical edge
	
						// inserts edge
						if(edgeChar == 'c'){
							insertEdge(currentNode, neighborNode, 0, "corridor");
						}
						
						else if(Character.isDigit(edgeChar)){
							int coinTax = Character.getNumericValue(edgeChar);
							insertEdge(currentNode, neighborNode, coinTax, "door");
						}
					}
				}
            }
		}
	}
	
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException { // selects the nodes and insert the appropriate edge
		getGraph().insertEdge(getGraph().getNode(node1), getGraph().getNode(node2), linkType, label);
	}
}

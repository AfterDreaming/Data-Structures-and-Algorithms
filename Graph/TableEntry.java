import java.util.LinkedList;

/**
 * Represents a single entry in our table of vertexes for solving Dijkstra's
 * 
 * @author robertwaters
 *
 */
public class TableEntry implements Comparable<TableEntry> {

	/**  the overall weight of the path we know so far */
	private int weight;
	
	/** the vertex for this entry */
	private Vertex myVertex;

	/** the immediately preceeding vertex in the shortest path */
	private Vertex parent;
	
	/** whether myVertex's shortest path has been found */
	private boolean known;

	/**
	 * Set up an initial table entry
	 * 
	 * @param vertex
	 *            a vertex  the initial vertex
	 */
	public TableEntry(Vertex vertex) {
		//todo
		myVertex = vertex;
		known = false;
		parent = null;
		weight = Integer.MAX_VALUE; // the weight is infiniti at first time
	}

	@Override
	public int compareTo(TableEntry other) {
		// TODO
		return (weight - other.getWeight());
	}
	
	@Override
	public String toString() {
		if(parent == null){
			return "[0]"+"("+ myVertex.getName()+")";
		}
		return "("+ myVertex.getName()
				+", "+ parent.getName() +", "+ weight +" )";
	}

	// Provided Methods
	public int getWeight() {
		return weight;
	}
	
	public Vertex getVertex() {
		return myVertex;
	}
	
	public boolean known() {
		return known;
	}
	
	public void setKnown(boolean val) {
		known = val;
	}
	
	public void setParent(Vertex p) {
		parent = p;
	}
    
    public void setWeight(int newWeight) {
        weight = newWeight;
    }
	
}


/**
 * Represents a single edge in a graph
 * 
 * @author robertwaters
 *
 */
public class Edge implements Comparable<Edge> {
	/** the edge;s weight */
	private int weight;
	/** the source vertex */
	private Vertex src;
	/** the destination vertex */
	private Vertex dest;
	
	/**
	 * Construct a new edge
	 * @param u  the source vertex
	 * @param v  the destination vertex
	 * @param weight the edge weight
	 */
	public Edge(Vertex u, Vertex v, int weight) {
		// TODO
		src = u;
		dest = v;
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		// TODO Extra Credit

		return "Edge Source: "+ src + " Destination is: " + dest + " the Weight is: " + weight;
	}

	@Override
	public int compareTo(Edge that) {
		// TODO
		return (weight - that.weight);
	}
	
	// Provided Methods
	public int getWeight() {
		return weight;
	}

	public Vertex getSource() {
		return src;
	}

	public Vertex getDestination() {
		return dest;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Edge) {
			Edge e = (Edge) o;
			boolean w = weight == e.weight;
			//edges are equal in either direction for undirected graph
			if (src.equals(e.src) && dest.equals(e.dest)) {
				return w;
			} else if (src.equals(e.dest) && dest.equals(e.src)) {
				return w;
			} else {
				return false;
			}
		} else { 
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return src.hashCode() ^ dest.hashCode() ^ weight;
	}
}

/**
 * This class represents a single vertex in a graph
 * 
 * @author robertwaters
 *
 */
public class Vertex {
	
	/** the name of the vertex */
	private String name;

	/**
	 * Construct a vertex from a name
	 * @param pname the name of the vertex
	 */
	public Vertex(String pname) {
		// TODO
		this.name = pname;
	}

	@Override
	public String toString() {
		// TODO Extra Credit
		return "Vertex name is : " + name;
	}
	
	/**
	 * @return the name of the vertex
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Vertex) {
			Vertex v = (Vertex) o;
			return name.equals(v.name);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}

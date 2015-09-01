import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a graph that uses an edge list for it's representation
 * 
 * @author robertwaters
 *
 */
public class EdgeList {
	
	/**
	 * The list of edges in the graph
	 */
	private Collection<Edge> edges;
    
    /**
     * The set of vertexes in the graph
     */
    private Set<Vertex> vertexes;
	
	/**
	 * Construct an EdgeList from a given file
	 * 
	 * @param filename the name of the file
	 * @throws IOException Any of the exceptions that might occur while we process the file
	 */
	public EdgeList(String filename) throws IOException {
		// TODO  Open/read/parse the file
		edges = new ArrayList<>();
		vertexes = new HashSet<>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		Vertex a;
		Vertex b;
		while(line != null){
			String[] arr = line.split(",");
			a = new Vertex(arr[0]);
			b = new Vertex(arr[1]);
			edges.add(new Edge(a,b,Integer.parseInt(arr[2])));
			vertexes.add(a);
			vertexes.add(b);
            line = br.readLine();
		}
	}
	
	@Override
	public String toString() {
		// TODO Extra Credit
        String toReturn = "";
        toReturn += "The size of EdgeList is: "+ edges.size();
		for(Edge e : edges){
            toReturn += e.toString();
        }
		return "EdgeList";
	}
	
	/**
	 * 
	 * @return all the edges in the graph
	 */
	public Collection<Edge> getEdges() {
		return edges;
	}
    
    public Set<Vertex> getVertexes() {
        return vertexes;
    }
}

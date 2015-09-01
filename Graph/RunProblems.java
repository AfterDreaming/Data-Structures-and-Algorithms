import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * A class to allow you to write drivers if you wish to test out your code
 * 
 * @author robertwaters
 *
 */
public class RunProblems {

	/**
	 * TEST YOUR CODE here (if you do not implement JUnit tests). This method is for your benefit
	 * and will not be graded.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO
        AdjacencyList adjacencyList1 = new AdjacencyList("debuggraph.txt", GraphType.UNDIRECTED);
        List<TableEntry> tableEntries = runDijkstra(adjacencyList1, "a");
        for (int i = 0; i < tableEntries.size(); i++) {
            System.out.println(tableEntries.get(i).toString());
        }
        EdgeList list = new EdgeList("debuggraph.txt");
        Collection<Edge> out = runKruskal(list);
        System.out.println(list);
        for(Edge e : out) {
            System.out.println(e.toString());
        }

    }

	/**
	 * Create an adjacency list (using the AdjacencyList constructor) from the given txtFile and run
	 * Dijkstra's algorithm with the given AdjacencyList and the given source vertex
	 * 
	 * @param source  the name of the start vertex
	 * @param list the name of the file that contains the graph information
	 * @return a map keyed by vertex that contains the shortest path to the vertex from the source
	 */
	public static List<TableEntry> runDijkstra(AdjacencyList list, String source) throws IOException {
		// TODO
        List<TableEntry> entries = Dijkstra.dijkstra(source,list);
		return entries;
	}

	/**
	 * run Kruskal's
	 * algorithm with that EdgeList, return the list of edges that are in the MST
	 * 
	 * @param edges graph in EdgeList format
	 * @return the edges in the MST
	 */
	public static Collection<Edge> runKruskal(EdgeList edges) throws IOException {
		// TODO
        Collection<Edge>  entries = KruskalsAlgorithm.kruskal(edges);
        return entries;
	}
}

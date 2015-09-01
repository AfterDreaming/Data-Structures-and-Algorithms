import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdjacencyList {

    /**
     * This is a map (Hash Table) of vertices to their adjacent vertices and the weight associated with that edge
     */
    private Map<Vertex, Map<Vertex, Integer>> adjList;
    private GraphType myType = GraphType.UNDIRECTED;

    /**
     * Create an adjacency list from a file
     */
    public AdjacencyList(String filename, GraphType pType) throws IOException {
        adjList = new HashMap<>();
        myType = pType;
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        Vertex a, b;
        Map<Vertex, Integer> subMap;
        while (line != null) {
            String[] arr = line.split(",");
            a = new Vertex(arr[0]);
            b = new Vertex(arr[1]);

            subMap = adjList.get(a);
            if (subMap != null) {
                subMap.put(new Vertex(arr[1]), Integer.parseInt(arr[2]));
            } else {
                subMap = new HashMap<>();
                subMap.put(new Vertex(arr[1]), Integer.parseInt(arr[2]));
                adjList.put(a, subMap);
            }

            subMap = adjList.get(b);
            if (subMap != null) {
                subMap.put(new Vertex(arr[0]), Integer.parseInt(arr[2]));
            }else {
                subMap = new HashMap<>();
                subMap.put(new Vertex(arr[0]), Integer.parseInt(arr[2]));
                adjList.put(b, subMap);
            }
            line = br.readLine();
        }
    }

    @Override
    public String toString() {
        return "AdjacencyList";
    }

    /**
     * Find a vertex by its name
     *
     * @param name the name of the vertex to find
     * @return the vertex with that name (or null if none)
     */

    public Vertex findVertexByName(String name) {
        //TODO
        if (name == null) {
            return null;
        }
        Set<Vertex> vertexSet = adjList.keySet();
        for (Vertex v : vertexSet) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    /**
     * @return the number of vertexes in the graph
     */
    public int vertexCount() {
        //TODO
        return adjList.size();
    }

    public Map<Vertex, Map<Vertex, Integer>> getAdjList() {
        return adjList;
    }
}

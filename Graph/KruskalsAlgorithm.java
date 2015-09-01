import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Set;


/**
 * This class encapsulates Kruskal's algorithm for finding a minimum spanning tree 
 * 
 * @author robertwaters
 *
 */
public final class KruskalsAlgorithm {

	public static Collection<Edge> kruskal(EdgeList edgeList) {
		// TODO
        Collection<Edge> edges = edgeList.getEdges();
        Collection<Edge> toReturn = new ArrayList<>();
        Set<Vertex> vertexes = edgeList.getVertexes();
        UnionFind uf = new UnionFind(vertexes);
        PriorityQueue<Edge> edgesQueue = new PriorityQueue<>();
        //add the edges to the PriorityQueue
        for(Edge e: edges){
            edgesQueue.add(e);
        }
        Edge toAdd;
        while(!edgesQueue.isEmpty()){
            // poll out the minimum edges
            toAdd = edgesQueue.poll();
            //check if in the same set
            //if they are not in the same set, union the des and scr
            if(uf.find(toAdd.getSource()) != uf.find(toAdd.getDestination())){
                toReturn.add(toAdd);
                uf.union(toAdd.getSource(),toAdd.getDestination());
            }
        }
		return toReturn;
	}
	
}

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the union find algorithm
 * 
 * @author robertwaters
 *
 */
public class UnionFind {

    private class Node<Vertex> {
        Vertex vertex;
        Node<Vertex> parent;
        int rank, index;
        public Node(Vertex v, int index) {
            this.vertex = v;
            this.parent = null;
            this.rank = 0;
            this.index = index;
        }
    }
    private Map<Vertex,Node<Vertex>> map;
	/**
	 * This should implement the make_set function of union find
	 * 
	 * @param
	 */
	public UnionFind(Set<Vertex> vertexes) {
		// TODO
        map = new HashMap<>();
        int i = 0;
        for(Vertex v: vertexes){
            map.put(v,new Node<>(v,i));
            i++;
        }
	}

	/**
	 * Assume that u is a vertex. Determine if they are currently
	 * in the same component of this UnionFind structure
	 * 
	 * @param u the vertex we want to find the set for
	 * @return the name of the set that u is in
	 */
	public int find(Vertex u) {
		// TODO
        //self is the root
        Node<Vertex> head = map.get(u);

        //find the head of the node
        while(head.parent != null){
            head = head.parent;
        }
        //return the head/root of u
        return head.index;
	}

	/**
	 * Assume that u and v are vertices that were in the edgeList. Assume that u and v are in
	 * different components (you have already checked find). Union the component containing u and the component containing v
	 * together.
	 * 
	 * @param u
	 *            a vertex
	 * @param v
	 *            a vertex
	 */
	public void union(Vertex u, Vertex v) {
		// TODO
		Node<Vertex> uNode = map.get(u);
        Node<Vertex> vNode = map.get(v);
        Node<Vertex> uRoot = uNode;
        int findU = find(u);
        int findV = find(v);
        //System.out.println("findU is: "+findU+" findV: "+ findV);
        int uRank = 0;
        int vRank = 0;
        while(uRoot.parent !=null ){
            uRoot = uRoot.parent;
            uRank++;
        }
        uRoot.rank = uRank;
        Node<Vertex> vRoot = vNode;
        while(vRoot.parent !=null ){
            vRoot= vRoot.parent;
            vRank++;
        }
        vRoot.rank = vRank;
        //System.out.println("uRoot is: "+uRoot.vertex+" vRoot: "+ vRoot.vertex);
        if(uRoot.rank > vRoot.rank){
            vRoot.parent = uRoot;
        } else {
            uRoot.parent = vRoot;
        }

	}
}

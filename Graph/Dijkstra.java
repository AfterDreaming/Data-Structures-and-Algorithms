import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This class implements Dijkstra's algorithm
 * 
 * @author Yufan Zhuang
 *
 */
public final class Dijkstra {
	
	/**
	 * 
	 * @param source  The vertex to start the algorithm from
	 * @param adjList the graph to perform the search on.
	 * @return  
	 */
	public static List<TableEntry> dijkstra(String source, AdjacencyList adjList) {
		// TODO
        if(source == null || adjList.vertexCount() == 0){return null;}
        List<TableEntry> table = new LinkedList<>();
        Vertex startVertex = adjList.findVertexByName(source);
        //System.out.println("start At: " + startVertex);
        if(startVertex == null){return null;}

        Map<Vertex, Map<Vertex, Integer>>  mapList = adjList.getAdjList();
        Set<Vertex> vertexSet = mapList.keySet();

        table.add(new TableEntry(startVertex));
        for(Vertex v : vertexSet){
            if(!v.equals(startVertex)) {
                table.add(new TableEntry(v));
               // System.out.println(v + " is Added");
            }
        }
//        System.out.println("TABLE DETAILS:");
//        for(int i = 0; i<table.size();i++){
//            System.out.println(table.get(i));
//
//        }

        Map<Vertex, Integer> currentVertexMap = mapList.get(startVertex);
        //System.out.println("starter MAP INSIDE: "+currentVertexMap.size());
        int i = 0;
        TableEntry currentEntry = table.get(i); // get the starter Vertex
        currentEntry.setKnown(true);
        currentEntry.setWeight(0);
        TableEntry checkTE;
        while(i != adjList.vertexCount()){
            //System.out.println("current is: "+currentEntry+" i: "+i);
            // update the table
            for (int j = 0; j < table.size();j++){
                checkTE = table.get(j);
                //System.out.println("checkTE is:" + checkTE);
                //update base on neighborhood info
                if(!checkTE.known() && currentVertexMap.get(checkTE.getVertex())!= null){
                    //update the distance
                    //System.out.println("MAP INSIDE: "+currentVertexMap.get(checkTE.getVertex()));
                    if(checkTE.getWeight() > (currentVertexMap.get(checkTE.getVertex())+ currentEntry.getWeight())) {
                        //System.out.println("upDate TE is:" + checkTE);
                        checkTE.setParent(currentEntry.getVertex());
                        checkTE.setWeight((currentVertexMap.get(checkTE.getVertex())+ currentEntry.getWeight()));
                    }
                }
            }
            //find the minimum total distance
            TableEntry miniEntry = null;
            for(int j = 0; j < table.size();j++) {
                if (!table.get(j).known()) {
                    if(miniEntry == null){
                        miniEntry = table.get(j);
                    }
                    if (table.get(j).getWeight() < miniEntry.getWeight()) {
                        miniEntry = table.get(j);
                        //System.out.println("Current is changed to: "+ currentEntry);
                    }
                }
            }
            currentEntry = miniEntry;
            if(currentEntry != null){
                currentEntry.setKnown(true);
                currentVertexMap = mapList.get(currentEntry.getVertex());
            }
            i++;
        }
        return table;
	}
}

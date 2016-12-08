package a4q2;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Graph<T> {

	HashMap<String, Vertex<T>> 	vertexMap;

	// constructor

	public Graph()              			  	
	{	
		vertexMap   = new HashMap<String, Vertex<T>>();			
	}  

	public void addVertex(String key, Vertex<T> vertex)   		  
	{	
		vertexMap.put(key, vertex);
		vertex.adjList = new LinkedList<Edge>();
	}	
	
	
	public void addEdge(String start, String end){
	//   throw exception (add this later
    //		if (vertexMap.get(start)  == null)
	//  	if (vertexMap.get(end) == null)
			
		vertexMap.get(start).adjList.add(  new Edge( vertexMap.get(end)) );	
	}

	//  override
	@Override
	public String toString()
	{
		String result="";

		Set<String>  vertexKeySet = vertexMap.keySet(); 
		for( String key : vertexKeySet ){
			result += key +'\n';

			LinkedList<Edge>  listEdges = vertexMap.get(key).adjList;	
			for( Edge   e : listEdges ){
				result += "   edge to Vertex " + e.getEndVertex().key + "\n";
			}
		}
		return(result);
	}
}
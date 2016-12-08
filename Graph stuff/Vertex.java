package a4q2;
import java.util.LinkedList;

class Vertex<T> {

	String key;
	LinkedList<Edge> adjList;
	boolean	visited;
	T       element;
	
	public Vertex(String key,  T element) {
		this.key = key;
		adjList = new LinkedList<Edge>();
		this.element = element;
	}

	public String getKey(){
		return key;
	}
	
	void setVisited(boolean value){
		visited = value;
	}
	
	boolean getVisited(){
		return visited;
	}
	
	void addEdge(Edge e){
		adjList.add(e);
	}


}

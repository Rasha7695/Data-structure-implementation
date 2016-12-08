package a4q2;

import java.util.LinkedList;
import java.util.Set;
//needed to scan all vertices in the hash map below
import java.util.*;

public class ShapeGraph extends Graph<Shape> {

	public ShapeGraph() {
	}

	public void resetVisited() {
		Set<String>  vertexKeySet =  vertexMap.keySet();
		for( String key : vertexKeySet ){
			vertexMap.get(key).visited = false;
		}
	}


	/**
	 * Returns a list of lists, each inner list is a path to a node that can be reached from a given node
	 * if the total area along the path to that node is greater than the threshold.
	 * Your solution must be a recursive, depth first implementation for a graph traversal.
	 * The Strings in the returned list of lists should be the vertex labels (keys).
	 */

	public LinkedList<LinkedList<String>> traverseFrom(String key, float threshold)
	{
		LinkedList<LinkedList<String>> masterList = new LinkedList<>();
		//make sure we are using a valid vertex
		if(this.vertexMap.get(key)!=null&&this.vertexMap.get(key).element!=null) {
			//mark all nodes as unvisited
			for(Map.Entry<String, Vertex<Shape>> entry:this.vertexMap.entrySet())
				entry.getValue().setVisited(false);
			//add all the paths calculated by a dfs starting from vertex at key
			masterList.addAll(dfs(this.vertexMap.get(key), this.vertexMap.get(key).element.getArea(), threshold));
		}
		return masterList;
	}

	//A helper method that do a Depth first search starting from vertex u and it collect all valid paths
	//that start from u and end in other vertices then return this collection of paths in a backward recursive manner
	private LinkedList<LinkedList<String>> dfs(Vertex<Shape> u,float pathValue,float threshold){
		//a variable to hold the result
		LinkedList<LinkedList<String>> result=new LinkedList<LinkedList<String>>();
		//check if we have a a valid path
		if(pathValue>threshold) {
			//add the current vertex to the path
			LinkedList<String> temp=new LinkedList<String>();
			temp.add(u.getKey());
			//add this as a valid path (parents will be added in the previous recursive calls)
			result.add(temp);
		}
		//set the current vertex as visited
		u.setVisited(true);
		for(int i=0;i<u.adjList.size();i++){
			//get the neighboring vertex
			Vertex<Shape> v=u.adjList.get(i).endVertex;
			//if not visited, visit it
			if(v.getVisited()==false){
				//get any possible valid paths starting from the neighboring
				//vertices with (pathValue = current path Value + area of the neighboring vertex)
				LinkedList<LinkedList<String>> localResult=dfs(v,pathValue+v.element.getArea(),threshold);
				//we can add the current node to the local paths and still get valid paths !
				//in other words, we are building the valid paths from the end to the start
				for (LinkedList<String> item: localResult)
					item.addFirst(u.getKey());
				result.addAll(localResult);
			}
		}
		//return the resulting list
		return result;
	}
}








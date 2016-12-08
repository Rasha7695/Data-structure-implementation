package a4q2;
import java.util.ArrayList;
import java.util.LinkedList;

public class TesterGraph {

	public static void main(String[] args){
		/*
		 *   Example of manually constructed graph.
		 */

		ShapeGraph graph = new ShapeGraph();

		ArrayList<Vertex<Shape>> listOfVertices = new ArrayList<Vertex<Shape>>();
		listOfVertices.add( new Vertex<Shape>("1", new Square(1)));
		listOfVertices.add( new Vertex<Shape>("2", new Square(2)));
		listOfVertices.add( new Vertex<Shape>("3", new Square(3)));
		listOfVertices.add( new Vertex<Shape>("4", new Square(4)));  // Triangle(10, 25)));
		listOfVertices.add( new Vertex<Shape>("5", new Square(5)));  // Circle(6)));
		listOfVertices.add( new Vertex<Shape>("6", new Square(6)));
		listOfVertices.add( new Vertex<Shape>("7", new Square(7)));
		listOfVertices.add( new Vertex<Shape>("8", new Square(8)));
		listOfVertices.add( new Vertex<Shape>("9", new Square(9)));
		listOfVertices.add( new Vertex<Shape>("10", new Square(10)));
		listOfVertices.add( new Vertex<Shape>("11", new Square(11)));
		listOfVertices.add( new Vertex<Shape>("12", new Square(12)));

		for (int i = 0; i < listOfVertices.size(); i++){
			graph.addVertex( listOfVertices.get(i).getKey(), listOfVertices.get(i) );
		}

		graph.addEdge("1", "2");
		graph.addEdge("1", "5");
		graph.addEdge("1", "6");
		graph.addEdge("2", "3");
		graph.addEdge("2", "4");
		graph.addEdge("6", "7");
		graph.addEdge("6", "12");
		graph.addEdge("7", "8");
		graph.addEdge("7", "11");
		graph.addEdge("8", "9");
		graph.addEdge("8", "10");

		System.out.println(graph);

		LinkedList<LinkedList<String>> fromOne = graph.traverseFrom("1", 25);
		System.out.println("From one, paths should be:");
		System.out.println("1 5");
		System.out.println("1 6");
		System.out.println("1 6 7");
		System.out.println("1 6 7 8");
		System.out.println("1 6 7 8 9");
		System.out.println("1 6 7 8 10");
		System.out.println("1 6 7 11");
		System.out.println("1 6 12");
		System.out.println("Paths returned were:");
		for(LinkedList<String> list : fromOne){
			for(String s : list){
				System.out.print(s + " ");
			}
			System.out.println();
		}

		LinkedList<LinkedList<String>> fromSeven = graph.traverseFrom("7", 200);
		System.out.println("From seven paths should be:");
		System.out.println("7 8 10");
		System.out.println("Paths returned were:");
		for(LinkedList<String> list : fromSeven){
			for(String s : list){
				System.out.print(s + " ");
			}
			System.out.println();
		}

		//You should add tests for more cases and different graphs

	}

}

















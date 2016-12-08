package a4q2;
class Edge {

	Vertex  endVertex;
	double	weight;
	
	Edge(Vertex end, double weight) {
		this.endVertex = end;
		this.weight    = weight;
	}

	Edge(Vertex end ) {
		this.endVertex = end;
	}
	
	Vertex getEndVertex(){
		return endVertex;
	}
}

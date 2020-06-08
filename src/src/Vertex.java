import java.util.LinkedList;

public class Vertex {
	private LinkedList<Vertex> connections;
	private int tag;

	public Vertex(int tag) {
		this.connections = new LinkedList<Vertex>();
		this.tag = tag;	
	}
	
	public void addConnection(Vertex vertex) {
		this.connections.add(vertex);
	}
	
	public void setConnections(LinkedList<Vertex> connections) {
		this.connections = connections;
	}
	
	public LinkedList<Vertex> getConnections() {
		return this.connections;
	}

	public int getTag(){
		return this.tag;
	}
}

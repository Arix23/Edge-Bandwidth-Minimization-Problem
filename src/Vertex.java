import java.util.LinkedList;

public class Vertex {
	private LinkedList<Vertex> connections;

	public Vertex() {
		this.connections = new LinkedList<Vertex>();	
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
}

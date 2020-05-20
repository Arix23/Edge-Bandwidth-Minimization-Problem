import java.util.LinkedList;

public class Arista {
	private int valor;
	private LinkedList<Arista> aristasIncidentes;
	private ConjuntoNodo nodos;
	
	public Arista(int valor, int inicio, int nodoFinal) {
		this.valor = valor;
		this.nodos = new ConjuntoNodo(inicio, nodoFinal);
		this.aristasIncidentes = new LinkedList<Arista>();	
		
	}
	
	public int GetVertex() {
		return this.nodos.getNodo2();
	}
	
	public int GetInicial() {
		return this.nodos.getNodo1();
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public void addAristaIncidente(Arista arista) {
		this.aristasIncidentes.add(arista);
	}
	
	public LinkedList<Arista> getAristasIncidentes() {
		return this.aristasIncidentes;
	}
}

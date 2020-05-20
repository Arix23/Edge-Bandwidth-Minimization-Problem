import java.util.LinkedList;

public class Arista {
	private int valor;
	private LinkedList<Arista> aristasIncidentes;
	private int nodoInicial;
	private int nodoFinal;
	
	public Arista(int valor, int inicio, int nodoFinal) {
		this.valor = valor;
		this.nodoInicial = inicio;
		this.nodoFinal = nodoFinal;
		this.aristasIncidentes = new LinkedList<Arista>();	
		
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

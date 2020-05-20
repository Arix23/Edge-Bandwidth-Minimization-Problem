
public class ConjuntoNodo {
	private int nodo1;
	private int nodo2;
	public ConjuntoNodo(int nodo1, int nodo2) {
		this.nodo1 = nodo1;
		this.nodo2 = nodo2;
	}
	
	public boolean equals (final Object O) {
	    if (!(O instanceof ConjuntoNodo)) return false;
	    if (((ConjuntoNodo) O).nodo1 != nodo1) return false;
	    if (((ConjuntoNodo) O).nodo2 != nodo2) return false;
	    return true;
	  }

	  public int hashCode() {
	    return (nodo1 << 16) + nodo2;
	  }
	  
	  public int getNodo2() {
		  return this.nodo2;
	  }
	  
	  public int getNodo1() {
		  return this.nodo1;
	  }
}

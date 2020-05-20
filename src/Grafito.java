import java.util.*;

public class Grafito {

	public static void main(String[] args) {
		//PRIMERO CREAR VERTICES
		//DESPUES METER LAS CONEXIONES
		//LUEGO CREAR HASHTABLE DE VERTICES
		//EL ALGORITMO RECIBE LA HASHTABLE DE VERTICES
		
		Scanner sc = new Scanner(System.in);
		int numVertex = sc.nextInt();
		HashMap<Integer,Vertex> Graph = new HashMap<Integer,Vertex>();
		HashMap<ConjuntoNodo,Arista> aristasProblema = new HashMap<ConjuntoNodo, Arista>();

		//Se agregan las vertices al grafo y se les asigna una llave
		for(int i = 1; i <= numVertex; i++){
			Graph.put(i,new Vertex(i));
		}

		for(int i = 1; i <= numVertex; i++){
			//Se recibe el numero de conexiones, y se agregan a las vertices
			int numConnections = sc.nextInt();

			for(int j = 1; j <= numConnections; j++){
				int conVertex = sc.nextInt();
				Graph.get(j).addConnection(Graph.get(conVertex));
				if(aristasProblema.get(new ConjuntoNodo(j,conVertex))!=null || aristasProblema.get(new ConjuntoNodo(conVertex,j))!=null) {
					aristasProblema.put(new ConjuntoNodo(j,conVertex), new Arista(0,j,conVertex));
				} else {
					
				}
			}
		}
		sc.close();
		
		
		
		
		
		
	}
	
	/*public void randomTag() {
		
	}*/
	
	public void EBMPAlgorithm() {
		
	}

}

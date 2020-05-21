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
		HashMap<ConjuntoNodo, Arista> aristasProblema = new HashMap<ConjuntoNodo, Arista>();

		//Se agregan las vertices al grafo y se les asigna una llave
		for(int i = 1; i <= numVertex; i++){
			Graph.put(i,new Vertex(i));
		}

		for(int i = 1; i <= numVertex; i++){
			//Se recibe el numero de conexiones, y se agregan a las vertices
			int numConnections = sc.nextInt();

			for(int j = 1; j <= numConnections; j++){
				int conVertex = sc.nextInt();
				Graph.get(i).addConnection(Graph.get(conVertex));
				if(aristasProblema.get(new ConjuntoNodo(i,conVertex))==null && aristasProblema.get(new ConjuntoNodo(conVertex,i))==null) {
					aristasProblema.put(new ConjuntoNodo(i,conVertex), new Arista(0,i,conVertex));
				} else {

				}
			}
		}

		sc.close();

		//INCIDENCIA
		for (Arista value : aristasProblema.values()) {
			Vertex finalVertex = Graph.get(value.GetVertex());
		    for(int i = 0; i<finalVertex.getConnections().size();i++) {
		    	if(finalVertex.getConnections().get(i).getTag()!=value.GetInicial()) {
		    		value.addAristaIncidente(aristasProblema.get(new ConjuntoNodo(value.GetVertex(),finalVertex.getConnections().get(i).getTag())));
		    	}
		    }
		}

		/*
		 4
		 1 2	
		 2 1 3
		 2 2 4
		 1 3
		 */

		//Valor random de vertices
		LinkedList<Integer> intList = createList(aristasProblema.size());
		//SE CREA POBLACION INICIAL
		HashMap<String,Solucion> poblacionInicial = CrearPoblacionInicial(intList, aristasProblema);
		
		
		LinkedList<Integer> LA = RandomTag(intList);
		LinkedList<Integer> LB = RandomTag(intList);

		Combinar(LA, LB);
	}
	
	public static HashMap<String,Solucion> CrearPoblacionInicial(LinkedList<Integer> inicial, HashMap<ConjuntoNodo, Arista> aristas){
		HashMap<String,Solucion> poblacion = new HashMap<String,Solucion>();
		for(int i = 0;i<3;i++) {
			LinkedList<Integer> temp = RandomTag(inicial);
			int count = 0;
			for (Arista value : aristas.values()) {
				value.setValor(temp.get(count));
				count++;
			}
			System.out.println(calculateBandwidth(aristas));
			if(!poblacion.containsKey(temp.toString())) {
				poblacion.put(temp.toString(), new Solucion(temp,calculateBandwidth(aristas)));
			}
		}
		return poblacion;
	}

	public static LinkedList<Integer> RandomTag(LinkedList<Integer> intList) {
		LinkedList<Integer> shuffled = (LinkedList<Integer>) intList.clone();
		Collections.shuffle(shuffled);
		return shuffled;
	}

	public static LinkedList<Integer> createList(int size) {
		LinkedList<Integer> intList = new LinkedList<Integer>();
		for(int i = 0; i < size; i ++) {
			intList.add(i + 1);
		}
		return intList;
	}	

	public static int calculateBandwidth(HashMap<ConjuntoNodo, Arista> aristas) {
		//SE PUEDE OPTIMIZAR
		int max = 0;
		for (Arista value : aristas.values()) {
			for(int i = 0;i<value.getAristasIncidentes().size();i++) {
				if(value.getAristasIncidentes().get(i)!=null) {
					int temp = (Math.abs(value.getValor()-value.getAristasIncidentes().get(i).getValor()));
					if(temp>max) {
						max = temp;
					}
				}
			}
		}
		return max;

	}

	public void EBMPAlgorithm() {

	}

	public static LinkedList<Integer> Combinar(LinkedList<Integer> listaA, LinkedList<Integer> listaB){
		if(listaA.getLast() == listaB.getLast()) {
			
		}
		return listaA;
	}

}

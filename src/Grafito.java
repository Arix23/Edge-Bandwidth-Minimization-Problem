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
		    
		    finalVertex = Graph.get(value.GetInicial());
		    for(int i = 0; i<finalVertex.getConnections().size();i++) {
		    	if(finalVertex.getConnections().get(i).getTag()!=value.GetVertex()) {
		    		if((aristasProblema.get(new ConjuntoNodo(finalVertex.getConnections().get(i).getTag(),value.GetInicial())) != null)) {
		    			value.addAristaIncidente(aristasProblema.get(new ConjuntoNodo(finalVertex.getConnections().get(i).getTag(),value.GetInicial())));
		    		} else {
		    			value.addAristaIncidente(aristasProblema.get(new ConjuntoNodo(value.GetInicial(),finalVertex.getConnections().get(i).getTag())));
		    		}
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
		HashMap<String,Solucion> segundaPoblacion = new HashMap<String, Solucion>();
		
		//optimizable usando un for que una ambas
		HashMap<String,Solucion> terceraPoblacion = new HashMap<String, Solucion>();
		terceraPoblacion.putAll(poblacionInicial);
		terceraPoblacion.putAll(segundaPoblacion);

		int iter = 0;
		int x = 5; //Variable futura de Ari

		while (iter < 10 || x > 0) {
			for (Solucion value : poblacionInicial.values()) {
				LinkedList<Integer> temp = new LinkedList<Integer>();
				if(value.getBandwidth()>value.minBandwidth) {
					temp = value.getSolucion();
					//INTERCAMBIAR UN VALOR RANDOM
					Mutacion(temp);
					value.setSolucion(temp);
					int count = 0;
					for (Arista arista : aristasProblema.values()) {
						arista.setValor(temp.get(count));
						count++;
					}
					segundaPoblacion.put(temp.toString(), new Solucion(temp,calculateBandwidth(aristasProblema)));
				}
			}
			
			
			int min = poblacionInicial.get(1).getBandwidth();

			for(int i = 1; i <= terceraPoblacion.size(); i++){
				if(poblacionInicial.get(i).getBandwidth() <= min ){
					min = poblacionInicial.get(i).getBandwidth();
				} 
			}

			iter++;
			x--;
		}
		
		int minimo = 1000000;
		Solucion solucionBuena = new Solucion();
		
		for (Solucion value : poblacionInicial.values()) {
			if(value.getBandwidth()<minimo) {
				solucionBuena=value;
			}
		}
		
		for (Solucion value : segundaPoblacion.values()) {
			if(value.getBandwidth()<minimo) {
				solucionBuena=value;
			}
		}
		
		//LinkedList<Integer> = new LinkedList<Integer>();
		
		
		
		System.out.println("La solucion encontrada tiene una bandwidth de: " + solucionBuena.getBandwidth());
		System.out.println("Se le asigna los siguientes valores a cada arista: ");
		int count = 0;
		for(Arista value : aristasProblema.values()) {
			System.out.println("Arista " + value.GetInicial() + "-" + value.GetVertex() + " Con valor: " + solucionBuena.getSolucion().get(count));
			count++;
		}
		
	}

	public static HashMap<String,Solucion> CrearPoblacionInicial(LinkedList<Integer> inicial, HashMap<ConjuntoNodo, Arista> aristas){
		HashMap<String,Solucion> poblacion = new HashMap<String,Solucion>();
		int min = 10000000;
		for(int i = 0;i<100;i++) {
			LinkedList<Integer> temp = RandomTag(inicial);
			int count = 0;
			for (Arista value : aristas.values()) {
				value.setValor(temp.get(count));
				count++;
			}
			if(!poblacion.containsKey(temp.toString())) {
				int bandwidth = calculateBandwidth(aristas);
				if(bandwidth<min) {
					poblacion.put(temp.toString(), new Solucion(temp,bandwidth,bandwidth));
				} else {
					poblacion.put(temp.toString(), new Solucion(temp,bandwidth));
				}

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
	
	public static void Mutacion(LinkedList<Integer> temp) {
		//System.out.println(temp.toString());
		Random rnd = new Random();
		int random1 = rnd.nextInt(temp.size());
		int random2 = rnd.nextInt(temp.size());
		
		while(random1 == random2) {
			random2 = rnd.nextInt(temp.size());
		}
		
		int ran2val = temp.get(random2);
		temp.set(random2, temp.get(random1));
		temp.set(random1, ran2val);
		//System.out.println(temp.toString());
	}

	public void EBMPAlgorithm() {

	}

}

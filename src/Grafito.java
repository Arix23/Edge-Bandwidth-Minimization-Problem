import java.util.*;

public class Grafito {

	public static void main(String[] args) {
		//PRIMERO CREAR VERTICES
		//DESPUES METER LAS CONEXIONES
		//LUEGO CREAR HASHTABLE DE VERTICES
		//EL ALGORITMO RECIBE LA HASHTABLE DE VERTICES
		
		
		//C�DIGO PARA LA ENTRADA
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
				
				//SE CREAN LAS ARISTAS; EVITANDO REPETIDOS
				if(aristasProblema.get(new ConjuntoNodo(i,conVertex))==null && aristasProblema.get(new ConjuntoNodo(conVertex,i))==null) {
					aristasProblema.put(new ConjuntoNodo(i,conVertex), new Arista(0,i,conVertex));
				} else {

				}
			}
		}

		sc.close();
		
		
		//INCIDENCIA DE ARISTAS
		for (Arista value : aristasProblema.values()) {
			Vertex finalVertex = Graph.get(value.GetVertex());
		    for(int i = 0; i<finalVertex.getConnections().size();i++) {
		    	if(finalVertex.getConnections().get(i).getTag()!=value.GetInicial()) {
		    		value.addAristaIncidente(aristasProblema.get(new ConjuntoNodo(value.GetVertex(),finalVertex.getConnections().get(i).getTag())));
		    	}
		    }
		    
		    
		    // SE PUEDE OPTIMIZAR
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

		
		//TEST CASE
		
		/*
		 4
		 1 2
		 2 1 3
		 2 2 4
		 1 3
		 */

		//Valor random de vertices
		LinkedList<Integer> intList = createList(aristasProblema.size());

		//SE CREA POBLACION INICIAL Y LA POBLACION EN LA QUE SE REALIZA LAS MUTACIONES
		HashMap<String,Solucion> poblacionInicial = CrearPoblacionInicial(intList, aristasProblema);
		HashMap<String,Solucion> segundaPoblacion = new HashMap<String, Solucion>();

		int iter = 0;
		int x = 10; //Variable futura de Ari

		
		//CICLO DONDE SE REALIZAN MUTACIONES DE LAS POBLACIONES Y SE ESCOGE LAS MEJORES
		//ALGORITMO PRINCIPAL METAHEURISTICO
		while (iter < 10 || x > 0) {
			//optimizable usando un for que una ambas
			HashMap<String,Solucion> terceraPoblacion = new HashMap<String, Solucion>();
			terceraPoblacion.putAll(poblacionInicial);
			terceraPoblacion.putAll(segundaPoblacion);

			Integer[] minimoArray = new Integer[terceraPoblacion.size()];

			for (Solucion value : poblacionInicial.values()) {
				LinkedList<Integer> temp = new LinkedList<Integer>();
				if(value.getBandwidth()>value.minBandwidth) {
					temp = value.getSolucion();
					//INTERCAMBIAR UN VALOR RANDOM
					intercambiar(temp);
					value.setSolucion(temp);
					int count = 0;
					for (Arista arista : aristasProblema.values()) {
						arista.setValor(temp.get(count));
						count++;
					}
					segundaPoblacion.put(temp.toString(), new Solucion(temp,calculateBandwidth(aristasProblema)));
				}
			}
			
			poblacionInicial.clear();

			for(int i = 1; i <= terceraPoblacion.size(); i++){
				minimoArray[i-1] = poblacionInicial.get(i).getBandwidth();
			}

			Arrays.sort(minimoArray, Collections.reverseOrder());

			int mitad = (int) Math.ceil((terceraPoblacion.size())/2);
			int i = 1;

			while(mitad>0){
				if(terceraPoblacion.get(i).getBandwidth() == minimoArray[i-1]){
					terceraPoblacion.remove(i);
					mitad --;
					i++;
				}
			}

			poblacionInicial = terceraPoblacion;

			iter++;
			x--;
		}
		
		//SE OBTIENE LA MEJOR SOLUCI�N POSIBLE DE LAS POBLACIONES OBTENIDAS DEL CICLO
		
		int minimo = 1000000;
		Solucion solucionBuena = new Solucion();
		
		for (Solucion value : poblacionInicial.values()) {
			if(value.getBandwidth()<minimo) {
				solucionBuena=value;
			}
		}
		
		//SE IMPRIME A PANTALLA LA SOLUCION ENCONTRADA CON LAS ARISTAS Y SUS VALORES DADOS
		
		System.out.println("La solucion encontrada tiene una bandwidth de: " + solucionBuena.getBandwidth());
		System.out.println("Se le asigna los siguientes valores a cada arista: ");
		int count = 0;
		for(Arista value : aristasProblema.values()) {
			System.out.println("Arista " + value.GetInicial() + "-" + value.GetVertex() + " Con valor: " + solucionBuena.getSolucion().get(count));
			count++;
		}
		
		
		
	}

	
	//FUNCION PARA CREAR LA POBLACION INICIAL
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

	//FUNCION PARA RANDOMIZAR LA LISTA
	public static LinkedList<Integer> RandomTag(LinkedList<Integer> intList) {
		LinkedList<Integer> shuffled = (LinkedList<Integer>) intList.clone();
		Collections.shuffle(shuffled);
		return shuffled;
	}

	
	//FUNCION PARA CREAR LA LISTA SIN RANDOMIZAR
	public static LinkedList<Integer> createList(int size) {
		LinkedList<Integer> intList = new LinkedList<Integer>();
		for(int i = 0; i < size; i ++) {
			intList.add(i + 1);
		}
		return intList;
	}

	
	//FUNCION PARA CALCULAR LA BANDA ANCHA DEL GRAFO
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
	
	
	//FUNCION PARA REALIZAR INTERCAMBIOS ENTRE VALORES DE LA SOLUCION
	public static void intercambiar(LinkedList<Integer> temp) {
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

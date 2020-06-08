import java.util.*;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

public class Grafito {
	public static int tamanoPoblacion,
					  numGeneraciones;
	public static double probMutar;
	public static LinkedList<Integer>[] llaves;
	public static void main(String[] args) {
		//PRIMERO CREAR VERTICES
		//DESPUES METER LAS CONEXIONES
		//LUEGO CREAR HASHTABLE DE VERTICES
		//EL ALGORITMO RECIBE LA HASHTABLE DE VERTICES
		//CODIGO PARA LA ENTRADA
		long startTime = System.nanoTime();
		HashMap<Integer,Vertex> Graph = new HashMap<Integer,Vertex>();
		HashMap<ConjuntoNodo, Arista> aristasProblema = new HashMap<ConjuntoNodo, Arista>();

		try{
			Scanner sc = new Scanner(System.in);
			int numVertex = sc.nextInt();
			
			validVertextNumber(numVertex);

			//Se agregan las vertices al grafo y se les asigna una llave
			for(int i = 1; i <= numVertex; i++){
				Graph.put(i,new Vertex(i));
			}
	
			for(int i = 1; i <= numVertex; i++){
				//Se recibe el numero de conexiones, y se agregan a las vertices
				int numConnections = sc.nextInt();
	
				for(int j = 1; j <= numConnections; j++){
					int conVertex = sc.nextInt();
					isPositive(conVertex);
					Graph.get(i).addConnection(Graph.get(conVertex));
	
					//SE CREAN LAS ARISTAS; EVITANDO REPETIDOS
					if(aristasProblema.get(new ConjuntoNodo(i,conVertex))==null && aristasProblema.get(new ConjuntoNodo(conVertex,i))==null) {
						aristasProblema.put(new ConjuntoNodo(i,conVertex), new Arista(0,i,conVertex));
					} else {
	
					}
				}
			}
	
			tamanoPoblacion = sc.nextInt();
			validPopulationSize(tamanoPoblacion);
			numGeneraciones = sc.nextInt(); 
			validGenerationNumber(numGeneraciones);
			probMutar = sc.nextDouble();
			validProbability(probMutar);
	
			sc.close();
		}catch(IllegalArgumentException | InputMismatchException | NullPointerException ex){
			//Se imprime el mensaje de la excepcion

			if(ex.getClass().getSimpleName().equals("NullPointerException"))
				System.out.println("Datos insuficientes");
			else if(ex.getMessage() == null)
				System.out.println("Entrada no aceptada");
			else
				System.out.println(ex.getMessage()); 

			System.exit(1);
		}

		//INCIDENCIA DE ARISTAS
		for (Arista value : aristasProblema.values()) {
			Vertex finalVertex = Graph.get(value.GetVertex());
		    for(int i = 0; i<finalVertex.getConnections().size();i++) {
		    	if(finalVertex.getConnections().get(i).getTag()!=value.GetInicial()) {
		    		if(aristasProblema.get(new ConjuntoNodo(value.GetVertex(),finalVertex.getConnections().get(i).getTag())) != null){
		    			value.addAristaIncidente(aristasProblema.get(new ConjuntoNodo(value.GetVertex(),finalVertex.getConnections().get(i).getTag())));
		    		} else {
		    			value.addAristaIncidente(aristasProblema.get(new ConjuntoNodo(finalVertex.getConnections().get(i).getTag(),value.GetVertex())));
		    		}
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

		//Valor random de vertices
		LinkedList<Integer> intList = createList(aristasProblema.size());

		//SE CREA POBLACION INICIAL Y LA POBLACION EN LA QUE SE REALIZA LAS MUTACIONES
		HashMap<String,Solucion> poblacionInicial = CrearPoblacionInicial(intList, aristasProblema);
		HashMap<String,Solucion> segundaPoblacion = new HashMap<String, Solucion>();
		
		
		int iter = 0;
		
		
		
		

		//CICLO DONDE SE REALIZAN MUTACIONES DE LAS POBLACIONES Y SE ESCOGE LAS MEJORES
		//ALGORITMO PRINCIPAL METAHEURISTICO
		LinkedList<Solucion> listaSoluciones = new LinkedList<Solucion>();
		while (iter < numGeneraciones) {
			//optimizable usando un for que una ambas
			HashMap<String,Solucion> terceraPoblacion = new HashMap<String, Solucion>();
			
			listaSoluciones = new LinkedList<Solucion>();
			
			//RECOMBINAR
			while(segundaPoblacion.size()<poblacionInicial.size()) {
				int firstParent = -1;
				int secondParent = -1;
				Random random = new Random();
				for(int i =0;i<2;i++) {
					int firstCompetitor = random.nextInt(poblacionInicial.size());
					int secondCompetitor = random.nextInt(poblacionInicial.size());
					while(secondCompetitor==firstCompetitor) {
						secondCompetitor = random.nextInt(poblacionInicial.size());
					}
					Solucion primerCompetidor = poblacionInicial.get(llaves[firstCompetitor].toString());
					Solucion segundoCompetidor = poblacionInicial.get(llaves[secondCompetitor].toString());
					if(primerCompetidor.getBandwidth()<=segundoCompetidor.getBandwidth()) {
						if(i==0) {
							firstParent = firstCompetitor;
						} else {
							secondParent = firstCompetitor;
						}
					} else {
						if(i==0) {
							firstParent = secondCompetitor;
						} else {
							secondParent = secondCompetitor;
						}
					}
				}
				
				if(firstParent==secondParent) {
					continue;
				} else {
					LinkedList<Integer> primerPadre = llaves[firstParent];
					LinkedList<Integer> segundoPadre = llaves[secondParent];
					LinkedList<Integer>[] hijos = PMX(primerPadre, segundoPadre);
					LinkedList<Integer> temp = hijos[0];
					int count = 0;
					for (Arista arista : aristasProblema.values()) {
						arista.setValor(temp.get(count));
						count++;
					}
					Solucion nuevaSolucion = new Solucion(temp,calculateBandwidth(aristasProblema));
					segundaPoblacion.put(nuevaSolucion.getSolucion().toString(), nuevaSolucion);
					
					
				    temp = hijos[1];
					count = 0;
					for (Arista arista : aristasProblema.values()) {
						arista.setValor(temp.get(count));
						count++;
					}
					nuevaSolucion = new Solucion(temp,calculateBandwidth(aristasProblema));
					segundaPoblacion.put(nuevaSolucion.getSolucion().toString(), nuevaSolucion);
					
				}
				
				
				
				
			}
			
			//MUTACION DE SEGUNDA POBLACION
			Solucion[] soluciones = (Solucion[]) segundaPoblacion.values().toArray(new Solucion[segundaPoblacion.size()]);
			segundaPoblacion.clear();
			for(int i = 0; i < soluciones.length; i++) {
				LinkedList<Integer> temp = soluciones[i].getSolucion();
				for(int j = 0; j < soluciones[i].getSolucion().size(); j++) {
					double probIntercambio = Math.random();
					if(probIntercambio <= probMutar) {
						temp = mutar(temp, j);
					}
				}
				int count = 0;
				for (Arista arista : aristasProblema.values()) {
					arista.setValor(temp.get(count));
					count++;
				}
				Solucion nuevaSolucion = new Solucion(temp,calculateBandwidth(aristasProblema));
				segundaPoblacion.put(nuevaSolucion.getSolucion().toString(), nuevaSolucion);
				listaSoluciones.add(nuevaSolucion);	
			}
			
			for (Solucion value : poblacionInicial.values()) {
				listaSoluciones.add(value);
				
			}
			
			
			/////////////////////////////Seleccion de los mejores resultados//////////////////////
			int randoms;
			if(tamanoPoblacion < 10){
				randoms = 1;
			}else{
				randoms = (int) Math.ceil((listaSoluciones.size())/10);
			}
			
			int mitad = (int) Math.ceil((listaSoluciones.size())/2) - randoms;
			
			Collections.sort(listaSoluciones);
			for(int i=0;i<=mitad;i++) {
				terceraPoblacion.put(listaSoluciones.get(i).getSolucion().toString(), listaSoluciones.get(i));
			}
			

			
			for(int i=0;i<randoms;i++){
				Random r = new Random();
				int index = r.nextInt(listaSoluciones.size()-mitad-1) + mitad;
				terceraPoblacion.put(listaSoluciones.get(index).getSolucion().toString(), listaSoluciones.get(index));
			}
			
		
			
			
			poblacionInicial = (HashMap<String, Solucion>) terceraPoblacion.clone();
			llaves = new LinkedList[poblacionInicial.size()];
			int count = 0;
			for (Solucion value : poblacionInicial.values()) {
				llaves[count] = value.getSolucion();
				count++;
			}

			iter++;
		}

		//SE OBTIENE LA MEJOR SOLUCION POSIBLE DE LAS POBLACIONES OBTENIDAS DEL CICLO
		
		int minimo = 1000000;
		Solucion solucionBuena = new Solucion();

		for (Solucion value : poblacionInicial.values()) {
			if(value.getBandwidth()<minimo) {
				minimo = value.getBandwidth();
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
		
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);



	}


	//FUNCION PARA CREAR LA POBLACION INICIAL
	public static HashMap<String,Solucion> CrearPoblacionInicial(LinkedList<Integer> inicial, HashMap<ConjuntoNodo, Arista> aristas){
		HashMap<String,Solucion> poblacion = new HashMap<String,Solucion>();
		int min = 10000000;
		for(int i = 0;i<tamanoPoblacion;i++) {
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
		llaves = new LinkedList[poblacion.size()];
		int count = 0;
		for (Solucion value : poblacion.values()) {
			llaves[count] = value.getSolucion();
			count++;
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
	public static LinkedList<Integer> mutar(LinkedList<Integer> temp, int i) {
		LinkedList<Integer> mutada = (LinkedList<Integer>) temp.clone();
        Random rnd = new Random();
        int index = i;
        int random2 = rnd.nextInt(temp.size());

        while(index == random2) {
            random2 = rnd.nextInt(temp.size());
        }

        int ran2val = temp.get(random2);
        mutada.set(random2, mutada.get(index));
        mutada.set(index, ran2val);
        //System.out.println(temp.toString());

        return mutada;
	}

	
	//PMX /////////////////////////////////////////////////////
	public static LinkedList<Integer>[] PMX(LinkedList<Integer> temp1, LinkedList<Integer> temp2) {
		LinkedList<Integer> parent1 = (LinkedList<Integer>) temp1.clone();
		LinkedList<Integer> parent2 = (LinkedList<Integer>) temp2.clone();
					
		Random rMin= new Random();
		Random rMax= new Random();
		int min, max;
		min = rMin.nextInt(temp1.size()-2);
		max = rMax.nextInt(temp1.size()-1);
		while(max<=min) {
			max = rMax.nextInt(temp1.size());
		}
		
		//create segments 
		int capacity_ofSegments = (max - min) + 1;
		int [] segment1 = new int[capacity_ofSegments];
        int [] segment2 = new int[capacity_ofSegments];
        int segment1and2Index = 0;
        for(int i = 0; i < temp1.size(); i++){
            if((i >= min) && (i <= max)){
               int x = parent1.get(i);  int y = parent2.get(i);
               segment1[segment1and2Index] = x;
               segment2[segment1and2Index] = y;
               segment1and2Index++;
            }
          }
        
        //crossOver
        LinkedList<Integer> offspring1 = parent1;
		LinkedList<Integer> offspring2 = parent2;
		
		// offspring2 gets segment 1, offspring1 gets segment2 //
		insert_Segments(offspring1, segment2, min, max);
		insert_Segments(offspring2, segment1, min, max);
		
		for(int i = 0; i < temp1.size(); i++){
            if((i < min) || (i > max)){
               offspring1.set(i, parent1.get(i));
            }
        }
        
        for(int i = 0; i < temp1.size(); i++){
            if((i < min) || (i > max)){
                while(check_forDuplicates(offspring1, i)){
                    sort_Duplicates(offspring1, i, segment1, segment2);
                }
            }
        }
        
        for(int i = 0; i < temp2.size(); i++){
            if((i < min) || (i > max)){
               offspring2.set(i, parent2.get(i));
            }
        }
        
        for(int i = 0; i < temp2.size(); i++){
            if((i < min) || (i > max)){
                while(check_forDuplicates(offspring2, i)){
                    sort_Duplicates(offspring2, i, segment1, segment2);
                }
            }
        }
		
        LinkedList<Integer>[] offsprings = new LinkedList[2];
        offsprings[0] = offspring1;
        offsprings[1] = offspring2;
        return offsprings;
	}
	
	private static void insert_Segments(LinkedList<Integer> offspring, int[] segment, int min, int max){
        int segmentIndex = 0;
        int size = offspring.size();
		for(int i = 0; i < size; i++){
           if((i >= min) && (i <= max)){
               offspring.set(i, segment[segmentIndex]);
               segmentIndex++;
           }
        }
    }
	
	// For an Element given by its i check that it doesn't appear twice //
	private static boolean check_forDuplicates(LinkedList<Integer> offspring, int indexOfElement){
       int size = offspring.size();
		for(int i = 0; i < size; i++){
            if((offspring.get(i) == offspring.get(indexOfElement)) && (indexOfElement != i) ){
                return true;
            }
        }
        return false;
    }
	
	// If Element is Duplicated, replace it by using its mapping //
    private static void sort_Duplicates(LinkedList<Integer> offspring, int indexOfElement, int[] segment1, int[] segment2){
        for(int i = 0; i < segment1.length; i++){
            if(segment1[i] == offspring.get(indexOfElement)){
                offspring.set(indexOfElement, segment2[i]);
            }
            else if(segment2[i] == offspring.get(indexOfElement)){
            	offspring.set(indexOfElement, segment1[i]);
            }
        }
    }

	//Validar que los numeros de objetos sean aceptados
	public static void validVertextNumber(int a){
		if(a <= 2)
			throw new IllegalArgumentException("El numero de vertices debe ser mayor a 2");
	}

	public static void validPopulationSize(int a){
		if(a <= 0)
			throw new IllegalArgumentException("El tamano de la poblacion debe ser mayor a 0");
	}

	public static void validGenerationNumber(int a){
		if(a <= 0)
			throw new IllegalArgumentException("El numero de generaciones debe ser mayor a 0");
	}

	public static void validProbability(double a){
		if(a <= 0)
			throw new IllegalArgumentException("La probabilidad de mutar debe ser positiva");
	}

	//Validar que sea positivo
	public static void isPositive(int a){
		if(a < 0)
			throw new IllegalArgumentException("Los datos deben ser positivos");
	}

	/////////////////////////////////////////////////////////////////////////
	
	public void EBMPAlgorithm() {

	}

}

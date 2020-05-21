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

		//Se agregan las vertices al grafo y se les asigna una llave
		for(int i = 1; i <= numVertex; i++){
			Graph.put(i,new Vertex(i));
		}

		for(int i = 1; i <= numVertex; i++){
			//Se recibe el numero de conexiones, y se agregan a las vertices
			int numConnections = sc.nextInt();

			for(int j = 0; j < numConnections; j++){
				Graph.get(j+1).addConnection(Graph.get(sc.nextInt()));
			}
		}

		sc.close();
		
		int oa = 1%4;
		System.out.println("siu: " + oa);
		//randomTag(Graph);
	}
	
	public static void randomTag(HashMap<Integer, Vertex> Graph) {
		int [] randomTag = new int[Graph.size()];
		boolean [] used = new boolean[Graph.size()];
		
		for(int i = 0; i < randomTag.length; i++ ) {
			Random rnd = new Random();
			int random = rnd.nextInt(randomTag.length);
			 if(!used[random]) {
				 System.out.println( "ra: " + random);
				 randomTag[i] = random;
				 used[random] = true;
			 }else {
				 System.out.println("rau: " + random + " - ao  " + (random + 1 % randomTag.length));
				/*while(used[random]) {
					 random %= randomTag.length;
				 }
				 
				 randomTag[random] = random;
				 used[random] = true;*/
			 }
		}
		
		System.out.println();
		for(int i = 0; i < randomTag.length; i++ ) {
			System.out.print(randomTag[i] + " ");
		}
	}
	
	public void EBMPAlgorithm() {
		
	}

}

import java.util.*;

public class Grafito {

	public static void main(String[] args) {
		//PRIMERO CREAR VERTICES
		//DESPUES METER LAS CONEXIONES
		//LUEGO CREAR HASHTABLE DE VERTICES
		//EL ALGORITMO RECIBE LA HASHTABLE DE VERTICES
		
		Scanner sc = new Scanner(System.in);
		int numVertex = sc.nextInt();
		HashMap Graph = new HashMap();

		for(int i = 1; i <= numVertex; i++){
			Graph.put(i,new Vertex(i));
		}

		sc.close();

	}
	
	public void EBMPAlgorithm() {
		
	}

}

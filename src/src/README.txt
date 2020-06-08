Hecho por:
Ari Valenzuela
Nataly Hernández
Héctor Álvarez

Esto es una implementación de un algortimo de solución al Problema de Bandwith Mínimo,
que consiste en las siguientes clases:

-Grafito, clase principal donde se soluciona el problema.
-Vertex, clase que define al objeto tipo vertex que representa los nodos del grafo.
-Arista, clase que define al objeto arista que representa las conexiones del grafo.
-ConjuntoNodo, clase usada para definir conjuntos de nodos que están unidos por una arista.
-Solución, clase usada para representar las soluciones n propuestas para resolver el algoritmo.

Grafito es nuestra clase principal

El algoritmo recibe el siguiente formato de entrada, en formatos .txt, desde consola:
	-Número de vértices / nodos.
	-Una serie de listas que definen las conexiones de cada nodo, con el siguiente formato:
		-Número de conexiones del nodo.
		-Nodos a los que está conectado*
		*(Aunque ya este espcíficada una conexión en un nodo anterior, se deben ingresar TODAS las conexiones.)
		-Tamaño de Población
		-Número de Generaciones
		-Probabilidad de mutar

Un ejemplo de entrada válida sería el siguiente:

Un archivo .txt que contenga:
3
2 2 3
2 1 3
2 1 2
5
5
0.5

Donde se muestra un grafo de tres vértices, en el cual todos los nodos están interconectados entre sí.

Una población de 5, 5 generaciones y una probablidad de 50% de mutar.

Para correr los TestCases desde consola es necesario que estos se encuentren en la misma carpeta que el algoritmo
y que se este se haya compilado previamente, utilizando "javac Grafito.java".

Una vez compilado, solo se debe escribir:
 "java Grafito < test01.txt"  en consola para ejecutarlo.


Esta carpeta contiene además la carpeta con los tests ordenados siguiendo su formato
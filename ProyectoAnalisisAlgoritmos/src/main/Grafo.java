package main;
import java.util.ArrayList; // Importa ArrayList para usar listas dinámicas
import java.util.Collections; // Importa Collections para operaciones de colección
import java.util.List; // Importa List para usar la interfaz de listas
import java.util.Random; // Importa Random para generar números aleatorios

public class Grafo {
    private int[][] capacidad; // Matriz que almacena las capacidades de las aristas
    private int numVertices; // Número de vértices en el grafo
    // Constructor original que genera aristas aleatorias
    public Grafo(int vertices, int arcos) {
        numVertices = vertices; // Asigna el número de vértices
        capacidad = new int[vertices][vertices]; // Inicializa la matriz de capacidad
        Random rand = new Random(); // Crea un objeto Random para generar números aleatorios
        List<int[]> posiblesAristas = new ArrayList<>(); // Lista para almacenar posibles aristas
        // Generar todas las posibles aristas válidas
        for (int from = 0; from < vertices; from++) { // Itera sobre los vértices de origen
            for (int to = 0; to < vertices; to++) { // Itera sobre los vértices de destino
                if (from != to) { // Asegura que no se crea un lazo (arista de un vértice a sí mismo)
                    posiblesAristas.add(new int[]{from, to}); // Añade la arista a la lista
                }
            }
        }
        // Barajar las posibles aristas
        Collections.shuffle(posiblesAristas, rand); // Baraja la lista de aristas aleatorias
        // Seleccionar las primeras 'arcos' aristas
        for (int i = 0; i < arcos && i < posiblesAristas.size(); i++) { // Itera sobre el número de aristas a añadir
            int[] arista = posiblesAristas.get(i); // Obtiene la arista en la posición i
            int from = arista[0]; // Extrae el vértice de origen
            int to = arista[1]; // Extrae el vértice de destino
            int peso = 20 + rand.nextInt(681); // Genera un peso aleatorio entre 20 y 700
            capacidad[from][to] = peso; // Asigna el peso a la matriz de capacidad
        }
    }
    // Nuevo constructor que acepta una matriz de capacidades
    public Grafo(int[][] capacidad) {
        this.numVertices = capacidad.length; // Asigna el número de vértices a partir de la matriz
        this.capacidad = capacidad; // Asigna la matriz de capacidad
    }
    public int getV() {
        return numVertices; // Retorna el número de vértices
    }
    public int[][] getCapacity() {
        return capacidad; // Retorna la matriz de capacidad
    }
    public void printGraph() {
        System.out.println("Matriz de capacidad del grafo:"); // Imprime un encabezado
        for (int i = 0; i < numVertices; i++) { // Itera sobre cada fila de la matriz
            for (int j = 0; j < numVertices; j++) { // Itera sobre cada columna de la matriz
                System.out.print(capacidad[i][j] + "\t"); // Imprime el valor de la capacidad
            }
            System.out.println(); // Imprime una nueva línea al final de cada fila
        }
    }
}
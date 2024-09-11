package main;

import java.util.Random;
public class Grafo {
    private int V; // Número de vértices en el grafo
    private int[][] capacity; // Capacidad de cada arco

    // Constructor que genera un grafo aleatorio
    public Grafo(int vertices, int edges) {
        this.V = vertices;
        this.capacity = new int[vertices][vertices];
        Random rand = new Random();

        // Conectar un camino directo de la fuente al sumidero para garantizar conectividad
        for (int i = 0; i < vertices - 1; i++) {
            int cap = rand.nextInt(20) + 1; // Capacidad aleatoria entre 1 y 20
            capacity[i][i + 1] = cap;
        }

        // Generar otros arcos aleatorios con capacidades aleatorias
        for (int i = 0; i < edges - (vertices - 1); i++) { // Ajuste en el número de arcos
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u != v && capacity[u][v] == 0) { // Evitar bucles y arcos duplicados
                int cap = rand.nextInt(20) + 1;
                capacity[u][v] = cap;
            }
        }
    }

    // Getter para el número de vértices
    public int getV() {
        return V;
    }

    // Getter para la capacidad del grafo
    public int[][] getCapacity() {
        return capacity;
    }

    // Función para imprimir el grafo
    public void printGraph() {
        System.out.println("Matriz de capacidades del grafo:");
        for (int i = 0; i < V; i++) {            
            for (int j = 0; j < V; j++) {
                System.out.print(capacity[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
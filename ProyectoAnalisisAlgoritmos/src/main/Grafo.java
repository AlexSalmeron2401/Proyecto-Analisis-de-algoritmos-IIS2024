package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Grafo {
    private int[][] capacidad;
    private int numVertices;

    // Constructor original que genera aristas aleatorias
    public Grafo(int vertices, int arcos) {
        numVertices = vertices;
        capacidad = new int[vertices][vertices];
        Random rand = new Random();
        List<int[]> posiblesAristas = new ArrayList<>();

        // Generar todas las posibles aristas válidas
        for (int from = 0; from < vertices; from++) {
            for (int to = 0; to < vertices; to++) {
                if (from != to) {
                    posiblesAristas.add(new int[]{from, to});
                }
            }
        }

        // Barajar las posibles aristas
        Collections.shuffle(posiblesAristas, rand);

        // Seleccionar las primeras 'arcos' aristas
        for (int i = 0; i < arcos && i < posiblesAristas.size(); i++) {
            int[] arista = posiblesAristas.get(i);
            int from = arista[0];
            int to = arista[1];
            int peso = 20 + rand.nextInt(681); // Peso entre 20 y 700
            capacidad[from][to] = peso;
        }
    }

    // Nuevo constructor que acepta una matriz de capacidades
    public Grafo(int[][] capacidad) {
        this.numVertices = capacidad.length;
        this.capacidad = capacidad;
    }

    public int getV() {
        return numVertices;
    }

    public int[][] getCapacity() {
        return capacidad;
    }

    public void printGraph() {
        System.out.println("Matriz de capacidad del grafo:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.print(capacidad[i][j] + "\t");
            }
            System.out.println();
        }
    }
}


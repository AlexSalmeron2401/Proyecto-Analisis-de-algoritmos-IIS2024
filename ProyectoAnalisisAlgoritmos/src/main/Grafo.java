package main;

import java.util.Random;

class Grafo {
    private int[][] capacidad;
    private int numVertices;
    private static final int MIN_PESO = 20;
    private static final int MAX_PESO = 700;

    public Grafo(int numVertices, int numArcos) {
        this.numVertices = numVertices;
        capacidad = new int[numVertices][numVertices];
        generarPesosAleatorios(numArcos);
    }

    private void generarPesosAleatorios(int numArcos) {
        Random rand = new Random();
        int arcosGenerados = 0;
        while (arcosGenerados < numArcos) {
            int i = rand.nextInt(numVertices);
            int j = rand.nextInt(numVertices);
            if (i != j && capacidad[i][j] == 0) { // Evitar bucles y arcos duplicados
                capacidad[i][j] = rand.nextInt(MAX_PESO - MIN_PESO + 1) + MIN_PESO;
                arcosGenerados++;
            }
        }
    }

    public int[][] getCapacidad() {
        return capacidad;
    }

    public int getNumVertices() {
        return numVertices;
    }
}
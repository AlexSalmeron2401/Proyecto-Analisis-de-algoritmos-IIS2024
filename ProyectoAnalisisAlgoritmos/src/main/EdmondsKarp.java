package main;

import java.util.LinkedList;
import java.util.Queue;

class EdmondsKarp {
    private int[][] capacidad;
    private int[][] flujo;
    private int[] padre;
    private boolean[] visitado;
    private int numVertices;

    public EdmondsKarp(Grafo grafo) {
        this.capacidad = grafo.getCapacidad();
        this.numVertices = grafo.getNumVertices();
        this.flujo = new int[numVertices][numVertices];
        this.padre = new int[numVertices];
        this.visitado = new boolean[numVertices];
    }

    public boolean bfs(int fuente, int sumidero) {
        Queue<Integer> cola = new LinkedList<>();
        cola.add(fuente);
        for (int i = 0; i < numVertices; i++) {
            visitado[i] = false;
        }
        visitado[fuente] = true;
        padre[fuente] = -1;

        while (!cola.isEmpty()) {
            int u = cola.poll();
            for (int v = 0; v < numVertices; v++) {
                if (!visitado[v] && capacidad[u][v] - flujo[u][v] > 0) {
                    cola.add(v);
                    padre[v] = u;
                    visitado[v] = true;
                    if (v == sumidero) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int edmondsKarp(int fuente, int sumidero) {
        int maxFlujo = 0;
        while (bfs(fuente, sumidero)) {
            int caminoFlujo = Integer.MAX_VALUE;
            for (int v = sumidero; v != fuente; v = padre[v]) {
                int u = padre[v];
                caminoFlujo = Math.min(caminoFlujo, capacidad[u][v] - flujo[u][v]);
            }
            for (int v = sumidero; v != fuente; v = padre[v]) {
                int u = padre[v];
                flujo[u][v] += caminoFlujo;
                flujo[v][u] -= caminoFlujo;
            }
            maxFlujo += caminoFlujo;
        }
        return maxFlujo;
    }
}
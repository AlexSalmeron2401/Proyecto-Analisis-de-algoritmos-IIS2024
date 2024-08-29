package main;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {
    private int comparisons; // Contador de comparaciones
    private int assignments; // Contador de asignaciones

    public FordFulkerson() {
        this.comparisons = 0;
        this.assignments = 0;
    }

    // BFS que busca un camino aumentante
    private boolean bfs(int[][] residualGraph, int source, int sink, int[] parent, int V) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;
        assignments += 3; // Asignaciones iniciales

        while (!queue.isEmpty()) {
            int u = queue.poll();
            assignments++; // Asignación

            for (int v = 0; v < V; v++) {
                comparisons++; // Comparación
                if (!visited[v] && residualGraph[u][v] > 0) {
                    comparisons += 2; // Comparación dentro del if
                    parent[v] = u;
                    assignments += 2; // Asignaciones
                    if (v == sink) {
                        return true;
                    }
                    queue.add(v);
                    visited[v] = true;
                    assignments += 2; // Asignaciones
                }
            }
        }
        return false;
    }

    // Función principal para encontrar el flujo máximo
    public int fordFulkerson(Grafo graph, int source, int sink) {
        int V = graph.getV();
        int[][] capacity = graph.getCapacity();
        int[][] residualGraph = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                residualGraph[i][j] = capacity[i][j];
                assignments++; // Asignación
            }
        }

        int[] parent = new int[V];
        int maxFlow = 0;
        assignments += 2; // Asignaciones iniciales

        // Aumentar el flujo mientras haya un camino aumentante
        while (bfs(residualGraph, source, sink, parent, V)) {
            int pathFlow = Integer.MAX_VALUE;
            assignments++; // Asignación

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                comparisons += 2; // Comparaciones en el bucle for
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                assignments++; // Asignación
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
                assignments += 2; // Asignaciones
            }

            maxFlow += pathFlow;
            assignments++; // Asignación
        }

        return maxFlow;
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getAssignments() {
        return assignments;
    }
}
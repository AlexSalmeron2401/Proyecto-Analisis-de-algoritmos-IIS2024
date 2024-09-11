package main;
import java.util.Arrays;

public class FordFulkerson {
    private int comparisons; // Contador de comparaciones
    private int assignments; // Contador de asignaciones

    public FordFulkerson() {
        this.comparisons = 0;
        this.assignments = 0;
    }

    // DFS que busca un camino aumentante en el grafo residual
    private boolean dfs(int[][] residualGraph, int source, int sink, int[] parent, boolean[] visited, int V) {
        // Marcar el vértice actual como visitado
        visited[source] = true;
        assignments++; // Asignación de visited[source]

        // Si llegamos al sumidero, retornamos true (encontramos un camino)
        if (source == sink) {
            comparisons++; // Comparación
            return true;
        }

        // Explorar todos los vértices adyacentes a source
        for (int v = 0; v < V; v++) {
            comparisons++; // Comparación en el bucle for
            // Si no está visitado y hay capacidad positiva en el arco residual
            if (!visited[v] && residualGraph[source][v] > 0) {
                comparisons += 2; // Comparaciones en el if
                parent[v] = source;
                assignments += 2; // Asignación de parent y visited
                if (dfs(residualGraph, v, sink, parent, visited, V)) {
                    return true;
                }
            }
        }

        // No se encontró un camino aumentante
        return false;
    }

    // Función principal para encontrar el flujo máximo
    public int fordFulkerson(int[][] graph, int source, int sink, int V) {
        int[][] residualGraph = new int[V][V]; // Grafo residual
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                residualGraph[i][j] = graph[i][j];
                assignments++; // Asignación
            }
        }

        int[] parent = new int[V]; // Para almacenar el camino aumentante
        boolean[] visited = new boolean[V]; // Para marcar vértices visitados
        int maxFlow = 0; // Inicializar el flujo máximo
        assignments += 3; // Asignación de parent, visited y maxFlow

        // Mientras haya un camino aumentante, incrementar el flujo máximo
        while (true) {
            Arrays.fill(visited, false); // Reiniciar los vértices visitados
            assignments++; // Asignación
            if (!dfs(residualGraph, source, sink, parent, visited, V)) {
                comparisons++; // Comparación del while
                break; // Si no se encuentra un camino aumentante, salimos del bucle
            }

            // Encontrar la capacidad mínima en el camino aumentante encontrado por DFS
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                comparisons += 2; // Comparaciones en el bucle for
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                assignments++; // Asignación
            }

            // Actualizar las capacidades residuales del grafo
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
                assignments += 2; // Asignaciones
            }

            maxFlow += pathFlow; // Sumar el flujo del camino al flujo total
            assignments++; // Asignación de maxFlow
        }

        return maxFlow;
    }

    // Getters para comparaciones y asignaciones
    public int getComparisons() {
        return comparisons;
    }

    public int getAssignments() {
        return assignments;
    }
    public void resetCounters() {
        this.comparisons = 0;
        this.assignments = 0;
    }
}
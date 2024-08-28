import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarp {

    // Método principal para ejecutar el algoritmo Edmonds-Karp
    public static int edmondsKarp(int[][] capacity, int source, int sink) {
        int n = capacity.length;  // Número de nodos en el grafo
        int[][] residualCapacity = new int[n][n]; // Capacidad residual de la red
        int[] parent = new int[n];  // Para almacenar el camino aumentante
        int maxFlow = 0;  // Flujo máximo inicial

        // Inicializar la capacidad residual como la capacidad original
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        // Mientras haya un camino aumentante desde la fuente al sumidero
        while (bfs(residualCapacity, source, sink, parent)) {
            // Encuentra la capacidad mínima en el camino aumentante encontrado
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
            }

            // Actualiza las capacidades residuales del grafo
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualCapacity[u][v] -= pathFlow;
                residualCapacity[v][u] += pathFlow;
            }

            // Suma la capacidad del camino aumentante al flujo máximo
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    // BFS para encontrar el camino aumentante
    private static boolean bfs(int[][] residualCapacity, int source, int sink, int[] parent) {
        int n = residualCapacity.length;
        boolean[] visited = new boolean[n];  // Para rastrear los nodos visitados
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        // Mientras la cola no esté vacía
        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                // Si no ha sido visitado y hay capacidad residual
                if (!visited[v] && residualCapacity[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;

                    // Si alcanzamos el sumidero, termina la búsqueda
                    if (v == sink) {
                        return true;
                    }
                }
            }
        }

        return false;  // No se encontró un camino aumentante
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        // Grafo de ejemplo
        int[][] capacity = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };

        System.out.println("El flujo máximo es: " + edmondsKarp(capacity, 0, 5));
    }
}
//Ejemplo de un codigo  Edmonds-Karp
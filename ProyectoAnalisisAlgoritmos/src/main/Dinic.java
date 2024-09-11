package main;
import java.util.*;

public class Dinic {
    int INF = Integer.MAX_VALUE;
    int nodes;
    List<List<Integer>> adj;
    int[][] capacity;
    int[] level;
    int[] ptr;
    int source, sink;
    int comparisons = 0;
    int assignments = 0;

    public Dinic(Grafo g) {
        this.nodes = g.getV();
        this.capacity = g.getCapacity();
        this.adj = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            adj.add(new ArrayList<>());
        }
        this.level = new int[nodes];
        this.ptr = new int[nodes];
        
        // Inicializar la lista de adyacencia basándose en la capacidad
        for (int u = 0; u < nodes; u++) {
            for (int v = 0; v < nodes; v++) {
                if (capacity[u][v] > 0) {
                    adj.get(u).add(v);
                    adj.get(v).add(u); // Para el grafo bidireccional
                    assignments += 2; // Asignaciones por añadir arcos
                }
            }
        }
    }

    boolean bfs() {
        Arrays.fill(level, -1);
        level[source] = 0;
        assignments++; // Asignación por nivel de fuente
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        assignments++; // Asignación por añadir a la cola

        while (!q.isEmpty()) {
            int u = q.poll();
            assignments++; // Asignación del valor de u
            for (int v : adj.get(u)) {
                comparisons++; // Comparación del nivel y la capacidad
                if (level[v] == -1 && capacity[u][v] > 0) {
                    level[v] = level[u] + 1;
                    assignments++; // Asignación por el nivel
                    q.add(v);
                    assignments++; // Asignación por añadir a la cola
                }
            }
        }
        return level[sink] != -1;
    }

    int dfs(int u, int flow) {
        if (flow == 0) return 0;
        if (u == sink) return flow;
        assignments++; // Asignación por recorrer el nodo

        for (; ptr[u] < adj.get(u).size(); ptr[u]++) {
            int v = adj.get(u).get(ptr[u]);
            comparisons += 2; // Comparaciones por verificar el nivel y la capacidad
            if (level[v] == level[u] + 1 && capacity[u][v] > 0) {
                int pushed = dfs(v, Math.min(flow, capacity[u][v]));
                comparisons++; // Comparación por el flujo empujado
                if (pushed > 0) {
                    capacity[u][v] -= pushed;
                    capacity[v][u] += pushed;
                    assignments += 2; // Asignaciones por actualizar las capacidades
                    return pushed;
                }
            }
        }
        return 0;
    }

    public int maxFlow(int source, int sink) {
        this.source = source;
        this.sink = sink;
        int flow = 0;

        while (bfs()) {
            Arrays.fill(ptr, 0);
            assignments++; // Asignación por reiniciar ptr

            int pushed;
            while ((pushed = dfs(source, INF)) != 0) {
                flow += pushed;
                assignments++; // Asignación por actualizar el flujo
            }
        }
        return flow;
    }

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


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

    public Dinic(int n) {
        nodes = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        capacity = new int[n][n];
        level = new int[n];
        ptr = new int[n];
    }

    public void addEdge(int u, int v, int cap) {
        adj.get(u).add(v);
        adj.get(v).add(u);
        capacity[u][v] += cap;
    }

    boolean bfs() {
        Arrays.fill(level, -1);
        level[source] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (level[v] == -1 && capacity[u][v] > 0) {
                    level[v] = level[u] + 1;
                    q.add(v);
                }
            }
        }
        return level[sink] != -1;
    }

    int dfs(int u, int flow) {
        if (flow == 0) return 0;
        if (u == sink) return flow;

        for (; ptr[u] < adj.get(u).size(); ptr[u]++) {
            int v = adj.get(u).get(ptr[u]);
            if (level[v] == level[u] + 1 && capacity[u][v] > 0) {
                int pushed = dfs(v, Math.min(flow, capacity[u][v]));
                if (pushed > 0) {
                    capacity[u][v] -= pushed;
                    capacity[v][u] += pushed;
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
            int pushed;
            while ((pushed = dfs(source, INF)) != 0) {
                flow += pushed;
            }
        }
        return flow;
    }

    // Método para inicializar el grafo desde la clase Grafo
    public void initializeFromGrafo(Grafo g) {
        this.nodes = g.getV();
        this.capacity = g.getCapacity();
        for (int u = 0; u < nodes; u++) {
            for (int v = 0; v < nodes; v++) {
                if (capacity[u][v] > 0) {
                    adj.get(u).add(v);
                    adj.get(v).add(u); // Esto es para asegurarnos de que el grafo sea bidireccional
                }
            }
        }
    }

    // Método para imprimir el grafo
    public void printGraph() {
        System.out.println("Grafo:");
        for (int u = 0; u < nodes; u++) {
            for (int v : adj.get(u)) {
                if (capacity[u][v] > 0) {
                    System.out.println(u + " -> " + v + " | Capacidad: " + capacity[u][v]);
                }
            }
        }
    }
}

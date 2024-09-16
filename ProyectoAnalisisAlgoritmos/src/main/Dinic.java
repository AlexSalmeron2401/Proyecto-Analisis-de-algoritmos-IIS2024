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

    // Clase pública para almacenar la ruta y su flujo
    public static class PathWithFlow {
        public List<Integer> path;
        public int flow;

        public PathWithFlow(List<Integer> path, int flow) {
            this.path = path;
            this.flow = flow;
        }
    }

    // Lista para almacenar rutas con su flujo
    private List<PathWithFlow> allPathsWithFlow;

    public Dinic(Grafo g) {
        this.nodes = g.getV();
        this.capacity = g.getCapacity();
        this.adj = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            adj.add(new ArrayList<>());
        }
        this.level = new int[nodes];
        this.ptr = new int[nodes];
        this.allPathsWithFlow = new ArrayList<>();

        for (int u = 0; u < nodes; u++) {
            for (int v = 0; v < nodes; v++) {
                if (capacity[u][v] > 0) {
                    adj.get(u).add(v);
                    adj.get(v).add(u);
                    assignments += 2;
                }
            }
        }
    }

    boolean bfs() {
        Arrays.fill(level, -1);
        level[source] = 0;
        assignments++;
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        assignments++;

        while (!q.isEmpty()) {
            int u = q.poll();
            assignments++;
            for (int v : adj.get(u)) {
                comparisons++;
                if (level[v] == -1 && capacity[u][v] > 0) {
                    level[v] = level[u] + 1;
                    assignments++;
                    q.add(v);
                    assignments++;
                }
            }
        }
        return level[sink] != -1;
    }

    int dfs(int u, int flow, List<Integer> path) {
        if (flow == 0) return 0;
        if (u == sink) {
            path.add(sink);
            allPathsWithFlow.add(new PathWithFlow(new ArrayList<>(path), flow)); // Guardar ruta y flujo
            return flow;
        }
        assignments++;

        for (; ptr[u] < adj.get(u).size(); ptr[u]++) {
            int v = adj.get(u).get(ptr[u]);
            comparisons += 2;
            if (level[v] == level[u] + 1 && capacity[u][v] > 0) {
                path.add(u);
                int pushed = dfs(v, Math.min(flow, capacity[u][v]), path);
                comparisons++;
                if (pushed > 0) {
                    capacity[u][v] -= pushed;
                    capacity[v][u] += pushed;
                    assignments += 2;
                    return pushed;
                }
                path.remove(path.size() - 1);
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
            assignments++;

            int pushed;
            while ((pushed = dfs(source, INF, new ArrayList<>())) != 0) {
                flow += pushed;
                assignments++;
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
        this.allPathsWithFlow.clear(); // Reiniciar las rutas almacenadas
    }

    public List<PathWithFlow> getAllPathsWithFlow() {
        return allPathsWithFlow;
    }
}



package main;
import java.util.*;

public class Dinic {
    int INF = Integer.MAX_VALUE; // Valor infinito para flujos
    int nodes; // Número de nodos en el grafo
    List<List<Integer>> adj; // Lista de adyacencia para el grafo
    int[][] capacity; // Matriz de capacidades
    int[] level; // Array de niveles para el algoritmo de BFS
    int[] ptr; // Puntero para seguir la exploración en DFS
    int source, sink; // Fuente y sumidero del flujo
    int comparisons = 0; // Contador de comparaciones realizadas
    int assignments = 0; // Contador de asignaciones realizadas
    // Clase pública para almacenar la ruta y su flujo
    public static class PathWithFlow {
        public List<Integer> path; // Ruta en el flujo
        public int flow; // Flujo asociado a la ruta

        // Constructor para inicializar la ruta y el flujo
        public PathWithFlow(List<Integer> path, int flow) {
            this.path = path; // Inicializar la ruta
            this.flow = flow; // Inicializar el flujo
        }
    }
    // Lista para almacenar rutas con su flujo
    private List<PathWithFlow> allPathsWithFlow;
    // Constructor que inicializa el objeto Dinic con un grafo dado
    public Dinic(Grafo g) {
        this.nodes = g.getV(); // Obtener el número de vértices
        this.capacity = g.getCapacity(); // Obtener la matriz de capacidades
        this.adj = new ArrayList<>(); // Inicializar la lista de adyacencia
        for (int i = 0; i < nodes; i++) {
            adj.add(new ArrayList<>()); // Crear listas para cada nodo
        }
        this.level = new int[nodes]; // Inicializar el array de niveles
        this.ptr = new int[nodes]; // Inicializar el puntero
        this.allPathsWithFlow = new ArrayList<>(); // Inicializar la lista de rutas con flujo
        // Construir la lista de adyacencia
        for (int u = 0; u < nodes; u++) {
            for (int v = 0; v < nodes; v++) {
                // Si hay capacidad positiva entre u y v
                if (capacity[u][v] > 0) {
                    adj.get(u).add(v); // Agregar v a la lista de adyacencia de u
                    adj.get(v).add(u); // Agregar u a la lista de adyacencia de v (grafo residual)
                    assignments += 2; // Contar asignaciones
                }
            }
        }
    }
    // BFS para construir el nivel de cada nodo
    boolean bfs() {
        Arrays.fill(level, -1); // Reiniciar el array de niveles
        level[source] = 0; // Establecer el nivel de la fuente
        assignments++; // Asignación para el nivel de la fuente
        Queue<Integer> q = new LinkedList<>(); // Cola para la búsqueda
        q.add(source); // Agregar la fuente a la cola
        assignments++; // Asignación para agregar a la cola
        // Búsqueda en anchura para encontrar niveles
        while (!q.isEmpty()) {
            int u = q.poll(); // Obtener el nodo actual
            assignments++; // Contar asignaciones
            // Explorar nodos adyacentes
            for (int v : adj.get(u)) {
                comparisons++; // Contar comparaciones
                // Si v no ha sido visitado y hay capacidad disponible
                if (level[v] == -1 && capacity[u][v] > 0) {
                    level[v] = level[u] + 1; // Establecer el nivel de v
                    assignments++; // Asignación para el nivel de v
                    q.add(v); // Agregar v a la cola
                    assignments++; // Asignación para agregar a la cola
                }
            }
        }
        return level[sink] != -1; // Retornar verdadero si se alcanzó el sumidero
    }
    // DFS para empujar el flujo a través del grafo
    int dfs(int u, int flow, List<Integer> path) {
        if (flow == 0) return 0; // Si no hay flujo disponible, retornar 0
        if (u == sink) { // Si se ha alcanzado el sumidero
            path.add(sink); // Agregar el sumidero a la ruta
            allPathsWithFlow.add(new PathWithFlow(new ArrayList<>(path), flow)); // Guardar ruta y flujo
            return flow; // Retornar el flujo
        }
        assignments++; // Contar asignaciones
        // Explorar los nodos adyacentes
        for (; ptr[u] < adj.get(u).size(); ptr[u]++) {
            int v = adj.get(u).get(ptr[u]); // Obtener el siguiente nodo adyacente
            comparisons += 2; // Contar comparaciones
            // Si el nivel de v es correcto y hay capacidad disponible
            if (level[v] == level[u] + 1 && capacity[u][v] > 0) {
                path.add(u); // Agregar u a la ruta
                int pushed = dfs(v, Math.min(flow, capacity[u][v]), path); // Llamar recursivamente a DFS
                comparisons++; // Contar comparaciones
                if (pushed > 0) { // Si se empujó flujo
                    capacity[u][v] -= pushed; // Reducir capacidad en el grafo residual
                    capacity[v][u] += pushed; // Aumentar capacidad en el grafo residual inverso
                    assignments += 2; // Contar asignaciones
                    return pushed; // Retornar el flujo empujado
                }
                path.remove(path.size() - 1); // Retroceder en la ruta
            }
        }
        return 0; // Retornar 0 si no se pudo empujar flujo
    }
    // Método para calcular el flujo máximo
    public int maxFlow(int source, int sink) {
        this.source = source; // Establecer la fuente
        this.sink = sink; // Establecer el sumidero
        int flow = 0; // Inicializar el flujo total
        // Mientras haya caminos aumentantes
        while (bfs()) {
            Arrays.fill(ptr, 0); // Reiniciar punteros para DFS
            assignments++; // Contar asignaciones

            int pushed;
            // Intentar empujar flujo desde la fuente
            while ((pushed = dfs(source, INF, new ArrayList<>())) != 0) {
                flow += pushed; // Incrementar el flujo total
                assignments++; // Contar asignaciones
            }
        }
        return flow; // Retornar el flujo máximo encontrado
    }
    
    // Retorna el número de comparaciones realizadas
    public int getComparisons() {
        return comparisons; // Devolver el contador de comparaciones
    }
    // Retorna el número de asignaciones realizadas
    public int getAssignments() {
        return assignments; // Devolver el contador de asignaciones
    }
    // Método para reiniciar los contadores
    public void resetCounters() {
        this.comparisons = 0; // Reiniciar contador de comparaciones
        this.assignments = 0; // Reiniciar contador de asignaciones
        this.allPathsWithFlow.clear(); // Reiniciar las rutas almacenadas
    }
    // Retorna todas las rutas con su flujo
    public List<PathWithFlow> getAllPathsWithFlow() {
        return allPathsWithFlow; // Devolver las rutas encontradas
    }
}
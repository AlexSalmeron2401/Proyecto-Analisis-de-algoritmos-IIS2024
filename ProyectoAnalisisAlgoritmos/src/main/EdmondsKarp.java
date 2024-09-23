package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class EdmondsKarp {
    private int[][] capacidad; // Matriz de capacidades del grafo
    private int[][] flujo; // Matriz de flujos del grafo
    private int[] padre; // Array para almacenar el camino aumentante
    private boolean[] visitado; // Array para marcar nodos visitados
    private int numVertices; // Número de vértices en el grafo
    private int comparaciones; // Contador de comparaciones realizadas
    private int asignaciones; // Contador de asignaciones realizadas
    // Constructor para inicializar las estructuras
    public EdmondsKarp(Grafo grafo) {
        this.capacidad = grafo.getCapacity(); // Obtener la matriz de capacidades del grafo
        this.numVertices = grafo.getV(); // Inicializar el número de vértices
        this.flujo = new int[numVertices][numVertices]; // Inicializar la matriz de flujos
        this.padre = new int[numVertices]; // Inicializar el array de padres
        this.visitado = new boolean[numVertices]; // Inicializar el array de visitados
        this.comparaciones = 0; // Inicializar el contador de comparaciones
        this.asignaciones = 0; // Inicializar el contador de asignaciones
    }
    // BFS para encontrar el camino aumentante
    public boolean bfs(int fuente, int sumidero) {
        Queue<Integer> cola = new LinkedList<>(); // Cola para la búsqueda en anchura
        cola.add(fuente); // Agregar la fuente a la cola
        Arrays.fill(visitado, false); // Reiniciar el array de visitados
        asignaciones += numVertices; // Asignaciones en visitado
        visitado[fuente] = true; // Marcar la fuente como visitada
        padre[fuente] = -1; // Inicializar el padre de la fuente
        asignaciones += 2; // Asignaciones en padre y visitado
        // Mientras haya nodos en la cola
        while (!cola.isEmpty()) {
            int u = cola.poll(); // Obtener el nodo actual
            // Explorar todos los vértices adyacentes
            for (int v = 0; v < numVertices; v++) {
                comparaciones++; // Contar comparaciones
                // Si el nodo no ha sido visitado y hay capacidad disponible
                if (!visitado[v] && capacidad[u][v] - flujo[u][v] > 0) {
                    cola.add(v); // Agregar el vértice a la cola
                    padre[v] = u; // Establecer el padre del vértice
                    visitado[v] = true; // Marcar el vértice como visitado
                    asignaciones += 2; // Actualización de padre y visitado
                    // Si se ha alcanzado el sumidero, retornar verdadero
                    if (v == sumidero) {
                        return true;
                    }
                }
            }
        }
        return false; // No se encontró un camino aumentante
    }
    // Función principal de Edmonds-Karp para encontrar el flujo máximo
    public int edmondsKarp(int fuente, int sumidero) {
        int maxFlujo = 0; // Inicializar el flujo máximo
        asignaciones++; // Inicialización de maxFlujo
        // Mientras haya un camino aumentante desde la fuente al sumidero
        while (bfs(fuente, sumidero)) {
            int caminoFlujo = Integer.MAX_VALUE; // Inicializar el flujo del camino
            asignaciones++; // Inicialización de caminoFlujo

            // Encuentra la capacidad mínima en el camino aumentante
            for (int v = sumidero; v != fuente; v = padre[v]) {
                int u = padre[v]; // Obtener el nodo padre
                comparaciones += 2; // Comparaciones dentro del bucle
                caminoFlujo = Math.min(caminoFlujo, capacidad[u][v] - flujo[u][v]); // Actualizar el flujo del camino
            }

            // Actualiza los flujos en el grafo residual
            for (int v = sumidero; v != fuente; v = padre[v]) {
                int u = padre[v]; // Obtener el nodo padre
                flujo[u][v] += caminoFlujo; // Aumentar flujo hacia adelante
                flujo[v][u] -= caminoFlujo; // Disminuir flujo hacia atrás
                asignaciones += 2; // Actualización de flujo hacia adelante y hacia atrás
            }
            maxFlujo += caminoFlujo; // Incrementar el flujo máximo total
            asignaciones++; // Actualización de maxFlujo
        }
        return maxFlujo; // Retornar el flujo máximo encontrado
    }
    // Método para recuperar las rutas de flujo máximo con sus flujos
    public List<PathWithFlow> getAllPathsWithFlow(int fuente, int sumidero) {
        List<PathWithFlow> pathsWithFlow = new ArrayList<>(); // Lista para almacenar las rutas con flujo
        boolean[] visited = new boolean[numVertices]; // Array para marcar nodos visitados
        ArrayList<Integer> path = new ArrayList<>(); // Lista para almacenar la ruta actual
        dfs(fuente, sumidero, visited, path, pathsWithFlow); // Llamar a DFS para encontrar rutas
        return pathsWithFlow; // Retornar las rutas encontradas
    }
    // Método DFS para recuperar las rutas con su flujo
    private void dfs(int current, int sink, boolean[] visited, ArrayList<Integer> path, List<PathWithFlow> pathsWithFlow) {
        visited[current] = true; // Marcar el nodo actual como visitado
        path.add(current); // Agregar el nodo actual a la ruta
        // Si se ha alcanzado el sumidero, calcular el flujo de la ruta
        if (current == sink) {
            int pathFlow = Integer.MAX_VALUE; // Inicializar el flujo de la ruta
            // Calcular la capacidad mínima en la ruta
            for (int i = 1; i < path.size(); i++) {
                int u = path.get(i - 1); // Nodo anterior en la ruta
                int v = path.get(i); // Nodo actual en la ruta
                pathFlow = Math.min(pathFlow, flujo[u][v]); // Actualizar el flujo de la ruta
            }
            // Almacenar la ruta y su flujo en la lista
            pathsWithFlow.add(new PathWithFlow(new ArrayList<>(path), pathFlow));
        } else {
            // Explorar todos los vértices adyacentes
            for (int v = 0; v < numVertices; v++) {
                // Si el vértice no ha sido visitado y hay flujo positivo
                if (!visited[v] && flujo[current][v] > 0) {
                    dfs(v, sink, visited, path, pathsWithFlow); // Llamar recursivamente a DFS
                }
            }
        }
        path.remove(path.size() - 1); // Retroceder en la ruta
        visited[current] = false; // Marcar el nodo actual como no visitado
    }
    // Retorna el número de comparaciones realizadas
    public int getComparisons() {
        return comparaciones; // Devolver el contador de comparaciones
    }
    // Retorna el número de asignaciones realizadas
    public int getAssignments() {
        return asignaciones; // Devolver el contador de asignaciones
    }
    // Método para reiniciar los contadores
    public void resetCounters() {
        this.comparaciones = 0; // Reiniciar contador de comparaciones
        this.asignaciones = 0; // Reiniciar contador de asignaciones
    }
    // Clase para almacenar la ruta y el flujo asociado
    public static class PathWithFlow {
        public final List<Integer> path; // Ruta de la cual se almacena el flujo
        public final int flow; // Flujo asociado a la ruta
        // Constructor para inicializar la ruta y el flujo
        public PathWithFlow(List<Integer> path, int flow) {
            this.path = path; // Inicializar la ruta
            this.flow = flow; // Inicializar el flujo
        }
    }
}
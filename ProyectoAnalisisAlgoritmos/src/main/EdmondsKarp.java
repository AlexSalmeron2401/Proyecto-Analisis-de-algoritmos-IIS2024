package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class EdmondsKarp {
    private int[][] capacidad;
    private int[][] flujo;
    private int[] padre;
    private boolean[] visitado;
    private int numVertices;
    private int comparaciones;
    private int asignaciones;

    // Constructor para inicializar las estructuras
    public EdmondsKarp(Grafo grafo) {
        this.capacidad = grafo.getCapacity();
        this.numVertices = grafo.getV();
        this.flujo = new int[numVertices][numVertices];
        this.padre = new int[numVertices];
        this.visitado = new boolean[numVertices];
        this.comparaciones = 0;
        this.asignaciones = 0;
    }

    // BFS para encontrar el camino aumentante
    public boolean bfs(int fuente, int sumidero) {
        Queue<Integer> cola = new LinkedList<>();
        cola.add(fuente);
        Arrays.fill(visitado, false);
        asignaciones += numVertices; // Asignaciones en visitado
        visitado[fuente] = true;
        padre[fuente] = -1;
        asignaciones += 2; // Asignaciones en padre y visitado

        while (!cola.isEmpty()) {
            int u = cola.poll();
            for (int v = 0; v < numVertices; v++) {
                comparaciones++; // Contar comparaciones
                if (!visitado[v] && capacidad[u][v] - flujo[u][v] > 0) {
                    cola.add(v);
                    padre[v] = u;
                    visitado[v] = true;
                    asignaciones += 2; // Actualización de padre y visitado
                    if (v == sumidero) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Función principal de Edmonds-Karp para encontrar el flujo máximo
    public int edmondsKarp(int fuente, int sumidero) {
        int maxFlujo = 0;
        asignaciones++; // Inicialización de maxFlujo

        // Mientras haya un camino aumentante desde la fuente al sumidero
        while (bfs(fuente, sumidero)) {
            int caminoFlujo = Integer.MAX_VALUE;
            asignaciones++; // Inicialización de caminoFlujo

            // Encuentra la capacidad mínima en el camino aumentante
            for (int v = sumidero; v != fuente; v = padre[v]) {
                int u = padre[v];
                comparaciones += 2; // Comparaciones dentro del bucle
                caminoFlujo = Math.min(caminoFlujo, capacidad[u][v] - flujo[u][v]);
            }

            // Actualiza los flujos en el grafo residual
            for (int v = sumidero; v != fuente; v = padre[v]) {
                int u = padre[v];
                flujo[u][v] += caminoFlujo;
                flujo[v][u] -= caminoFlujo;
                asignaciones += 2; // Actualización de flujo hacia adelante y hacia atrás
            }

            maxFlujo += caminoFlujo;
            asignaciones++; // Actualización de maxFlujo
        }

        return maxFlujo;
    }

    // Método para recuperar las rutas de flujo máximo con sus flujos
    public List<PathWithFlow> getAllPathsWithFlow(int fuente, int sumidero) {
        List<PathWithFlow> pathsWithFlow = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        ArrayList<Integer> path = new ArrayList<>();
        dfs(fuente, sumidero, visited, path, pathsWithFlow);
        return pathsWithFlow;
    }

    // Método DFS para recuperar las rutas con su flujo
    private void dfs(int current, int sink, boolean[] visited, ArrayList<Integer> path, List<PathWithFlow> pathsWithFlow) {
        visited[current] = true;
        path.add(current);

        if (current == sink) {
            int pathFlow = Integer.MAX_VALUE;
            for (int i = 1; i < path.size(); i++) {
                int u = path.get(i - 1);
                int v = path.get(i);
                pathFlow = Math.min(pathFlow, flujo[u][v]);
            }
            pathsWithFlow.add(new PathWithFlow(new ArrayList<>(path), pathFlow));
        } else {
            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && flujo[current][v] > 0) {
                    dfs(v, sink, visited, path, pathsWithFlow);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[current] = false;
    }

    // Retorna el número de comparaciones realizadas
    public int getComparisons() {
        return comparaciones;
    }

    // Retorna el número de asignaciones realizadas
    public int getAssignments() {
        return asignaciones;
    }

    public void resetCounters() {
        this.comparaciones = 0;
        this.asignaciones = 0;
    }

    // Clase para almacenar la ruta y el flujo asociado
    public static class PathWithFlow {
        public final List<Integer> path;
        public final int flow;

        public PathWithFlow(List<Integer> path, int flow) {
            this.path = path;
            this.flow = flow;
        }
    }
}

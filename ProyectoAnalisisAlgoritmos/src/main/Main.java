package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Define el directorio actual donde se encuentran los archivos
        Path directoryPath = Paths.get(System.getProperty("user.dir"));

        int[] vertices = {10, 20, 40, 80};
        int[] arcos = {12, 24, 48, 56, 100, 400, 1600, 6400};

        // Leer y procesar los grafos desde los archivos
        for (int i = 0; i < vertices.length; i++) {
            if (i + 4 < arcos.length) {
                // Archivos de entrada
                Path filename1 = directoryPath.resolve("Grafo_" + vertices[i] + "_vertices_" + arcos[i] + "_arcos.txt");
                Path filename2 = directoryPath.resolve("Grafo_" + vertices[i] + "_vertices_" + arcos[i + 4] + "_arcos.txt");

                // Leer matrices desde los archivos
                int[][] matrix1 = readMatrixFromFile(filename1);
                int[][] matrix2 = readMatrixFromFile(filename2);

                // Crear instancias de Grafo
                Grafo grafo1 = (matrix1 != null) ? new Grafo(matrix1) : null;
                Grafo grafo2 = (matrix2 != null) ? new Grafo(matrix2) : null;

                if (grafo1 != null && grafo2 != null) {
                    // Inicialización de los puntos de inicio y final del grafo
                    int source = 0;
                    int sink = vertices[i] - 1;

                    // Inicialización y ejecución del algoritmo Edmonds-Karp
                    System.out.println("Edmonds-Karp");
                    EdmondsKarp edmondsKarp1 = new EdmondsKarp(grafo1);
                    EdmondsKarp edmondsKarp2 = new EdmondsKarp(grafo2);

                    edmondsKarp1.resetCounters();
                    edmondsKarp2.resetCounters();

                    long startTime = System.nanoTime(); // Tiempo inicial en nanosegundos

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowEdmondsKarp1 = edmondsKarp1.edmondsKarp(source, sink);
                    long endTime = System.nanoTime(); // Tiempo final en nanosegundos
                    System.out.println("El flujo maximo es: " + maxFlowEdmondsKarp1);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el primer grafo:");
                    printPathsWithFlow(edmondsKarp1.getAllPathsWithFlow(source, sink));
                    System.out.println("Comparaciones: " + edmondsKarp1.getComparisons());
                    System.out.println("Asignaciones: " + edmondsKarp1.getAssignments());

                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowEdmondsKarp2 = edmondsKarp2.edmondsKarp(source, sink);
                    endTime = System.nanoTime();
                    System.out.println("El flujo maximo es: " + maxFlowEdmondsKarp2);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el segundo grafo:");
                    printPathsWithFlow(edmondsKarp2.getAllPathsWithFlow(source, sink));
                    System.out.println("Comparaciones: " + edmondsKarp2.getComparisons());
                    System.out.println("Asignaciones: " + edmondsKarp2.getAssignments());

                    System.out.println("############################################################");

                    // Inicialización y ejecución del algoritmo Ford-Fulkerson
                    System.out.println("Ford-Fulkerson");
                    FordFulkerson fordFulkerson1 = new FordFulkerson();
                    FordFulkerson fordFulkerson2 = new FordFulkerson();

                    fordFulkerson1.resetCounters();
                    fordFulkerson2.resetCounters();

                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowFordFulkerson1 = fordFulkerson1.fordFulkerson(grafo1.getCapacity(), source, sink, grafo1.getV());
                    endTime = System.nanoTime();
                    System.out.println("El flujo maximo es: " + maxFlowFordFulkerson1);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el primer grafo:");
                    printPathsWithFlow(fordFulkerson1.getAllPathsWithFlow()); // Método adaptado
                    System.out.println("Comparaciones: " + fordFulkerson1.getComparisons());
                    System.out.println("Asignaciones: " + fordFulkerson1.getAssignments());

                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowFordFulkerson2 = fordFulkerson2.fordFulkerson(grafo2.getCapacity(), source, sink, grafo2.getV());
                    endTime = System.nanoTime();
                    System.out.println("El flujo maximo es: " + maxFlowFordFulkerson2);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el segundo grafo:");
                    printPathsWithFlow(fordFulkerson2.getAllPathsWithFlow()); // Método adaptado
                    System.out.println("Comparaciones: " + fordFulkerson2.getComparisons());
                    System.out.println("Asignaciones: " + fordFulkerson2.getAssignments());

                    System.out.println("############################################################");

                    // Inicialización y ejecución del algoritmo Dinic
                    System.out.println("Dinic");
                    Dinic dinic1 = new Dinic(grafo1);
                    Dinic dinic2 = new Dinic(grafo2);

                    dinic1.resetCounters();
                    dinic2.resetCounters();

                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowDinic1 = dinic1.maxFlow(source, sink);
                    endTime = System.nanoTime();
                    System.out.println("El flujo maximo es: " + maxFlowDinic1);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Comparaciones: " + dinic1.getComparisons());
                    System.out.println("Asignaciones: " + dinic1.getAssignments());
                    System.out.println("Rutas de flujo maximo para el primer grafo:");
                    printPathsWithFlow(dinic1.getAllPathsWithFlow());

                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowDinic2 = dinic2.maxFlow(source, sink);
                    endTime = System.nanoTime();
                    System.out.println("El flujo maximo es: " + maxFlowDinic2);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Comparaciones: " + dinic2.getComparisons());
                    System.out.println("Asignaciones: " + dinic2.getAssignments());
                    System.out.println("Rutas de flujo maximo para el segundo grafo:");
                    printPathsWithFlow(dinic2.getAllPathsWithFlow());

                    System.out.println("-------------------------------------------------\n");
                } else {
                    System.err.println("Error al leer las matrices para los grafos.");
                }
            }
        }
    }

    public static int[][] readMatrixFromFile(Path filename) {
        try (BufferedReader reader = Files.newBufferedReader(filename)) {
            String line = reader.readLine();
            if (line == null) {
                System.err.println("El archivo está vacío o no se pudo leer.");
                return null;
            }

            int numVertices = line.trim().split("\\s+").length;
            int[][] matrix = new int[numVertices][numVertices];

            for (int i = 0; i < numVertices; i++) {
                if (i != 0) {  // La primera línea ya fue leída
                    line = reader.readLine();
                }
                if (line == null) {
                    System.err.println("El archivo no tiene suficiente contenido.");
                    return null;
                }
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != numVertices) {
                    System.err.println("La línea en el archivo no tiene la longitud esperada.");
                    return null;
                }
                for (int j = 0; j < numVertices; j++) {
                    matrix[i][j] = Integer.parseInt(tokens[j]);
                }
            }

            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método auxiliar para imprimir todas las rutas con su flujo
    public static void printPathsWithFlow(List<?> pathsWithFlow) {
        for (Object obj : pathsWithFlow) {
            if (obj instanceof Dinic.PathWithFlow) {
                Dinic.PathWithFlow pathWithFlow = (Dinic.PathWithFlow) obj;
                System.out.println("Ruta: " + pathWithFlow.path + ", Flujo: " + pathWithFlow.flow);
            } else if (obj instanceof EdmondsKarp.PathWithFlow) {
                EdmondsKarp.PathWithFlow pathWithFlow = (EdmondsKarp.PathWithFlow) obj;
                System.out.println("Ruta: " + pathWithFlow.path + ", Flujo: " + pathWithFlow.flow);
            } else if (obj instanceof FordFulkerson.PathWithFlow) {
                FordFulkerson.PathWithFlow pathWithFlow = (FordFulkerson.PathWithFlow) obj;
                System.out.println("Ruta: " + pathWithFlow.path + ", Flujo: " + pathWithFlow.flow);
            }
        }
    }
}


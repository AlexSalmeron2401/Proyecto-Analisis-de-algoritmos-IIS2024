package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Define el directorio actual donde se encuentran los archivos
        String directoryPath = System.getProperty("user.dir") + File.separator;

        int[] vertices = {10, 20, 40, 80};
        int[] arcos = {12, 24, 48, 56, 100, 400, 1600, 6400};

        // Leer y procesar los grafos desde los archivos
        for (int i = 0; i < vertices.length; i++) {
            if (i + 4 < arcos.length) {
                // Archivos de entrada
                String filename1 = directoryPath + "Grafo_" + vertices[i] + "_vertices_" + arcos[i] + "_arcos.txt";
                String filename2 = directoryPath + "Grafo_" + vertices[i] + "_vertices_" + arcos[i + 4] + "_arcos.txt";

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
                    System.out.println("EdmondsKarp");
                    EdmondsKarp edmondsKarp1 = new EdmondsKarp(grafo1);
                    EdmondsKarp edmondsKarp2 = new EdmondsKarp(grafo2);
                    
                    edmondsKarp1.resetCounters();
                    edmondsKarp2.resetCounters();

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    System.out.println("El flujo maximo es: " + edmondsKarp1.edmondsKarp(source, sink));
                    System.out.println("Comparaciones: " + edmondsKarp1.getComparisons());
                    System.out.println("Asignaciones: " + edmondsKarp1.getAssignments());

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    System.out.println("El flujo maximo es: " + edmondsKarp2.edmondsKarp(source, sink));
                    System.out.println("Comparaciones: " + edmondsKarp2.getComparisons());
                    System.out.println("Asignaciones: " + edmondsKarp2.getAssignments());

                    System.out.println("############################################################");

                    // Inicialización y ejecución del algoritmo Ford-Fulkerson
                    System.out.println("FordFulkerson");
                    FordFulkerson fordFulkerson1 = new FordFulkerson();
                    FordFulkerson fordFulkerson2 = new FordFulkerson();

                    fordFulkerson1.resetCounters();
                    fordFulkerson2.resetCounters();

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowFordFulkerson1 = fordFulkerson1.fordFulkerson(grafo1.getCapacity(), source, sink, grafo1.getV());
                    System.out.println("El flujo maximo es: " + maxFlowFordFulkerson1);
                    System.out.println("Comparaciones: " + fordFulkerson1.getComparisons());
                    System.out.println("Asignaciones: " + fordFulkerson1.getAssignments());

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowFordFulkerson2 = fordFulkerson2.fordFulkerson(grafo2.getCapacity(), source, sink, grafo2.getV());
                    System.out.println("El flujo maximo es: " + maxFlowFordFulkerson2);
                    System.out.println("Comparaciones: " + fordFulkerson2.getComparisons());
                    System.out.println("Asignaciones: " + fordFulkerson2.getAssignments());

                    System.out.println("############################################################");

                    // Inicialización y ejecución del algoritmo Dinic
                    System.out.println("Dinic");
                    Dinic dinic1 = new Dinic(grafo1);
                    Dinic dinic2 = new Dinic(grafo2);

                    dinic1.resetCounters();
                    dinic2.resetCounters();

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowDinic1 = dinic1.maxFlow(source, sink);
                    System.out.println("El flujo maximo es: " + maxFlowDinic1);
                    System.out.println("Comparaciones: " + dinic1.getComparisons());
                    System.out.println("Asignaciones: " + dinic1.getAssignments());

                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowDinic2 = dinic2.maxFlow(source, sink);
                    System.out.println("El flujo maximo es: " + maxFlowDinic2);
                    System.out.println("Comparaciones: " + dinic2.getComparisons());
                    System.out.println("Asignaciones: " + dinic2.getAssignments());

                    System.out.println("-------------------------------------------------\n");
                } else {
                    System.err.println("Error al leer las matrices para los grafos.");
                }
            }
        }
    }

    public static int[][] readMatrixFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Leer la primera línea para determinar el número de vértices
            String line = reader.readLine();
            if (line == null) {
                System.err.println("El archivo está vacío o no se pudo leer.");
                return null;
            }

            // Determinar el número de vértices basándonos en la longitud de la primera línea
            int numVertices = line.trim().split("\\s+").length;
            int[][] matrix = new int[numVertices][numVertices];

            // Leer la matriz de capacidad
            reader.close(); // Cerrar el lector anterior
            try (BufferedReader newReader = new BufferedReader(new FileReader(filename))) {
                for (int i = 0; i < numVertices; i++) {
                    line = newReader.readLine();
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
            }

            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
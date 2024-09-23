package main;
import java.io.BufferedReader; // Importa BufferedReader para leer archivos
import java.io.IOException; // Importa IOException para manejar excepciones de entrada/salida
import java.nio.file.Files; // Importa Files para acceder a operaciones de archivos
import java.nio.file.Path; // Importa Path para representar rutas de archivos
import java.nio.file.Paths; // Importa Paths para crear instancias de Path
import java.util.List; // Importa List para trabajar con listas

public class Main {
    public static void main(String[] args) {
        // Define el directorio actual donde se encuentran los archivos
        Path directoryPath = Paths.get(System.getProperty("user.dir"));
        // Arreglos que contienen el número de vértices y arcos a utilizar para los grafos
        int[] vertices = {10, 20, 40, 80};
        int[] arcos = {12, 24, 48, 56, 100, 400, 1600, 6400};
        // Itera sobre los diferentes tamaños de grafos
        for (int i = 0; i < vertices.length; i++) {
            // Comprueba que hay suficientes arcos para el índice actual
            if (i + 4 < arcos.length) {
                // Construye las rutas de los archivos de entrada correspondientes a los grafos
                Path filename1 = directoryPath.resolve("Grafo_" + vertices[i] + "_vertices_" + arcos[i] + "_arcos.txt");
                Path filename2 = directoryPath.resolve("Grafo_" + vertices[i] + "_vertices_" + arcos[i + 4] + "_arcos.txt");
                // Lee las matrices de adyacencia desde los archivos
                int[][] matrix1 = readMatrixFromFile(filename1);
                int[][] matrix2 = readMatrixFromFile(filename2);
                // Crea instancias de la clase Grafo a partir de las matrices leídas
                Grafo grafo1 = (matrix1 != null) ? new Grafo(matrix1) : null;
                Grafo grafo2 = (matrix2 != null) ? new Grafo(matrix2) : null;
                // Verifica que ambos grafos se hayan creado correctamente
                if (grafo1 != null && grafo2 != null) {
                    // Define los vértices de origen y destino para el flujo
                    int source = 0; // Vértice fuente
                    int sink = vertices[i] - 1; // Vértice sumidero (último vértice)
                    // Inicializa y ejecuta el algoritmo Edmonds-Karp
                    System.out.println("Edmonds-Karp");
                    EdmondsKarp edmondsKarp1 = new EdmondsKarp(grafo1); // Primer grafo
                    EdmondsKarp edmondsKarp2 = new EdmondsKarp(grafo2); // Segundo grafo
                    // Restablece los contadores para el algoritmo
                    edmondsKarp1.resetCounters();
                    edmondsKarp2.resetCounters();
                    // Mide el tiempo de ejecución del algoritmo
                    long startTime = System.nanoTime(); // Tiempo inicial en nanosegundos
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    // Ejecuta el algoritmo y obtiene el flujo máximo
                    int maxFlowEdmondsKarp1 = edmondsKarp1.edmondsKarp(source, sink);
                    long endTime = System.nanoTime(); // Tiempo final en nanosegundos
                    // Imprime resultados del primer grafo
                    System.out.println("El flujo maximo es: " + maxFlowEdmondsKarp1);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el primer grafo:");
                    printPathsWithFlow(edmondsKarp1.getAllPathsWithFlow(source, sink)); // Imprime las rutas de flujo
                    System.out.println("Comparaciones: " + edmondsKarp1.getComparisons()); // Imprime el número de comparaciones
                    System.out.println("Asignaciones: " + edmondsKarp1.getAssignments()); // Imprime el número de asignaciones
                    // Repite el proceso para el segundo grafo
                    startTime = System.nanoTime(); // Reinicia el cronómetro
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowEdmondsKarp2 = edmondsKarp2.edmondsKarp(source, sink);
                    endTime = System.nanoTime(); // Tiempo final en nanosegundos
                    // Imprime resultados del segundo grafo
                    System.out.println("El flujo maximo es: " + maxFlowEdmondsKarp2);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el segundo grafo:");
                    printPathsWithFlow(edmondsKarp2.getAllPathsWithFlow(source, sink)); // Imprime las rutas de flujo
                    System.out.println("Comparaciones: " + edmondsKarp2.getComparisons());
                    System.out.println("Asignaciones: " + edmondsKarp2.getAssignments());
                    System.out.println("############################################################");
                    // Inicializa y ejecuta el algoritmo Ford-Fulkerson
                    System.out.println("Ford-Fulkerson");
                    FordFulkerson fordFulkerson1 = new FordFulkerson(); // Instancia para el primer grafo
                    FordFulkerson fordFulkerson2 = new FordFulkerson(); // Instancia para el segundo grafo
                    // Restablece contadores para el algoritmo
                    fordFulkerson1.resetCounters();
                    fordFulkerson2.resetCounters();
                    // Mide el tiempo de ejecución para el primer grafo
                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowFordFulkerson1 = fordFulkerson1.fordFulkerson(grafo1.getCapacity(), source, sink, grafo1.getV());
                    endTime = System.nanoTime(); // Tiempo final en nanosegundos
                    // Imprime resultados del primer grafo
                    System.out.println("El flujo maximo es: " + maxFlowFordFulkerson1);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el primer grafo:");
                    printPathsWithFlow(fordFulkerson1.getAllPathsWithFlow()); // Imprime las rutas de flujo
                    System.out.println("Comparaciones: " + fordFulkerson1.getComparisons());
                    System.out.println("Asignaciones: " + fordFulkerson1.getAssignments());
                    // Repite el proceso para el segundo grafo
                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowFordFulkerson2 = fordFulkerson2.fordFulkerson(grafo2.getCapacity(), source, sink, grafo2.getV());
                    endTime = System.nanoTime();
                    // Imprime resultados del segundo grafo
                    System.out.println("El flujo maximo es: " + maxFlowFordFulkerson2);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Rutas de flujo maximo para el segundo grafo:");
                    printPathsWithFlow(fordFulkerson2.getAllPathsWithFlow()); // Imprime las rutas de flujo
                    System.out.println("Comparaciones: " + fordFulkerson2.getComparisons());
                    System.out.println("Asignaciones: " + fordFulkerson2.getAssignments());
                    System.out.println("############################################################");
                    // Inicializa y ejecuta el algoritmo Dinic
                    System.out.println("Dinic");
                    Dinic dinic1 = new Dinic(grafo1); // Instancia para el primer grafo
                    Dinic dinic2 = new Dinic(grafo2); // Instancia para el segundo grafo
                    // Restablece contadores para el algoritmo
                    dinic1.resetCounters();
                    dinic2.resetCounters();
                    // Mide el tiempo de ejecución para el primer grafo
                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i]);
                    int maxFlowDinic1 = dinic1.maxFlow(source, sink);
                    endTime = System.nanoTime();
                    // Imprime resultados del primer grafo
                    System.out.println("El flujo maximo es: " + maxFlowDinic1);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Comparaciones: " + dinic1.getComparisons());
                    System.out.println("Asignaciones: " + dinic1.getAssignments());
                    System.out.println("Rutas de flujo maximo para el primer grafo:");
                    printPathsWithFlow(dinic1.getAllPathsWithFlow()); // Imprime las rutas de flujo
                    // Repite el proceso para el segundo grafo
                    startTime = System.nanoTime();
                    System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + arcos[i + 4]);
                    int maxFlowDinic2 = dinic2.maxFlow(source, sink);
                    endTime = System.nanoTime();
                    // Imprime resultados del segundo grafo
                    System.out.println("El flujo maximo es: " + maxFlowDinic2);
                    System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ns");
                    System.out.println("Comparaciones: " + dinic2.getComparisons());
                    System.out.println("Asignaciones: " + dinic2.getAssignments());
                    System.out.println("Rutas de flujo maximo para el segundo grafo:");
                    printPathsWithFlow(dinic2.getAllPathsWithFlow()); // Imprime las rutas de flujo

                    System.out.println("-------------------------------------------------\n");
                } else {
                    // Imprime un error si no se pudieron leer las matrices
                    System.err.println("Error al leer las matrices para los grafos.");
                }
            }
        }
    }
    // Método que lee una matriz de adyacencia desde un archivo
    public static int[][] readMatrixFromFile(Path filename) {
        try (BufferedReader reader = Files.newBufferedReader(filename)) { // Usa BufferedReader para leer el archivo
            String line = reader.readLine(); // Lee la primera línea del archivo
            if (line == null) { // Comprueba si la línea es nula
                System.err.println("El archivo está vacío o no se pudo leer."); // Imprime un error si el archivo está vacío
                return null; // Retorna null si hay un error
            }
            // Determina el número de vértices a partir de la primera línea
            int numVertices = line.trim().split("\\s+").length; // Cuenta el número de valores en la línea
            int[][] matrix = new int[numVertices][numVertices]; // Crea la matriz de adyacencia
            // Itera sobre cada línea del archivo para llenar la matriz
            for (int i = 0; i < numVertices; i++) {
                if (i != 0) {  // La primera línea ya fue leída
                    line = reader.readLine(); // Lee la siguiente línea
                }
                if (line == null) { // Comprueba si la línea es nula
                    System.err.println("El archivo no tiene suficiente contenido."); // Imprime un error si no hay suficiente contenido
                    return null; // Retorna null si hay un error
                }
                String[] tokens = line.trim().split("\\s+"); // Divide la línea en valores usando espacios en blanco
                if (tokens.length != numVertices) { // Comprueba si la longitud de la línea es la esperada
                    System.err.println("La línea en el archivo no tiene la longitud esperada."); // Imprime un error si la longitud no es correcta
                    return null; // Retorna null si hay un error
                }
                // Convierte cada valor a entero y lo almacena en la matriz
                for (int j = 0; j < numVertices; j++) {
                    matrix[i][j] = Integer.parseInt(tokens[j]); // Convierte cada valor de String a int
                }
            }
            return matrix; // Retorna la matriz de adyacencia
        } catch (IOException e) { // Maneja cualquier excepción de entrada/salida
            e.printStackTrace(); // Imprime la traza de la excepción
            return null; // Retorna null si ocurre un error
        }
    }
    // Método auxiliar para imprimir todas las rutas con su flujo
    public static void printPathsWithFlow(List<?> pathsWithFlow) {
        for (Object obj : pathsWithFlow) { // Itera sobre cada objeto en la lista
            // Comprueba si el objeto es una ruta de flujo de Dinic
            if (obj instanceof Dinic.PathWithFlow) {
                Dinic.PathWithFlow pathWithFlow = (Dinic.PathWithFlow) obj; // Convierte el objeto a la clase correspondiente
                System.out.println("Ruta: " + pathWithFlow.path + ", Flujo: " + pathWithFlow.flow); // Imprime la ruta y su flujo
            } 
            // Comprueba si el objeto es una ruta de flujo de Edmonds-Karp
            else if (obj instanceof EdmondsKarp.PathWithFlow) {
                EdmondsKarp.PathWithFlow pathWithFlow = (EdmondsKarp.PathWithFlow) obj; // Convierte el objeto a la clase correspondiente
                System.out.println("Ruta: " + pathWithFlow.path + ", Flujo: " + pathWithFlow.flow); // Imprime la ruta y su flujo
            } 
            // Comprueba si el objeto es una ruta de flujo de Ford-Fulkerson
            else if (obj instanceof FordFulkerson.PathWithFlow) {
                FordFulkerson.PathWithFlow pathWithFlow = (FordFulkerson.PathWithFlow) obj; // Convierte el objeto a la clase correspondiente
                System.out.println("Ruta: " + pathWithFlow.path + ", Flujo: " + pathWithFlow.flow); // Imprime la ruta y su flujo
            }
        }
    }
}
package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/*
    Autores:
    Alex Salmeron
    Alisson Rodriguez
    Asdrubal Madrigal
*/
public class MainOld {
    public static void main(String[] args) {
        int[] vertices = {10, 20, 40, 80};
        int[] arcos = {12, 24, 48, 56, 100, 400, 1600, 6400};

        // Directorio actual donde se guardarán los archivos
        String directoryPath = System.getProperty("user.dir") + File.separator;

        for (int i = 0; i < vertices.length; i++) {
            // Crear los grafos con la información de los vértices y arcos
            int numVertices1 = vertices[i];
            int numArcos1 = arcos[i];
            int numVertices2 = vertices[i];
            int numArcos2 = arcos[i + 4];

            // Generar matrices de capacidad para ambos grafos
            int[][] matrix1 = generateGraphMatrix(numVertices1, numArcos1);
            int[][] matrix2 = generateFullGraphMatrix(numVertices2); // Cambiar aquí para grafo completo

            // Guardar las matrices en archivos
            saveMatrixToFile(matrix1, directoryPath + "Grafo_" + numVertices1 + "_vertices_" + numArcos1 + "_arcos.txt");
            saveMatrixToFile(matrix2, directoryPath + "Grafo_" + numVertices2 + "_vertices_" + numArcos2 + "_arcos.txt");
        }
    }

    // Método para generar una matriz de capacidad de grafo con pesos de arcos (sin diagonal llena)
    private static int[][] generateGraphMatrix(int vertices, int arcos) {
        int[][] capacidad = new int[vertices][vertices];
        Random rand = new Random();
        List<int[]> posiblesAristas = new ArrayList<>();

        // Generar todas las posibles aristas válidas
        for (int from = 0; from < vertices; from++) {
            for (int to = 0; to < vertices; to++) {
                if (from != to) {
                    posiblesAristas.add(new int[]{from, to});
                }
            }
        }

        // Verificar que el número de arcos no sea mayor que el número posible de aristas
        int maxArcosPosibles = vertices * (vertices - 1); // Sin aristas que vuelven a sí mismos
        arcos = Math.min(arcos, maxArcosPosibles); // Asegurar que no exceda el número máximo de arcos posibles

        // Barajar las posibles aristas
        Collections.shuffle(posiblesAristas, rand);

        // Garantizar un camino básico del nodo 0 al último nodo
        for (int i = 0; i < vertices - 1; i++) {
            capacidad[i][i + 1] = 20 + rand.nextInt(681); // Peso entre 20 y 700
            arcos--; // Reducir el número de arcos disponibles
        }

        // Seleccionar las primeras 'arcos' aristas restantes y asignarles pesos
        for (int i = 0; i < arcos && i < posiblesAristas.size(); i++) {
            int[] arista = posiblesAristas.get(i);
            int from = arista[0];
            int to = arista[1];
            if (capacidad[from][to] == 0) {  // Solo agregar arcos donde no hay ya una conexión
                int peso = 20 + rand.nextInt(681); // Peso entre 20 y 700
                capacidad[from][to] = peso;
            }
        }

        return capacidad;
    }

    // Método para generar una matriz completamente llena, incluyendo la diagonal
    private static int[][] generateFullGraphMatrix(int vertices) {
        int[][] capacidad = new int[vertices][vertices];
        Random rand = new Random();

        // Llenar todas las celdas, incluidas las de la diagonal
        for (int from = 0; from < vertices; from++) {
            for (int to = 0; to < vertices; to++) {
                int peso = 20 + rand.nextInt(681); // Peso entre 20 y 700
                capacidad[from][to] = peso;
            }
        }

        return capacidad;
    }

    // Método para guardar una matriz en un archivo
    private static void saveMatrixToFile(int[][] matrix, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int[] row : matrix) {
                for (int value : row) {
                    writer.write(value + "\t");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

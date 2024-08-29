package main;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //Grafos con el algoritmo EdmondsKarp
        int[] vertices = {10, 20, 40, 80};
        int[] edges = {12, 24, 48, 56, 100, 400, 1600, 6400};

        for (int i = 0; i < vertices.length; i++) {
            Grafo grafo1 = new Grafo(vertices[i], edges[i]);
            EdmondsKarp graph1 = new EdmondsKarp(grafo1);
            int source = 0;
            int sink = vertices[i] - 1;
            //grafo1.printGraph();
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i]);
            System.out.println("El flujo maximo es: " + graph1.edmondsKarp(source, sink));
            System.out.println("Comparaciones: " + graph1.getComparisons());
            System.out.println("Asignaciones: " + graph1.getAssignments());

            Grafo grafo2 = new Grafo(vertices[i], edges[i + 4]);
            EdmondsKarp graph2 = new EdmondsKarp(grafo2);
            //grafo2.printGraph();
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i + 4]);
            System.out.println("El flujo maximo es: " + graph2.edmondsKarp(source, sink));
            System.out.println("Comparaciones: " + graph2.getComparisons());
            System.out.println("Asignaciones: " + graph2.getAssignments());
        }
        //Grafo con el algoritmo FordFulkersen
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < vertices.length; i++) {
            Grafo graph1 = new Grafo(vertices[i], edges[i]);
            Grafo graph2 = new Grafo(vertices[i], edges[i + 4]);
            FordFulkerson fordFulkerson = new FordFulkerson();
            int source = 0;
            int sink = vertices[i] - 1;

            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i]);
            //graph1.printGraph();
            System.out.println("El flujo maximo es: " + fordFulkerson.fordFulkerson(graph1, source, sink));
            System.out.println("Comparaciones: " + fordFulkerson.getComparisons());
            System.out.println("Asignaciones: " + fordFulkerson.getAssignments());

            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i + 4]);
            //graph2.printGraph();
            System.out.println("El flujo maximo es: " + fordFulkerson.fordFulkerson(graph2, source, sink));
            System.out.println("Comparaciones: " + fordFulkerson.getComparisons());
            System.out.println("Asignaciones: " + fordFulkerson.getAssignments());
        }
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < vertices.length; i++) {
            Grafo graph1 = new Grafo(vertices[i], edges[i]);
            Grafo graph2 = new Grafo(vertices[i], edges[i + 4]);
            Dinic dinic1 = new Dinic(vertices[i]);
            Dinic dinic2 = new Dinic(vertices[i]);
            int source = 0;
            int sink = vertices[i] - 1;

            // Initialize Dinic with graph1
            dinic1.initializeFromGrafo(graph1);
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i]);
            System.out.println("El flujo maximo es: " + dinic1.maxFlow(source, sink));

            // Initialize Dinic with graph2
            dinic2.initializeFromGrafo(graph2);
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i + 4]);
            System.out.println("El flujo maximo es: " + dinic2.maxFlow(source, sink));
        }
    }
}


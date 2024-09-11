package main;

public class Main {
    public static void main(String[] args) {
        int[] vertices = {10, 20, 40, 80};
        int[] edges = {12, 24, 48, 56, 100, 400, 1600, 6400};

        for (int i = 0; i < vertices.length; i++) {
            // Inicialización de los grafos
            Grafo grafo1 = new Grafo(vertices[i], edges[i]);
            Grafo grafo2 = new Grafo(vertices[i], edges[i + 4]);
            //grafo1.printGraph(); // Opcional, para imprimir el grafo 1
            //grafo2.printGraph(); // Opcional, para imprimir el grafo 2

            // Inicialización de los puntos de inicio y final del grafo
            int source = 0;
            int sink = vertices[i] - 1;

            // Inicialización del algoritmo Edmonds-Karp
            System.out.println("EdmondsKarp");
            EdmondsKarp edmonds_Karp = new EdmondsKarp(grafo1);
            EdmondsKarp edmonds_Karp2 = new EdmondsKarp(grafo2);
            
            // Reiniciar contadores
            edmonds_Karp.resetCounters();
            edmonds_Karp2.resetCounters();

            // Muestra de información con grafo 1
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i]);
            System.out.println("El flujo maximo es: " + edmonds_Karp.edmondsKarp(source, sink));
            System.out.println("Comparaciones: " + edmonds_Karp.getComparisons());
            System.out.println("Asignaciones: " + edmonds_Karp.getAssignments());

            // Muestra de información con grafo 2
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i + 4]);
            System.out.println("El flujo maximo es: " + edmonds_Karp2.edmondsKarp(source, sink));
            System.out.println("Comparaciones: " + edmonds_Karp2.getComparisons());
            System.out.println("Asignaciones: " + edmonds_Karp2.getAssignments());

            System.out.println("############################################################");

            // Inicialización del algoritmo Ford-Fulkerson
            System.out.println("FordFulkerson");
            FordFulkerson fordFulkerson1 = new FordFulkerson();
            FordFulkerson fordFulkerson2 = new FordFulkerson();

            // Reiniciar contadores
            fordFulkerson1.resetCounters();
            fordFulkerson2.resetCounters();

            // Muestra de información con grafo 1
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i]);
            int maxFlowFordFulkerson1 = fordFulkerson1.fordFulkerson(grafo1.getCapacity(), source, sink, grafo1.getV());
            System.out.println("El flujo maximo es: " + maxFlowFordFulkerson1);
            System.out.println("Comparaciones: " + fordFulkerson1.getComparisons());
            System.out.println("Asignaciones: " + fordFulkerson1.getAssignments());

            // Muestra de información con grafo 2
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i + 4]);
            int maxFlowFordFulkerson2 = fordFulkerson2.fordFulkerson(grafo2.getCapacity(), source, sink, grafo2.getV());
            System.out.println("El flujo maximo es: " + maxFlowFordFulkerson2);
            System.out.println("Comparaciones: " + fordFulkerson2.getComparisons());
            System.out.println("Asignaciones: " + fordFulkerson2.getAssignments());

            System.out.println("############################################################");

            // Inicialización del algoritmo Dinic
            System.out.println("Dinic");
            Dinic dinic1 = new Dinic(grafo1);
            Dinic dinic2 = new Dinic(grafo2);

            // Reiniciar contadores
            dinic1.resetCounters();
            dinic2.resetCounters();

            // Muestra de información con grafo 1
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i]);
            int maxFlowDinic1 = dinic1.maxFlow(source, sink);
            System.out.println("El flujo maximo es: " + maxFlowDinic1);
            System.out.println("Comparaciones: " + dinic1.getComparisons());
            System.out.println("Asignaciones: " + dinic1.getAssignments());

            // Muestra de información con grafo 2
            System.out.println("Numero de vertices: " + vertices[i] + ", Numero de arcos: " + edges[i + 4]);
            int maxFlowDinic2 = dinic2.maxFlow(source, sink);
            System.out.println("El flujo maximo es: " + maxFlowDinic2);
            System.out.println("Comparaciones: " + dinic2.getComparisons());
            System.out.println("Asignaciones: " + dinic2.getAssignments());

            System.out.println("-------------------------------------------------\n");
        }
    }
}



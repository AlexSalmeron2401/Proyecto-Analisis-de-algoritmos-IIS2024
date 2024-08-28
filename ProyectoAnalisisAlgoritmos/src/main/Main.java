package main;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(10, 12); // Crear un grafo con 10 vértices y 12 arcos
        EdmondsKarp ek = new EdmondsKarp(grafo);
        int fuente = 0;
        int sumidero = 9;
        int maxFlujo = ek.edmondsKarp(fuente, sumidero);
        System.out.println("El flujo maximo es: " + maxFlujo);
    }
}

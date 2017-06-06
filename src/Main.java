import Algos.*;
import GraphTools.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {

        // --------------- création du graph ---------------


//        GraphTools.Graph metroGraph = new GraphTools.Graph();
//        metroGraph.addLine("1");
//        metroGraph.addLine("2");
//        metroGraph.addLine("3");
//        metroGraph.addLine("3b");
//        metroGraph.addLine("4");
//        metroGraph.addLine("5");
//        metroGraph.addLine("6");
//        metroGraph.addLine("7");
//        metroGraph.addLine("7b");
//        metroGraph.addLine("8");
//        metroGraph.addLine("9");
//        metroGraph.addLine("10");
//        metroGraph.addLine("11");
//        metroGraph.addLine("12");
//        metroGraph.addLine("13");
//        metroGraph.addLine("14");
//        metroGraph.saveInJson();
//

        // --------------- récupération du Graph ---------------
        System.out.println("");
        System.out.println("on charge le graph");

        Graph metroGraph = Graph.loadJson();


        // --------------- bfs ---------------
        System.out.println("");
        System.out.println("avec BFS, de Marcel Sembat à Gare de Lyon");
        Bfs bfs = new Bfs(metroGraph,"Marcel Sembat");
        bfs.printSPWithNames("Gare de Lyon");


        // --------------- Dijkstra ---------------
        System.out.println("");
        System.out.println("avec Dijkstra , de Marcel Sembat à Gare de Lyon");
        Dijkstra dijkstra = new Dijkstra(metroGraph,"Marcel Sembat");
        dijkstra.printSPWithNames("Gare de Lyon");

        // --------------- Un pathFinder ---------------
        System.out.println("");
        System.out.println("avec beaucoup de dijkstra");
        DijkstraSet dijkstraSet = new DijkstraSet(metroGraph);

        System.out.println("Diamètre " + dijkstraSet.getDiameter());
        dijkstraSet.printDiamaterPath();
        System.out.println("Rayon " + dijkstraSet.getRadius());
        dijkstraSet.printCenterPath();


        // --------------- la clusterisation ---------------
        System.out.println("");
        System.out.println("la clusterisation");
//        Graph subGraph = Graph.graphExtract(metroGraph,new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,55,56)),true);
        Clusteriser clusteriser1 = new Clusteriser(10,metroGraph);
        clusteriser1.print();


        System.out.println("fini");

    }
}

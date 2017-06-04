import Algos.Clusteriser;
import Algos.DijkstraSet;
import GraphTools.Graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
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
        Graph metroGraph = Graph.loadJson();
        DijkstraSet dijkstraSet = new DijkstraSet(metroGraph);
//        System.out.println("Diamètre" + dijkstraSet.getDiameterIndex());
//        System.out.println("Rayon" + dijkstraSet.getCenterIndex());
        dijkstraSet.printCenterPath();
        dijkstraSet.printDiamaterPath();
//        Bfs bfs = new Bfs(metroGraph,"Marcel Sembat");
//        bfs.printSPWithNames("Gare de Lyon");
//        Dijkstra dijkstra = new Dijkstra(metroGraph,"Marcel Sembat");
//        dijkstra.printSPWithNames("Gare de Lyon");

        Graph subGraph = Graph.graphExtract(metroGraph,new ArrayList<Integer>(Arrays.asList(0,1,2,3)),true);
        Clusteriser clusteriser = new Clusteriser(4,subGraph);
        System.out.println("fini");

    }
}

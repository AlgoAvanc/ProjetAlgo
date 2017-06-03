import GraphTools.Graph;

import java.io.*;

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

        Graph metroGraph = Graph.loadJson();
        System.out.println("fini");


    }
}

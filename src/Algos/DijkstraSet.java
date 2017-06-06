package Algos;

import GraphTools.Edge;
import GraphTools.Graph;
import GraphTools.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by etien on 04/06/2017.
 */
public class DijkstraSet{
    protected ArrayList<Dijkstra> dijkstraS;
    protected ArrayList<Integer> brockenIndexes; // utile si ne graph n'est pas connecté
    protected ArrayList<Integer> connectedIndexes;
    protected Graph baseGraph;


    // --------------- Constructeur ---------------

    public DijkstraSet(Graph baseGraph) {
        this.brockenIndexes = new ArrayList<Integer>();
        this.connectedIndexes = new ArrayList<Integer>();
        this.baseGraph = baseGraph;
        update();

    }
    public void update(){
        this.dijkstraS = new ArrayList<Dijkstra>();
        baseGraph.setEdges(Edge.resetBetweeness(baseGraph.getEdges()));//si non la beetweeness s'envole
        ArrayList<ArrayList<Integer>> brockenIndexesList =  new ArrayList<ArrayList<Integer>>();
        for (String id :baseGraph.getNodesIndex()){
            this.dijkstraS.add(new Dijkstra(baseGraph,id));
        }
        brockenIndexesListHandeler();

    }

    // --------------- Getters ---------------


    public ArrayList<Integer> getBrockenIndexes() {
        return brockenIndexes;
    }

    public int getindexOfCentralDijkstra (){
        int indexOfCentralDijkstra = 0;
        Double smallestExentricity = Double.POSITIVE_INFINITY;
        for (int i = 0; i < dijkstraS.size(); i++) {
            Double thisExentricity = dijkstraS.get(i).getExentricity();
            smallestExentricity = Double.min(smallestExentricity,thisExentricity);
            if (thisExentricity.equals(smallestExentricity)){indexOfCentralDijkstra=i;}
        }
        return indexOfCentralDijkstra;
    }
    public Double getRadius (){
        return dijkstraS.get(getindexOfCentralDijkstra()).getExentricity();
    }

    public  int getindexOfDiametralDijkstra (){
        int indexOfDiametralDijkstra = 0;
        Double biggestExentricity = 0.;
        for (int i = 0; i < dijkstraS.size(); i++) {
            Double thisExentricity = dijkstraS.get(i).getExentricity();
            biggestExentricity = Double.max(biggestExentricity,thisExentricity);
            if (thisExentricity.equals(biggestExentricity)){indexOfDiametralDijkstra = i;}
        }
        return indexOfDiametralDijkstra;
    }
    public Double getDiameter (){
        return dijkstraS.get(getindexOfDiametralDijkstra()).getExentricity();
    }

    // --------------- printers ---------------
    public void printCenterPath(){
        dijkstraS.get(getindexOfCentralDijkstra()).prinntExentricityWithNames();
    }
    public void printDiamaterPath(){
        dijkstraS.get(getindexOfDiametralDijkstra()).prinntExentricityWithNames();
    }
    public void print(){baseGraph.printSimple();}
    public void printWithInfos(){baseGraph.printWithInfos();}


    // --------------- Checkers ---------------
    public void deleteEdgesWithBitwinnessOver (int maxBeetwiness){
//        boolean edgesDeleted = false;
        for (int i = 0; i <baseGraph.getEdges().size() ; i++) {
            Edge edge = baseGraph.getEdges().get(i);
            if (edge.getBetweenness()>maxBeetwiness){
                baseGraph.removeEdge(edge);
//                edgesDeleted = true;
            }
        }
//        return edgesDeleted;
    }

    // --------------- Pour la clusterisation ---------------
    public static ArrayList<DijkstraSet> splitIfBrocken(DijkstraSet dijkstraSet){
        ArrayList<DijkstraSet> list = new ArrayList<DijkstraSet>();

        if(dijkstraSet.isBrocken()){
            Graph graph1 = Graph.graphExtract(dijkstraSet.baseGraph,dijkstraSet.brockenIndexes,true);
            Graph graph2 = Graph.graphExtract(dijkstraSet.baseGraph,dijkstraSet.brockenIndexes,false);
            list.add(new DijkstraSet(graph1));
            list.add(new DijkstraSet(graph2));
            return list;
        }else {
            list.add(dijkstraSet);
            return list;
        }

    }
    public boolean isBrocken(){
        return (! brockenIndexes.isEmpty());
    }

    public boolean hasBeetweenessOver (int maxBeetweeness){
        for (Edge edge : baseGraph.getEdges()) {
            if (edge.getBetweenness()>maxBeetweeness){return true;}
        }
        return false;
    }
    protected void brockenIndexesListHandeler (){
        connectedIndexes = new ArrayList<Integer>();
        brockenIndexes = new ArrayList<Integer>();
        // --------------- on remplis le connectedIndex ---------------
        for (int i = 0; i < this.dijkstraS.size(); i++) {
            ArrayList<Integer> brockenIndexHere = dijkstraS.get(i).brockenIndexes;
            ArrayList<Integer> connectedIndexHere = dijkstraS.get(i).connectedIndexes;
            int numberOfConnectedHere = connectedIndexHere.size();
            connectedIndexHere.removeAll(connectedIndexes); // on enlève les communs;
            int numberOfConnectedHereNotInConnected = connectedIndexHere.size();

            if (connectedIndexes.isEmpty() || (numberOfConnectedHere> numberOfConnectedHereNotInConnected)){ //si les deux connected (qui représentent des nouds connectés enshortestpath) ont des noeuds encommuns
                connectedIndexes.removeAll(connectedIndexHere); //alors on les merge (en fait ils peuvent etre différents et avoir des noeuds en communs du fait que le graph est orienté)
                connectedIndexes.addAll(connectedIndexHere);
            }
        }
        // --------------- on s'occupe du brockenIndex ---------------
        for (int i = 0; i <this.dijkstraS.size() ; i++) {
            brockenIndexes.add(i);
        }
        brockenIndexes.removeAll(connectedIndexes);
//        System.out.println();

//        System.out.println("size" + dijkstraS.size());
//        System.out.println("connectedIndexes " + connectedIndexes);
//        System.out.println("brockenIndexes "+brockenIndexes);
    }

    // --------------- Draw ---------------
    public GraphPlot draw(GraphPlot graphPlot){
        return  this.baseGraph.draw(graphPlot);
    }
    public GraphPlot draw(){
        GraphPlot graphPlot = new GraphPlot();
        return  this.baseGraph.draw(graphPlot);
    }
    public static GraphPlot drawList (ArrayList<DijkstraSet> dsList, GraphPlot graphPlot){
        for (DijkstraSet ds:dsList) {
            ds.baseGraph.draw(graphPlot);
        }
        return graphPlot;
    }
}
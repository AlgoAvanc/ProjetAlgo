package Algos;

import GraphTools.Edge;
import GraphTools.Graph;

import java.util.ArrayList;

/**
 * Created by etien on 04/06/2017.
 */
public class DijkstraSet{
    protected ArrayList<Dijkstra> dijkstraS;
    protected ArrayList<Integer> brockenIndexes; // utile si ne graph n'est pas connecté
    protected Graph baseGraph;

    // --------------- Constructeur ---------------

    public DijkstraSet(Graph baseGraph) {
        this.dijkstraS = new ArrayList<Dijkstra>();
        this.brockenIndexes = new ArrayList<Integer>();
        this.baseGraph = baseGraph;
        update();

    }
    public void update(){
        baseGraph.setEdges(Edge.resetBetweeness(baseGraph.getEdges()));
        for (String id :baseGraph.getNodesIndex()){
            Dijkstra dijkstraToAdd = new Dijkstra(baseGraph,id);
            this.dijkstraS.add(dijkstraToAdd);
            if (!dijkstraToAdd.getBrockenIndexes().isEmpty()){
                this.brockenIndexes = dijkstraToAdd.getBrockenIndexes();
                break;
            }
        }
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


    // --------------- Checkers ---------------
    public void deleteEdgesWithBitwinnessOver (int maxBeetwiness){
        for (int i = 0; i <baseGraph.getEdges().size() ; i++) {
            Edge edge = baseGraph.getEdges().get(i);
            if (edge.getBetweenness()>maxBeetwiness){
                baseGraph.removeEdge(edge);
            }
        }
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

}

package Algos;

import GraphTools.Edge;
import GraphTools.Graph;
import GraphTools.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
    protected ArrayList<Integer> previous;
    protected ArrayList<Boolean> marked;
    protected ArrayList<Double> distance;
    protected Graph baseGraph;
    protected ArrayList<Integer> brockenIndexes; // utile si ne graph n'est pas connecté
    protected ArrayList<Integer> connectedIndexes; // utile si ne graph n'est pas connecté

    public Dijkstra(Graph graph,String startName){
        this(graph,graph.getStationNodeIndex(startName));
    }
    public Dijkstra(Graph graph, int startIndex) {

        // --------------- on initialise ---------------

        int cardinality = graph.getOrder();
        marked =  new ArrayList<>(Arrays.asList(new Boolean[cardinality]));
        previous =  new ArrayList<>(Arrays.asList(new Integer[cardinality]));
        distance =  new ArrayList<>(Arrays.asList(new Double[cardinality]));
        brockenIndexes = new ArrayList<Integer>();
        connectedIndexes = new ArrayList<Integer>();
        Collections.fill(marked, Boolean.FALSE);
        Collections.fill(distance, Double.POSITIVE_INFINITY);
        this.baseGraph=graph;

        if (verifyNonNegative(baseGraph)){

        }else{
            System.out.println("il y a des veleurs négatives dans ce graph");
        }

        distance.set(startIndex, 0.);
        ArrayList<Double> unvisitedDistance = (ArrayList<Double>)distance.clone();
        int beingVisited = -1;

        // --------------- l'algorithme en soi ---------------

        while (marked.indexOf(false)!= -1){
            beingVisited = unvisitedDistance.indexOf(Collections.min(unvisitedDistance)); //on prend le noaud à plus petite distance
            if (!(distance.get(beingVisited)!= Double.POSITIVE_INFINITY && !marked.get(beingVisited))){
                while (marked.indexOf(false)!= -1){// on a plusieurs sous graphes non connectés car le minimunm des non marqués est à l'infini
                    int brockenIndex = marked.indexOf(false);
                    brockenIndexes.add(brockenIndex);
                    marked.set(brockenIndex,true);
                }
//                brockenIndexes.add(beingVisited);
            }else{
                connectedIndexes.add(beingVisited);
                marked.set(beingVisited,true);
                unvisitedDistance.set(beingVisited,Double.POSITIVE_INFINITY);// on met à l'infini ce noeud pour ne plus le re-sélectionner
                List<Edge> edges = baseGraph.getNodeFromIndex(beingVisited).getEdgesList(); // on en prend les edges
                if (edges!=null){ // si le vertex a quelque lien
                    for (Edge edge:edges) {
                        int nodeTo = baseGraph.getIndexOfNode(edge.getTo());
//                    int nodeFrom = baseGraph.getIndexOfNode(edge.getFrom());
                        if (!marked.get(nodeTo)){ //on n'update que les vertex non marqués
                            Double previousDistance = distance.get(nodeTo);
                            Double newDistance = distance.get(beingVisited)+edge.getWeight();
                            Double smallestDistance = Double.min(previousDistance,newDistance);
                            distance.set(nodeTo, smallestDistance ); // on update la distance au minimum entre la précédente et celle qui part de ce noeud
                            unvisitedDistance.set(nodeTo, distance.get(nodeTo)); //on update les distances à visiter
                            if (smallestDistance.equals(newDistance) ){
                                previous.set(nodeTo,beingVisited); // on donne le bon précédent
                                edge.setBetweenness(edge.getBetweenness()+1);
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean verifyNonNegative(Graph graph){
        for (Edge edge : graph.getEdges()) {
         if (edge.getWeight()<0){return true;}
        }
        return true;
    }
    public boolean hasPathTo (int v){
        return (marked.get(v)); // si c'est marqué, alors on est passé par v en partant de s
    }
    public double distTo( int v){
        return (distance.get(v));
    }
    public ArrayList<Integer> PathGetter (int v){
        ArrayList<Integer> path = new ArrayList<>();
        if (hasPathTo(v)){
            int vertex = v;
            while (distance.get(vertex)>0){
                path.add(0,vertex);
                vertex = previous.get(vertex);
            }
            path.add(0,distance.indexOf(0.0));
        }
        return path;
    }



    // --------------- printers ---------------

    public void printSPWithNames (String nodeName){
        int index = baseGraph.getStationNodeIndex(nodeName);
        System.out.println("path : "+baseGraph.NameAPathOfIndexes(getShortestPathOfIndexes(index))+" | distance : "+this.distTo(index));
    }
    public void prinntExentricityWithNames (){
        int exentricityIndex=distance.indexOf(getExentricity());
        this.printSPWithNames(baseGraph.getNodeFromIndex(exentricityIndex).getId());
    }
    public void printSP (int v){
        System.out.println("path : "+this.getShortestPathOfIndexes(v)+" | distance : "+this.distTo(v));
    }

    public void print () {
        System.out.println("marked " + marked);
        System.out.println("previous " + previous);
        System.out.println("distance " + distance);
    }

    // --------------- Getters ---------------
    public Double getExentricity (){
        return Collections.max(distance);
    }
//    public ArrayList<Integer> getPathOfIndexOfExentricity (){
//        int indexOfExentricity = distance.indexOf(getExentricity());
//        return getShortestPathOfIndexes(indexOfExentricity);
//    }
    public ArrayList<Integer> getShortestPathOfIndexes(int v){
        return PathGetter(v);
    }

    public ArrayList<Integer> getPrevious() {
        return previous;
    }

    public ArrayList<Double> getDistance() {
        return distance;
    }
    public ArrayList<Integer> getBrockenIndexes(){
        return brockenIndexes;
    }

    public ArrayList<Integer> getConnectedIndexes() {
        return connectedIndexes;
    }

}

package Algos;



import GraphTools.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Algos.Library.BubleSortArrylist;

public class Bfs {
    protected ArrayList<Boolean> marked;
    protected ArrayList<Integer> previous;
    protected ArrayList<Integer> distance;
    protected Graph baseGraph;

    public Bfs(Graph graph, String startName){
        this(graph,graph.getStationNodeIndex(startName));
    }
    public Bfs(Graph graph, int startIndex) {
        int cardinality = graph.getOrder();
        marked =  new ArrayList<>(Arrays.asList(new Boolean[cardinality]));
        previous =  new ArrayList<>(Arrays.asList(new Integer[cardinality]));
        distance =  new ArrayList<>(Arrays.asList(new Integer[cardinality]));
        Collections.fill(marked, Boolean.FALSE);
        baseGraph = graph;

        ArrayList<Integer> toVisit = new ArrayList<>();
        toVisit.add(startIndex);
        while (!toVisit.isEmpty()) {
            int beingVisited = toVisit.get(0);
            toVisit.remove(0);
            if (beingVisited == startIndex){
                distance.set(beingVisited,0);
            } else {distance.set(beingVisited, distance.get( previous.get(beingVisited) ) +1 );}

            ArrayList neighbours = graph.getNeighboursIndexOfNodeIndex(beingVisited);
            if (neighbours != null){
                ArrayList<Integer> sortedNeighbours = BubleSortArrylist(neighbours);
                for (int n = 0 ; n<sortedNeighbours.size(); n++){
                    int neightbour = sortedNeighbours.get(n);
                    if (!marked.get(neightbour)){
                        previous.set(neightbour,beingVisited);
                        toVisit.add(neightbour);
                    }
                }
            }
            marked.set(beingVisited, true);
        }
    }

    public boolean hasPathTo (int v){
        return (marked.get(v)); // si c'est marqué, alors on est passé par v en partant de s
    }
    public int distTo( int v){
        return (distance.get(v));
    }
    public ArrayList<Integer> SP (int v){
        ArrayList<Integer> path = new ArrayList<>();
        int vertex = v;
        while (distance.get(vertex)>0){
            path.add(0,vertex);
            vertex = previous.get(vertex);
        }
        return path;
    }
    public ArrayList<String> SPWithNames (String nodeName){
        ArrayList<Integer> pathIndex = SP(baseGraph.getStationNodeIndex(nodeName));
        ArrayList<String> path = new ArrayList<String>();
        List<String> nodeNames = baseGraph.getNodesIndex();
        for (Integer nodeIndex:pathIndex) {
            path.add(nodeNames.get(nodeIndex));
        }
        return path;
    }
    public void printSP (int v){
        System.out.println("path : "+this.SP(v)+" | Nombre d'arrets : "+this.distTo(v));
    }
    public void printSPWithNames (String nodeName){
        System.out.println("path : "+this.SPWithNames(nodeName)+" | Nombre d'arrets : "+this.distTo(baseGraph.getStationNodeIndex(nodeName)));
    }

    public void print () {
        System.out.println("marked " + marked);
        System.out.println("previous " + previous);
        System.out.println("distance " + distance);
    }
}



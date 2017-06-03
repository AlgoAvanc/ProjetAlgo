package Algos;



import GraphTools.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static Algos.Library.BubleSortArrylist;

public class Bfs {
    public ArrayList<Boolean> marked;
    public ArrayList<Integer> previous;
    public ArrayList<Integer> distance;

    public Bfs(Graph graph) {
        int cardinality = graph.getOrder();
        marked =  new ArrayList<>(Arrays.asList(new Boolean[cardinality]));
        previous =  new ArrayList<>(Arrays.asList(new Integer[cardinality]));
        distance =  new ArrayList<>(Arrays.asList(new Integer[cardinality]));
        Collections.fill(marked, Boolean.FALSE);
    }
    public void bfsDigraph(Graph graph, int startIndex){
        ArrayList<Integer> toVisit = new ArrayList<>();
        toVisit.add(startIndex);
        while (!toVisit.isEmpty()) {
            int beingVisitedIndex = toVisit.get(0);
            toVisit.remove(0);
            if (beingVisitedIndex == startIndex){
                distance.set(beingVisitedIndex,0);
            } else {distance.set(beingVisitedIndex, distance.get( previous.get(beingVisitedIndex) ) +1 );}

            ArrayList neighbours = graph.getIndexOfNodeNeighbours(beingVisitedIndex);
            if (neighbours != null){
                ArrayList<Integer> sortedNeighbours = BubleSortArrylist(neighbours);
                for (int n = 0 ; n<sortedNeighbours.size(); n++){
                    int neightbour = sortedNeighbours.get(n);
                    if (!marked.get(neightbour)){
                        previous.set(neightbour,beingVisitedIndex);
                        toVisit.add(neightbour);
                    }
                }
            }
            marked.set(beingVisitedIndex, true);
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
    public void printSP (int v){
        System.out.println("path : "+this.SP(v)+" | distance : "+this.distTo(v));
    }

    public void print () {
        System.out.println("marked " + marked);
        System.out.println("previous " + previous);
        System.out.println("distance " + distance);
    }
}



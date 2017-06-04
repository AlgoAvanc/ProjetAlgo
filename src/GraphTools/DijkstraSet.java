package GraphTools;

import Algos.Dijkstra;

import java.util.ArrayList;

/**
 * Created by etien on 04/06/2017.
 */
public class DijkstraSet{
    protected ArrayList<Dijkstra> dijkstraS;
    protected Graph baseGraph;

    // --------------- Constructeur ---------------

    public DijkstraSet(Graph baseGraph) {
        this.dijkstraS = new ArrayList<Dijkstra>();
        this.baseGraph = baseGraph;
        for (String id :baseGraph.nodesIndex){
            this.dijkstraS.add(new Dijkstra(baseGraph,id));
        }

    }


    // --------------- Getters ---------------

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

}

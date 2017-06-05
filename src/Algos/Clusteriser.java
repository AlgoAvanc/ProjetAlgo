package Algos;

import GraphTools.Graph;

import java.util.ArrayList;

/**
 * Created by etien on 04/06/2017.
 */
public class Clusteriser {
    protected int maxBeetwinnessAllowed;
    protected ArrayList<DijkstraSet> dijkstraSetList;
    protected GraphPlot graphPlot;

    public Clusteriser(int maxBeetwinnessAllowed, Graph graph) {
        this.graphPlot = new GraphPlot();
        this.maxBeetwinnessAllowed = maxBeetwinnessAllowed;
        this.dijkstraSetList = new ArrayList<DijkstraSet>();
        this.dijkstraSetList.add( new DijkstraSet(graph));
        this.dijkstraSetList = clusterize(new DijkstraSet(graph));
        DijkstraSet.drawList(dijkstraSetList,graphPlot);
//        clusterizeDecreasing();

    }

    public void clusterizeDecreasing() {
        for (int i = maxBeetwinnessAllowed+100; i >= maxBeetwinnessAllowed; i-=10) {
            for (int j = 0; j <dijkstraSetList.size() ; j++) {
                DijkstraSet dijkstraSet = dijkstraSetList.get(j);
                dijkstraSetList.remove(dijkstraSet);
                ArrayList<DijkstraSet> dijkstraSetListToAdd = clusterize(i,dijkstraSet);
                dijkstraSetList.removeAll(dijkstraSetListToAdd) ;//on merge
                dijkstraSetList.addAll(dijkstraSetListToAdd) ;
            }
        }
        DijkstraSet.drawList(dijkstraSetList,graphPlot);
//        graphPlot = new GraphPlot();

    }

    public ArrayList<DijkstraSet> clusterize(int maxBeetwinnessAllowed ,DijkstraSet dijkstraSet) {
        ArrayList<DijkstraSet> list = new ArrayList<DijkstraSet>();

        dijkstraSet.deleteEdgesWithBitwinnessOver(maxBeetwinnessAllowed);
        list.addAll(DijkstraSet.splitIfBrocken(dijkstraSet));
        for (DijkstraSet dS: list) {
            dS.update();
            if (dS.isBrocken() || dS.hasBeetweenessOver(maxBeetwinnessAllowed)){

                list.addAll(clusterize(maxBeetwinnessAllowed,dS));
                list.remove(dS);
            }
        }

        return list;

    }
    public ArrayList<DijkstraSet> clusterize (DijkstraSet dijkstraSet) {
        return clusterize(this.maxBeetwinnessAllowed,dijkstraSet);
    }


}
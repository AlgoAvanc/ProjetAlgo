package Algos;

import GraphTools.Graph;

import java.util.ArrayList;

/**
 * Created by etien on 04/06/2017.
 */
public class Clusteriser {
    protected int maxBeetwinnessAllowed;
    protected ArrayList<DijkstraSet> dijkstraSetS;

    public Clusteriser(int maxBeetwinnessAllowed, Graph graph) {
        this.maxBeetwinnessAllowed = maxBeetwinnessAllowed;
        this.dijkstraSetS = clusterize(new DijkstraSet(graph));
    }

    public ArrayList<DijkstraSet> clusterize(DijkstraSet dijkstraSet) {
        ArrayList<DijkstraSet> list = new ArrayList<DijkstraSet>();

        dijkstraSet.deleteEdgesWithBitwinnessOver(maxBeetwinnessAllowed);
        list.addAll(DijkstraSet.splitIfBrocken(dijkstraSet));
        for (DijkstraSet dS: list) {
            dS.update();
            if (dS.isBrocken() || dS.hasBeetweenessOver(maxBeetwinnessAllowed)){
                list.addAll(clusterize(dS));
                list.remove(dS);
            }
        }
        return list;
    }


}
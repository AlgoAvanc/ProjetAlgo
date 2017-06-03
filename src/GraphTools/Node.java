package GraphTools;

import java.util.ArrayList;
import java.util.List;

public class Node {
    protected String id;
    protected ArrayList<Edge> edgesList;
    protected double latitude;
    protected double longitude;
    protected ArrayList<String> lines;


    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.edgesList = new ArrayList<Edge>();
        this.lines = new ArrayList<String>();

    }
    public Node (){
        this.edgesList = new ArrayList<Edge>();
        this.lines = new ArrayList<String>();
    } //node vide


    public void addEdge (Edge edge){
        this.edgesList.add(edge);
    }
    public void addLine (String line){
        this.lines.add(line);
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------------- Merge ------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void merge(Node nodeToMerge){
        this.mergeEdgeList(nodeToMerge.getEdgesList());
        this.lines.removeAll(nodeToMerge.getLines());
        this.lines.addAll(nodeToMerge.getLines());
    }

    public void mergeEdgeList(ArrayList<Edge> edgesListToMerge){
        for (Edge edge: edgesListToMerge
             ) {
            edge.setFrom(this);//on réoriente les from qui sont appelés par référence
        }
        this.edgesList.removeAll(edgesListToMerge);
        this.edgesList.addAll(edgesListToMerge);
        this.edgesList = Edge.removeDuplicatesInList(this.edgesList);
    }


    // --------------- Consolidate ---------------

    public void consolidateEdgeOfNode (Edge edge){
        if (edge.getFromId().equals(this.id)){
//            for (GraphTools.Edge subEdge :this.edgesList) {
            for (int i = 0; i <this.edgesList.size() ; i++) {
                Edge subEdge = this.edgesList.get(i);
                if (subEdge.getToId().equals(edge.getToId()) && subEdge.length==edge.getLength()){
                    this.edgesList.set(i,edge);
                }
            }
        }
    }


    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------- Getters & Setters ------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public ArrayList<Edge> getEdgesList() {
        return edgesList;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEdgesList(ArrayList<Edge> edgesList) {
        this.edgesList = edgesList;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }
}

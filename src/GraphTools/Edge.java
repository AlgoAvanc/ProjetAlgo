package GraphTools;

import Algos.Library;

import java.util.List;

public class Edge {
transient protected Node from;
protected String fromId; // on garde les noms car on les retiens pour la serialization
transient protected Node to;
protected String toId;
protected double length;

    public Edge(Node from, Node to) {
        this.from = from;
        this.fromId = from.getId();
        this.to = to;
        this.toId = to.getId();
        this.length = Library.meterDistanceBetweenGPSPoints(from.getLatitude(),from.getLongitude(),to.getLatitude(),to.getLongitude());
    }


    public boolean equals(Edge edge){
        return ( this.from.getId().equals(edge.getFrom().getId()) && this.to.getId().equals(edge.getTo().getId()) && (this.length == edge.getLength()) );
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Getters & setters ------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
        this.fromId = from.getId();
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
        this.toId = to.getId();
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

// ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Méthodes Statiques -----------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public static ArrayList<Edge> removeDuplicatesInList (ArrayList<Edge> edgesList){
        if (edgesList.size()<2){return edgesList;}
        for (int i = 0; i < edgesList.size(); i++) {
            Edge edge1 = edgesList.get(i);
            for (int j = edgesList.indexOf(edge1)+1; j < edgesList.size(); j++) {
                Edge edge2 = edgesList.get(j);
                boolean thereIsADuplicate = edge2!=null && edge1.equals(edge2) && edge1!=edge2 ;// si deux edges (existants) différents s'équivallent, on enlève celui de plus haut degré
                if ( thereIsADuplicate ){
                    edgesList.remove(j);
                    j--;// on reste au même j puisque le contennu de  j+1 viens de lui être associé
                }
            }
        }
        return edgesList;
    }
    public void consolidate (Graph graph){
        this.to = graph.getStationNode(toId);
        this.from = graph.getStationNode(fromId);

        this.from.consolidateEdgeOfNode(this);
    }
    public static ArrayList<Edge> consolidateEdgeList (ArrayList<Edge> edgesList,Graph graph){
        for (Edge edge:edgesList) {
            edge.consolidate(graph);
        }
        return edgesList;
    }

}

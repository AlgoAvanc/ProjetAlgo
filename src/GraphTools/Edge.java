package GraphTools;

import Algos.Library;

import java.util.List;

public class Edge {
transient protected Node from;
protected String fromId; // on garde les noms car on les retiens pour la serialization
transient protected Node to;
protected String toId;
protected double weight;
protected int betweenness;

    public Edge(Node from, Node to) {
        this.from = from;
        this.fromId = from.getId();
        this.to = to;
        this.toId = to.getId();
        this.weight = Library.kiloMeterDistanceBetweenGPSPoints(from.getLatitude(),from.getLongitude(),to.getLatitude(),to.getLongitude());
    }


    public boolean equals(Edge edge){
        return ( this.from.getId().equals(edge.getFrom().getId()) && this.to.getId().equals(edge.getTo().getId()) && (this.weight == edge.getWeight()) );
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

    public double getWeight() {
        return weight;
    }

    public void setWeigth(double length) {
        this.weight = length;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public Integer getBetweenness() {
        return betweenness;
    }

    public void setBetweenness(Integer betweenness) {
        this.betweenness = betweenness;
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Cloner ------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public Edge cloneButKeepNodes(){ // ca n'a pas d'intéret de garder les nodes mais c'est juste pour prévenir que la référence demaure
        Edge clone = new Edge(this.from,this.to);
        return clone;
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Méthodes Statiques -----------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public static List<Edge> removeDuplicatesInList (List<Edge> edgesList){
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
        this.to = graph.getNodeFromId(toId);
        this.from = graph.getNodeFromId(fromId);

        this.from.consolidateEdgeOfNode(this);
    }
    public static List<Edge> consolidateEdgeList (List<Edge> edgesList,Graph graph){
        for (Edge edge:edgesList) {
            edge.consolidate(graph);
        }
        return edgesList;
    }
    public static List<Edge> resetBetweeness (List<Edge> edgesList){
        for (Edge edge:edgesList) {
            edge.betweenness = 0;
        }
        return edgesList;
    }


}

import Algos.Library;

public class Edge {
protected Node from;
protected Node to;
protected double length;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.length = Library.meterDistanceBetweenGPSPoints(from.getLatitude(),from.getLongitude(),to.getLatitude(),to.getLongitude());
    }




    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Getters & setters ------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    List<Node> nodes;
    List<String>nodesIndex;
    List<Edge>edges;

    public Graph() {
        this.nodes = new ArrayList<Node>();
        this.nodesIndex = new ArrayList<String>();
        this.edges = new ArrayList<Edge>();
    }


    // ----------------------------------------------------------------------------------------------------------
    // ----------------------------------------- Add Functions --------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public  void  addLine(StopTimeHandler stopTimeHandler, StopHandler stopHandler){ //pour ajouter une ligne de métro
        Map<String, String> nameOfId = stopHandler.getNameOfId();
        Map<String, Map> stops = stopHandler.getStops();
        for (List<String> subStopList : new ArrayList<List>(stopTimeHandler.getStopList()) // on prend tous les sets de liste d'arrets
             ) {
            Graph subgraph = graphFromSmallListAndStopContent(subStopList,nameOfId,stops);
            this.merge(subgraph);
        }

    }
    protected Graph graphFromSmallListAndStopContent (List<String> smallList, Map<String, String> nameOfId,  Map<String,Map> stops){ // l'argument stops c'est du Map<String,Map<String,String>> en vrai mais il veux pas être aussi précis
        Graph subGraph = new Graph();
        Node oldNode = new Node();
        Node nowNode = new Node();
        for (int i = smallList.size()-1; i > -1 ; i--) { // on va décroissant car on ajoute des liens entre deux Edges existants (les deriers de listes ne sont liés à personne)

            // --------------- on créé le node ---------------

            String nodeName = nameOfId.get(smallList.get(i));
            Map<String,String> stop = stops.get(nodeName);
            oldNode = nowNode;
            nowNode = new Node(); // on ré instancie si non on ne va faire que réécrire sur la meme instance
            nowNode.setId(nodeName);
            nowNode.setLatitude(Double.parseDouble(stop.get("latitude")));
            nowNode.setLatitude(Double.parseDouble(stop.get("longitude")));
            nowNode.addLine(stop.get("ligne"));
            if (i!= smallList.size()-1){
                nowNode.addEdge(new Edge(nowNode,oldNode));
            }
            // --------------- les conséquences sur le graph ---------------
            subGraph.addNode(nowNode);
        }
        return subGraph;
    }

    public void addNode (Node node){
        this.nodes.add(node);
        this.nodesIndex.add(node.getId());
        this.edges.removeAll(node.getEdgesList());//au cas ou il y aie des duplicatas
        this.edges.addAll(node.getEdgesList());
    }
//arrayList1.removeAll(arrayList2); // remove duplicates
//arrayList1.addAll(arrayList2);  // merge

    public void merge (Graph graphToMerge){
        List<Node> toMergeNodes = graphToMerge.getNodes();
        List<Edge>toMergeEdges = graphToMerge.getEdges();

        for (Node toMergeNode:toMergeNodes //on prend chaque node à merger, si la station existe déjà, les deux mergent, si non, on la rajoute
             ) {
            Node localCorrespondingNode = getStationNode(toMergeNode.getId());
            if (localCorrespondingNode!=null){
                localCorrespondingNode.merge(toMergeNode);
            }else {
                this.nodes.add(toMergeNode);
                this.nodesIndex.add(toMergeNode.getId());
            }
        }

        // on réoriente les edge car ils sont nottés par référence (je crois)
        for (Edge toAjustEdge:toMergeEdges
             ) {
            Node from = toAjustEdge.getFrom();
            toAjustEdge.setFrom(this.getStationNode(from.getId()));//normalement le from est déjà géré par le node merge mais on insiste
            Node to = toAjustEdge.getTo();
            toAjustEdge.setTo(this.getStationNode(to.getId()));
        }

    }




    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------- Getters & Setters ------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------


    // --------------- les défaults ---------------

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<String> getNodesIndex() {
        return nodesIndex;
    }

    public void setNodesIndex(List<String> nodesIndex) {
        this.nodesIndex = nodesIndex;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }


    // --------------- les ajoutés ---------------
    public int getStationNodeIndex (String name){
        return this.nodesIndex.indexOf(name);
    }

    public Node getStationNode (String name){
        int index = getStationNodeIndex(name);
        if (index == -1){
            return null;
        }
        else {
            return this.nodes.get(index);
        }
    }

}

package GraphTools;

import Algos.Dijkstra;
import Algos.Library;
import DataExtraction.FileReader;
import DataExtraction.StopHandler;
import DataExtraction.StopTimeHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    // ----------------------------------------- Creation Functions ---------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void addLine(String line) throws IOException {
        FileReader fr1StopTimes = new FileReader(Library.stopTimesDirectory(line));
        StopTimeHandler handleStopTimes1 = new StopTimeHandler(fr1StopTimes);

        FileReader fr1Stops = new FileReader(Library.stopDirectory(line));
        StopHandler handleStops1 = new StopHandler(fr1Stops,line);

        this.addLine(handleStopTimes1,handleStops1);
    }

    protected   void  addLine(StopTimeHandler stopTimeHandler, StopHandler stopHandler){ //pour ajouter une ligne de métro
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
            nowNode.setLongitude(Double.parseDouble(stop.get("longitude")));
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

    protected void merge (Graph graphToMerge){
        List<Node>toMergeNodes = graphToMerge.getNodes();
        List<Edge>toMergeEdges = graphToMerge.getEdges();

        for (Node toMergeNode:toMergeNodes //on prend chaque node à merger, si la station existe déjà, les deux mergent, si non, on la rajoute
             ) {
            Node localCorrespondingNode = getNodeFromId(toMergeNode.getId());
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
            toAjustEdge.setFrom(this.getNodeFromId(from.getId()));//normalement le from est déjà géré par le node merge mais on insiste
            Node to = toAjustEdge.getTo();
            toAjustEdge.setTo(this.getNodeFromId(to.getId()));
        }
        this.edges.addAll(toMergeEdges);
        this.edges = Edge.removeDuplicatesInList(this.edges);

    }

    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------------ Fonctions de sauvegarde et de réparation ----------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void consolidate () {
        this.edges = Edge.consolidateEdgeList(this.edges,this);
    }
    public static Graph loadJson (){
        Gson gson = new Gson();
        List<String> rawLines = null;
        try {
            rawLines = Files.readAllLines(Paths.get(Library.DatasDirectory+"/data.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph graph = gson.fromJson(rawLines.get(0), Graph.class);
        graph.consolidate();
        return graph;
    }
    public void saveInJson (){
        Gson gson = new Gson();

        String myObjectJson = gson.toJson(this);
        List<String> jsonInput = new ArrayList<String>();
        jsonInput.add(myObjectJson);

        try {
            Files.write(Paths.get(Library.DatasDirectory+"/data.json"),jsonInput, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
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
    // nodes
    public int getStationNodeIndex (String name){
        return this.nodesIndex.indexOf(name);
    }
    public Node getNodeFromId (String name){
        int index = getStationNodeIndex(name);
        if (index == -1){
            return null;
        }
        else {
            return this.nodes.get(index);
        }
    }
    public int getIndexOfNode (Node node){
        return nodes.indexOf(node);
    }
    public Node getNodeFromIndex (int index) {
        return nodes.get(index);
    }
    //graph properties
    public int getOrder (){
        return nodesIndex.size();
    }
    public int getSize (){
        return edges.size();
    }

    //neighbourhood
    public ArrayList<Node> getNeighboursOfNodeIndex(int nodeIndex){
        Node node = nodes.get(nodeIndex);
        List<Edge> nodeEdges = node.getEdgesList();
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for (Edge edge: nodeEdges){
            neighbours.add(edge.to);
        }
        return neighbours;
    }
    public ArrayList<Integer> getNeighboursIndexOfNodeIndex(int nodeIndex){
        ArrayList<Node> neighbours = getNeighboursOfNodeIndex(nodeIndex);
        ArrayList<Integer> neighboursIndex = new ArrayList<Integer>();
        for (Node neighbour:neighbours) {
            neighboursIndex.add(getIndexOfNode(neighbour));
        }
        return neighboursIndex;
    }


    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------------------ Interpretes ---------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public ArrayList<String> NameAPathOfIndexes (ArrayList<Integer> pathOfIndexes){
        ArrayList<String> path = new ArrayList<String>();
        List<String> nodeNames = getNodesIndex();
        for (Integer nodeIndex:pathOfIndexes) {
            path.add(nodeNames.get(nodeIndex));
        }
        return path;
    }
    public ArrayList<Node> NodePathOfIndexes (ArrayList<Integer> pathOfIndexes){
        ArrayList<Node> path = new ArrayList<Node>();
        List<Node> nodes = getNodes();
        for (Integer nodeIndex:pathOfIndexes) {
            path.add(nodes.get(nodeIndex));
        }
        return path;
    }


    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Print ------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void printSimple () {
        System.out.println(nodesIndex);
    }

    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------------------ Coupure en 2 --------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

//    public static ArrayList<Graph> graphCut (Graph graph, ArrayList<Integer> indexGroup){
//        ArrayList<Graph> graphs = new ArrayList<Graph>();
//        graphs.add(Graph.graphExtract(graph,indexGroup,true));
//        graphs.add(Graph.graphExtract(graph,indexGroup,false));
//        return graphs;
//
//    }
    public static Graph graphExtract (Graph graph, ArrayList<Integer> indexGroup, boolean indexGroupIsToTake){
        Graph newGraph = new Graph();
        for (int i = 0; i <graph.nodes.size() ; i++) {
            if(indexGroup.indexOf(i)!=-1 == indexGroupIsToTake){  // si ce node est à prendre
                Node node = graph.nodes.get(i);
                Node newNode = node.cloneButNotEdgeList();
                ArrayList newEdgeList = new ArrayList<Edge>();
                for (Edge edge:node.getEdgesList()) {
                    if ((indexGroup.indexOf(graph.nodes.indexOf(edge.from))!=-1 == indexGroupIsToTake) && (indexGroup.indexOf(graph.nodes.indexOf(edge.to))!=-1 == indexGroupIsToTake) ){ //si cet edge est à prendre (ie: si from est dedans et doit être pris, et que to aussi )
                        newEdgeList.add(edge.cloneButKeepNodes());
                    }
                }
                newNode.setEdgesList(newEdgeList);

                newGraph.nodesIndex.add(newNode.getId());
                newGraph.nodes.add(newNode);
                newGraph.edges.removeAll(newEdgeList);
                newGraph.edges.addAll(newEdgeList);
            }
        }
        newGraph.consolidate(); //parce que les Edges pointent encore vers les anciens Nodes
        return newGraph;
    }

    // ----------------------------------------------------------------------------------------------------------
    // ------------------------------------------- Les removers -------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    public void removeEdge (Edge edgeToRemove){
        edges.remove(edgeToRemove);
        nodes.get(nodesIndex.indexOf(edgeToRemove.getFromId())).getEdgesList().remove(edgeToRemove);
    }



}

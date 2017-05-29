import Algos.Library;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph metroGraph = new Graph();

//        metroGraph.addLine("1");
//
//        metroGraph.addLine("2");
//        metroGraph.addLine("3");
//        metroGraph.addLine("3b");
//        metroGraph.addLine("4");
//        metroGraph.addLine("5");
//        metroGraph.addLine("6");
//        metroGraph.addLine("7");
//        metroGraph.addLine("7b");
//        metroGraph.addLine("8");
//        metroGraph.addLine("9");
//        metroGraph.addLine("10");
//        metroGraph.addLine("11");
//        metroGraph.addLine("12");
//        metroGraph.addLine("13");
//        metroGraph.addLine("14");
//
        Gson metroGrapgGson = new Gson();
//
//        String myObjectJson = metroGrapgGson.toJson(metroGraph);
//        List<String> jsonInput = new ArrayList<String>();
//        jsonInput.add(myObjectJson);
//
//        try {
//            Files.write(Paths.get(Library.DatasDirectory+"/data.json"),jsonInput, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        List<String> rawLines = Files.readAllLines(Paths.get(Library.DatasDirectory+"/data.json"),StandardCharsets.UTF_8);




        Graph newMetroGraph = metroGrapgGson.fromJson(rawLines.get(0), Graph.class);
        newMetroGraph.consolidate();
        double testDist = Library.meterDistanceBetweenGPSPoints(45.613677, 5.885025,45.605640, 5.889892);
        System.out.println(testDist);


    }
}

//try {
//            SerializableGraph serializableGraph = new SerializableGraph(metroGraph);
//
//            // write object to file
//            FileOutputStream fos = new FileOutputStream("metroGraph.ser");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(serializableGraph);
//            oos.close();
//
//            // read object from file
//            FileInputStream fis = new FileInputStream("metroGraph.ser");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            SerializableGraph result = (SerializableGraph) ois.readObject();
//            ois.close();
//
//            System.out.println("ca marche");
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

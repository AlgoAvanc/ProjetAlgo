package DataExtraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by etien on 25/05/2017.
 */
public class StopTimeHandler {
    protected Set<List<String>> stopList = new HashSet<List<String>>();

    public StopTimeHandler(FileReader file) {

        // ----------------------On isole par trip_id -------------------------------------------------------
        // --------------------- et on enlève les trips qui ne sont pas entre 10h et 20h---------------------
        // --------------------- (en effet, il y a des lignes allégées aux horraires extrèmes)---------------
        // --------------------- on enlève tout ce qui n'est ni stop_id, ni stop_sequence--------------------
        //---------------------- avec un hashset, on réduit au minimum de listes de listes différentes-------
        //---------------------- on obtiens la liste voulue--------------------------------------------------

        int trip_idIndex = file.getFirstLine().indexOf("trip_id");
        int arrival_timeIndex = file.getFirstLine().indexOf("arrival_time");
        int stop_idIndex = file.getFirstLine().indexOf("stop_id");
        int stop_sequenceIndex = file.getFirstLine().indexOf("stop_sequence");
        List<List<String>> lines = file.getLines();

        this.stopList = linesPack(lines,trip_idIndex,arrival_timeIndex,stop_idIndex,stop_sequenceIndex);
//        System.out.println(this.stopList);






    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- La librairie de construction -------------------------------
    // ----------------------------------------------------------------------------------------------------------

    protected static Set<List<String>> linesPack (List<List<String>> lines, int trip_idIndex, int arrival_timeIndex, int stop_idIndex, int stop_sequenceIndex){
        Set<List<String>> pack = new HashSet<List<String>>();
        String tripId = "premierId";
        List<String> trip = new ArrayList<String>();
        boolean outOfTime = false;


        for (List<String> line:lines){
            if (!line.get(trip_idIndex).equals(tripId)){ //quand on arrive à un nouveau trip
                if (!outOfTime){
                    pack.add(trip);
                }
                tripId = line.get(trip_idIndex);
                trip = new ArrayList<String>();
                outOfTime = false;
            }
            if (!line.get(arrival_timeIndex).substring(0,1).equals("1")){
                outOfTime=true;
            }
//            trip.add(lineReduce(line,stop_idIndex,stop_sequenceIndex));
            trip.add(line.get(stop_idIndex));
        }
        pack.remove(new ArrayList<List<String>>() );
        return pack;
    }
    protected static List<String> lineReduce (List<String> line, int idToKeep1, int idToKeep2){ //on pourrait affiner à n variables à garder à garder mais on finirait par s'y perdre
        String keep1 = line.get(idToKeep1);
        String keep2 = line.get(idToKeep2);

        List<String> reducedLine = new ArrayList<String>();
        reducedLine.add(keep1);
        reducedLine.add(keep2);
        return reducedLine;
    }


    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Getters & ToString -----------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "DataExtraction.StopTimeHandler{" +
                "stopList=" + stopList +
                '}';
    }

    public Set<List<String>> getStopList() {
        return stopList;
    }
}


//        this.set = new HashSet<List<String>>();
//        int dateIndex = file.getFirstLine().indexOf("departure_time");
//        int idIndex = file.getFirstLine().indexOf("stop_id");
//        int sequesnce = file.getFirstLine().indexOf("stop_sequence");
//        List<List<String>> lines = file.getLines();
//        for (List<String> line:lines){
//            if ()
//        }

package DataExtraction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by etien on 27/05/2017.
 */
public class StopHandler {
    protected Map<String, String> nameOfId = new HashMap<>();
    protected Map<String,Map> Stops = new HashMap<>(); // pour obtenir une structure proche du js du genre {Nation {id:8354, lat:25.3, long:46.2} ; ...}
    protected String line;



    public StopHandler(FileReader file, String line) {
        this.line = line;

        int stop_idIndex = file.getFirstLine().indexOf("stop_id");
        int stop_nameIndex = file.getFirstLine().indexOf("stop_name");
        int stop_latIndex = file.getFirstLine().indexOf("stop_lat");
        int stop_lonIndex = file.getFirstLine().indexOf("stop_lon");
        List<List<String>> lines = file.getLines();

        for (List<String> fileLine :lines
             ) {
            nameOfId.put(fileLine.get(stop_idIndex), fileLine.get(stop_nameIndex).replace("\"", ""));//ya des quotes en trop si non

            Map<String,String> stop = Stops.get(fileLine.get(stop_nameIndex));
            if (stop==null){
                stop = new HashMap<>();
                stop.put("ids",fileLine.get(stop_idIndex));
                stop.put("latitude",fileLine.get(stop_latIndex));
                stop.put("longitude",fileLine.get(stop_lonIndex));
                stop.put("ligne", line);
            }else {
                stop.put("ids",stop.get("ids")+" ; "+fileLine.get(stop_idIndex));
            }
            Stops.put(fileLine.get(stop_nameIndex).replace("\"", ""), stop);
        }
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Getters & Setters ------------------------------------------
    // ----------------------------------------------------------------------------------------------------------


    public Map<String, String> getNameOfId() {
        return nameOfId;
    }

    public void setNameOfId(Map<String, String> nameOfId) {
        this.nameOfId = nameOfId;
    }

    public Map<String, Map> getStops() {
        return Stops;
    }

    public void setStops(Map<String, Map> stops) {
        Stops = stops;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by etien on 27/05/2017.
 */
public class StopHandler {
    protected Map<String, String> nameOfId = new HashMap<>();
    protected Map<String,Map> Stops = new HashMap<>(); // pour obtenir une structure proche du js du genre {Nation {id:8354, lat:25.3, long:46.2} ; ...}
    protected String ligne;



    public StopHandler(FileReader file, String ligne) {
        this.ligne = ligne;

        int stop_idIndex = file.getFirstLine().indexOf("stop_id");
        int stop_nameIndex = file.getFirstLine().indexOf("stop_name");
        int stop_latIndex = file.getFirstLine().indexOf("stop_lat");
        int stop_lonIndex = file.getFirstLine().indexOf("stop_lon");
        List<List<String>> lines = file.getLines();

        for (List<String> line :lines
             ) {
            nameOfId.put(line.get(stop_idIndex), line.get(stop_nameIndex));

            Map<String,String> stop = Stops.get(line.get(stop_nameIndex));
            if (stop==null){
                stop = new HashMap<>();
                stop.put("ids",line.get(stop_idIndex));
                stop.put("latitude",line.get(stop_latIndex));
                stop.put("longitude",line.get(stop_lonIndex));
                stop.put("ligne", ligne);
            }else {
                stop.put("ids",stop.get("ids")+" ; "+line.get(stop_idIndex));
            }
            Stops.put(line.get(stop_nameIndex), stop);
        }
        System.out.println("test");
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- La librairie de construction -------------------------------
    // ----------------------------------------------------------------------------------------------------------

}

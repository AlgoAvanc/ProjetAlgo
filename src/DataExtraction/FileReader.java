package DataExtraction;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {
    protected ArrayList<ArrayList<String>> lines;
    protected ArrayList<String> firstLine;

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Constructers -----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public FileReader(String filepath,String separator) throws IOException {
        ArrayList<String> rawLines = Files.readAllLines(Paths.get(filepath),
                StandardCharsets.UTF_8);
        this.lines = new ArrayList<ArrayList<String>>();
//        firstLine =  Arrays.asList(rawLines.get(0).split(separator)); // on met ici la première ligne qui contient les infos des colones
        firstLine =  Arrays.asList(rawLines.get(0).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)); // c'est pas moi qui ai inventé cette regex de la mort: cf https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
        for (int index = 1; index< rawLines.size(); index++) {
//            System.out.println(index);
//            System.out.println(rawLines.get(index));
//            System.out.println(Arrays.asList(rawLines.get(index).split(",")));
            this.lines.add( Arrays.asList(rawLines.get(index).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1))); // et ici les colones (donc décalées de 1 dans leur index
        }

    }
    public FileReader(String filePath) throws IOException {
        this(filePath,",");
    }
    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Getters & setters ------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

    public ArrayList<ArrayList<String>> getLines() {
        return lines;
    }
    public ArrayList<String> getLine(int index) {
            return lines.get(index);
    }
    public ArrayList<String> getFirstLine() {
            return firstLine;
    }

    public void setLines(ArrayList<ArrayList<String>> lines) {
        this.lines = lines;
    }

    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- Reste ------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------

}

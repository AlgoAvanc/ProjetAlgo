import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        String filePath = "lines_datas/RATP_GTFS_METRO_1/stop_times.txt";
//        FileReader fr1 = new FileReader(filePath);
//        System.out.println(fr1.getLine(0));
//        StopTimeHandler handleFile1 = new StopTimeHandler(fr1);

        String filePath1Stops = "lines_datas/RATP_GTFS_METRO_1/stops.txt";
        FileReader fr1Stops = new FileReader(filePath1Stops);
        System.out.println(fr1Stops.getLine(0));
        StopHandler handleFile1Stops = new StopHandler(fr1Stops);

    }
}

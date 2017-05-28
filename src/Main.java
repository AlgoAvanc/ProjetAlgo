import Algos.Library;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath1StopTimes = "lines_datas/RATP_GTFS_METRO_1/stop_times.txt";
        FileReader fr1StopTimes = new FileReader(filePath1StopTimes);
        System.out.println(fr1StopTimes.getLine(0));
        StopTimeHandler handleStopTimes1 = new StopTimeHandler(fr1StopTimes);

        String filePath1Stops = "lines_datas/RATP_GTFS_METRO_1/stops.txt";
        FileReader fr1Stops = new FileReader(filePath1Stops);
        System.out.println(fr1Stops.getLine(0));
        StopHandler handleStops1 = new StopHandler(fr1Stops,"1");


        Graph graph1 = new Graph();
        graph1.addLine(handleStopTimes1,handleStops1);

        double testDist = Library.meterDistanceBetweenGPSPoints(45.613677, 5.885025,45.605640, 5.889892);
        System.out.println(testDist);


    }
}

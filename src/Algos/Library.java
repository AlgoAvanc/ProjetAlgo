package Algos;

import java.util.ArrayList;

public class Library {


    public static double meterDistanceBetweenGPSPoints(double  lat_a, double  lng_a, double  lat_b, double  lng_b) {
        double pk = (double) (180/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }
    public static String DatasDirectory ="lines_datas";
    public static String linesDirectory(String line) {return DatasDirectory+"/RATP_GTFS_METRO_"+ line;}
    public static String stopTimesDirectory (String line) {return linesDirectory(line)+"/stop_times.txt";}
    public static String stopDirectory (String line) {return linesDirectory(line)+"/stops.txt";}

    public static ArrayList<Integer> swapArraylist(ArrayList<Integer> data, int i, int j){
        int tmp= data.get(i);
        data.set(i,data.get(j));
        data.set(j,tmp);
        return data;
    }
    public static ArrayList<Integer> BubleSortArrylist(ArrayList<Integer> data){
        if(data.size() < 2){return data;}
        boolean hadToSwap = false;
        do{
            hadToSwap=false;
            for(int i= 0; i != data.size()-1; ++i){
                if(data.get(i) >data.get(i+1)){
                    data = swapArraylist(data, i, i+1);
                    hadToSwap= true;
                } }
        }while(hadToSwap);
        return data;
    }

}

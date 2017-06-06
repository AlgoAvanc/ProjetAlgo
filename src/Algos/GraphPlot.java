package Algos;

import GraphTools.Edge;
import GraphTools.Node;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 * Created by etien on 05/06/2017.
 */
public class GraphPlot {
    public static double lenght=1.0;
    public static double height=1.0;
    public static Color white = new Color(255, 255, 255);
    public static Color red = new Color(255, 0, 0);
    public static int step = 0;

    public GraphPlot() {
        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setPenRadius(0.005);

        StdDraw.setPenColor(white);
        StdDraw.clear(StdDraw.getPenColor());
        StdDraw.setXscale(0, lenght);
        StdDraw.setYscale(0, height);
        addLegend();

    }
    protected double reglageX (double x){
        return (x-2.23)*4;
    }
    protected double reglageY (double x){
        return (x-48.75)*4;
    }
    protected void addDot(double x,double y){
        StdDraw.setPenColor(red);
        StdDraw.circle(x,y,0.003);
    }
    protected void addLink(double x1,double y1, double x2,double y2, int colortype){
        StdDraw.setPenColor(colorFade(colortype));
        StdDraw.line(x1,y1,x2,y2);
    }
    public void addNode (Node node){
        double nodeX = reglageX(node.getLongitude());
        double nodeY = reglageY(node.getLatitude());
        addDot(nodeX,nodeY);
        for (Edge edge: node.getEdgesList()) {
            double toX = reglageX(edge.getTo().getLongitude());
            double toY = reglageY(edge.getTo().getLatitude());
            addLink(nodeX,nodeY,toX,toY,edge.getBetweenness());
        }
    }
    protected void addLegend (){
        double offsetX = 0.05;
        StdDraw.setPenColor(Color.black);
        StdDraw.text(0+offsetX,0.05,"0");
        StdDraw.text(0.1+offsetX,0.05,"20");
//        StdDraw.text(0.15,0.05,"300");
        for (double i = 0; i < 0.1; i+=0.005) {
            int beetweeness = (int)(i*200);
            addLink(i+offsetX,0.08,i+offsetX, 0.1, beetweeness);
        }

    }

    public Color colorFade(int betweeness){
//        int red=(int)Math.floor(150-((betweeness/10)*(betweeness/10)*1.5)//on gere le rouge
//                +(10*2.5*2.5066*Math.exp(betweeness*betweeness*0.00001/2)));//on ajoute de quoi faire du jaune grace a une gausienne allant jusqu'à 0.4 multipliee par 2. puis par 100
//        int green=(int)Math.floor(((betweeness/10)*(betweeness/10)*1.5)
//                +(10*2.5*2.5066*Math.exp(betweeness*betweeness*0.00001/2)));
//        Color couleur=new Color(red,green,00);
//        return couleur;
//        int red=(int)Math.floor(150-((betweeness)*(betweeness)*1.5)//on gere le rouge
//                +(10*2.5*2.5066*Math.exp(betweeness*betweeness*0.00001/2)));//on ajoute de quoi faire du jaune grace a une gausienne allant jusqu'à 0.4 multipliee par 2. puis par 100
//        int green=(int)Math.floor(((betweeness)*(betweeness)*1.5)
//                +(10*2.5*2.5066*Math.exp(betweeness*betweeness*0.00001/2)));
        double betweenessDouble =   Math.log((betweeness)+10) ;
        int red     =  (int) Math.abs(Math.cos((betweenessDouble+0.0)*Math.PI)*125 +125 %250);
        int green   =  (int) Math.abs(Math.cos((betweenessDouble+1.0)*Math.PI)*125 +125 %250);
        int blue    =  (int) Math.abs(Math.cos((betweenessDouble+0.5)*Math.PI)*125 +125 %250);


        Color couleur=new Color(red,green,blue);
        return couleur;
    }
//    public void save(){
//        StdDraw.save("./"+Library.DatasDirectory+"/pictures/"+step+".png");
//    }
}

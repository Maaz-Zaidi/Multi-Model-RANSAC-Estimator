//Maaz Zaidi
//Date: December 5, 2022

import java.util.List;
import java.util.ArrayList;

public class NearestNeighbors implements NN{

    //Instantiates class variables
    private List<Point3D> Points;
    private List<Point3D> n;

    //Constructor
    public NearestNeighbors(List<Point3D> points){
        Points = points;
    }

    //Outputs nearby points given a range
    public List<Point3D> rangeQuery(double Eps, Point3D Q){
        List<Point3D> N = new ArrayList<Point3D>();
        for (int i = 0; i<Points.size(); i++){
            Point3D P = Points.get(i);
            if(Q.distance(P) <= Eps){
                N.add(P);
            }
        }
        n = N;
        return N;
    }

    //Getter
    public int getSize(){return n.size();}


}

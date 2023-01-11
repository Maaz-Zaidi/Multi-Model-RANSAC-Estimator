//Maaz Zaidi
//Student Number: 300246507
//Date: December 5, 2022

import java.util.List;

//Interface for neighbor classes
public interface NN {

    //Neccessary methods to be created
    public List<Point3D> rangeQuery(double Eps, Point3D Q);
    public int getSize();
}

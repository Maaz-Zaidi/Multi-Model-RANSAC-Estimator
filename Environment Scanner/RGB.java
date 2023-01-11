//Maaz Zaidi
//Student Number: 300246507
//Date: December 5, 2022

import java.util.Random;

public class RGB {

    //Instantiates class variables
    private double R;
    private double G;
    private double B;

    //Constructor
    public RGB(double r, double g, double b){
        R = r;
        G = g;
        B = b;
    }

    //More convenient construcor (randomizes RGB values)
    public RGB(){
        Random rand = new Random();
        R = rand.nextDouble();
        G = rand.nextDouble();
        B = rand.nextDouble();
    }

    //Getters/Setters

    public void setR(double r){R = r;}
    public void setG(double g){G = g;}
    public void setB(double b){B = b;}
    public double getR(){return R;}
    public double getG(){return G;}
    public double getB(){return B;}

    //Randomizes pre-existing RGB
    public RGB randomizeRGB(){
        Random rand = new Random();
        double red = rand.nextDouble();
        double green = rand.nextDouble();
        double blue = rand.nextDouble();
        return new RGB(red, green, blue);
    }

}

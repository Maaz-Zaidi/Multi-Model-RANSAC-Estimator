//Maaz Zaidi
//Student Number: 300246507
//Date: December 5, 2022

public class Point3D {

    //Instantiates class variables
    private double x;
    private double y;
    private double z;

    private int Label;
    private RGB rgb;

    //Constructor
    public Point3D(double X, double Y, double Z){
        x = X;
        y = Y;
        z = Z;
        setLabel(-1);
        rgb = new RGB(0, 0, 0);
    }

    //Getters/Setters
    
    public double get(int axis){
        switch(axis){
            case 0:
                return getX();
            case 1:
                return getY();
            case 2:
                return getZ();
            default:
                return 0;
        }
    }

    public double getX(){return x;}
    public double getY(){return y;}
    public double getZ(){return z;}
    public void setLabel(int label){Label = label;}
    public int label(){return Label;}
    public void setRGB(RGB newRGB){rgb = newRGB;}
    public RGB getRGB(){return rgb;}

    //Calculates distance between two classes
    public double distance(Point3D pt){
        double distance = Math.sqrt(Math.pow(this.x - pt.x, 2)+Math.pow(this.y-pt.y, 2) + Math.pow(this.z-pt.z, 2));
        return distance;
    }
    
    public boolean equals(Point3D pt){
        return getX() == pt.getX() && getY() == pt.getY() && getZ() == pt.getZ();
    }

    //Prints the string value of a point
    public String printPoint(){
        return (String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z) + "," +String.valueOf(Label) + "," + String.valueOf(getRGB().getR()) + "," + String.valueOf(getRGB().getG())+","+String.valueOf(getRGB().getB()));
    }
}

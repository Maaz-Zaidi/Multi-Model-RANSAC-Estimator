//Maaz Zaidi
//Date: December 5, 2022

import java.util.List;
import java.util.Stack;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DBScan{

    //Instantiates class variables
    private List<Point3D> Points;
    private double Eps;
    private double MinPts;
    private int C;
    private boolean FAST;

    //Constructur
    public DBScan(List<Point3D> points){
        FAST = true;
        Points = points;
    }

    //DBScan Algorithm
    public void findClusters(){
        C = 0;
        NN N = FAST?new NearestNeighborsKD(Points):new NearestNeighbors(Points);
        Stack<Point3D> S = new Stack<Point3D>();

        for (Point3D P : Points){
            if (P.label() != -1){continue;}
            List<Point3D> rangeQ = N.rangeQuery(Eps, P);
            if (rangeQ.size() < MinPts){
                P.setLabel(0);
                continue;
            }
            C+=1;
            P.setLabel(C);
            S.addAll(rangeQ);
            while(!S.isEmpty()){
                Point3D Q = S.pop();
                if(Q.label() == 0){
                    Q.setLabel(C);
                }
                if(Q.label() == -1){
                    Q.setLabel(C);
                    List<Point3D> rangeQ2 = N.rangeQuery(Eps, Q);
                    if(rangeQ2.size() >= MinPts){
                        S.addAll(rangeQ2);
                    }   
                }
            }
        }
    }

    //Getters/Setters:
    public void setEps(double eps){Eps = eps;}
    public void setMinPts(double minPts){MinPts = minPts;}
    public void setFAST(Boolean speed){FAST = speed;}
    public Boolean getFAST(){return FAST;}
    public List<Point3D> getPoints(){return Points;}
    public int getNumberOfClusters(){return C;}

    //function to count the number of points inside a cluster
    public int countCluster(int cluster){
        int count = 0;

        //loops through the class points variable
        for (int i = 0; i<Points.size(); i++){
            Point3D P = Points.get(i);
            if(P.label() == cluster){
                count++;
            }
        }
        return count;
    }

    //Read function to input a file and assign 3D points to the class
    public static List<Point3D> read(String filename){

        //Instantiates local variables
        List<Point3D> result = new ArrayList<Point3D>();
        String line = "";
        int x = 0, y = 0, z = 0;

        try   
        {  
            
            //Reads in file
            BufferedReader text = new BufferedReader(new FileReader(filename));  
            String firstline = text.readLine();

            //Local check for valid file
            if(firstline != null){

                //Local check for correct data input
                String[] placement = firstline.split(",");
                if (placement.length!=3){
                    System.out.println("Error: Invalid inputs (!=3)");
                    text.close();
                    return null;
                }

                //Uses first input to determine x/y/z placement
                for(int i=0; i<placement.length; i++){
                    placement[i] = placement[i].replaceAll("\\s+","");
                    if(placement[i].equals("x")){
                        x = i;
                    }
                    else if(placement[i].equals("y")){
                        y=i;
                    }
                    else if(placement[i].equals("z")){
                        z=i;
                    } 
                    else{
                        System.out.println("Invalid File Input (x/y/z)");
                        System.out.println("Trying: " + placement[i]);
                        text.close();
                        return null;
                    }
                }
            }

            //Loops through file to input all valid 3D points
            while ((line = text.readLine()) != null) 
            {  
                String[] points = line.split(",");
                double x_val = 0, y_val = 0, z_val = 0;
                for (int w = 0; w<points.length; w++){
                    points[w] = points[w].replaceAll("\\s+","");
                    if(w==x){
                        x_val=Double.parseDouble(points[w]);
                    }
                    else if(w==y){
                        y_val=Double.parseDouble(points[w]);
                    }
                    else if(w==z){
                        z_val=Double.parseDouble(points[w]);
                    }
                    else{
                        System.out.println("Unable to input points");
                    }
                }   
                Point3D cur = new Point3D(x_val, y_val, z_val);
                result.add(cur);
            }  
            text.close();
        }      
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        return result;
    }  

    //Assigns RGB values to same clusters 
    public void assignRGB(){

        //Check for existence of clusters
        if(getNumberOfClusters()>=1){
            for (int i = 1; i<=getNumberOfClusters(); i++){

                //Instantiates an RGB class with random RGB values
                RGB rgb_i = new RGB().randomizeRGB();
                for (int x = 0; x<Points.size(); x++){
                    Point3D P = Points.get(x);
                    if(P.label()==i){
                        P.setRGB(rgb_i);
                    }
                }
            }
        }
    }

    //Saves current DBScan class with points in a valid CSV file
    public void save(String filename) throws IOException{

        //Creates new file
        File nf = new File(filename);
	    FileOutputStream fileOut = new FileOutputStream(nf);
 
	    BufferedWriter file = new BufferedWriter(new OutputStreamWriter(fileOut));

        //First line
        file.write("x,y,z,C,R,G,B");
        file.newLine();

        //Assigns clusters an RGB value
        assignRGB();

        //Loops through each point to input into file
        for (int i = 0; i<Points.size(); i++){
            Point3D P = Points.get(i);
            file.write(P.printPoint());
            file.newLine();
        }
 
	    file.close();
    }

    //String check for double
    static boolean isDouble(String s){
        if (s == null) {
            System.out.println("Error type: Null");
            return false;
        }
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.println("Error type: " + e);
            return false;
        }
        return true;
     }

    public void displayClusters(){
         //counting the size of each cluster
        int[] cs = new int[C];
        int noise=0,sum_ = 0;
        for(Point3D P: Points){
            // funny one liner to increment each cluster properly
            noise+=(P.label()==0)?1:(cs[P.label()-1]++)*0;
        }
        // sort & print sizes
        Arrays.sort(cs);
        sum_=noise;
        System.out.print("Cluster sizes: [");
        for(int i=C-1;i>=0;i--){
            System.out.print(cs[i]+",");
            sum_+=cs[i];
        }
        System.out.println("]\nNoise: " +noise+ "\nNumber of Points: "+sum_);
    }

    public static boolean argumentValidation(String[] args){
        //Check for proper argument length
        if(args.length!=3){
            System.out.println("Invalid Arguments (args.len!=3)");
            return false;
        }

        //check for valid argument type
        if(!isDouble(args[1]) || !isDouble(args[2])){
            System.out.println("Invalid EPS and MinPts: " + args[1] + ", " + args[2]);
            return false;
        }

        //creates enw file given argument/variable
        File file = new File("./" + args[0]);

        //checks for File validity
        if (!file.isFile()){
            System.out.println("Invalid File");
            return false;
        }

        //Passes all test cases
        return true;
    }

     //Main function
    public static void main(String[] args) throws IOException{
        //Main three argument checks
        if(!argumentValidation(args)){
            return;
        }
        
        //Instantiates variables through arguments
        String filename = args[0];
        String input_eps = args[1];
        String input_minpts = args[2];

        try{
            //Instantiate class variables
            DBScan DBS_res = new DBScan(read("./" + filename));
            DBS_res.setEps(Double.parseDouble(input_eps));
            DBS_res.setMinPts(Double.parseDouble(input_minpts));
    
            //Main algorithm
            DBS_res.findClusters();

            String file_save = filename.substring(0, filename.length()-4)+"_clusters_"+input_eps+"_"+input_minpts+"_"+DBS_res.getNumberOfClusters()+".csv";
            DBS_res.save(file_save);
            
            DBS_res.displayClusters();
        }
        catch(Exception e){
            System.out.println("Error: Occured in main: " + e);
        }
    }
}
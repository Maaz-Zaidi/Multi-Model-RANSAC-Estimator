//Maaz Zaidi
//Date: December 5, 2022

import java.util.List;
import java.util.Stack;
import java.io.*;
import java.util.ArrayList;

//Compiled test class for Exp1, Exp2, Exp3
public class ExperimentTests {

    //Main method to run all three tests
    public static void main(String[] args) {
        testValidation();
        testComputationalTime();
        testDBSComparison();
    }

    //Automated test method for Exp1
    public static void testValidation(){
        System.out.println("Running Experiment 1: Validation\n");

        //Tests with these 6 points
        List<Point3D> testPoints = new ArrayList<Point3D>();
        testPoints.add(new Point3D(-5.429850155, 0.807567048, -0.398216823));
        testPoints.add(new Point3D(-12.97637373, 5.09061138, 0.762238889));
        testPoints.add(new Point3D(-36.10818686, 14.2416184, 4.293473762));
        testPoints.add(new Point3D(3.107437007, 0.032869335, 0.428397562));
        testPoints.add(new Point3D(11.58047393, 2.990601868, 1.865463342));
        testPoints.add(new Point3D(14.15982089, 4.680702457, -0.133791584));

        //Set variables to test with
        double ep = 0.5;
        String filename = "Point_cloud_1.csv";

        //Check if file exists in directory
        File file = new File("./" + filename);
        if (!file.isFile()){
            System.out.println("FAIL: Error on [args 2]: Invalid File name/directory.");
            return;
        }

        //Declaring array lists for both ouput variables
        List<Point3D> linOutput = new ArrayList<Point3D>();
        List<Point3D> kdOutput = new ArrayList<Point3D>();

        //Part indexer
        int pt = 1;

        //Loop through each point
        for(Point3D p: testPoints){

            //Run function from Exp1 for both KD and Linear methods
            linOutput = Exp1.validation(false, ep, filename, p.getX(), p.getY(), p.getZ());
            kdOutput = Exp1.validation(false, ep, filename, p.getX(), p.getY(), p.getZ());

            //Save outputs to relative part index
            try{
                saveFile("pt" + String.valueOf(pt) + "_lin.txt", linOutput);
                saveFile("pt" + String.valueOf(pt) + "_kd.txt", kdOutput);
            }
            catch(Exception e){
                System.out.println("FAIL: Error '" + e + "'' on experiment 1, Test Validation");
                return;
            }

            //Print result (Pass/Fail)
            String result = (pointsEquality(linOutput, kdOutput))?"PASS: Point " + String.valueOf(pt) + " identical for both methods":"FAIL: Point " +  String.valueOf(pt) + " does not produce identical outputs for both methods";
            System.out.println(result);

            pt+=1;
        }
    }

    //Automated test method for Exp2
    public static void testComputationalTime(){
        System.out.println("\nRunning Experiment 2: Computational Time\n");

        //Arguments declaration
        double eps = 0.5;
        int step = 10;

        //Files array to loop through
        String[] files = new String[]{"Point_cloud_1.csv", "Point_cloud_2.csv", "Point_cloud_3.csv"};

        //Avarages variable to store in for both methods
        long[] linAvarages = new long[3];
        long[] kdAvarages = new long[3];
        int index = 0;

        // Avarage time using linear range query

        for(String filename : files){
            linAvarages[index] = Exp2.timeComputation(false, eps, filename, step)/1000000;
            index+=1;
        }

        index = 0;

        // Avarage time using KD tree range query

        for(String filename : files){
            kdAvarages[index] = Exp2.timeComputation(true, eps, filename, step)/1000000;
            index+=1;
        }

        //Result outputs
        String output1 = (kdAvarages[0] < linAvarages[0])?"PASS: KD time proven bigger (Input files 1: KD Avrage: " + String.valueOf(kdAvarages[0]) + "ms, Linear Avarage: " + String.valueOf(linAvarages[0]) + "ms)": "FAIL: KD avarage time for set 1 proved bigger (Input files 1: KD Avrage: " + String.valueOf(kdAvarages[0]) + "ms, Linear Avarage: " + String.valueOf(linAvarages[0]) + "ms)";
        String output2 = (kdAvarages[1] < linAvarages[1])?"PASS: KD time proven bigger (Input files 2: KD Avrage: " + String.valueOf(kdAvarages[1]) + "ms, Linear Avarage: " + String.valueOf(linAvarages[1]) + "ms)": "FAIL: KD avarage time for set 2 proved bigger (Input files 2: KD Avrage: " + String.valueOf(kdAvarages[1]) + "ms, Linear Avarage: " + String.valueOf(linAvarages[1]) + "ms)";
        String output3 = (kdAvarages[2] < linAvarages[2])?"PASS: KD time proven bigger (Input files 3: KD Avrage: " + String.valueOf(kdAvarages[2]) + "ms, Linear Avarage: " + String.valueOf(linAvarages[2]) + "ms)": "FAIL: KD avarage time for set 3 proved bigger (Input files 3: KD Avrage: " + String.valueOf(kdAvarages[2]) + "ms, Linear Avarage: " + String.valueOf(linAvarages[2]) + "ms)";

        System.out.println(output1 + "\n" + output2 + "\n" + output3);
    }

    //Automated test method for Exp3
    public static void testDBSComparison(){
        System.out.println("\nRunning Experiment 3: DBS Comparison\n");

        //Boolean for method choice
        boolean FAST = false;

        //Loop method choice to find avarage time for both
        for(int i = 0; i<2;i++){

            //Files to be used and looped through
            String[] files = new String[]{"Point_Cloud_1.csv", "Point_Cloud_2.csv", "Point_Cloud_3.csv"};

            //Method run for each file
            for(String file : files){

                //Main arguments
                long mainStartTime = System.nanoTime();
                String[] input = new String[]{file, "1.2", "10"};

                //Main three argument checks
                if(!DBScan.argumentValidation(input)){
                    System.out.println("FAIL: Error in given arguments for experiment 3");
                    return;
                }
                
                //Instantiates variables through arguments
                String filename = input[0];
                String input_eps = input[1];
                String input_minpts = input[2];

                //Reads through new DBSCan class
                DBScan DBS_res = new DBScan(DBScan.read("./" + file));

                //Set variables
                DBS_res.setEps(Double.parseDouble(input_eps));
                DBS_res.setMinPts(Double.parseDouble(input_minpts));
                DBS_res.setFAST(FAST);
        
                //Main algorithm
                DBS_res.findClusters();

                String file_save = filename.substring(0, filename.length()-4)+"_clusters_"+input_eps+"_"+input_minpts+"_"+DBS_res.getNumberOfClusters()+".csv";

                //Save file
                try{
                    DBS_res.save(file_save);
                }catch(Exception e){
                    System.out.println("FAIL: Error in experiment 3: " + e);
                    return;
                }
                
                //Display all cluster sizes
                DBS_res.displayClusters();
                long mainEndTime = System.nanoTime();

                //Record duration
                long duration = (mainEndTime - mainStartTime)/1000000; // in milliseconds.

                //Output results for this iteration
                String method = FAST?"KD":"Linear";
                System.out.println("Full runtime for file with method [" + method + "]: (" + file + "), is " + String.valueOf(duration) + "ms\n");
            }
            
            //Set fast to true to run next method
            FAST = true;
        }
    }

    //Private method to check if a list of point is equal to another
    private static boolean pointsEquality(List<Point3D> l1, List<Point3D> l2){

        //Initial checks to avoid a longer runtime
        if(l1 == null && l2 == null ){return true;}
        if(l1 == null || l2 == null ){return false;}
        if(l1.size() != l2.size()){return false;}

        //Creation of stacks to be removed
        Stack<Point3D> S = new Stack<Point3D>(); 
        S.addAll(l1);
       
        //Nested for loops to check through each variables
        for(Point3D p1 : l1){
            for(Point3D p2 : l2){
                if(p1.equals(p2)){
                    S.pop();
                }
            }
        }

        //True if nothing remains in stack
        return S.isEmpty();
    }

    //Advanced version of savefile with an extra constructor
    private static void saveFile(String filename, List<Point3D> points) throws IOException{
        File nf = new File(filename);
        FileOutputStream fileOut = new FileOutputStream(nf);

        BufferedWriter file = new BufferedWriter(new OutputStreamWriter(fileOut));

        //First line
        file.write("x,y,z,C,R,G,B");
        file.newLine();

        //Loops through each point to input into file
        for (int i = 0; i<points.size(); i++){
            Point3D P = points.get(i);
            file.write(P.printPoint());
            file.newLine();
        }

        //Close file
        file.close();
    }
}

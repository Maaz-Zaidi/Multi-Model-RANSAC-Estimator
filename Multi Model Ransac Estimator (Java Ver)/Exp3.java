//Maaz Zaidi
//Date: December 5, 2022

import java.io.IOException;

public class Exp3 {
    
    //Experiment 3: DBSCan runtime for KD 
    public static void main(String[] args) throws IOException {

        //Array for all files
        String[] files = new String[]{"Point_Cloud_1.csv", "Point_Cloud_2.csv", "Point_Cloud_3.csv"};

        //Loop for each file
        for(String file : files){

            //Input arguments already implemented
            String[] input = new String[]{file, "1.2", "10"};

            //Main function to run from DBSCan
            try{
                long startTime = System.nanoTime();
                DBScan.main(input);
                long endTime = System.nanoTime();

                long duration = (endTime - startTime)/1000000; // in milliseconds.

                //Print results
                System.out.println("Runtime for file with method KD: (" + file + "), is " + String.valueOf(duration) + "ms\n");
            }
            catch(Exception e){
                System.out.println("Error in Main: " + e);
            }
        }
    }
}

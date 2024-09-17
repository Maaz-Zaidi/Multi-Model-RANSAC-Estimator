//Maaz Zaidi
//Date: December 5, 2022

import java.util.List;

public class test {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        DBScan.main(new String[]{args[0], "1.2", "10"});
        long endTime = System.nanoTime();

        long time = (endTime - startTime)/1000000; // in milliseconds.

        //Print results
        System.out.println("Runtime: " + String.valueOf(time));
      }
}

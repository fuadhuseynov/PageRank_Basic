import java.text.DecimalFormat;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by Fuad Huseynov on 15.11.2016.
 *
 * --------------------DESCRIPTION----------------------
 * A basic PageRank algorithm implementation.
 * The program takes the READY index matrixin the
 * sparse form, adds the "taxation"to implement the
 * random teleports functionality and avoid dead-ends
 * and spiders problems,calculates the pagerank of
 * each node and shows the results.
 * -----------------------------------------------------
 * VERSION 1.0--VERY BASIC, SIMPLE ARRAY IMPLEMENTATION
 * --No DEAD-END avoidance functionality
 * --No SPIDER avoidance functionality
 */
public class PageRank {

    public double probability = 0.8;  //Probability that it will not teleport and follow the next random link

    /*Calculates the PageRank of each node
     *Input N --> number of nodes*/
    public double[] calculate(int N, double[][] theMatrix) {
        double TEMP[] = new double[N];  //saves outdated pagerank values
        double PAGERANK[] = new double[N];  //Saves the updated values at the end of the each run
        double convergence = 0.7;

        double temp = 0;
        int runNumber = 0;  //counts # of runs

        //Set TEMP entrances to 1/N
        for (int i = 0; i < N; i++) {
            TEMP[i] = 1.0/N;
            PAGERANK[i] = 0;
        }

        System.out.println();

        //Converges when the difference || SUM(PAGERANK)-SUM(TEMP) < 0.1 ||
        //the difference limit is set to 0.001 for accepting convergence
        while(abs(convergence) >= 0.001) {
            System.out.println("RUN " + runNumber);

            //MATRIX MULTIPLICATION
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    temp = temp + theMatrix[i][j] * TEMP[j];
                    PAGERANK[i] = temp;
                }
                System.out.println("PageRank[NODE" + (i + 1) + "]: " + PAGERANK[i]);

                temp = 0;
            }

            //Calculate convergence factor
            convergence = PAGERANK[1] - TEMP[1];
            System.out.println("CONVERGENCE = " + convergence);

            //Update TEMP
            for (int k = 0; k < N; k++) {
                TEMP[k] = PAGERANK[k];
            }

            runNumber++;
        }

        return PAGERANK;
    }

    public static void main(String[] args) {

        PageRank pr = new PageRank();

        DecimalFormat df = new DecimalFormat("#.##");
        Scanner scan = new Scanner(System.in);
        int numberOfNodes = 0;
        double MATRIX[][];

        //Get the number of nodes from user
        System.out.print("Enter the number of nodes: ");
        numberOfNodes = scan.nextInt();
        System.out.println();

        //Initialise the NxN Matrix
        MATRIX = new double[numberOfNodes][numberOfNodes];

        //Get the matrix from user
        System.out.println("Enter the matrix (row by row): ");
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                MATRIX[i][j] = scan.nextDouble();
            }
        }

        //For testing
        /*
        MATRIX[0][0] = 0.5;
        MATRIX[0][1] = 0.5;
        MATRIX[0][2] = 0;
        MATRIX[1][0] = 0.5;
        MATRIX[1][1] = 0;
        MATRIX[1][2] = 1;
        MATRIX[2][0] = 0;
        MATRIX[2][1] = 0.5;
        MATRIX[2][2] = 0;*/

        //Print the MATRIX:
        System.out.println("\nINPUT MATRIX: ");
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                System.out.print(MATRIX[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nCalculating...");

        double FINAL_PAGERANK[] = new double[numberOfNodes];
        FINAL_PAGERANK = pr.calculate(numberOfNodes, MATRIX);

        //Print the resulting pagerank value for each node
        System.out.println("\nPAGERANKS:");
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.println(df.format(FINAL_PAGERANK[i]));
        }
    }

}

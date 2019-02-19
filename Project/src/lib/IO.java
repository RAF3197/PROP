package lib;

import classes.Hidato;
import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.enums.TDifficulty;
import classes.taulell.Taulell;
import classes.taulell.TaulellRectangular;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Marc Ferreiro
 */
public class IO{
    /*
     * I/O functions
     */

    private final static Scanner in = new Scanner(System.in);

    //constructor

    /**
     *
     */
    public void IO(){}

    //Read an Integer

    /**
     *
     * @return returns next integer
     */
    public static int readInt(){
        return in.nextInt();
    }

    //Read a string

    /**
     *
     * @return returns read strign
     */
    public static String readString(){
        return in.next();
    }

    /**
     *
     * @param file
     * @return returns array of read hidatos
     * @throws Exception
     */
    public static ArrayList<Hidato> readFileHidatos(String file) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        ArrayList<Hidato> hidatos = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            String[] header = s.split(",");
            TCellShape shape = null;
            switch (header[0]) {
                case "Q":
                    shape = TCellShape.SQUARE;
                    break;
                case "H":
                    shape = TCellShape.HEXAGON;
                    break;
                case "T":
                    shape = TCellShape.TRIANGLE;
                    break;
            }
            TAdjacency adjacency = null;
            switch (header[1]) {
                case "C":
                    adjacency = TAdjacency.SIDE;
                    break;
                case "CA":
                    adjacency = TAdjacency.BOTH;
                    break;
            }
            int rows = Integer.parseInt(header[2]);
            int cols = Integer.parseInt(header[3]);
            Taulell t = new TaulellRectangular();
            Hidato a = new Hidato(t);
            a.initParams(shape, adjacency, rows, cols);

            String[] data = new String[rows];
            for (int i = 0; i < rows; i++) {
                data[i] = br.readLine();
            }
            a.setDades(data);
            if(a.getTaulell().getNumCaselles() < 22)  a.setDificultat(TDifficulty.EASY);
            else if (a.getTaulell().getNumCaselles() < 30) a.setDificultat(TDifficulty.MEDIUM);
            else a.setDificultat(TDifficulty.HARD);
            hidatos.add(a);
           /* System.out.printf("HIDATO\nShape: %s\nAdjacency: %s\n", shape, adjacency);
            h.print();
            System.out.println("Validating HIDATO:");
            System.out.println("The hidato have solution -> "+String.valueOf(h.validate()));

            System.out.println("Solving hidato...");
            if (h.solve()) {
                System.out.println("Hidato solved! Printing...");
                h.print();
            } else {
                System.out.println("No solution!");
            }

            h.print();*/
            br.readLine();
        }
        return hidatos;
    }
}
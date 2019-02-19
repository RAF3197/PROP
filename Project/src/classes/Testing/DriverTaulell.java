package classes.Testing;


import classes.taulell.Taulell;
import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.taulell.TaulellTriangular;
import lib.IO;

import java.io.IOException;

/**
 * Driver of Taulell Class
 * @author Ricard Abril
 */

public class DriverTaulell {

    private static String option;

    //Reads a string from the System input
    private static String readString() {
        return IO.readString();
    }

    private static void readOpcions() {
        System.out.println("\nEnter an option: ");
        option = readString();
    }


    private static void opcions() {
        System.out.println("Options for Taulell class:");
        System.out.println("Constructors:");
        System.out.println("\tcreate_taulell -> Create a non empty object of Taulell class");
        System.out.println("Seters:");
        System.out.println("\tset_cela_type");
        System.out.println("\tset_adjacency_type");
        System.out.println("\tset_rows");
        System.out.println("\tset_columns");
        System.out.println("Geters:");
        System.out.println("\tget_rows");
        System.out.println("\tget_columns");
        System.out.println("\tget_adjacency_type");
        System.out.println("\tget_cela_type");
        System.out.println("Exit -> Finish the test");
    }


    public static void main(String args[])throws IOException {
        opcions();
        readOpcions();
        Taulell test = new TaulellTriangular();
        while (!option.equals("Exit")) {
            switch (option) {
                case "create_taulell":
                    String params[] = new String[4];
                    System.out.println("Cell type ? -> options: TRIANGLE, SQUARE or HEXAGON");
                    params[0] = readString();
                    System.out.println("Adjacency type ? -> options: SIDE, BOTH (BOTH -> includes vertex and side adjacency)");
                    params[1] = readString();
                    System.out.println("Number of rows ?");
                    params[2] = readString();
                    System.out.println("Number of columns ?");
                    params[3] = readString();
                    test = new TaulellTriangular(TCellShape.valueOf(params[0]), TAdjacency.valueOf(params[1]),Integer.valueOf(params[2]),Integer.valueOf(params[3]));
                    break;
                case "set_cela_type":
                    System.out.println("Cell type ? -> options: TRIANGLE, SQUARE or HEXAGON");
                    String type = readString();
                    test.setTipusCela(TCellShape.valueOf(type));
                    break;
                case "set_adjacency_type":
                    System.out.println("Adjacency type ? -> options: SIDE, BOTH (BOTH -> includes vertex and side adjacency)");
                    String adj = readString();
                    test.setTipusAdjacencia(TAdjacency.valueOf(adj));
                    break;
                case "set_rows":
                    System.out.println("Number of rows ?");
                    String rows = readString();
                    test.setNombreFiles(Integer.valueOf(rows));
                    break;
                case "set_columns":
                    System.out.println("Number of columns ?");
                    String columns=readString();
                    test.setNombreColumnes(Integer.valueOf(columns));
                    break;
                case "get_cela_type":
                    System.out.println(String.valueOf(test.getTipusCela()));
                    break;
                case "get_adjacency_type":
                    System.out.println(String.valueOf(test.getTipusAdjacencia()));
                    break;
                case "get_rows":
                    System.out.println(test.getNombreFiles());
                    break;
                case "get_columns":
                    System.out.println(test.getNombreColumnes());
                    break;
                case "list":
                    opcions();
                    break;
                default:
                    System.out.println("Invalid option\n you can type 'list' to see the options again");
            }
            readOpcions();
        }
    }
}
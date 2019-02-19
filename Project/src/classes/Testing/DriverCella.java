package classes.Testing;

import classes.cella.Cella;
import classes.enums.TCellContent;
import lib.IO;

import java.io.IOException;

/**
 * @author Ricard Abril
 */

public class DriverCella {
    private static String option;

    /**
     *
     * @return
     */

    //Reads a string from the System input
    private static String readString() {
        return IO.readString();
    }

    /**
     *
     */

    private static void readOpcions() {
        System.out.println("\nEnter an option: ");
        option = readString();
    }

    /**
     *
     */

    private static void opcions() {
        System.out.println("Options of Cella class:");
        System.out.println("Constructors:");
        System.out.println("\tcreate_cella -> Create a non empty object of Cella class");
        System.out.println("Seters:");
        System.out.println("\tset_content_type");
        System.out.println("\tset_content");
        System.out.println("Geters:");
        System.out.println("\tget_content_type");
        System.out.println("\tget_content");
        System.out.println("Exit -> Finish the test");
    }

    /**
     *
     * @param args
     * @throws IOException
     */

    public static void main(String args[])throws IOException {
        /*
        opcions();
        readOpcions();
        Cella test = new Cella();

        while(!option.equals("Exit")) {
            switch (option) {
                case "create_cella":
                    System.out.println("Content type ? -> NUMBER OR HOLE");
                    String ty = readString();
                    System.out.println("The number '0' represents a white space (a cell to be filled with a number for the player/system) and it's the default value for a HOLE");
                    System.out.println("Content ? ");
                    String co = readString();
                    if (ty == "HOLE") {
                        System.out.println("Warning: you are trying to assign a value to a HOLE");
                        System.out.println("The value of a HOLE always will be ignored");
                    }
                    test = new Cella(TCellContent.valueOf(ty),Integer.valueOf(co));
                    break;
                case "set_content_type":
                    System.out.println("The possible content types are -> NUMBER or HOLE");
                    String type = readString();
                    test.setContentType(TCellContent.valueOf(type));
                    break;
                case "set_content":
                    System.out.println("Warning: The values for HOLE type always will be ignored");
                    System.out.println("The number '0' represents a white space (a cell to be filled with a number for the player/system)");
                    System.out.println("Number ?");
                    String con = readString();
                    test.setContent(Integer.valueOf(con));
                    break;
                case "get_content_type":
                    System.out.println(String.valueOf(test.getContentType()));
                    break;
                case "get_content":
                    System.out.println("The default content for a cell it's '0'");
                    System.out.println(String.valueOf(test.getContent()));
                    break;
                case "list":
                    opcions();
                    break;
                default:
                    System.out.println("Invalid option\n you can type 'list' to see the options again");
                    break;
            }
            readOpcions();
        }*/
    }
}

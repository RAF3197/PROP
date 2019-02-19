package classes.Testing;

//Driver for class Usuari


import classes.Usuari;
import lib.IO;
import java.io.IOException;

public class DriverUsuari {
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
        System.out.println("Options of Usuari class:");
        System.out.println("Constructors:");
        System.out.println("\tcreate_usuari -> Create a non empty object of Usuari class");
        System.out.println("Seters:");
        System.out.println("\tset_username");
        System.out.println("\tset_id");
        System.out.println("Geters:");
        System.out.println("\tget_username");
        System.out.println("\tget_id");
        System.out.println("\tget_admin");
        System.out.println("Exit -> Finish the test");
    }

    public static void main(String args[])throws IOException {
        opcions();
        readOpcions();
        Usuari test = new Usuari();
        String username,Id,admin;
        while (!option.equals("Exit")) {
            switch (option) {
                case "create_usuari":
                    System.out.println("Id of the user ? -> The Id it's a integer");
                    Id = readString();
                    System.out.println("Username ?");
                    username = readString();
                    System.out.println("The user it's administrator ? -> yes or no (blanc will be trated as no)");
                    admin = readString();
                    if(admin.equals("yes")||admin.equals("y"))test = new Usuari(Integer.valueOf(Id),username,true);
                    else test = new Usuari(Integer.valueOf(Id),username,false);
                    break;
                case "set_username":
                    System.out.println("Username ?");
                    username = readString();
                    test.setUsername(username);
                    break;
                case "set_id":
                    System.out.println("Id of the user ? -> The Id it's a integer");
                    Id = readString();
                    test.setId(Integer.valueOf(Id));
                    break;
                case "get_username":
                    System.out.println(test.getUsername());
                    break;
                case "get_id":
                    System.out.println(String.valueOf(test.getId()));
                    break;
                case "get_admin":
                    System.out.println(test.isAdmin());
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

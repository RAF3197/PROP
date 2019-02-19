package classes.Testing;

import classes.RankingItem;
import classes.Usuari;
import lib.IO;


import java.io.IOException;

//Driver for RankingItem class

public class DriverRankingItem {

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
        System.out.println("Options for RankingItem class:");
        System.out.println("Constructors:");
        System.out.println("\tcreate_rankingItem -> Create a non empty object of RankingItem class");
        System.out.println("Seters:");
        System.out.println("\tset_user");
        System.out.println("\tset_score");
        System.out.println("Geters:");
        System.out.println("\tget_user");
        System.out.println("\tget_score");
        System.out.println("Exit -> Finish the test");
    }

    public static void main(String args[])throws IOException {
        opcions();
        readOpcions();
        String IsAdmin,username,score;
        boolean admin;
        Usuari testUser;
        RankingItem test = new RankingItem();
        while(!option.equals("Exit")) {
            switch (option) {
                case "create_rankingItem":
                    System.out.println("Specify a user:");
                    System.out.println("Username:");
                    username = readString();
                    System.out.println("The user it's administrator ? -> yes or no (blanc will be trated as no)");
                    IsAdmin = readString();
                    if(IsAdmin.equals("yes"))admin=true;
                    else admin = false;
                    testUser = new Usuari(1234,username,admin);
                    System.out.println("Score ?");
                    score = readString();
                    test = new RankingItem(testUser.getId(),Integer.valueOf(score));
                    break;
                case "set_user":
                    System.out.println("Specify a user:");
                    System.out.println("Username:");
                    username = readString();
                    System.out.println("The user it's administrator ? -> yes or no (blanc will be trated as no)");
                    IsAdmin = readString();
                    if(IsAdmin.equals("yes"))admin=true;
                    else admin = false;
                    testUser = new Usuari(1234,username,admin);
                    test.setUser(testUser.getId());
                    break;
                case "set_score":
                    System.out.println("Score ?");
                    score = readString();
                    test.setPuntuacio(Integer.valueOf(score));
                    break;
                case "get_user":
                    System.out.println("Username: "+test.getUser());
                    //System.out.println("Is administrator: "+String.valueOf(test.getUser().isAdmin()));
                    break;
                case "get_score":
                    System.out.println(String.valueOf(test.getPuntuacio()));
                    break;
                case "list":
                    opcions();
                    break;
                default:
                    System.out.println("Invalid option\n you can type 'list' to see the options again");
                    break;
            }
            readOpcions();
        }
    }
}


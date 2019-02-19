package classes.Testing;

//Driver for Ranking class

import classes.Ranking;
import classes.RankingItem;
import classes.Usuari;
import lib.IO;


import java.util.Random;

import java.io.IOException;
import java.util.ArrayList;

import static lib.IO.readInt;

public class DriverRanking {

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
        System.out.println("Options for Ranking class:");
        System.out.println("Constructors:");
        System.out.println("\tcreate_ranking -> Create a random  Ranking ");
        System.out.println("Seters:");
        System.out.println("\tset_ranking -> get a copy of a ranking");
        System.out.println("\tadd_item -> add a new randomItem to the ranking");
        System.out.println("\tremove_item -> remove a given rankingItem from Ranking");
        System.out.println("Geters:");
        System.out.println("\tget_ranking -> obtain a copy of the ranking");
        System.out.println("\t print_ranking -> print the ranking on the standar I/O chanel");
        System.out.println("Exit -> Finish the test");
    }

    private static RankingItem createItem() {
        Random random=new Random();
        Usuari user=new Usuari(1234,String.valueOf(random.nextInt(9999999)),random.nextBoolean());
        RankingItem a = new RankingItem(user.getId(),random.nextInt());
        return a;
    }

    private static ArrayList<RankingItem> createRanking() {
        Random random=new Random();
        String[] usernames = new String[20];
        Boolean[] admins = new Boolean[20];
        Integer puntuacio;
        ArrayList<RankingItem> items = new ArrayList<RankingItem>();
        Usuari user;
        for (int i=0;i<20;++i){
            usernames[i]=String.valueOf(random.nextInt(9999999));
            admins[i]=random.nextBoolean();
            user=new Usuari(1234,usernames[i],admins[i]);
            puntuacio = random.nextInt(999999);
            items.add(new RankingItem(user.getId(), puntuacio));
        }
        return items;
    }

    public static void main(String args[])throws IOException {
        opcions();
        readOpcions();
        Ranking test = new Ranking();
        ArrayList<RankingItem> rankingItems;
        while(!option.equals("Exit")) {
            switch (option) {
                case "create_ranking":
                    rankingItems=createRanking();
                    test = new Ranking(rankingItems);
                    test.getRanking().sort((a,b) -> b.getPuntuacio().compareTo(a.getPuntuacio()));
                    break;
                case "add_item":
                    test.addRankingItem(createItem());
                    test.getRanking().sort((a,b) -> b.getPuntuacio().compareTo(a.getPuntuacio()));
                    break;
                case "remove_item":
                    System.out.println("Username to remove from the ranking ?");
                    test.removeRankingItem(readInt());
                    test.getRanking().sort((a,b) -> b.getPuntuacio().compareTo(a.getPuntuacio()));
                    break;
                case "get_ranking":
                    System.out.println(test);
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

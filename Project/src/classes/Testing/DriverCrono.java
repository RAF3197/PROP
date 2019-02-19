package classes.Testing;

import classes.Crono;
import classes.Hidato;
import lib.IO;

import java.io.IOException;
import java.util.ArrayList;

public class DriverCrono {
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
        System.out.println("Options for Crono class:");
        System.out.println("Constructors:");
        System.out.println("\tcreate_crono -> Create an object of Crono class");
        System.out.println("\tstart_crono");
        System.out.println("\tpause_crono");
        System.out.println("\trestart_crono");
        System.out.println("\tget_time");
        System.out.println("Exit -> Finish the test");
    }
    public static void main(String args[]) throws IOException {

        Crono cron = new Crono();
        opcions();
        readOpcions();
        while(!option.equals("Exit")) {
            switch (option) {
                case "create_crono":
                    cron=new Crono();
                    break;
                case "start_crono":
                    cron.startCron();
                    break;
                case "pause_crono":
                    cron.pauseCron();
                    break;
                case "restart_crono":
                    cron.restartCron();
                    break;
                case "get_time":
                    if(cron.getTime()<0) System.out.println("You don't have paused the cronometer");
                    else System.out.println(String.valueOf(cron.getTime()));
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

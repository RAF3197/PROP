package classes.Testing;

import classes.Hidato;
import classes.Partida;
import classes.Usuari;
import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.enums.TDifficulty;
import classes.enums.TTopology;
import classes.taulell.TaulellQuadrat;
import classes.taulell.TaulellRectangular;
import classes.taulell.TaulellTriangular;
import lib.IO;
import lib.Manager;
import lib.SQLiteJDBCDriverConnection;

import java.io.IOException;
import java.util.ArrayList;

import static lib.IO.readFileHidatos;

/**
 * Driver for Hidato Class
 * @author Ricard Abril
 */
public class DriverPersistence {

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
        System.out.println("Options:");
        System.out.println("Insert/Update:");
        System.out.println("\tregister -> Register a new user");
        System.out.println("\tadd_hidato -> Add hidato to db");
        System.out.println("\tadd_points -> Add a player's game results to db");
        System.out.println("\tsave_game -> Save a player's last ongoing game status to db. (Register or login needs to be done first)");
        System.out.println("Get:");
        System.out.println("\tlogin -> Check if user is un db");
        System.out.println("\tget_hidato_id -> Get hidato from db with given id");
        System.out.println("\tget_hidato_shape -> Get hidatos from db with given shape");
        System.out.println("\tget_points -> Get a player's game results from db");
        System.out.println("\tcontinue_game -> Load a player's last ongoing game from db");
        System.out.println("\tget_ranking -> Get all saved game results in one hidato from db.");

        System.out.println("Exit -> Finish the test");
    }


    public static void main(String args[]) throws Exception {
        SQLiteJDBCDriverConnection.isDatabaseAccessible();

        ArrayList<Hidato> hidato = new ArrayList<>();
        Hidato test = new Hidato(new TaulellQuadrat());
        hidato = readFileHidatos("hidato_solo.txt");
        Usuari u = new Usuari();
        opcions();
        readOpcions();
        while(!option.equals("Exit")) {
            switch (option) {
                case "register":
                    String [] params = new String[3];
                    System.out.println("Write the username of the player");
                    params[0] = readString();
                    System.out.println("Write the password of the player");
                    params[1] = readString();
                    System.out.println("Will he be admin? -> YES,NO");
                    params[2] = readString();
                    boolean adm;
                    switch (params[2]){
                        case "YES":
                            adm = true;
                            break;
                        default:
                            adm = false;
                            break;

                    }
                    u = Manager.register(params[0], params[1], adm);
                    if(u != null){
                        System.out.println("User added to Database correctly (or already existed) with id: "+u.getId());
                    }
                    else
                    break;
                case "add_hidato":
                    int id = Manager.afegirHidato(hidato.get(0));
                    if(id != -1){
                        System.out.println("Hidato added to Database correctly with id: "+id);
                    }
                    break;
                case "add_points":
                    params = new String[4];
                    System.out.println("Write the id of the player");
                    params[0] = readString();
                    System.out.println("Write the id of the hidato");
                    params[1] = readString();
                    System.out.println("How many points");
                    params[2] = readString();
                    System.out.println("Write the username of the player");
                    params[3] = readString();
                    if(Manager.afegirPuntuacio(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2]), params[3])){
                        System.out.println("Game results added to Database correctly");
                    }
                    break;
                case "save_game":
                    if(u != null) {
                        Partida p = new Partida(hidato.get(0), u);

                        if (Manager.afegirPartida(u.getId(), p) != 999) {
                            System.out.println("Player's game state added to Database correctly");
                        }
                    }
                    break;
                case "login":
                    params = new String[2];
                    System.out.println("Write the username of the player");
                    params[0] = readString();
                    System.out.println("Write the password of the player");
                    params[1] = readString();
                    u = Manager.login(params[0], params[1]);
                    if(u != null){
                        System.out.println("User found in Database with username: "+u.getUsername());
                    }
                    break;
                case "get_hidato_id":
                    params = new String[1];
                    System.out.println("Write the id of the hidato");
                    params[0] = readString();
                    if(Manager.getHidatoId(Integer.valueOf(params[0])) != null){
                        System.out.println("Hidato found in Database");
                    }
                    break;
                case "get_hidato_shape":
                    params = new String[1];
                    System.out.println("Write the shape of the hidato -> SQUARE, RECTANGLE, TRIANGLE");
                    params[0] = readString();
                    System.out.println("Topology "+params[0]+" has " + Manager.getHidatosForma(params[0])+" hidatos in db");
                    break;
                case "get_points":
                    params = new String[2];
                    System.out.println("Write the id of the player");
                    params[0] = readString();
                    System.out.println("Write the id of the hidato");
                    params[1] = readString();
                    if(Manager.getPuntuacio(Integer.valueOf(params[0]), Integer.valueOf(params[1])) != -1){
                        System.out.println("Game results found in Database");
                    }
                    break;
                case "continue_game":
                    params = new String[1];
                    System.out.println("Write the id of the user");
                    params[0] = readString();
                    if(Manager.getPartida(Integer.valueOf(params[0])) != null){
                        System.out.println("User's last game status has been restored");
                    }
                    break;
                case "get_ranking":
                    params = new String[1];
                    System.out.println("Write the id of the hidato");
                    params[0] = readString();
                    System.out.println("Hidato "+params[0]+" has " + Manager.getRanking(Integer.valueOf(params[0])).getRanking().size()+" game results in db");
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

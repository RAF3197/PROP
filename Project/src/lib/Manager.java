package lib;



import classes.Hidato;

import classes.Partida;
import classes.Ranking;
import classes.Usuari;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Marc Ferreiro
 */
public class Manager {
    private static Usuari user = null;
    private static Partida partida = null;
    private static boolean  loadHidato = false;

    /**
     *
     * @return returns current user
     */
    public static Usuari getUser() { return Manager.user; }

    /**
     *
     * @return returns current game
     */
    public static Partida getPartida() { return Manager.partida; }

    /**
     *
     * @return returns true if user is logged in, else false
     */
    public static boolean isUserLoggedIn() {
        return Manager.user != null;
    }

    /**
     *
     * @param username
     * @param password
     * @param isAdmin
     * @return returns registered user
     */
    public static Usuari register(String username, String password, boolean isAdmin) {
        if (Manager.user == null) {
            Usuari user = DBManager.registerUser(username, password, isAdmin);
            if (user != null) {
                Manager.user = user;
            }
            return user;
        }
        return Manager.user;
    }

    /**
     *
     * @param username
     * @param password
     * @return return logged user
     */
    public static Usuari login(String username, String password) {
        if (Manager.user == null) {
            Usuari user = DBManager.loginUser(username, password);
            if (user != null) {
                Manager.user = user;
            }
            return user;
        }
        return Manager.user;
    }

    /**
     *
     * @param b
     */
    public static void setLoading(boolean b){
        Manager.loadHidato = b;
    }

    /**
     *
     * @return return true if we want to load hidatos or false if load scores
     */
    public static boolean getLoading(){
        return Manager.loadHidato;
    }

    /**
     *
     */
    public static void logOut() {
        Manager.user = null;
    }

    /**
     *
     * @param partida
     */
    public static void registerGame(Partida partida) {
        Manager.partida = partida;
    }

    /**
     *
     * @param h
     * @return returns hidato id
     */
    public static int afegirHidato(Hidato h){
        return DBManager.afegirHidato(h);

    }

    /**
     *
     * @param id
     * @return returns hidato with id
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Hidato getHidatoId(int id) throws IOException, ClassNotFoundException {
        return DBManager.getHidatoId(id);
    }

    /**
     *
     * @param forma
     * @return returns list of hidatos with shape forma
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<Hidato> getHidatosForma(String forma) throws IOException, ClassNotFoundException {
        return DBManager.getHidatosForma(forma);
    }

    /**
     *
     * @param IdUser
     * @param IdHidato
     * @return returns user score in hidato
     * @throws SQLException
     */
    public static int getPuntuacio(int IdUser,int IdHidato) throws SQLException {
        return DBManager.getPuntuacio(IdUser,IdHidato);
    }

    /**
     *
     * @param IdUser
     * @param IdHidato
     * @param puntuacio
     * @param username
     * @return add user score in hidato
     * @throws SQLException
     */
    public static boolean afegirPuntuacio(int IdUser, int IdHidato, int puntuacio, String username) throws SQLException {
        return DBManager.afegirPuntuacio(IdUser,IdHidato,puntuacio,username);
    }

    /**
     *
     * @param IdUser
     * @param partida
     * @return return 1 if added, 9999 else
     * @throws SQLException
     */
    public static int afegirPartida(int IdUser, Partida partida) throws SQLException {
        return DBManager.afegirPartida(IdUser,partida);
    }

    /**
     *
     * @param IdUser
     * @return returns user's last game
     * @throws SQLException
     */
    public static Partida getPartida (int IdUser) throws SQLException {
        return DBManager.getPartida(IdUser);
    }

    /**
     *
     * @param IdHidato
     * @return return all scores for hidato id
     * @throws SQLException
     */
    public static Ranking getRanking(int IdHidato) throws SQLException {
        return DBManager.getRanking(IdHidato);
    }
}

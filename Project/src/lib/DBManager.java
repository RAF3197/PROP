package lib;

import classes.Ranking;
import classes.Hidato;
import classes.Partida;
import classes.Usuari;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Marc Ferreiro
 */

public class DBManager {

    /**
     *
     * @param username
     * @param password
     * @return Returns User logged in, else null
     */
    public static Usuari loginUser(String username, String password) {
        return SQLiteJDBCDriverConnection.getUser(username, password);
    }

    /**
     *
     * @param username
     * @param password
     * @param isAdmin
     * @return Returns User registered in, else null
     */
    public static Usuari registerUser(String username, String password, boolean isAdmin) {
        return SQLiteJDBCDriverConnection.registerUser(username, password, isAdmin);
    }

    /**
     *
     * @param h
     * @return returns hidato id
     */
    public static int afegirHidato(Hidato h){
        return SQLiteJDBCDriverConnection.afegirHidato(h);
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Hidato getHidatoId(int id) throws IOException, ClassNotFoundException {
        return SQLiteJDBCDriverConnection.getHidatoId(id);
    }

    /**
     *
     * @param forma
     * @return returns all hidatos with determined shape
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<Hidato> getHidatosForma(String forma) throws IOException, ClassNotFoundException {
        return SQLiteJDBCDriverConnection.getHidatosForma(forma);
    }

    /**
     *
     * @param IdUser
     * @param IdHidato
     * @return returns player score in hidato
     * @throws SQLException
     */
    public static int getPuntuacio(int IdUser,int IdHidato) throws SQLException {
        return SQLiteJDBCDriverConnection.getPuntuacio(IdUser,IdHidato);
    }

    /**
     *
     * @param IdUser
     * @param IdHidato
     * @param puntuacio
     * @param username
     * @return return true if score was added, false else
     * @throws SQLException
     */
    public static boolean afegirPuntuacio(int IdUser, int IdHidato, int puntuacio, String username) throws SQLException {
        return SQLiteJDBCDriverConnection.afegirPuntuacio(IdUser,IdHidato,puntuacio,username);
    }

    /**
     *
     * @param IdUser
     * @param partida
     * @return return 1 if game was added, 9999 else
     * @throws SQLException
     */
    public static int afegirPartida (int IdUser, Partida partida) throws SQLException {
        return  SQLiteJDBCDriverConnection.afegirPartida(IdUser,partida);
    }

    /**
     *
     * @param IdUser
     * @return returns user's last game
     * @throws SQLException
     */
    public static Partida getPartida (int IdUser) throws SQLException {
        return SQLiteJDBCDriverConnection.getPartida(IdUser);
    }

    /**
     *
     * @param IdHidato
     * @return returns all scores for one hidato
     * @throws SQLException
     */
    public static Ranking getRanking(int IdHidato) throws SQLException {
        return SQLiteJDBCDriverConnection.getRanking(IdHidato);
    }
}

package lib;

import classes.*;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Marc Ferreiro
 */
public class SQLiteJDBCDriverConnection {

    private static Connection connection;

    /**
     *
     * @param IdUser
     * @return retun user's last game
     * @throws SQLException
     */
    public static Partida getPartida(int IdUser) throws SQLException {
        PreparedStatement ps = null;
        Partida partida = null;
        ResultSet rs = null;
        try{
            connect();
            ps = connection.prepareStatement("SELECT partida FROM partides WHERE IdUser=?");
            ps.setInt(1,IdUser);
            rs = ps.executeQuery();
            if (rs.next()) {
                byte[] aBlob = rs.getBytes("partida");
                ByteArrayInputStream baip = new ByteArrayInputStream(aBlob);
                ObjectInputStream ois = new ObjectInputStream(baip);
                partida = (Partida) ois.readObject();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            ps.close();
            disconnect();
        }

        return partida;
    }

    /**
     *
     * @param IdUser
     * @param partida
     * @return returns 1 if added, 9999 else
     * @throws SQLException
     */
    public static int afegirPartida(int IdUser, Partida partida) throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        int rs = 9999;
        System.out.println(IdUser);
        System.out.println(partida.getHidato().getId());
        try {
            connect();
            ps = connection.prepareStatement("INSERT OR REPLACE INTO partides (IdUser,partida) VALUES (?,?)");
      //      ps2 = connection.prepareStatement("UPDATE partides SET partida=? WHERE IdUser=?");

            ps.setInt(1,IdUser);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(partida);
            byte[] partidaAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(partidaAsBytes);

            ps.setBinaryStream(2, bais, partidaAsBytes.length);

  //          ps2.setInt(2,IdUser);
//            ps2.setBinaryStream(1, bais, partidaAsBytes.length);

            ps.execute();
            rs = 1;
    //        rs = ps2.executeUpdate();
           // if(rs!=1){

             //   rs = 20;
            //}

        } catch (Exception e) {
            e.printStackTrace();
            rs = 9999;
           // System.out.println(e.getMessage());
        }
        finally {
            ps.close();
//            ps2.close();
            disconnect();
        }
        return rs;
    }

    /**
     *
     * @param IdHidato
     * @return return all scores for hidato i
     * @throws SQLException
     */
    public static Ranking getRanking(int IdHidato) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Ranking ranking = new Ranking();
        try {
            connect();
            ps = connection.prepareStatement("SELECT * FROM rankings WHERE IdHidato = ?");
            System.out.println("ID GETTING: "+IdHidato);
            ps.setInt(1,IdHidato);
            rs = ps.executeQuery();

            while (rs.next()) {
                RankingItem a = new RankingItem();
                a.setPuntuacio(rs.getInt("puntuacio"));
                a.setUser(rs.getInt("IdUser"));
                a.setIdHidato(rs.getInt("IdHidato"));
                a.setUsername(rs.getString("username"));
                System.out.println(a.getPuntuacio());
                boolean b = ranking.addRankingItem(a);
                System.out.println(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            rs.close();
            ps.close();
            disconnect();
        }
        return ranking;
    }

    /**
     *
     * @param IdUser
     * @param IdHidato
     * @return return user score in hidato
     * @throws SQLException
     */
    public static  int getPuntuacio(int IdUser, int IdHidato) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int score = -1;
        try {
            connect();
            ps = connection.prepareStatement("SELECT puntuacio FROM rankings WHERE IdUser = ? AND IdHidato = ?");
            ps.setInt(1,IdUser);
            ps.setInt(2,IdHidato);
            rs = ps.executeQuery();
            while (rs.next()) {
                score = rs.getInt("puntuacio");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            rs.close();
            ps.close();
            disconnect();
        }

        return score;
    }

    /**
     *
     * @param IdUser
     * @param IdHidato
     * @param puntuacio
     * @param username
     * @return return true if score added, false else
     * @throws SQLException
     */
    public static boolean afegirPuntuacio(int IdUser,int IdHidato,int puntuacio,String username) throws SQLException {
        PreparedStatement ps = null;
        boolean rs = false;
        try{

            connect();
            //System.out.println("connecteeeeeeed");
            ps = connection.prepareStatement("INSERT OR REPLACE INTO rankings (username,IdHidato,IdUser,puntuacio) VALUES (?,?,?,?)");

            ps.setInt(3,IdUser);
            ps.setInt(2,IdHidato);
            ps.setInt(4,puntuacio);
            ps.setString(1,username);

            rs = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally {
            ps.close();
            disconnect();
        }
        return rs;
    }

    /**
     *
     * @param h
     * @return return hidato id
     */
    public static int afegirHidato(Hidato h) {
        PreparedStatement ps = null;
        int id = -1;
        try {
            connect();
            ps = connection.prepareStatement("INSERT INTO hidatos(hidato,forma) VALUES (?,?)");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(h);
            byte[] hidatoAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(hidatoAsBytes);
            ps.setBinaryStream(1, bais, hidatoAsBytes.length);
            ps.setString(2,String.valueOf(h.getTaulell().getShape()));

            ps.executeUpdate();


            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     *
     * @param id
     * @return returns hidato with id
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Hidato getHidatoId(int id) throws IOException, ClassNotFoundException {
        Hidato hidato = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connect();
            ps = connection.prepareStatement("SELECT * FROM hidatos WHERE id = ?");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next()) {
                byte[] aBlob = rs.getBytes("hidato");
                ByteArrayInputStream baip = new ByteArrayInputStream(aBlob);
                ObjectInputStream ois = new ObjectInputStream(baip);
                hidato = (Hidato) ois.readObject();
                hidato.setId(id);
            }

        } catch (SQLException a) {
            a.printStackTrace();
        }
        finally {
            try {
                rs.close();
                ps.close();
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hidato;
    }

    /**
     *
     * @param formas
     * @return return all hidatos with shape
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<Hidato> getHidatosForma(String formas) throws IOException, ClassNotFoundException {
        Hidato hidato = null;
        ArrayList<Hidato> hidatos = new ArrayList<Hidato>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connect();
            ps = connection.prepareStatement("SELECT * FROM hidatos WHERE forma = ?");
            ps.setString(1,formas);
            rs = ps.executeQuery();
            while (rs.next()) {
                hidato = null;
                byte[] aBlob = rs.getBytes("hidato");
                ByteArrayInputStream baip = new ByteArrayInputStream(aBlob);
                ObjectInputStream ois = new ObjectInputStream(baip);
                hidato = (Hidato) ois.readObject();
                int id = rs.getInt("id");
                hidato.setId(id);
                hidatos.add(hidato);
            }

        } catch (SQLException a) {
            a.printStackTrace();
        }
        finally {
            try {
                rs.close();
                ps.close();
                disconnect();
              //  return hidatos;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        //    return hidatos;
        }
        return hidatos;
    }

    /**
     *
     * @param username
     * @param password
     * @return returns loggin user
     */
    public static Usuari getUser(String username, String password) {
        Usuari user = null;
        try {
            connect();
            ResultSet rs = executeQuery("SELECT * FROM users WHERE username == '" + username + "' AND password == '" + password + "';");
            while(rs.next()) {
                int x = rs.getInt("admin");
                boolean b;
                if (x!=0) b = true;
                else b = false;
                user = new Usuari(rs.getInt("id"), rs.getString("username"), b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }
    }

    /**
     *
     * @param username
     * @param password
     * @param isAdmin
     * @return return register user
     */
    public static Usuari registerUser(String username, String password, boolean isAdmin) {
        int result = 0;
        try {
            connect();
            result = executeUpdate("INSERT INTO users(username, password, admin)\n" +
                    "VALUES ('"+ username +"', '"+ password +"', "+ (isAdmin ? 1 : 0) +");");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (result == 1) return getUser(username, password);
            else return null;
        }
    }

    /**
     *
     * @return returns bool is database accessible
     */
    public static boolean isDatabaseAccessible() {
        try {
            connect();
            checkDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                disconnect();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                connection = null;
                return false;
            }
        }
    }

    /**
     *
     * @throws SQLException
     */
    private static void connect() throws SQLException {
        connection = null;
        System.out.println("Checking for db...");
        File f = new File("./db", "hidato-game.db");
        if(!f.exists()){
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("File created... " + f.getAbsolutePath());
        //URL path = SQLiteJDBCDriverConnection.class.getClassLoader().getResource("/db/hidato-game.db");
        //String path = f.getAbsolutePath();
        String path = f.getPath();
        //String path = ":resource:db/hidato-game.db";
        // db parameters
        String url = "jdbc:sqlite:" + path;
        // create a connection to the database
        connection = DriverManager.getConnection(url);

        System.out.println("Connection to SQLite has been established.");
    }

    /**
     *
     * @throws SQLException
     */
    private static void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     *
     * @throws SQLException
     */
    private static void checkDataBase() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(name) FROM sqlite_master WHERE type='table'");
        if (rs.next()) {
            if (rs.getInt(1) == 0) {
                System.out.println("Creating schema...");
                SQLiteJDBCDriverConnection.createDataBase();
                System.out.println("Schema created!");
            }
        }
    }

    /**
     *
     * @param query
     * @return return resultset of query db
     * @throws SQLException
     */
    private static ResultSet executeQuery(String query) throws SQLException {
        Statement st = connection.createStatement();
        return st.executeQuery(query);
    }

    /**
     *
     * @param query
     * @return return number of updates
     * @throws SQLException
     */
    private static int executeUpdate(String query) throws SQLException {
        Statement st = connection.createStatement();
        return st.executeUpdate(query);
    }

    /**
     *
     * @param in
     * @throws SQLException
     */
    public static void importSQL(InputStream in) throws SQLException {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|((\r)?\n)?(--)?.*(--(\r)?\n)");
        Statement st = null;
        try {
            st = SQLiteJDBCDriverConnection.connection.createStatement();
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        }
        finally {
            if (st != null) st.close();
        }
    }

    private static void createDataBase() throws SQLException {
        Statement st = null;
        try {
            st = SQLiteJDBCDriverConnection.connection.createStatement();
            st.execute("DROP TABLE IF EXISTS 'hidatos'\n");
            st.execute("CREATE TABLE IF NOT EXISTS 'hidatos' (\n" +
                    "  `id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  `hidato`\tBLOB,\n" +
                    "  `forma` TEXT\n" +
                    ")");
            st.execute("DROP TABLE IF EXISTS `users`;");
            st.execute("CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "  `id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  `username`\tTEXT NOT NULL UNIQUE,\n" +
                    "  `password`\tTEXT NOT NULL,\n" +
                    "  `admin`\tINTEGER NOT NULL DEFAULT 0\n" +
                    ")");
            st.execute("INSERT INTO `users` VALUES (1,'test','1234',1);");
            st.execute("CREATE TABLE IF NOT EXISTS `rankings` (username TEXT,\n" +
                    "                                      IdHidato INTEGER,\n" +
                    "                                      IdUser INTEGER,\n" +
                    "                                      puntuacio INTEGER,\n" +
                    "                                      PRIMARY KEY (IdHidato, IdUser))");
            st.execute("CREATE TABLE IF NOT EXISTS `partides` (idUser int PRIMARY KEY ,partida BLOB)");
        }
        finally {
            if (st != null) st.close();
        }
    }
}

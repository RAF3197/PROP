package classes;

import java.io.Serializable;

/**
 * @author Marc Ferreiro
 * @author Bernat Felip
 * @author Ricard Abril
 */

public class Usuari implements Serializable {
    private int id;
    private String username;
    private boolean isAdmin;

    /**
     *
     * @param id
     * @param username
     * @param isAdmin
     */
    public Usuari(int id, String username, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    /**
     *
     */
    public Usuari(){}

    /**
     *
     * @return Returns the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return Returns the Id of the user
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Returns true if the user have Admin privileges
     */
    public boolean isAdmin() {
        return this.isAdmin;
    }
}

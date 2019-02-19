package classes;

/**
 * @author Ricard Abril
 */

public class RankingItem {
    private int IdHidato;
    private int user;
    private Integer puntuacio;
    private String username;

    /**
     *
     * @param user
     * @param puntuacio
     */

    public RankingItem(int user, Integer puntuacio){
        this.user = user;
        this.puntuacio = puntuacio;
    }

    public RankingItem(int user, Integer puntuacio,int IdHidato,String username){
        this.user = user;
        this.puntuacio = puntuacio;
        this.IdHidato = IdHidato;
        this.username = username;
    }

    /**
     *
     */
    public RankingItem(){}

    /**
     *
     * @return Returns the Id of the user
     */

    public int getUser() {

        return this.user;
    }

    /**
     *
     * @return Returns the score of the ranking element
     */

    public Integer getPuntuacio() {

        return this.puntuacio;
    }

    /**
     *
     * @param puntuacio
     */

    public void setPuntuacio(Integer puntuacio) {

        this.puntuacio = puntuacio;
    }

    /**
     *
     * @param user
     */

    public void setUser(int user) {

        this.user = user;
    }

    /**
     *
     * @param idHidato
     */

    public void setIdHidato(int idHidato) {
        IdHidato = idHidato;
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
     * @return Returns the Id of a Hidato
     */

    public int getIdHidato() {
        return IdHidato;
    }

    /**
     *
     * @return Returns the username of the user
     */

    public String getUsername() {
        return username;
    }

}


package classes;


import classes.enums.TCellShape;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.Stack;


/**
 * @author Ricard Abril, Bernat Felip
 */
public class Partida implements Serializable {

    private Hidato hidato;
    private Usuari jugador;
    private Crono cronometre;
    private Stack<Log> timeline;

    /**
     *
     * @param hidato
     * @param jugador
     */
    public Partida(Hidato hidato, Usuari jugador) {
        this.hidato = hidato;
        this.jugador = jugador;
        this.cronometre = new Crono();
        this.timeline = new Stack<>();
    }
    /**
     *
     * @param hidato
     * @param jugador
     * @param cronometre
     */
    public Partida(Hidato hidato, Usuari jugador, Crono cronometre) {

        this.hidato = hidato;
        this.jugador = jugador;
        this.cronometre = cronometre;
        this.timeline = new Stack<>();

    }

    /**
     *
     */

    public void startGame(){

        cronometre.startCron();
    }

    /**
     *
     */

    public void pauseGame(){

        cronometre.pauseCron();
    }
    /**
     *
     */

    public void continueGame(){

        cronometre.restartCron();
    }


    /**
     *
     */

    public void finishGame(){

        cronometre.pauseCron();

    }

    /**
     *
     * @return Returns the Hidato of the current match
     */

    public Hidato getHidato() {

        return this.hidato;
    }

    /**
     *
     * @return Return the player of the current match
     */

    public Usuari getJugador() {

        return this.jugador;
    }

    /**
     *
     * @return Return the Crono state
     */

    public Crono getCronometre() {

        return this.cronometre;
    }

    /**
     *
     * @param hidato
     */

    public void setHidato(Hidato hidato) {

        this.hidato = hidato;
    }

    /**
     *
     * @param jugador
     */

    public void setJugador(Usuari jugador) {

        this.jugador = jugador;
    }

    /**
     *
     * @param fila
     * @param columna
     * @return Returns true if it's possible to do a Step Foward
     */

    public Boolean stepForward(Integer fila,Integer columna){
        if(this.hidato.putCell(fila, columna)){
            timeline.push(new Log(fila, columna));
            return true;
        }
        else return false;
    }

    /**
     *
     * @return Returns true if it's possible to do a Step Back
     */

    public Boolean stepBack(){
        if(!timeline.empty()){
            Log aux = timeline.pop();
            if(this.hidato.popCell(aux.getX(), aux.getY())){
                return true;
            }
            else timeline.push(aux);
        }
        return false;
    }

    /**
     *
     */

    public void reset(){
        while(stepBack());
    }

    /**
     *
     * @param x
     * @param y
     * @return Returns true if we had add somthing in the position x,y
     */

    public boolean inTimeline(int x, int y){
        for(Log obj : this.timeline)
        {
            if (obj.getX() == x && obj.getY() == y) return true;
        }
        return false;
    }


    /**
     *
     * @return Returns a int with the number of rows
     */

    public Integer getFiles(){

        return hidato.getTaulell().getNombreFiles();
    }

    /**
     *
     * @return Returns a int with the number of colummns
     */

    public Integer getColumnes(){

        return hidato.getTaulell().getNombreColumnes();
    }

    /**
     * @return Returns the type of cell
     */

    public TCellShape getTipusCela(){

        return hidato.getTaulell().getTipusCela();
    }

}

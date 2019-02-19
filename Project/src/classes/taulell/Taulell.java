package classes.taulell;

import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.enums.TTopology;
import org.junit.runners.model.TestTimedOutException;

import java.io.Serializable;

/**
 * @author Marc Ferreiro
 */

public abstract class Taulell implements Serializable {
    private TCellShape tipusCela;
    private TAdjacency tipusAdjacencia;
    private int numHoles;
    private int nombreFiles;
    private int nombreColumnes;
    private Integer numCaselles;
    private Integer numGiven;

    /**
     *
     * @return Returns the number of cells of a table
     */

    public Integer getNumCaselles() {
        return numCaselles;
    }

    /**
     *
     * @param numCaselles
     */

    public void setNumCaselles(Integer numCaselles) {
        this.numCaselles = numCaselles;
    }

    /**
     *
     * @return Returns the number of cells with a value given
     */

    public Integer getNumGiven() {
        return numGiven;
    }

    /**
     *
     * @param numGiven
     */

    public void setNumGiven(Integer numGiven) {
        this.numGiven = numGiven;
    }

    /**
     *
     * @return Returns the number of holes of a table
     */

    public Integer getNumHoles() {
        return numHoles;
    }

    /**
     *
     * @param numHoles
     */

    public void setNumHoles(Integer numHoles) {
        this.numHoles = numHoles;
    }

    /**
     *
     * @return Abstract function that returns the shape of a table
     */

    public abstract TTopology getShape();

    /**
     *
     * @return Abstract function that returns all the position in a table
     */

    public abstract int[][] getAllPositions();

    /**

     *
     * @param tipusCela
     * @param tipusAdj
     * @param files
     * @param columnes
     */

    public Taulell(TCellShape tipusCela, TAdjacency tipusAdj, Integer files, Integer columnes) {
        this.tipusCela = tipusCela;
        this.tipusAdjacencia = tipusAdj;
        this.nombreColumnes = columnes;
        this.nombreFiles = files;
    }

    /**
     *
     */

    public Taulell() { }

    /**
     *
     */

    public abstract void generateTaulell();

    /**
     *
     * @return Returns the shape of the cells in the table
     */

    public TCellShape getTipusCela() {
        return tipusCela;
    }

    /**
     *
     * @param tipusCela
     */

    public void setTipusCela(TCellShape tipusCela) {
        this.tipusCela = tipusCela;
    }

    /**
     *
     * @return Returns the adjacency type of the cells in a table
     */

    public TAdjacency getTipusAdjacencia() {
        return tipusAdjacencia;
    }

    /**
     *
     * @param tipusAdjacencia
     */

    public void setTipusAdjacencia(TAdjacency tipusAdjacencia) {
        this.tipusAdjacencia = tipusAdjacencia;
    }

    /**
     *
     * @return Returns the number of rows of the table
     */

    public Integer getNombreFiles() {
        return nombreFiles;
    }

    /**
     *
     * @param nombreFiles
     */

    public void setNombreFiles(Integer nombreFiles) {
        this.nombreFiles = nombreFiles;
    }

    /**
     *
     * @return Returns the number of columns of the table
     */

    public Integer getNombreColumnes() {
        return nombreColumnes;
    }

    /**
     *
     * @param nombreColumnes
     */

    public void setNombreColumnes(Integer nombreColumnes) {
        this.nombreColumnes = nombreColumnes;
    }
}

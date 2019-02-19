package classes.cella;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;


import java.io.Serializable;

import classes.enums.TCellShape;


/**
 * @author Marc Ferreiro
 */

public abstract class Cella implements Serializable{
    protected TCellContent contentType;
    protected Integer content;
    protected TAdjacency adjacencia;


    /**
     *
     */

    public Cella() {
        this.content = 0;
    }

    /**
     *
     * @param type
     * @param content
     */

    public Cella(TCellContent type, Integer content) {
        this.content = content;
        this.contentType = type;
    }

    /**
     *
     * @param type
     */

    public Cella(TCellContent type) {
        this.contentType = type;
        this.content = 0;
    }

    /**
     *
     * @param aux
     */

    public Cella(Cella aux) {
        this.contentType = aux.contentType;
        this.content  = aux.content;
    }

    /**
     *
     * @return Abstract function that returns the type of adjacency of a cell
     */

    public abstract TAdjacency getTipusAdjacencia();

    /**
     *
     * @return Abstract function that returns the number of adjacencies of a cell
     */

    public abstract int getNumeroAdjacencies();

    /**
     *
     * @param r
     * @param c
     * @return Abstract function that returns a matrix of int's that contains the values to sum to a cell position to get their adjacencies
     */

    public abstract int[][] getAdjacencies(int r, int c);

    /**
     *
     * @return Abstract function that returns the shape of a cell
     */

    public abstract TCellShape getShape();

    /**
     *
     * @return Returns the content type of a cell
     */

    public TCellContent getContentType() {
        return contentType;
    }

    /**
     *
     * @param contentType
     */

    public void setContentType(TCellContent contentType) {
        this.contentType = contentType;
    }

    /**
     *
     * @return Returns the content of a cell
     */

    public Integer getContent() {
        return content;
    }

    /**
     *
     * @param content
     */

    public void setContent(Integer content) {
        this.content = content;
    }

    /**
     *
     * @return Abstract function that returns a copy of a given cell
     */

    public abstract Cella copyCell();

    /**
     *
     * @return Returns a cell object in String type
     */

    @Override
    public String toString() {
        String result;
        Integer cont = this.getContent();
        if (cont == 0) {
            result = "_";
        } else {
            result = cont.toString();
        }
        return result;
    }
}

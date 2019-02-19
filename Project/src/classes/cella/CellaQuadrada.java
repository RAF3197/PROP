package classes.cella;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;
import classes.enums.TCellShape;

/**
 * @author Marc Ferreiro
 */

public class CellaQuadrada extends Cella {

    /**
     *
     * @param type
     * @param adjacencia
     */

    public CellaQuadrada(TCellContent type, TAdjacency adjacencia) {
        super(type);
    }

    /**
     *
     * @param type
     * @param adjacencia
     * @param content
     */

    public CellaQuadrada(TCellContent type, TAdjacency adjacencia, int content) {
        super(type, content);
    }

    /**
     *
     * @param aux
     * @param adjacencia
     */

    public CellaQuadrada(CellaTriangular aux, TAdjacency adjacencia) {
        super(aux);
    }

    /**
     *
     * @return Returns the shape of the Cells
     */

    @Override
    public TCellShape getShape() {
        return TCellShape.SQUARE;
    }

    /**
     *
     * @return Returns the number of adjacencies of a Cell
     */

    @Override
    public int getNumeroAdjacencies() {
        if (this.adjacencia == TAdjacency.SIDE){
            return 4;
        } else {
            return 8;
        }
    }

    /**
     *
     * @return Returns the type of adjacency
     */

    @Override
    public TAdjacency getTipusAdjacencia() {
        return this.adjacencia;
    }

    /**
     *
     * @param r
     * @param c
     * @return Returns a matrix of int's that contains the values to sum to a cell position to get their adjacencies
     */

    @Override
    public int[][] getAdjacencies(int r, int c) {
        int adj[][] = new int[this.getNumeroAdjacencies()][2];
        if (this.adjacencia == TAdjacency.SIDE) {
            adj[0][0] = -1; adj[0][1] = 0;
            adj[1][0] = 0; adj[1][1] = 1;
            adj[2][0] = 1; adj[2][1] = 0;
            adj[3][0] = 0; adj[3][1] = -1;
        } else {
            adj[0][0] = -1; adj[0][1] = 0;
            adj[1][0] = 0; adj[1][1] = 1;
            adj[2][0] = 1; adj[2][1] = 0;
            adj[3][0] = 0; adj[3][1] = -1;
            adj[4][0] = -1; adj[4][1] = 1;
            adj[5][0] = 1; adj[5][1] = 1;
            adj[6][0] = 1; adj[6][1] = -1;
            adj[7][0] = -1; adj[7][1] = -1;
        }
        return adj;
    }

    /**
     *
     * @return Returns a copy of a cell
     */

    @Override
    public Cella copyCell() {
        Cella aux = new CellaQuadrada(this.contentType, this.adjacencia, this.content);
        return aux;
    }
}

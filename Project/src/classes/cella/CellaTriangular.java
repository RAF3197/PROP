package classes.cella;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;
import classes.enums.TCellShape;

/**
 *
 */

public class CellaTriangular extends Cella {

    /**
     *
     * @param type
     * @param adjacencia
     *
     */

    public CellaTriangular(TCellContent type, TAdjacency adjacencia) {
        super(type);
        this.adjacencia = adjacencia;
    }

    /**
     *
     * @param type
     * @param adjacencia
     * @param content
     */

    public CellaTriangular(TCellContent type, TAdjacency adjacencia, int content) {
        super(type, content);
        this.adjacencia = adjacencia;
    }

    /**
     *
     * @param aux
     */

    public CellaTriangular(CellaTriangular aux) {
        super(aux);
        this.adjacencia = aux.adjacencia;
    }

    /**
     *
     * @return Returns the number of adjacencies of a triangular cell
     */

    @Override
    public int getNumeroAdjacencies() {
        if (this.adjacencia == TAdjacency.SIDE){
            return 3;
        } else {
            return 12;
        }
    }

    /**
     *
     * @param r
     * @param c
     * @return Returns a matrix of int's that contains the values to sum to a cell position to get their adjacencies
     */

    @Override
    public int[][] getAdjacencies(int r, int c) {
        boolean isPair = (r+c)%2 == 0;
        int adj[][] = new int[this.getNumeroAdjacencies()][2];
        if (this.adjacencia == TAdjacency.SIDE) {
            adj[0][0] = 0; adj[0][1] = -1;
            adj[1][0] = 0; adj[1][1] = 1;
            if (isPair) {
                adj[2][0] = 1; adj[2][1] = 0;
            } else {
                adj[2][0] = -1; adj[2][1] = 0;
            }

        } else {
            adj[0][0] = -1; adj[0][1] = 0;
            adj[1][0] = -1; adj[1][1] = 1;
            adj[2][0] = 0; adj[2][1] = 1;
            adj[3][0] = 0; adj[3][1] = 2;
            adj[4][0] = 1; adj[4][1] = 1;
            adj[5][0] = 1; adj[5][1] = 0;
            adj[6][0] = 1; adj[6][1] = -1;
            adj[7][0] = 0; adj[7][1] = -2;
            adj[8][0] = 0; adj[8][1] = -1;
            adj[9][0] = -1; adj[9][1] = -1;
            if (isPair) {
                adj[10][0] = 1; adj[10][1] = -2;
                adj[11][0] = 1; adj[11][1] = 2;
            } else {
                adj[10][0] = -1; adj[10][1] = 2;
                adj[11][0] = -1; adj[11][1] = -2;
            }
        }
        return adj;
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
     * @return Returns the shape of the Cells
     */

    @Override
    public TCellShape getShape() {
        return TCellShape.TRIANGLE;
    }

    /**
     *
     * @return Return a copy of a Cell
     */

    @Override
    public Cella copyCell() {
        Cella aux = new CellaTriangular(this.contentType, this.adjacencia, this.content);
        return aux;
    }
}

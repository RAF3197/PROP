package classes.cella;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;
import classes.enums.TCellShape;

/**
 * @author Marc Ferreiro
 */

public class CellaHexagonal extends Cella {

    /**
     *
     * @param type
     * @param adjacencia
     */

    public CellaHexagonal(TCellContent type, TAdjacency adjacencia) {
        super(type);
    }

    /**
     *
     * @param type
     * @param adjacencia
     * @param content
     */

    public CellaHexagonal(TCellContent type, TAdjacency adjacencia, int content) {
        super(type, content);
    }

    /**
     *
     * @param aux
     * @param adjacencia
     */

    public CellaHexagonal(CellaTriangular aux, TAdjacency adjacencia) {
        super(aux);
    }

    /**
     *
     * @return Returns the shape of the cells
     */

    @Override
    public TCellShape getShape() {
        return TCellShape.HEXAGON;
    }

    /**
     *
     * @return Returns the number of adjacencies
     */

    @Override
    public int getNumeroAdjacencies() {
        return 6;
    }

    /**
     *
     * @return Return the type of adjacency
     */

    @Override
    public TAdjacency getTipusAdjacencia() {
        return this.adjacencia;
    }

    /**
     *
     * @param r
     * @param c
     * @return Returns the type of adjacency
     */

    @Override
    public int[][] getAdjacencies(int r, int c) {
        boolean isPair = (r-1)%2 == 0;
        int adj[][] = new int[this.getNumeroAdjacencies()][2];
        adj[0][0] = -1; adj[0][1] = -1;
        adj[1][0] = -1; adj[1][1] = 0;
        adj[2][0] = 0; adj[2][1] = 1;
        adj[3][0] = 1; adj[3][1] = 0;
        adj[4][0] = 1; adj[4][1] = -1;
        adj[5][0] = 0; adj[5][1] = -1;
        if (!isPair) {
            adj[0][1] = adj[0][1]+1;
            adj[1][1] = adj[1][1]+1;
            adj[3][1] = adj[3][1]+1;
            adj[4][1] = adj[4][1]+1;
        }
        return adj;
    }

    /**
     *
     * @return Returns a copy of a cell
     */

    @Override
    public Cella copyCell() {
        Cella aux = new CellaHexagonal(this.contentType, this.adjacencia, this.content);
        return aux;
    }
}

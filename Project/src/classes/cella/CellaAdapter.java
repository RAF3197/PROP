package classes.cella;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;
import classes.enums.TCellShape;

/**
 *
 */

public class CellaAdapter {

    /**
     *
     * @param shape
     * @param adjacencia
     * @param type
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @return Returns a instance of Cella
     */

    public static Cella createInstance(TCellShape shape, TAdjacency adjacencia, TCellContent type) throws InstantiationException, IllegalAccessException{
        Cella cella;
        switch (shape) {
            case SQUARE: cella = new CellaQuadrada(type, adjacencia);
            break;
            case TRIANGLE: cella = new CellaTriangular(type, adjacencia);
            break;
            default: cella = new CellaHexagonal(type, adjacencia);
        }
        return cella;
    }

    /**
     *
     * @param shape
     * @param adjacencia
     * @param type
     * @param content
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @return Returns a instance of Cella
     */

    public static Cella createInstance(TCellShape shape, TAdjacency adjacencia, TCellContent type, int content) throws InstantiationException, IllegalAccessException{
        Cella cella;
        switch (shape) {
            case SQUARE: cella = new CellaQuadrada(type, adjacencia, content);
                break;
            case TRIANGLE: cella = new CellaTriangular(type, adjacencia, content);
                break;
            default: cella = new CellaHexagonal(type, adjacencia, content);
        }
        return cella;
    }

    /**
     *
     * @param cella
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @return Returns a duplicate instance of a given instance
     */

    public static Cella duplicateInstance(Cella cella) throws InstantiationException, IllegalAccessException{
        Cella newInstance;
        switch (cella.getShape()) {
            case SQUARE: newInstance = new CellaQuadrada(cella.contentType, cella.adjacencia, cella.content);
                break;
            case TRIANGLE: newInstance = new CellaTriangular(cella.contentType, cella.adjacencia, cella.content);
                break;
            default: newInstance = new CellaHexagonal(cella.contentType, cella.adjacencia, cella.content);
        }
        return newInstance;
    }
}

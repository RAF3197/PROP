package classes.taulell;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;
import classes.enums.TCellShape;
import classes.enums.TTopology;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marc Ferreiro
 */

public class TaulellTriangular extends Taulell {

    /**
     * Generates a Triangular Table
     */

    @Override
    public void generateTaulell() {
        Double aux = Math.ceil(Math.sqrt(this.getNumCaselles().doubleValue()));
        this.setNombreFiles((aux.intValue() % 2 == 0) ? aux.intValue()+1 : aux.intValue());
        this.setNombreColumnes((this.getNombreFiles()-1)*2+1);
        aux = Math.pow(this.getNombreFiles(),2.0);
        this.setNumHoles(aux.intValue() - this.getNumCaselles());
        this.setNumCaselles(aux.intValue());
    }

    /**
     *
     */

    public TaulellTriangular() {}

    /**
     *
     * @return Returns the shape of the table
     */

    @Override
    public TTopology getShape() {
        return TTopology.TRIANGLE;
    }

    /**
     *
     * @return Returns all the positions in a table
     */

    @Override
    public int[][] getAllPositions() {
        List<int[]> pos = new ArrayList<>();
        List<int[]> posAux = new ArrayList<>();
        System.out.println("Columnes: " + this.getNombreColumnes());
        for(int i = 1; i < this.getNombreFiles()+1; ++i) {
            for(int j = 0; j < i; ++j) {
                int[] p = new int[2];
                p[0] = i;
                p[1] = ((this.getNombreColumnes()/2)+j+1);
                pos.add(p);

                p = new int[2];
                p[0] = i;
                p[1] = ((this.getNombreColumnes()/2)-j+1);
                pos.add(p);
            }
        }
        for (int i = 0 ; i < pos.size() ; i++) {
            int[] p = new int[2];
            p[0] = pos.get(i)[0]; p[1] = pos.get(i)[1];
            boolean add = true;
            for (int y = 0 ; y < posAux.size() ; y++) {
                if (posAux.get(y)[0] == p[0] && posAux.get(y)[1] == p[1]) {
                    add = false;
                }
            }
            if (add) {
                posAux.add(p);
            }
        }

        int[][] posArray = new int[posAux.size()][2];
        for (int i = 0 ; i < posAux.size() ; i++) {
            posArray[i][0] = posAux.get(i)[0]; posArray[i][1] = posAux.get(i)[1];
        }

        return posArray;
    }

    /**
     *
     * @param tipusCela
     * @param tipusAdj
     * @param files
     * @param columnes
     */

    public TaulellTriangular(TCellShape tipusCela, TAdjacency tipusAdj, Integer files, Integer columnes) {
        super(tipusCela, tipusAdj, files, columnes);
    }
}

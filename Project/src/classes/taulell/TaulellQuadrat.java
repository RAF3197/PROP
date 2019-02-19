package classes.taulell;

import classes.enums.TAdjacency;
import classes.enums.TCellContent;
import classes.enums.TCellShape;
import classes.enums.TTopology;

/**
 * @author Marc Ferreiro
 */

public class TaulellQuadrat extends Taulell {

    /**
     *
     */

    @Override
    public void generateTaulell() {
        Double aux = Math.ceil(Math.sqrt(this.getNumCaselles().doubleValue()));
        this.setNombreFiles(aux.intValue());
        this.setNombreColumnes(aux.intValue());
        aux = Math.pow(this.getNombreFiles(),2.0);
        this.setNumHoles(aux.intValue() - this.getNumCaselles());
        this.setNumCaselles(aux.intValue());
    }

    /**
     *
     */

    public TaulellQuadrat() {}

    /**
     *
     * @return Returns the shape of a table
     */

    @Override
    public TTopology getShape() {
        return TTopology.SQUARE;
    }

    /**
     *
     * @return Returns all the positions in a table
     */

    @Override
    public int[][] getAllPositions() {
        int[][] pos = new int[this.getNombreFiles()*this.getNombreColumnes()][2];
        int current = 0;
        for(int i = 1; i < this.getNombreFiles()+1;++i){
            for(int j = 1; j < this.getNombreColumnes()+1;++j){
                pos[current][0] = i; pos[current][1] = j;
                current++;
            }
        }
        return pos;
    }

    /**
     *
     * @param tipusCela
     * @param tipusAdj
     * @param files
     * @param columnes
     */

    public TaulellQuadrat(TCellShape tipusCela, TAdjacency tipusAdj, Integer files, Integer columnes) {
        super(tipusCela, tipusAdj, files, columnes);
    }
}

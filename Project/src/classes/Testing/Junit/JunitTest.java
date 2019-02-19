package classes.Testing.Junit;

import classes.Hidato;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static lib.IO.readFileHidatos;

/**
 * @author Ricard Abril
 */

public class JunitTest {

    /**
     *
     * @param args
     */

    public static void main(String args[]) {
        JunitTest test = new JunitTest();
        test.hidatoSolveTest();
    }

    /**
     *
     */
    @Test
    public void hidatoSolveTest() {
        try {
            ArrayList<Hidato> hidatoss = readFileHidatos("Hidatos_Junit.txt");
            ArrayList<Hidato> hidatosSolved = readFileHidatos("HidatosSolved_Junit.txt");
            for(int i=0;i<hidatoss.size();i++) {
                if (hidatoss.get(i).validate()) {
                    hidatoss.get(i).solve();
                }
            }
            for(int j=0;j<hidatoss.size();j++){
                Assert.assertEquals(hidatosSolved.get(j).toString(),hidatoss.get(j).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

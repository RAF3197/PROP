package classes;

import classes.cella.Cella;
import classes.cella.CellaAdapter;
import classes.enums.*;
import classes.taulell.*;


import java.io.Serializable;

import java.util.*;

/**
 * @author Bernat Felip, Marc Ferreiro
 *
 */

public class Hidato<T extends Taulell> implements Serializable {

    private int id;
    private Taulell taulell;
    private Cella dades[][];
    private int[] given, start;
    private TDifficulty dificultat;


    /**
     *
     * @param taulell
     */

    public Hidato(T taulell) {
        this.taulell = taulell;
        this.id = -1;
    }

    /**
     * @param tipusCela
     * @param tipusAdj
     * @param files
     * @param columnes
     */

    public Hidato(TCellShape tipusCela, TAdjacency tipusAdj, Integer files, Integer columnes) throws InstantiationException, IllegalAccessException {
        this.initParams(tipusCela, tipusAdj, files, columnes);
        this.initDades();
    }

    /**
     *
     * @param tipusCela
     * @param tipusAdj
     * @param files
     * @param columnes
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public void initParams(TCellShape tipusCela, TAdjacency tipusAdj, Integer files, Integer columnes) throws InstantiationException, IllegalAccessException {
        this.taulell.setTipusCela(tipusCela);
        this.taulell.setTipusAdjacencia(tipusAdj);
        this.taulell.setNombreFiles(files);
        this.taulell.setNombreColumnes(columnes);
        this.initDades();
        this.id = -1;
    }

    /**
     *
     * @return Returns the next number that should be written down
     */

    public int getCurrent(){
        for(int i = 0; i < this.given.length-1; ++i){
            if(given[i]+1 != given[i+1]){
                return given[i]+1;
            }
        }
        return 0;
    }

    /**
     *
     * @param x
     * @param y
     * @return returns true if the cell was successfully put in the hidato, else false
     */

    public boolean putCell(Integer x, Integer y){
        if(x < dades.length && y < dades[0].length && y>= 0 && x>=0 && dades[x][y].getContentType() != TCellContent.HOLE && dades[x][y].getContent() == 0) {
            int auxVal = this.getCurrent();
            if (auxVal != 0) {
                int[][] adj = dades[x][y].getAdjacencies(x, y);
                for (int i = 0; i < adj.length; ++i) {
                    if (x + adj[i][0] < dades.length && y + adj[i][1] < dades[0].length && y + adj[i][1] >= 0 && x + adj[i][0] >= 0) {

                        Cella auxCell = dades[x+adj[i][0]][y+adj[i][1]];
                        if(auxCell.getContentType() == TCellContent.NUMBER){
                            if(auxCell.getContent() == auxVal-1){
                                boolean found = false;
                                for(int k = 0; k  < this.given.length && !found; ++k){
                                    if(this.given[k] == auxVal+1) found = true;
                                }
                                if(found){
                                    found = false;
                                    for(int l = 0; l < adj.length; ++l){
                                        if (x + adj[l][0] < dades.length && y + adj[l][1] < dades[0].length && y + adj[l][1] >= 0 && x + adj[l][0] >= 0) {
                                            Cella auxCell2 = dades[x+adj[l][0]][y+adj[l][1]];
                                            if(auxCell2.getContentType() == TCellContent.NUMBER){
                                                if(auxCell2.getContent() == auxVal+1) found = true;
                                            }
                                        }
                                    }
                                    if(!found) return false;
                                }
                                this.dades[x][y].setContent(auxVal);
                                this.given  = Arrays.copyOf(this.given, this.given.length + 1);
                                this.given[this.given.length-1] = auxVal;
                                Arrays.sort(this.given);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param x
     * @param y
     * @return return true if cell was removed from hidato, else false
     */

    public boolean popCell(Integer x, Integer y){
            int aux = this.dades[x][y].getContent();
            for(int i = 0; i < this.given.length; ++i){
                if(this.given[i] == aux){
                    this.given[i] = this.given[this.given.length-1];
                    this.given[this.given.length-1] = aux;
                    this.given  = Arrays.copyOf(this.given, this.given.length - 1);
                    Arrays.sort(this.given);
                    this.dades[x][y].setContent(0);
                    return true;
                }
            }


        return false;
    }


    /**
     *
     */

    public void prepareParams() {
        int[][] pos = this.taulell.getAllPositions();
        int holes = 0;
        ArrayList<Integer> positions = new ArrayList<Integer>();
        this.start = new int[2];

        for (int i = 0 ; i < pos.length ; i++) {
            Cella c = this.dades[pos[i][0]][pos[i][1]];
            if (c.getContentType() == TCellContent.NUMBER && c.getContent() != 0) {
                if (c.getContent() == 1){
                    this.start[0] = pos[i][0];
                    this.start[1] = pos[i][1];
                }
                positions.add(c.getContent());
            } else if (c.getContentType() == TCellContent.HOLE && c.getContent() == -1) {
                holes++;
            }
        }
        given = new int[positions.size()];
        this.taulell.setNumCaselles(pos.length);
        this.taulell.setNumHoles(holes);
        this.taulell.setNumGiven(positions.size());
        Collections.sort(positions);
        for (int i = 0 ; i < positions.size() ; i++) {
            given[i] = positions.get(i);
        }
    }

    /**
     *
     * @return
     */

    public Taulell getTaulell() {
        return this.taulell;
    }

    /**
     * @param dificultat
     * @return returns true if the hidato was generated
     */

    public boolean generateHidato(TDifficulty dificultat) throws InstantiationException, IllegalAccessException {
        Double aux;
        // Randomize number of cells.
        int[] rangeCells = new int[2];
        if (dificultat == TDifficulty.EASY) {
            rangeCells[0] = 16;
            rangeCells[1] = 25;
        } else if (dificultat == TDifficulty.MEDIUM) {
            rangeCells[0] = 25;
            rangeCells[1] = 32;
        } else {
            rangeCells[0] = 32;
            rangeCells[1] = 45;
        }
        this.setDificultat(dificultat);
        this.taulell.setNumCaselles(this.generateBetween(rangeCells[0], rangeCells[1]));

        // Randomize number of given numbers.
        Double numberOfCellsGiven;
        if (dificultat == TDifficulty.EASY) {
            numberOfCellsGiven = 0.7;
        } else if (dificultat == TDifficulty.MEDIUM) {
            numberOfCellsGiven = 0.5;
        } else {
            numberOfCellsGiven = 0.15;
        }

        Double doubleNumGiven = this.taulell.getNumCaselles() * numberOfCellsGiven;

        this.taulell.setNumGiven(doubleNumGiven.intValue());

        this.taulell.generateTaulell();
        System.out.println("\n---------------------------");
        System.out.println("---- Generating hidato ----");
        System.out.println("---------------------------");
        System.out.println("Dificultat: " + dificultat);
        System.out.println("Forma taulell: " + this.taulell.getShape().name());
        System.out.println("---------------------------");
        System.out.println("Files: " + this.taulell.getNombreFiles()
                + " Columnes: " + this.taulell.getNombreColumnes()
                + " Holes: " + this.taulell.getNumHoles()
                + " Valors inicials: " + this.taulell.getNumGiven()
                + " Celles totals: " + this.taulell.getNumCaselles());
        System.out.println("---------------------------");

        this.initDades();
        return this.generateDades(this.taulell.getNumCaselles(), this.taulell.getNumHoles(), this.taulell.getNumGiven(), this.taulell.getShape());
    }

    /**
     * @param first
     * @param second
     * @return returns a random integer between first and second
     */

    private int generateBetween(int first, int second) {
        Random r = new Random();
        int Low = first;
        int High = second;
        return r.nextInt(High - Low) + Low;
    }

    /**
     *
     * @param i
     * @param j
     * @param visited
     */

    private void dfs(int i, int j, boolean[][] visited){
        if (i < 0 || j < 0) {
            return;
        }

        if (i >= this.dades.length || j >= this.dades[0].length) {
            return;
        }
        if(visited[i][j]){
            return;
        }

        visited[i][j] = true;
        int[][] adj = this.dades[i][j].getAdjacencies(i, j);
        for (int x = 0; x < adj.length ; x++) {
                dfs(i+adj[x][0], j+adj[x][1], visited);
        }


    }

    /**
     *
     * @param i
     * @param j
     * @param visited
     * @param dades
     */

    private void dfs(int i, int j, boolean[][] visited, Cella dades[][]){
        if (i < 0 || j < 0) {
            return;
        }

        if (i >= dades.length || j >= dades[0].length) {
            return;
        }
        if(visited[i][j]){
            return;
        }

        visited[i][j] = true;
        int[][] adj = dades[i][j].getAdjacencies(i, j);
        for (int x = 0; x < adj.length ; x++) {
            dfs(i+adj[x][0], j+adj[x][1], visited, dades);
        }


    }

    /**
     *
     * @return returns true if the hidato is not connected
     */

    private boolean connected(){
        boolean[][] visited = new boolean[this.dades.length][this.dades[0].length];
        boolean found = false;
        int x_start = 0;
        int y_start = 0;
        for(int i = 0; i < visited.length; ++i){
            for(int j = 0; j < visited[i].length;++j) {
                if(dades[i][j].getContentType() != TCellContent.NUMBER || dades[i][j].getContent() != 0){
                    visited[i][j] = true;
                }
                else{
                    visited[i][j] = false;
                    if(!found){
                        x_start = i;
                        y_start = j;
                        found = true;
                    }
                }

            }
        }
        if(!found) return true;
        else{
            dfs(x_start, y_start, visited);
        }
        for(int i = 0; i < visited.length; ++i){
            for(int j = 0; j < visited[i].length; ++j){
                if(!visited[i][j]) return false;
            }
        }
        return true;

    }

    /**
     *
     * @param dades
     * @return true if the hidato is connected
     */

    private boolean connected(Cella dades [][]){
        boolean[][] visited = new boolean[dades.length][dades[0].length];
        boolean found = false;
        int x_start = 0;
        int y_start = 0;
        for(int i = 0; i < visited.length; ++i){
            for(int j = 0; j < visited[i].length;++j) {
                if(dades[i][j].getContentType() != TCellContent.NUMBER){
                    visited[i][j] = true;
                }
                else{
                    visited[i][j] = false;
                    if(!found){
                        x_start = i;
                        y_start = j;
                        found = true;
                    }
                }
            }
        }
        if(!found) return true;
        else{
            dfs(x_start, y_start, visited, dades);
        }
        for(int i = 0; i < visited.length; ++i){
            for(int j = 0; j < visited[i].length; ++j){
                if(!visited[i][j]) return false;
            }
        }
        return true;

    }

    /**
     * @param n
     * @param r
     * @param c
     * @param numCaselles
     * @return returns true if the hidato was generated
     */

    private boolean generatePath(int n, int r, int c, int numCaselles){

        if(n == numCaselles+1){
            return true;
        }

        if (r < 0 || c < 0) {
            return false;
        }

        if (r >= dades.length || c >= dades[0].length) {
            return false;
        }

        if (dades[r][c].getContentType() == TCellContent.HOLE) {
            return false;
        }
        if(dades[r][c].getContent()!= 0) {
            return false;
        }

        if(!connected()){
           // System.out.println("NOT CONNECTED; GO BACK");
            return false;
        }
        int back = dades[r][c].getContent();
        dades[r][c].setContent(n);


        //this.print();


        int[][] adj = dades[r][c].getAdjacencies(r, c);

        //Collections.shuffle(Arrays.asList(adj));
        for (int i = 0; i < adj.length ; i++) {
            if (generatePath(n + 1,r + adj[i][0],c + adj[i][1], numCaselles)) {
                return true;
            }
        }

        dades[r][c].setContent(back);
        return false;

    }

    /**
     * @param numCaselles
     * @param numHoles
     * @param numGive
     * @param shape
     * @return returns true if the hidato data is generated
     */

    private boolean generateDades(Integer numCaselles, Integer numHoles, Integer numGive, TTopology shape) throws InstantiationException, IllegalAccessException {
        numCaselles = numCaselles - numHoles;
        int[][] pos = this.taulell.getAllPositions();

        for (int i = 0 ; i < pos.length; i++) {
            this.dades[pos[i][0]][pos[i][1]].setContentType(TCellContent.NUMBER);
            this.dades[pos[i][0]][pos[i][1]].setContent(0);
        }

        Cella[][] auxDades = Hidato.cloneDades(this.dades);
        int loop = 0;
        boolean solved = false;
        do {
            this.dades = Hidato.cloneDades(auxDades);
            if (loop > 0) this.emptyDades();
            if (loop > 10000) {
                System.out.println("Couldn't generate");
                return false;
            }
            System.out.println(loop);
            int[] initial = new int[2];
            int[] last = new int[2];
            this.given = new int[2];

            do {
                initial[0] = this.generateBetween(0, this.taulell.getNombreFiles());
                initial[1] = this.generateBetween(0, this.taulell.getNombreColumnes());
            } while(this.dades[initial[0]][initial[1]].getContentType() != TCellContent.NUMBER);

            //this.dades[initial[0]][initial[1]].setContent(1);

            /*do {
                last[0] = this.generateBetween(0, this.getNombreFiles());
                last[1] = this.generateBetween(0, this.getNombreColumnes());
            } while(this.dades[last[0]][last[1]].getContentType() != TCellContent.NUMBER || (last[0] == initial[0] && last[1] == initial[1]));
            this.dades[last[0]][last[1]].setContent(numCaselles);
            */
            int holes = 0;
            //Podem mirar
            while (holes < numHoles) {//millora molt el numro de iteracions si es posen ara nomes com la meitat dels holes i la resta després
                int[] holePlace = new int[2];
                do {
                    holePlace[0] = this.generateBetween(0, this.taulell.getNombreFiles());
                    holePlace[1] = this.generateBetween(0, this.taulell.getNombreColumnes());
                } while(this.dades[holePlace[0]][holePlace[1]].getContentType() != TCellContent.NUMBER || (this.dades[holePlace[0]][holePlace[1]].getContentType() == TCellContent.NUMBER && this.dades[holePlace[0]][holePlace[1]].getContent() != 0));
                this.dades[holePlace[0]][holePlace[1]].setContentType(TCellContent.HOLE);
                this.dades[holePlace[0]][holePlace[1]].setContent(-1);
                holes++;
                //this.print();
            }
            //solved = this.generatePath(1,initial[0], initial[1], numCaselles);
            solved = this.generatePath(1,initial[0], initial[1], numCaselles);
            this.start = initial;
            this.given[0] = 1;
            this.given[1] = numCaselles;
            loop++;
        } while (!solved);
        for(int i = 1; i < this.taulell.getNombreFiles()+1;++i){
            for(int j = 1; j < this.taulell.getNombreColumnes()+1; ++j){
                if(dades[i][j].getContentType() == TCellContent.NUMBER && dades[i][j].getContent() == 0){
                    dades[i][j].setContentType(TCellContent.HOLE);
                    dades[i][j].setContent(-1);
                }
            }
        }

        List<Integer> elements = new ArrayList<Integer>(numCaselles);

        for(int index = 1 ; index <= numCaselles; index++) {
            elements.add(index);
        }
        // Remove numbers keeping just the given numbers.
        int numOfNumbers = numCaselles - numGive;
        Random random = new Random();
        for (int i = 0; i < numOfNumbers; i++) {
            int index = random.nextInt(elements.size());
            if (elements.get(index) != 1 && elements.get(index) != numCaselles) {
                this.emptyCella(elements.get(index));
                elements.remove(index);
            } else {
                i--;
            }
        }
        this.given = new int[elements.size()];
        for (int i = 0; i < this.given.length; i++)
            this.given[i] = elements.get(i);

        return true;
    }

    /**
     * @param value
     */

    private void emptyCella(Integer value) {
        for (int row = 0 ; row < this.taulell.getNombreFiles()+2 ; row++) {
            for (int col = 0; col < this.taulell.getNombreColumnes()+2; col++) {
                if (this.dades[row][col].getContent() == value) {
                    this.dades[row][col].setContent(0);
                }
            }
        }
    }

    /**
     *
     */

    private void emptyDades() {
        for (int row = 0 ; row < this.taulell.getNombreFiles()+2 ; row++) {
            for (int col = 0; col < this.taulell.getNombreColumnes()+2; col++) {
                if (this.dades[row][col].getContentType() == TCellContent.NUMBER) {
                    this.dades[row][col].setContent(0);
                }
            }
        }
    }

    /**
     *
     */

    private void initDades() throws IllegalAccessException, InstantiationException {
        this.dades = new Cella[this.taulell.getNombreFiles()+2][this.taulell.getNombreColumnes()+2];
        for (int row = 0 ; row < this.taulell.getNombreFiles()+2 ; row++) {
            for (int col = 0; col < this.taulell.getNombreColumnes()+2; col++) {
                this.dades[row][col] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.HOLE, -2);
            }
        }

        int[][] pos = this.taulell.getAllPositions();

        for (int i = 0 ; i < pos.length; i++) {
            this.dades[pos[i][0]][pos[i][1]].setContentType(TCellContent.NUMBER);
            this.dades[pos[i][0]][pos[i][1]].setContent(0);
        }
    }

    /**
     * @return returns hidato data
     */

    public Cella[][] getDades() {
        return dades;
    }

    /**
     * @param data
     */

    public void setDades(String[] data) throws IllegalAccessException, InstantiationException {
        String[][] puzzle = new String[data.length][];
        for (int i = 0; i < data.length; i++)
            puzzle[i] = data[i].split(",");

        int nCols = puzzle[0].length;
        int nRows = puzzle.length;
        this.taulell.setNombreColumnes(nCols);
        this.taulell.setNombreFiles(nRows);
        int numCaselles=0;
        int numGiven = 0;
        int numHoles = 0;

        List<Integer> list = new ArrayList<>(nRows * nCols);

        this.dades = new Cella[nRows + 2][nCols + 2];
        for (Cella[] row : this.dades)
            for (int c = 0; c < nCols + 2; c++)
                row[c] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.HOLE);

        for (int r = 0; r < nRows; r++) {
            String[] row = puzzle[r];
            for (int c = 0; c < nCols; c++) {
                String cell = row[c];
                switch (cell) {
                    case "?":
                        this.dades[r + 1][c + 1] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.NUMBER);
                        ++numCaselles;
                        break;
                    case "#":
                        this.dades[r + 1][c + 1] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.HOLE, -2);
                        ++numHoles;
                        break;
                    case "*":
                        this.dades[r + 1][c + 1] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.HOLE, -1);
                        ++numHoles;
                        ++numCaselles;
                        break;
                    default:
                        int val = Integer.parseInt(cell);
                        this.dades[r + 1][c + 1] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.NUMBER, val);
                        ++numCaselles;
                        ++numGiven;
                        list.add(val);
                        if (val == 1)
                            this.start = new int[]{r + 1, c + 1};
                }
            }
        }
        this.taulell.setNumGiven(numGiven);
        this.taulell.setNumHoles(numHoles);
        this.taulell.setNumCaselles(numCaselles);
        Collections.sort(list);
        this.given = new int[list.size()];
        for (int i = 0; i < this.given.length; i++)
            this.given[i] = list.get(i);
    }

    /**
     * @return returns hidato difficulty
     */

    public TDifficulty getDificultat() {
        return dificultat;
    }

    /**
     * @param dificultat
     */

    public void setDificultat(TDifficulty dificultat) {
        this.dificultat = dificultat;
    }

    public void undoSolve() {
        List<Integer> givenAux = new ArrayList<Integer>();
        for (int i : this.given) { givenAux.add(i); }



        for (int i = 0 ; i < dades.length ; i++) {
            for (int y = 0 ; y < dades[0].length ; y++) {
                if (dades[i][y].getContentType() != TCellContent.HOLE && !givenAux.contains(dades[i][y].getContent())) {
                    dades[i][y].setContent(0);
                }
            }
        }
    }

    /**
     * @param r
     * @param c
     * @param n
     * @param next
     * @param dades
     * @return Returns true if hidato is solved
     */
    private boolean solve(int r, int c, int n, int next, Cella dades [][]) throws IllegalAccessException, InstantiationException {

        if (r<0 || c<0) {
            return false;
        }

        if (r >= dades.length || c >= dades[0].length) {
            return false;
        }

        if (n > given[given.length - 1]) {
            return true;
        }

        if (dades[r][c].getContentType() == TCellContent.HOLE) {
            return false;
        }

        if (dades[r][c].getContent() != 0 && dades[r][c].getContent() != n) {
            return false;
        }

        if (dades[r][c].getContent() == 0 && given[next] == n) {
            return false;
        }
        if(!connected(dades)){
            return false;
        }

        Cella back = dades[r][c];
        if (back.getContent() == n)
            next++;
        dades[r][c] = CellaAdapter.createInstance(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), TCellContent.NUMBER, n);

        int[][] adj = dades[r][c].getAdjacencies(r, c);

        for (int i = 0; i < adj.length ; i++) {
            if (solve(r + adj[i][0], c + adj[i][1], n + 1, next, dades)) {
                return true;
            }
        }

        dades[r][c] = back;
        return false;
    }



    /**
     * @return Return true if hidato is solved
     */



    public boolean solve() throws InstantiationException, IllegalAccessException {
        System.out.println("Taulell: " + this.taulell.getNombreFiles() + " / " + this.taulell.getNombreColumnes());
        return this.solve(this.start[0], this.start[1], 1, 0,this.dades);
    }

    /**
     *
     * @return Return true if solution is correct
     */

    public boolean check() {
        for(int i = 0; i < dades.length; ++i){
            for(int j = 0; j < dades[i].length; ++j){
                if(this.dades[i][j].getContentType() == TCellContent.NUMBER && this.dades[i][j].getContent() == 0) return false;
            }
        }
        int n = 0;
        int x = this.start[0];
        int y = this.start[1];
        int auxX, auxY;
        boolean found;
        while(n < this.taulell.getNumCaselles()-this.getTaulell().getNumHoles()-1){
            int[][] adj = dades[x][y].getAdjacencies(x, y);
            found = false;
            for (int i = 0; i < adj.length && !found ; i++) {
                auxX = x + adj[i][0];
                auxY = y + adj[i][1];
                if (auxX>=0 && auxY>=0 && auxX<dades.length && auxY<dades[0].length) {
                    if (dades[auxX][auxY].getContentType() == TCellContent.NUMBER) {
                        if (dades[auxX][auxY].getContent() == dades[x][y].getContent() + 1) {
                            found = true;
                            ++n;
                            x = auxX;
                            y = auxY;
                        }
                    }
                }
            }
            if(!found) return false;
        }
        return true;
    }

    /**
     *
     */

    public void print() {
        for (Cella[] row : this.dades) {
            for (Cella c : row) {
                if (c.getContentType() == TCellContent.HOLE)
                    System.out.print(" . ");
                else
                    System.out.printf(c.getContent() > 0 ? "%2d " : "__ ", c.getContent());
            }
            System.out.println();
        }
    }

    /**
     * @param dades
     * @return Returns a copy of hidato data
     */


    private static Cella[][] cloneDades(Cella[][] dades) throws IllegalAccessException, InstantiationException {
        Cella [][] auxdades = new Cella [dades.length][dades[0].length];
        for(int i=0;i<dades.length;i++){
            for(int j=0;j<dades[i].length;j++){
                auxdades[i][j] = CellaAdapter.duplicateInstance(dades[i][j]);
            }
        }
        return auxdades;
    }

    /**
     * @return Return true if hidato is valid
     */


    public boolean validate() throws InstantiationException, IllegalAccessException {
        Cella [][] auxdades = Hidato.cloneDades(this.dades);
        int celles = this.taulell.getNumCaselles() - this.taulell.getNumHoles();
        if (this.given.length < 2) {
            return false;
        } else {
            if (this.given[0] != 1 || this.given[this.given.length-1] != celles) {
                return false;
            }
        }
        return this.solve(this.start[0],this.start[1],1,0,auxdades);
    }

    /**
     * @return returns hidato in string visualization
     */

    @Override
    public String toString() {
        String res = "";
        res += "Hidato:\n";
        res += "-----------------\n";
        res += "Dificultat: " + this.getDificultat() + "\n";

        for (int row = 0 ; row < this.taulell.getNombreFiles() ; row++) {
            res += "\n";
            for (int col = 0 ; col < this.taulell.getNombreColumnes() ; col++) {
                res += this.dades[row][col].toString() + " ";
            }
        }
        return res;
    }

    /**
     *
     * @return Returns this hidato's id
     */

    public int getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */

    public void setId(int id) {
        this.id = id;
    }
}

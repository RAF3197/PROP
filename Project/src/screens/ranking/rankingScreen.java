package screens.ranking;

import javafx.beans.property.SimpleStringProperty;


public class rankingScreen {

    private final SimpleStringProperty num;
    private final SimpleStringProperty topo;
    private final SimpleStringProperty rows;
    private final SimpleStringProperty col;
    private final SimpleStringProperty dif;



    public rankingScreen(String num, String topo, String rows, String col, String dif) {
        this.num = new SimpleStringProperty(num);
        this.topo = new SimpleStringProperty(topo);
        this.rows = new SimpleStringProperty(rows);
        this.col = new SimpleStringProperty(col);
        this.dif = new SimpleStringProperty(dif);
    }



    public String getNum() {
        return num.get();
    }



    public SimpleStringProperty numProperty() {
        return num;
    }


    public String getTopo() {
        return topo.get();
    }


    public SimpleStringProperty topoProperty() {
        return topo;
    }



    public String getRows() {
        return rows.get();
    }



    public SimpleStringProperty rowsProperty() {
        return rows;
    }



    public String getCol() {
        return col.get();
    }



    public SimpleStringProperty colProperty() {
        return col;
    }



    public String getDif() {
        return dif.get();
    }


    public SimpleStringProperty difProperty() {
        return dif;
    }


    public void setNum(String num) {
        this.num.set(num);
    }


    public void setTopo(String topo) {
        this.topo.set(topo);
    }



    public void setRows(String rows) {
        this.rows.set(rows);
    }



    public void setCol(String col) {
        this.col.set(col);
    }



    public void setDif(String dif) {
        this.dif.set(dif);
    }
}

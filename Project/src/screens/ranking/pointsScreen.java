package screens.ranking;

import classes.Ranking;
import classes.RankingItem;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lib.Manager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class pointsScreen implements Initializable {
    @FXML TableView<scoreTable> ranking = new TableView<>();
    @FXML TableColumn usernames;
    @FXML TableColumn score;
    static int IdHidato;
    Ranking rank;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rank = Manager.getRanking(IdHidato);
            usernames.setCellValueFactory(new PropertyValueFactory<scoreTable,String>("username"));
            usernames.setStyle("-fx-alignment: center");
            score.setCellValueFactory(new PropertyValueFactory<scoreTable,String>("score"));
            score.setStyle("-fx-alignment: center");
            final ObservableList<scoreTable> items = FXCollections.observableArrayList();
            scoreTable a;
            for (int i=0;i<rank.getRanking().size();i++) {
                a = new scoreTable(rank.getRanking().get(i).getUsername(),String.valueOf(rank.getRanking().get(i).getPuntuacio()));
                items.add(a);
            }
            ranking.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void main() {


    }
}

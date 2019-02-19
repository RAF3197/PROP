package screens.game.create.cells;

import classes.Hidato;
import classes.Partida;
import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.enums.TDifficulty;
import classes.taulell.Taulell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lib.Manager;
import lib.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class CellsController implements Initializable {
    @FXML
    private Polygon triangle, hexagon;
    @FXML
    private Rectangle quadrat;
    @FXML
    private RadioButton radioCostats, radioBoth;

    private Taulell taulell;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.taulell = Manager.getPartida().getHidato().getTaulell();

        this.triangle.getStyleClass().add("game_creation_polygon_selected");

        this.taulell.setTipusCela(TCellShape.TRIANGLE);
        this.taulell.setTipusAdjacencia(TAdjacency.SIDE);

        this.radioCostats.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    taulell.setTipusAdjacencia(TAdjacency.SIDE);
                }
            }
        });
        this.radioBoth.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    taulell.setTipusAdjacencia(TAdjacency.BOTH);
                }
            }
        });
    }

    @FXML
    private void handleTriangleClick(MouseEvent event) {
        this.triangle.getStyleClass().add("game_creation_polygon_selected");
        this.quadrat.getStyleClass().remove("game_creation_polygon_selected");
        this.hexagon.getStyleClass().remove("game_creation_polygon_selected");

        this.taulell.setTipusCela(TCellShape.TRIANGLE);
    }
    @FXML
    private void handleQuadratClick(MouseEvent event) {
        this.triangle.getStyleClass().remove("game_creation_polygon_selected");
        this.quadrat.getStyleClass().add("game_creation_polygon_selected");
        this.hexagon.getStyleClass().remove("game_creation_polygon_selected");

        this.taulell.setTipusCela(TCellShape.SQUARE);
    }
    @FXML
    private void handleHexagonClick(MouseEvent event) {
        this.triangle.getStyleClass().remove("game_creation_polygon_selected");
        this.quadrat.getStyleClass().remove("game_creation_polygon_selected");
        this.hexagon.getStyleClass().add("game_creation_polygon_selected");

        this.taulell.setTipusCela(TCellShape.HEXAGON);
    }
    @FXML
    private void handleConstructButton(ActionEvent event) {
        this.taulell.generateTaulell();

        Hidato h = new Hidato(this.taulell);
        try {
            h.initParams(this.taulell.getTipusCela(), this.taulell.getTipusAdjacencia(), this.taulell.getNombreFiles(), this.taulell.getNombreColumnes());
            if(this.taulell.getNumCaselles() < 22)  h.setDificultat(TDifficulty.EASY);
            else if (this.taulell.getNumCaselles() < 30) h.setDificultat(TDifficulty.MEDIUM);
            else h.setDificultat(TDifficulty.HARD);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Manager.registerGame(new Partida(h, Manager.getUser()));

        new Utils().openScreen("screens/game/create/create.fxml", "Create hidato", null, null, null, (Stage)this.triangle.getScene().getWindow(), false);
    }
}

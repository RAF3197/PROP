package screens.game.create.form;

import classes.Hidato;
import classes.Partida;
import classes.enums.TDifficulty;
import classes.taulell.Taulell;
import classes.taulell.TaulellQuadrat;
import classes.taulell.TaulellRectangular;
import classes.taulell.TaulellTriangular;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lib.Manager;
import lib.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {
    @FXML
    private Polygon triangle;
    @FXML
    private Rectangle quadrat;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Slider fileres, columnes;
    @FXML
    private Label fileresLabel, columnesLabel;

    private Taulell taulell = null;

    @FXML
    private void handleTriangleClick(Event event) {
        this.triangle.getStyleClass().add("game_creation_polygon_selected");
        this.quadrat.getStyleClass().remove("game_creation_polygon_selected");
        this.rectangle.getStyleClass().remove("game_creation_polygon_selected");

        this.taulell = new TaulellTriangular();
    }
    @FXML
    private void handleQuadratClick(Event event) {
        this.triangle.getStyleClass().remove("game_creation_polygon_selected");
        this.quadrat.getStyleClass().add("game_creation_polygon_selected");
        this.rectangle.getStyleClass().remove("game_creation_polygon_selected");

        this.taulell = new TaulellQuadrat();
    }
    @FXML
    private void handleRectangleClick(Event event) {
        this.triangle.getStyleClass().remove("game_creation_polygon_selected");
        this.quadrat.getStyleClass().remove("game_creation_polygon_selected");
        this.rectangle.getStyleClass().add("game_creation_polygon_selected");

        this.taulell = new TaulellRectangular();
    }

    @FXML
    private void handleConstructButton(ActionEvent event) {
        int numCaselles = (int)(this.fileres.getValue()*this.columnes.getValue());
        this.taulell.setNumCaselles(numCaselles);
        Hidato h = new Hidato(this.taulell);
        if(this.taulell.getNumCaselles() < 22)  h.setDificultat(TDifficulty.EASY);
        else if (this.taulell.getNumCaselles() < 30) h.setDificultat(TDifficulty.MEDIUM);
        else h.setDificultat(TDifficulty.HARD);
        Partida p = new Partida(h, Manager.getUser());
        Manager.registerGame(p);
        new Utils().openScreen("screens/game/create/cells/cells.fxml", "Create hidato", null, null, null, (Stage)this.triangle.getScene().getWindow(), false);
        System.out.println("Opening hidato creation.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileres.setValue(fileres.getMin());
        fileresLabel.setText(Integer.toString((int)fileres.getMin()));
        columnes.setValue(columnes.getMin());
        columnesLabel.setText(Integer.toString((int)columnes.getMin()));

        this.triangle.getStyleClass().add("game_creation_polygon_selected");
        this.quadrat.getStyleClass().remove("game_creation_polygon_selected");
        this.rectangle.getStyleClass().remove("game_creation_polygon_selected");
        this.taulell = new TaulellTriangular();
        this.taulell.setNombreFiles((int)this.fileres.getValue());
        this.taulell.setNombreColumnes((int)this.columnes.getValue());


        this.fileres.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fileres.setValue(Math.round(fileres.getValue()));
                fileresLabel.setText(Integer.toString((int)fileres.getValue()));
                taulell.setNombreFiles((int)fileres.getValue());
            }
        });
        this.columnes.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                columnes.setValue(Math.round(columnes.getValue()));
                columnesLabel.setText(Integer.toString((int)columnes.getValue()));
                taulell.setNombreColumnes((int)columnes.getValue());
            }
        });
    }
}


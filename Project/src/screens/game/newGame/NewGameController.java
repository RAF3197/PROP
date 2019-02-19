package screens.game.newGame;

import classes.Hidato;
import classes.Jugador;
import classes.Partida;
import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.enums.TDifficulty;
import classes.enums.TTopology;
import classes.taulell.Taulell;
import classes.taulell.TaulellQuadrat;
import classes.taulell.TaulellRectangular;
import classes.taulell.TaulellTriangular;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lib.Manager;
import lib.Utils;
import screens.loader.LoaderController;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGameController implements Initializable {

    @FXML
    ProgressBar complexityBar;

    // TAULELL -> Forma cel·les
    String taulellFormaCellaSelected = "";
    @FXML
    RadioButton taulellFormaTriangular, taulellFormaQuadrada, taulellFormaRectangular;

    // TAULELL -> Adjaçencia cel·les
    String taulellAdjacenciaCellaSelected = "";
    @FXML
    RadioButton taulellAdjacenciaCostats, taulellAdjacenciaBoth;

    // HIDATO -> Forma cel·les
    String hidatoFormaCellaSelected = "";
    @FXML
    RadioButton hidatoFormaTriangular, hidatoFormaQuadrada, hidatoFormaHexagonal;

    //HIDATO -> Dificultat
    @FXML
    ComboBox<String> dificultCmb;
    @FXML
    final Stage[] retStage = {new Stage()};

    @FXML
    private void handleDificultAction(ActionEvent event) {
        this.calculateComplexity();
    }
    @FXML
    private void handleContinueButtonClick(ActionEvent event) throws InterruptedException {
        Hidato hidato;
        TTopology topo;
        Taulell taulell;
        switch (this.taulellFormaCellaSelected) {
            case "quadrada": taulell = new TaulellQuadrat();
                break;
            case "rectangular": taulell = new TaulellRectangular();
                break;
            case "triangular": taulell = new TaulellTriangular();
                break;
            default:
                new Utils().openAlert(Alert.AlertType.ERROR, "Problema de generació", "Sisuplau seleciona totes les opcions","", false);
                return;
        }
        switch (this.hidatoFormaCellaSelected) {
            case "triangular": taulell.setTipusCela(TCellShape.TRIANGLE);
                break;
            case "quadrada": taulell.setTipusCela(TCellShape.SQUARE);
                break;
            case "hexagonal": taulell.setTipusCela(TCellShape.HEXAGON);
                break;
            default:
                new Utils().openAlert(Alert.AlertType.ERROR, "Problema de generació", "Sisuplau seleciona totes les opcions","", false);
                return;
        }
        switch(this.taulellAdjacenciaCellaSelected) {
            case "costats": taulell.setTipusAdjacencia(TAdjacency.SIDE);
                break;
            case "both": taulell.setTipusAdjacencia(TAdjacency.BOTH);
                break;
            default:
                new Utils().openAlert(Alert.AlertType.ERROR, "Problema de generació", "Sisuplau seleciona totes les opcions","", false);
                return;
        }
        hidato = new Hidato(taulell);
        TDifficulty difficult;
        switch (this.dificultCmb.getValue()) {
            case "Fàcil": difficult = TDifficulty.EASY;
            break;
            case "Mitjà": difficult = TDifficulty.MEDIUM;
            break;
            case "Difícil": difficult = TDifficulty.HARD;
            break;
            default:
                new Utils().openAlert(Alert.AlertType.ERROR, "Problema de generació", "Sisuplau seleciona totes les opcions","", false);
                return;
        }

        Stage auxStage = (Stage) complexityBar.getScene().getWindow();

                retStage[0] = new Utils().openScreen("screens/loader/loader.fxml", "Carregant", 400, 600, Modality.WINDOW_MODAL, auxStage, false);



        final boolean[] aux = {false};
        Thread s = new Thread(new Runnable() {
            @Override
            public void run() {
                        //Thread.sleep(10000);
                        try {
                            //Thread.sleep(100000);
                            aux[0] = hidato.generateHidato(difficult);
                            System.out.println("Generating hidato... " + aux[0]);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }/* catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/

            }
        });
        //s.setDaemon(true);
        s.start();
        s.join(5000);
        if (s.isAlive()){
            s.interrupt();
        }
        if (aux[0]) {
            Partida p = new Partida(hidato, Manager.getUser());
            Manager.registerGame(p);
            Stage stage = (Stage) complexityBar.getScene().getWindow();
            // do what you have to do
            stage.close();
            System.out.println("GOODBYE:"+retStage[0].getTitle());
            retStage[0].close();

            new Utils().openScreen("screens/game/party/party.fxml", "Partida", 400, 600, Modality.WINDOW_MODAL, retStage[0], false);

        } else {
            Stage stage = (Stage) complexityBar.getScene().getWindow();
            // do what you have to do
            stage.close();
            retStage[0].close();
            new Utils().openAlert(Alert.AlertType.ERROR, "Problema de generació", "Les opcions selecionades fan la generació de l'hidato impossible o extremadament complexa", "Please try again with different settings", false);

        }
    }

    // TAULELL -> Forma cel·les
    @FXML
    private void handleFormaTaulellTriangular(ActionEvent event) {
        if (this.taulellFormaTriangular.isSelected()) {
            this.taulellFormaCellaSelected = "triangular";
        }
        this.calculateComplexity();
    }
    @FXML
    private void handleFormaTaulellQuadrada(ActionEvent event) {
        if (this.taulellFormaQuadrada.isSelected()) {
            this.taulellFormaCellaSelected = "quadrada";
        }
        this.calculateComplexity();
    }
    @FXML
    private void handleFormaTaulellRectangular(ActionEvent event) {
        if (this.taulellFormaRectangular.isSelected()) {
            this.taulellFormaCellaSelected = "rectangular";
        }
        this.calculateComplexity();
    }
    // TAULELL -> Adjaçencia cel·les
    @FXML
    private void handleAdjacenciaTaulellCostats(ActionEvent event) {
        if (this.taulellAdjacenciaCostats.isSelected()) {
            this.taulellAdjacenciaCellaSelected = "costats";
        }
        this.calculateComplexity();
    }
    @FXML
    private void handleAdjacenciaTaulellBoth(ActionEvent event) {
        if (this.taulellAdjacenciaBoth.isSelected()) {
            this.taulellAdjacenciaCellaSelected = "both";
        }
        this.calculateComplexity();
    }
    // HIDATO -> Forma cel·les
    @FXML
    private void handleFormaHidatoTriangular(ActionEvent event) {
        if (this.hidatoFormaTriangular.isSelected()) {
            this.hidatoFormaCellaSelected = "triangular";
        }
        this.calculateComplexity();
    }
    @FXML
    private void handleFormaHidatoQuadrada(ActionEvent event) {
        if (this.hidatoFormaQuadrada.isSelected()) {
            this.hidatoFormaCellaSelected = "quadrada";
        }
        this.calculateComplexity();
    }
    @FXML
    private void handleFormaHidatoHexagonal(ActionEvent event) {
        if (this.hidatoFormaHexagonal.isSelected()) {
            this.hidatoFormaCellaSelected = "hexagonal";
        }
        this.calculateComplexity();
    }

    private void calculateComplexity() {
        int dificultat = 0;
        String dif = this.dificultCmb.getValue();
        switch(this.taulellFormaCellaSelected) {
            case "quadrada": {
                if (dif == "Mitjà" || dif == "Difícil") {
                    if (this.hidatoFormaCellaSelected == "quadrada" && this.taulellAdjacenciaCellaSelected == "both") {
                        dificultat = 80;
                    } else {
                        dificultat = 60;
                    }
                } else {
                    dificultat = 30;
                }
                break;
            }
            case "triangular": {
                if (dif == "Fàcil") {
                    if (this.hidatoFormaCellaSelected == "quadrada" && this.taulellAdjacenciaCellaSelected == "costats") {
                        dificultat = 80;
                    } else if (this.hidatoFormaCellaSelected == "triangles" && this.taulellAdjacenciaCellaSelected == "costats") {
                        if (this.taulellAdjacenciaCellaSelected == "costats") {
                            dificultat = 80;
                        } else {
                            dificultat = 50;
                        }
                    }
                    else dificultat = 20;
                } else {
                    dificultat = 60;
                }
                break;
            }
            case "rectangular": {
                dificultat = 30;
                break;
            }
        }
        this.showComplexity(dificultat);
    }

    private void showComplexity(int percent) {
        Double progress = ((double)percent)/100;
        this.complexityBar.setProgress(progress);
        String color;
        if (percent > 80) {
            color = "rgba(255,0,0)";
        } else if (percent > 50) {
            color = "rgba(255,255,0)";
        } else {
            color = "rgba(0,255,0)";
        }
        this.complexityBar.setStyle("-fx-accent:" + color);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dificultCmb.getItems().addAll("Fàcil", "Mitjà", "Difícil");
        this.dificultCmb.setValue("Fàcil");
    }
}

package lib;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import screens.game.party.PartyController;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;


public class Utils {
    public Stage openScreen(String path, String title, Integer width, Integer height, Modality mod, Stage parent, boolean hideParent) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Stage stage = new Stage();
            stage.setTitle(title);
            if (width != null && height != null) {
                stage.setScene(new Scene(root));
            } else {
                stage.setScene(new Scene(root));
            }
            if (mod != null) {
                stage.initModality(mod);
            }
            if (parent != null) {
                stage.initOwner(parent);
            }
            stage.setResizable(false);
            //stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
            if(stage.getTitle() == "Partida") {
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        try {
                            Manager.getPartida().pauseGame();
                            Manager.afegirPartida(Manager.getUser().getId(), Manager.getPartida());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            if(stage.getTitle() == "Access to game") {
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                       event.consume();
                    }
                });
            }
            // Hide this current window (if this is what you want)
            if (hideParent) parent.hide();
            return stage;
        } catch (IOException e) {
            return null;
        }
    }



    public int openAlert(Alert.AlertType type, String title, String header, String content, boolean showButtons) {

        if (showButtons){
            ButtonType restartGame = new ButtonType("Restart Game", ButtonBar.ButtonData.OK_DONE);
            ButtonType returnMenu = new ButtonType("Return to Menu", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(type,content,restartGame, returnMenu);
            alert.setTitle(title);
            alert.setHeaderText(header);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(restartGame) == returnMenu) {
                return 0;
            }
            else{
                return 1;
            }
        }
        else {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
        return -1;
    }
}

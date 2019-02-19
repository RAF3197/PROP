package main;

import classes.Partida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lib.Conf;
import lib.Manager;
import lib.Utils;
import screens.game.party.PartyController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @FXML
    AnchorPane parentPanel;

    @FXML
    private void handleNewGameButton(ActionEvent event) {
        new Utils().openScreen("screens/game/newGame/newGame.fxml", "New game", null, null, null, (Stage)this.parentPanel.getScene().getWindow(), false);
    }
    @FXML
    private void handleContinueGameButton(ActionEvent event) throws SQLException {
        System.out.println("Try to open game");
        Partida p = Manager.getPartida(Manager.getUser().getId());
        Manager.registerGame(p);
        new Utils().openScreen("screens/game/party/party.fxml", "Partida", null, null, null, (Stage)this.parentPanel.getScene().getWindow(), false);
    }

    @FXML
    private void handleRankingButton(ActionEvent event) {
        Manager.setLoading(false);
        new Utils().openScreen("screens/ranking/ranking.fxml", "Ranking's", null, null, Modality.APPLICATION_MODAL, (Stage)this.parentPanel.getScene().getWindow(), false);
    }

    @FXML
    private void handleLoadButton(ActionEvent event){
        Manager.setLoading(true);
        new Utils().openScreen("screens/ranking/ranking.fxml", "Community", null, null, Modality.APPLICATION_MODAL, (Stage)this.parentPanel.getScene().getWindow(), false);
    }

    @FXML
    private void handleLogOutMenu(ActionEvent event) {
        Manager.logOut();
        new Utils().openScreen(
                "screens/user/user.fxml",
                "Access to game",
                null, null,
                Modality.APPLICATION_MODAL,
                Main.app,
                false
        );
    }


    @FXML
    private void handleMenuAction(ActionEvent event) {
        MenuItem actionedMenuItem = (MenuItem)event.getSource();
        new Utils().openAlert(Alert.AlertType.INFORMATION, "Developers", "We present you, with all our love...", "Juego realizado por Ricard Abril, Bernat Felip y Marc Ferreiro. Con todo nuestro amor, como futuros ingenieros.", false);
    }

    @FXML
    private void handleCreateButton(ActionEvent event) {
        Stage stage = new Utils().openScreen("screens/game/create/form/form.fxml", "Create hidato", null, null, null, (Stage)this.parentPanel.getScene().getWindow(), false);
    }

    public void handleWindowShownEvent()
    {
        System.out.printf("Main showing");

        if (!Manager.isUserLoggedIn()) {
            new Utils().openScreen(
                    "screens/user/user.fxml",
                    "Access to game",
                    null, null,
                    Modality.APPLICATION_MODAL,
                    Main.app,
                    false
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

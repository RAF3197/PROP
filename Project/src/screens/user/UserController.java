package screens.user;

import classes.Usuari;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lib.Manager;
import lib.Utils;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    AnchorPane parentAnchor;

    @FXML
    VBox parentDiv;

    @FXML
    TextField usernameText, passwordText;

    @FXML private void handleOnLogin() {
        Usuari u = Manager.login(this.usernameText.getText(), this.passwordText.getText());
        if (u == null) {
            new Utils().openAlert(Alert.AlertType.ERROR, "ERROR", "Credencials errònies", "El teu usuari o contraseña no son correctes.", false);
        } else {
            System.out.println("User: " + u.getId() + " " + u.getUsername() + " " + u.isAdmin());
            Stage stage = (Stage) this.parentAnchor.getScene().getWindow();
            stage.close();
        }
    }
    @FXML private void handleOnRegister() {
        Usuari u = Manager.register(this.usernameText.getText(), this.passwordText.getText(), false);
        if (u == null) {
            new Utils().openAlert(Alert.AlertType.ERROR, "ERROR", "No s'ha pogut crear l'usuari", "El teu usuari no s'ha pogut crear.", false);
        } else {
            System.out.println("User: " + u.getId() + " " + u.getUsername() + " " + u.isAdmin());
            Stage stage = (Stage) this.parentAnchor.getScene().getWindow();
            stage.close();
        }
    }

    @FXML private void handleOnKeyPresed(javafx.scene.input.KeyEvent e) {
        KeyCode key = e.getCode();
        if (key.equals(KeyCode.ENTER)) {
            handleOnLogin();
        }
    }

    public void handleWindowShownEvent() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

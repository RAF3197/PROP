package screens.loader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;


public class LoaderController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    ImageView img;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //img.setImage(new Image(this.getClass().getResource("/assets/background.jpg").toExternalForm()));

    }
}

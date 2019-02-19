package main;


import classes.Hidato;
import classes.enums.TAdjacency;
import classes.enums.TCellShape;
import classes.enums.TDifficulty;
import classes.enums.TTopology;
import classes.taulell.TaulellTriangular;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lib.Conf;
import lib.SQLiteJDBCDriverConnection;
import lib.Utils;

public class Main extends Application {
    public static Stage app;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.app = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle(Conf.getConst("appName"));
        final MainController controller = loader.getController();
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event) {
                controller.handleWindowShownEvent();
            }
        });
        primaryStage.setScene(new Scene(root, Integer.parseInt(Conf.getConst("width")), Integer.parseInt(Conf.getConst("height"))));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //System.out.println("Checking DataBase connection...");
        Hidato h = new Hidato(new TaulellTriangular());
        if (SQLiteJDBCDriverConnection.isDatabaseAccessible()) {
            launch(args);
        } else {
            new Utils().openAlert(Alert.AlertType.ERROR, "Error amb la base de dades", "Base de dades no accessible", "Hi ha hagut un error intentant establir la connexió amb la base de dades, torna-ho a provar o contacta amb l'administrador.", false);
        }
        //System.out.printf("Test here what you want. It will be showed before red lines.\n");
        /**
         * Test here!!
         */

        /**
         * End test here.
         */

    }
}

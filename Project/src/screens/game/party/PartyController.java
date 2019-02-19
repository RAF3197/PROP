package screens.game.party;

import classes.cella.Cella;
import classes.Hidato;
import classes.Hexagon;
import classes.Partida;
import classes.enums.TCellContent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lib.Manager;
import lib.Utils;
import screens.game.create.CreateController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PartyController implements Initializable {
    @FXML
    Group theGroup;
    @FXML
    Canvas canvas;
    @FXML
    Label nextNumber;
    @FXML
    Label timerLabel;
    @FXML
    Button resetButton;



    private static Partida partida;
    private static GraphicsContext gc;
    private int[] pointer = {1,1};

    @FXML
    private void handleKey(KeyEvent ke) throws IllegalAccessException, InstantiationException, SQLException {
        int[] pointerChanges = {0,0};
        switch(ke.getCode()) {
            case UP:
                pointerChanges[1] = -1;
                break;
            case DOWN:
                pointerChanges[1] = 1;
                break;
            case LEFT:
                pointerChanges[0] = -1;
                break;
            case RIGHT:
                pointerChanges[0] = 1;
                break;
            case ENTER:
                partida.stepForward(this.pointer[1], this.pointer[0]);
                int aux = partida.getHidato().getCurrent();
                if(aux == 0){
                    paint();
                    if(PartyController.partida.getHidato().check()){
                        PartyController.partida.finishGame();
                        if(PartyController.partida.getHidato().getId() != -1){
                            long punts = PartyController.partida.getCronometre().getTime()/1000;
                            int aux_idPlayer = PartyController.partida.getJugador().getId();
                            int aux_idHidato = PartyController.partida.getHidato().getId();
                            System.out.println("PUNTS: "+punts);
                            System.out.println(Manager.getPuntuacio(aux_idPlayer, aux_idHidato));
                            if(Manager.getPuntuacio(aux_idPlayer, aux_idHidato) > punts || Manager.getPuntuacio(aux_idPlayer,aux_idHidato) == -1){
                                System.out.println("Entrem a afegir");
                                Manager.afegirPuntuacio(aux_idPlayer, aux_idHidato, (int) punts, PartyController.partida.getJugador().getUsername());
                            }
                        }
                        int choice = new Utils().openAlert(Alert.AlertType.INFORMATION, "Felicitats!", "Ho has aconseguit!  La teva puntuació ha estat guardada al ranking", "T'ha costat: " + timerLabel.getText() , true);
                        if(choice == 0){
                            PartyController.partida.getCronometre().startCron();
                            Stage stage = (Stage) timerLabel.getScene().getWindow();
                            stage.close();

                        }
                        else{
                            PartyController.partida.reset();
                            PartyController.partida.getCronometre().startCron();
                        }
                    }
                    else{
                        PartyController.partida.pauseGame();
                        new Utils().openAlert(Alert.AlertType.INFORMATION, "Hi ha un error en algun punt!", "", "Torna enrere!", false);
                        PartyController.partida.continueGame();
                    }
                }
                nextNumber.setText(String.valueOf(aux));
                break;
            case TAB:
                partida.stepBack();
                nextNumber.setText(String.valueOf(partida.getHidato().getCurrent()));
                break;
        }
        int [] aux = {pointer[0], pointer[1]};
        if (this.pointer[0] + pointerChanges[0] <= partida.getColumnes() && this.pointer[0] + pointerChanges[0] > 0) {
            aux[0] += pointerChanges[0];
        }
        if (this.pointer[1] + pointerChanges[1] <= partida.getFiles() && this.pointer[1] + pointerChanges[1] > 0) {
            aux[1] += pointerChanges[1];
        }
        Cella c = PartyController.partida.getHidato().getDades()[aux[1]][aux[0]];
        if (c.getContentType() != TCellContent.HOLE || c.getContentType() == TCellContent.HOLE && c.getContent() != -2) {
            this.pointer[0] = aux[0];
            this.pointer[1] = aux[1];
        }
        /*if ((PartyController.partida.getHidato().getDades()[pointer[0]+pointerChanges[0]][pointer[1]+pointerChanges[1]]).getContent() != -2) {
            if (this.pointer[0] + pointerChanges[0] <= partida.getColumnes() && this.pointer[0] + pointerChanges[0] > 0) {
                pointer[0] += pointerChanges[0];
            }
            if (this.pointer[1] + pointerChanges[1] <= partida.getFiles() && this.pointer[1] + pointerChanges[1] > 0) {
                pointer[1] += pointerChanges[1];
            }
        }*/

        paint();
    }
    @FXML private void handleResoldreButton(ActionEvent event) {
        PartyController.partida.pauseGame();
        try {
            if(PartyController.partida.getHidato().validate()){

                new Utils().openAlert(Alert.AlertType.INFORMATION, "Vas bé!", "", "Segueix així!", false);

            }
            else{

                new Utils().openAlert(Alert.AlertType.INFORMATION, "Hi ha un error en algun punt!", "", "Torna enrere!", false);

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        PartyController.partida.continueGame();
    }
    @FXML private void handleResetButton(ActionEvent event) {
        PartyController.partida.reset();
        nextNumber.setText(String.valueOf(partida.getHidato().getCurrent()));
        paint();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        theGroup.setFocusTraversable(true); // doesn't have any effect
        theGroup.requestFocus(); // doesn't have any effect

        PartyController.partida = Manager.getPartida();
        if(PartyController.partida.getCronometre().getTime() == 0) PartyController.partida.startGame();
        else PartyController.partida.continueGame();

        int[][] positions = PartyController.partida.getHidato().getTaulell().getAllPositions();
        this.pointer[0] = positions[0][0];
        this.pointer[1] = positions[0][1];

        PartyController.gc = canvas.getGraphicsContext2D();
        nextNumber.setText(String.valueOf(partida.getHidato().getCurrent()));
        paint();
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            long millis = PartyController.partida.getCronometre().getTime();
                            long second = (millis / 1000) % 60;
                            long minute = (millis / (1000 * 60)) % 60;
                            long hour = (millis / (1000 * 60 * 60)) % 24;

                            String time = String.format("%02d:%02d:%02d", hour, minute, second);

                            timerLabel.setText(time);
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
        System.out.println("ENTERED!!");

    }

    private void paint() {
        gc.clearRect(0,0,600,400);
        switch (PartyController.partida.getHidato().getTaulell().getTipusCela()) {
            case TRIANGLE:
                this.drawTaulellTriangular(gc);
                break;
            case SQUARE:
                this.drawTaulellQuadrat(gc);
                break;
            case HEXAGON:
                this.drawTaulellHexagon(gc);
                break;
        }
    }

    private Paint setStrokeColor(TCellContent type) {
        switch (type) {
            case NUMBER:
                return Color.BLACK;
        }
        return Color.TRANSPARENT;
    }
    private Paint setFillColor(TCellContent type, int content) {
        switch (type) {
            case HOLE:
                if (content == -2) {
                    return Color.valueOf("#829191");
                } else {
                    return Color.BLACK;
                }
        }
        return Color.TRANSPARENT;
    }

    private double[] getTextPositionTriangle(String direction, String text, double cellaWidth, double cellaHeight, double positionX, double positionY) {
        double[] pos = {positionX + cellaWidth/2, positionY + cellaHeight/2};
        if (direction == "top") {
            pos[1] += cellaHeight/4;
        }
        if (text.length() > 1) {
            pos[0] -= cellaWidth/3.5;
        } else {
            pos[0] -= cellaWidth/6;
        }
        return pos;
    }
    private double[] getTextPositionQuadrat(String text, double cellaWidth, double cellaHeight, double positionX, double positionY) {
        double[] pos = {positionX + cellaWidth/2, positionY + cellaHeight/1.7};

        if (text.length() > 1) {
            pos[0] -= cellaWidth/5;
        } else {
            pos[0] -= cellaWidth/8;
        }

        return pos;
    }

    private void drawTaulellTriangular(GraphicsContext gc) {
        Hidato h = PartyController.partida.getHidato();
        Cella[][] dades = h.getDades();

        int rows = h.getTaulell().getNombreFiles(), cols = h.getTaulell().getNombreColumnes();

        double marginHorizontal = 160.0*2, marginVertical = 60.0*2;

        double cellaHeight = (400.0-marginVertical)/rows;
        double cellaWidth = (600.0-marginHorizontal)/cols;

        for (int fila = 1 ; fila <= rows ; fila++) {
            for (int col = 1 ; col <= cols ; col++) {
                boolean isLogged = PartyController.partida.inTimeline(fila, col);

                Cella cella = dades[fila][col];
                double initX = (col-1)*cellaWidth+marginHorizontal/2;
                double initY = (fila-1)*cellaHeight+marginVertical/2;

                gc.setLineWidth(1.0);
                gc.setStroke(this.setStrokeColor(cella.getContentType()));
                gc.setFill(this.setFillColor(cella.getContentType(), cella.getContent()));

                double[] pointsX = {initX-cellaWidth*0.5, initX+cellaWidth*0.5, initX+cellaWidth*1.5};
                double[] pointsY = new double[3];
                String direction;
                if ((fila % 2 == 0 && col % 2 == 0) || (fila % 2 != 0 && col % 2 != 0)) {
                    pointsY[0] = initY+cellaHeight;
                    pointsY[1] = initY;
                    pointsY[2] = initY+cellaHeight;
                    direction = "top";
                } else {
                    pointsY[0] = initY;
                    pointsY[1] = initY+cellaHeight;
                    pointsY[2] = initY;
                    direction = "bottom";
                }

                if (this.pointer[0] == col && this.pointer[1] == fila) {
                    if (cella.getContentType() == TCellContent.HOLE) {
                        gc.setFill(Color.ORANGE);
                    } else {
                        gc.setStroke(Color.ORANGE);
                    }
                    gc.setLineWidth(2.0);
                }


                if (cella.getContentType() == TCellContent.HOLE) {
                    gc.fillPolygon(pointsX, pointsY, 3);
                } else {
                    gc.strokePolygon(pointsX, pointsY, 3);
                }

                Polygon p = new Polygon();
                p.getPoints().addAll(pointsX[0], pointsY[0],
                        pointsX[1], pointsY[1],
                        pointsX[2], pointsY[2]);

                if (isLogged && cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    gc.setStroke(Color.WHITE);
                }
                if (cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    double[] position = this.getTextPositionTriangle(direction, cella.getContent().toString(), cellaWidth, cellaHeight, initX, initY);
                    gc.strokeText(cella.getContent().toString(), position[0], position[1]);
                }
            }
        }
    }

    private void drawTaulellQuadrat(GraphicsContext gc) {
        Hidato h = PartyController.partida.getHidato();
        Cella[][] dades = h.getDades();

        int rows = h.getTaulell().getNombreFiles(), cols = h.getTaulell().getNombreColumnes();

        double marginVertical = 60.0*2, marginHorizontal = 150.0*2;

        double cellaHeight = (400.0-marginVertical)/rows;
        double cellaWidth = cellaHeight;

        for (int fila = 1 ; fila <= rows ; fila++) {
            for (int col = 1 ; col <= cols ; col++) {
                boolean isLogged = PartyController.partida.inTimeline(fila, col);
                Cella cella = dades[fila][col];
                double initX = (col-1)*cellaWidth+marginHorizontal/2;
                double initY = (fila-1)*cellaHeight+marginVertical/2;

                gc.setLineWidth(1.0);
                gc.setStroke(this.setStrokeColor(cella.getContentType()));
                gc.setFill(this.setFillColor(cella.getContentType(), cella.getContent()));

                if (this.pointer[0] == col && this.pointer[1] == fila) {
                    if (cella.getContentType() == TCellContent.HOLE) {
                        gc.setFill(Color.ORANGE);
                    } else {
                        gc.setStroke(Color.ORANGE);
                    }
                    gc.setLineWidth(2.0);
                }


                if (cella.getContentType() == TCellContent.HOLE) {
                    gc.fillRect(initX, initY, cellaWidth, cellaHeight);
                } else {
                    gc.strokeRect(initX, initY, cellaWidth, cellaHeight);
                }

                if (isLogged && cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    gc.setStroke(Color.WHITE);
                }
                if (cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    double[] position = this.getTextPositionQuadrat(cella.getContent().toString(), cellaWidth, cellaHeight, initX, initY);
                    gc.strokeText(cella.getContent().toString(), position[0], position[1]);
                }
            }
        }
    }

    private void drawTaulellHexagon(GraphicsContext gc) {
        Hidato h = PartyController.partida.getHidato();
        Cella[][] dades = h.getDades();

        int rows = h.getTaulell().getNombreFiles(), cols = h.getTaulell().getNombreColumnes();

        double marginHorizontal = 160.0*2, marginVertical = 60.0*2;

        double cellaWidth = (600.0-marginHorizontal)/cols;
        double cellaHeight = Hexagon.getSide(cellaWidth);

        for (int fila = 1 ; fila <= rows ; fila++) {
            for (int col = 1 ; col <= cols ; col++) {
                boolean isLogged = PartyController.partida.inTimeline(fila, col);
                Cella cella = dades[fila][col];
                double initX = (col-1)*cellaWidth+marginHorizontal/2;
                double initY = (fila-1)*cellaHeight+marginVertical/2 + Hexagon.getOutOfBounds(cellaHeight)*(fila*2);

                if (fila % 2 == 0) {
                    initX += cellaWidth/2;
                }

                gc.setLineWidth(1.0);
                gc.setStroke(this.setStrokeColor(cella.getContentType()));
                gc.setFill(this.setFillColor(cella.getContentType(), cella.getContent()));

                Hexagon hexagon = new Hexagon(initX, initY, cellaWidth);

                if (this.pointer[0] == col && this.pointer[1] == fila) {
                    if (cella.getContentType() == TCellContent.HOLE) {
                        gc.setFill(Color.ORANGE);
                    } else {
                        gc.setStroke(Color.ORANGE);
                    }
                    gc.setLineWidth(2.0);
                }


                if (cella.getContentType() == TCellContent.HOLE) {
                    gc.fillPolygon(hexagon.getPointsX(), hexagon.getPointsY(), hexagon.getPoints());
                } else {
                    gc.strokePolygon(hexagon.getPointsX(), hexagon.getPointsY(), hexagon.getPoints());
                }

                if (isLogged && cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    gc.setStroke(Color.WHITE);
                }
                if (cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    double[] position = this.getTextPositionQuadrat(cella.getContent().toString(), cellaWidth, cellaHeight, initX, initY);
                    gc.strokeText(cella.getContent().toString(), position[0], position[1]);
                }
            }
        }
    }
}


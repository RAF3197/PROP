package screens.game.create;

import classes.Hexagon;
import classes.Hidato;
import classes.Partida;
import classes.cella.Cella;
import classes.cella.CellaAdapter;
import classes.enums.TCellContent;
import classes.enums.TCellShape;
import classes.taulell.Taulell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import lib.Manager;
import lib.Utils;
import screens.game.party.PartyController;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable {
    @FXML
    Group theGroup;
    @FXML
    Canvas canvas;
    @FXML
    Button resoldre;
    @FXML
    Label holesLabel, cellsLabel;
    @FXML
    Button guardar;



    private static Partida partida;
    private static GraphicsContext gc;
    private int[] pointer = {1,1};

    private int holes = 0;
    private int cells = 0;

    private boolean solved = false;

    private String creationType = "hole";

    @FXML
    private void handleKey(KeyEvent ke) {
        int[] pointerChanges = {0,0};
        System.out.println("Code: " + ke.getCode().getName());
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
                System.out.println("ENTERED CONTROLLER");
                partida.stepForward(this.pointer[1], this.pointer[0]);
                break;
            case TAB:
                partida.stepBack();
                break;
            case H:
                this.convertCella();
                break;
            case DIGIT1: case DIGIT2: case DIGIT3: case DIGIT4: case DIGIT5: case DIGIT6: case DIGIT7: case DIGIT8: case DIGIT9: case DIGIT0:
                this.addNumberToCella(ke.getCode());
                break;
            case BACK_SPACE:
                this.deleteNumber();
                break;
            default:
                //char c = ke.
                break;

        }
        int [] aux = {pointer[0], pointer[1]};
        if (this.pointer[0] + pointerChanges[0] <= partida.getColumnes() && this.pointer[0] + pointerChanges[0] > 0) {
            aux[0] += pointerChanges[0];
        }
        if (this.pointer[1] + pointerChanges[1] <= partida.getFiles() && this.pointer[1] + pointerChanges[1] > 0) {
            aux[1] += pointerChanges[1];
        }
        Cella c = CreateController.partida.getHidato().getDades()[aux[1]][aux[0]];
        if (c.getContentType() != TCellContent.HOLE || c.getContentType() == TCellContent.HOLE && c.getContent() != -2) {
            this.pointer[0] = aux[0];
            this.pointer[1] = aux[1];
        }

        paint();
    }

    private int getNumFromKeyCode(KeyCode key) {
        int val;
        switch(key) {
            case DIGIT0:
                val = 0;
                break;
            case DIGIT1:
                val = 1;
                break;
            case DIGIT2:
                val = 2;
                break;
            case DIGIT3:
                val = 3;
                break;
            case DIGIT4:
                val = 4;
                break;
            case DIGIT5:
                val = 5;
                break;
            case DIGIT6:
                val = 6;
                break;
            case DIGIT7:
                val = 7;
                break;
            case DIGIT8:
                val = 8;
                break;
            default:
                val = 9;
                break;
        }
        return val;
    }

    private void deleteNumber() {
        Cella c = CreateController.partida.getHidato().getDades()[this.pointer[1]][this.pointer[0]];

        if (c.getContentType() != TCellContent.HOLE) {
            String val = Integer.toString(c.getContent());

            if (val.length() > 1) {
                c.setContent(Integer.valueOf(val.substring(0, val.length()-1)));
            } else {
                c.setContent(0);
            }
        }
    }

    private void addNumberToCella(KeyCode key) {
        Cella c = CreateController.partida.getHidato().getDades()[this.pointer[1]][this.pointer[0]];

        if (c.getContentType() != TCellContent.HOLE) {
            int val = c.getContent();
            String valFinal = Integer.toString(val);

            if (val == 0 && key != KeyCode.DIGIT0) {
                valFinal = Integer.toString(this.getNumFromKeyCode(key));
            } else {
                if (valFinal.length() < 2) {
                    valFinal += Integer.toString(this.getNumFromKeyCode(key));
                }
            }

            c.setContent(Integer.valueOf(valFinal));
            System.out.println("Cell value: " + c.getContent());
            System.out.println("Hidato Cell value: " + CreateController.partida.getHidato().getDades()[this.pointer[1]][this.pointer[0]].getContent());
        }
    }

    private void convertCella() {
        Cella c = CreateController.partida.getHidato().getDades()[this.pointer[1]][this.pointer[0]];

        switch (c.getContentType()) {
            case HOLE:
                c.setContentType(TCellContent.NUMBER);
                c.setContent(0);
                this.holes--;
                this.cells++;
                this.holesLabel.setText(Integer.toString(this.holes));
                this.cellsLabel.setText(Integer.toString(this.cells));
                break;
            default:
                c.setContentType(TCellContent.HOLE);
                c.setContent(-1);
                this.holes++;
                this.cells--;
                this.holesLabel.setText(Integer.toString(this.holes));
                this.cellsLabel.setText(Integer.toString(this.cells));
                break;
        }

        System.out.println("Cella type: " + c.getContentType().name());
    }


    @FXML private void handleResoldreButton(ActionEvent event) {
        try {
            if (!this.solved) {
                CreateController.partida.getHidato().prepareParams();
                CreateController.partida.getHidato().solve();
                this.resoldre.setText("Desfés");
                this.solved = true;
            } else {
                CreateController.partida.getHidato().undoSolve();
                this.resoldre.setText("Resoldre");
                this.solved = false;
            }
            paint();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleValidarButton(ActionEvent event) {
        try {
            CreateController.partida.getHidato().prepareParams();
            if (CreateController.partida.getHidato().validate()) {
                new Utils().openAlert(Alert.AlertType.CONFIRMATION, "Hidato Valid", "Validacio hidato", "L'hidato es correcte!", false);
            } else {
                new Utils().openAlert(Alert.AlertType.ERROR, "Hidato invalid", "Validacio hidato", "L'hidato no es valid!", false);
            }
            paint();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleGuardarButton(ActionEvent event) {
        try{
            CreateController.partida.getHidato().prepareParams();
            if (CreateController.partida.getHidato().validate()) {
                int b = Manager.afegirHidato(CreateController.partida.getHidato());
                System.out.println(b);
                if (b!=-1) new Utils().openAlert(Alert.AlertType.CONFIRMATION, "Hidato guardat", "Hidato guardat", "L'hidato s'ha guardat correctament a la basde de dades", false);
            }
            else {
                new Utils().openAlert(Alert.AlertType.ERROR, "Hidato invalid", "Validacio hidato", "L'hidato no es valid!", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        theGroup.setFocusTraversable(true); // doesn't have any effect
        theGroup.requestFocus(); // doesn't have any effect

        CreateController.partida = Manager.getPartida();
        CreateController.partida.getHidato().prepareParams();
        int[][] positions = CreateController.partida.getHidato().getTaulell().getAllPositions();
        this.pointer[0] = positions[0][0];
        this.pointer[1] = positions[0][1];
        CreateController.gc = canvas.getGraphicsContext2D();

        this.cells = CreateController.partida.getHidato().getTaulell().getNumCaselles();
        this.cellsLabel.setText(Integer.toString(this.cells));

        //Ricard
        if (!partida.getJugador().isAdmin()) {
            guardar.setVisible(false);
        }
        else guardar.setVisible(true);


        paint();
    }

    private void paint() {
        gc.clearRect(0,0,600,400);
        switch (CreateController.partida.getHidato().getTaulell().getTipusCela()) {
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
        Hidato h = CreateController.partida.getHidato();
        Cella[][] dades = h.getDades();

        int rows = h.getTaulell().getNombreFiles(), cols = h.getTaulell().getNombreColumnes();

        double marginHorizontal = 160.0*2, marginVertical = 60.0*2;

        double cellaHeight = (400.0-marginVertical)/rows;
        double cellaWidth = (600.0-marginHorizontal)/cols;

        for (int fila = 1 ; fila <= rows ; fila++) {
            for (int col = 1 ; col <= cols ; col++) {
                boolean isLogged = CreateController.partida.inTimeline(fila, col);

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
                        gc.setFill(Color.WHITE);
                    } else {
                        gc.setStroke(Color.WHITE);
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

                gc.setLineWidth(0.7);
                if (cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    double[] position = this.getTextPositionTriangle(direction, cella.getContent().toString(), cellaWidth, cellaHeight, initX, initY);
                    gc.strokeText(cella.getContent().toString(), position[0], position[1]);
                }
            }
        }
    }

    private void drawTaulellQuadrat(GraphicsContext gc) {
        Hidato h = CreateController.partida.getHidato();
        Cella[][] dades = h.getDades();

        int rows = h.getTaulell().getNombreFiles(), cols = h.getTaulell().getNombreColumnes();

        double marginVertical = 60.0*2, marginHorizontal = 150.0*2;

        double cellaHeight = (400.0-marginVertical)/rows;
        double cellaWidth = cellaHeight;

        for (int fila = 1 ; fila <= rows ; fila++) {
            for (int col = 1 ; col <= cols ; col++) {
                boolean isLogged = CreateController.partida.inTimeline(fila, col);
                Cella cella = dades[fila][col];
                double initX = (col-1)*cellaWidth+marginHorizontal/2;
                double initY = (fila-1)*cellaHeight+marginVertical/2;

                gc.setLineWidth(1.0);
                gc.setStroke(this.setStrokeColor(cella.getContentType()));
                gc.setFill(this.setFillColor(cella.getContentType(), cella.getContent()));

                if (this.pointer[0] == col && this.pointer[1] == fila) {
                    if (cella.getContentType() == TCellContent.HOLE) {
                        gc.setFill(Color.WHITE);
                    } else {
                        gc.setStroke(Color.WHITE);
                    }
                    gc.setLineWidth(2.0);
                }


                if (cella.getContentType() == TCellContent.HOLE) {
                    gc.fillRect(initX, initY, cellaWidth, cellaHeight);
                } else {
                    gc.strokeRect(initX, initY, cellaWidth, cellaHeight);
                }

                gc.setLineWidth(0.5);
                if (cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    double[] position = this.getTextPositionQuadrat(cella.getContent().toString(), cellaWidth, cellaHeight, initX, initY);
                    gc.strokeText(cella.getContent().toString(), position[0], position[1]);
                }
            }
        }
    }

    private void drawTaulellHexagon(GraphicsContext gc) {
        Hidato h = CreateController.partida.getHidato();
        Cella[][] dades = h.getDades();

        int rows = h.getTaulell().getNombreFiles(), cols = h.getTaulell().getNombreColumnes();

        double marginHorizontal = 160.0*2, marginVertical = 60.0*2;

        double cellaWidth = (600.0-marginHorizontal)/cols;
        double cellaHeight = Hexagon.getSide(cellaWidth);

        for (int fila = 1 ; fila <= rows ; fila++) {
            for (int col = 1 ; col <= cols ; col++) {
                boolean isLogged = CreateController.partida.inTimeline(fila, col);
                System.out.println("LOGGED:   "+isLogged);
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
                        gc.setFill(Color.WHITE);
                    } else {
                        gc.setStroke(Color.WHITE);
                    }
                    gc.setLineWidth(2.0);
                }


                if (cella.getContentType() == TCellContent.HOLE) {
                    gc.fillPolygon(hexagon.getPointsX(), hexagon.getPointsY(), hexagon.getPoints());
                } else {
                    gc.strokePolygon(hexagon.getPointsX(), hexagon.getPointsY(), hexagon.getPoints());
                }

                gc.setLineWidth(0.5);
                if (cella.getContentType() == TCellContent.NUMBER && cella.getContent() != 0) {
                    double[] position = this.getTextPositionQuadrat(cella.getContent().toString(), cellaWidth, cellaHeight, initX, initY);
                    gc.strokeText(cella.getContent().toString(), position[0], position[1]);
                }
            }
        }
    }
}


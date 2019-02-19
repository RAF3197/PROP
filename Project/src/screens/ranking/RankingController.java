package screens.ranking;

import classes.Hidato;
import classes.Partida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lib.Manager;
import lib.Utils;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class RankingController implements Initializable {

    ArrayList<Hidato> hidatosHexagonal = new ArrayList<>();
    ArrayList<Hidato> hidatosTriangular = new ArrayList<>();
    ArrayList<Hidato> hidatosSquare = new ArrayList<>();

    @FXML TableView<rankingScreen> listSquare = new TableView<>();

    @FXML TableColumn listSquareNum;

    @FXML TableColumn listSquareCol;

    @FXML TableColumn listSquareRow;

    @FXML TableColumn listSquareDif;

    @FXML TableColumn listSquareTopo;

    @FXML TableView<rankingScreen> listTriang;

    @FXML TableColumn listTriangNum;

    @FXML TableColumn listTriangCol;

    @FXML TableColumn listTriangRow;

    @FXML TableColumn listTriangDif;

    @FXML TableColumn listTriangTopo;

    @FXML TableView<rankingScreen> listHex;

    @FXML TableColumn listHexNum;

    @FXML TableColumn listHexCol;

    @FXML TableColumn listHexRow;

    @FXML TableColumn listHexDif;

    @FXML TableColumn listHexTopo;

    @FXML Tab squareTab;

    @FXML Tab triangularTab;

    @FXML Tab hexagonalTab;



    @FXML private void tabSelected(Event event) throws IOException, ClassNotFoundException {


        if(squareTab.isSelected()){ //<Pair<int,int>> ->> Id,Number -- TTopology, dificulty, rows, cols

            listSquareNum.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("num"));
            listSquareNum.setStyle("-fx-alignment: center");
            listSquareCol.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("col"));
            listSquareCol.setStyle("-fx-alignment: center");
            listSquareRow.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("rows"));
            listSquareRow.setStyle("-fx-alignment: center");
            listSquareDif.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("dif"));
            listSquareDif.setStyle("-fx-alignment: center");
            listSquareTopo.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("topo"));
            listSquareTopo.setStyle("-fx-alignment: center");
            final ObservableList<rankingScreen> items = FXCollections.observableArrayList();
            hidatosSquare = Manager.getHidatosForma("SQUARE");
            rankingScreen a;
            for (int i=0;i<hidatosSquare.size();++i){
                a = new rankingScreen(String.valueOf(i),String.valueOf(hidatosSquare.get(i).getTaulell().getShape()),
                                                    String.valueOf(hidatosSquare.get(i).getTaulell().getNombreFiles()),
                                                    String.valueOf(hidatosSquare.get(i).getTaulell().getNombreColumnes()),
                                                    String.valueOf(hidatosSquare.get(i).getDificultat()));
                items.add(a);
            }
            listSquare.setItems(items);
            //squareTab.setContent(listSquare);

        }

        else if(triangularTab.isSelected()) {

            listTriangNum.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("num"));
            listTriangNum.setStyle("-fx-alignment: center");
            listTriangCol.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("col"));
            listTriangCol.setStyle("-fx-alignment: center");
            listSquareRow.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("rows"));
            listTriangRow.setStyle("-fx-alignment: center");
            listTriangDif.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("dif"));
            listTriangDif.setStyle("-fx-alignment: center");
            listTriangTopo.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("topo"));
            listTriangTopo.setStyle("-fx-alignment: center");
            ObservableList<rankingScreen> items = FXCollections.observableArrayList();
            hidatosTriangular = Manager.getHidatosForma("TRIANGLE");
            rankingScreen a;
            for (int i=0;i<hidatosTriangular.size();++i){
                a = new rankingScreen(String.valueOf(i),String.valueOf(hidatosTriangular.get(i).getTaulell().getShape()),
                        String.valueOf(hidatosTriangular.get(i).getTaulell().getNombreFiles()),
                        String.valueOf(hidatosTriangular.get(i).getTaulell().getNombreColumnes()),
                        String.valueOf(hidatosTriangular.get(i).getDificultat()));
                items.add(a);
            }
            listTriang.setItems(items);
            //triangularTab.setContent(listTriang);

        }

      else if(hexagonalTab.isSelected()) {

            listHexNum.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("num"));
            listHexNum.setStyle("-fx-alignment: center");
            listHexCol.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("col"));
            listHexCol.setStyle("-fx-alignment: center");
            listHexRow.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("rows"));
            listHexRow.setStyle("-fx-alignment: center");
            listHexDif.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("dif"));
            listHexDif.setStyle("-fx-alignment: center");
            listHexTopo.setCellValueFactory(new PropertyValueFactory<rankingScreen,String>("topo"));
            listHexTopo.setStyle("-fx-alignment: center");
            ObservableList<rankingScreen> items = FXCollections.observableArrayList();
            hidatosHexagonal = Manager.getHidatosForma("HEXAGON");
            rankingScreen a;
            for (int i=0;i<hidatosHexagonal.size();++i){
                a = new rankingScreen(String.valueOf(i),String.valueOf(hidatosHexagonal.get(i).getTaulell().getShape()),
                        String.valueOf(hidatosHexagonal.get(i).getTaulell().getNombreFiles()),
                        String.valueOf(hidatosHexagonal.get(i).getTaulell().getNombreColumnes()),
                        String.valueOf(hidatosHexagonal.get(i).getDificultat()));
                items.add(a);
            }
            listHex.setItems(items);
            //hexagonalTab.setContent(listHex);
        }

    }



    @FXML public void handleMouseClickSquare(MouseEvent arg) throws IOException, ClassNotFoundException {
        if(!hidatosSquare.isEmpty()) {
            pointsScreen.IdHidato = hidatosSquare.get(listSquare.getSelectionModel().getFocusedIndex()).getId();
            this.handleOpening();
        }
    }


    @FXML public void handleMouseClickTriang(MouseEvent arg) throws IOException, ClassNotFoundException {
        if(!hidatosTriangular.isEmpty()) {
            pointsScreen.IdHidato = hidatosTriangular.get(listTriang.getSelectionModel().getFocusedIndex()).getId();

            this.handleOpening();

        }
    }


    @FXML public void handleMouseClickHex(Event arg) throws IOException, ClassNotFoundException {
        if(!hidatosHexagonal.isEmpty()) {
            pointsScreen.IdHidato = hidatosHexagonal.get(listHex.getSelectionModel().getFocusedIndex()).getId();
            this.handleOpening();
        }
    }

    private void handleOpening() throws IOException, ClassNotFoundException {
        if (!Manager.getLoading()) {
            Stage stage = new Utils().openScreen("screens/ranking/pointsScreen.fxml", "Ranking", null, null, Modality.APPLICATION_MODAL, (Stage) this.listSquare.getScene().getWindow(), false);
        } else {
            //System.out.println(listHex.getSelectionModel().getFocusedIndex());
            Hidato hidato = Manager.getHidatoId(pointsScreen.IdHidato);
            Partida p = new Partida(hidato, Manager.getUser());
            Manager.registerGame(p);
            Stage stage = (Stage) listSquare.getScene().getWindow();


            new Utils().openScreen("screens/game/party/party.fxml", "Partida", 400, 600, Modality.WINDOW_MODAL, stage, false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

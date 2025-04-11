package no.ntnu.idatt2003.controller.laddergameController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.fileManagement.boardFiles.BoardFileReaderGson;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;
import no.ntnu.idatt2003.view.LaddergameView;

/**
 * The controller class handling game actions.
 */
public class GameController {
    
    BorderPane laddergamePane = new BorderPane();
    PlayerFileReader playerFileReader = new PlayerFileReader();
    Board board = null;

    public static LaddergameView laddergameView;
    public static void setLadderGame(LaddergameView view){
        laddergameView = view;
    }

    /**
     * This method creats a new game.
     */
    public void newGame(){
        if (playerFileReader.readPlayers().isEmpty()) {
            showInfoPopup("To create a new game, you need to add players first.");
        }else{
            
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Game");
            popupStage.setMinWidth(500);
            popupStage.setMinHeight(300);
            popupStage.setResizable(false);

            VBox chooseIcons = new VBox(10);

            List<ComboBox<Player>> playerComboBoxes = new ArrayList<>();
            List<Player> players = playerFileReader.readPlayers();
            List<String> pictureName = List.of("cheese.png", "mushroom.png", "olives.png", "pepperoni.png", "pineapple.png");

            for (int i = 0; i < 5; i++) {

                HBox pictureMenuSplitter = new HBox(10);
                Image picture = new Image(getClass().getResourceAsStream("/playerPieces/" + pictureName.get(i)));
                ImageView imageView = new ImageView(picture);
                imageView.setFitWidth(60); 
                imageView.setPreserveRatio(true); 
                StackPane pictureContainer = new StackPane(imageView);
                ComboBox<Player> choosePlayer = new ComboBox<>(FXCollections.observableArrayList(players));
               
                choosePlayer.setPromptText("Choose player");
                choosePlayer.setOnAction(e -> {
                    Player selected = choosePlayer.getValue();
                    if (selected != null) {
                        for (ComboBox<Player> comboBox : playerComboBoxes) {
                            if (comboBox != choosePlayer) {
                                comboBox.getItems().remove(selected);
                            }
                        }
                    }
                });

                playerComboBoxes.add(choosePlayer);

                pictureMenuSplitter.getChildren().addAll(pictureContainer, choosePlayer);
                chooseIcons.getChildren().add(pictureMenuSplitter);
            }

            VBox chooseIconsLayout = new VBox(10, chooseIcons);
            chooseIconsLayout.setPadding(new Insets(20));

            StackPane centerChooseBoard = new StackPane();
            VBox chooseBoard = new VBox();
            chooseBoard.setPadding(new Insets(20));
            Label label = new Label("Choose board size:");
            ComboBox<String> boardSize = new ComboBox<>(FXCollections.observableArrayList());
            boardSize.setPromptText("Choose board size");        
            boardSize.getItems().addAll("Small", "Medium", "Chaos");
            
            BoardFileReaderGson boardFileReader = new BoardFileReaderGson();
            
            List<String> jsonBoardNames = List.of("laddergame1");
            for (String jsonBoardName : jsonBoardNames) {
                boardSize.getItems().add(jsonBoardName);
            }

            chooseBoard.getChildren().addAll(label, boardSize);
            centerChooseBoard.getChildren().add(chooseBoard);

            StackPane centerStartButton = new StackPane();
            centerStartButton.setPadding(new Insets(20));
            Button startButton = new Button("Start Game");
            
            ArrayList<Player> selectedPlayers = new ArrayList<>();
            
            startButton.setOnAction(e ->{
                for (ComboBox<Player> comboBox : playerComboBoxes) {
                    Player selectedPlayer = comboBox.getValue();
                    if (selectedPlayer != null) {
                        selectedPlayer.setPicture(getClass().getResource("/playerPieces/" + pictureName.get(playerComboBoxes.indexOf(comboBox))));
                        selectedPlayers.add(selectedPlayer);
                    }
                }

                String selectedBoard = boardSize.getValue();
                if(selectedBoard.equals("Small")|| selectedBoard.equals("Medium") || selectedBoard.equals("Chaos")){
                    switch (selectedBoard) {
                        case "Small":
                            board = BoardGameFactory.createSmallBoard();
                            System.out.println("Selected board: " + selectedBoard);
                            break;
                
                        case "Medium":
                            board = BoardGameFactory.createMediumBoard();
                            System.out.println("Selected board: " + selectedBoard);
                            break;

                        case "Chaos":
                            board = BoardGameFactory.createChaosBoard();
                            System.out.println("Selected board: " + selectedBoard);
                            break;
                        }
                } else if (selectedBoard != null) {
                    try {
                        board = boardFileReader.readBoardFromFile("src/main/resources/boards/"+selectedBoard+".json");
                    } catch (IOException ex) {
                        //TODO Add a logger to the project and log the error
                        ex.printStackTrace();
                    }
                    System.out.println("Selected board: " + selectedBoard);
                }
            
                if(board.equals(null)){
                    showInfoPopup("Please select a board first.");
                }else if(selectedPlayers.isEmpty()){
                    showInfoPopup("Please select players first.");
                }else{
                    Game game = new Game(selectedPlayers, board);
                    game.start(); 
                    System.out.println("Game started with players: " + selectedPlayers);
                    System.out.println("Selected board: " + selectedBoard);
                    System.out.println("Game: " + game.getPlayers().toString());

                    laddergameView.setGameBoard(game);
                    popupStage.close();
                }
            });

            centerStartButton.getChildren().add(startButton);

            BorderPane mainLayout = new BorderPane();
            mainLayout.setPadding(new Insets(20));
            mainLayout.setLeft(chooseIconsLayout);
            mainLayout.setRight(centerChooseBoard);
            mainLayout.setBottom(centerStartButton); 

            popupStage.setScene(new Scene(mainLayout));
            popupStage.showAndWait(); 
        }
    }

     /**
     * This method shows an information popup when the user tries to create a new game without players.
     */
    private void showInfoPopup(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Can't create new game!");
        alert.setContentText(message);
        alert.setResizable(false);
        alert.setWidth(300);
        alert.setHeight(200);
        alert.showAndWait();
    }

    public void playerList(){

        VBox playerListBox = new VBox();
        List<Player> players = playerFileReader.readPlayers();

        for (Player player : players) {
            Label playerLabel = new Label(player.getPlayerName() + " - " + player.getPlayerNumber() + " - " + player.getBirthDate());
            playerListBox.getChildren().add(playerLabel);
        }
    }
}

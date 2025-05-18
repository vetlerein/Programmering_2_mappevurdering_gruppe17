package no.ntnu.idatt2003.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.fileManagement.boardFiles.BoardFileReaderGson;
import no.ntnu.idatt2003.model.fileManagement.boardFiles.BoardFileWriterGson;
import no.ntnu.idatt2003.model.fileManagement.boardFiles.BoardLoader;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;
import no.ntnu.idatt2003.view.GenericGameView;
import no.ntnu.idatt2003.view.LaddergameView;
import no.ntnu.idatt2003.view.PopupView;

/**
 * The controller class handling game actions.
 */
public class LaddergameController {

    GenericGameView genericGameView = new GenericGameView();
    PlayerFileReader playerFileReader;
    Board board;

    public static LaddergameView laddergameView;

    /**
     * Sets the laddergameView as view.
     * @param view
     */
    public static void setLadderGame(LaddergameView view){
        laddergameView = view;
    }

    /**
     * This method creats a new laddergame.
     */
    public void newGame(){
        playerFileReader = new PlayerFileReader();
        if (playerFileReader.readPlayers().isEmpty()) {
            PopupView.showInfoPopup("Can't create game","To create a new game, you need to add players first.");
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
                choosePlayer.getStyleClass().add("custom-combo");;
               
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
            ComboBox<String> boardSizeBox = new ComboBox<>(FXCollections.observableArrayList());
            boardSizeBox.getStyleClass().add("custom-combo");;
            boardSizeBox.setPromptText("Choose board size");        
            boardSizeBox.getItems().addAll("Small", "Medium", "Chaos", "laddergame1");
            
            File folder = new File("data/boards"); 
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

            if (files != null) {
                for (File file : files) {
                    String name = file.getName().replaceFirst("[.][^.]+$", ""); 
                    boardSizeBox.getItems().add(name);
                }
            }

            BoardFileReaderGson boardFileReaderGson = new BoardFileReaderGson();
            BoardFileWriterGson boardFileWriterGson = new BoardFileWriterGson();
            Button loadBoardButton = new Button("Load JSON board to the list");
            loadBoardButton.setId("buttons");
            loadBoardButton.setOnAction(e -> {
                BoardLoader boardLoader = new BoardLoader();
                File selectedFile = boardLoader.loadBoard();
                
                if (selectedFile != null) {
                    
                    String filename = selectedFile.getName().toString(); // f.eks. "mittBrett.json"
                    String name = filename.replaceFirst("[.][^.]+$", ""); // fjerner .json

                    try {
                        Board boardCopy = boardFileReaderGson.readBoardFromFile(selectedFile.getAbsolutePath());
                        boardFileWriterGson.writeBoardToFile(Paths.get("data/boards/"+name+".json"), boardCopy);
                        boardSizeBox.getItems().add(name);
                    } catch (IOException e1) {
                        PopupView.showInfoPopup("Can't create game","Error loading board: " + e1.getMessage());
                    }
                }
            });
           
            chooseBoard.getChildren().addAll(loadBoardButton, boardSizeBox);
            chooseBoard.setSpacing(10);
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

                String selectedBoard = boardSizeBox.getValue();
                if (selectedBoard == null) {
                    board = null;
                } else if(selectedBoard.equals("Small")|| selectedBoard.equals("Medium") || selectedBoard.equals("Chaos")){
                    switch (selectedBoard) {
                        case "Small" -> board = BoardGameFactory.createSmallBoard();
                        case "Medium" -> board = BoardGameFactory.createMediumBoard();
                        case "Chaos" -> board = BoardGameFactory.createChaosBoard();
                    }
                } else if (selectedBoard.equals("laddergame1")) {
                    try {
                        board = boardFileReaderGson.readBoardFromFile("src/main/resources/boards/laddergame1.json");
                    } catch (IOException ex) {
                        PopupView.showInfoPopup("Can't create game","Error loading board: " + ex.getMessage());
                    }
                } else if (selectedBoard != null) {

                    try {
                        board = boardFileReaderGson.readBoardFromFile("data/boards/" + selectedBoard + ".json");
                    } catch (IOException e1) {
                        PopupView.showInfoPopup("Can't create game","Error loading board: " + e1.getMessage());
                    }
                } 
            
                if(selectedBoard==null){
                    PopupView.showInfoPopup("Can't create game","Please select a board first.");
                }else if(selectedPlayers.isEmpty()){
                    PopupView.showInfoPopup("Can't create game","Please select players first.");
                }else if (selectedPlayers.size() == 1) {
                    PopupView.showInfoPopup("Can't create game","You need more than 1 player to start the game.");
                }else{
                    Game game = new Game(selectedPlayers, board);
                    game.start(); 
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
            mainLayout.getStylesheets().add(getClass().getResource("/Style/NewGame.css").toExternalForm());         

            popupStage.setScene(new Scene(mainLayout));
            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pineapple.png")));
            popupStage.showAndWait(); 
        }
    }

    /**
     * This method throws the dice for the palyer
     * @param game the game
     * @param animationActive if an animation is active
     */
    public void throwDice(Game game, boolean animationActive) {
        if(game.getGameStatus() && !animationActive) {
                Player player = game.getPlayers().get(game.getActivePlayer());
                player.move(game);
                genericGameView.showDice(player.getDicePaths());
                if(game.getPlayers().get(game.getActivePlayer()).getPlayerPause() == true){
                    while(game.getPlayers().get(game.getActivePlayer()).getPlayerPause() == true) {
                        game.getPlayers().get(game.getActivePlayer()).move(game);
                        game.nextPlayer();
                    }
                }
            }
    }

    /**
     * This method simulates the game.
     * @param currentTurn the current turn
     * @param maxTurns the maximum number of turns
     * @param game the game
     * @param animationActive if an animation is active
     */
    public void simulateGame(int currentTurn, int maxTurns, Game game, boolean animationActive) {
        if (currentTurn > maxTurns || !game.getGameStatus()) {
            return;
        }

        PauseTransition pause = new PauseTransition(Duration.millis(100));

        if(game.getGameStatus() && !animationActive) {
            Player player = game.getPlayers().get(game.getActivePlayer());
            player.move(game);
            genericGameView.showDice(player.getDicePaths());
            if(game.getPlayers().get(game.getActivePlayer()).getPlayerPause() == false){
                while(game.getPlayers().get(game.getActivePlayer()).getPlayerPause() == true) {
                    game.getPlayers().get(game.getActivePlayer()).move(game);
                    game.nextPlayer();
                }
            }

        } else {
            pause = new PauseTransition(Duration.millis(2000));
        }


        pause.setOnFinished(e -> {
            simulateGame(currentTurn + 1, maxTurns, game, animationActive); 
        });
        pause.play();
    }
}

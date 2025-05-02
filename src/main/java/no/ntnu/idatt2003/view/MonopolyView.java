package no.ntnu.idatt2003.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;
import no.ntnu.idatt2003.model.tile.PropertyTile;
import no.ntnu.idatt2003.model.tile.Tile;

public class MonopolyView implements PositionChangeObserver{
    public GenericGameView genericGameView = new GenericGameView();
    private Game game;
    private final int playerSize = 25;
    private final int pivotX = playerSize/2;
    private final int pivotY = playerSize;

    public BorderPane mainLayout = new BorderPane();

    public BorderPane mainLayout(){
        mainLayout.setId("mainLayout");
        
        //Top box
        HBox topMenu = new HBox();
        topMenu.setId("topMenu");
        Button newGameButton = new Button("Start new game");
        newGameButton.setOnAction(e->{
            //TODO add a new game button with player selection
            Board board = BoardGameFactory.createMonopolyBoard();

            PlayerFileReader playerFileReader = new PlayerFileReader();
            ArrayList<Player> players = playerFileReader.readPlayers();
            for(Player player : players){
                player.setPicture(getClass().getResource("/playerPieces/cheese.png"));
            }

            Game game = new Game(players, board);
            setGameBoard(game);
        }
        );

        Button backToMenuButton = new Button("Main menu");
        backToMenuButton.setOnAction(e -> {
            Stage currentStage = (Stage) mainLayout.getScene().getWindow();
            try {
                Scene mainMenuScene = new Scene(new MenuView().mainMenu(currentStage), currentStage.getWidth(), currentStage.getHeight());
                mainMenuScene.getStylesheets().add(getClass().getResource("/Style/Launcherwindow.css").toExternalForm());
                currentStage.setScene(mainMenuScene);
                currentStage.setTitle("Main Menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        topMenu.getChildren().addAll(newGameButton, backToMenuButton);

        //Right box
        VBox rightMenu = new VBox();
        rightMenu.setId("rightMenu");
                
        //Bottom box
        HBox bottomMenu = new HBox();
        bottomMenu.setId("bottomMenu");

        //Adding everything to the final window
        mainLayout.setTop(topMenu);
        mainLayout.setRight(rightMenu);
        mainLayout.setBottom(bottomMenu);

        return mainLayout;
    }

    public void setGameBoard(Game game){
        this.game = game;
        GridPane gameBoard = new GridPane();
        Pane centerOfBoard = new Pane();

        gameBoard.setId("gameBoard");
        int tileSize = 60;
        int rows = game.getBoard().getRows();
        int cols = game.getBoard().getCols();

        for (int i = 0; i < cols; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / cols);
            columnConstraints.setHgrow(Priority.ALWAYS);
            gameBoard.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / rows);
            rowConstraints.setVgrow(Priority.ALWAYS);
            gameBoard.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < game.getBoard().getGameboard().size(); i++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(tileSize, tileSize);
                stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHgrow(stackPane, Priority.ALWAYS);
                GridPane.setVgrow(stackPane, Priority.ALWAYS);

                int[] coordiantes = game.getBoard().getCoordinatesMonopoly(i+1);

                Board board = game.getBoard();
                Tile tile = board.getGameboard().get(i);
                switch (tile.getClass().getSimpleName()) {
                    case "StartTile":
                        stackPane.setStyle("-fx-background-color:rgb(251, 255, 0);");
                        break;
                    case "PropertyTile":
                        PropertyTile propertyTile = (PropertyTile) tile;
                        Property property = propertyTile.getProperty();
                        String name = property.getName();
                        int price = property.getPrice();
                        Text text = new Text(name + "\n" + price + " kr");
                        text.setStyle("-fx-font-size: 9px; -fx-fill: white; -fx-font-weight: bold");
                        stackPane.setStyle("-fx-background-color:" + property.getColor() + ";");
                        stackPane.getChildren().add(text);
                        break;
                    case "ChanceCardTile":
                        stackPane.setStyle("-fx-background-color:rgba(31, 16, 241, 0.85);");
                        Text chanceCardText = new Text("?");
                        chanceCardText.setStyle("-fx-font-size: 25px; -fx-fill: white; -fx-font-weight: bold;");
                        stackPane.getChildren().add(chanceCardText);
                        break;
                    case "GoToJailTile":
                        stackPane.setStyle("-fx-background-color:rgb(228, 168, 239);");
                        Text goToJailText = new Text("Go to Jail");
                        goToJailText.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
                        stackPane.getChildren().add(goToJailText);
                        break;
                    case "JailTile":
                        stackPane.setStyle("-fx-background-color:rgb(255, 167, 26);");
                        Text jailText = new Text("Jail");
                        jailText.setStyle("-fx-font-size: 15px; -fx-fill: white; -fx-font-weight: bold;");
                        stackPane.getChildren().add(jailText);
                        break;
                }


                //Adds the lines to squares
                boolean isTopRow    = (coordiantes[1] == 0);
                boolean isBottomRow = (coordiantes[1] == rows - 1);
                boolean isLeftCol   = (coordiantes[0] == 0);
                boolean isRightCol  = (coordiantes[0] == cols - 1);

                double top    = 1;                        
                double left   = 1;                        
                double bottom = (isBottomRow || isTopRow) ? 1 : 0;   
                double right = (isLeftCol || isRightCol) ? 1 : 0;   

                BorderStroke stroke = new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(top, right, bottom, left)
                );
                stackPane.setBorder(new Border(stroke));
                gameBoard.add(stackPane, coordiantes[0], coordiantes[1]); 
        }
        
        //Adds the players pieces to the board
        int index = 0;
        for (Player player : game.getPlayers()){
            StackPane stackPane = getTileAt(gameBoard, 0, game.getBoard().getRows()-1);
            ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
            playerImage.setFitWidth(playerSize);
            playerImage.setFitHeight(playerSize);
            //rotate by the bottom middle of the image
            Rotate rotate = new Rotate(72*index, pivotX, pivotY);
            playerImage.getTransforms().add(rotate);
            playerImage.setTranslateY(-10);
            index++;

            playerImage.setId("player" + player.getPlayerNumber());
            stackPane.getChildren().add(playerImage);
            player.setObserver(this);
        }

        //Right box
        VBox rightMenu = new VBox();
        rightMenu.setId("rightMenu");
        Button throwDice = new Button("Throw dice");
        throwDice.setOnAction(e -> {
            if(game.getGameStatus() == true) {
                Player player = game.getPlayers().get(game.getActivePlayer());
                player.move(game);
                genericGameView.showDice(player.getDicePaths(), mainLayout);
                game.getPlayers().get(game.getActivePlayer()).move(game);
                game.nextPlayer();
            }
        });

        Label whosTurn = new Label(game.getPlayers().get(game.activePlayer) + "'s turn");
        whosTurn.setId("whosTurn");
        Label players = new Label("Players: ");

        //Box on the side for the players
        VBox playersBox = new VBox();
        playersBox.setId("playersBox");
        for (Player player : game.getPlayers()){
            HBox pictureNameSplitter = new HBox(10);
            ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
            ImageView pauseImage = new ImageView();
            pauseImage.setId("image"+player.getPlayerNumber());
            playerImage.setFitWidth(40); 
            playerImage.setPreserveRatio(true); 
            Label playerLabel = new Label(player.getPlayerName()+": ");
            Label position = new Label(player.getPosition() + "");
            position.setId("position" + player.getPlayerNumber());
            pictureNameSplitter.getChildren().addAll(playerImage, playerLabel, position);
            pictureNameSplitter.setId("playerBox" + player.getPlayerNumber());
            playersBox.getChildren().add(pictureNameSplitter);
        }

        rightMenu.getChildren().addAll(throwDice, whosTurn, players, playersBox);

        
        mainLayout = mainLayout();
        //Adding a pane in the middle of the board 
        gameBoard.add(centerOfBoard, 1, 1, cols-2, rows-2);
        gameBoard.setGridLinesVisible(false);
        mainLayout.setCenter(gameBoard);
        mainLayout.setRight(rightMenu);
    }

    @Override
    public void positionChanged(Player player) {

    }

     private StackPane getTileAt(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (StackPane) node;
            }
        }
        return null;
    }
}

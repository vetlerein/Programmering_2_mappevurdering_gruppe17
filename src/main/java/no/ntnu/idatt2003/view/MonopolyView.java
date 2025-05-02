package no.ntnu.idatt2003.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

        mainLayout = mainLayout();
        gameBoard.setGridLinesVisible(false);
        gameBoard.setId("gameBoardFinal");
        mainLayout.setCenter(gameBoard);
        updateSideBar();   
    }

    @Override
    public void positionChanged(Player player) {
        showPlayerCards(player);
        int[] coordiantes = this.game.getBoard().getCoordinatesMonopoly(player.getPosition());
        StackPane stackPane = getTileAt((GridPane) mainLayout.lookup("#gameBoardFinal"), coordiantes[0], coordiantes[1]);
        String id = "player" + player.getPlayerNumber();
        Node node = mainLayout.lookup("#"+id);
        Rotate rotate = null;
        if (node != null) {
            rotate = (Rotate) node.getTransforms().get(0);
            ((Pane) node.getParent()).getChildren().remove(node);
        }
        ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
        playerImage.setId(id);
        playerImage.setFitWidth(playerSize);
        playerImage.setFitHeight(playerSize);
        playerImage.setTranslateY(-10);
        playerImage.getTransforms().add(rotate);
        stackPane.getChildren().add(playerImage);

        Label positionLabel = (Label) mainLayout.lookup("#position" + player.getPlayerNumber());
        positionLabel.setText(game.getBoard().getGameboard().get(player.getPosition()-1).getClass().getSimpleName());
        if (game.getBoard().getGameboard().get(player.getPosition()-1) instanceof PropertyTile propertyTile){
            positionLabel.setText(propertyTile.getProperty().getName());
        }
    }

    private StackPane getTileAt(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (StackPane) node;
            }
        }
        return null;
    }

    public void showPlayerCards(Player player) {
        HBox cardBox = new HBox();

        ScrollPane scroll = new ScrollPane(cardBox);
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight(100);
        scroll.setPannable(true);
        

        for (int i = 0; i < player.getProperties().size(); i++) {
            Property property = player.getProperties().get(i);
            cardBox.getChildren().add(getPropertyCard(property));
        }

        mainLayout.setBottom(scroll);
    }

    private VBox getPropertyCard(Property property) {
        VBox card = new VBox(5);
        card.setPrefSize(80, 100);
        card.setPadding(new Insets(5));
        card.setMaxSize(80, 100);
        card.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
        
        //Nameplate
        StackPane nameHolder = new StackPane();
        nameHolder.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(nameHolder, Priority.NEVER);  
        Text name = new Text(property.getName());
        name.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
        Rectangle rect = new Rectangle();
        rect.setHeight(30);
        rect.widthProperty().bind(nameHolder.widthProperty());
        rect.setFill(Color.valueOf(property.getColor()));
        nameHolder.getChildren().addAll(rect, name);

        //Rent grid
        GridPane rentGrid = new GridPane();
        for (int i = 0; i < 4; i++) {
            Text rentText = new Text("Rent with " + i + " houses");
            rentGrid.add(rentText, 0, i);
            Text rentAmount = new Text(String.valueOf(property.getRent()) + " $");
            rentGrid.add(rentAmount, 1, i);
            rentGrid.setMargin(rentText, new Insets(5));
            rentGrid.setMargin(rentAmount, new Insets(5));
        }

        //Hotel rent
        Text rentText = new Text("Rent with a hotel");
        rentGrid.add(rentText, 0, 4);
        Text rentAmount = new Text(String.valueOf(property.getRent()) + " $");
        rentGrid.add(rentAmount, 1, 4);
        rentGrid.setGridLinesVisible(true);
        rentGrid.setMargin(rentText, new Insets(5));
        rentGrid.setMargin(rentAmount, new Insets(5));
        rentGrid.setPadding(new Insets(5));

        //Costs
        Text houseCostText = new Text("House cost: " + property.getHouseCost() + " $");
        Text propertyBuyPrice = new Text("Property price: " + property.getPrice() + " $");

        Button pawnButton = new Button("Pawn property");
        pawnButton.setId("pawnButton");
        pawnButton.setOnAction(e -> {
            //TODO add pawn button functionality
            System.out.println("Pawn property: " + property.getName());
        });
        card.getChildren().addAll(nameHolder, rentGrid, houseCostText, propertyBuyPrice, pawnButton);
        return card;
    }

    private void updateSideBar() {
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

        Button tradeDice = new Button("Trade");
        tradeDice.setOnAction(e -> {
            if(game.getGameStatus() == true) {
                
            }
        });

        Label whosTurn = new Label(game.getPlayers().get(game.activePlayer) + "'s turn");
        whosTurn.setId("whosTurn");
        Label players = new Label("Players: ");

        //Box on the side for the players
        VBox playersBox = new VBox(10);
        playersBox.setId("playersBox");
        for (Player player : game.getPlayers()){
            VBox personalBox = new VBox();
            personalBox.setPadding(new Insets(5));
            personalBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
            personalBox.setMinWidth(Region.USE_COMPUTED_SIZE);
            personalBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
                        
            HBox pictureNameSplitter = new HBox(10);
            ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
            playerImage.setPreserveRatio(true); 
            playerImage.setFitWidth(20); 
            playerImage.setPreserveRatio(true); 
            Label playerLabel = new Label(player.getPlayerName()+": ");
            pictureNameSplitter.getChildren().addAll(playerImage, playerLabel);
            pictureNameSplitter.setId("playerBox" + player.getPlayerNumber());

            Label position = new Label(game.getBoard().getGameboard().get(player.getPosition()-1).getClass().getSimpleName());

            if (game.getBoard().getGameboard().get(player.getPosition()-1) instanceof PropertyTile propertyTile){
                position.setText(propertyTile.getProperty().getName());
            }
            position.setId("position" + player.getPlayerNumber());

            Label playerBalance = new Label(player.getBalance() + " $");
            personalBox.getChildren().addAll(pictureNameSplitter, playerBalance, position);
            personalBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
            playersBox.getChildren().addAll(personalBox);
        }

        rightMenu.getChildren().addAll(throwDice, tradeDice, whosTurn, players, playersBox);
        mainLayout.setRight(rightMenu);
    }
}


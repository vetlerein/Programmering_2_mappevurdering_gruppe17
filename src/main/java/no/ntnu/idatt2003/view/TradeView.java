package no.ntnu.idatt2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.controller.MonopolyController;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Property;

public class TradeView {
    
    Stage tradeStage = new Stage();
    BorderPane mainLayout = new BorderPane();
    VBox leftMenu = new VBox();
    VBox rightMenu = new VBox();
    MonopolyController monopolyController = new MonopolyController();

    public void showTradeView(Game game){
        
        tradeStage.initModality(Modality.APPLICATION_MODAL);
        tradeStage.setTitle("New Game");
        tradeStage.setMinWidth(500);
        tradeStage.setMinHeight(300);
        tradeStage.setResizable(false);
        leftMenu.setId("tradeMenu");
        rightMenu.setId("tradeMenu");

        Label leftMenuName = new Label("Player 1");
        
        //TODO add a way to choose which player to trade with
        Label rightMenuName = new Label("Player 2");
        
        leftMenu.getChildren().add(monopolyController.getPlayerPropertiesBox(game.getPlayers().get(game.activePlayer)));
        rightMenu.getChildren().add(monopolyController.getPlayerPropertiesBox(game.getPlayers().get(game.activePlayer + 1)));
        //Player 2 properties



     
        leftMenu.getChildren().addAll(leftMenuName);
        rightMenu.getChildren().addAll(rightMenuName);
        mainLayout.setLeft(leftMenu);
        mainLayout.setRight(rightMenu);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getStylesheets().add(getClass().getResource("/Style/TradeView.css").toExternalForm());         
        tradeStage.setScene(new Scene(mainLayout));
        tradeStage.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pepperoni.png")));
        tradeStage.showAndWait(); 
    }

}

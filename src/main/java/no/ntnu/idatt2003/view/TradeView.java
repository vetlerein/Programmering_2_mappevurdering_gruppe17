package no.ntnu.idatt2003.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TradeView {
    
    Stage tradeStage = new Stage();
    BorderPane mainLayout = new BorderPane();
    VBox leftMenu = new VBox();
    VBox rightMenu = new VBox();

    

    public void showTradeView(){
        
        tradeStage.initModality(Modality.APPLICATION_MODAL);
        tradeStage.setTitle("New Game");
        tradeStage.setMinWidth(500);
        tradeStage.setMinHeight(300);
        tradeStage.setResizable(false);
        leftMenu.setId("tradeMenu");
        rightMenu.setId("tradeMenu");

        Label leftMenuName = new Label("Player 1");
        Label rightMenuName = new Label("Player 2");
        
             





     
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

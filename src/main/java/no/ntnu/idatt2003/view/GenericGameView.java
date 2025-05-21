package no.ntnu.idatt2003.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import no.ntnu.idatt2003.model.Player;

/**
 * a shared class for the parts of the view that are used by both games
 */
public class GenericGameView {

  /**
   * The main layout of the game
   */
  public static BorderPane mainLayout;

  /**
   * Shows the physical dice on the board
   *
   * @param dicePaths the paths to the dice images
   */
  public void showDice(URL[] dicePaths) {
    StackPane centerStackPane = new StackPane();
    Pane dicePane = new Pane();
    dicePane.setId("dicePane");
    int size = 50;
    for (URL dicePath : dicePaths) {
      ImageView diceImageView = new ImageView(dicePath.toExternalForm());
      diceImageView.setFitWidth(size);
      diceImageView.setFitHeight(size);
      diceImageView.setMouseTransparent(true);

      Random random = new Random();

      double maxX = 400 - size;
      double maxY = 400 - size;

      double x = random.nextDouble() * maxX;
      double y = random.nextDouble() * maxY;

      diceImageView.setLayoutX(x);
      diceImageView.setLayoutY(y);

      diceImageView.setRotate(random.nextInt(0, 360));
      dicePane.setMouseTransparent(true);
      dicePane.getChildren().add(diceImageView);
      dicePane.setMouseTransparent(true);
    }

    centerStackPane.getChildren().clear();
    centerStackPane.getChildren().add(mainLayout.lookup("#gameBoardFinal"));
    centerStackPane.getChildren().add(dicePane);
    mainLayout.setCenter(centerStackPane);
  }

  /**
   * Method for when a player wins the game
   *
   * @param player the player that won the game
   */
  public void playerWon(Player player) {
    Pane centerPane = (Pane) mainLayout.getCenter();

    Pane overlay = null;
    for (Node child : centerPane.getChildren()) {
      if ("winnerOverlay".equals(child.getId())) {
        overlay = (Pane) child;
        break;
      }
    }
    if (overlay == null) {
      overlay = new Pane();
      overlay.setId("winnerOverlay");
      overlay.setMouseTransparent(false);
      centerPane.getChildren().add(overlay);
    }
    int size = 50;

    List<Image> images = new ArrayList<>();
    images.add(new Image(getClass().getResourceAsStream("/playerPieces/mushroom.png")));
    images.add(new Image(getClass().getResourceAsStream("/playerPieces/cheese.png")));
    images.add(new Image(getClass().getResourceAsStream("/playerPieces/olives.png")));
    images.add(new Image(getClass().getResourceAsStream("/playerPieces/pepperoni.png")));
    images.add(new Image(getClass().getResourceAsStream("/playerPieces/pineapple.png")));
    Random random = new Random();
    double layoutHeight = mainLayout.getHeight();
    double layoutWidth = mainLayout.getWidth();
    ArrayList<ImageView> pizzas = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      Image img = images.get(random.nextInt(images.size()));
      ImageView imageView = new ImageView(img);

      double initX = random.nextDouble() * layoutWidth;
      double initY = random.nextDouble() * layoutHeight;
      imageView.setLayoutX(initX);
      imageView.setLayoutY(initY);
      imageView.setRotate(random.nextDouble() * 360);

      imageView.setFitWidth(size);
      imageView.setFitHeight(size);
      overlay.getChildren().add(imageView);
      pizzas.add(imageView);
    }
    Text winnerText = new Text(player.getPlayerName() + " has won the game!");
    winnerText.setId("winnerText");
    overlay.getChildren().add(winnerText);
    overlay.setMouseTransparent(true);
    mainLayout.setCenter(overlay);

    double fallSpeed = 2;
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        for (int i = 0; i < pizzas.size(); i++) {
          ImageView view = pizzas.get(i);

          double newY = view.getLayoutY() + fallSpeed;
          if (newY > layoutHeight) {
            newY = -size + 50;
            view.setLayoutX(random.nextDouble() * layoutWidth);
            view.setRotate(random.nextDouble() * 360);
          }
          view.setLayoutY(newY);
        }
      }
    };
    timer.start();

  }


  /**
   * Sets the main layout for the view
   *
   * @param layout the main layout of the game
   */
  public static void setMainLayout(BorderPane layout) {
    mainLayout = layout;
  }
}

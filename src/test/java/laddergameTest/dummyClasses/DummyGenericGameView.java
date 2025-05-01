package laddergameTest.dummyClasses;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.view.GenericGameView;

public class DummyGenericGameView extends GenericGameView {
    
    /**
     * Constructor for the DummyGenericGameView class.
     * Initializes the main layout and sets up the view.
     * Needed for testing purposes.
     */
    @Override
    public void playerWon(Player player, BorderPane mainLayout) {
    }
}   
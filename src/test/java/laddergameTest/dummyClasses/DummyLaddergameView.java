package laddergameTest.dummyClasses;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt2003.view.LaddergameView;

public class DummyLaddergameView extends LaddergameView {
    
    /**
     * Constructor for the DummyLaddergameView class.
     * Initializes the main layout and sets up the view.
     * Needed for testing purposes.
     */
    @Override
    public BorderPane getMainLayout() {
        return new BorderPane(); 
    }
}
    


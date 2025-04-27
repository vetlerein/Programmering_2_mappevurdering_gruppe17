package laddergameTest;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt2003.view.LaddergameView;

public class DummyLaddergameView extends LaddergameView {
    
    @Override
    public BorderPane getMainLayout() {
        return new BorderPane(); 
    }
}
    


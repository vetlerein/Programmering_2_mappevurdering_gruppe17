package no.ntnu.idatt2003.model.fileManagement.boardFiles;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BoardLoader {
    /**
     * @param primaryStage Needs to be passed in to open the file chooser dialog.
     * @return Returns the selected file from the file chooser dialog.
     */
    public File loadBoard() {
        
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("New Game");
        popupStage.setMinWidth(500);
        popupStage.setMinHeight(300);
        popupStage.setResizable(false);
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Board File");
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(popupStage);

        return selectedFile;
    } 
}

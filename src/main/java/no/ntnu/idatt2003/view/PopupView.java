package no.ntnu.idatt2003.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The PopupView class is responsible for displaying popups in the application.
 */
public class PopupView {
    /**
     * This method is a general popup method that can be used to show a popup with a header and a message.
     */
    public static void showInfoPopup(String headertext, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(headertext);
        alert.setContentText(message);
        alert.setResizable(false);
        alert.setWidth(300);
        alert.setHeight(200);
        alert.showAndWait();
    }
}

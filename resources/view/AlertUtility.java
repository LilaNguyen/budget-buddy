package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlertUtility {
	
	/**
	 * The following method displays a pop-up alert, informing the user of the error.
	 * @param message the error message to be displayed
	 */
	public static void displayErrorAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
		alert.setTitle("Input Error");
		// remove default header
		alert.setHeaderText(null);
		// display the alert and wait for user interaction
		alert.showAndWait();
	}
	
	/**
	 * The following method displays a pop-up alert, informing the user that an action was completed
	 * successfully.
	 * @param message the success message to be displayed
	 */
	public static void displaySuccessAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.setTitle("Success");
		// remove default header
		alert.setHeaderText(null);
		// load custom image to use as icon
		Image icon = new Image("images/successIcon.png");
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(50);
		iconView.setFitWidth(50);
		alert.setGraphic(iconView);
		// display the alert and wait for user interaction
		alert.showAndWait();
	}
	
	/*
	 * The following method displays a pop-up alert, confirming that user wants to delete a transaction.
	 */
	public static boolean displayDeleteConfirmationAlert() {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this transaction?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }
	
}

package application.controller;

import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.TransTypeBean;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CreateTransTypeController {

	@FXML TextField transTypeField;

	@FXML public void enterTransTypeOp() {}

	@FXML public void submitOp() {
		DalInt dalInterface = new FileDal();

		String transType = transTypeField.getText();
		
        // Perform validation
        if (transType == null || transType.trim().isEmpty()) {
        	displayErrorAlert("Transaction type's name is required.");
            return;
        }
        
		// Check for duplicates
		boolean haveDuplicate = false;
		List<TransTypeBean> types = dalInterface.loadTransTypes();
		for (TransTypeBean existingType : types) {
			try {
				if (existingType.getTransTypeName().equalsIgnoreCase(transType)) {
					haveDuplicate = true;
					displayErrorAlert("This transaction type's name already exists. Please enter a unique name.");
					return;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        // Create TransTypeBean object to represent new transaction type
		TransTypeBean newTransType = new TransTypeBean(transType);
        
        // Save account using DAL
        dalInterface.saveTransType(newTransType);
        
        // Set new account in CommonObjs to access most recently created account
        CommonObjs.getInstance().setTransTypeBean(newTransType);
        
        displaySuccessAlert("Transaction type successfully created.");
        clearFormOp();
	}

	/**
	 * This method clears the field for "Transaction type's name."
	 */
	@FXML public void clearFormOp() {
		transTypeField.clear();
	}
	
	/**
	 * The following method displays a pop-up alert, informing the user of the error.
	 * @param message the error message to be displayed
	 */
	private void displayErrorAlert(String message) {
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
	private void displaySuccessAlert(String message) {
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

}

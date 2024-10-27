package application.controller;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class CreateAccountController {
	// Need a reference of CommonObjs objects here
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML private TextField accNameField;  // For account name input
    @FXML private DatePicker openingDateField;  // For opening date input
    @FXML private TextField openingBalField;  // For opening balance input

    @FXML
	public void initialize() {
    	enterOpeningDateOp();
	}
    
	@FXML public void enterAccNameOp() {}

	/**
	 * This method sets the default value of opening date to the current date. 
	 * The opening date is updated if user selects a date. 
	 */
	@FXML public void enterOpeningDateOp() {
		openingDateField.setValue(LocalDate.now());
		openingDateField.setOnAction(event -> openingDateField.getValue());
	}

	@FXML public void enterOpeningBalOp() {}

	@FXML public void submitOp() {
		String accName = accNameField.getText();  // Get the account name
        String openingDate = openingDateField.getValue() != null ? openingDateField.getValue().toString() : null;  // Convert date to String
        String openingBalance = openingBalField.getText();  // Get the balance

        /*
        // Set the input values into CommonObjs
        commonObjs.setAccountName(accName);
        commonObjs.setOpeningDate(openingDate);
        commonObjs.setOpeningBalance(openingBalance);
        */

        // Perform validation
        if (accName == null || accName.trim().isEmpty()) {
        	displayErrorAlert("Account name is required.");
            return;
        }
        
        if (openingDate == null || openingDate.trim().isEmpty()) {
        	displayErrorAlert("Opening date is required.");
            return;
        }
		
        double balance = 0.0;
        try {
            balance = Double.parseDouble(openingBalance);
            if (balance < 0) {
            	displayErrorAlert("Balance cannot be negative.");
                // clear field for new input
                openingBalField.setText("");
                return;
            }
            // format balance to 2 decimal places
            openingBalField.setText(String.format("%.2f", balance));
        } 
        catch (NumberFormatException e) {
        	displayErrorAlert("Invalid balance. Please enter a numeric value.");
            // clear field for new input
            openingBalField.setText("");
            return;
        }
        displaySuccessAlert("Account successfully created.");
        clearFormOp();
	}

	/**
	 * This method clears the fields for "Account Name" and "Opening Balance."
	 * The field "Opening Date" is not cleared but reset to its default value (current date).
	 */
	@FXML public void clearFormOp() {
		accNameField.clear();
		enterOpeningDateOp();
		openingBalField.clear();
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

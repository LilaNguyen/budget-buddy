package application.controller;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;

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

	@FXML public void enterOpeningDateOp() {
		// By default, opening date is set to current date 
		openingDateField.setValue(LocalDate.now());
		// Updates date if user selects a date
		openingDateField.setOnAction(event -> openingDateField.getValue());
		
	}

	@FXML public void enterOpeningBalOp() {}

	@FXML public void submitOp() {
		String accName = accNameField.getText();  // Get the account name
        String openingDate = openingDateField.getValue() != null ? openingDateField.getValue().toString() : null;  // Convert date to String
        String openingBalance = openingBalField.getText();  // Get the balance

        // Set the input values into CommonObjs
        commonObjs.setAccountName(accName);
        commonObjs.setOpeningDate(openingDate);
        commonObjs.setOpeningBalance(openingBalance);

        // Perform validation
        if (accName == null || accName.trim().isEmpty()) {
            System.out.println("Account name is required.");
            return;
        }
        
        if (openingDate == null || openingDate.trim().isEmpty()) {
            System.out.println("Opening date is required.");
            return;
        }
		
        double balance = 0.0;
        try {
            balance = Double.parseDouble(openingBalance);
            if (balance < 0) {
                System.out.println("Balance cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance. Please enter a numeric value.");
            return;
        }
	}

	@FXML public void cancelOp() {}

	
}

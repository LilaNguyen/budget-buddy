package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import view.AlertUtility;

public class CreateAccountController {
	// Reference to CommonObjs objects
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
		DalInt dalInterface = new FileDal();
        
		String accName = accNameField.getText();
		LocalDate openingDate = openingDateField.getValue();
		String openingBalanceStr = openingBalField.getText(); 

		/*
        // Set the input values into CommonObjs
        commonObjs.setAccountName(accName);
        commonObjs.setOpeningDate(openingDate);
        commonObjs.setOpeningBalance(openingBalance);
        */
		
        // Perform validation
        if (accName == null || accName.trim().isEmpty()) {
        	AlertUtility.displayErrorAlert("Account name is required.");
            return;
        }
        
        // Check for duplicate account name
		List<AccountBean> accounts = dalInterface.loadAccounts();
		for (AccountBean existingAcc : accounts) {
			try {
				if (existingAcc.getAccountName().equalsIgnoreCase(accName)) {
					AlertUtility.displayErrorAlert("This account name already exists. Please enter a unique account name.");
					return;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        if (openingDate == null) {
        	AlertUtility.displayErrorAlert("Opening date selection is required.");
            return;
        }
		
        double openingBalance = 0.0;
        try {
        	openingBalance = Double.parseDouble(openingBalanceStr);
            if (openingBalance < 0) {
            	AlertUtility.displayErrorAlert("Balance cannot be negative.");
                // clear field for new input
            	openingBalField.clear();
                return;
            }
            // format balance to 2 decimal places
            openingBalField.setText(String.format("%.2f", openingBalance));
        } 
        catch (NumberFormatException e) {
        	AlertUtility.displayErrorAlert("Invalid balance. Please enter a numeric value.");
            // clear field for new input
        	openingBalField.clear();
            return;
        }
        // Create AccountBean object to represent new account
        AccountBean newAccount = new AccountBean(accName, openingDate, openingBalance);
        
        // Save account using DAL
        dalInterface.saveAccount(newAccount);
        
        // Set new account in CommonObjs to access most recently created account
        CommonObjs.getInstance().setAccountBean(newAccount);
        
        AlertUtility.displaySuccessAlert("Account successfully created.");
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
	
	
}

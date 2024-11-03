package application.controller;

import java.time.LocalDate;

import application.CommonObjs;
import application.interfaces.FileDal;
import application.model.AccountBean;
import application.model.DalInt;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class CreateTransactionController {
	// Need a reference of CommonObjs objects here
		private CommonObjs commonObjs = CommonObjs.getInstance();
		
		@FXML private ComboBox<String> accountDropDown;
	    @FXML private ComboBox<String> transactionTypeDropDown;
	    @FXML private DatePicker transactionDatePicker;
	    @FXML private TextField descriptionField;
	    @FXML private TextField paymentAmountField;
	    @FXML private TextField depositAmountField;
	    @FXML private Button save;
	    @FXML private Button cancel; 

	    @FXML
	    public void initialize() {
	        System.out.println("Initializing the controller...");

	        // Filler Data
	        accountDropDown.getItems().addAll("Acc1", "Acc2");
	        transactionTypeDropDown.getItems().addAll("Deposit", "Withdrawal");
	        
	        // Fill values
	        accountDropDown.getSelectionModel().selectFirst(); // shows first by default
	        transactionTypeDropDown.getSelectionModel().selectFirst(); // shows first by default

	        // Set the date to today
	        transactionDatePicker.setValue(LocalDate.now());
	        
	        // Print to confirm values
	        System.out.println("Default account: " + accountDropDown.getSelectionModel().getSelectedItem());
	        System.out.println("Default transaction type: " + transactionTypeDropDown.getSelectionModel().getSelectedItem());
	        System.out.println("Default date: " + transactionDatePicker.getValue());

	        // Action for save button
	        save.setOnAction(event -> submit());
	    }
	    

		private void submit() {
			String accName = accountDropDown.getSelectionModel().getSelectedItem();
			String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
			LocalDate transDate = transactionDatePicker.getValue();
			String descr = descriptionField.getText();
			Double payment = null;
			Double deposit = null;
			
			//make sure completing all fields
			if (accName == null ||
				transType == null ||
				transDate == null ||
				descr.isEmpty()) {
				displayErrorAlert("Please Complete All Fields");
				return;
			}
			//make sure entered either payment or deposit
			if(paymentAmountField.getText().isEmpty() && depositAmountField.getText().isEmpty()) {
				displayErrorAlert("Please enter a Payment or Deposit amount");
				return;
			}
			//not both
			if(!paymentAmountField.getText().isEmpty() && !depositAmountField.getText().isEmpty()) {
				displayErrorAlert("Please enter only one: a Payment or Deposit amount");
				return;
			}
			//i
			//if deposit
			if(paymentAmountField.getText().isEmpty()) {
				try {
					deposit = Double.parseDouble(depositAmountField.getText());
				}
				catch (NumberFormatException e) {
					displayErrorAlert("Deposit must be a valid number");
					return;
				}
			}
			//if payment
			if(depositAmountField.getText().isEmpty()) {
				try {
					payment = Double.parseDouble(paymentAmountField.getText());
				}
				catch (NumberFormatException e) {
					displayErrorAlert("Payment must be a valid number");
					return;
				}
			}
			
			System.out.println("transaction saved");
		}
		private void displayErrorAlert(String message) {
			Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
			alert.setTitle("Input Error");
			// remove default header
			alert.setHeaderText(null);
			// display the alert and wait for user interaction
			alert.showAndWait();
		}
		
	}
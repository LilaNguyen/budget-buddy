package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import view.AlertUtility;


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

	        DalInt dalInterface = new FileDal();
	        List<AccountBean> accounts = dalInterface.loadAccounts();
	        List<TransTypeBean> types = dalInterface.loadTransTypes();
	        
	        for (AccountBean bean : accounts) {
	        	accountDropDown.getItems().add(bean.getAccountName());
	        }
	        
	        for (TransTypeBean bean : types) {
	        	transactionTypeDropDown.getItems().add(bean.getTransTypeName());
	        }
	        
	        // Fill values
	        accountDropDown.getSelectionModel().selectFirst(); // shows first by default
	        transactionTypeDropDown.getSelectionModel().selectFirst(); // shows first by default

	        // Set the date to today
	        transactionDatePicker.setValue(LocalDate.now());
	    }
	    

	    @FXML public void submitOp() {
			DalInt dalInterface = new FileDal();
			
			String accName = accountDropDown.getSelectionModel().getSelectedItem();
			String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
			LocalDate transDate = transactionDatePicker.getValue();
	        
			String descr = descriptionField.getText();
			Double payment = 0.0;
			Double deposit = 0.0;
			
			//make sure completing all fields
			if (accName == null) {
				AlertUtility.displayErrorAlert("Account selection is required.");
				return;
			}
			
			if (transType == null) {
				AlertUtility.displayErrorAlert("Transaction Type selection is required.");
				return;
			}
			
			if (transDate == null) {
				AlertUtility.displayErrorAlert("Transaction Date is required.");
				return;
			}
			
			if (descr.isEmpty()) {
				AlertUtility.displayErrorAlert("Transaction Description is required.");
				return;
			}
			
			//make sure entered either payment or deposit
			if(paymentAmountField.getText().isEmpty() && depositAmountField.getText().isEmpty()) {
				AlertUtility.displayErrorAlert("Payment or deposit amount required.");
				return;
			}
			//not both
			if(!paymentAmountField.getText().isEmpty() && !depositAmountField.getText().isEmpty()) {
				AlertUtility.displayErrorAlert("Enter either a payment OR deposit amount.");
				return;
			}
			//if deposit
			if(paymentAmountField.getText().isEmpty()) {
				try {
					deposit = Double.parseDouble(depositAmountField.getText());
				}
				catch (NumberFormatException e) {
					AlertUtility.displayErrorAlert("Deposit must be a valid number.");
					// clear field for new input
					depositAmountField.clear();
					return;
				}
				depositAmountField.setText(String.format("%.2f", deposit));
			}
			//if payment
			if(depositAmountField.getText().isEmpty()) {
				try {
					payment = Double.parseDouble(paymentAmountField.getText());
				}
				catch (NumberFormatException e) {
					AlertUtility.displayErrorAlert("Payment must be a valid number.");
					// clear field for new input
					paymentAmountField.clear();
					return;
				}
				paymentAmountField.setText(String.format("%.2f", payment));
			}

	        // Create TransTypeBean object to represent new transaction type
			TransBean newTrans = new TransBean(accName, transType, transDate, descr, payment, deposit);
	        
	        // Save account using DAL
	        dalInterface.saveTransactions(newTrans);
	        
	        // Set new account in CommonObjs to access most recently created account
	        CommonObjs.getInstance().setTransBean(newTrans);

	        AlertUtility.displaySuccessAlert("Transaction successfully saved");
			clearFormOp();
		}
		
		/**
		 * This method clears the fields for "Description," "Payment Amount," and "Deposit Amount."
		 * The fields "Account," "Transaction Type," and "Transaction Date" are not cleared but reset 
		 * to their default values.
		 */
		@FXML public void clearFormOp() {
			descriptionField.clear();
			paymentAmountField.clear();
			depositAmountField.clear();
		}
		
	}
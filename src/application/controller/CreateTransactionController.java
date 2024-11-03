package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import application.dal.DalInt;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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
	        
	        // Print to confirm values
	        System.out.println("Default account: " + accountDropDown.getSelectionModel().getSelectedItem());
	        System.out.println("Default transaction type: " + transactionTypeDropDown.getSelectionModel().getSelectedItem());
	        System.out.println("Default date: " + transactionDatePicker.getValue());

	        // Action for save button
	        save.setOnAction(event -> submit());
	    }
	    

		private void submit() {
			DalInt dalInterface = new FileDal();
			
			String accName = accountDropDown.getSelectionModel().getSelectedItem();
			String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
			LocalDate transDate = transactionDatePicker.getValue();
			String descr = descriptionField.getText();
			Double payment = 0.0;
			Double deposit = 0.0;
			
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
				displayErrorAlert("Please enter either a Payment OR Deposit amount");
				return;
			}
			//if deposit
			if(paymentAmountField.getText().isEmpty()) {
				try {
					deposit = Double.parseDouble(depositAmountField.getText());
				}
				catch (NumberFormatException e) {
					displayErrorAlert("Deposit must be a valid number");
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
					displayErrorAlert("Payment must be a valid number");
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

			displaySuccessAlert("Transaction successfully saved");
			
			
			
		}
		
		private void displayErrorAlert(String message) {
			Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
			alert.setTitle("Input Error");
			// remove default header
			alert.setHeaderText(null);
			// display the alert and wait for user interaction
			alert.showAndWait();
		}
		
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
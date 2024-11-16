package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.ScheduledTransBean;
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


public class CreateScheduledTransController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML private TextField scheduleName;
	@FXML private ComboBox<String> accountDropDown;
    @FXML private ComboBox<String> transactionTypeDropDown;
    @FXML private ComboBox<String> frequencyDropDown;
	@FXML private TextField dueDateField;
	@FXML private TextField paymentAmountField;
	@FXML private Button submit;
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
        
        // Hard code monthly into list
        frequencyDropDown.getItems().add("Monthly");
        frequencyDropDown.getSelectionModel().select("Monthly");
        
        // Fill values
        accountDropDown.getSelectionModel().selectFirst(); // shows first by default
        transactionTypeDropDown.getSelectionModel().selectFirst(); // shows first by default

    }
    
    @FXML public void submitOp() {
		DalInt dalInterface = new FileDal();
		
		String schedName = scheduleName.getText();
		String accName = accountDropDown.getSelectionModel().getSelectedItem();
		String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
		String frequency = frequencyDropDown.getSelectionModel().getSelectedItem();

		int dueDate = 0;
		Double paymentAmount = 0.0;
		
		//make sure completing all fields
		if(schedName.isEmpty()) {
			displayErrorAlert("Schedule name is required.");
			return;
		}
		if (accName == null) {
			displayErrorAlert("Account selection is required.");
			return;
		}
		
		if (transType == null) {
			displayErrorAlert("Transaction Type selection is required.");
			return;
		}
		
		if (frequency == null) {
			displayErrorAlert("Frequency is required.");
			return;
		}
		//check due date field to make sure its not empty and then turn it back into an integer
		if (dueDateField.getText().isEmpty()) {
		    displayErrorAlert("Due date is required.");
		    return;
		}
		try {
		    dueDate = Integer.parseInt(dueDateField.getText());
		} catch (NumberFormatException e) {
		    displayErrorAlert("Due date must be a valid number.");
		    dueDateField.clear();
		    return;
		}
		if (paymentAmountField.getText().isEmpty()) {
		    displayErrorAlert("Payment amount is required.");
		    return;
		}

		try {
		    paymentAmount = Double.parseDouble(paymentAmountField.getText());
		    if (paymentAmount <= 0) {
		        displayErrorAlert("Payment amount must be greater than zero.");
		        paymentAmountField.clear();
		        return;
		    }
		    paymentAmountField.setText(String.format("%.2f", paymentAmount));
		} catch (NumberFormatException e) {
		    displayErrorAlert("Payment amount must be a valid numeric value.");
		    paymentAmountField.clear();
		    return;
		}

        // Create ScheduledTransBean object to represent new scheduled transaction type
		ScheduledTransBean newScheduledTrans = new ScheduledTransBean(schedName, accName, transType, frequency, dueDate, paymentAmount);
        
        // Save account using DAL
        dalInterface.saveScheduledTrans(newScheduledTrans);
        
        // Set new account in CommonObjs to access most recently created account
        CommonObjs.getInstance().setScheduledTransBean(newScheduledTrans);

		displaySuccessAlert("Transaction successfully saved");
		clearFormOp();
	}

    @FXML public void clearFormOp() {
    	scheduleName.clear();
        dueDateField.clear();
        paymentAmountField.clear();
        accountDropDown.getSelectionModel().clearSelection();
        transactionTypeDropDown.getSelectionModel().clearSelection();
        frequencyDropDown.getSelectionModel().clearSelection();
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

package application.controller;

import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransTypeBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import view.AlertUtility;


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
			AlertUtility.displayErrorAlert("Schedule name is required.");
			return;
		}
		if (accName == null) {
			AlertUtility.displayErrorAlert("Account selection is required.");
			return;
		}
		
		if (transType == null) {
			AlertUtility.displayErrorAlert("Transaction Type selection is required.");
			return;
		}
		
		if (frequency == null) {
			AlertUtility.displayErrorAlert("Frequency is required.");
			return;
		}
		//check due date field to make sure its not empty and then turn it back into an integer
		if (dueDateField.getText().isEmpty()) {
			AlertUtility.displayErrorAlert("Due date is required.");
		    return;
		}
		try {
		    dueDate = Integer.parseInt(dueDateField.getText());
		} catch (NumberFormatException e) {
			AlertUtility.displayErrorAlert("Due date must be a valid number.");
		    dueDateField.clear();
		    return;
		}
		if (paymentAmountField.getText().isEmpty()) {
			AlertUtility.displayErrorAlert("Payment amount is required.");
		    return;
		}
		
		// Check for duplicate schedule name
		List<ScheduledTransBean> scheduleNames = dalInterface.loadScheduledTrans();
		for (ScheduledTransBean existingSchedule : scheduleNames) {
			try {
				if (existingSchedule.getScheduleName().equalsIgnoreCase(schedName)) {
					AlertUtility.displayErrorAlert("This schedule name already exists. Please enter a unique schedule name.");
					return;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
		    paymentAmount = Double.parseDouble(paymentAmountField.getText());
		    if (paymentAmount <= 0) {
		    	AlertUtility.displayErrorAlert("Payment amount must be greater than zero.");
		        paymentAmountField.clear();
		        return;
		    }
		    paymentAmountField.setText(String.format("%.2f", paymentAmount));
		} catch (NumberFormatException e) {
			AlertUtility.displayErrorAlert("Payment amount must be a valid numeric value.");
		    paymentAmountField.clear();
		    return;
		}

        // Create ScheduledTransBean object to represent new scheduled transaction type
		ScheduledTransBean newScheduledTrans = new ScheduledTransBean(schedName, accName, transType, frequency, dueDate, paymentAmount);
        
        // Save account using DAL
        dalInterface.saveScheduledTrans(newScheduledTrans);
        
        // Set new account in CommonObjs to access most recently created account
        CommonObjs.getInstance().setScheduledTransBean(newScheduledTrans);

        AlertUtility.displaySuccessAlert("Transaction successfully saved");
		clearFormOp();
	}

    @FXML public void clearFormOp() {
    	scheduleName.clear();
        dueDateField.clear();
        paymentAmountField.clear();
    }
    

}

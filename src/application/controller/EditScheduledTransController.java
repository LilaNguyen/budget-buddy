package application.controller;

import java.util.List;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransTypeBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import view.AlertUtility;

public class EditScheduledTransController {
	@FXML private ComboBox<String> accountDropDown;
    @FXML private ComboBox<String> transactionTypeDropDown;
    @FXML private ComboBox<String> frequencyDropDown;
    @FXML private TextField dueDateField;
    @FXML private TextField scheduleNameField;
    @FXML private TextField paymentAmountField;
    @FXML private Button save;
    @FXML private Button delete;
    @FXML private Button cancel;
    
    private int oldScheduledTransIndex;
    private ScheduledTransBean oldScheduledTrans;
    private DalInt dalInterface = new FileDal();
    private ObservableList<ScheduledTransBean> scheduledTransList;
    
    public void initialize() {
        System.out.println("Initializing the EditScheduledTransController...");
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
    }
   
    @FXML public void saveOp() {
    	String scheduleName = scheduleNameField.getText();
        String accountName = accountDropDown.getSelectionModel().getSelectedItem();
        String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
        String frequency = frequencyDropDown.getSelectionModel().getSelectedItem();
        String dueDateStr = dueDateField.getText();
        String paymentAmountStr = paymentAmountField.getText();
        
        // Validate inputs
        if (scheduleName == null || scheduleName.isEmpty()) {
        	AlertUtility.displayErrorAlert("Schedule Name is required.");
            return;
        }
        if (accountName == null) {
        	AlertUtility.displayErrorAlert("Account selection is required.");
            return;
        }
        if (transType == null) {
        	AlertUtility.displayErrorAlert("Transaction Type selection is required.");
            return;
        }
        if (frequency == null) {
        	AlertUtility.displayErrorAlert("Frequency selection is required.");
            return;
        }
        int dueDate;
        try {
            dueDate = Integer.parseInt(dueDateStr);
            if (dueDate < 1 || dueDate > 31) {
            	AlertUtility.displayErrorAlert("Due Date must be between 1 and 31.");
                return;
            }
        } catch (NumberFormatException e) {
        	AlertUtility.displayErrorAlert("Due Date must be a valid number.");
            return;
        }
        double paymentAmount;
        try {
            paymentAmount = Double.parseDouble(paymentAmountStr);
            if (paymentAmount <= 0) {
            	AlertUtility.displayErrorAlert("Payment Amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
        	AlertUtility.displayErrorAlert("Payment Amount must be a valid number.");
            return;
        }
        // Create new ScheduledTransBean with updated info
        ScheduledTransBean newScheduledTrans = new ScheduledTransBean(scheduleName, accountName, transType, frequency, dueDate, paymentAmount);
        // Update the list and save
        scheduledTransList.set(oldScheduledTransIndex, newScheduledTrans);
        dalInterface.saveAllScheduledTrans(scheduledTransList);
        AlertUtility.displaySuccessAlert("Changes successfully saved");
    }
    
    /*
	 * This method reverts any changes from the user back to the transaction's original entries.
	 */
	@FXML
	public void resetOp() {
		updateFields();
	}
   
    public void setScheduledTransList(ObservableList<ScheduledTransBean> scheduledTransList) {
        this.scheduledTransList = scheduledTransList;
    }
    
    public void setIndex(int oldScheduledTransIndex) {
        this.oldScheduledTransIndex = oldScheduledTransIndex;
        oldScheduledTrans = scheduledTransList.get(oldScheduledTransIndex);
        System.out.println("Edit Scheduled Transaction Index: " + oldScheduledTransIndex);
        updateFields();
    }
    
    private void updateFields() {
        scheduleNameField.setText(oldScheduledTrans.getScheduleName());
        accountDropDown.setValue(oldScheduledTrans.getAccount());
        transactionTypeDropDown.setValue(oldScheduledTrans.getTransType());
        frequencyDropDown.setValue(oldScheduledTrans.getFrequency());
        dueDateField.setText(String.valueOf(oldScheduledTrans.getDueDate()));
        paymentAmountField.setText(String.valueOf(oldScheduledTrans.getPaymentAmount()));
    }
}

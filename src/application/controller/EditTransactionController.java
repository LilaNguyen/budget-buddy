package application.controller;

import java.util.List;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransTypeBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditTransactionController {
    @FXML private ComboBox<String> accountDropDown;
    @FXML private ComboBox<String> transactionTypeDropDown;
    @FXML private ComboBox<String> frequencyDropDown;
    @FXML private TextField dueDateField;
    @FXML private TextField scheduleNameField;
    @FXML private TextField paymentAmountField;
    @FXML private Button save;
    @FXML private Button delete;
    @FXML private Button cancel;

    private int oldTransIndex;
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

        frequencyDropDown.getItems().addAll("Daily", "Weekly", "Monthly", "Yearly");
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
            displayErrorAlert("Schedule Name is required.");
            return;
        }
        if (accountName == null) {
            displayErrorAlert("Account selection is required.");
            return;
        }
        if (transType == null) {
            displayErrorAlert("Transaction Type selection is required.");
            return;
        }
        if (frequency == null) {
            displayErrorAlert("Frequency selection is required.");
            return;
        }
        int dueDate;
        try {
            dueDate = Integer.parseInt(dueDateStr);
            if (dueDate < 1 || dueDate > 31) {
                displayErrorAlert("Due Date must be between 1 and 31.");
                return;
            }
        } catch (NumberFormatException e) {
            displayErrorAlert("Due Date must be a valid number.");
            return;
        }
        double paymentAmount;
        try {
            paymentAmount = Double.parseDouble(paymentAmountStr);
            if (paymentAmount <= 0) {
                displayErrorAlert("Payment Amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            displayErrorAlert("Payment Amount must be a valid number.");
            return;
        }

        // Create new ScheduledTransBean with updated info
        ScheduledTransBean newScheduledTrans = new ScheduledTransBean(scheduleName, accountName, transType, frequency, dueDate, paymentAmount);

        // Update the list and save
        scheduledTransList.set(oldTransIndex, newScheduledTrans);
        dalInterface.saveAllScheduledTrans(scheduledTransList);
        displaySuccessAlert("Changes successfully saved");
        clearFormOp();
    }

    @FXML public void deleteOp() {
        // Confirm deletion
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this scheduled transaction?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            scheduledTransList.remove(oldTransIndex);
            dalInterface.saveAllScheduledTrans(scheduledTransList);
            displaySuccessAlert("Scheduled transaction deleted successfully.");
            clearFormOp();
        }
    }

    @FXML public void cancelOp() {
        // Logic to handle cancel operation, e.g., close the window or clear form
        clearFormOp();
    }

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML public void clearFormOp() {
        scheduleNameField.clear();
        accountDropDown.getSelectionModel().clearSelection();
        transactionTypeDropDown.getSelectionModel().clearSelection();
        frequencyDropDown.getSelectionModel().clearSelection();
        dueDateField.clear();
        paymentAmountField.clear();
    }

    public void setScheduledTransList(ObservableList<ScheduledTransBean> scheduledTransList) {
        this.scheduledTransList = scheduledTransList;
    }

    public void setIndex(int oldTransIndex) {
        this.oldTransIndex = oldTransIndex;
        oldScheduledTrans = scheduledTransList.get(oldTransIndex);
        System.out.println("Edit Scheduled Transaction Index: " + oldTransIndex);
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
package application.controller;

import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransTypeBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType; // Import AlertType
import javafx.scene.control.ButtonType; // Import ButtonType
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EditTransactionController {
    @FXML private ComboBox<String> accountDropDown;
    @FXML private ComboBox<String> transactionTypeDropDown;
    @FXML private TextField dueDateField;
    @FXML private TextField scheduleNameField;
    @FXML private TextField paymentAmountField;

    private int oldTransIndex;
    private ScheduledTransBean oldScheduledTrans;
    private DalInt dalInterface = new FileDal();
    private ObservableList<ScheduledTransBean> scheduledTransList;
    private CommonObjs commonObjs = CommonObjs.getInstance();

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

        // Frequency is always "Monthly" for scheduled transactions
        // No frequency dropdown is needed
    }

    @FXML public void saveOp() {
        String scheduleName = scheduleNameField.getText();
        String accountName = accountDropDown.getSelectionModel().getSelectedItem();
        String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
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
        if (dueDateStr == null || dueDateStr.isEmpty()) {
            displayErrorAlert("Due Date is required.");
            return;
        }
        int dueDate;
        try {
            dueDate = Integer.parseInt(dueDateStr);
        } catch (NumberFormatException e) {
            displayErrorAlert("Due Date must be a valid number.");
            return;
        }
        if (paymentAmountStr == null || paymentAmountStr.isEmpty()) {
            displayErrorAlert("Payment Amount is required.");
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
        ScheduledTransBean newScheduledTrans = new ScheduledTransBean(
            scheduleName, accountName, transType, "Monthly", dueDate, paymentAmount);

        // Update the list and save
        scheduledTransList.set(oldTransIndex, newScheduledTrans);
        dalInterface.saveAllScheduledTrans(scheduledTransList);
        displaySuccessAlert("Changes successfully saved");

        // Clear form or navigate back to the previous view
        cancelOp();
    }

    @FXML public void cancelOp() {
        // Logic to handle cancel operation, consistent with other edit controllers
        // For example, navigate back to the View Scheduled Transactions page
        HBox mainBox = commonObjs.getMainBox();

        if (mainBox.getChildren().size() > 1) {
            mainBox.getChildren().remove(1);
        }

        // Optionally, you can load the ViewScheduledTrans.fxml to replace the current pane
        // URL url = getClass().getClassLoader().getResource("view/ViewScheduledTrans.fxml");
        // Load and display the view as needed
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
        dueDateField.setText(String.valueOf(oldScheduledTrans.getDueDate()));
        paymentAmountField.setText(String.valueOf(oldScheduledTrans.getPaymentAmount()));
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
}
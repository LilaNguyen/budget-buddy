package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ViewTransactionsController {
    @FXML private ComboBox<String> accountDropDown;
    @FXML private ComboBox<String> transactionTypeDropDown;
    @FXML private DatePicker transactionDatePicker;
    @FXML private TextField descriptionField;
    @FXML private TextField paymentAmountField;
    @FXML private TextField depositAmountField;
    @FXML private Button save;
    @FXML private Button cancel;
    
    private int oldTransIndex;
    private TransBean oldTransaction;
    private DalInt dalInterface = new FileDal();
    private ObservableList<TransBean> transactionList;
    private CommonObjs commonObjs = CommonObjs.getInstance();
   
    public void initialize() {
        System.out.println("Initializing the EditTransactionController...");
        List<AccountBean> accounts = dalInterface.loadAccounts();
        List<TransTypeBean> types = dalInterface.loadTransTypes();
       
        for (AccountBean bean : accounts) {
            accountDropDown.getItems().add(bean.getAccountName());
        }
       
        for (TransTypeBean bean : types) {
            transactionTypeDropDown.getItems().add(bean.getTransTypeName());
        }
    }
   
    @FXML public void saveOp() {
        // Your save operation code
    }
    
    @FXML public void cancelOp() {
        // Your cancel operation code
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
        // Your clear form code
    }
    
    // **Ensure this method is present and correctly defined**
    public void setTransactionList(ObservableList<TransBean> transactionList) {
        this.transactionList = transactionList;
    }
    
    public void setIndex(int oldTransIndex) {
        this.oldTransIndex = oldTransIndex;
        oldTransaction = transactionList.get(oldTransIndex);
        System.out.println("Edit Index: " + oldTransIndex);
        updateFields();
    }
    
    private void updateFields() {
        accountDropDown.setValue(oldTransaction.getAccount());
        transactionTypeDropDown.setValue(oldTransaction.getTransType());
        transactionDatePicker.setValue(oldTransaction.getTransDate());
        descriptionField.setText(oldTransaction.getDescription());
        if(oldTransaction.getPaymentAmount() != 0) { 
            paymentAmountField.setText(String.valueOf(oldTransaction.getPaymentAmount())); 
        }
        if(oldTransaction.getDepositAmount() != 0) { 
            depositAmountField.setText(String.valueOf(oldTransaction.getDepositAmount())); 
        }
    }
}
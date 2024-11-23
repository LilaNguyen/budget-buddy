package application.controller;
import java.time.LocalDate;
import java.util.List;
import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class EditTransactionController {
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
       
   }
  
	@FXML public void saveOp() {
		// chat am I cooking?
		String accName = accountDropDown.getSelectionModel().getSelectedItem();
		String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
		LocalDate transDate = transactionDatePicker.getValue();
      
		String descr = descriptionField.getText();
		Double payment = 0.0;
		Double deposit = 0.0;
		
		//make sure completing all fields
		if (accName == null) {
			displayErrorAlert("Account selection is required.");
			return;
		}
		
		if (transType == null) {
			displayErrorAlert("Transaction Type selection is required.");
			return;
		}
		
		if (transDate == null) {
			displayErrorAlert("Transaction Date is required.");
			return;
		}
		
		if (descr.isEmpty()) {
			displayErrorAlert("Transaction Description is required.");
			return;
		}
		
		//make sure entered either payment or deposit
		if(paymentAmountField.getText().isEmpty() && depositAmountField.getText().isEmpty()) {
			displayErrorAlert("Payment or deposit amount required.");
			return;
		}
		//not both
		if(!paymentAmountField.getText().isEmpty() && !depositAmountField.getText().isEmpty()) {
			displayErrorAlert("Enter either a payment OR deposit amount.");
			return;
		}
		//if deposit
		if(paymentAmountField.getText().isEmpty()) {
			try {
				deposit = Double.parseDouble(depositAmountField.getText());
			}
			catch (NumberFormatException e) {
				displayErrorAlert("Deposit must be a valid number.");
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
				displayErrorAlert("Payment must be a valid number.");
				// clear field for new input
				paymentAmountField.clear();
				return;
			}
			paymentAmountField.setText(String.format("%.2f", payment));
		}
       // Create TransTypeBean object to represent new transaction type
		TransBean newTrans = new TransBean(accName, transType, transDate, descr, payment, deposit);
      
       // Save account using DAL
		transactionList.set(oldTransIndex, newTrans);
		dalInterface.saveAllTransactions(transactionList);
		displaySuccessAlert("Changes successfully saved");
		clearFormOp();
	}
	@FXML public void cancelOp() {
		// To be implemented
		// NOTE: would be the same as not clicking save so maybe not necessary
		// Maybe return to View Trans page is better
		updateFields();
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
	@FXML public void clearFormOp() {

	}
	
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
	    if(oldTransaction.getPaymentAmount() != 0) { paymentAmountField.setText("" + oldTransaction.getPaymentAmount()); }
	    else {paymentAmountField.setText(""); }
	    if(oldTransaction.getDepositAmount() != 0) { depositAmountField.setText("" + oldTransaction.getDepositAmount()); }
	    else {depositAmountField.setText(""); }
	}
		
}

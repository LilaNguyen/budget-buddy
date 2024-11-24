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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import view.AlertUtility;

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

	@FXML
	public void saveOp() {
		String accName = accountDropDown.getSelectionModel().getSelectedItem();
		String transType = transactionTypeDropDown.getSelectionModel().getSelectedItem();
		LocalDate transDate = transactionDatePicker.getValue();

		String descr = descriptionField.getText();
		Double payment = 0.0;
		Double deposit = 0.0;

		// make sure completing all fields
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

		// make sure entered either payment or deposit
		if (paymentAmountField.getText().isEmpty() && depositAmountField.getText().isEmpty()) {
			AlertUtility.displayErrorAlert("Payment or deposit amount required.");
			return;
		}
		// not both
		if (!paymentAmountField.getText().isEmpty() && !depositAmountField.getText().isEmpty()) {
			AlertUtility.displayErrorAlert("Enter either a payment OR deposit amount.");
			return;
		}
		// if deposit
		if (paymentAmountField.getText().isEmpty()) {
			try {
				deposit = Double.parseDouble(depositAmountField.getText());
			} catch (NumberFormatException e) {
				AlertUtility.displayErrorAlert("Deposit must be a valid number.");
				// clear field for new input
				depositAmountField.clear();
				return;
			}
			depositAmountField.setText(String.format("%.2f", deposit));
		}
		// if payment
		if (depositAmountField.getText().isEmpty()) {
			try {
				payment = Double.parseDouble(paymentAmountField.getText());
			} catch (NumberFormatException e) {
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
		transactionList.set(oldTransIndex, newTrans);
		dalInterface.saveAllTransactions(transactionList);
		AlertUtility.displaySuccessAlert("Changes successfully saved");
	}

	/*
	 * This method reverts any changes from the user back to the transaction's original entries.
	 */
	@FXML
	public void resetOp() {
		updateFields();
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
		if (oldTransaction.getPaymentAmount() != 0) {
			paymentAmountField.setText("" + oldTransaction.getPaymentAmount());
		} else {
			paymentAmountField.setText("");
		}
		if (oldTransaction.getDepositAmount() != 0) {
			depositAmountField.setText("" + oldTransaction.getDepositAmount());
		} else {
			depositAmountField.setText("");
		}
	}

}

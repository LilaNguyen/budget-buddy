package application.controller;

import application.CommonObjs;
import application.model.TransBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ViewTransactionDetailsController {
	@FXML ComboBox<String> accountDropDown;
	@FXML ComboBox<String> transactionTypeDropDown;
	@FXML DatePicker transactionDatePicker;
	@FXML TextField descriptionField;
	@FXML TextField paymentAmountField;
	@FXML TextField depositAmountField;

	private ObservableList<TransBean> transactionList;
	private CommonObjs commonObjs = CommonObjs.getInstance();

	/**
	 * Sets the transaction data to populate the labels.
	 * 
	 * @param transaction The selected transaction to display.
	 */
	public void setTransactionData(TransBean trans) {
		accountDropDown.setValue(trans.getAccount());
		transactionTypeDropDown.setValue(trans.getTransType());
		transactionDatePicker.setValue(trans.getTransDate());
		descriptionField.setText(trans.getDescription());
		paymentAmountField.setText(String.valueOf(trans.getPaymentAmount()));
		depositAmountField.setText(String.valueOf(trans.getDepositAmount()));
		
		// read-only
		accountDropDown.setDisable(true);
		transactionTypeDropDown.setDisable(true);
		transactionDatePicker.setDisable(true);
		descriptionField.setDisable(true);
		paymentAmountField.setDisable(true);
		depositAmountField.setDisable(true);
	}
	
	/**
	 * Handles the Back button to navigate back to the originating page.
	 */
	@FXML
	public void goBackOp() {
		try {
			commonObjs.loadPage("view/ViewReports.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setTransactionList(ObservableList<TransBean> transactionList) {
		this.transactionList = transactionList;
	}

	public void setIndex(int oldTransIndex) {
		if (oldTransIndex >= 0 && oldTransIndex < transactionList.size()) {
			TransBean oldTrans = transactionList.get(oldTransIndex);
			setTransactionData(oldTrans);
		}
	}
}

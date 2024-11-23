package application.controller;

import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.TransTypeBean;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import view.AlertUtility;

public class CreateTransTypeController {

	@FXML TextField transTypeField;

	@FXML public void enterTransTypeOp() {}

	@FXML public void submitOp() {
		DalInt dalInterface = new FileDal();

		String transType = transTypeField.getText();
		
        // Perform validation
        if (transType == null || transType.trim().isEmpty()) {
        	AlertUtility.displayErrorAlert("Transaction type's name is required.");
            return;
        }
        
		// Check for duplicate transaction type
		List<TransTypeBean> types = dalInterface.loadTransTypes();
		for (TransTypeBean existingType : types) {
			try {
				if (existingType.getTransTypeName().equalsIgnoreCase(transType)) {
					AlertUtility.displayErrorAlert("This transaction type's name already exists. Please enter a unique name.");
					return;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        // Create TransTypeBean object to represent new transaction type
		TransTypeBean newTransType = new TransTypeBean(transType);
        
        // Save account using DAL
        dalInterface.saveTransType(newTransType);
        
        // Set new account in CommonObjs to access most recently created account
        CommonObjs.getInstance().setTransTypeBean(newTransType);
        
        AlertUtility.displaySuccessAlert("Transaction type successfully created.");
        clearFormOp();
	}

	/**
	 * This method clears the field for "Transaction type's name."
	 */
	@FXML public void clearFormOp() {
		transTypeField.clear();
	}

}

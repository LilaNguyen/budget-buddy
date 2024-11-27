package application.controller;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML HBox mainBox;
	private static CommonObjs commonObj = CommonObjs.getInstance();

	@FXML
	public void initialize() {
		commonObj.setMainBox(mainBox);
		returnToHomepageOp();
	}

	@FXML public void returnToHomepageOp() {
		commonObj.loadPage("view/Homepage.fxml");
	}
	
	@FXML public void showNewAccOp() {
		commonObj.loadPage("view/CreateAccount.fxml");
	}

	
	@FXML public void showViewAccsOp() {
		commonObj.loadPage("view/ViewAccounts.fxml");
	}
	
	@FXML public void showViewTransOp() {
		commonObj.loadPage("view/ViewTransactions.fxml");

	}
	
	@FXML public void showNewTransOp() {
		commonObj.loadPage("view/CreateTransaction.fxml");
	}
	
	@FXML public void showScheduleTransOp() {
		commonObj.loadPage("view/CreateScheduledTrans.fxml");
	}

	@FXML public void showViewScheduledTransOp() {
		commonObj.loadPage("view/ViewScheduledTrans.fxml");
	}

	@FXML public void showTransTypeOp() {
		commonObj.loadPage("view/CreateTransType.fxml");
	}


	
}

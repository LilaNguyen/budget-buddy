package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CreateAccountController {
	// Need a reference of CommonObjs objects here
	private CommonObjs commonObjs = CommonObjs.getInstance();

	@FXML public void enterAccNameOp() {}

	@FXML public void enterOpeningDateOp() {}

	@FXML public void enterOpeningBalOp() {}

	@FXML public void submitOp() {}

	@FXML public void cancelOp() {}

	
}

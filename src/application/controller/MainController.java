package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML HBox mainBox;

	@FXML
	public void initialize() {
		returnToHomepageOp();
	}

	@FXML public void returnToHomepageOp() {
		URL url = getClass().getClassLoader().getResource("view/Homepage.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void showNewAccOp() {
		URL url = getClass().getClassLoader().getResource("view/CreateAccount.fxml");
		
		try {
			AnchorPane pane2 = (AnchorPane) FXMLLoader.load(url);
			// before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * To be implemented at a later date.
	 */
	@FXML public void showViewAccsOp() {}

	/*
	 * To be implemented at a later date.
	 */
	@FXML public void showNewTransOp() {}

	/*
	 * To be implemented at a later date.
	 */
	@FXML public void showScheduledTransOp() {}

	/*
	 * To be implemented at a later date.
	 */
	@FXML public void showTransTypeOp() {}
	
}

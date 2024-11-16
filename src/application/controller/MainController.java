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
		// Create url to get fxml file 
		URL url = getClass().getClassLoader().getResource("view/Homepage.fxml");
		
		try {
			// Read the fxml file, then convert to AnchorPane
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			// Before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			// Get children of root pane manager, then add new pane to it
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

	
	@FXML public void showViewAccsOp() {
		URL url = getClass().getClassLoader().getResource("view/ViewAccounts.fxml");
		
		try {
			AnchorPane pane3 = (AnchorPane) FXMLLoader.load(url);
			// before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane3);
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void showViewTransOp() {
		URL url = getClass().getClassLoader().getResource("view/ViewTransactions.fxml");
		
		try {
			AnchorPane pane4 = (AnchorPane) FXMLLoader.load(url);
			// before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane4);
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void showNewTransOp() {
		URL url = getClass().getClassLoader().getResource("view/CreateTransaction.fxml");
		
		try {
			AnchorPane pane5 = (AnchorPane) FXMLLoader.load(url);
			// before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane5);
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void showScheduleTransOp() {
		URL url = getClass().getClassLoader().getResource("view/CreateScheduledTrans.fxml");
		
		try {
			AnchorPane pane6 = (AnchorPane) FXMLLoader.load(url);
			// before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane6);
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML public void showViewScheduledTransOp() {
		URL url = getClass().getClassLoader().getResource("view/ViewScheduledTrans.fxml");
		
		try {
			AnchorPane pane7 = (AnchorPane) FXMLLoader.load(url);
			// before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane7);
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML public void showTransTypeOp() {
		URL url = getClass().getClassLoader().getResource("view/CreateTransType.fxml");
		
		try {
			AnchorPane pane8 = (AnchorPane) FXMLLoader.load(url);
			// Before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane8);
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	
}

package application.controller;

import java.time.LocalDate;

import application.FileDal;
import application.FileDal.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewAccountsController {
	@FXML private TableView<Account> accountTable;
    @FXML private TableColumn<Account, String> nameColumn;
    @FXML private TableColumn<Account, Double> balanceColumn;
    @FXML private TableColumn<Account, LocalDate> transactionColumn;

    private FileDal fileDal = new FileDal();
    private ObservableList<Account> accountList;
    
    @FXML
    public void initialize() {
        // Set up the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        transactionColumn.setCellValueFactory(new PropertyValueFactory<>("lastTransaction"));

        // Load accounts from FileDal and populate the table
        accountList = FXCollections.observableArrayList(fileDal.LoadAccounts());
        accountTable.setItems(accountList);

        // Add some sample data for testing
        addSampleData();
    }
    private void addSampleData() {
        fileDal.saveAccount("123", "John Doe", 1000.0);
        fileDal.saveAccount("456", "Jane Smith", 2500.0);
        accountList.setAll(fileDal.LoadAccounts());  // Refresh the table with new data
    }
}
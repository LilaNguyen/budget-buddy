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
	// Table and the columns
    @FXML private TableView<Account> accountTable;
    @FXML private TableColumn<Account, String> nameColumn;
    @FXML private TableColumn<Account, Double> balanceColumn;
    @FXML private TableColumn<Account, LocalDate> transactionColumn;

    // fileDal
    private FileDal fileDal = new FileDal();
    private ObservableList<Account> accountList;

    @FXML
    public void initialize() {
        // Set up the columns MAKE SURE NAMES MATCH
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        transactionColumn.setCellValueFactory(new PropertyValueFactory<>("lastTransaction"));

        // Initialize the list and table
        accountList = FXCollections.observableArrayList(fileDal.LoadAccounts());
        accountTable.setItems(accountList);
        
        // Sort the data by opening date
        transactionColumn.setSortType(TableColumn.SortType.DESCENDING);
        accountTable.getSortOrder().add(transactionColumn);

        // Add sample data
        addSampleData();
    }

    private void addSampleData() {
    	// Test data and Refresh the table
        fileDal.saveAccount("John Doe", 1000.0, LocalDate.now().minusDays(5));
        fileDal.saveAccount("Jane Smith", 2500.0, LocalDate.now().minusDays(2));
        
        accountList.setAll(fileDal.LoadAccounts());  
        accountTable.sort();
    }
}
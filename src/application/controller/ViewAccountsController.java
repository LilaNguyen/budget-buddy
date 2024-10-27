package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.interfaces.FileDal;
import application.model.AccountBean;
import application.model.DalInt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewAccountsController {
	// Table and the columns
    @FXML private TableView<AccountBean> accountTable;
    @FXML private TableColumn<AccountBean, String> nameColumn;
    @FXML private TableColumn<AccountBean, Double> balanceColumn;
    @FXML private TableColumn<AccountBean, LocalDate> transactionColumn;

    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    // Make it AccountBean
    private ObservableList<AccountBean> accountList;

    @FXML
    public void initialize() {
        // Set up the columns - MAKE SURE NAMES MATCH
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));
        transactionColumn.setCellValueFactory(new PropertyValueFactory<>("openingDate"));

        // Initialize the list and table
        accountList = FXCollections.observableArrayList(dalInterface.loadAccounts());
        accountTable.setItems(accountList);
        
        // For Debugging-- make sure data is being loaded
        System.out.println("Loaded accounts: " + accountList.size());
    }

    /*
    private void addSampleData() {
    	// Test data and Refresh the table
        fileDal.saveAccount("John Doe", 1000.0, LocalDate.now().minusDays(5));
        fileDal.saveAccount("Jane Smith", 2500.0, LocalDate.now().minusDays(2));
        accountList.setAll(fileDal.loadAccounts());  
    }
    */
}
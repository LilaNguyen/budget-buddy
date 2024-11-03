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
        
        // Sort the data by opening date
        transactionColumn.setSortType(TableColumn.SortType.DESCENDING);
        accountTable.getSortOrder().add(transactionColumn);

        // Add sample data
        addSampleData();
    }

    
    private void addSampleData() {
    	//Refresh the table
    	
        accountList.setAll(dalInterface.loadAccounts());
        accountTable.refresh();
        accountTable.sort();
    }
}
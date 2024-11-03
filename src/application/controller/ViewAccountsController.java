package application.controller;

import java.time.LocalDate;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
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
    @FXML private TableColumn<AccountBean, LocalDate> openingDateColumn;

    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    // Make it AccountBean
    private ObservableList<AccountBean> accountList;

    @FXML
    public void initialize() {
        // Set up the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));
        openingDateColumn.setCellValueFactory(new PropertyValueFactory<>("openingDate"));

        // Initialize the list and table
        accountList = FXCollections.observableArrayList(dalInterface.loadAccounts());
        accountTable.setItems(accountList);
        
<<<<<<< HEAD
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
=======
        // Sort the data by opening date in descending order
        openingDateColumn.setSortType(TableColumn.SortType.DESCENDING);
        accountTable.getSortOrder().add(openingDateColumn);
        
        // Sort table
        accountTable.sort();
        
        // For Debugging-- make sure data is being loaded
        System.out.println("Loaded accounts: " + accountList.size());
    }
    
>>>>>>> 1f1f8c2c8269b5750aa0a49f25ede40388abfd7e
}
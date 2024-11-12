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

public class ViewTransactionsController {
	// Table and the columns
    @FXML private TableView<AccountBean> transactionTable;
    @FXML private TableColumn<AccountBean, String> accountColumn;
    @FXML private TableColumn<AccountBean, String> typeColumn;
    @FXML private TableColumn<AccountBean, LocalDate> dateColumn;
    @FXML private TableColumn<AccountBean, String> descriptionColumn;
    @FXML private TableColumn<AccountBean, Double> amountColumn;
    
    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    // Make it AccountBean
    
    //private ObservableList<TransactionBean> transactionList;
    
    @FXML
    public void initialize() {
        // Set up the columns MAKE SURE NAMES MATCH GET/SET METHODS
    	accountColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
    	typeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<>("latestDate"));
    	descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    	//not sure if best way to incorporate deposit/payment
    	amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));	

        // Initialize the list and table
    	
    	// transactionList = FXCollections.observableArrayList(dalInterface.loadTransactions());
    	//transactionTable.setItems(transactionList);

        // Sort the data by opening date in descending order
    	dateColumn.setSortType(TableColumn.SortType.DESCENDING);
        transactionTable.getSortOrder().add(dateColumn);
        
        // Sort table
        transactionTable.sort();
        
        // For Debugging-- make sure data is being loaded
        //System.out.println("Loaded accounts: " + transactionList.size());
    }
    
}

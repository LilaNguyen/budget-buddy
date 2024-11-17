package application.controller;

import java.time.LocalDate;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.TransBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import view.TableUtility;

public class ViewTransactionsController {
	// Table and the columns
    @FXML private TableView<TransBean> transactionTable;
    @FXML private TableColumn<TransBean, String> accountColumn;
    @FXML private TableColumn<TransBean, String> typeColumn;
    @FXML private TableColumn<TransBean, LocalDate> dateColumn;
    @FXML private TableColumn<TransBean, String> descriptionColumn;
    @FXML private TableColumn<TransBean, Double> paymentAmountColumn;
    @FXML private TableColumn<TransBean, Double> depositAmountColumn;

    
    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    
    private ObservableList<TransBean> transactionList;
    
    @FXML
    public void initialize() {
        // Set up the columns MAKE SURE NAMES MATCH GET/SET METHODS
    	accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
    	typeColumn.setCellValueFactory(new PropertyValueFactory<>("transType"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<>("transDate"));
    	descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    	paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));	
    	depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));	
    	
    	// Enable text wrapping for respective column
    	TableUtility.setTextWrappingForColumn(accountColumn);
    	TableUtility.setTextWrappingForColumn(typeColumn);
    	TableUtility.setTextWrappingForColumn(descriptionColumn);
    	
        // Initialize the list and table
    	transactionList = FXCollections.observableArrayList(dalInterface.loadTransactions());
    	transactionList.forEach(trans -> System.out.println("Payment: " + trans.getPaymentAmount() + ", Deposit: " + trans.getDepositAmount()));

    	transactionTable.setItems(transactionList);

        // Sort the data by opening date in descending order
    	dateColumn.setSortType(TableColumn.SortType.DESCENDING);
        transactionTable.getSortOrder().add(dateColumn);
        
        // Sort table
        transactionTable.sort();
        
        // For Debugging-- make sure data is being loaded
        System.out.println("Loaded accounts: " + transactionList.size());
    }
    
}

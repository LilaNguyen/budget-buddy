package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.TransBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    @FXML private TextField TransactionsSearchBar;
    
    private DalInt dalInterface = new FileDal();
    private ObservableList<TransBean> transactionList;
    private CommonObjs commonObjs = CommonObjs.getInstance();
    
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
        
        // Initialize search-- will show all items until user starts a search
        searchOp();
    }

	@FXML public void showEditTransOp() {
		URL url = getClass().getClassLoader().getResource("view/EditTransaction.fxml");

		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			HBox mainBox = commonObjs.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
		
			mainBox.getChildren().add(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void searchOp() {
		FilteredList<TransBean> filteredTrans = new FilteredList<>(transactionList);
		transactionTable.setItems(filteredTrans); // table will show filtered list
		
		// Observe changes in search input
		TransactionsSearchBar.textProperty().addListener((observable, previousText, currentText) -> {
			filteredTrans.setPredicate(TransBean -> {
				
				// If search input is empty, show all results
				if (currentText == null || currentText.isEmpty()) {
					return true;
				}
				
				String searchedDescription = currentText.toLowerCase();
				// Check if description contains search input
				return TransBean.getDescription().toLowerCase().contains(searchedDescription);
			});
		});
	}
	
	@FXML public void deleteOp() {
		try {
			// Get selected transaction
			TransBean selectedTrans = transactionTable.getSelectionModel().getSelectedItem();
			
			if (selectedTrans != null) {
				// Remove transaction from list and table
				transactionList.remove(selectedTrans);
				dalInterface.deleteTransaction(selectedTrans);
				
				// For debugging
				System.out.println("Transaction deleted: " + selectedTrans.toString());
			}
			else {
				System.out.println("To delete, a transaction must be selected first.");
			}
		}
		catch (NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}
    
}

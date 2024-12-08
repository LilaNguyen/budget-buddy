package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.ScheduledTransBean;
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
import view.AlertUtility;
import view.TableUtility;

public class ViewScheduledTransController {
    // Table and columns
    @FXML private TableView<ScheduledTransBean> scheduledTransTable;
    @FXML private TableColumn<ScheduledTransBean, String> scheduleNameColumn;
    @FXML private TableColumn<ScheduledTransBean, String> accountColumn;
    @FXML private TableColumn<ScheduledTransBean, String> typeColumn;
    @FXML private TableColumn<ScheduledTransBean, String> frequencyColumn;
    @FXML private TableColumn<ScheduledTransBean, Integer> dueDateColumn;
    @FXML private TableColumn<ScheduledTransBean, Double> paymentAmountColumn;
    @FXML private TextField ScheduledTransSearchBar;

    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    private ObservableList<ScheduledTransBean> scheduledTransList;
    private CommonObjs commonObjs = CommonObjs.getInstance();

    @FXML
    public void initialize() {
        // Set up the columns to match the ScheduledTransBean fields
    	scheduleNameColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transType"));
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        
        // Enable text wrapping for respective columns
        TableUtility.setTextWrappingForColumn(scheduleNameColumn);
        TableUtility.setTextWrappingForColumn(accountColumn);
        TableUtility.setTextWrappingForColumn(typeColumn);
        TableUtility.setTextWrappingForColumn(frequencyColumn);
        
        // Initialize the list and table
        scheduledTransList = FXCollections.observableArrayList(dalInterface.loadScheduledTrans());
        scheduledTransList.forEach(payment -> System.out.println("Payment: " + payment.getPaymentAmount()));
        scheduledTransTable.setItems(scheduledTransList);

        // Sort the data by opening date in ascending order
    	dueDateColumn.setSortType(TableColumn.SortType.ASCENDING);
    	scheduledTransTable.getSortOrder().add(dueDateColumn);
        scheduledTransTable.sort();
        
        // For Debugging-- make sure data is being loaded
        System.out.println("Loaded scheduled transactions: " + scheduledTransList.size());
        
        // Initialize search-- will show all items until user starts a search
        searchOp();
    }


	@FXML public void searchOp() {
		FilteredList<ScheduledTransBean> filteredScheduledTrans = new FilteredList<>(scheduledTransList);
		scheduledTransTable.setItems(filteredScheduledTrans); // table will show filtered list
		
		// Observe changes in search input
		ScheduledTransSearchBar.textProperty().addListener((observable, previousText, currentText) -> {
			filteredScheduledTrans.setPredicate(ScheduledTransBean -> {
				
				// If search input is empty, show all results
				if (currentText == null || currentText.isEmpty()) {
					return true;
				}
				
				String searchedName = currentText.toLowerCase();
				
				// Check if schedule name contains search input
				return ScheduledTransBean.getScheduleName().toLowerCase().contains(searchedName);
			});
		});
	}
	
	@FXML public void showEditScheduledTransOp() {
		URL url = getClass().getClassLoader().getResource("view/EditScheduledTrans.fxml");
		try {
			FXMLLoader loader = new FXMLLoader(url);
	        AnchorPane pane = loader.load();
	       
	        EditScheduledTransController controller = loader.getController();
	        
	        ScheduledTransBean selectedTransaction = scheduledTransTable.getSelectionModel().getSelectedItem();        
	        if (selectedTransaction != null) {
	        	int selectedIndex = findTransactionIndexHelper(selectedTransaction);
	        	controller.setScheduledTransList(scheduledTransList);
	        	controller.setIndex(selectedIndex);
	        	System.out.println("Selected Index: " + selectedIndex);
	        	
	        	scheduledTransTable.getSelectionModel().clearSelection(); // Clear selection
	        	
	        	HBox mainBox = commonObjs.getMainBox();
				
				if (mainBox.getChildren().size() > 1) {
					mainBox.getChildren().remove(1);
				}
			
				mainBox.getChildren().add(pane);
	        }
	        else {
	        	AlertUtility.displayErrorAlert("A scheduled transaction must be selected first.");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private int findTransactionIndexHelper(ScheduledTransBean selected) {
    	int index = 0;
    	for (ScheduledTransBean schedBean : scheduledTransList) {
    		if(selected.equals(schedBean)) {
        		System.out.println("Index in transactionList: " + index);
        		return index; 
        	}
    		index++;
    	}
    	return -1;
    }
	
	@FXML public void deleteOp() {
		// Check if scheduled transaction is selected
		ScheduledTransBean selectedScheduledTrans = scheduledTransTable.getSelectionModel().getSelectedItem();
		if (selectedScheduledTrans == null) { 
			AlertUtility.displayErrorAlert("No scheduled transaction selected to delete.");
			return;
		}
		
		if (AlertUtility.displayDeleteConfirmationAlert()) {
			try {
				// Remove scheduled transaction from list and table
				scheduledTransList.remove(selectedScheduledTrans);
				dalInterface.deleteScheduledTrans(selectedScheduledTrans);
				
				// For debugging
				System.out.println("Scheduled transaction deleted: " + selectedScheduledTrans.toString());
				
				AlertUtility.displaySuccessAlert("Scheduled transaction successfully deleted.");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			AlertUtility.displaySuccessAlert("Scheduled transaction deletion canceled.");
		}
	}
	
	@FXML public void showViewSchedReportsOp() {
		commonObjs.loadPage("view/ViewScheduledReports.fxml");
	}
    
}

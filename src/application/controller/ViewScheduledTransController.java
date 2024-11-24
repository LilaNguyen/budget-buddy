package application.controller;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.ScheduledTransBean;
import application.model.TransBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TextField TransactionsSearchBar;

    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    private ObservableList<ScheduledTransBean> scheduledTransList;

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
    }


	@FXML public void searchOp() {
		FilteredList<ScheduledTransBean> filteredScheduledTrans = new FilteredList<>(scheduledTransList);
		scheduledTransTable.setItems(filteredScheduledTrans); // table will show filtered list
		
		// Observe changes in search input
		TransactionsSearchBar.textProperty().addListener((observable, previousText, currentText) -> {
			filteredScheduledTrans.setPredicate(TransBean -> {
				
				// If search input is empty, show all results
				if (currentText == null || currentText.isEmpty()) {
					return true;
				}
				
				String searchedScheduleName = currentText.toLowerCase();
				// Check if description contains search input
				return TransBean.getScheduleName().toLowerCase().contains(searchedScheduleName);
			});
		});
	}
	
	@FXML public void deleteOp() {
		try {
			// Get selected scheduled transaction
			ScheduledTransBean selectedScheduledTrans = scheduledTransTable.getSelectionModel().getSelectedItem();
			
			if (selectedScheduledTrans != null) {
				// Remove scheduled transaction from list and table
				scheduledTransList.remove(selectedScheduledTrans);
				dalInterface.deleteScheduledTrans(selectedScheduledTrans);
				
				// For debugging
				System.out.println("Scheduled transaction deleted: " + selectedScheduledTrans.toString());
			}
			else {
				System.out.println("To delete, a scheduled transaction must be selected first.");
			}
		}
		catch (NullPointerException e) {
			System.out.print(e.getMessage());
		}
	}
}

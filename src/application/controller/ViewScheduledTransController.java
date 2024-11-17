package application.controller;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.ScheduledTransBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

}

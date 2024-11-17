package application.controller;

import java.time.LocalDate;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.ScheduledTransactionBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.TableUtility;

public class ViewScheduledTransController {
    // Table and columns
    @FXML private TableView<ScheduledTransactionBean> scheduledTransactionTable;
    @FXML private TableColumn<ScheduledTransactionBean, String> accountColumn;
    @FXML private TableColumn<ScheduledTransactionBean, String> typeColumn;
    @FXML private TableColumn<ScheduledTransactionBean, LocalDate> dueDateColumn;
    @FXML private TableColumn<ScheduledTransactionBean, String> descriptionColumn;
    @FXML private TableColumn<ScheduledTransactionBean, Double> amountColumn;
    @FXML private Button showTransactionsButton;

    // Reference to DalInt
    private DalInt dalInterface = new FileDal();
    private ObservableList<ScheduledTransactionBean> scheduledTransactionList;

    @FXML
    public void initialize() {
        // Set up the columns to match the ScheduledTransactionBean fields
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transType"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        // Enable text wrapping for respective columns
        TableUtility.setTextWrappingForColumn(accountColumn);
        TableUtility.setTextWrappingForColumn(typeColumn);
        TableUtility.setTextWrappingForColumn(descriptionColumn);

        // Initialize button to load data when clicked
        showTransactionsButton.setOnAction(e -> loadScheduledTransactions());

        // For Debugging-- ensure data is set up correctly
        System.out.println("Initialized ViewScheduledTransController");
    }

    private void loadScheduledTransactions() {
        // Load data from DAL
        scheduledTransactionList = FXCollections.observableArrayList(dalInterface.loadScheduledTransactions());
        
        // Sort the data by due date in ascending order
        dueDateColumn.setSortType(TableColumn.SortType.ASCENDING);
        scheduledTransactionTable.setItems(scheduledTransactionList);
        scheduledTransactionTable.getSortOrder().add(dueDateColumn);
        scheduledTransactionTable.sort();

        // For Debugging-- make sure data is loaded correctly
        System.out.println("Loaded scheduled transactions: " + scheduledTransactionList.size());
    }
}

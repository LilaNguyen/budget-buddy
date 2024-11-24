package application.controller;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.ScheduledTransBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

        // Initialize the list and table
        scheduledTransList = FXCollections.observableArrayList(dalInterface.loadScheduledTrans());
        scheduledTransTable.setItems(scheduledTransList);

        // Sort the data by due date in ascending order
        dueDateColumn.setSortType(TableColumn.SortType.ASCENDING);
        scheduledTransTable.getSortOrder().add(dueDateColumn);
        scheduledTransTable.sort();

        // For Debugging-- make sure data is being loaded
        System.out.println("Loaded scheduled transactions: " + scheduledTransList.size());

        // Initialize the search operation
        searchOp();
    }

    @FXML public void searchOp() {
        FilteredList<ScheduledTransBean> filteredScheduledTrans = new FilteredList<>(scheduledTransList, p -> true);
        scheduledTransTable.setItems(filteredScheduledTrans); // table will show filtered list

        // Observe changes in search input
        TransactionsSearchBar.textProperty().addListener((observable, previousText, currentText) -> {
            filteredScheduledTrans.setPredicate(scheduledTransBean -> {

                // If search input is empty, show all results
                if (currentText == null || currentText.isEmpty()) {
                    return true;
                }

                String searchedName = currentText.toLowerCase();

                // Check if schedule name contains search input
                return scheduledTransBean.getScheduleName().toLowerCase().contains(searchedName);
            });
        });
    }

    @FXML public void deleteOp() {
        // Get selected scheduled transaction
        ScheduledTransBean selectedScheduledTrans = scheduledTransTable.getSelectionModel().getSelectedItem();

        if (selectedScheduledTrans != null) {
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this scheduled transaction?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // Remove scheduled transaction from list and table
                scheduledTransList.remove(selectedScheduledTrans);
                dalInterface.deleteScheduledTrans(selectedScheduledTrans);

                displaySuccessAlert("Scheduled transaction deleted successfully.");
                System.out.println("Scheduled transaction deleted: " + selectedScheduledTrans.toString());
            }
        } else {
            displayErrorAlert();
        }
    }

    private void displayErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "A scheduled transaction must be selected first.", ButtonType.OK);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
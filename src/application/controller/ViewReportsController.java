package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import view.AlertUtility;
import view.TableUtility;

public class ViewReportsController {
	@FXML private TableView<TransBean> reportTable;
	@FXML private TableColumn<TransBean, String> uniqueColumn;
	@FXML private TableColumn<TransBean, LocalDate> dateColumn;
	@FXML private TableColumn<TransBean, String> descriptionColumn;
	@FXML private TableColumn<TransBean, Double> paymentAmountColumn;
	@FXML private TableColumn<TransBean, Double> depositAmountColumn;

	@FXML ComboBox<String> reportTypeDropDown; // type of report
	@FXML ComboBox<String> optionsDropDown; // specific account or transaction type

	private DalInt dalInterface = new FileDal();
	private ObservableList<TransBean> transactionList;
	private CommonObjs commonObjs = CommonObjs.getInstance();

	@FXML
	public void initialize() {
		reportTypeDropDown.getItems().addAll("Account", "Transaction Type");
		reportTypeDropDown.setOnAction(e -> updateOptions());
		optionsDropDown.setOnAction(e -> generateReport());

		// Set up columns
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("transDate"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
		depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));

		// Enable text wrapping for respective column
		TableUtility.setTextWrappingForColumn(uniqueColumn);
		TableUtility.setTextWrappingForColumn(descriptionColumn);

		// Initialize list and table
		transactionList = FXCollections.observableArrayList(dalInterface.loadTransactions());
		reportTable.setItems(transactionList);

		// Sort by transaction date in descending order
		dateColumn.setSortType(TableColumn.SortType.DESCENDING);
		reportTable.getSortOrder().add(dateColumn);
		reportTable.sort();
	}

	/*
	 * This method updates the options drop-down based on type of report selected.
	 */
	private void updateOptions() {
		optionsDropDown.getItems().clear();
		String selectedReportType = reportTypeDropDown.getValue();

		if (selectedReportType.equals("Account")) {
			// Set desired header and column
			uniqueColumn.setText("Type");
			uniqueColumn.setCellValueFactory(new PropertyValueFactory<>("transType"));

			// Populate options drop-down
			List<AccountBean> accounts = dalInterface.loadAccounts();
			for (AccountBean bean : accounts) {
				optionsDropDown.getItems().add(bean.getAccountName());
			}
		} else if (selectedReportType.equals("Transaction Type")) {
			// Set desired header and column
			uniqueColumn.setText("Account");
			uniqueColumn.setCellValueFactory(new PropertyValueFactory<>("account"));

			// Populate options drop-down
			List<TransTypeBean> types = dalInterface.loadTransTypes();
			for (TransTypeBean bean : types) {
				optionsDropDown.getItems().add(bean.getTransTypeName());
			}
		}
		optionsDropDown.getSelectionModel().selectFirst(); // shows first by default
		reportTable.refresh();
	}

	/*
	 * This method will generate the report based on user's selection of report type
	 * and specific account or transaction type.
	 */
	@FXML
	public void generateReport() {
		String selectedReportType = reportTypeDropDown.getSelectionModel().getSelectedItem();
		String selectedOption = optionsDropDown.getSelectionModel().getSelectedItem();

		ReportInt report = reportBy(selectedReportType);
		if (report != null) {
			// Generate filtered list of transactions based on option user selects
			List<TransBean> filteredTransactions = report.generateReport(transactionList, selectedOption);

			// Update table with filtered list
			reportTable.setItems(FXCollections.observableArrayList(filteredTransactions));
			reportTable.refresh();
		}
	}

	/*
	 * This method returns the desired filtering strategy for transactions based on
	 * report type selected.
	 * 
	 * @return desired filtering strategy for transactions
	 */
	private ReportInt reportBy(String reportType) {
		if (reportType.equals("Account")) {
			return new ReportByAccount();
		} else if (reportType.equals("Transaction Type")) {
			return new ReportByTransType();
		}
		return null;
	}

	@FXML public void showViewDetailsOp() {
		URL url = getClass().getClassLoader().getResource("view/ViewTransactionDetails.fxml");
		try {
			FXMLLoader loader = new FXMLLoader(url);
	        AnchorPane pane = loader.load();
	       
	        ViewTransactionDetailsController controller = loader.getController();
	        //account for if table has been searched
	        TransBean selectedTransaction = reportTable.getSelectionModel().getSelectedItem();
	        if (selectedTransaction != null) {
	        	
	        	controller.setTransactionData(selectedTransaction);
	        	reportTable.getSelectionModel().clearSelection(); // Clear selection
	        	
	        	HBox mainBox = commonObjs.getMainBox();		
				if (mainBox.getChildren().size() > 1) {
					mainBox.getChildren().remove(1);
				}
			
				mainBox.getChildren().add(pane);
	        }
	        else {
	        	AlertUtility.displayErrorAlert("A transaction must be selected first.");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

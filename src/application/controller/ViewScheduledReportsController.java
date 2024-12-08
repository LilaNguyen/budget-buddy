package application.controller;


import java.time.LocalDate;
import java.util.List;

import application.dal.DalInt;
import application.dal.FileDal;
import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.TableUtility;

public class ViewScheduledReportsController {
	@FXML private TableView<ScheduledTransBean> scheduledReportTable;
    @FXML private TableColumn<ScheduledTransBean, String> scheduleNameColumn;
    @FXML private TableColumn<ScheduledTransBean, String> accountColumn;
    @FXML private TableColumn<ScheduledTransBean, Integer> dueDateColumn;
    @FXML private TableColumn<ScheduledTransBean, Double> paymentAmountColumn;
    
    private DalInt dalInterface = new FileDal();
	private ObservableList<ScheduledTransBean> scheduledTransList;
    
	public void initialize() {
		scheduleNameColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
		accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
		
		scheduledTransList = FXCollections.observableArrayList(dalInterface.loadScheduledTrans());
		scheduledReportTable.setItems(scheduledTransList);

		// Sort by transaction date in descending order
		dueDateColumn.setSortType(TableColumn.SortType.ASCENDING);
		scheduledReportTable.getSortOrder().add(dueDateColumn);
		scheduledReportTable.sort();
    
	}

}

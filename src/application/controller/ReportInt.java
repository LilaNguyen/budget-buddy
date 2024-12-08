package application.controller;

import java.util.List;

import application.model.ScheduledTransBean;
import application.model.TransBean;

public interface ReportInt {
	List<TransBean> generateReport(List<TransBean> transactionList, String selection);
}

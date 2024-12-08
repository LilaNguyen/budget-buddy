package application.controller;

import java.util.ArrayList;
import java.util.List;
import application.model.TransBean;

public class ReportByTransType implements ReportInt {
	@Override
	public List<TransBean> generateReport(List<TransBean> transactionList, String selectedTransType) {
		List<TransBean> filteredTransactions = new ArrayList<>();
		for (TransBean trans : transactionList) {
			if (trans.getTransType().equals(selectedTransType)) {
				filteredTransactions.add(trans);
			}
		}
		return filteredTransactions;
	}
	
}

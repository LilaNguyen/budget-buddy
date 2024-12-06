package application.controller;

import java.util.ArrayList;
import java.util.List;

import application.model.TransBean;

public class ReportByAccount implements ReportInt {
	@Override
	public List<TransBean> generateReport(List<TransBean> transactionList, String selectedAccount) {
		List<TransBean> filteredTransactions = new ArrayList<>();
	    for (TransBean trans : transactionList) {
	        if (trans.getAccount().equals(selectedAccount)) {
	            filteredTransactions.add(trans);
	        }
	    }
	    return filteredTransactions;
	}
}

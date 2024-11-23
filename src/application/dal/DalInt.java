package application.dal;

import java.util.List;

import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransBean;
import application.model.TransTypeBean;

public interface DalInt {
	// Load and save methods for accounts
	List<AccountBean> loadAccounts();
	List<AccountBean> saveAccount(AccountBean account);
	
	// Load and save methods for transaction types
	List<TransTypeBean> loadTransTypes();
	List<TransTypeBean> saveTransType(TransTypeBean transType);
	
	// Load, save, and delete methods for transactions
	List<TransBean> loadTransactions();
	List<TransBean> saveTransactions(TransBean transaction);
	void saveAllTransactions(List<TransBean> transList);
	List<TransBean> deleteTransaction(TransBean transaction);

	// Load, save, and delete methods for scheduled transactions
	List<ScheduledTransBean> loadScheduledTrans();
	List<ScheduledTransBean> saveScheduledTrans(ScheduledTransBean scheduledTran);
	void saveAllScheduledTrans(List<ScheduledTransBean> scheduledTransList);
	List<ScheduledTransBean> deleteScheduledTrans(ScheduledTransBean scheduledTran);
	
}

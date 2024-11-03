package application.dal;

import java.util.List;

import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;

public interface DalInt {
	List<AccountBean> loadAccounts();
	
	List<AccountBean> saveAccount(AccountBean account);
	
	List<TransTypeBean> loadTransTypes();
	
	List<TransTypeBean> saveTransType(TransTypeBean transType);
	
	List<TransBean> loadTransactions();
	
	List<TransBean> saveTransactions(TransBean Transaction);


	
}

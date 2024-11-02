package application.dal;

import java.util.List;

import application.model.AccountBean;

public interface DalInt {
	List<AccountBean> loadAccounts();
	
	List<AccountBean> saveAccount(AccountBean account);
	
	List<TransTypeBean> loadTransTypes();
	
	List<TransTypeBean> saveTransType();
	
}

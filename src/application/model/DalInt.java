package application.model;

import java.util.List;

public interface DalInt {
	List<AccountBean> loadAccounts();
	
	List<AccountBean> saveAccount(AccountBean account);
	
}

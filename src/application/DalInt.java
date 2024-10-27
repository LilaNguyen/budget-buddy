package application;

import java.util.List;

public interface DalInt {
	List<AccountBean> loadAccounts();
	
	List<AccountBean> saveAccount(AccountBean account);
	
}

package application;

import application.model.AccountBean;
import javafx.scene.layout.HBox;

/**
 * This class contains all references of mainBox that we would like to share among the 
 * controllers, using the Singleton design pattern.
 */
public class CommonObjs {
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
    private AccountBean accountBean; // reference to AccountBean
    
	private CommonObjs() {}
	
	public static CommonObjs getInstance() {
		return commonObjs;
	}

	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}
	
	public AccountBean getAccountBean() {
		return accountBean;
	}
	
	public void setAccountBean(AccountBean accountBean) {
		this.accountBean = accountBean;
	}
	
}

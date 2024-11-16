package application;

import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import application.model.ScheduledTransBean;
import javafx.scene.layout.HBox;

/**
 * This class contains all references of mainBox that we would like to share among the 
 * controllers, using the Singleton design pattern.
 */
public class CommonObjs {
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
    private AccountBean accountBean; // reference to AccountBean
    private TransTypeBean transTypeBean; // reference to TransTypeBean
    private TransBean transBean; // reference to TransBean
    private ScheduledTransBean scheduledTransBean;
    
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
	
	public TransTypeBean getTransTypeBean() {
		return transTypeBean;
	}
	
	public void setTransTypeBean(TransTypeBean transTypeBean) {
		this.transTypeBean = transTypeBean;
	}
	
	public TransBean getTransBean() {
		return transBean;
	}
	
	public void setTransBean(TransBean transBean) {
		this.transBean = transBean;
	}
	public ScheduledTransBean getScheduledTransBean() {
		return scheduledTransBean;
	}
	public void setScheduledTransBean(ScheduledTransBean scheduledTransBean) {
		this.scheduledTransBean = scheduledTransBean;
	}
}

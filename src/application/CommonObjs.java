package application;

import java.io.IOException;
import java.net.URL;

import application.model.AccountBean;
import application.model.TransBean;
import application.model.TransTypeBean;
import application.model.ScheduledTransBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * This class contains all references of mainBox that can be shared among the
 * controllers, using the Singleton design pattern.
 */
public class CommonObjs {
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
    private AccountBean accountBean; // reference to AccountBean
    private TransTypeBean transTypeBean; // reference to TransTypeBean
    private TransBean transBean; // reference to TransBean
    private ScheduledTransBean scheduledTransBean; // reference to ScheduledTransBean
    
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
	
	/*
	 * This method loads the respective fxml page.
	 * @param path the string path of the fxml
	 */
	public void loadPage(String path) {
		// Create url to get fxml file 
		URL url = getClass().getClassLoader().getResource(path);
				
		try {
			// Read the fxml file, then convert to AnchorPane
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			// Before inserting new child, remove previous one
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			// Get children of root pane manager, then add new pane to it
			mainBox.getChildren().add(pane);
					
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
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

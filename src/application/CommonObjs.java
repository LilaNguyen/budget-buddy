package application;

import javafx.scene.layout.HBox;

/**
 * This class contains all the references we would like to share among all controllers.
 */
public class CommonObjs {
	// create commonObjs which stores references of mainBox
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
	
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
	
}

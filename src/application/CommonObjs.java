package application;

import javafx.scene.layout.HBox;

/**
 * This class contains all the references we would like to share among all controllers.
 */
public class CommonObjs {
	// create commonObjs which stores references of mainBox
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
	
<<<<<<< HEAD
	private String accountName;
    private String openingDate;
    private String openingBalance;
	
=======
>>>>>>> 3296208537fe1ee93c40435a1d45f8078642fb19
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
	

    // Getter and Setter for accountName
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // Getter and Setter for openingDate
    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    // Getter and Setter for openingBalance
    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }
	
	
	
}

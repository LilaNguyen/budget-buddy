package application;

import java.time.LocalDate;

import application.model.AccountBean;
import javafx.scene.layout.HBox;

/**
 * This class contains all the references we would like to share among all controllers.
 */
public class CommonObjs {
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
	/*
	private String accountName;
    private LocalDate openingDate;
    private double openingBalance;
    */
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
	
	/*
    // Getter and Setter for accountName
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // Getter and Setter for openingDate
    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    // Getter and Setter for openingBalance
    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
    */
	
	
	
}

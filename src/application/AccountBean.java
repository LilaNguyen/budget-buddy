package application;

import java.time.LocalDate;

public class AccountBean {
	// Fields for account information
	private String accountName;
    private LocalDate openingDate;
    private double openingBalance;

    // Constructor
    public AccountBean(String accountName, LocalDate openingDate, double openingBalance) {
    	this.accountName = accountName;
    	this.openingDate = openingDate;
    	this.openingBalance = openingBalance;
    }

    // Getter for name
    public String getAccountName() {
        return accountName;
    }

    // Setter for name
    public void setAccName(String accountName) {
        this.accountName = accountName;
    }

    // Getter for date
    public LocalDate getOpeningDate() {
        return openingDate;
    }

    // Setter for date
    public void setOpeningDate(LocalDate date) {
        this.openingDate = date;
    }

    // Getter for balance
    public double getOpeningBalance() {
        return openingBalance;
    }

    // Setter for balance
    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    //toString method to display account details
    @Override
    public String toString() {
        return "AccountBean{" +
                "accountName='" + accountName + '\'' +
                ", openingDate=" + openingDate +
                ", openingBalance=" + openingBalance +
                '}';
    }

}

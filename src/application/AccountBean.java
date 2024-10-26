package application;

public class AccountBean {
	// Fields for account information
	private String accountName;
    private String openingDate;
    private String openingBalance;

    // Constructor
    public AccountBean() {
    }

    // Getter for name
    public String getaccountName() {
        return accountName;
    }

    // Setter for name
    public void setName(String accountName) {
        this.accountName = accountName;
    }

    // Getter for date
    public String getDate() {
        return openingDate;
    }

    // Setter for date
    public void setopeningDate(String date) {
        this.openingDate = date;
    }

    // Getter for balance
    public String getopeningBalance() {
        return openingBalance;
    }

    // Setter for balance
    public void setBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    //toString method to display account details
    @Override
    public String toString() {
        return "AccountBean{" +
                "name='" + accountName + '\'' +
                ", date=" + openingDate +
                ", balance=" + openingBalance +
                '}';
    }

}

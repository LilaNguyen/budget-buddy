
package application.model;

import java.time.LocalDate;

public class TransBean {
    // Fields for transaction information
    private String accountName;
    private String transactionTypeName;
    private LocalDate transactionDate;
    private String transactionDescription;
    private double paymentAmount;
    private double depositAmount;

    // Constructor
    public TransBean(String accountName, String transactionTypeName, LocalDate transactionDate, 
                     String transactionDescription, double paymentAmount, double depositAmount) {
        this.accountName = accountName;
        this.transactionTypeName = transactionTypeName;
        this.transactionDate = transactionDate;
        this.transactionDescription = transactionDescription;
        this.paymentAmount = paymentAmount;
        this.depositAmount = depositAmount;
    }

    // Getter for account name
    public String getAccountName() {
        return accountName;
    }

    // Setter for account name
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // Getter for transaction type name
    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    // Setter for transaction type name
    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    // Getter for transaction date
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    // Setter for transaction date
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    // Getter for transaction description
    public String getTransactionDescription() {
        return transactionDescription;
    }

    // Setter for transaction description
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    // Getter for payment amount
    public double getPaymentAmount() {
        return paymentAmount;
    }

    // Setter for payment amount
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    // Getter for deposit amount
    public double getDepositAmount() {
        return depositAmount;
    }

    // Setter for deposit amount
    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    // toString method to display transaction details
    @Override
    public String toString() {
        return "TransBean{" +
                "accountName='" + accountName + '\'' +
                ", transactionTypeName='" + transactionTypeName + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", depositAmount=" + depositAmount +
                '}';
    }
}

package application.model;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a financial transaction in the Budget Buddy application.
 */
public class TransBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int transId;
    private TransTypeBean transType;  // Reference to TransTypeBean instead of String
    private double amount;
    private LocalDate date;
    private String description;

    // Default constructor
    public TransBean() {}

    // Parameterized constructor
    public TransBean(int transId, TransTypeBean transType, double amount, LocalDate date, String description) {
        this.transId = transId;
        this.transType = transType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getter and Setter for transId
    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    // Getter and Setter for transType
    public TransTypeBean getTransType() {
        return transType;
    }

    public void setTransType(TransTypeBean transType) {
        this.transType = transType;
    }

    // Getter and Setter for amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter and Setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Validates the TransBean instance
    public boolean isValid() {
        return transId > 0 &&
               transType != null && transType.isValid() &&
               amount >= 0 &&
               date != null;
    }

    // Overrides equals method for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransBean transBean = (TransBean) o;
        return transId == transBean.transId &&
               Double.compare(transBean.amount, amount) == 0 &&
               Objects.equals(transType, transBean.transType) &&
               Objects.equals(date, transBean.date) &&
               Objects.equals(description, transBean.description);
    }

    // Overrides hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(transId, transType, amount, date, description);
    }

    // Overrides toString method for readable representation
    @Override
    public String toString() {
        return "TransBean{" +
               "transId=" + transId +
               ", transType=" + transType +
               ", amount=" + amount +
               ", date=" + date +
               ", description='" + description + '\'' +
               '}';
    }
}

//
//package application.model;
//
//import java.time.LocalDate;
//
//public class TransBean {
//    private String accountName;          // Name of the account associated with the transaction
//    private String transactionTypeName;  // Type of transaction (e.g., Education, House)
//    private LocalDate transactionDate;   // Date of the transaction
//    private String transactionDescription; // Description of the transaction
//    private double paymentAmount;        // Payment amount (if applicable)
//    private double depositAmount;        // Deposit amount (if applicable)
//
//    // Constructor
//    public TransBean(String accountName, String transactionTypeName, LocalDate transactionDate, 
//                     String transactionDescription, double paymentAmount, double depositAmount) {
//        this.accountName = accountName;
//        this.transactionTypeName = transactionTypeName;
//        this.transactionDate = transactionDate;
//        this.transactionDescription = transactionDescription;
//        this.paymentAmount = paymentAmount;
//        this.depositAmount = depositAmount;
//    }
//
//    // Getter for account name
//    public String getAccountName() {
//        return accountName;
//    }
//
//    // Setter for account name
//    public void setAccountName(String accountName) {
//        this.accountName = accountName;
//    }
//
//    // Getter for transaction type name
//    public String getTransactionTypeName() {
//        return transactionTypeName;
//    }
//
//    // Setter for transaction type name
//    public void setTransactionTypeName(String transactionTypeName) {
//        this.transactionTypeName = transactionTypeName;
//    }
//
//    // Getter for transaction date
//    public LocalDate getTransactionDate() {
//        return transactionDate;
//    }
//
//    // Setter for transaction date
//    public void setTransactionDate(LocalDate transactionDate) {
//        this.transactionDate = transactionDate;
//    }
//
//    // Getter for transaction description
//    public String getTransactionDescription() {
//        return transactionDescription;
//    }
//
//    // Setter for transaction description
//    public void setTransactionDescription(String transactionDescription) {
//        this.transactionDescription = transactionDescription;
//    }
//
//    // Getter for payment amount
//    public double getPaymentAmount() {
//        return paymentAmount;
//    }
//
//    // Setter for payment amount
//    public void setPaymentAmount(double paymentAmount) {
//        this.paymentAmount = paymentAmount;
//    }
//
//    // Getter for deposit amount
//    public double getDepositAmount() {
//        return depositAmount;
//    }
//
//    // Setter for deposit amount
//    public void setDepositAmount(double depositAmount) {
//        this.depositAmount = depositAmount;
//    }
//
//    // toString method to display transaction details
//    @Override
//    public String toString() {
//        return "TransBean{" +
//                "accountName='" + accountName + '\'' +
//                ", transactionTypeName='" + transactionTypeName + '\'' +
//                ", transactionDate=" + transactionDate +
//                ", transactionDescription='" + transactionDescription + '\'' +
//                ", paymentAmount=" + paymentAmount +
//                ", depositAmount=" + depositAmount +
//                '}';
//    }
//}

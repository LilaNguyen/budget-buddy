package application.model;

public class TransTypeBean {
    // Fields for transaction type information
    private String transactionTypeName;  // Name of the transaction type (e.g., Deposit, Withdrawal)
    private String transactionDescription; // Description of the transaction type
    private int transactionCode;         // Unique identifier or code for the transaction type

    // Constructor
    public TransTypeBean(String transactionTypeName, String transactionDescription, int transactionCode) {
        this.transactionTypeName = transactionTypeName;
        this.transactionDescription = transactionDescription;
        this.transactionCode = transactionCode;
    }

    // Getter for transaction type name
    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    // Setter for transaction type name
    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    // Getter for transaction description
    public String getTransactionDescription() {
        return transactionDescription;
    }

    // Setter for transaction description
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    // Getter for transaction code
    public int getTransactionCode() {
        return transactionCode;
    }

    // Setter for transaction code
    public void setTransactionCode(int transactionCode) {
        this.transactionCode = transactionCode;
    }

    // toString method to display transaction type details
    @Override
    public String toString() {
        return "TransTypeBean{" +
                "transactionTypeName='" + transactionTypeName + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", transactionCode=" + transactionCode +
                '}';
    }
}

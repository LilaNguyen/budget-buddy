package application.model;

public class TransTypeBean {
    // Fields for transaction type information
    private String transTypeName;  // Name of the transaction type (e.g., Deposit, Withdrawal)
    private String transDescription; // Description of the transaction type
    private int transCode;         // Unique identifier or code for the transaction type

    // Constructor
    public TransTypeBean(String transTypeName, String transDescription, int transCode) {
        this.transTypeName = transTypeName;
        this.transDescription = transDescription;
        this.transCode = transCode;
    }

    // Getter for transaction type name
    public String getTransTypeName() {
        return transTypeName;
    }

    // Setter for transaction type name
    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    // Getter for transaction description
    public String getTransDescription() {
        return transDescription;
    }

    // Setter for transaction description
    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

    // Getter for transaction code
    public int getTransCode() {
        return transCode;
    }

    // Setter for transaction code
    public void setTransCode(int transCode) {
        this.transCode = transCode;
    }

    // toString method to display transaction type details
    @Override
    public String toString() {
        return "TransTypeBean{" +
                "transTypeName='" + transTypeName + '\'' +
                ", transDescription='" + transDescription + '\'' +
                ", transCode=" + transCode +
                '}';
    }
}

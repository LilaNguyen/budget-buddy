
package application.model;

public class TransTypeBean {
    // Fields for transaction type information
    private String transTypeName;  
    private int transCode;         // Unique identifier or code for the transaction type

    // Constructor
    public TransTypeBean(String transTypeName, int transCode) {
        this.transTypeName = transTypeName;
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
                ", transCode=" + transCode +
                '}';
    }
}


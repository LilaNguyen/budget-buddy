
package application.model;

public class TransTypeBean {
    // Fields for transaction type information
    private String transTypeName;  
    
    // Constructor
    public TransTypeBean(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    // Getter for transaction type name
    public String getTransTypeName() {
        return transTypeName;
    }

    // Setter for transaction type name
    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }
    
 // toString method to display transaction type details
    @Override
    public String toString() {
        return "TransTypeBean{" +
                "transTypeName='" + transTypeName +
                '}';
    }
}


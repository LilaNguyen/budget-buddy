package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a transaction type in the Budget Buddy application.
 */
public class TransTypeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    // Static list to mimic database or storage
    private static List<TransTypeBean> transTypes = new ArrayList<>();

    // Default constructor
    public TransTypeBean() {
    }

    // Parameterized constructor
    public TransTypeBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Validates the TransTypeBean instance
    public boolean isValid() {
        return id > 0 && name != null && !name.trim().isEmpty();
    }

    // Load all transaction types from static list
    public static List<TransTypeBean> loadTransTypes() {
        // This method would typically load from a database or external file
        return new ArrayList<>(transTypes);
    }

    // Save the transaction type to the static list
    public boolean saveTransType() {
        if (this.isValid()) {
            transTypes.add(this);
            return true;
        }
        return false;
    }

    // Overrides equals method for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransTypeBean that = (TransTypeBean) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    // Overrides hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // Overrides toString method for readable representation
    @Override
    public String toString() {
        return "TransTypeBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

/* Simplified 
 package application.model;

public class TransTypeBean {
    // Field for transaction type name
    private String transTypeName;  // Name of the transaction type (e.g., Deposit, Withdrawal)

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
                "transTypeName='" + transTypeName + '\'' +
                '}';
    }
}

 */

//
//package application.model;
//
//public class TransTypeBean {
//    // Fields for transaction type information
//    private String transTypeName;  // Name of the transaction type (e.g., Deposit, Withdrawal)
//    private String transDescription; // Description of the transaction type
//    private int transCode;         // Unique identifier or code for the transaction type
//
//    // Constructor
//    public TransTypeBean(String transTypeName, String transDescription, int transCode) {
//        this.transTypeName = transTypeName;
//        this.transDescription = transDescription;
//        this.transCode = transCode;
//    }
//
//    // Getter for transaction type name
//    public String getTransTypeName() {
//        return transTypeName;
//    }
//
//    // Setter for transaction type name
//    public void setTransTypeName(String transTypeName) {
//        this.transTypeName = transTypeName;
//    }
//
//    // Getter for transaction description
//    public String getTransDescription() {
//        return transDescription;
//    }
//
//    // Setter for transaction description
//    public void setTransDescription(String transDescription) {
//        this.transDescription = transDescription;
//    }
//
//    // Getter for transaction code
//    public int getTransCode() {
//        return transCode;
//    }
//
//    // Setter for transaction code
//    public void setTransCode(int transCode) {
//        this.transCode = transCode;
//    }
//
//    // toString method to display transaction type details
//    @Override
//    public String toString() {
//        return "TransTypeBean{" +
//                "transTypeName='" + transTypeName + '\'' +
//                ", transDescription='" + transDescription + '\'' +
//                ", transCode=" + transCode +
//                '}';
//    }
//}


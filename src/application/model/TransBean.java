package application.model;

import application.model.AccountBean;
import application.model.TransTypeBean;
import java.time.LocalDate;
import java.util.Objects;

/*
  Represents a financial transaction in the Budget Buddy application.
 */
public class TransBean {
    private TransTypeBean transType;  // Reference to TransTypeBean instead of String
    private AccountBean account; 
    private double paymentAmount;
    private String description;
    private double depositAmount;
    private LocalDate transDate; 

    // Parameterized constructor
    public TransBean(AccountBean account, TransTypeBean transType, LocalDate transDate, String description, double payment, double deposit) {
        this.account = account;
        this.transType = transType;
        this.transDate = transDate;
        this.description = description;
    }

    // Getter and Setter for transType
    public TransTypeBean getTransType() {
        return transType;
    }

    // Getter and Setter for amount
    public double setPaymentAmount() {
        return paymentAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }
    @Override
    public String toString() {
        return 
        	   "account" + account +
        	   ",TransBean{" + transType +
               ", amount=" + depositAmount +
               ", date=" + transDate +
               ", description='" + description + '\'' +
               '}';
        
    }

}



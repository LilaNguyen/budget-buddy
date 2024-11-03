package application.model;

import java.time.LocalDate;

/*
  Represents a financial transaction in the Budget Buddy application.
 */
public class TransBean {
    private String transType;  // Reference to TransTypeBean instead of String
    private String account; 
    private double paymentAmount;
    private String description;
    private double depositAmount;
    private LocalDate transDate; 

    // Parameterized constructor
    public TransBean(String account, String transType, LocalDate transDate, String description, double payment, double deposit) {
        this.account = account;
        this.transType = transType;
        this.transDate = transDate;
        this.description = description;
        this.paymentAmount = payment;
        this.depositAmount = deposit;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getTransType() {
        return transType;
    }
    
    public void setTransType(String type) {
        this.transType = type;
    }

    public LocalDate getTransDate() {
        return transDate;
    }

    public void setTransDate(LocalDate transDate) {
        this.transDate = transDate;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getDepositAmount() {
        return depositAmount;
    }
    
    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }
    @Override
    public String toString() {
       /*
    	return 
        	   "account" + account +
        	   ",TransBean{" + transType +
               ", amount=" + depositAmount +
               ", date=" + transDate +
               ", description='" + description + '\'' +
               '}';
       */
        return "TransBean{" +
		        "account='" + account + '\'' +
		        ", transDate=" + transDate +
		        ", description=" + description +
		        ", paymentAmount=" + paymentAmount +
		        ", depositAmount=" + depositAmount + '\'' +
		        '}';
    }

}



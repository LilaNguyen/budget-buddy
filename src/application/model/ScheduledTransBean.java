package application.model;

public class ScheduledTransBean {
	private String scheduleName;  // Reference to TransTypeBean instead of String
    private String account; 
    private String transType;
    private String frequency;
    private int dueDate;
    private double paymentAmount;

    public ScheduledTransBean(String scheduleName, String account, String transType, String frequency, int dueDate, double paymentAmount) {
        this.scheduleName = scheduleName;
        this.account = account;
        this.transType = transType;
        this.frequency = frequency;
        this.dueDate = dueDate;
        this.paymentAmount = paymentAmount;
    }
    
    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
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
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int due) {
        this.dueDate = due;
    }
    
    public double getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    @Override
    public String toString() {
        return "ScheduledTransBean{" +
		        "scheduleName='" + scheduleName + '\'' +
		        ", account=" + account +
		        ", transType=" + transType +
		        ", frequency=" + frequency +
		        ", dueDate=" + dueDate +
		        ", paymentAmount=" + paymentAmount +
		        '}';
    }
}

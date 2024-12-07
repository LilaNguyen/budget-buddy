package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionDetailsBulletinController {

    @FXML private Label transactionIdLabel;
    @FXML private Label accountLabel;
    @FXML private Label typeLabel;
    @FXML private Label dateLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label paymentLabel;
    @FXML private Label depositLabel;

    // Method to populate the transaction details
    public void setTransactionData(String transactionId, String account, String type, String date,
                                   String description, double payment, double deposit) {
        transactionIdLabel.setText("Transaction ID: " + transactionId);
        accountLabel.setText("Account: " + account);
        typeLabel.setText("Type: " + type);
        dateLabel.setText("Date: " + date);
        descriptionLabel.setText("Description: " + description);
        paymentLabel.setText("Payment: $" + payment);
        depositLabel.setText("Deposit: $" + deposit);
    }
}
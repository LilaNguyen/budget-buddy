package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewTransactionDetailController {

    @FXML private Label transactionIdLabel;
    @FXML private Label accountLabel;
    @FXML private Label typeLabel;
    @FXML private Label dateLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label paymentLabel;
    @FXML private Label depositLabel;

    // Populate the labels with transaction data
    public void setTransactionData(String id, String account, String type, String date,
                                   String description, double payment, double deposit) {
        transactionIdLabel.setText("Transaction ID: " + id);
        accountLabel.setText("Account: " + account);
        typeLabel.setText("Type: " + type);
        dateLabel.setText("Date: " + date);
        descriptionLabel.setText("Description: " + description);
        paymentLabel.setText("Payment: $" + payment);
        depositLabel.setText("Deposit: $" + deposit);
    }
}
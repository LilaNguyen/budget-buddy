package application.controller;

import application.CommonObjs;
import application.model.TransBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewTransactionDetailController {

    // FXML Components for displaying transaction details
    @FXML private Label accountLabel;
    @FXML private Label typeLabel;
    @FXML private Label dateLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label paymentLabel;
    @FXML private Label depositLabel;

    /**
     * Sets the transaction data to populate the labels.
     * @param transaction The selected transaction to display.
     */
    public void setTransactionData(TransBean transaction) {
        accountLabel.setText("Account: " + transaction.getAccount());
        typeLabel.setText("Type: " + transaction.getTransType());
        dateLabel.setText("Date: " + transaction.getTransDate());
        descriptionLabel.setText("Description: " + transaction.getDescription());
        paymentLabel.setText("Payment: $" + transaction.getPaymentAmount());
        depositLabel.setText("Deposit: $" + transaction.getDepositAmount());
    }
    @FXML
    public void handleBackButton() {
        try {
            // Navigate back to the ViewTransactions page
            CommonObjs.getInstance().loadPage("view/ViewTransactions.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Handles the Back button to navigate back to the originating page.
     */
   /* @FXML
    public void handleBackButton() {
        try {
            // Determine the origin and navigate back to the appropriate page
            String origin = CommonObjs.getInstance().getNavigationOrigin();
            if ("account".equals(origin)) {
                // Navigate back to Transactions by Account
                CommonObjs.getInstance().loadPage("view/ViewTransactions.fxml");
            } else if ("type".equals(origin)) {
                // Navigate back to Transactions by Type
                CommonObjs.getInstance().loadPage("view/ViewTransactionsByType.fxml");
            } else {
                // Fallback to homepage or default page
                CommonObjs.getInstance().loadPage("view/Homepage.fxml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } */
}
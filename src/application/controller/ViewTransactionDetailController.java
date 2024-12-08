package application.controller;

import application.CommonObjs;
import application.model.TransBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewTransactionDetailController {

    // FXML Components for displaying transaction details
	private String navigationOrigin; // Tracks the page that opened this detail page
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
    /**
     * Sets the origin page for navigation.
     * @param origin The originating page, e.g., "ViewReports" or "ViewTransactions".
     */
    public void setNavigationOrigin(String origin) {
        this.navigationOrigin = origin;
    }

    /**
     * Handles the Back button to navigate back to the originating page.
     */
    @FXML
    public void handleBackButton() {
        try {
            // Navigate back to the ViewReports page
            CommonObjs.getInstance().loadPage("view/ViewReports.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package application.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import application.model.ScheduledTransBean;
import application.dal.FileDal;

import java.util.List;

public class SchedNotification {

    /**
     * Checks for scheduled transactions due today and displays a notification if any exist.
     *
     * @param ownerStage the stage to which the alert is linked.
     * @param fileDal    the data access layer instance.
     */
    public static void checkAndAlert(Stage ownerStage, FileDal fileDal) {
        // Fetch scheduled transactions due today
        List<ScheduledTransBean> dueToday = fileDal.getDueTransactionsForToday();

        // If there are transactions due today, show an alert
        if (!dueToday.isEmpty()) {
            showAlert(ownerStage, dueToday);
        }
    }

    /**
     * Displays an alert with the details of due transactions.
     *
     * @param ownerStage the stage to which the alert is linked.
     * @param dueToday   the list of transactions due today.
     */
    private static void showAlert(Stage ownerStage, List<ScheduledTransBean> dueToday) {
        StringBuilder message = new StringBuilder("");

        // Format each scheduled transaction into the message
        for (ScheduledTransBean trans : dueToday) {
            message.append("- ").append(trans.getScheduleName())
                   .append(" (Account: ").append(trans.getAccount()).append(", Amount: $")
                   .append(trans.getPaymentAmount()).append(")\n");
        }

        // Create and display the alert
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(ownerStage);
        alert.setTitle("Scheduled Payments Due");
        alert.setHeaderText("The following payments are due today:");
        alert.setContentText(message.toString());
        alert.showAndWait();
    }
}

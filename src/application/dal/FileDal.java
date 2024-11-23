package application.dal;

import java.util.ArrayList;
import java.util.List;

import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransBean;
import application.model.TransTypeBean;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileDal implements DalInt {

    // In-memory lists to store data
    private List<AccountBean> accounts = new ArrayList<>();
    private List<TransTypeBean> transTypes = new ArrayList<>();
    private List<TransBean> transactions = new ArrayList<>();
    private List<ScheduledTransBean> scheduledTrans = new ArrayList<>();

    private final String accountsFilePath = "CSVs/accounts.csv";
    private final String transTypeFilePath = "CSVs/TransType.csv";
    private final String transactionsFilePath = "CSVs/transactions.csv";
    private final String scheduledTransFilePath = "CSVs/ScheduledTrans.csv";

    // Date formatter
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Load Accounts
    @Override
    public List<AccountBean> loadAccounts() {
        accounts.clear();

        try {
            URL url = getClass().getClassLoader().getResource(accountsFilePath);
            if (url == null) {
                System.out.println("Unable to find accounts.csv");
                return accounts;
            }

            InputStream inputStream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = br.readLine(); // Read header
            if (line != null && line.contains("Account Name,Opening Date,Balance")) {
                System.out.println("Skipping header row");
            }

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String accountName = fields[0].trim();
                    LocalDate openingDate = LocalDate.parse(fields[1], dateFormatter);
                    double openingBalance = Double.parseDouble(fields[2].trim());
                    accounts.add(new AccountBean(accountName, openingDate, openingBalance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing balance: " + e.getMessage());
        }
        return accounts;
    }

    // Save Account
    @Override
    public List<AccountBean> saveAccount(AccountBean account) {
        accounts.add(account);

        try {
            URL url = getClass().getClassLoader().getResource(accountsFilePath);
            if (url == null) {
                System.out.println("Unable to find accounts.csv");
                return accounts;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, true)) {
                writer.append(account.getAccountName()).append(",")
                    .append(account.getOpeningDate().format(dateFormatter)).append(",")
                    .append(String.valueOf(account.getOpeningBalance())).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Load Transaction Types
    @Override
    public List<TransTypeBean> loadTransTypes() {
        transTypes.clear();
        try {
            URL url = getClass().getClassLoader().getResource(transTypeFilePath);
            if (url == null) {
                System.out.println("Unable to find TransType.csv");
                return transTypes;
            }

            InputStream inputStream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = br.readLine(); // Read header
            if (line != null && line.contains("Transaction Type")) {
                System.out.println("Skipping header row");
            }

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 1) {
                    String transTypeName = fields[0].trim();
                    transTypes.add(new TransTypeBean(transTypeName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transTypes;
    }

    // Save Transaction Type
    @Override
    public List<TransTypeBean> saveTransType(TransTypeBean transType) {
        transTypes.add(transType);

        try {
            URL url = getClass().getClassLoader().getResource(transTypeFilePath);
            if (url == null) {
                System.out.println("Unable to find TransType.csv");
                return transTypes;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, true)) {
                writer.append(transType.getTransTypeName()).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transTypes;
    }

    // Load Transactions
    @Override
    public List<TransBean> loadTransactions() {
        transactions.clear();

        try {
            URL url = getClass().getClassLoader().getResource(transactionsFilePath);
            if (url == null) {
                System.out.println("Unable to find transactions.csv");
                return transactions;
            }

            InputStream inputStream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = br.readLine(); // Read header
            if (line != null && line.contains("accountName,transactionTypeName,transactionDate,transactionDescription,paymentAmount,depositAmount")) {
                System.out.println("Skipping header row");
            }

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String accountName = fields[0].trim();
                    String transType = fields[1].trim();
                    LocalDate transDate = LocalDate.parse(fields[2], dateFormatter);
                    String transDescription = fields[3].trim();
                    double paymentAmount = Double.parseDouble(fields[4].trim());
                    double depositAmount = Double.parseDouble(fields[5].trim());
                    transactions.add(new TransBean(accountName, transType, transDate, transDescription, paymentAmount, depositAmount));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing amount: " + e.getMessage());
        }
        return transactions;
    }

    // Save Transaction
    @Override
    public List<TransBean> saveTransactions(TransBean transaction) {
        transactions.add(transaction);
        try {
            URL url = getClass().getClassLoader().getResource(transactionsFilePath);
            if (url == null) {
                System.out.println("Unable to find transactions.csv");
                return transactions;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, true)) {
                writer.append(transaction.getAccount()).append(",")
                    .append(transaction.getTransType()).append(",")
                    .append(transaction.getTransDate().format(dateFormatter)).append(",")
                    .append(transaction.getDescription()).append(",")
                    .append(String.valueOf(transaction.getPaymentAmount())).append(",")
                    .append(String.valueOf(transaction.getDepositAmount())).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Save All Transactions
    @Override
    public void saveAllTransactions(List<TransBean> transList) {
        try {
            URL url = getClass().getClassLoader().getResource(transactionsFilePath);
            if (url == null) {
                System.out.println("Unable to find transactions.csv");
                return;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, false)) {
                // Write header
                writer.append("accountName,transactionTypeName,transactionDate,transactionDescription,paymentAmount,depositAmount\n");
                for (TransBean transaction : transList) {
                    writer.append(transaction.getAccount()).append(",")
                        .append(transaction.getTransType()).append(",")
                        .append(transaction.getTransDate().format(dateFormatter)).append(",")
                        .append(transaction.getDescription()).append(",")
                        .append(String.valueOf(transaction.getPaymentAmount())).append(",")
                        .append(String.valueOf(transaction.getDepositAmount())).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Transaction
    @Override
    public List<TransBean> deleteTransaction(TransBean transaction) {
        transactions.remove(transaction);
        saveAllTransactions(transactions);
        return transactions;
    }

    // Load Scheduled Transactions
    @Override
    public List<ScheduledTransBean> loadScheduledTrans() {
        scheduledTrans.clear();

        try {
            URL url = getClass().getClassLoader().getResource(scheduledTransFilePath);
            if (url == null) {
                System.out.println("Unable to find ScheduledTrans.csv");
                return scheduledTrans;
            }

            InputStream inputStream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = br.readLine(); // Read header
            if (line != null && line.contains("scheduleName,account,transType,frequency,dueDate,paymentAmount")) {
                System.out.println("Skipping header row");
            }

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String scheduleName = fields[0].trim();
                    String account = fields[1].trim();
                    String transType = fields[2].trim();
                    String frequency = fields[3].trim();
                    int dueDate = Integer.parseInt(fields[4].trim());
                    double paymentAmount = Double.parseDouble(fields[5].trim());
                    scheduledTrans.add(new ScheduledTransBean(scheduleName, account, transType, frequency, dueDate, paymentAmount));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }
        return scheduledTrans;
    }

    // Save Scheduled Transaction
    @Override
    public List<ScheduledTransBean> saveScheduledTrans(ScheduledTransBean scheduledTran) {
        scheduledTrans.add(scheduledTran);
        try {
            URL url = getClass().getClassLoader().getResource(scheduledTransFilePath);
            if (url == null) {
                System.out.println("Unable to find ScheduledTrans.csv");
                return scheduledTrans;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, true)) {
                writer.append(scheduledTran.getScheduleName()).append(",")
                    .append(scheduledTran.getAccount()).append(",")
                    .append(scheduledTran.getTransType()).append(",")
                    .append(scheduledTran.getFrequency()).append(",")
                    .append(String.valueOf(scheduledTran.getDueDate())).append(",")
                    .append(String.valueOf(scheduledTran.getPaymentAmount())).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduledTrans;
    }

    // Save All Scheduled Transactions
    @Override
    public void saveAllScheduledTrans(List<ScheduledTransBean> scheduledTransList) {
        try {
            URL url = getClass().getClassLoader().getResource(scheduledTransFilePath);
            if (url == null) {
                System.out.println("Unable to find ScheduledTrans.csv");
                return;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, false)) {
                // Write header
                writer.append("scheduleName,account,transType,frequency,dueDate,paymentAmount\n");
                for (ScheduledTransBean scheduledTran : scheduledTransList) {
                    writer.append(scheduledTran.getScheduleName()).append(",")
                        .append(scheduledTran.getAccount()).append(",")
                        .append(scheduledTran.getTransType()).append(",")
                        .append(scheduledTran.getFrequency()).append(",")
                        .append(String.valueOf(scheduledTran.getDueDate())).append(",")
                        .append(String.valueOf(scheduledTran.getPaymentAmount())).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Scheduled Transaction
    @Override
    public List<ScheduledTransBean> deleteScheduledTrans(ScheduledTransBean scheduledTran) {
        scheduledTrans.remove(scheduledTran);
        saveAllScheduledTrans(scheduledTrans);
        return scheduledTrans;
    }
}
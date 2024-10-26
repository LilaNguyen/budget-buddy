package application;

import java.util.ArrayList;
import java.util.List;

public class FileDal {

    // In-memory list to store accounts
    private List<Account> accounts;

    public FileDal() {
        // Initialize the list of accounts
        accounts = new ArrayList<>();
    }

    // Load all accounts from the list
    public List<Account> LoadAccounts() {
        return accounts;
    }

    // Save a new account to the list
    public List<Account> saveAccount(String accountId, String accountName, double balance) {
        // Create a new Account and add it to the list
        Account account = new Account(accountId, accountName, balance);
        accounts.add(account);
        
        // Return the updated list of accounts
        return accounts;
    }

    // Inner class to represent Account data
    class Account {
        private String accountId;
        private String accountName;
        private double balance;

        public Account(String accountId, String accountName, double balance) {
            this.accountId = accountId;
            this.accountName = accountName;
            this.balance = balance;
        }

        // Getters and setters
        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "accountId='" + accountId + '\'' +
                    ", accountName='" + accountName + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    // Example main method for testing
    public static void main(String[] args) {
        FileDal fileDal = new FileDal();

        // Save some accounts
        fileDal.saveAccount("123", "John Doe", 1000.0);
        fileDal.saveAccount("456", "Jane Smith", 2500.0);

        // Load and display all accounts
        List<Account> accounts = fileDal.LoadAccounts();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}
package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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
    public List<Account> saveAccount(String accountName, double balance, LocalDate lastTransaction) {
        // Create a new Account and add it to the list
        Account account = new Account(accountName, balance, lastTransaction);
        accounts.add(account);
        // Return the updated list of accounts
        return accounts;
    }

    // Inner class to represent Account data
    public class Account {
        private String accountName;
        private double balance;
        private LocalDate lastTransaction;

        public Account(String accountName, double balance, LocalDate lastTransaction) {
            this.accountName = accountName;
            this.balance = balance;
            this.lastTransaction = lastTransaction;
        }

        // Getters and setters
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
        
        public LocalDate getLastTransaction() {
            return lastTransaction;
        }

        public void setLastTransaction(LocalDate lastTransaction) {
            this.lastTransaction = lastTransaction;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "accountName='" + accountName + '\'' +
                    ", balance='" + balance + '\'' +
                    '}';
        }
    }

/**    // Example main method for testing
    public static void main(String[] args) {
        FileDal fileDal = new FileDal();

        // Save some accounts
        fileDal.saveAccount("John Doe", 1000.0, LocalDate.now().minusDays(5));
        fileDal.saveAccount("Jane Smith", 2500.0, LocalDate.now().minusDays(2));

        // Load and display all accounts
        List<Account> accounts = fileDal.LoadAccounts();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
**/
}
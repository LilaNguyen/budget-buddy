package application;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileDal implements DalInt {

    // In-memory list to store accounts
    private List<AccountBean> accounts = new ArrayList<>();
    private final String csvFilePath = "CSVs/accounts.csv";
    // Makes sure dates are formatted correctly
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    
    /**
     * This method load all accounts from the CSV file.
     * @return a list of AccountBean objects
     */
    @Override
    public List<AccountBean> loadAccounts() {
    	
    	try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CSVs/accounts.csv");
    		 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
    		// Hold each line read from CSV file
    		String line;
    		
    		// Check to see if file was found
    		if (inputStream == null) {
    			System.out.println("Was not able to find accounts.csv");
    			// Return empty list of accounts
    			return accounts;
    		}
    		
    		// Read lines from file until there are no more lines
    		while ((line = br.readLine()) != null) {
    			String[] fields = line.split(",");
    			// Check that line contains exactly 3 fields
    			if (fields.length == 3) {
    				String accountName = fields[0];
    				LocalDate openingDate = LocalDate.parse(fields[1]);
    				double openingBalance = Double.parseDouble(fields[2]);
    				// Create new AccountBean object to add to list
    				accounts.add(new AccountBean(accountName, openingDate, openingBalance));
    			}
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	// Return list of accounts loaded
        return accounts;
    }

    /**
     * This method saves an AccountBean object.
     * @return a list of AccountBean objects
     */
	@Override
	public List<AccountBean> saveAccount(AccountBean account) {
		// Add AccountBean object to list of accounts
		accounts.add(account);
		try (FileWriter writer = new FileWriter(csvFilePath, true)) {
			// Append account name, opening date, and opening balance to CSV file
			writer.append(account.getAccountName()).append(",")
				.append(account.getOpeningDate().format(dateFormatter)).append(",")
				.append(String.valueOf(account.getOpeningBalance())).append("\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		// Return updated list of accounts
		return accounts;
	}
	
	/**
    public FileDal() {
        // Initialize the list of accounts
        accounts = new ArrayList<>();
    }
	
	
	// Save a new account to the list
    public List<AccountBean> saveAccount(String accountName, LocalDate lastTransaction, double balance) {
        // Create a new Account and add it to the list
    	AccountBean account = new AccountBean(accountName, lastTransaction, balance);
        accounts.add(account);
        // Return the updated list of accounts
        return accounts;
    }
	
    // Inner class to represent Account data
    public static class Account {
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
    */
}
package application.dal;

import java.util.ArrayList;
import java.util.List;

import application.model.AccountBean;
import application.model.ScheduledTransBean;
import application.model.TransTypeBean;
import application.model.TransBean;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileDal implements DalInt {

    // In-memory list to store accounts
    private List<AccountBean> accounts = new ArrayList<>();
    private List<TransTypeBean> transTypes = new ArrayList<>();
    private List<TransBean> transactions = new ArrayList<>();
    private List<ScheduledTransBean> scheduledTrans = new ArrayList<>();

    private final String accountsFilePath = "CSVs/accounts.csv";
    private final String transTypeFilePath = "CSVs/TransType.csv";
    private final String transactionsFilePath = "CSVs/transactions.csv";
    private final String scheduledTransFilePath = "CSVs/ScheduledTrans.csv";

    // Makes sure dates are formatted correctly
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * This method load all accounts from the CSV file.
     * NOTE: System.out.println are used for debugging purposes.
     * @return a list of AccountBean objects
     */
    @Override
    public List<AccountBean> loadAccounts() {
		
		accounts.clear();
		 
    	try {
    		System.out.println("Here we go");

    		URL url = getClass().getClassLoader().getResource(accountsFilePath);
			System.out.println("Here is url " + url);
						
			InputStream inputStream = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    
			String line = br.readLine(); // Read first line (header)
			if (line != null && line.contains("Account Name,Opening Date,Balance")) {
			    System.out.println("Skipping header row");
			}

    		// Check to see if file was found
    		if (inputStream == null) {
    			System.out.println("Unable to find accounts.csv");
    			// Return empty list of accounts
    			return accounts;
    		}
 
    		// Hold each line read from CSV file
    		//String line;
    		// Read lines from file until there are no more lines
    		while ((line = br.readLine()) != null) {
    			line = line.trim();
    			
    			if (line.isEmpty()) {
    				continue;
    			}
    			
    			String[] fields = line.split(",");
    			// Check that line contains exactly 3 fields
    			if (fields.length == 3) {
    				String accountName = fields[0].trim(); // Remove any potential whitespace
    				LocalDate openingDate = LocalDate.parse(fields[1], dateFormatter);
    				double openingBalance = Double.parseDouble(fields[2].trim()); // Remove any potential whitespace
    				// Create new AccountBean object to add to list
    				accounts.add(new AccountBean(accountName, openingDate, openingBalance));
    			}
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	catch (DateTimeParseException e) {
    		System.out.println("Error parsing date: " + e.getMessage());
    	}
    	catch (NumberFormatException e) {
    		System.out.println("Error parsing balance: " + e.getMessage());
    	}
    	// Return list of accounts loaded
        return accounts;
    }

    /**
     * This method saves an AccountBean object.
     * NOTE: System.out.println are used for debugging purposes.
     * @return a list of AccountBean objects
     */
	@Override
	public List<AccountBean> saveAccount(AccountBean account) {
		System.out.println("IN SAVE ACCOUNT");
		
		// Add AccountBean object to list of accounts
		accounts.add(account);
		
		try {
			URL url = getClass().getClassLoader().getResource(accountsFilePath);
			System.out.println("Here is url " + url);
			
			String path = url.toURI().getPath();
			
			try (FileWriter writer = new FileWriter(path, true)) {	
				// Append account name, opening date, and opening balance to CSV file
				writer.append(account.getAccountName()).append(",")
					.append(account.getOpeningDate().format(dateFormatter)).append(",")
					.append(String.valueOf(account.getOpeningBalance())).append("\n");
			}		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// Return updated list of accounts
		return accounts;
	}

	@Override
	public List<TransTypeBean> loadTransTypes() {
		transTypes.clear();
	    try {
	    	System.out.println("Here we go");
	    	
	        URL url = getClass().getClassLoader().getResource(transTypeFilePath);
	        System.out.println("Here is url " + url);
						
			InputStream inputStream = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    
			String line = br.readLine(); // Read first line (header)
			if (line != null && line.contains("Transaction Type")) {
			    System.out.println("Skipping header row");
			}

	        // Check to see if file was found
	        if (inputStream == null) {
	            System.out.println("Unable to find TransTypes.csv");
	            return transTypes;
	        }

	        // Hold each line read from CSV file
	        //String line;
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
	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }
	    return transTypes;
	}

	@Override
	public List<TransTypeBean> saveTransType(TransTypeBean transType) {
		transTypes.add(transType);

        try {
            URL url = getClass().getClassLoader().getResource(transTypeFilePath);
            if (url == null) {
                System.out.println("Unable to find TransTypes.csv");
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

	@Override
	public List<TransBean> loadTransactions() {
		transactions.clear();
		 
    	try {
    		System.out.println("Here we go");

    		URL url = getClass().getClassLoader().getResource(transactionsFilePath);
			System.out.println("Here is url " + url);
						
			InputStream inputStream = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    
			String line = br.readLine(); // Read first line (header)
			if (line != null && line.contains("accountName,transactionTypeName,transactionDate,transactionDescription,paymentAmount,depositAmount")) {
			    System.out.println("Skipping header row");
			}
			
    		// Check to see if file was found
    		if (inputStream == null) {
    			System.out.println("Unable to find transactions.csv");
    			// Return empty list of accounts
    			return transactions;
    		}
    		
    		// Hold each line read from CSV file
    		//String line;
    		// Read lines from file until there are no more lines
    		while ((line = br.readLine()) != null) {
    			line = line.trim();
    			
    			if (line.isEmpty()) {
    				continue;
    			}
    			
    			String[] fields = line.split(",");
    			// Check that line contains exactly 6 fields
    			if (fields.length == 6) {
    				String accountName = fields[0].trim();
    				String transType = fields[1].trim();
    				LocalDate transDate = LocalDate.parse(fields[2], dateFormatter);
    				String transDescription = fields[3].trim();
    				double paymentAmount = Double.parseDouble(fields[4].trim()); // Remove any potential whitespace
    				double depositAmount = Double.parseDouble(fields[5].trim()); // Remove any potential whitespace
    				// Create new Transaction object to add to list
    				transactions.add(new TransBean(accountName, transType, transDate, transDescription, paymentAmount, depositAmount));
    			}
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	catch (DateTimeParseException e) {
    		System.out.println("Error parsing date: " + e.getMessage());
    	}
    	catch (NumberFormatException e) {
    		System.out.println("Error parsing balance: " + e.getMessage());
    	}
    	// Return list of accounts loaded
        return transactions;
	}

	@Override
	public List<TransBean> saveTransactions(TransBean transaction) {
		ArrayList<TransBean> transList = new ArrayList<TransBean>();
		transList.add(transaction);
		return saveAllTransactions(transList);
	}
	@Override
	public List<TransBean> saveAllTransactions(List<TransBean> transList) {
		for (TransBean transaction : transList) {
			transactions.add(transaction);
		}
		try {
            URL url = getClass().getClassLoader().getResource(transactionsFilePath);
            if (url == null) {
                System.out.println("Unable to find transactions.csv");
                return transactions;
            }

            String path = url.toURI().getPath();

            try (FileWriter writer = new FileWriter(path, true)) {
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
		return transactions;
	}
	
		
	@Override
	public List<ScheduledTransBean> loadScheduledTrans() {
		scheduledTrans.clear();
		 
    	try {
    		System.out.println("Here we go");

    		URL url = getClass().getClassLoader().getResource(scheduledTransFilePath);
			System.out.println("Here is url " + url);
						
			InputStream inputStream = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    
			String line = br.readLine(); // Read first line (header)
			if (line != null && line.contains("scheduleName,account,transType,frequency,dueDate,paymentAmount")) {
			    System.out.println("Skipping header row");
			}
			
    		// Check to see if file was found
    		if (inputStream == null) {
    			System.out.println("Unable to find ScheduledTrans.csv");
    			// Return empty list of accounts
    			return scheduledTrans;
    		}
    		
    		// Hold each line read from CSV file
    		//String line;
    		// Read lines from file until there are no more lines
    		while ((line = br.readLine()) != null) {
    			line = line.trim();
    			
    			if (line.isEmpty()) {
    				continue;
    			}
    			
    			String[] fields = line.split(",");
    			// Check that line contains exactly 6 fields
    			if (fields.length == 6) {
    				String scheduleName = fields[0].trim();
    				String account = fields[1].trim();
    				String transType = fields[2].trim();
    				String frequency = fields[3].trim();
    				int dueDate = Integer.parseInt(fields[4].trim()); // Remove any potential whitespace
    				double paymentAmount = Double.parseDouble(fields[5].trim()); // Remove any potential whitespace
    				// Create new Transaction object to add to list
    				scheduledTrans.add(new ScheduledTransBean(scheduleName, account, transType, frequency, dueDate, paymentAmount));
    			}
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	// Return list of accounts loaded
        return scheduledTrans;
	}

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
}
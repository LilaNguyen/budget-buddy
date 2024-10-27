package application.interfaces;

import java.util.ArrayList;
import java.util.List;

import application.model.AccountBean;
import application.model.DalInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileDal implements DalInt {

    // In-memory list to store accounts
    private List<AccountBean> accounts = new ArrayList<>();
    private final String csvFilePath = "CSVs/accounts.csv";
    // Makes sure dates are formatted correctly
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * This method load all accounts from the CSV file.
     * @return a list of AccountBean objects
     */
    @Override
    public List<AccountBean> loadAccounts() {
		System.out.println("IN LOAD ACCOUNTS");

    	try {
			System.out.println("Here we go");

    		URL url = getClass().getClassLoader().getResource("CSVs/accounts.csv");
			System.out.println("Here is url " + url);
			
			String path = url.toURI().getPath();
			System.out.println("Here is path" + path);
			
			InputStream inputStream = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
    
    		// Check to see if file was found
    		if (inputStream == null) {
    			System.out.println("Unable to find accounts.csv");
    			// Return empty list of accounts
    			return accounts;
    		}
    		
    		// Skip header line
    		String header = br.readLine(); 
    		System.out.println("Header: " + header);
    		
    		// Hold each line read from CSV file
    		String line;
    		// Read lines from file until there are no more lines
    		while ((line = br.readLine()) != null) {
    			line = line.trim();
    			System.out.println("Reading line: " + line);
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
    	catch (URISyntaxException e) {
			e.printStackTrace();
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
     * @return a list of AccountBean objects
     */
	@Override
	public List<AccountBean> saveAccount(AccountBean account) {
		System.out.println("IN SAVEACCOUNT");
		// Add AccountBean object to list of accounts
		accounts.add(account);
		
		try {
			URL url = getClass().getClassLoader().getResource("CSVs/accounts.csv");
			System.out.println("Here is url " + url);
			
			String path = url.toURI().getPath();
			System.out.println("Here is path" + path);
			
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
}
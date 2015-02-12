package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;
import edu.pitt.utilities.*;

public class Transaction {
	private String transactionID;
	private String accountID;
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate; 
	
	/**
	 * Constructs a transaction object from an existing transaction
	 * with the provided transaction id.
	 * @param transactionID
	 */
	public Transaction(String transactionID){
		DbUtilities db = new MySqlUtilities();
		ResultSet rs = null;
		
		String sql = "SELECT * FROM transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'";

		try {
			rs = db.getResultSet(sql);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");
				this.transactionDate = new Date();
			}
		} catch (SQLException e) {
			ErrorLogger.log("Unable to retrieve transaction.");
			ErrorLogger.log(e.getMessage());
		}finally{
			db.silentClose(rs);
			db.silentClose();
		}
	}
	
	/**
	 * Constructs a new transaction object using the provided parameters.
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 */
	public Transaction(String accountID, String type, double amount, double balance){
		Date d = new Date();
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
		this.transactionDate = d;
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;
		
		String sql = "INSERT INTO transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "'" + currentDate + "', ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";
		
		DbUtilities db = new MySqlUtilities();
		
		try{			
			db.executeQuery(sql);		
		} catch (Exception e){
			ErrorLogger.log("Unable to retrieve customer object.");
			ErrorLogger.log(e.getMessage());
		}finally{
			db.silentClose();
		}
		
	}
	
	/**
	 * Retrieves the dollar amount of the transaction.
	 * @return double
	 */
	public double getAmount(){
		return this.amount;
	}
	
	/**
	 * Retrieves the transaction type; deposit, withdrawal, etc.
	 * @return String
	 */
	public String getType(){
		return this.type;
	}
	
	/**
	 * Retrieves the date the transaction was made.
	 * @return Date
	 */
	public Date getTransactionDate(){
		return this.transactionDate;
	}
	
	/**
	 * Retrieves the remaining balance after the transaction was made.
	 * @return double
	 */
	public double getBalance(){
		return this.balance;
	}
}

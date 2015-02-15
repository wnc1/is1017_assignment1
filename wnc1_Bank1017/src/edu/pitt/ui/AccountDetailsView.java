package edu.pitt.ui;

import javax.swing.JFrame;

import edu.pitt.bank.Account;
import edu.pitt.bank.Bank;
import edu.pitt.bank.Customer;
import edu.pitt.utilities.ErrorLogger;

import javax.swing.JTextArea;

import java.util.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Color;

/**
 * Provides the account view for the application.
 * @author bconner
 *
 */
public class AccountDetailsView {
	protected static NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    protected static NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.ENGLISH);
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList<String>permissions = new ArrayList<String>();
	private JFrame frmAccountDetails;	
	private Customer accountOwner;
	private Account selectedAccount;
	JLabel lblAccountTypeText;
	JLabel lblBalanceText;
	JComboBox cboAccounts;
	JLabel lblInterestRateText;
	JLabel lblPenaltyText ;
	JTextArea txtAreaMessage;
	JTextField txtAmount;
	JLabel lblAccountStatus;
	JButton btnWithdraw;
	JButton btnDeposit;

	/**
	 * Create the application.
	 */
	public AccountDetailsView(Customer c, ArrayList<String> groups) {
		accountOwner = c;
		permissions = groups;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//////////////////////////////////////////////////////////////////////////////
		//		FRAME CONTROL OBJECTS
		//////////////////////////////////////////////////////////////////////////////
		frmAccountDetails = new JFrame();
		frmAccountDetails.setTitle("Account Details    " + dateFormat.format(new Date(System.currentTimeMillis())));
		frmAccountDetails.setBounds(100, 100, 507, 300);
		frmAccountDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAccountDetails.getContentPane().setLayout(null);
					
		//combo box controls
		cboAccounts = new JComboBox();
		cboAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedAccount = (Account) cboAccounts.getSelectedItem();
				refreshScreenData();
			}
		});
		cboAccounts.setBounds(133, 89, 350, 27);
		frmAccountDetails.getContentPane().add(cboAccounts);
		
		JLabel lblAccounts = new JLabel("Your Accounts:");
		lblAccounts.setBounds(20, 93, 109, 16);
		frmAccountDetails.getContentPane().add(lblAccounts);
		
		//account details
		JLabel lblAccountType = new JLabel("Account type:");
		lblAccountType.setBounds(20, 126, 97, 16);
		frmAccountDetails.getContentPane().add(lblAccountType);
		
		lblAccountTypeText = new JLabel("");
		lblAccountTypeText.setBounds(122, 126, 109, 16);
		frmAccountDetails.getContentPane().add(lblAccountTypeText);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setBounds(20, 151, 97, 16);
		frmAccountDetails.getContentPane().add(lblBalance);
		
		JLabel lblInterestRate = new JLabel("Interest Rate:");
		lblInterestRate.setBounds(20, 178, 97, 16);
		frmAccountDetails.getContentPane().add(lblInterestRate);
		
		JLabel lblPenalty = new JLabel("Penalty:");
		lblPenalty.setBounds(20, 200, 97, 16);
		frmAccountDetails.getContentPane().add(lblPenalty);
		
		txtAmount = new JTextField();
		txtAmount.setBounds(366, 122, 108, 28);
		frmAccountDetails.getContentPane().add(txtAmount);
		txtAmount.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(304, 125, 61, 16);
		frmAccountDetails.getContentPane().add(lblAmount);
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
					double amount = Double.parseDouble(txtAmount.getText());//convert text to double
					if(amount <= selectedAccount.getBalance()){
						selectedAccount.withdraw(amount);
						refreshScreenData();							
					}else{
						JOptionPane.showMessageDialog(null, "Insufficient funds!");
					}
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "You must enter a numeric value.");
					
				} catch (NullPointerException e2){
					JOptionPane.showMessageDialog(null, "Account not found.");
					ErrorLogger.log("Missing account information. Possible data referential integrity loss.");
					ErrorLogger.log("Withdaw attempt failed. No account found for customerID " + accountOwner.getCustomerID());
				}
			}
		});
		btnWithdraw.setBounds(385, 150, 89, 29);
		frmAccountDetails.getContentPane().add(btnWithdraw);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedAccount.deposit(Double.parseDouble(txtAmount.getText()));
					refreshScreenData();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "You must enter a numeric value.");
					
				} catch (NullPointerException e2){
					JOptionPane.showMessageDialog(null, "Account not found.");
					ErrorLogger.log("Missing account information. Possible data referential integrity loss.");
					ErrorLogger.log("Deposit attempt failed. No account found for customerID " + accountOwner.getCustomerID());
				}
			}
		});
		btnDeposit.setBounds(300, 150, 89, 29);
		frmAccountDetails.getContentPane().add(btnDeposit);
		
		JButton btnShowTransactions = new JButton("Show Transactions");
		btnShowTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TransactionView transListView = new TransactionView(selectedAccount);
			}
		});
		btnShowTransactions.setBounds(203, 227, 162, 29);
		frmAccountDetails.getContentPane().add(btnShowTransactions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(366, 227, 117, 29);
		frmAccountDetails.getContentPane().add(btnExit);	
		
		txtAreaMessage = new JTextArea();
		txtAreaMessage.setBackground(UIManager.getColor("window"));
		txtAreaMessage.setLineWrap(true);
		txtAreaMessage.setWrapStyleWord(true);
		txtAreaMessage.setText(getWelcomeMessage());
		txtAreaMessage.setBounds(20, 16, 463, 46);
		frmAccountDetails.getContentPane().add(txtAreaMessage);
		
		lblBalanceText = new JLabel("");
		lblBalanceText.setBounds(122, 154, 109, 16);
		frmAccountDetails.getContentPane().add(lblBalanceText);
		
		lblInterestRateText = new JLabel("");
		lblInterestRateText.setBounds(122, 178, 109, 16);
		frmAccountDetails.getContentPane().add(lblInterestRateText);
		
		lblPenaltyText = new JLabel("");
		lblPenaltyText.setBounds(122, 200, 109, 16);
		frmAccountDetails.getContentPane().add(lblPenaltyText);
		
		lblAccountStatus = new JLabel("");
		lblAccountStatus.setForeground(Color.RED);
		lblAccountStatus.setBounds(139, 67, 335, 16);
		lblAccountStatus.setVisible(false);
		frmAccountDetails.getContentPane().add(lblAccountStatus);
		
		//Load customer's accounts
		Bank b = new Bank();
		
		for(Account a: b.getCustomerAccounts(accountOwner.getCustomerID())){
			cboAccounts.addItem(a);
		}

		//finally display the form
		frmAccountDetails.setVisible(true);
		
		//timer to end application after a minute
		Timer timer = new Timer();
		 timer.schedule(new SessionEnd(), 60000);
	}
	
	/**
	 * Timed task  object to end the application after a certain time
	 * references subclass version from http://www.java2s.com/
	 *
	 */
	class SessionEnd extends TimerTask{
		public void run() {
			frmAccountDetails.setVisible(false);
			JOptionPane.showMessageDialog(null, "Your session has automaticlaly expired for security purposes. Application closing.");
			System.exit(0);
		}
	}
	
	/**
	 * Reloads the account information displayed
	 * on the form. Updates the current account's Type, Balance, Interest Rate
	 * and Penalty.
	 */
	private void refreshScreenData(){
		lblAccountTypeText.setText(selectedAccount.getType());
		lblBalanceText.setText(moneyFormat.format(selectedAccount.getBalance()));
		lblInterestRateText.setText(percentFormat.format(selectedAccount.getInterestRate()));
		lblPenaltyText.setText(moneyFormat.format(selectedAccount.getPenalty()));
		txtAmount.setText("");//clear the values
		
		//checks to see if the account is active. if not will disable controls
		if(!selectedAccount.getStatus().equals(selectedAccount.ACTIVE_ACCOUNT_STATUS)){
			lblAccountStatus.setText("This account is " + selectedAccount.getStatus() + ". You can not withdraw or deposit.");
			lblAccountStatus.setVisible(true);
			btnWithdraw.setEnabled(false);
			btnDeposit.setEnabled(false);
		}else{
			lblAccountStatus.setVisible(false);
			btnWithdraw.setEnabled(true);
			btnDeposit.setEnabled(true);
		}
	}
	
	/**
	 * Generates the welcome message for the account screen
	 * @return String
	 */
	private String getWelcomeMessage(){
		String welcomeMsg = accountOwner.getFirstName() + " " + accountOwner.getLastName();
		welcomeMsg +=", welcome to 1017 bank. You have the following permissions in this system: ";
		
		StringBuilder sb = new StringBuilder();
			for(String access: permissions){
				sb.append(access + ", ");
			}
			
			if(sb.length()>0){
				sb.setLength(sb.length() - 2);				
				welcomeMsg += sb.toString();
			}else{
				welcomeMsg += "none";
			}
	
		return welcomeMsg;
	}
}

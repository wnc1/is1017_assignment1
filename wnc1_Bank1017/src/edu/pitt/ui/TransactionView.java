package edu.pitt.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.bank.Account;
import edu.pitt.utilities.ErrorLogger;

import java.awt.FlowLayout;

/**
 * Provides the list view for transaction
 * @author bconner
 *
 */
public class TransactionView {
	private static final String COL_HEADER_TYPE = "Transaction Type";
	private static final String COL_HEADER_DATE = "Date/Time";
	private static final String COL_HEADER_AMOUNT = "Amount";
	private JFrame frmTransactions;
	private JScrollPane transactionPane;
	private JTable tblTransactions;
	private Account currentAccount;
	
	/**
	 * Create the application.
	 */
	public TransactionView(Account a) {
		currentAccount = a;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {	
			frmTransactions = new JFrame();
			frmTransactions.setTitle("Account Transactions");
			frmTransactions.setBounds(100, 100, 450, 329);
			frmTransactions.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					
			//account list and object should be passed here
			transactionPane = new JScrollPane();
			frmTransactions.getContentPane().add(transactionPane);
			
			//create button Panel to house any controls or buttons
			JPanel buttonPanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			
			
			JButton btnClose = new JButton("Close");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//close and dispose window
					frmTransactions.setVisible(false);
					frmTransactions.dispose();
				}
			});	
			
			buttonPanel.add(btnClose);
			
			frmTransactions.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
			
			//add column header names
			Vector<String> columns = new Vector<String>();
			columns.add(COL_HEADER_TYPE);
			columns.add(COL_HEADER_DATE);
			columns.add(COL_HEADER_AMOUNT);	
			
			// add data and columns names into a table model
			DefaultTableModel transactionList = new DefaultTableModel(currentAccount.renderTransactionVector(),columns);
			
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.black);
			transactionPane.setViewportView(tblTransactions);
			frmTransactions.setVisible(true);
			
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "No account or transactions found.");
			ErrorLogger.log("Missing account information. Possible data referential integrity loss.");
			ErrorLogger.log("Transaction view attempt failed. No account found for logged in user.");
			currentAccount = null;
		}
				
		
	}

}

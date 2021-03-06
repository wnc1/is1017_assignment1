package edu.pitt.ui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import edu.pitt.bank.Security;
import edu.pitt.bank.Customer;
import edu.pitt.utilities.ErrorLogger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

/**
 * provides the login view for the application.
 * @author bconner
 *
 */
public class LoginView {

	private JFrame frmLogin;
	private JTextField txtLoginName;
	private JLabel lblPassword;
	private JPasswordField pwdPinNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Account Login");
		frmLogin.setBounds(100, 100, 450, 209);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		//button controls
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String username = txtLoginName.getText();	
					String pwd = new String(pwdPinNumber.getPassword());//convert char[] from JpasswordField to String
					int pin = Integer.parseInt(pwd);//convert string to integer
					
					Security session = new Security();
					Customer user = session.validateLogin(username, pin);
					if(user !=null){
						ArrayList<String> permissions = session.listUserGroups(user.getCustomerID());//get permissions
						AccountDetailsView accDetails = new AccountDetailsView(user,permissions);
						frmLogin.setVisible(false);					
					}else{
						JOptionPane.showMessageDialog(null, "Invalid Login.");
						//TODO: should we log every exception?
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Pin number must be numeric.");
				}
			}
		});	
		btnLogin.setBounds(222, 121, 117, 29);
		frmLogin.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(334, 121, 75, 29);
		frmLogin.getContentPane().add(btnExit);	
		
		//login name field controls
		txtLoginName = new JTextField();
		txtLoginName.setBounds(158, 41, 251, 28);
		txtLoginName.setColumns(10);
		frmLogin.getContentPane().add(txtLoginName);
		
		JLabel lblLoginName = new JLabel("Login Name:");
		lblLoginName.setBounds(33, 47, 117, 16);
		frmLogin.getContentPane().add(lblLoginName);
		
		//password controls
		lblPassword = new JLabel("Pin Number:");
		lblPassword.setBounds(33, 87, 117, 16);
		frmLogin.getContentPane().add(lblPassword);
		
		pwdPinNumber = new JPasswordField();
		pwdPinNumber.setBounds(158, 81, 251, 28);
		frmLogin.getContentPane().add(pwdPinNumber);	
		
	}
}

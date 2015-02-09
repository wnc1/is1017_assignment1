package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import edu.pitt.utilities.*;

public class Test {

	public static void main(String[] args) {
		DbUtilities db = new MySqlUtilities();
		String sql = "SELECT * FROM account;";
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				System.out.println(rs.getString("accountID"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
		
		//in class stuff week 2
//		DbUtilities db = new DbUtilities();
//		String loginName = "nmarcus";
//		int pin = 8125;
//		
//		String sql = "SELECT lastName, loginName, pin FROM customer WHERE loginName='" + loginName + "' AND pin=" + pin +";";
//		
//		try{
//			ResultSet rs = db.getResultSet(sql);	
//			while(rs.next()){
//				String lName = rs.getString("lastName");
//				String fName = rs.getString("loginName");
//
//				System.out.println(lName + ", " + fName);
//			}			
//		}catch(SQLException e){
//			e.printStackTrace();
//			ErrorLogger.log(e.getMessage());
//		}
	}

}

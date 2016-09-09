package hibertest;

import java.sql.Connection;
import java.sql.DriverManager;

public class HiberTestConnect {

	public static void main(String[] args) {
		try{
			String jdbcUrl = "jdbc:mysql://192.168.0.2:3306/hb_student_tracker?useSSL=false";
			String user = "hbstudent";
			String pwd = "hbstudent";
			
			System.out.println("Connecting to " + jdbcUrl);
//			Class.forName("com.mysql.jdbc.Driver");
//			System.out.println("After class forname()");
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, pwd);
			
			System.out.println("Connected!");
			myConn.close();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}

	}

}

package com.app.onetomany;



import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class JdbcUtility {

	private static String url="jdbc:mysql://localhost:3306/rdbmsjoins2";
	private static String username="root";
	private static String password="root";
	private static Connection con=null;
	
   static {
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
		   con=DriverManager.getConnection(url, username, password);
	   }catch(ClassNotFoundException | SQLException e) {
		   e.printStackTrace();
	   }
   }   
	   public static Connection getConnection() {
		   return con;
	   }
   
}

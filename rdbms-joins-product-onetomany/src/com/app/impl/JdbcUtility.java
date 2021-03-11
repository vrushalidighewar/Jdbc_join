package com.app.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtility {

	private static String url = "jdbc:mysql://localhost:3306/rdbmsjoins3";
	private static String username = "root";
	private static String password = "root";
	private static Connection con = null;
	private static CallableStatement cs=null;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}
	
	public static ResultSet executeQuery(String sql) throws SQLException {
		cs=con.prepareCall(sql);
		return cs.executeQuery();
	}
	public static boolean execute(String sql) throws SQLException {
		cs=con.prepareCall(sql);
		return cs.execute();
	}
}

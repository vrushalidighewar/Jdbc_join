package com.app.client;

import java.sql.SQLException;

import com.app.impl.JdbcTest;

public class Main {

	public static void main(String[] args) throws SQLException {

		JdbcTest t=new JdbcTest();
		t.insert();
		t.select();
		t.addToCart();
		t.createBill();

	}

}

package com.app.client;

import com.app.impl.JdbcTest;

public class Test {

	public static void main(String[] args) {

		JdbcTest test=new JdbcTest();
		test.insert();
		test.select();
	}

}

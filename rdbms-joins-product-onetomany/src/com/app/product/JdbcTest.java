package com.app.product;

import java.util.Scanner;

public class JdbcTest {

	Scanner sc=new Scanner(System.in);
	
	public void addColor() {
		System.out.println("How many colors do you want");
		int noofcol=sc.nextInt();
		for(int i=0;i<noofcol;i++) {
			Color cc=new Color();
			System.out.println("Enter color ");
			//cc.setCname(cname);
			
		}
	}
	public static void main(String[] args) {


	}

}

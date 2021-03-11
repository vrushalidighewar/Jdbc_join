package com.app.onetomany;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

	Scanner sc = new Scanner(System.in);
	private boolean flag=Boolean.FALSE;
	private static Connection con=null;

	public void insert() throws SQLException {
		System.out.println("How many employee do you want to add");
		int noofemp = sc.nextInt();
		List<Employee> employee = new ArrayList();
		for (int i = 0; i < noofemp; i++) {
			Employee emp = new Employee();
			System.out.println("Enter name of employee");
			emp.setName(sc.next());
			//employee.add(emp);

			System.out.println("Enter no of addresses");
			int noofadd = sc.nextInt();
			List<Address> address = new ArrayList();
			for (int j = 0; j < noofadd; j++) {
				Address addr = new Address();
				System.out.println("Enter city");
				addr.setCity(sc.next());
				System.out.println("Enter pincode");
				addr.setPincode(sc.nextInt());
				address.add(addr);
				flag=Boolean.TRUE;
				
			}
			 emp.setAddress(address);
			 employee.add(emp);
		}
		con=JdbcUtility.getConnection();
		for(Employee emp : employee) {
		PreparedStatement ps=con.prepareStatement("insert into employee(name) values(?)");
	       ps.setString(1,emp.getName());
	       int result=ps.executeUpdate();
	       if(result > 0) {
	    	   ps=con.prepareStatement("select max(id) from employee");
	    	   ResultSet rs=ps.executeQuery();
	    	   if(rs.next())
	    		   emp.setId(rs.getInt(1));
	    	   for(Address addr : emp.getAddress()) {
	    		   PreparedStatement ps1=con.prepareStatement("insert into address(city,pincode,eid) values(?,?,?)");
	    		   ps1.setString(1, addr.getCity());
	    		   ps1.setInt(2, addr.getPincode());
	    		   ps1.setInt(3, emp.getId());
	    		   int result1=ps1.executeUpdate();
	    		   flag=Boolean.TRUE;
	    	   }
	       }
		}
		if(flag=Boolean.TRUE) {
			System.out.println("Successful");
		}else
			System.out.println("failed"); 
			
	}
	//______________________________________________________________________________
	
	public void select() throws SQLException {
		
		List<Employee> employee=new ArrayList();
		con=JdbcUtility.getConnection();
		PreparedStatement ps=con.prepareStatement("select * from employee ");
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			Employee emp=new Employee();
			emp.setId(rs.getInt(1));
			emp.setName(rs.getString(2));
			employee.add(emp);
		}
		for(Employee emp : employee) {
			List<Address> address=new ArrayList<Address>();
			PreparedStatement ps1=con.prepareStatement("select a.id, a.city, a.pincode from employee e inner join address a on e.id=a.eid where a.eid=" +emp.getId());
					                                        
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()) {
				Address addr=new Address();
				addr.setAid(rs1.getInt(1));
				addr.setCity(rs1.getString(2));
				addr.setPincode(rs1.getInt(3));
				//addr.setEmployee(emp);             //has-a
				address.add(addr);
			}
			emp.setAddress(address);                     //has-a
		}
		for(Employee emp : employee) {
			System.out.println(emp.getId()+ "\t" +emp.getName());
			for(Address addr : emp.getAddress()) {
				System.out.println("\t" +addr.getAid()+ "\t"  +addr.getCity()+ "\t" +addr.getPincode());
			}
		}
		
	}
	//______________________________________________________________________________
        public static void main(String[] args) throws SQLException {
			
        	  Test test=new Test();
        	  test.insert();
        	  test.select();
		}
}

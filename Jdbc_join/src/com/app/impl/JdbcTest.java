package com.app.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.model.Registration;
import com.app.model.Student;

public class JdbcTest {

	private boolean flag = Boolean.FALSE;
	Scanner sc = new Scanner(System.in);

	public void insert() {
		Connection con = JdbcUtility.getConnection();
		try (PreparedStatement ps = con.prepareStatement("insert into student(name,lastname) values(?,?)");) {
			System.out.println("How many student do you want to add");
			int num = sc.nextInt();
			for (int i = 0; i < num; i++) {
				Student s = new Student();
				System.out.println("Enter name");
				s.setName(sc.next());
				System.out.println("Enter lastname");
				s.setLastname(sc.next());

				ps.setString(1, s.getName());
				ps.setString(2, s.getLastname());
				int result = ps.executeUpdate();
				if (result > 0) {
					PreparedStatement ps1 = con.prepareStatement("select max(id) from student");
					ResultSet rs = ps1.executeQuery();
					if (rs.next()) {
						s.setSid(rs.getInt(1));

						Registration r = new Registration();
						PreparedStatement ps2 = con.prepareStatement("insert into registration(id,rollNo) values(?,?)");
						System.out.println("Enter roll no");
						r.setRollNo(sc.nextInt());

						s.setRegistration(r);

						ps2.setInt(1, s.getSid());
						ps2.setInt(2, s.getRegistration().getRollNo());

						ps2.executeUpdate();
						flag = Boolean.TRUE;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == Boolean.TRUE)
			System.out.println("inserted successfully....");
		else
			System.out.println("failed.....");
	}

	public void select() {
		List<Student> list = new ArrayList();
		Connection con=JdbcUtility.getConnection();
		try(PreparedStatement ps=con.prepareStatement("select * from student stud inner join registration reg on stud.id=reg.id");){
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Student s=new Student();
				s.setSid(rs.getInt(1));
				s.setName(rs.getString(2));
				s.setLastname(rs.getString(3));
				
				Registration r=new Registration();
				r.setRid(rs.getInt(4));
				r.setRollNo(rs.getInt(5));
				
				s.setRegistration(r);
				
				list.add(s);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(Student s : list) {
			System.out.println(s.getSid()+ "\t" +s.getName()+ "\t" +s.getLastname()+ "\t" +s.getRegistration().getRid()+ "\t" +s.getRegistration().getRollNo());
		}

	}
}

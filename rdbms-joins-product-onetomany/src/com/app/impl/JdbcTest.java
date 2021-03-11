package com.app.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.model.Color;
import com.app.model.Product;
import com.app.model.Size;

public class JdbcTest {
	Scanner sc = new Scanner(System.in);
	private static Connection con = null;
	private boolean flag = Boolean.FALSE;
	private List cart = null;
	List<Product> products = new ArrayList();
	

	public void insert() throws SQLException {

		List<Color> colorr = new ArrayList();
		List<Size> sizee = new ArrayList();
		List<Product> products = new ArrayList();

		Color cc = new Color();
		Size ss = new Size();
		Connection con = JdbcUtility.getConnection();

		System.out.println("How many colors do you want");
		int noofcol = sc.nextInt();
		for (int i = 0; i < noofcol; i++) {
			System.out.println("Enter name");
			cc.setCname(sc.next());

			PreparedStatement ps = con.prepareStatement("insert into color(cname) values (?)");
			ps.setString(1, cc.getCname());
			int result = ps.executeUpdate();
			if (result > 0) {
				PreparedStatement ps1 = con.prepareStatement("select max(cid) from color");
				ResultSet rs = ps1.executeQuery();
				if (rs.next()) {
					cc.setCid(rs.getInt(1));
					colorr.add(cc);
				}
			}
		}

		System.out.println("How many size options you want");
		int noofsz = sc.nextInt();
		for (int j = 0; j < noofsz; j++) {
			System.out.println("Enter size");
			ss.setSname(sc.next());

			PreparedStatement ps = con.prepareStatement("insert into size(sname) values (?)");
			ps.setString(1, ss.getSname());
			int result = ps.executeUpdate();
			if (result > 0) {
				PreparedStatement ps1 = con.prepareStatement("select max(sid) from size");
				ResultSet rs = ps1.executeQuery();
				if (rs.next()) {
					ss.setSid(rs.getInt(1));
					sizee.add(ss);
				}
			}
		}

		Product pp = new Product();
		System.out.println("How many products you want");
		int noofprod = sc.nextInt();
		for (int a = 0; a < noofprod; a++) {
			System.out.println("Enter product");
			pp.setPname(sc.next());
			System.out.println("Enter price");
			pp.setPrice(sc.nextDouble());
		}
		pp.setColorr(colorr);
		pp.setSizee(sizee);
		products.add(pp);

		for (Product pp1 : products) {
			PreparedStatement ps = con
					.prepareStatement("insert into product(pname,price,procid,prosid) values (?,?,?,?)");
			ps.setString(1, pp1.getPname());
			ps.setDouble(2, pp1.getPrice());
			ps.setInt(3, cc.getCid());
			ps.setInt(4, ss.getSid());
			ps.executeUpdate();

			flag = Boolean.TRUE;
		}
		if (flag = Boolean.TRUE) {
			System.out.println("Successful");
		} else
			System.out.println("failed");
	}
//________________________________________________________________________
	
	public void select() throws SQLException {
		
		PreparedStatement ps=null;
		
		Connection con=JdbcUtility.getConnection();
		
		ps=con.prepareStatement("select * from product");
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			Product pp=new Product();
			pp.setPid(rs.getInt(1));
			pp.setPname(rs.getString(2));
			pp.setPrice(rs.getDouble(3));
			products.add(pp);
		}
		
		for(Product pp1 : products) {
			List<Color> colorr=new ArrayList();
			ps=con.prepareStatement("select c.cid,c.cname from product p inner join color c on c.cid=p.procid where p.pid=" +pp1.getPid());
			ResultSet rs1=ps.executeQuery();
			while(rs1.next()) {
				Color cc=new Color();
				cc.setCid(rs1.getInt(1));
				cc.setCname(rs1.getString(2));
				colorr.add(cc);
			}
			pp1.setColorr(colorr);
			
			List<Size> sizee=new ArrayList();
			ps=con.prepareStatement("select s.sid,s.sname from product p inner join size s on s.sid=p.prosid where p.pid=" +pp1.getPid());
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()) {
				Size ss=new Size();
				ss.setSid(rs2.getInt(1));
				ss.setSname(rs2.getString(2));
				sizee.add(ss);
			}
			pp1.setSizee(sizee);
		}
		
		for(Product pp1 : products) {
			System.out.println(pp1.getPid()+ "\t" +pp1.getPname()+ "\t" +pp1.getPrice());
			for(Color cc : pp1.getColorr()) {
				System.out.println("\t\t" +cc.getCid()+ "\t" +cc.getCname());
				for(Size ss : pp1.getSizee()) {
					System.out.println("\t\t\t" +ss.getSid()+ "\t" +ss.getSname());
				}
			}
		}
	}
//________________________________________________________________________________________
	
	public void addToCart() {
		
		cart=new ArrayList();
		
		System.out.println("How many products you want to add");
		int no=sc.nextInt();
		for(int b=0;b<no;b++) {
			System.out.println("Enter product id");
			int z=sc.nextInt();
			for(Object obj : products) {
				Product p=(Product) obj;
				if(p.getPid()== z) {
					cart.add(p);
				}
			}
		}
	}
//_____________________________________________________________________________________	
	
	public void createBill() {
		
		int Total=0;
		
		for(Object ob : cart) {
			Product pp1=(Product) ob;
			System.out.println(pp1.getPid()+ "\t" +pp1.getPname()+ "\t" +pp1.getPrice());
			Total+=pp1.getPrice();
		}
		System.out.println("Grand Total is:" +Total);
	}
}









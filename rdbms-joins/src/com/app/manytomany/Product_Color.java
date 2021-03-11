package com.app.manytomany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product_Color {

	Scanner sc = new Scanner(System.in);
	private boolean flag = Boolean.FALSE;

	public void addColor() throws SQLException {

		Connection con = JdbcUtility.getConnection();
		System.out.println("How many colors do you want");
		int noofcol = sc.nextInt();
		for (int i = 0; i < noofcol; i++) {
			Color cc = new Color();
			System.out.println("Enter color");
			cc.setCname(sc.next());
			PreparedStatement ps = con.prepareStatement("insert into color(c_name) values(?)"); // table name
			ps.setString(1, cc.getCname());
			ps.executeUpdate();
			flag = Boolean.TRUE;
		}
		if (flag = Boolean.TRUE) {
			System.out.println("Successful");
		} else
			System.out.println("Failed");
	}
//____________________________________________________________

	public void displayColor() throws SQLException {

		List<Color> colorList = new ArrayList();
		Connection con = JdbcUtility.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from color");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Color cc = new Color();
			cc.setCid(rs.getInt(1));
			cc.setCname(rs.getString(2));
			colorList.add(cc);
		}
		for (Color cc : colorList) {
			System.out.println(cc.getCid() + "\t" + cc.getCname());
		}
	}
//____________________________________________________________

	public void addProduct() throws SQLException {

		List<Product> products = new ArrayList();

		System.out.println("How many products do you want");
		int num = sc.nextInt();
		for (int j = 0; j < num; j++) {
			Product pp = new Product();
			System.out.println("Enter product");
			pp.setPname(sc.next());
			System.out.println("Enter price");
			pp.setPrice(sc.nextDouble());

			System.out.println("_______________________________");
			displayColor();
			System.out.println("_______________________________");
			System.out.println("How many colors you want to allocate");
			int n = sc.nextInt();
			List<Color> allocatedcolorList = new ArrayList();
			for (int m = 0; m < n; m++) {
				Color cc = new Color();
				System.out.println("Enter color id");
				cc.setCid(sc.nextInt());
				allocatedcolorList.add(cc);
			}
			pp.setColors(allocatedcolorList);
			products.add(pp);
		}
		Connection con = JdbcUtility.getConnection();
		PreparedStatement ps = con.prepareStatement("insert into product(pname,price) values (?,?)");
		for (Product pp2 : products) {
			ps.setString(1, pp2.getPname());
			ps.setDouble(2, pp2.getPrice());
			int result = ps.executeUpdate();

			if (result > 0) {
				PreparedStatement ps1 = con.prepareStatement("select max(id) from product");
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next()) {
					pp2.setPid(rs1.getInt(1));
					PreparedStatement ps2 = con.prepareStatement("insert into product_color(prod_id,color_id) values (?,?)");
					for (Color cc : pp2.getColors()) {

						ps2.setInt(1, pp2.getPid());
						ps2.setInt(2, cc.getCid());
						ps2.executeUpdate();
						flag = Boolean.TRUE;

					}
					if (flag == Boolean.TRUE) {
						System.out.println("Successful");
					} else
						System.out.println("failed");
				}
			}
		}
	}
//___________________________________________________________________
	
	public void displayProduct() throws SQLException {
		
		List<Product> products=new ArrayList();
		Connection con=JdbcUtility.getConnection();
		PreparedStatement ps=con.prepareStatement("select * from product");
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			Product pp=new Product();
			pp.setPid(rs.getInt(1));
			pp.setPname(rs.getString(2));
			pp.setPrice(rs.getDouble(3));
			products.add(pp);
		}
		for(Product pp : products) {
			List<Color> colors=new ArrayList();
			PreparedStatement ps1=con.prepareStatement("select c.id,c.c_name from product p inner join color c inner join product_color pc on p.id=pc.prod_id and c.id=pc.color_id where pc.prod_id=" +pp.getPid());
		    ResultSet rs1=ps1.executeQuery();
			while(rs1.next()) {
				Color cc=new Color();

				cc.setCid(rs1.getInt(1));
				cc.setCname(rs1.getString(2));
				colors.add(cc);
			}
			pp.setColors(colors);
		}
		for(Product pp : products) {
			System.out.println(pp.getPid()+ "\t" +pp.getPname()+ "\t" +pp.getPrice());
			for(Color cc : pp.getColors()) {
				System.out.println("\t\t" +cc.getCid()+ "\t" +cc.getCname());
			}
		}	
		
	}
//___________________________________________________________________	
	public static void main(String[] args) throws SQLException {
		Product_Color pc = new Product_Color();
		pc.addColor();
		pc.displayColor();
		pc.addProduct();
		pc.displayProduct();
	}

}

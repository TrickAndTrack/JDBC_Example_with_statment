package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class productManagmentPrepare {
	private Boolean flag = Boolean.FALSE;
	private static Connection con = null;
//changes
	private PreparedStatement ps = null;
	private ResultSet rs = null ;
	
	private static final String URL="jdbc:mysql://localhost:3306/nz";
	private static final String USERNAME="root";
	private static final String PWD="root";
	
	
	static {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(URL,USERNAME,PWD);
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}		
		}
	
	Scanner scanner = new Scanner(System.in);
	
	public void InsertProduct() throws SQLException {
		List<product> productsq =  new ArrayList<product>();
		System.out.println("Enter Your Total Number of Product");
		int no = scanner.nextInt();
		for(int i=0; i < no; i++) {
			
			product products = new product();

			System.out.println("Enter name");
			products.setName(scanner.next());
			
			System.out.println("Enter Product_name");
			products.setProduct_name(scanner.next());
			
			System.out.println("Enter product_qlt");
			products.setProduct_qlt(scanner.next());
			
			productsq.add(products);
		}
//changes
		ps=con.prepareStatement("insert into product(name,product_name,product_qlt) value(?,?,?)");
		for (product product : productsq) { 
			//smt.executeUpdate("insert into product(name,product_name,product_qlt) value('"+product.getName() +"','" + product.getProduct_name()+"','" + product.getProduct_qlt()+"')" );
//changes
			ps.setString(1, product.getName());
			ps.setString(2, product.getProduct_name());
			ps.setString(3, product.getProduct_qlt());
			ps.executeUpdate();
			flag = Boolean.TRUE; 
  		}
		if(flag) 
			System.out.println("success");
		else 
		System.out.println("failed");
	}
	public void DisplayProduct() throws SQLException {
		List<product> productsq =  new ArrayList<product>();
//changes
		ps=con.prepareStatement("select * from product");
		rs =ps.executeQuery();
		
		while(rs.next()) {
			
			product products = new product();
			products.setId(rs.getInt(1));
			products.setName(rs.getString(2));
			products.setProduct_name(rs.getString(3));
			products.setProduct_qlt(rs.getString(4));
			
			productsq.add(products);	
			
			System.out.println("--------------------------------");
		}
		for (product product : productsq) {
		System.out.println(product);	
		}
		System.out.println("--------------------------------");
	} 
	public void UpdateProduct() throws SQLException {
		product products = new product();

		System.out.println("Enter Product id which you want to update");
		products.setId(scanner.nextInt());
		System.out.println("Enter name");
		products.setName(scanner.next());
		System.out.println("Enter Product_name");
		products.setName(scanner.next());
		System.out.println("Enter product_qlt");
		products.setProduct_qlt(scanner.next());
//changes
		ps=con.prepareStatement("update product set name=?,Product_name=?,Product_qlt=? where id=?");
		ps.setString(1, products.getName());
		ps.setString(2, products.getProduct_name());
		ps.setString(3, products.getProduct_qlt());
		ps.setInt(4, products.getId()); 
//changes		
		int result = ps.executeUpdate();
		
		if (result >0) {
			System.out.println("Successfully updated");
		} else {
			System.out.println("Not updated");
		}
		}
	
	
	public void DeleteProduct() throws SQLException {
//changes
		ps=con.prepareStatement("delete from product where id=?");
		System.out.println("Enter product id Which you want to delete");
		int id = scanner.nextInt();
		ps.setInt(1, id);
//changes
		
		int result = ps.executeUpdate();
		if (result >0) {
			System.out.println("Successfully delete");
		} else {
			System.out.println("Not delete");
		}
	}
	public static void main(String[] args) throws SQLException {
		Scanner scanner=new Scanner(System.in);
		productManagmentPrepare PRM = new productManagmentPrepare();
		
		while(true) {
		System.out.println("1) Add Product");   
		System.out.println("2) Update Product");
		System.out.println("3) Delete Product");
		System.out.println("4) Display Product");
		System.out.println("5) Exit");
		
		System.out.println("Select any one Option");
		int option=scanner.nextInt();
		
		switch (option) {
		case 1:
				PRM.InsertProduct();
			break;
		case 2:
			PRM.UpdateProduct();
			break;
		case 3:
			PRM.DeleteProduct();
			break;
		case 4:
			PRM.DisplayProduct();
			break;
		case 5:
			System.out.println("Yor are Exit");
			System.exit(0);
			break;

		default:
			System.out.println("You Enter Wrong Input Try Letter");
			break;
		}
		}
		
	}

}

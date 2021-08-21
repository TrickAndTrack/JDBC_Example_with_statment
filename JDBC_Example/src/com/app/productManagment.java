package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class productManagment {
	private Boolean flag = Boolean.FALSE;
	private static Connection con = null;
	private Statement smt = null;
	private ResultSet rs = null;
	
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
		smt=con.createStatement();
		for (product product : productsq) { 
			smt.executeUpdate("insert into product(name,product_name,product_qlt) value('"+product.getName() +"','" + product.getProduct_name()+"','" + product.getProduct_qlt()+"')" );
			
			flag = Boolean.TRUE; 
		}
		if(flag) 
			System.out.println("success");
		else 
		System.out.println("failed");
	}
	public void DisplayProduct() throws SQLException {
		List<product> productsq =  new ArrayList<product>();
		smt=con.createStatement();
		rs =smt.executeQuery("select * from product");
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
		
		smt = con.createStatement();
		int result = smt.executeUpdate("update product set name= ' "+products.getName() +"',"+"Product_name='"+products.getProduct_name()+"',"+"Product_qlt='"+products.getProduct_qlt()+"'where id="+products.getId()+"");
		
		if (result >0) {
			System.out.println("Successfully updated");
		} else {
			System.out.println("Not updated");
		}
		}
	public void DeleteProduct() throws SQLException {
		smt = con.createStatement();
		System.out.println("Enter product id Which you want to delete");
		int id = scanner.nextInt();
		int result = smt.executeUpdate("delete from product where id=" +id);
		
		
		if (result >0) {
			System.out.println("Successfully delete");
		} else {
			System.out.println("Not delete");
		}
	}
	public static void main(String[] args) throws SQLException {
		Scanner scanner=new Scanner(System.in);
		productManagment PRM = new productManagment();
		
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

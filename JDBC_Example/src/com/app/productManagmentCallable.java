package com.app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class productManagmentCallable {
	private Boolean flag = Boolean.FALSE;
	private static Connection con = null;
//changes
	private CallableStatement cs = null;
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
		cs=con.prepareCall("{call InsertProduct(?,?,?)}");
		for (product product : productsq) { 
			cs.setString(1, product.getName());
			cs.setString(2, product.getProduct_name());
			
//ethe je get madhe name ahe tech same name workbench madhe phije nhi tr error yeto  jas insert product chy at madhe column madhe 			
			cs.setString(3, product.getProduct_qlt());
			cs.executeUpdate();
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
		cs=con.prepareCall("{ call DisplayProduct()}");
		rs =cs.executeQuery();
		
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
		products.setProduct_name(scanner.next());
		System.out.println("Enter product_qlt");
		products.setProduct_qlt(scanner.next());
//changes
		cs=con.prepareCall("{call UpdateProduct( ?,?,?,?) }");
		cs.setString(2, products.getName());
		cs.setString(3, products.getProduct_name());
		cs.setString(4, products.getProduct_qlt());
		cs.setInt(1, products.getId()); 		
		int result = cs.executeUpdate();
		
		if (result >0) {
			System.out.println("Successfully updated");
		} else {
			System.out.println("Not updated");
		}
		}
	
	
	public void DeleteProduct() throws SQLException {
//changes
		cs=con.prepareCall("{call DeleteProduct(?)}");
		System.out.println("Enter product id Which you want to delete");
		int id = scanner.nextInt();
		cs.setInt(1, id);		
		int result = cs.executeUpdate();
		if (result >0) {
			System.out.println("Successfully delete");
		} else {
			System.out.println("Not delete");
		}
	}
	public static void main(String[] args) throws SQLException {
		Scanner scanner=new Scanner(System.in);
		productManagmentCallable PRM = new productManagmentCallable();
		
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
			try {
				PRM.InsertProduct();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			
			try {
				PRM.UpdateProduct();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			
			try {
				PRM.DeleteProduct();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			
			try {
				PRM.DisplayProduct();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

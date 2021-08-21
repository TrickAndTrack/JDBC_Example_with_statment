package com.app;

public class product {

	
	private int id;
	private String name;
	private String product_name;
	private String product_qlt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_qlt() {
		return product_qlt;
	}
	public void setProduct_qlt(String product_qlt) {
		this.product_qlt = product_qlt;
	}
	@Override
	public String toString() {
		return "| id=" + id + ", name=" + name + ", product_name=" + product_name + ", product_qlt="
				+ product_qlt + "  |";
	}
}

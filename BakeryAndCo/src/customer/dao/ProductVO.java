package customer.dao;

import java.sql.Date;

public class ProductVO {
	private int prodNum; 
	private String scategory, storename, prodName;
	private int price, Inventory;
	private String description;
	private Date registerdate;
	private int Rating;
	public ProductVO() {
		super();
	}
	/*
	int prodNum, String scategory, String storename, String prodName, int price, int inventory,
	String description, Date registerdate, int rating
	*/
	
	// prodNum,  scategory,  storename,  prodName,  price,  inventory, description,  registerdate,  rating;
	
	public ProductVO(int prodNum, String scategory, String storename, String prodName, int price, int inventory,
			String description, Date registerdate, int rating) {
		super();
		this.prodNum = prodNum;
		this.scategory = scategory;
		this.storename = storename;
		this.prodName = prodName;
		this.price = price;
		Inventory = inventory;
		this.description = description;
		this.registerdate = registerdate;
		Rating = rating;
	}
	public int getProdNum() {
		return prodNum;
	}
	public void setProdNum(int prodNum) {
		this.prodNum = prodNum;
	}
	public String getScategory() {
		return scategory;
	}
	public void setScategory(String scategory) {
		this.scategory = scategory;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getInventory() {
		return Inventory;
	}
	public void setInventory(int inventory) {
		Inventory = inventory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegisterdate() {
		return registerdate;
	}
	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}
	public int getRating() {
		return Rating;
	}
	public void setRating(int rating) {
		Rating = rating;
	}
	@Override
	public String toString() {
		return "ProductVO [prodNum=" + prodNum + ", scategory=" + scategory + ", storename=" + storename + ", prodName="
				+ prodName + ", price=" + price + ", Inventory=" + Inventory + ", description=" + description
				+ ", registerdate=" + registerdate + ", Rating=" + Rating + "]";
	} 
	
	
	

}

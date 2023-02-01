package customer.dao;

import java.util.Date;

public class ProductVO {

	private int prodNum, price,Inventory,Rating;
	private String storename,prodName,description,category;
	private Date registerdate;

	public ProductVO() {
		super();
	}

	public ProductVO(int prodNum, String category, String storename, String prodName, int price, int inventory, String description, 
		  Date registerdate , int rating) {
		super();
		this.prodNum = prodNum;
		this.price = price;
		Inventory = inventory;
		Rating = rating;
		this.storename = storename;
		this.prodName = prodName;
		this.description = description;
		this.category = category;
		this.registerdate = registerdate;
	}

	public int getProNum() {
		return prodNum;
	}

	public void setProNum(int proNum) {
		this.prodNum = proNum;
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

	public int getRating() {
		return Rating;
	}

	public void setRating(int rating) {
		Rating = rating;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	@Override
	public String toString() {



		return "ProductVO : [ID: " + prodNum + ", 가게이름" +  storename +  ", 제품이름: " + prodName + "가격: " + price + ", 재고량: " + Inventory +", 상품설명: " + description + ", 등록일: " + registerdate +", 별점: " + Rating  +"]";
	}




}//ProductVO
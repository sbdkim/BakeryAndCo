package customer.dao;

import java.util.Date;

public class ProductVO {

	private int prodNum, price, inventory, rating;
	private String storeName, prodName, description, category;
	private Date registerdate;

	public ProductVO() {
		super();
	}

	public ProductVO(String category, String storeName) {
		super();
		this.category = category;
		this.storeName = storeName;
	}
	
	
	public ProductVO(int prodNum, String category, String storeName, String prodName) {
		super();
		this.prodNum = prodNum;
		this.category = category;
		this.storeName = storeName;
		this.prodName = prodName;
	}
	
	
	public ProductVO(int prodNum, String category, String storeName, String prodName, int price, int inventory,
			String description, Date registerdate, int rating) {
		super();
		this.prodNum = prodNum;
		this.price = price;
		this.inventory = inventory;
		this.rating = rating;
		this.storeName = storeName;
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
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getstoreName() {
		return storeName;
	}

	public void setstoreName(String storeName) {
		this.storeName = storeName;
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

		return "ProductVO : [ID: " + prodNum + ", Category: " + category + ", 가게이름: " + storeName + ", 제품이름: " + prodName + ", 가격: " + price + ", 재고량: "
				+ inventory + ", 상품설명: " + description + ", 등록일: " + registerdate + ", 별점: " + rating + "]";
	}

}// ProductVO

package customer0203.dao;

public class CartVO {

	String userID, prodName;
	int quantity;
	int price;
	
	public CartVO() {
		super();
	}
	
	public CartVO(String userID, String prodName, int price, int quantity) {
		super();
		this.userID = userID;
		this.prodName = prodName;
		this.price = price;
		this.quantity = quantity;
		
	}

	
	
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	@Override
	public String toString() {
		return "CartVO : [ID: "  + userID + ", 제품명: " + prodName + ", 갯수: " + quantity + ", 가격: " + price +"]";
	}
	
	
	
	
	
	
}

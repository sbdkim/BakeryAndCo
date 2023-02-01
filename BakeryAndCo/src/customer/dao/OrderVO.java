package customer.dao;

import java.sql.Date;

public class OrderVO {
	int orderNo, prodNum, orderCompleted, quantity, cost;
	String prodName, storeName, userID, shippingCost, review;
	Date orderDate;

	public OrderVO() {
		super();
	}

	public OrderVO(int orderNo, int prodNum, String prodName, String storeName, String userID, int quantity, int cost,
			String shippingCost, String review, int orderCompleted, Date orderDate) {
		super();
		this.orderNo = orderNo;
		this.prodNum = prodNum;
		this.prodName = prodName;
		this.storeName = storeName;
		this.userID = userID;
		this.quantity = quantity;
		this.cost = cost;
		this.shippingCost = shippingCost;
		this.review = review;
		this.orderCompleted = orderCompleted;
		this.orderDate = orderDate;
	}

	public int getorderNo() {
		return orderNo;
	}

	public void setorderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getProdNum() {
		return prodNum;
	}

	public void setProdNum(int prodNum) {
		this.prodNum = prodNum;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getstoreName() {
		return storeName;
	}

	public void setstoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getuserID() {
		return userID;
	}

	public void setuserID(String userID) {
		this.userID = userID;
	}

	public int getquantity() {
		return quantity;
	}

	public void setquantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getshippingCost() {
		return shippingCost;
	}

	public void setshippingCost(String shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getorderCompleted() {
		return orderCompleted;
	}

	public void setorderCompleted(int orderCompleted) {
		this.orderCompleted = orderCompleted;
	}

	public Date getorderDate() {
		return orderDate;
	}

	public void setorderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "OrderVO [orderNo=" + orderNo + ", prodNum=" + prodNum + ", prodName=" + prodName + ", storeName="
				+ storeName + ", userID=" + userID + ", quantity=" + quantity + ", cost=" + cost + ", shippingCost="
				+ shippingCost + ", review=" + review + ", orderCompleted=" + orderCompleted + ", orderDate="
				+ orderDate + "]";
	}

}
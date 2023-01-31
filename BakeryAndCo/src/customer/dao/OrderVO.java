package customer.dao;

import java.sql.Date;

public class OrderVO {
	private int Orderno,prodNum;
	private String prodName,Storename,Customerid;
	private int Quantity,cost;
	private String shippingcost,review;
	private int orderCompletedboolean;
	private Date orderdate;
	public OrderVO() {
		super();
	}
	public OrderVO(int orderno, int prodNum, String prodName, String storename, String customerid, int quantity,
			int cost, String shippingcost, String review, int orderCompletedboolean, Date orderdate) {
		super();
		Orderno = orderno;
		this.prodNum = prodNum;
		this.prodName = prodName;
		Storename = storename;
		Customerid = customerid;
		Quantity = quantity;
		this.cost = cost;
		this.shippingcost = shippingcost;
		this.review = review;
		this.orderCompletedboolean = orderCompletedboolean;
		this.orderdate = orderdate;
	}
	public int getOrderno() {
		return Orderno;
	}
	public void setOrderno(int orderno) {
		Orderno = orderno;
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
	public String getStorename() {
		return Storename;
	}
	public void setStorename(String storename) {
		Storename = storename;
	}
	public String getCustomerid() {
		return Customerid;
	}
	public void setCustomerid(String customerid) {
		Customerid = customerid;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getShippingcost() {
		return shippingcost;
	}
	public void setShippingcost(String shippingcost) {
		this.shippingcost = shippingcost;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getOrderCompletedboolean() {
		return orderCompletedboolean;
	}
	public void setOrderCompletedboolean(int orderCompletedboolean) {
		this.orderCompletedboolean = orderCompletedboolean;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	@Override
	public String toString() {
		return "OrderVO [Orderno=" + Orderno + ", prodNum=" + prodNum + ", prodName=" + prodName + ", Storename="
				+ Storename + ", Customerid=" + Customerid + ", Quantity=" + Quantity + ", cost=" + cost
				+ ", shippingcost=" + shippingcost + ", review=" + review + ", orderCompletedboolean="
				+ orderCompletedboolean + ", orderdate=" + orderdate + "]";
	}
	
	
}

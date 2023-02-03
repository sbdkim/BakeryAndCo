package examc;


public class ProdVO {

	
	private String pname;
	private int qty, price;
	public ProdVO() {
		super();
	}
	public ProdVO(String pname, int qty, int price) {
		super();
		this.pname = pname;
		this.qty = qty;
		this.price = price;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ProdVO [pname=" + pname + ", qty=" + qty + ", price=" + price + "]";
	}
	
	
	
	
	
	
}

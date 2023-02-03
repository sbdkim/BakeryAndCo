package examc;


public class OrdVO {

	private String pname;
	private int price , qty, co;
	private String cid;
	public OrdVO() {
		super();
	}
	public OrdVO(String pname, int price, String cid,int qty,int co) {
		super();
		this.pname = pname;
		this.price = price;
		this.cid = cid;
		this.qty = qty;
		this.co= co;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public int getCo() {
		return co;
	}
	public void setCo(int co) {
		this.co = co;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "OrdVO [pname=" + pname + ", price=" + price + ", cid=" + cid +", qty=" + qty+", co=" + co+ "]";
	}
	
	
	
	
	
}

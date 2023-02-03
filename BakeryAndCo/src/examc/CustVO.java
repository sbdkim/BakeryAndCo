package examc;


public class CustVO {

	private String id,pwd,name,mobile;
	private int active;
	public CustVO() {
		super();
	}
	public CustVO(String id, String pwd, String name, String mobile,int active) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.mobile = mobile;
		this.active = active;
	}
	public CustVO(String id, String pwd, String name, String mobile) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.mobile = mobile;
	}
	
	public CustVO(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "CustVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", mobile=" + mobile + ", active=" + active
				+ "]";
	}
	
	
	
	
	
	
	
	
}

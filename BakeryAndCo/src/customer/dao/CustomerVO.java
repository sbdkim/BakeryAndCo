package customer.dao;

import java.sql.Timestamp;

public class CustomerVO {
	//fields
	private String id, password, name, birthDate, mobile, addr;
	private Timestamp reg_date;
	
	//constructors
	public CustomerVO() {super();}
	
	public CustomerVO(String id,  String name, String birthDate, String mobile, String addr, int grade, Timestamp reg_date ) {
		super();
		this.id =  id;		
		this.name = name;
		this.birthDate = birthDate;
		this.mobile = mobile;
		this.addr = addr;
		this.reg_date = reg_date;
	}

	
	
	//methods
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getbirthDate() {
		return birthDate;
	}
	
	public void setbirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Timestamp getReg_date() {
		return reg_date;
	}

	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		
		return "MemberVO : [ID" + id + ", 이름:" +  name + ", 전화번호: " + mobile + ", 주소: " + addr +", 등록날짜: "  + reg_date +"]"; 
		
	}
	

}//CustomerVO

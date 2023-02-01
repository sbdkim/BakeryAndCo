package customer.dao;

import java.sql.Timestamp;
import java.util.Date;

public class CustomerVO {
	// fields
	private String userID, pwd, name, email, mobile, addr;
	private Date birthDate;
	private int active;
	private Timestamp enrollDate;

	// constructors
	public CustomerVO() {
		super();
	}

	public CustomerVO(String userID, String pwd, String name, Date birthDate, String mobile, String email, String addr, int active,
			Timestamp enrollDate) {
		super();
		this.userID = userID;
		this.pwd = pwd;
		this.name = name;
		this.birthDate = birthDate;
		this.mobile = mobile;
		this.email = email;
		this.addr = addr;
		this.active = active;
		this.enrollDate = enrollDate;
	}

	public CustomerVO(String userID, String name, Date birthDate, String mobile, String email, String addr, int active,
			Timestamp enrollDate) {
		super();
		this.userID = userID;
		this.name = name;
		this.birthDate = birthDate;
		this.mobile = mobile;
		this.email = email;
		this.addr = addr;
		this.active = active;
		this.enrollDate = enrollDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Timestamp getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Timestamp enrollDate) {
		this.enrollDate = enrollDate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	//public CustomerVO(String userID,  String pwd, String name,  Date birthDate,  String  mobile, String email, String addr, int active, Timestamp enrollDate ) {
	@Override
	public String toString() {

		return "CustomerVO : [ID: " + userID + ", 이름: " + name + ", 생년월일: " + birthDate + ", 전화번호: " + mobile
				+ ", 이메일: " + email + ", 주소: " + addr + ", 활성: " + active +", 등록날짜: " + enrollDate + "]";

	}

}// CustomerVO

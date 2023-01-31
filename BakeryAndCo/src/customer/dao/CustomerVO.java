package customer.dao;

import java.sql.Timestamp;
import java.util.Date;

public class CustomerVO {
	//fields
	private String userID, pwd, name, gender, email, mobile,  addr ;
	private Date birthDate;
	private Timestamp enrollDate;
	
	//constructors
	public CustomerVO() {super();}
	
	public CustomerVO(String userID,  String pwd, String name, String gender, Date birthDate, String email, String  mobile, String addr, Timestamp enrollDate ) {
		super();
		this.userID = userID;
		this.pwd = pwd;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.email = email;
		this.mobile = mobile;
		this.addr = addr;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	
	
	@Override
	public String toString() {
		
		return "CustomerVO : [ID: " + userID + ", 이름: " +  name +  ", 성: " +  gender + ", 생년월일: " + birthDate +", 이메일: " + email + ", 전화번호: " + mobile + ", 주소: " + addr +", 등록날짜: "  + enrollDate +"]"; 
		
	}
	

}//CustomerVO

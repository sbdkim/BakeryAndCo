package customer.dao;

import java.sql.Timestamp;
import java.util.Date;

public class SellerVO {
	//fields
	private String sellerID, pwd, name, storeName, storeMobile, gender, email, mobile,  storeAddr ;
	private int regionCode;
	private Date birthDate;
	private Timestamp enrollDate;
	
	//constructors
	public SellerVO() {super();}
	
	public SellerVO(String sellerID,  String pwd, String name, Date birthDate, String gender, String storeName, String storeMobile, String email, String  mobile, String storeAddr, int regionCode, Timestamp enrollDate ) {
		super();
		this.sellerID = sellerID;
		this.pwd = pwd;
		this.name = name;
		this.birthDate = birthDate;
		this.storeName = storeName;
		this.email = email;
		this.mobile = mobile;
		this.storeAddr = storeAddr;
		this.regionCode = regionCode;
		this.enrollDate = enrollDate;
	}

	
		
	
	public String getSellerID() {
		return sellerID;
	}

	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreMobile() {
		return storeMobile;
	}

	public void setStoreMobile(String storeMobile) {
		this.storeMobile = storeMobile;
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

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}

	public int getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
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

	
	
	
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	//	public SellerVO(String sellerID,  String pwd, String name, Date birthDate, String gender, String storeName, String storeMobile, String email, String  mobile, String storeAddr, int regionCode, Timestamp enrollDate ) {
	@Override
	public String toString() {
		
		return "SellerVO : [ID: " + sellerID + ", 이름:" +  name +  ", 생년월일: " + birthDate + "성: " + gender + ", 가게명: " + storeName +", 이메일: " + email + ", 전화번호: " + mobile +", 가게 전화번호: " + storeMobile + ", 가게주소: " + storeAddr +", 가게지역코드: " + regionCode + ", 등록날짜: "  + enrollDate +"]"; 
		
	}
	

}//CustomerVO

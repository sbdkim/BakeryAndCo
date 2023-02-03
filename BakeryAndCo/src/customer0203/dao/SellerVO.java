package customer0203.dao;

import java.sql.Timestamp;
import java.util.Date;

public class SellerVO {
	// fields
	private String sellerID, pwd, name, storeName, storeMobile, email, storeAddr, birthDate;
	private int regionCode, active;
	private Timestamp enrollDate;

	// constructors
	public SellerVO() {
		super();
	}
	
	public SellerVO(String sellerID, String storeName, int regionCode, int active) {
		this.sellerID = sellerID;
		this.storeName = storeName;
		this.regionCode = regionCode;
		this.active = active;
	}
	

	public SellerVO(String sellerID, String pwd, String name, String birthDate, String storeName, String storeMobile,
			String email, String storeAddr, int regionCode, int active, Timestamp enrollDate) {
		super();
		this.sellerID = sellerID;
		this.pwd = pwd;
		this.name = name;
		this.birthDate = birthDate;
		this.storeName = storeName;
		this.storeMobile = storeMobile;
		this.email = email;
		this.storeAddr = storeAddr;
		this.regionCode = regionCode;
		this.active = active;
		this.enrollDate = enrollDate;
	}

	public SellerVO(String sellerID, String name, String birthDate, String storeName, String storeMobile, String email,
			String storeAddr, int regionCode, int active, Timestamp enrollDate) {
		super();
		this.sellerID = sellerID;
		this.name = name;
		this.birthDate = birthDate;
		this.storeName = storeName;
		this.storeMobile = storeMobile;
		this.email = email;
		this.storeAddr = storeAddr;
		this.regionCode = regionCode;
		this.active = active;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Timestamp getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Timestamp enrollDate) {
		this.enrollDate = enrollDate;
	}

//	public SellerVO(String sellerID,  String name, Date birthDate, String storeName, 
//			String storeMobile, String email,  String storeAddr, int regionCode, int active, Timestamp enrollDate ) {

	@Override
	public String toString() {

		return "SellerVO : [ID: " + sellerID + ", 이름:" + name + ", 생년월일: " + birthDate + ", 가게명: " + storeName
				+ ", 가게 전화번호: " + storeMobile + ", 이메일: " + email + ", 가게주소: " + storeAddr + ", 가게지역코드: " + regionCode
				+ ", 활성: " + active + ", 등록날짜: " + enrollDate + "]";

	}

}// CustomerVO

package customer.dao;

import java.sql.Timestamp;

public class SellerVO {
	private String id,password,name,mobile,addr;
	private int grade;
	private Timestamp reg_date;
	//생성자 패스워드까지 포함
	public SellerVO(String id, String password, String name, String mobile, String addr, int grade,
			Timestamp reg_date) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.mobile = mobile;
		this.addr = addr;
		this.grade = grade;
		this.reg_date = reg_date;
	}
	//패스워드 제외
	public SellerVO(String id, String name, String mobile, String addr, int grade,
			Timestamp reg_date) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.addr = addr;
		this.grade = grade;
		this.reg_date = reg_date;
	}

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
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "MemberVO [아이디=" + id + ", 이름=" + name + ", 연락처=" + mobile + ", 주소=" + addr + ", 등급="
				+ grade + ", 등록일자=" + reg_date + "]";
	}
}
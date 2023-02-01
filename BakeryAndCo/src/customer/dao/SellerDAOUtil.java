package customer.dao;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 여기에 들어갈 메소드들은 메뉴 출력, 입력받기, 그리고 DAO로 입력받은것을 념겨줘서 결과를 DAO에서 받기
public class SellerDAOUtil {

	private Matcher match;
	SellerDAO dao = null;
	
	public SellerDAOUtil() {
		super();
		dao = SellerDAO.getInstance();
	}
	
	public String login2(Scanner sc) {
		String id=null;
		String password=null;
		System.out.println("--- 로그인 아이디/패스워드 입력 ----");
		System.out.print("아이디>>");
		id=sc.nextLine();
		System.out.print("패스워드>>");
		password=sc.nextLine();
		SellerVO vo=dao.selectSeller(id, password);
		if(vo!=null)id=vo.getSellerID();
		else id=null;
		return id;
	}
	
	
	
	public HashMap<String, String> login(Scanner sc) {
		HashMap<String, String> map = null;
		String id = null;
		String password = null;
		System.out.println("--- 로그인 아이디/패스워드 입력 ----");
		System.out.print("아이디>>");
		id = sc.nextLine();
		System.out.print("패스워드>>");
		password = sc.nextLine();
		SellerVO vo = dao.selectSeller(id, password);
		if (vo != null) {
			map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", vo.getName());
		}
		return map;
	}
	
	public boolean idCheck(String id) {
		String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$";
		boolean chk = false;
		// 영문, 숫자 조합 (8~20 자리)
		match = Pattern.compile(pattern).matcher(id);
		if (match.find()) {
			chk = true;
		}
		return chk;
	}

	public boolean pwdCheck(String pwd) {
		boolean chk = false;
		// 영문, 숫자, 특수문자 조합 (10~20 자리)
		String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}])[A-Za-z[0-9]!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}]{10,20}$"; // 영문,
																																													// 숫자,
																																													// 특수문자
		match = Pattern.compile(pattern).matcher(pwd);
		if (match.find()) {// 패턴에 맞는지 확인
			chk = true;
		}
		return chk;
	}

	public boolean mobileCheck(String mobile) {
		boolean chk = false;
		String pattern = "^01(?:0|1|[6-9])-\\d{4}-\\d{4}$";
		match = Pattern.compile(pattern).matcher(mobile);
		if (match.find()) {// 패턴에 맞는지 확인
			chk = true;
		}
		return chk;
	}
	
	public boolean emailCheck(String email) {
		 boolean chk = false;
		// 영문, 숫자, 특수문자 조합 (10~20 자리)
		 String pattern ="^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\\\\\<>|_-[+]=\\\\`~\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}])[A-Za-z[0-9]!@#$%^&*?,./\\\\\\\\<>|_-[+]=\\\\`~\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}]{10,20}$";
		 match = Pattern.compile(pattern).matcher(email);	
		 if(match.find()) {//패턴에 맞는지 확인
			  chk = true;
		 }
		 return chk;
	}
	
	
	
	
}//SellerDAOUtil

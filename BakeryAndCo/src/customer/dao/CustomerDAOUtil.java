package customer.dao;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerDAOUtil {

	private Matcher match;
	CustomerDAO dao = null;

	public CustomerDAOUtil() {
		super();
		dao = CustomerDAO.getInstance();
	}

	
	public String login2(Scanner sc) {
		String id=null;
		String password=null;
		System.out.println("--- 로그인 아이디/패스워드 입력 ----");
		System.out.print("아이디>>");
		id=sc.nextLine();
		System.out.print("패스워드>>");
		password=sc.nextLine();
		CustomerVO vo=dao.selectCustomer(id, password);
		if(vo!=null)id=vo.getUserID();
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
		CustomerVO vo = dao.selectCustomer(id, password);
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

	public boolean update(Scanner sc) {
		// 아이디/패스워드 입력 받아서 조회
		// userID,pwd,name,gender,birthDate,email,mobile,addr,enrolldate
		String userID, password;
		CustomerVO vo = null;
		// 아이디 패스워드 입력 받기
		while (true) {
			System.out.print("사용자 ID(enter 취소)>>");
			userID = sc.nextLine().trim();
			if (userID.length() == 0)
				return false;
			System.out.print("패스워드>>");
			password = sc.nextLine().trim();
			vo = dao.selectCustomer(userID, password);
			if (vo == null) {
				System.out.println("없는 사용자 입니다.");
				continue;
			}
			break;
		}
		System.out.println(vo.toString());
		// 아이디 제외 다시 입력
		// enter 수정 안하면 null 저장
		// 수정항목이 1개 이상
		// 공백이 아니면 패턴체크
		// 비번 입력 첫번째 enter 비빌번호 변경 안하겠다
		String name = null, mobile = null, addr = null, pwd_check, gender = null, birthDate = null, email = null;
		int cnt = 0;

		while (true) {
			System.out.print("패스워드(enter 수정안함)>>");
			password = sc.nextLine().trim();
			if (password.length() != 0) {
				// 패턴체크
				if (!this.pwdCheck(password)) {
					System.out.println("부적합한 비밀번호 형식 입니다. 영문자,특수문자,숫자조합(10~20자리)");
					continue;
				}
				System.out.print("패스워드 확인>>");
				pwd_check = sc.nextLine().trim();
				if (!password.equals(pwd_check)) {
					System.out.println("입력하신 패스워드 2개가 다릅니다.");
					continue;
				}
				cnt++;// 수정:패스워드가 오류가 없는경우
			} else
				password = null;
			break;
		}

		System.out.print("이름 >>");
		name = sc.nextLine().trim();
		// 공백의 경우 다시 입력
		if (name.length() == 0) {
			name = null;
		} else
			cnt++;

		while (true) {
			// 전화번호 입력
			System.out.print("휴대폰 번호>>");
			mobile = sc.nextLine().trim();
			if (mobile.length() != 0) {
				// 1. 패턴체크
				if (!mobileCheck(mobile)) {
					System.out.println("전화번호 형식이 틀립니다.xxx-xxxx-xxxx");
					continue;
				}
				cnt++;
				// 패턴 틀린경우 다시 입력
			} else
				mobile = null;
			break;
		}
		// 주소는 그냥 입력 공백이면 null
		System.out.print("주소>>");
		addr = sc.nextLine().trim();
		if (addr.length() == 0)
			addr = null;
		else
			cnt++;

		// 생년월일은 그냥 입력 공백이면 null

		if (cnt != 0) {// update 진행
			if (dao.updateCustomer(userID, password, name, gender, email, mobile, addr) == 1)
				return true;
		}
		return false;
	}//update
	
	
	
	public boolean delete(Scanner sc) {
		//아이디 비번 입력
		String id,password;
		//아이디 패스워드 입력 받기
		System.out.print("사용자 ID>>");
		id=sc.nextLine().trim();
		System.out.print("패스워드>>");
		password=sc.nextLine().trim();		
		//MemberVO vo=dao.selectMember(id, password);
		//회원조회 하고 있으면 삭제 수행
		//if(vo==null)return false;
		//삭제 수행
		if(dao.CustomerDelete(id, password)==1)return true;
		else return false;
	}//delete
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}// CustomerDAOUtil

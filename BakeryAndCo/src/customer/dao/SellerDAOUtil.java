package customer.dao;

import java.util.ArrayList;
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
		String id = null;
		String password = null;
		System.out.println("--- 로그인 아이디/패스워드 입력 ----");
		System.out.print("아이디>>");
		id = sc.nextLine();
		System.out.print("패스워드>>");
		password = sc.nextLine();
		SellerVO vo = dao.selectSeller(id, password);
		if (vo != null)
			id = vo.getSellerID();
		else
			id = null;
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
		String pattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		match = Pattern.compile(pattern).matcher(email);
		if (match.find()) {// 패턴에 맞는지 확인
			chk = true;
		}
		return chk;
	}

	
	public boolean dateCheck(String birthDate) {
		boolean chk = false;
		// 영문, 숫자, 특수문자 조합 (10~20 자리)
		String pattern = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
		match = Pattern.compile(pattern).matcher(birthDate);
		if (match.find()) {// 패턴에 맞는지 확인
			chk = true;
		}
		return chk;
	}

	//calls createSeller method in SellerDAO to insert into table the values entered in registerSeller
	public boolean registerSeller(Scanner sc) {
		boolean result = false;
		int runMethod, regionCode;
		String sellerID, pwd, pwdCheck, name, email, storeName, storeMobile, storeAddr, birthDate ;
		
		while (true) {
			System.out.print("판매자 ID(영문자,숫자조합(8~20자리)>>");
			sellerID = sc.nextLine().trim();
			// 1. 패턴 체크
			if (!idCheck(sellerID)) {
				System.out.println("부적합한 판매자 ID 입니다. 영문자,숫자조합(8~20자리)");
				continue;
			}
			// 2. 아이디 중복 조회 select메소드를 DAO 만들기 boolean selectMember(String id)
			// 정상
			if (dao.selectSeller(sellerID)) {
				System.out.println("이미 사용중인 ID 입니다.");
				continue;
			}
			break;
		} // while -- check ID
		while (true) {
			// 패스워드 입력
			System.out.print("패스워드 ID(영문자,숫자,특수문자 조합(10~20자리)>>");
			pwd = sc.nextLine().trim();
			// 1. 패턴체크
			if (!pwdCheck(pwd)) {
				System.out.println("부적합한 비밀번호 형식 입니다. 영문자,특수문자,숫자조합(10~20자리)");
				continue;
			}
			// 패스워드 체크 입력
			System.out.print("패스워드 다시 입력 >>");
			pwdCheck = sc.nextLine().trim();
			// 두 패스워드 비교 틀린경우 다시 입력
			if (!pwd.equals(pwdCheck)) {
				System.out.println("입력하신 패스워드 2개가 다릅니다.");
				continue;
			}
			break;
		}
		while (true) {
			// 이름 입력
			System.out.print("이름 >>");
			name = sc.nextLine().trim();
			// 공백의 경우 다시 입력
			if (name.length() == 0) {
				System.out.println("이름은 필수 항목 입니다.");
				continue;
			}
			break;
		}
		
		
		
		
		
		
		
		while (true) {
			// 전화번호 입력
			System.out.print("생년월일(1900-01-01) >>");
			birthDate = sc.nextLine().trim();
			
			// 1. 패턴체크
			if (!dateCheck(birthDate)) {
				System.out.println("생년월일 입력한 포멧 오류");
				continue;
			}
			// 패턴 틀린경우 다시 입력
			break;
		}
		
		
		
		while (true) {
			// 이름 입력
			System.out.print("가개이름 >>");
			storeName = sc.nextLine().trim();
			// 공백의 경우 다시 입력
			if (storeName.length() == 0) {
				System.out.println("이름은 필수 항목 입니다.");
				continue;
			}
			break;
		}
		
		while (true) {
			// 전화번호 입력
			System.out.print("이매일>>");
			email = sc.nextLine().trim();
			// 1. 패턴체크
			if (!emailCheck(email)) {
				System.out.println("이매일 입력한 포멧 오류");
				continue;
			}
			// 패턴 틀린경우 다시 입력
			break;
		}


		// String userID, pwd, pwdCheck, name, gender, email, mobile, addr;
		// Date birthDate;
		while (true) {
			// 전화번호 입력
			System.out.print("휴대폰 번호>>");
			storeMobile = sc.nextLine().trim();
			// 1. 패턴체크
			if (!mobileCheck(storeMobile)) {
				System.out.println("전화번호 형식이 틀립니다.xxx-xxxx-xxxx");
				continue;
			}
			// 패턴 틀린경우 다시 입력
			break;
		}
		
		while (true) {
			// 전화번호 입력
			System.out.print("주소>>");
			storeAddr = sc.nextLine().trim();

			// 패턴 틀린경우 다시 입력
			break;
		}
		
		while (true) {
			// 전화번호 입력
			System.out.println("[1. 서울 | 2. 부산 | 3. 대구 ,.....]");
			System.out.print("가게 지역>>");
			try {
				regionCode = sc.nextInt();
			}catch(Exception e) {
				System.out.println("지역 입력 오류... 다시 입력하세요.");
				continue;
			}
		

			// 패턴 틀린경우 다시 입력
			break;
		}

		
		runMethod = dao.createSeller(sellerID, pwd, name, birthDate, storeName, storeMobile, email, storeAddr, 1, regionCode );
		if(runMethod == 1) {
			result = true;
		}else result = false;
		
		
		
		return result;
	}// registerSeller

	
	
	
	
	
	
	
	
	
	
	
	

}// SellerDAOUtil

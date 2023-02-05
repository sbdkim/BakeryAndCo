package bakeryComapny;

import java.util.ArrayList;
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

//  전체 매인 메뉴 [1]로그인 - [1] 소비자
	public HashMap<String, String> login(Scanner sc, String id) {
		HashMap<String, String> map = null;
		String pwd = null;
		System.out.print("패스워드>>");
		pwd = sc.nextLine();
		CustomerVO vo = dao.selectCustomer(id, pwd);
		if (vo != null) {
			map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", vo.getName());
		}
		return map;
	}

//  전체 매인 메뉴 [1]로그인 - [1] 소비자 - 비밀번호 제설정
	public boolean resetPassword(String userID, Scanner sc) {
		boolean result = false;
		int resetSuccess;
		String pwd, pwdCheck, mobile, birthDate;

		System.out.println("[비밀번호를 제설정 하기 위해서 올바른 정보를 입력하시오]");

		while (true) {
			// 전화번호 입력
			System.out.print("휴대폰 번호>>");
			mobile = sc.nextLine().trim();
			// 1. 패턴체크
			if (!mobileCheck(mobile)) {
				System.out.println("전화번호 형식이 틀립니다.xxx-xxxx-xxxx");
				continue;
			}
			// 패턴 틀린경우 다시 입력
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

		System.out.println("[세로운 비밀번호을 입력합니다]");
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

		resetSuccess = dao.resetPassword(userID, pwd, mobile, birthDate);
		if (resetSuccess == 1)
			result = true;
		else
			result = false;

		return result;

	}

	// 전체 매인 메뉴 [2]회원가입 - [1] 소비자
	public boolean registerCustomer(Scanner sc) {
		boolean result = false;
		int runMethod;
		String userID, pwd, pwdCheck, name, email, mobile, addr, birthDate;

		while (true) {
			System.out.print("사용자 ID(영문자,숫자조합(8~20자리)>>");
			userID = sc.nextLine().trim();
			// 1. 패턴 체크
			if (!idCheck(userID)) {
				System.out.println("부적합한 사용자 ID 입니다. 영문자,숫자조합(8~20자리)");
				continue;
			}
			// 2. 아이디 중복 조회 select메소드를 DAO 만들기 boolean selectMember(String id)
			// 정상
			if (dao.selectCustomer(userID)) {
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
			mobile = sc.nextLine().trim();
			// 1. 패턴체크
			if (!mobileCheck(mobile)) {
				System.out.println("전화번호 형식이 틀립니다.xxx-xxxx-xxxx");
				continue;
			}
			// 패턴 틀린경우 다시 입력
			break;
		}

		while (true) {
			// 전화번호 입력
			System.out.print("주소>>");
			addr = sc.nextLine().trim();

			// 패턴 틀린경우 다시 입력
			break;
		}

		runMethod = dao.createCustomer(userID, pwd, name, birthDate, mobile, email, addr, 1);
		if (runMethod == 1) {
			result = true;
		} else
			result = false;

		return result;
	}// registerCustomer

	// CUSTOMER MENU 1 - [1] 주문내역 - [1] 완료된 주문 목록
	public boolean showCompletedOrder(String id) {
		boolean result = false;
		ArrayList<OrderVO> list = null;
		list = dao.viewCompletedOrder(id);
		if (list != null) {
			for (OrderVO v : list) {
				System.out.println(v.toString());
			}
			result = true;
		}

		return result;
	}

	// CUSTOMER MENU 1 - [1] 주문내역 - [2] 진행중인 주문 목록
	public boolean showCurrentOrder(String id) {
		boolean result = false;
		ArrayList<OrderVO> list = null;
		list = dao.viewOrderProcessing(id);
		if (list != null) {
			for (OrderVO v : list) {
				System.out.println(v.toString());
			}
			result = true;
		}

		return result;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 지역에 있는 가게 출력
	public ArrayList<SellerVO> showRegionStore(int regionCode) {

		ArrayList<SellerVO> list = null; // 이 리스트에 담긴 SellerVO에는 sellerID, storeName, regionCode, active이거 4개가 담겨있다
		list = dao.viewRegionStore(regionCode);
		if (list != null) {
			int i = 0;
			for (SellerVO vo : list) {
				i++;
				System.out.println("[" + i + "] " + vo.getStoreName()); // 출력
			}

		}
		return list;
	}

	public ArrayList<SellerVO> hasRegionStore(int regionCode) {
		ArrayList<SellerVO> list = null; // 이 리스트에 담긴 SellerVO에는 sellerID, storeName, regionCode, active이거 4개가 담겨있다
		list = dao.viewRegionStore(regionCode);
		if (list != null) {
			int i = 0;
			for (SellerVO vo : list) {
				i++;
			}
		}
		return list;
	}

	public int showRegionStoreNumber(int regionCode) {
		ArrayList<SellerVO> list = null;
		int i = 0;
		list = dao.viewRegionStore(regionCode);
		if (list != null) {

			for (SellerVO vo : list) {
				i++;
				// System.out.println("[" + i + "] " +vo.getStoreName());
			}
		}
		return i;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 들어온 가게의 모든 케타고리 출력
	public ArrayList<ProductVO> showCategory(String storeName) {
		ArrayList<ProductVO> list = null; // 이 리스트에 담긴 SellerVO에는 sellerID, storeName, regionCode, active이거 4개가 담겨있다
		list = dao.viewCategory(storeName);
		if (list != null) {
			int i = 0;
			System.out.println("───────────────────────────────────");
			for (ProductVO vo : list) {
				i++;
				System.out.println("[" + i + "] " + vo.getCategory()); // 출력
			}
			System.out.println("[0] 장바구니로 이동");
			System.out.println("[ENTER] 장바구니 비우고 나가기");
			System.out.println("───────────────────────────────────");

		}

		return list;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 들어온 가게의 케타고리 안에 있슨 모든 물건
	public ArrayList<ProductVO> viewStoreProduct(String storeName, String category) {
		ArrayList<ProductVO> list = null;
		list = dao.viewProducts(storeName, category);
		if (list != null) {
			int i = 0;
			for (ProductVO v : list) {
				i++;
				System.out.println("[" + i + "] " + v.toString());
			}
		}

		return list;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 장바구니에 담기
	public int addToCart(String userID, int prodNum, String prodName, int price, int quantity) {
		int result = 0;

		result = dao.addCart(userID, prodNum, prodName, price, quantity);

		return result;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 장바구니에 보기
	public ArrayList<CartVO> viewCart() {
		ArrayList<CartVO> list = null;
		list = dao.viewShoppingCart();
		if (list != null) {
			for (CartVO v : list) {
				System.out.println(v.toString());
			}
		}

		return list;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 장바구니에 있는것들을 주문 테이블에 담기
	public boolean checkout(Scanner sc, String storeName, String userID, ArrayList<CartVO> list) {
		// 아이디 비번 입력
		boolean result = false;
		String confirm;
		System.out.print("[카트에 있는것을 체크아웃하시겠습니까? (Y/N)]:");
		confirm = sc.nextLine().trim();
		if (confirm.equals("y") || confirm.equals("Y")) {
			// 여기에서 결재 (cartTBL에서 orderTBL로 넘겨주기);

			result = dao.checkout(storeName, userID, list);

		} else {
			result = false;
		}
		return result;

	}

	// CUSTOMER MENU 2 - [2] 주문하기 - 총 개수 총 금액 출력
	public int total(ArrayList<CartVO> list) {
		int result = 0;
		int totalItems = 0, totalCost = 0;
		if (list != null) {
			for (CartVO v : list) {
				v.getPrice();
				v.getQuantity();
				totalItems += v.getQuantity();
				totalCost += (v.getPrice() * v.getQuantity());
			}
			System.out.println("카트에 있는 총 개수: " + totalItems);
			System.out.println("총 금액: " + totalCost);

		}

		return result;
	}

	//CUSTOMER MENU 2 - [2] 주문하기 - 장바구니 비우기

	public boolean emptyCart() {
		boolean result = false;
		if (result = dao.emptyCart())
			result = true;

		return result;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - productTBL에서 주문한 주문한 갯수는 제거
	public int minusToProduct(String prodName, int inventory) {
		int result = 0;

		result = dao.minusProduct(prodName, inventory);

		return result;
	}

	// CUSTOMER MENU 2 - [2] 주문하기 - productTBL에서 주문한 주문안하고 나가면 갯수는 다시 추가
	public int plusToProduct(String prodName, int inventory) {
		int result = 0;

		result = dao.plusProduct(prodName, inventory);

		return result;
	}

	// CUSTOMER MENU 3 - [3] 회원 정보 수정
	public boolean update(String userID, Scanner sc) {
		// 아이디/패스워드 입력 받아서 조회
		// userID,pwd,name,gender,birthDate,email,mobile,addr,enrolldate
		String pwd;
		CustomerVO vo = null;
		// 아이디 패스워드 입력 받기
		while (true) {

			System.out.print("현재 비밀번호를 입력하세요>>");
			pwd = sc.nextLine().trim();
			vo = dao.selectCustomer(userID, pwd);
			if (vo == null) {
				System.out.println("비밀번호가 틀렸습니다...");
				continue;
			}
			break;
		}
		System.out.println("[현재 정보가 출력되었습니다]");
		System.out.println(vo.toString());
		// 아이디 제외 다시 입력
		// enter 수정 안하면 null 저장
		// 수정항목이 1개 이상
		// 공백이 아니면 패턴체크
		// 비번 입력 첫번째 enter 비빌번호 변경 안하겠다
		String name = null, mobile = null, addr = null, pwd_check, email = null;
		int cnt = 0;

		while (true) {
			System.out.print("패스워드(enter 수정안함)>>");
			pwd = sc.nextLine().trim();
			if (pwd.length() != 0) {
				// 패턴체크
				if (!this.pwdCheck(pwd)) {
					System.out.println("부적합한 비밀번호 형식 입니다. 영문자,특수문자,숫자조합(10~20자리)");
					continue;
				}
				System.out.print("패스워드 확인>>");
				pwd_check = sc.nextLine().trim();
				if (!pwd.equals(pwd_check)) {
					System.out.println("입력하신 패스워드 2개가 다릅니다.");
					continue;
				}
				cnt++;// 수정:패스워드가 오류가 없는경우
			} else
				pwd = null;
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
			if (dao.updateCustomer(userID, pwd, name, email, mobile, addr) == 1)
				return true;
		}
		return false;
	}// update

	// CUSTOMER MENU 4 - [4] 회원탈퇴
	public boolean delete(Scanner sc, String userID) {
		// 아이디 비번 입력
		boolean result = false;
		String pwd, confirm;
		// 아이디 패스워드 입력 받기
		System.out.print("패스워드>>");
		pwd = sc.nextLine().trim();

		System.out.print("탈퇴하시겠습니까(y/n)>>");
		confirm = sc.nextLine().trim();

		if (confirm.equals("y") || confirm.equals("Y")) {
			if (dao.customerDelete(userID, pwd) == 1)
				result = true;
		} else {
			result = false;
		}
		return result;

	}// delete

}// CustomerDAOUtil

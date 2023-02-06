package bakeryCompany;

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

//  전체 매인 메뉴 [1]로그인 - [2] 판매자
	public HashMap<String, String> login(Scanner sc, String id) {
		HashMap<String, String> map = null;
		String password = null;
		System.out.print("패스워드>>");
		password = sc.nextLine();
		SellerVO vo = dao.selectSeller(id, password);
		if (vo != null) {
			map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", vo.getName());
			map.put("storeName", vo.getStoreName());
		}
		return map;
	}

	// 전체 매인 메뉴 [1]로그인 - [2] 판매자 - 비밀번호 제설정
	public boolean resetPassword(String sellerID, Scanner sc) {
		boolean result = false;
		int resetSuccess;
		String pwd, pwdCheck, mobile, birthDate, storeName;

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

		resetSuccess = dao.passwordReset(sellerID, pwd, storeName, mobile, birthDate);
		if (resetSuccess == 1)
			result = true;
		else
			result = false;

		return result;
	}

	// 전체 매인 메뉴 [2]회원가입 - [2] 판매자
	public boolean registerSeller(Scanner sc) {
		boolean result = false;
		int runMethod, regionCode;
		String sellerID, pwd, pwdCheck, name, email, storeName, storeMobile, storeAddr, birthDate;

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
			if (!pwd.equals(pwdCheck)) {
				System.out.println("입력하신 패스워드 2개가 다릅니다.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("이름 >>");
			name = sc.nextLine().trim();
			if (name.length() == 0) {
				System.out.println("이름은 필수 항목 입니다.");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("생년월일(1900-01-01) >>");
			birthDate = sc.nextLine().trim();

			// 1. 패턴체크
			if (!dateCheck(birthDate)) {
				System.out.println("생년월일 입력한 포멧 오류");
				continue;
			}
			break;
		}

		while (true) {
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
			break;
		}

		while (true) {
			// 전화번호 입력
			System.out.print("휴대폰 번호>>");
			storeMobile = sc.nextLine().trim();
			// 1. 패턴체크
			if (!mobileCheck(storeMobile)) {
				System.out.println("전화번호 형식이 틀립니다.xxx-xxxx-xxxx");
				continue;
			}
			break;
		}

		while (true) {
			// 전화번호 입력
			System.out.print("주소>>");
			storeAddr = sc.nextLine().trim();
			break;
		}

		while (true) {
			// 전화번호 입력
			System.out.println("[1. 서울특별시 | 2. 인천광역시 | 3. 부산광역시 | 4. 울산광역시 | 5. 대전광역시 | 6. 광주광역시 7. 대구광역시 | 8. 경기도]");
			System.out.println("[9. 강원도 | 10. 충청남도 | 11. 충청북도 | 12. 경상북도 | 13. 경상남도 | 14. 전라북도 | 15. 전라남도 | 16. 제주]");
			System.out.print("가게 지역>>");
			try {
				regionCode = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				System.out.println("지역 입력 오류... 다시 입력하세요.");
				sc.nextLine();
				continue;
			}

			break;
		}

		runMethod = dao.createSeller(sellerID, pwd, name, birthDate, storeName, storeMobile, email, storeAddr, 1,
				regionCode);
		if (runMethod == 1) {
			result = true;
		} else
			result = false;

		return result;
	}// registerSeller

	// SELLER MENU 1 - [1] 주문목록
	public boolean viewStoreOrder(String storeName) {
		boolean result = false;
		ArrayList<OrderVO> list = null;
		list = dao.viewOrders(storeName);
		if (list != null) {
			for (OrderVO v : list) {
				System.out.println(v.toString());
			}
			result = true;
		}

		return result;
	}

	// SELLER MENU 2 - [2] 고객목록
	public boolean viewCustomerList(String storeName) {
		boolean result = false;
		ArrayList<CustomerVO> list = null;
		list = dao.viewCustomers(storeName);
		if (list != null) {
			for (CustomerVO v : list) {
				System.out.println(v.toString());
			}
			result = true;
		}

		return result;
	}

	// SELLER MENU 3 - [3] 제품 목록
	public boolean viewStoreProduct(String storeName) {
		boolean result = false;
		ArrayList<ProductVO> list = null;
		list = dao.viewProducts(storeName);
		if (list != null) {
			for (ProductVO v : list) {
				System.out.println(v.toString());
			}
			result = true;
		}

		return result;
	}

	// SELLER MENU 3 [3] 제품 목록 - PRODUCT MENU 1 - [1] 제품등록
	public boolean createProduct(Scanner sc, String storeName) {
		boolean result = false;
		boolean createBoolean = true;
		int runMethod;
		String category = null, prodName = null, description = null, priceString = null, inventoryString = null;
		int price = 0, inventory = 0, rating;

		// 하나씩 입력받기
		// prodNum은 prod_seq로 증가
		while (createBoolean) {
			while (true) {
				System.out.print("상품 종류>>");
				category = sc.nextLine().trim();

				// 아무것도 입력 안 한 경우에 종료
				if (category.length() == 0) {
					System.out.println("[입력을 안하셨습니다.. 등록을 종료합니다.]");
					createBoolean = false;
				}

				break;
			}
			// storeName, received from this method

			if (createBoolean = false) {
				break;//
			}

			while (true) {
				System.out.print("상품 이름>>");
				prodName = sc.nextLine().trim();
				if (prodName.length() == 0) {
					System.out.println("[입력을 안하셨습니다.. 다시 입력하세요.]");
					sc.nextLine();
					continue;
				} else {
					break;
				}

			}
			// price
			while (true) {
				System.out.print("상품 가격>>");
				priceString = sc.nextLine().trim();

				if (priceString.length() == 0) {
					System.out.println("가격 입력 안하셨습니다... 다시 입력하세요.");
					sc.nextLine();
					continue;
				} else {

					try {
						price = Integer.parseInt(priceString);
					} catch (Exception e) {
						System.out.println("가격 입력 오류... 다시 입력하세요.");
						sc.nextLine();
						continue;
					}
					break;
				}

			}

			while (true) {
				System.out.print("상품 갯수>>");
				inventoryString = sc.nextLine().trim();

				if (inventoryString.length() == 0) {
					System.out.println("갯수 입력 안하셨습니다... 다시 입력하세요.");
					sc.nextLine();
					continue;
				} else {

					try {
						inventory = Integer.parseInt(inventoryString);
					} catch (Exception e) {
						System.out.println("갯수 입력 오류... 다시 입력하세요.");
						sc.nextLine();
						continue;
					}
					break;
				}

			}

			while (true) {
				System.out.print("상품 설명 (간단하게)>>");
				description = sc.nextLine().trim();
				if (prodName.length() == 0) {
					System.out.println("[설명을 입력 안하셨습니다.. 다시 입력하세요.]");
					sc.nextLine();
					continue;
				} else {
					break;
				}

			}

		} // createBoolean
			// 입력 다 받은 후 DAO에서 메소드 부르기

		runMethod = dao.registerProduct(category, storeName, prodName, price, inventory, description);

		if (runMethod == 1) {
			result = true;
		} else
			result = false;

		return result;

	}

	// SELLER MENU 3 [3] 제품 목록 - PRODUCT MENU 1 - [2] 제품수정
	public boolean editProduct(Scanner sc, String storeName) {
		boolean result = false;
		boolean methodRun = true;
		int runMethod;
		String category = null, prodName = null, description = null, prodNumString = null, priceString, inventoryString;
		int prodNum = 0, price = 0, inventory = 0, rating = 0;

		while (methodRun) {
			System.out.print("[수정하실 제품에 번호를 입력하세요]: ");
			prodNumString = sc.nextLine().trim();
			if (prodNumString.length() == 0) {
				System.out.println("입력하신 제품번호가 없습니다... 수정을 종료하겠습니다");
				methodRun = false;
				break;
			} else {

				try {
					prodNum = Integer.parseInt(prodNumString);
				} catch (Exception e) {
					System.out.println("입력하신 제품번호에 오류가 있습니다... 수정을 종료하겠습니다");
					methodRun = false;
					break;
				}

				// 여기까지 오면 prodNum이 제대로 입력 됐다 (있는지 없는지는 확인 안함, 오류만 확인)
				while (true) {
					System.out.print("상품 종류(enter 수정안함) >>");
					category = sc.nextLine().trim();
					if (category.length() == 0) {
						category = null;
					}
					break;
				}
				// storeName, received from this method

				while (true) {
					System.out.print("상품 이름>>");
					prodName = sc.nextLine().trim();
					if (prodName.length() == 0) {
						prodName = null;
					}
					break;
				}
				// price
				while (true) {
					System.out.print("상품 가격>>");
					priceString = sc.nextLine().trim();

					if (priceString.length() == 0) { // 아무것도 입력 안했으면
						price = -1; // SellerDAO 에서 -1이면 수정 안함
						break;
					} else { // 뭐를 입력했으면
						try {
							price = Integer.parseInt(priceString);
						} catch (Exception e) {
							System.out.println("가격 입력 오류... 다시 입력하세요.");
							sc.nextLine();
							continue;
						}
						break; // break from inputting price
					}
				} // price

				while (true) {
					System.out.print("상품 갯수>>");
					inventoryString = sc.nextLine().trim();

					if (inventoryString.length() == 0) {
						inventory = -1;
						break;
					} else {

						try {
							inventory = Integer.parseInt(inventoryString);

						} catch (Exception e) {
							System.out.println("갯수 입력 오류... 다시 입력하세요.");
							sc.nextLine();
							continue;
						}

						break;

					}

				}

				while (true) {
					System.out.print("상품 설명 (간단하게)>>");
					description = sc.nextLine().trim();
					if (description.length() == 0) {
						description = null;
					}
					break;
				}

				// 수정할 필드들 다 받기 완료

				break;// 다 받았으니까
			} // else

		} // methodRun

		runMethod = dao.editProduct(prodNum, price, inventory, storeName, prodName, description, category);
		if (runMethod == 1) {
			result = true;
		} else
			result = false;

		return result;

	}

	// SELLER MENU 3 [3] 제품 목록 - PRODUCT MENU 1 - [3] 제품삭제
	public boolean deleteProduct(Scanner sc, String storName) {
		boolean result = false;
		boolean methodRun = true;
		String prodNumString, confirm;
		int prodNum;
		while (methodRun) {
			System.out.print("[삭제하실 제품에 번호를 입력하세요]: ");
			prodNumString = sc.nextLine().trim();
			if (prodNumString.length() == 0) {
				System.out.println("입력하신 제품번호가 없습니다... 삭제를 종료하겠습니다");
				methodRun = false;
				break;
			} else {
				try {
					prodNum = Integer.parseInt(prodNumString);
				} catch (Exception e) {
					System.out.println("입력하신 제품번호에 오류가 있습니다... 삭제를 종료하겠습니다");
					methodRun = false;
					break;
				}

				System.out.print("제품을 삭제하시겠습니까(y/n)>>");
				confirm = sc.nextLine().trim();

				if (confirm.equals("y") || confirm.equals("Y")) {
					if (dao.deleteProduct(prodNum) == 1)

						result = true;
					break;
				} else {
					result = false;
					break;
				}

			}
		} // while methodRun

		return result;
	}

	// SELLER MENU 4 - [4] 회원정보 수정

	public boolean update(String sellerID, Scanner sc) {
		String pwd;
		int wrongCount = 0;
		boolean updateRun = true;
		SellerVO vo = null;
		while (true) {
			System.out.print("현재 비밀번호를 입력하세요>>");
			pwd = sc.nextLine().trim();
			vo = dao.selectSeller(sellerID, pwd);

			// if password is wrong.
			if (vo == null) {
				wrongCount++;
				if (wrongCount < 3) {
					System.out.println("비밀번호가 틀렸습니다...");
					continue;
				} else {
					System.out.println("비밀번호가 3번 틀렸습니다...수정을 취소합니다.");
					updateRun = false;
					break;
				}

			} // if vo == null; Password is wrong
			break;
		} // while

		while (updateRun) {
			System.out.println("[현재 정보가 출력되었습니다]");
			System.out.println(vo.toString());

			String name = null, regionCode = null, storeMobile = null, storeAddr = null, pwd_check, birthDate = null,
					email = null;
			int regionCodeNum;
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
				try {
					System.out.println(
							"[1. 서울특별시 | 2. 인천광역시 | 3. 부산광역시 | 4. 울산광역시 | 5. 대전광역시 | 6. 광주광역시 7. 대구광역시 | 8. 경기도]");
					System.out.println(
							"[9. 강원도 | 10. 충청남도 | 11. 충청북도 | 12. 경상북도 | 13. 경상남도 | 14. 전라북도 | 15. 전라남도 | 16. 제주]");
					System.out.print("가개 지역코드 >>");
					regionCode = sc.nextLine().trim();
					regionCodeNum = Integer.parseInt(regionCode); // enter causes error.
				} catch (NumberFormatException e) {
					regionCodeNum = 0;
					break;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("지역번호 입력 오유, 다시 입력하세요....");
					continue;
				}

				// 공백의 경우 다시 입력
				if (regionCode.length() == 0) {
					regionCodeNum = 0;
				} else
					cnt++;
				break;
			}

			while (true) {
				// 전화번호 입력
				System.out.print("가개 전화번호>>");
				storeMobile = sc.nextLine().trim();
				if (storeMobile.length() != 0) {
					// 1. 패턴체크
					if (!mobileCheck(storeMobile)) {
						System.out.println("전화번호 형식이 틀립니다.xxx-xxxx-xxxx");
						continue;
					}
					cnt++;
					// 패턴 틀린경우 다시 입력
				} else
					storeMobile = null;
				break;
			}
			// 주소는 그냥 입력 공백이면 null
			System.out.print("주소>>");
			storeAddr = sc.nextLine().trim();
			if (storeAddr.length() == 0)
				storeAddr = null;
			else
				cnt++;

			// 생년월일은 그냥 입력 공백이면 null

			if (cnt != 0) {// update 진행
				if (dao.updateSeller(sellerID, pwd, name, email, storeMobile, storeAddr, regionCodeNum) == 1)// storeName,
					return true;
			}
		}
		return false;
	}// update

	// SELLER MENU 5 - [5] 회원탈퇴
	public boolean delete(Scanner sc, String userID) {
		boolean result = false;
		String pwd, confirm;
		// 아이디 패스워드 입력 받기
		System.out.print("패스워드>>");
		pwd = sc.nextLine().trim();

		System.out.print("탈퇴하시겠습니까(y/n)>>");
		confirm = sc.nextLine().trim();

		if (confirm.equals("y") || confirm.equals("Y")) {
			if (dao.SellerDelete(userID, pwd) == 1)
				result = true;
		} else {
			result = false;
		}
		return result;
	}// delete

}// SellerDAOUtil

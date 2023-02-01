package customer.dao;

import java.util.HashMap;
import java.util.Scanner;

public class BakerCompanyMain {

	public static void main(String[] args) {
		int menu = 0;
		Scanner sc = new Scanner(System.in);
		CustomerDAOUtil util = new CustomerDAOUtil();
		SellerDAOUtil util2 = new SellerDAOUtil();
		String id = null;
		String name = null;
		boolean result;
		boolean isJoin = false;

		while (true) {
			if (id == null) {
				System.out.println("___________________________________");
				System.out.println("--------Bread & Co. Main Menu -----");
				System.out.println("───────────────────────────────────");
				if (!isJoin)
					System.out.println("[1] 로그인");
				System.out.println("[2] 회원가입");
				System.out.println("[0] Bread & Co. 종료");
				System.out.println("───────────────────────────────────");

				System.out.print("[번호를 선택하세요]: ");
			}

			try {
				menu = sc.nextInt();sc.nextLine();
			} catch (Exception e) {
				System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
				//e.printStackTrace();
				sc.nextLine();
				continue;
			}
			if (menu == 0) {
				System.out.println("프로그렘을 종료합니다...");
				break;
			}
			switch (menu) {

			case 1: // 로그인
				if(id==null) {//로그인 진행
					HashMap<String,String> map=util.login(sc);
					if(map!=null) {
						id=map.get("id");
						name=map.get("name");

						System.out.println(name+"님 로그인 성공");				
					}
					else System.out.println("로그인 실패");
				}else {//로그아웃
					id=null;name=null;isJoin=false;
				}

				break;
			case 2: // 회원가입
				System.out.println("───────────────────────────────────");
				System.out.println("[선택: 2] 회원가입을 선택하셨습니다");
				while(true) {
					int subMenu;

					try {
						System.out.println("───────────────────────────────────");
						System.out.println("[회원가입]");
						System.out.println("[1] 소비자 ");
						System.out.println("[2] 판매자 ");
						System.out.println("[0] 취소, 매인 메뉴로 ");
						System.out.println("───────────────────────────────────");
						System.out.print("[회원가입 하실 메뉴를 선택하세요]: ");
						subMenu = sc.nextInt();
						sc.nextLine();
					} catch (Exception e) {
						System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
						//e.printStackTrace();
						sc.nextLine();
						continue;
					}
					
					
					if(subMenu==1) { //소비자 회원가입
						result = util.registerCustomer(sc);
						if(result) System.out.println("[회원가입 완료!]");
						else System.out.println("[회원가입 실패...]");
						break;
					}else if(subMenu==2) { //판매자 회원가입
						result = util2.registerSeller(sc);
						if(result) System.out.println("[회원가입 완료!]");
						else System.out.println("[회원가입 실패...]");
						break;
					}else if(subMenu==0){
						System.out.println("[메인 매뉴로 돌아갑니다...]");
						break;
					}else {
						System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
						System.out.println();
						continue;
					}

				}
				
				break;

			default:
				System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
				System.out.println();
				break;
			}//switch case (MAIN MENU)

		} // end of while

	}// main method

}// CustomerDAOMain

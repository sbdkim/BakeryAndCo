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
		int result;
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
				menu = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
				e.printStackTrace();
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

				break;

			default:
				System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
				break;
			}//switch case (MAIN MENU)

		} // end of while

	}// main method

}// CustomerDAOMain

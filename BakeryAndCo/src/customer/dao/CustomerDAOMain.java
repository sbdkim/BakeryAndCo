package customer.dao;

import java.util.Scanner;

public class CustomerDAOMain {

	public static void main(String[] args) {
		int menu = 0;
		CustomerDAOUtil util = new CustomerDAOUtil();
		Scanner sc = new Scanner(System.in);
		String id = null;

		while (true) {
			System.out.println("-----Bread & Co. Main Menu -----");
			System.out.println("[1] 회원가입");
			System.out.println("[2] 로그인");
			System.out.println("---------------------------------");
			System.out.print("[번호를 선택하세요]: ");
			try {
				menu = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
				e.printStackTrace();
				sc.nextLine();
				continue;
			}
			if (menu == 0)
				break;
			switch (menu) {

			case 1: //회원가입
				
				break;
			case 2: // 로그인
				
				break;
			}

		} // end of while

	}// main method

}// CustomerDAOMain

package customer.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
 
public class BakerCompanyMain {

	public static void main(String[] args) {
		int menu = 0;
		CustomerDAO dao = CustomerDAO.getInstance();
		ProductDAO pdao=ProductDAO.getInstance();
		
		ArrayList<ProductVO> pvo = null;
		
		CustomerDAOUtil util = new CustomerDAOUtil();
		Scanner sc = new Scanner(System.in);
		String id = null,name =null;
		boolean isJoin=false;//회원가입수행
		
//		String userID=null, pwd=null,  name=null,  gender=null,  email=null, mobile=null, addr=null;
//		Date birthDate=null;
//		Timestamp enrollDate=null ;
 
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
				util.createCustomer(sc);
				break;
			case 2: // 로그인
				if(id==null) {//로그인 진행
					HashMap<String,String> map=util.login(sc);
					if(map!=null) {
						id=map.get("userID");
						name=map.get("name");
						System.out.println(name+"님 로그인 성공");				
					}
					else System.out.println("로그인 실패");
				}else {//로그아웃
					id=null;name=null;isJoin=false;
				}
				
				//로그인 이후 진행
				System.out.println("------------------");
				System.out.println("상점목록");
				System.out.println("1. 파리바게트");
				System.out.println("2. 뚜레쥬르");
				System.out.println("------------------");
				int submenu;
				System.out.print("상점 선택 (1,2)>>");
				submenu = sc.nextInt();
				if(submenu==1) {//파리바게트
					pvo = pdao.selectProductALL();
					if(pvo!=null)
						for(ProductVO v : pvo)
							System.out.println(v.toString());
				}
				else if(submenu==2) {//뚜레쥬르
					
				}
				break;
				
				
			case 3 : 
				
				break;
			case 4 : 
				
				break;
			case 5 : 
				
				break;
				
			}

		} // end of while

	}// main method

}// CustomerDAOMain

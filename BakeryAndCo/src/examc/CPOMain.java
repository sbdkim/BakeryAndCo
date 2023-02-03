package examc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;



public class CPOMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		int menu = 0;
		CustDAO cao= CustDAO.getInstance();
		CustDAOUtil ctil=new CustDAOUtil();
		
		
//		ArrayList<CustVO> cvo = null;
		String id=null,pwd=null,name =null;
		boolean isJoin=false;
		
		CustVO co = cao.selectCust(id,pwd);
		
		ArrayList<CustVO> clist = null;
		
		ProdDAO pao = ProdDAO.getInstance();
		ArrayList<ProdVO> plist = null;
		
		int a= sc.nextInt();sc.nextLine();
		
		while (true) {
		System.out.println("        -- Test Ver. -- ");
		System.out.println("-- Bread & Co. Main Menu -----");
		System.out.println("[1] Cust 회원가입 ");
		System.out.println("[2] Cust 로그인");
		System.out.println("[3] Cust 부분조회");
		System.out.println("[4] Cust 전체조회");
		System.out.println("[5] Cust 정보수정");
		System.out.println("[6] Cust 회원탈퇴");
		
		System.out.println("[11] Prod 전체조회 ");
		System.out.println("[12] Prod 상품등록 ");
		
		System.out.println("[21] ord 주문삽입 ");
		System.out.println("[22] ord 주문수정 ");
		
		System.out.println("[30] Prod Cust 조인으로 장바구니 처리. ");
		System.out.println("[31] 장바구니를 Ord 로 삽입. ");
		
		
		System.out.println("[0] CPOMain 종료");
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
			ctil.createCust(sc);
			break;
		case 2:  
			if(id==null) {//로그인 진행
				HashMap<String,String> map=ctil.login(sc);
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
		case 3 : 
			//회원정보 조회 (menu==3)
			if(ctil.showCust(sc))System.out.println("조회성공");
			else System.out.println("조회실패");
			break;
			
		case 4 : 
			clist = cao.selectCustALL();
			for(CustVO vo : clist )
				System.out.println(vo.toString());
			break;
			
		case 5 :
			if(ctil.updateCust(sc))System.out.println("회원정보 수정 성공");
			else System.out.println("회원정보 수정 실패");
			break;
			
		case 6 :
			if(ctil.unregistCust(sc))System.out.println("회원탈퇴 성공");
			else System.out.println("회원탈퇴 실패");
			break;
			
			//------------------ 상품 내역 ------------
		case 11 :
		plist = pao.selectProdALL();
		for(ProdVO vo : plist )
			System.out.println(vo.toString());
		break;
		
		case 12 :
			break;
		
		case 13 :
			break;
			
			
			
			
			
		case 30 : // 로그인 하고 Prod Cust 조인으로 장바구니 처리.
			if(id==null) {//로그인 진행
				HashMap<String,String> map=ctil.login(sc);
				if(map!=null) {
					id=map.get("id");
					name=map.get("name");
					System.out.println(name+"님 로그인 성공");		
					
					//상품목록 조회
					plist = pao.selectProdALL();
					for(ProdVO vo : plist )
						System.out.println(vo.toString());
					
					
						
						
						
						
						
						
					
				}else System.out.println("로그인 실패");
			}else {//로그아웃
				id=null;name=null;isJoin=false;
			}
			break;
			
			//case 30 의 끝.
			
			
			
			
		case 31 : //장바구니를 Ord 로 삽입.
			String pname;
			System.out.println("pname 입력 >> ");
			pname = sc.nextLine().trim();
			//select * from prod where 
			ProdVO po = pao.viewProd(pname);
			 System.out.println(po.toString());
			ArrayList<ProdVO> aa = new ArrayList<ProdVO>();
//					po = new ProdVO(po.getPname()));
			
			break;
		}//switch

	}//while

}//main--end
}//class--end

package examc;

import java.util.Scanner;

public class OrdDAOUtil {

	OrdDAO dao= null;
	Scanner sc = new Scanner(System.in);
	
	public OrdDAOUtil() {
		super();
		dao=OrdDAO.getInstance();
	}
	
	
	
//	public boolean showOrd(Scanner sc) {
//		String id,password;
//		//아이디 패스워드 입력 받기
//		System.out.print("사용자 ID>>");
//		id=sc.nextLine().trim();
//		System.out.print("패스워드>>");
//		password=sc.nextLine().trim();		
//		OrdVO vo=null;
//		//dao 에 있는 dao.selectMember(String id, String password)
//		vo=dao.selectMember(id, password);
//		//없으면 false return		
//		if(vo==null)return false;
//		//있으면 출력 true return
//		System.out.println("----- 회원 정보 -----");
//		System.out.println("아이디 : "+vo.getId());
//		System.out.println("이름  : "+vo.getName());
//		System.out.println("전화번호 : "+vo.getMobile());
//		if(vo.getAddr()!=null)
//			System.out.println("주소 : "+vo.getAddr());
//		if(vo.getGrade()==0)
//			System.out.println("등급 : 일반사용자");
//		else System.out.println("등급 : 관리자");
//		System.out.println("----- 회원 정보 끝 -----");
//		return true;
//	}
}

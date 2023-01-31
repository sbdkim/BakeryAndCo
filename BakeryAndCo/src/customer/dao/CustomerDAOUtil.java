package customer.dao;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class CustomerDAOUtil {

	private Matcher match;
	CustomerDAO dao = null;
	
	public CustomerDAOUtil() {
		super();
		dao = CustomerDAO.getInstance();
	}
	
	//login 성공 : id 반환, 실패 null
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
			public HashMap<String,String> login(Scanner sc) {
				HashMap<String,String>  map=null;
				String id=null;
				String password=null;
				System.out.println("--- 로그인 아이디/패스워드 입력 ----");
				System.out.print("아이디>>");
				id=sc.nextLine();
				System.out.print("패스워드>>");
				password=sc.nextLine();
				CustomerVO vo=dao.selectCustomer(id, password);
				if(vo!=null) {
					map=new HashMap<String,String>();
					map.put("id", id);
					map.put("name",vo.getName());
				}	
				return map;
			}
		
		
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
			}
	
	
	
	
}//CustomerDAOUtil

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

	public HashMap<String, String> login(Scanner sc) {
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
	
	
	
	
	
	
}//CustomerDAOUtil

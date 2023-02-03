package examc;

import java.util.HashMap;
import java.util.Scanner;


public class CustDAOUtil {
	
	CustDAO dao= null;
	
	Scanner sc = new Scanner(System.in);
	
	public CustDAOUtil() {
		super();
		dao=CustDAO.getInstance();
	}
	
	
	public String login2(Scanner sc) {
		String id=null;
		String password=null;
		System.out.println("--- 로그인 아이디/패스워드 입력 ----");
		System.out.print("아이디>>");
		id=sc.nextLine();
		System.out.print("패스워드>>");
		password=sc.nextLine();
		CustVO vo=dao.selectCust(id, password);
		if(vo!=null)id=vo.getId();
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
		CustVO vo=dao.selectCust(id, password);
		if(vo!=null) {
			map=new HashMap<String,String>();
			map.put("id", id);
			map.put("name",vo.getName());
		}	
		return map;
	}
	
	//회원가입
	public int createCust(Scanner sc) {
		int result=0;
		String id=null, pwd=null,  name=null,   mobile=null;
		String pwdCheck=null;
		
	
	while(true) {
	//아이디 입력 ->
		System.out.print("사용자 ID(영문자,숫자조합(8~20자리)>>");
		id=sc.nextLine().trim();
	//2. 아이디 중복 조회 select메소드를 DAO 만들기 boolean selectMember(String id)
		//정상
		if(dao.selectCust(id)) {
			System.out.println("이미 사용중인 ID 입니다.");
			continue;				
		}
		break;
	}
	while(true) {
	//패스워드 입력
		System.out.print("패스워드 ID(영문자,숫자,특수문자 조합(10~20자리)>>");
		pwd=sc.nextLine().trim();
	//패스워드 체크 입력
		System.out.print("패스워드 다시 입력 >>");
		pwdCheck=sc.nextLine().trim();
	//두 패스워드 비교 틀린경우 다시 입력
		if(!pwd.equals(pwdCheck)) {
			System.out.println("입력하신 패스워드 2개가 다릅니다.");
			continue;						
		}			
		break;
	}
	while(true) {
		//이름 입력
		System.out.print("이름 >>");
		name=sc.nextLine().trim();
		//공백의 경우 다시 입력
		if(name.length()==0) {
			System.out.println("이름은 필수 항목 입니다.");
			continue;									
		}
		break;
	}
		//전화번호 입력
		System.out.print("휴대폰 번호>>");
		mobile=sc.nextLine().trim();			
	//연결~~~
	result=dao.createCust(id, pwd, name, mobile);
	System.out.println("회원가입 완료");
	//삽입완료
	return result;
	}
	//회정정보 조회 id,password 입력받아서 있으면 출력 해주고 true 반환, 그렇지않으면 false반환
		public boolean showCust(Scanner sc) {
			String id=null,password=null;
			//아이디 패스워드 입력 받기
			System.out.print("사용자 ID>>");
			id=sc.nextLine().trim();
			System.out.print("패스워드>>");
			password=sc.nextLine().trim();		
			CustVO vo=null;
			//dao 에 있는 dao.selectMember(String id, String password)
			vo=dao.selectCust(id, password);
			//없으면 false return		
			if(vo==null)return false;
			//있으면 출력 true return
			System.out.println("----- 회원 정보 -----");
			System.out.println("아이디 : "+vo.getId());
			System.out.println("이름  : "+vo.getName());
			System.out.println("전화번호 : "+vo.getMobile());
			System.out.println("----- 회원 정보 끝 -----");
			return true;
		}
	
		public boolean updateCust(Scanner sc) {
			String id,pwd,name,mobile;
			//아이디 패스워드 입력 받기
			int vo;
			
			System.out.println("--------수정하기---");
			System.out.print("아이디 >> ");
			id= sc.nextLine().trim();
			System.out.print("비밀번호 >> ");
			pwd= sc.nextLine().trim();
			
			if(dao.selectCust(id)==true) {
//					System.out.println("중복 아이디");
				System.out.print("이름 >> ");
				name= sc.nextLine().trim();
				System.out.print("전화번호 >> ");
				mobile= sc.nextLine().trim();
				vo = dao.updateCust(id, pwd,name,mobile);
				if(vo==0) {
					System.out.println("해당 아이디/비밀번호가 없습니다.(수정 실패)");
					return false;
				}
			}else {
					System.out.println("없는 아이디 입니다.");
					return false;
			}
			return true;
		}
		
		public boolean delete(Scanner sc) {
			//아이디 비번 입력
			String id,password;
			//아이디 패스워드 입력 받기
			System.out.print("사용자 ID>>");
			id=sc.nextLine().trim();
			System.out.print("패스워드>>");
			password=sc.nextLine().trim();		
			if(dao.CustDelete(id, password)==1)return true;
			else return false;
		}
		
		public boolean unregistCust(Scanner sc) {
			String id=null,pwd=null;
			int vo;
			System.out.println("--------계정 탈퇴하기---");
			System.out.print("아이디 >> ");
			id= sc.nextLine().trim();
			System.out.print("비밀번호 >> ");
			pwd= sc.nextLine().trim();
			
			if(dao.selectCust(id)==true) {
				vo = dao.updateCust(id, pwd);
				if(vo==0) {
					System.out.println("해당 아이디/비밀번호가 없습니다.(수정 실패)");
					return false;
				}
			}else {
					System.out.println("없는 아이디 입니다.");
					return false;
			}
			return true;
		}
		
		/////////////////////////////////////////
		
		public void Custcart (Scanner sc) {
			
		}
		//////////////////////////////////////////////////////
		
		
		
		

}

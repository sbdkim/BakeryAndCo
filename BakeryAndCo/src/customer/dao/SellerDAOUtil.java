
	package customer.dao;

	import java.util.ArrayList;
import java.util.Date;
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
		
		public String login2(Scanner sc) {
			String id=null;
			String password=null;
			System.out.println("--- 로그인 아이디/패스워드 입력 ----");
			System.out.print("아이디>>");
			id=sc.nextLine();
			System.out.print("패스워드>>");
			password=sc.nextLine();
			SellerVO vo=dao.selectSeller(id, password);
			if(vo!=null)id=vo.getSellerID();
			else id=null;
			return id;
		}
		
		
		
		public HashMap<String, String> login(Scanner sc) {
			HashMap<String, String> map = null;
			String id = null;
			String password = null;
			System.out.println("--- 로그인 아이디/패스워드 입력 ----");
			System.out.print("아이디>>");
			id = sc.nextLine();
			System.out.print("패스워드>>");
			password = sc.nextLine();
			SellerVO vo = dao.selectSeller(id, password);
			if (vo != null) {
				map = new HashMap<String, String>();
				map.put("id", id);
				map.put("name", vo.getName());
			}
			return map;
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
			 String pattern ="^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\\\\\<>|_-[+]=\\\\`~\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}])[A-Za-z[0-9]!@#$%^&*?,./\\\\\\\\<>|_-[+]=\\\\`~\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}]{10,20}$";
			 match = Pattern.compile(pattern).matcher(email);	
			 if(match.find()) {//패턴에 맞는지 확인
				  chk = true;
			 }
			 return chk;
		}
		
		
		public boolean viewCustomer(Scanner sc) {
		
		
			CustomerVO vo=null;
			
			dao.viewCustomers();
			//없으면 false return		
			if(dao.viewCustomers()==null)return false;
		    
			System.out.println("----- 지금까지 주문했던 회원 정보 -----");
			System.out.println("아이디 : "+vo.getUserID());
			System.out.println("이름  : "+vo.getName());
			System.out.println("생년월일 : "+vo.getBirthDate());
			System.out.println("e-mail : "+vo.getEmail());
			System.out.println("주소 : "+vo.getAddr());
			
			System.out.println("----- 회원 정보 끝 -----");
			return true; 
		}
		
		public boolean delete(Scanner sc) {
			// 아이디 비번 입력
			String id, pwd;
			// 아이디 패스워드 입력 받기
			System.out.print("판매자 ID를 입력하세요 >> ");
			id = sc.nextLine().trim();
			System.out.print("패스워드를 입력하세요 >> ");
			pwd = sc.nextLine().trim();
			
			
			if (dao.SellerDelete(id, pwd) == 1)
				return true;
			else
				return false;
		}// delete
		
		public boolean viewProduct(Scanner sc) {
		 
			boolean result = false;
			ArrayList<ProductVO> list = null;
			list = dao.viewProducts();
			if(list!=null) {
				int i = 0;
				for(ProductVO vo: list) {
					i++;
					System.out.println("[" + i + "]" +vo.getProdName()+vo.getScategory()+vo.getPrice()+vo.getInventory()+vo.getRegisterdate() );
				}	
				result = true;
			}

			return result;
		}
		public boolean pwdUpdate(Scanner sc) {
			boolean result = false;
			SellerVO vo= null;
			String id,pwd,mob,name,pwdchk;
		    Date birthdate=null;
		
			int cnt=0;
			
			while(true) {
			System.out.print("사용자 ID를 입력하세요");
			id=sc.nextLine().trim();
			if(id.length()==0) System.out.print("ID는 필수적으로 입력해야합니다");
	
			System.out.print("패스워드를 입력하세요");
			pwd=sc.nextLine().trim();	
			
			System.out.print("이름을 입력하세요");
			name=sc.nextLine().trim();
			
			
			System.out.print("전화번호를 입력하세요");
			mob=sc.nextLine().trim();
			
			System.out.print("생년월일을 입력하세요");
			birthdate=sc.next();
			System.out.println(vo.toString());
			vo=dao.passwordReset(id, pwd, name, mob, birthdate);
			if(vo==null) {
				System.out.println("없는 판매자 입니다.");
				continue;
			}
			break;	
			
			}
			
			while(true) {
				System.out.print("패스워드(enter 수정안함)를 입력하세요>>");
				pwd=sc.nextLine().trim();	
				if(pwd.length()!=0) {
					
					if(!this.pwdCheck(pwd)) {
						System.out.println("부적합한 비밀번호 형식 입니다. 영문자,특수문자,숫자조합(10~20자리)");
						continue;					
					}
					System.out.print("패스워드를 다시 입력하세요>>");
					pwdchk=sc.nextLine().trim();	
					if(!pwd.equals(pwdchk)) {
						System.out.println("입력하신 패스워드 2개가 다릅니다.");
						continue;						
					}	
					cnt++;//수정:패스워드가 오류가 없는경우
				}else pwd=null;
				break;
			}
			return true;
			
		}
		
		
		
		
		public boolean viewOrders(Scanner sc) {
			String storename=null;
			boolean result = false;
			ArrayList<OrderVO> list = null;
			list = dao.viewOrders(storename);
			if(list!=null) {
				int i = 0;
				for(OrderVO vo: list) {
					i++;
					System.out.println("[" + i + "] " +vo.getorderNo()+vo.getuserID()+vo.getProdName()+vo.getquantity());
				}	
				result = true;
			}

			return result;
		}

	
		
		
		
		
		
	}//SellerDAOUtil
	



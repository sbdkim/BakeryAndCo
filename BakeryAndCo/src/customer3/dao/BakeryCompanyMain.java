package customer3.dao;

import java.util.HashMap;
import java.util.Scanner;

public class BakeryCompanyMain {

	public static void main(String[] args) {
		int menu = 0; 
		boolean entireProgram = true;
		Scanner sc = new Scanner(System.in);
		CustomerDAOUtil util = new CustomerDAOUtil();
		SellerDAOUtil util2 = new SellerDAOUtil();
		String id = null;
		String name = null;
		boolean result;
		boolean isJoin = false;

		while (entireProgram == true) {
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
				// e.printStackTrace();
				sc.nextLine();
				continue;
			}
			if (menu == 0) {
				System.out.println("프로그렘을 종료합니다...");
				break;
			}

			if (menu == 1) {

				int subMenu;// subMenu for 로그인
				while (true) {
					System.out.println("───────────────────────────────────");
					System.out.println("[로그인]");
					System.out.println("[1] 소비자 ");
					System.out.println("[2] 판매자 ");
					System.out.println("[0] 취소, 매인 메뉴로 ");
					System.out.println("───────────────────────────────────");
					System.out.print("[로그인 하실 메뉴를 선택하세요]: ");

					try {

						subMenu = sc.nextInt();
						sc.nextLine();
					} catch (Exception e) {
						System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
						// e.printStackTrace();
						sc.nextLine();
						continue;
					}
					// 1 customer login, 2 seller login 0 cancel
					if (subMenu == 1) {
						if (id == null) {// 로그인 진행
							HashMap<String, String> map = util.login(sc);
							if (map != null) {
								id = map.get("id");
								name = map.get("name");

								System.out.println("[" + name + "님 로그인 성공]");
								int customerMenu; // 소비자 메뉴
								boolean customerRun = true;
								while (customerRun) {
									System.out.println("───────────────────────────────────");
									System.out.println("[소비자 메뉴]");
									System.out.println("[1] 주문내역");
									System.out.println("[2] 주문하기");
									System.out.println("[3] 회원 정보 수정");
									System.out.println("[4] 회원탈퇴");
									System.out.println("[5] 로그아웃");
									System.out.println("[6] 프로그램 종료");
									System.out.println("───────────────────────────────────");
									try {
										// customer 로그인한 상테
										System.out.print("[메뉴번호를 선택하세요]: ");
										customerMenu = sc.nextInt();
										sc.nextLine();
									} catch (Exception e) {
										System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
										// e.printStackTrace();
										sc.nextLine();
										continue;
									}

									if (customerMenu > 6 || customerMenu < 1) {
										System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
										// e.printStackTrace();
										sc.nextLine();
										continue;
									}
									int customerOrderMenu; // 소비자 주문내역 메뉴

									boolean result2 = false;
									if (customerMenu == 1) {

										System.out.println("───────────────────────────────────");
										System.out.println("[주문내역]");
										System.out.println("[1] 완료된 주문 목록");
										System.out.println("[2] 진행중인 주문 목록");
										System.out.println("[3] 돌아가기");
										System.out.println("───────────────────────────────────");
										try {
											System.out.print("[메뉴번호를 선택하세요]: ");
											customerOrderMenu = sc.nextInt();
											sc.nextLine();
										} catch (Exception e) {
											System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
											// e.printStackTrace();
											sc.nextLine();
											continue;
										}

										if (customerOrderMenu > 3 || customerOrderMenu < 1) {
											System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
											// e.printStackTrace();
											sc.nextLine();
											continue;
										}

										if (customerOrderMenu == 1) {
											System.out.println("[1] 완료된 주문 목록을 선택하셨습니다");
											result2 = util.showCompletedOrder(id);
											if (result2) {
												System.out.println("[완료된 주문 목록 출력 완료]");
												System.out.println();
											} else {
												System.out.println("[완료된 주문 목록 출력 실패]");
												System.out.println();
											}
											continue;
										} else if (customerOrderMenu == 2) {
											System.out.println("[2] 진행중인 주문 목록을 선택하셨습니다");
											result2 = util.showCurrentOrder(id);
											if (result2) {
												System.out.println("[진행중인 주문 목록 출력 완료]");
												System.out.println();
											} else {
												System.out.println("[진행중인 주문 목록 출력 실패]");
												System.out.println();
											}
											continue;
										} else {
											System.out.println("[3] 소비자 메뉴로 다시 돌아갑니다");
											System.out.println();
											continue;//customerMenu로 돌아가기
										}

										
										
										
										
										
										
										
										
									} else if (customerMenu == 2) { //주문하기
										while(true) {
											int regionCode;
											System.out.println("───────────────────────────────────");
											System.out.println("[주문하기]");
											//충청남도/충청북도/경상북도/경상남도/전라북도/전라남도/제주
											System.out.println("[1. 서울특별시 | 2. 인천광역시 | 3. 부산광역시 | 4. 울산광역시 | 5. 대전광역시 | 6. 광주광역시 7. 대구광역시 | 8. 경기도]");
											System.out.println("[9. 강원도 | 10. 충청남도 | 11. 충청북도 | 12. 경상북도 | 13. 경상남도 | 14. 전라북도 | 15. 전라남도 | 16. 제주]");
											System.out.println("───────────────────────────────────");
											try {
												System.out.print("[상점지역을 선택하세요]: ");
												regionCode = sc.nextInt();
												sc.nextLine();
											} catch (Exception e) {
												System.out.println("[오류: 입력하신 지역번호에 오류가 있습니다.. 다시 입력하세요]");
												// e.printStackTrace();
												sc.nextLine();
												continue;
											}
											if (regionCode > 3 || regionCode < 1) {
												System.out.println("[오류: 입력하신 지역번호에 오류가 있습니다.. 다시 입력하세요]");
												// e.printStackTrace();
												sc.nextLine();
												continue;
											}
											
											result2 = util.showRegionStore(regionCode);
											int numberofStores = util.showRegionStoreNumber(regionCode);
											if (result2) {
												System.out.println("───────────────────────────────────");
												System.out.println();
												while(true) {//상점들어가기
													int storeSelected; //selected number
									
													try {
														System.out.print("들어가실 상점번호를 입력하세요: ");
														storeSelected = sc.nextInt();
														sc.nextLine();
													}catch(Exception e) {
														System.out.println("[오류: 입력하신 상점번호에 오류가 있습니다.. 다시 입력하세요]");
														// e.printStackTrace();
														sc.nextLine();
														continue;
													}
													
													if(storeSelected > numberofStores || storeSelected< 0) {
														System.out.println("[오류: 입력하신 상점번호에 오류가 있습니다.. 다시 입력하세요]");
														// e.printStackTrace();
														sc.nextLine();
														continue;
													}
													
													
													
													
													
													
													
												}//상점에들어가기
												
												
												
											} else {
												System.out.println("[입력하신 지역에 상점이 없습니다...소비자 메뉴로 돌아갑니다]");
												System.out.println();
												break;
											}//입력한 지역에 상점 없음 - 소비자 메뉴 돌아가기
											
											
											
											
											
										} //while regionCode
										//주문하기
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										//continue;//customerMenu로 돌아가기
									} else if (customerMenu == 3) {
										//회원정보 조회
										result2 = util.update(id, sc);
										if (result2) {
											System.out.println("[회원정보 수정 완료]");
											System.out.println();
										} else {
											System.out.println("[회원정보 실패]");
											System.out.println();
										}
										
										continue;
									} else if (customerMenu == 4) {
										//회원탈퇴
										result2 = util.delete(sc);
										if (result2) {
											System.out.println("[회원탈퇴 완료]");
											System.out.println();
											customerRun = false;
											break;
										} else {
											System.out.println("[회원탈퇴 실패]");
											System.out.println();
										}
										
										continue;//customerMenu로 돌아가기
									} else if (customerMenu == 5) {
										System.out.println("[로그아웃 완료!]");
										//로그아웃
										id = null;
										name = null;
										isJoin = false;
										break;
									} else {
										//프로그렘 종료
									}
								} // customerMenu while loop

							} else
								System.out.println("[로그인 실패]");
						} else {// 로그아웃
							id = null;
							name = null;
							isJoin = false;
						}
					} else if (subMenu == 2) {
						if (id == null) {// 로그인 진행
							HashMap<String, String> map = util2.login(sc);
							if (map != null) {
								id = map.get("id");
								name = map.get("name");

								System.out.println("[" + name + "님 로그인 성공]");

								// 판매자 로그인 상태
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								
								

							} else
								System.out.println("[로그인 실패]");
						} else {// 로그아웃
							id = null;
							name = null;
							isJoin = false;
						}
					} else if (subMenu == 0) {
						break;
					} else {
						System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
						System.out.println();
						continue;
					}
				} // 로그인 while loop
			} else if (menu == 2) {
				System.out.println("───────────────────────────────────");
				System.out.println("[선택: 2] 회원가입을 선택하셨습니다");
				while (true) {
					int subMenu2; // subMenu for 회원가입
					try {
						System.out.println("───────────────────────────────────");
						System.out.println("[회원가입]");
						System.out.println("[1] 소비자 ");
						System.out.println("[2] 판매자 ");
						System.out.println("[0] 취소, 매인 메뉴로 ");
						System.out.println("───────────────────────────────────");
						System.out.print("[회원가입 하실 메뉴를 선택하세요]: ");
						subMenu2 = sc.nextInt();
						sc.nextLine();
					} catch (Exception e) {
						System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
						// e.printStackTrace();
						sc.nextLine();
						continue;
					}
					if (subMenu2 == 1) { // 소비자 회원가입
						result = util.registerCustomer(sc);
						if (result)
							System.out.println("[회원가입 완료!]");
						else
							System.out.println("[회원가입 실패...]");
						break;
					} else if (subMenu2 == 2) { // 판매자 회원가입
						result = util2.registerSeller(sc);
						if (result)
							System.out.println("[회원가입 완료!]");
						else
							System.out.println("[회원가입 실패...]");
						break;
					} else if (subMenu2 == 0) {
						System.out.println("[메인 매뉴로 돌아갑니다...]");
						break;
					} else {
						System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
						System.out.println();
						continue;
					}
				} // 회원가입 while loop
			} else {
				System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
				System.out.println();
				break;
			}

		} // end of while

	}// main method

}// CustomerDAOMain

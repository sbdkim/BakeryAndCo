package customer.dao;

import java.util.ArrayList;
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
		boolean result = false;
		boolean isJoin = false;
		boolean loginBoolean = true;

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
				while (loginBoolean) {
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
									ArrayList<SellerVO> list = null;
									ArrayList<ProductVO> list2 = null;
									ArrayList<ProductVO> list3 = null;
									ArrayList<CartVO> list4 = null;

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
											continue;// customerMenu로 돌아가기
										}

									} else if (customerMenu == 2) { // 주문하기
																	// //**************************************************************************************************주문하기
																	// 시작
										while (true) {
											int regionCode;
											String sellerID, storeName, category, prodName ;
											int active, quantity, price; 
											System.out.println("───────────────────────────────────");
											System.out.println("[주문하기]");
											// 충청남도/충청북도/경상북도/경상남도/전라북도/전라남도/제주
											System.out.println(
													"[1. 서울특별시 | 2. 인천광역시 | 3. 부산광역시 | 4. 울산광역시 | 5. 대전광역시 | 6. 광주광역시 7. 대구광역시 | 8. 경기도]");
											System.out.println(
													"[9. 강원도 | 10. 충청남도 | 11. 충청북도 | 12. 경상북도 | 13. 경상남도 | 14. 전라북도 | 15. 전라남도 | 16. 제주]");
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

											list = new ArrayList<SellerVO>(); // 지역에 있는 가격 모두 출력
											list = util.hasRegionStore(regionCode);
											int numberofStores = util.showRegionStoreNumber(regionCode);
											if (list != null) {
												
												while (true) {// 상점들어가기
													list = util.showRegionStore(regionCode);
													System.out.println("───────────────────────────────────");
													System.out.println();
													
													int storeSelected; // selected number

													try {
														System.out.print("들어가실 상점번호를 입력하세요: ");
														storeSelected = sc.nextInt();
														sc.nextLine();
													} catch (Exception e) {
														System.out.println("[오류: 입력하신 상점번호에 오류가 있습니다.. 다시 입력하세요]");
														// e.printStackTrace();
														sc.nextLine();
														continue;
													}

													if (storeSelected > numberofStores || storeSelected < 0) {
														System.out.println("[오류: 입력하신 상점번호에 오류가 있습니다.. 다시 입력하세요]");
														// e.printStackTrace();
														sc.nextLine();
														continue;
													}

													storeName = (list.get(storeSelected - 1)).getStoreName();
													// get store name fro the selected menu
													System.out.println();
													System.out.println();
													System.out.println("[" + storeName + "에 오신것을 환영합니다!!]");
													// ENTER THE STORE DONE!!!!!

													list2 = new ArrayList<ProductVO>(); // 지역에 있는 가개 모두 출력
												//	list3 = new ArrayList<ProductVO>();//
													
												//	list4 = new ArrayList<CartVO>();
													int categorySelected = 0;
													String categorySelectedString;
													while (true) {
														System.out.println("───────────────────────────────────");
														list2 = util.showCategory(storeName);
														System.out.print("관심있으신 제품에 케타고리를 입력하시오>>");
														categorySelectedString = sc.nextLine().trim();
														
														if(categorySelectedString.equals("0")) {
															System.out.println("───────────────────────────────────");
															System.out.println("[장바구니로 이동하셨습니다]");
															list4 = util.viewCart(); //prints the cart;
															//CART에 담은거 모든것 출력!!!!!!!!
															
															
															

															
															
														}else if (categorySelectedString.length() == 0) {
															result = util.emptyCart(); 
															if(result) System.out.println("[장바구니 비우기 완료]");
															else System.out.println("[장바구니 비우기 완료]");
															
															System.out
																	.println("[ENTER를 입력하셨습니다.. 다시 가개 매뉴로 돌아갑니다...]");
															break;
														} else {
															try {
																categorySelected = Integer
																		.parseInt(categorySelectedString);
																// 제품 출력
																
																
																
																
																
																
																
																
																
																boolean selectProduct = true;
																String selectedProduct = null;
																int selectedProductNum;
																while(selectProduct) {
																	category = (list2.get(categorySelected - 1).getCategory()); 
																	System.out.println("───────────────────────────────────");
																	System.out.println("[" + category + " 을 선택하셨습니다]");
																	list3 = util.viewStoreProduct(storeName, category); //PRINTS ALL THE PRODUCTS IN THAT CATEGORY (RETURN ARRAYLIST OF THE PRODUCTS)
																	System.out.print("장바구니에 담으실 물건에 [번호]를 입력하세요: " );
																	selectedProduct = sc.nextLine().trim();
								
																	
																	if(selectedProduct.length() == 0) {
																		
																		System.out.println("[상품을 입력 안하셨습니다.. 다시 케타고리로 돌아갑니다...]");
																		break;
																	}else {
																		try {
																			//프로덕트 list2에 있는 index number를 입력 받았다. 
																			selectedProductNum = Integer.parseInt(selectedProduct);
																			
																			
																			//index를 사용해서 가 ProductList list2 에 있는 prodName이랑 price를 받는다.
																			prodName = list3.get(selectedProductNum-1).getProdName();
																			price = list3.get(selectedProductNum-1).getPrice(); 
																			
																			System.out.print("담으실 갯수를 입력하세요");
																			quantity = sc.nextInt();
																			sc.nextLine();
																			
																			int added = util.addToCart(id, prodName, price, quantity);
																			if(added == 1) {
																				System.out.println("[장바구니에 담으쎴습니다]");
																			}else {
																				System.out.println("[장바구니에 담기 실패]");
																			}
																			
																					
																					
																					
																					
																					
																					
																					
																					
																					
																					
																					
																					
																					
																		}catch(Exception e) {
																			System.out.println("입력하신 제품 입력 오류... 다시 입력하세요.");
																			
																			continue;
																		}
																	}
																	
																	
																	
																	
																	
																}
																
																
															
															} catch (Exception e) {
																result = util.emptyCart(); 
																if(result) System.out.println("[장바구니 비우기 완료]");
																else System.out.println("[장바구니 비우기 완료]");
																System.out.println("[잘못입력하셨습니다...장바구니를 비우고 가개 매뉴로 돌아갑니다]");
																sc.nextLine();
																continue;
															}

														}

													} // 케타고리에들어가기


												} // 상점에들어가기

											} else {
												System.out.println("[입력하신 지역에 상점이 없습니다...소비자 메뉴로 돌아갑니다]");
												System.out.println();
												break;
											} // 입력한 지역에 상점 없음 - 소비자 메뉴 돌아가기

										} // while regionCode
											// 주문하기

										// continue;//customerMenu로 돌아가기
										// *************************************************************************************************************************************************
										// 주문하기 끝

									} else if (customerMenu == 3) {
										// 회원정보 조회
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
										// 회원탈퇴
										result2 = util.delete(sc, id);
										if (result2) {
											System.out.println("[회원탈퇴 완료]");
											System.out.println();
											customerRun = false;
											break;
										} else {
											System.out.println("[회원탈퇴 실패]");
											System.out.println();
										}

										continue;// customerMenu로 돌아가기
									} else if (customerMenu == 5) {
										System.out.println("[로그아웃 완료!]");
										// 로그아웃
										id = null;
										name = null;
										isJoin = false;
										break;
									} else {
										// 프로그렘 종료
										System.out.println("[프로그램을 종료하겠습니다...]");
										loginBoolean = false;
										entireProgram = false;
										break;
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
								String storeName;
								id = map.get("id");
								name = map.get("name");
								storeName = map.get("storeName");
								System.out.println("[" + name + "님 로그인 성공]");

								// 판매자 로그인 상태
								int sellerMenu;
								boolean sellerRun = true;
								while (sellerRun) {
									System.out.println("───────────────────────────────────");
									System.out.println("[판매자 메뉴]");
									System.out.println("[1] 주문목록");
									System.out.println("[2] 고객목록");
									System.out.println("[3] 제품 목록 - 제품 등록/수정");
									System.out.println("[4] 회원정보 수정");
									System.out.println("[5] 회원탈퇴");
									System.out.println("[6] 로그아웃");
									System.out.println("[7] 프로그램 종료");
									System.out.println("───────────────────────────────────");
									try {
										System.out.print("[메뉴번호를 선택하세요]: ");
										sellerMenu = sc.nextInt();
										sc.nextLine();
									} catch (Exception e) {
										System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
										// e.printStackTrace();
										sc.nextLine();
										continue;
									}

									if (sellerMenu > 7 || sellerMenu < 1) {
										System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
										// e.printStackTrace();
										sc.nextLine();
										continue;
									}

									if (sellerMenu == 1) {

										System.out.println("[" + storeName + "의 주문목록]");

										if (util2.viewStoreOrder(storeName)) {
											System.out.println("[모든 주문목록 조회 완료]");
											System.out.println();
										} else {
											System.out.println("[주문목록 조회 실패]");
											System.out.println();
										}
									} else if (sellerMenu == 2) { // 고객목록
										System.out.println("[" + storeName + "의 고객목록]");
										if (util2.viewCustomerList(storeName)) {
											System.out.println(" 모든 고객 조회 완료]");
											System.out.println();
										} else {
											System.out.println("[모든 고객 조회 실패]");
											System.out.println();
										}
									} else if (sellerMenu == 3) {
										System.out.println("[" + storeName + "의 제품목록]");

										// 제품등록/ 제품 수정 메뉴
										int productMenu;
										boolean productRun = true;
										while (productRun) {
											if (util2.viewStoreProduct(storeName)) {
												System.out.println("[제품목록 조회 완료]");
											} else {
												System.out.println("[제품목록 조회 실패]");
											}
											System.out.println();
											System.out.println("───────────────────────────────────");
											System.out.println("[제품목록 메뉴]");
											System.out.println("[1] 제품등록");
											System.out.println("[2] 제품수정");
											System.out.println("[3] 취소, 판매자 메뉴로 돌아가기");
											System.out.println("───────────────────────────────────");
											try {
												System.out.print("[메뉴번호를 선택하세요]: ");
												productMenu = sc.nextInt();
												sc.nextLine();
											} catch (Exception e) {
												System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
												// e.printStackTrace();
												sc.nextLine();
												continue;
											}

											if (productMenu > 4 || sellerMenu < 1) {
												System.out.println("[오류: 입력하신 매뉴에 오류가 있습니다.. 다시 입력하세요]");
												// e.printStackTrace();
												sc.nextLine();
												continue;
											}

											if (productMenu == 1) {// 제품등록
												if (util2.createProduct(sc, storeName)) {
													System.out.println("[제품등록 완료!]");
												} else {
													System.out.println("[제품 등록 실패]");
												}
											} else if (productMenu == 2) {// 제품수정하기
												// 먼저 제품목록 또 보여주기
												util2.viewStoreProduct(storeName);

												if (util2.editProduct(sc, storeName)) {
													System.out.println("제품 수정 완료");
												} else {
													System.out.println("제품 수정 실팬");
												}

											} else {
												System.out.println("판매자 메뉴로 다시 돌아갑니다...");
												productRun = false;
												break;

											}

										} // productRun

									} else if (sellerMenu == 4) {// 회원정보 수정 (완료)

										if (util2.update(id, sc)) {
											System.out.println("[회원정보 수정 완료]");
											System.out.println();
										} else {
											System.out.println("[회원정보 수정 실패]");
											System.out.println();
										}
									} else if (sellerMenu == 5) {// 회원탈퇴
										if (util2.delete(sc, id)) {
											System.out.println("[회원탈퇴 완료]");
											System.out.println();
											sellerRun = false;
											break;
										} else {
											System.out.println("[회원탈퇴 실패]");
											System.out.println();
										}

										continue;// customerMenu로 돌아가기
									} else if (sellerMenu == 6) {// 로그아웃
										System.out.println("[로그아웃 완료!]");
										// 로그아웃
										id = null;
										name = null;
										isJoin = false;
										break;
									} else if (sellerMenu == 7) {// 프로그램 종료
										System.out.println("[프로그램을 종료하겠습니다...]");
										loginBoolean = false;
										entireProgram = false;
										break;

									}

								} // sellerRun while

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

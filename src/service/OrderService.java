package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import dao.CardDao;
import dao.OrderDao;
import dao.StoreDao;

public class OrderService {
	
	private OrderService(){}
	private static OrderService instance;
	public static OrderService getInstance() {
		if(instance == null){
			instance = new OrderService();
		}
		return instance;
	}
	public static int count = 0;
	public static String STRADD = null;
	public static int STORENO = 0;
	public static String ORD_SLT = null;
	public static int PRODNO = 0;
	public static Object PRODNAME = null;
	public static Object ORDNO = 0;
	public static Object sels = 0;
	
	
	private static CardDao cardDao = CardDao.getInstance();
	
	private static OrderDao orderDao = OrderDao.getInstance();

	public int ord(){

			while(true){
			Map<String, Object> param = new HashMap<>();
			
			param.put("ORD_STA", 1);

			int update = orderDao.deleteord(param);
			break;
			}
	
		System.out.println("========== 픽업방법 선택 ==========");
		System.out.println("1: TAKE OUT 2: 매장식사");
		System.out.print("입력>");
		int one = 1;
					
		Map<String, Object> param = new HashMap<>();
		ORD_SLT = ScanUtil.nextLine();
		param.put("ORD_SLT", ORD_SLT);
		param.put("ORD_STA", one);
		int result = orderDao.insertord(param);
		
		List<Map<String, Object>> ordno = orderDao.ordno();
		
		ORDNO = ordno.get(0).get("ORD_NO");//주문번호 임시저장
		
		
		if(0 < result){
			return View.STOREORD;
		}else{
			System.out.println("실패");
			}
		return View.ORD;
		}
	
	private StoreDao storeDao = StoreDao.getInstance();
	
	public int storeOrd(){
	
		while(true){

			List<Map<String, Object>> store_list = storeDao.selectstorelist();

			System.out.println("====================================================================");
			System.out.println("매장번호\t매장연락처\t\t매장이름\t\t  매장주소\t\t운영시간");
			System.out.println("====================================================================");
			for(int i = 0; i < store_list.size(); i++){
				System.out.print(store_list.get(i).get("STR_NO") + "\t");
				System.out.print(store_list.get(i).get("STR_CAL") + "\t");
				System.out.print(store_list.get(i).get("STR_NAME") + "         \t");
				System.out.print(store_list.get(i).get("STR_ADD")+ "\t");
				System.out.print(store_list.get(i).get("STR_DATE") + "\t");
				System.out.println();
			}
			System.out.println("====================================================================");
			System.out.println("1.매장 검색 \t2.매장 선택 \t3.돌아가기");
			System.out.print("입력>");

			int input = ScanUtil.nextInt();

			switch (input) {
			case 1:
				return View.STOREORDADD;
				
			case 2:
				System.out.println("매장번호");
				STORENO = ScanUtil.nextInt();
				return View.STORECHOICE;
			case 3:
				return View.ORD;
			}
		}
	}
	
	
	public int storeselect(){
		while(true){

			System.out.println("검색할 가게 주소");
			System.out.print("입력>");
			
			STRADD = ScanUtil.nextLine();
			
			List<Map<String, Object>> storeaddList = storeDao.selectstoreadd();
				
			System.out.println("======================================================================");
			System.out.println("매장번호\t매장연락처            \t매장이름                 \t매장주소             \t운영시간");
			System.out.println("======================================================================");
			for(int i = 0; i < storeaddList.size(); i++){
				System.out.print(storeaddList.get(i).get("STR_NO") + "\t");
				System.out.print(storeaddList.get(i).get("STR_CAL") + "\t");
				System.out.print(storeaddList.get(i).get("STR_NAME") + "         \t");
				System.out.print(storeaddList.get(i).get("STR_ADD") + "\t");
				System.out.print(storeaddList.get(i).get("STR_DATE"));
				System.out.println();
			}
			System.out.println("======================================================================");
			System.out.println("1.매장검색 \t2.매장선택 \t3.돌아가기");
			System.out.print("입력>");
			int input = ScanUtil.nextInt();
			
			switch (input) {
			case 1:
				return View.STOREORDADD;
			case 2:
				System.out.println("매장번호");
				STORENO = ScanUtil.nextInt();
				return View.STORECHOICE;
			case 3:
				return View.USERMAIN;
			}
		}
	}
	
	
	public int storechoice(){
		
	
			System.out.println("===================== 주문작성중 =====================");
		//상품목록

		while(true){
			List<Map<String, Object>> prodlist = storeDao.selectprodlist();
			System.out.println("★STARBUK의 다양한 음료들을 만나보세요★");
			
			System.out.println("========================================================");
			System.out.println("상품번호\t상품명                      \t가격         \t설명");
			System.out.println("========================================================");
			for(int i = 0; i < prodlist.size(); i++){
				System.out.print(prodlist.get(i).get("PDT_NO") + "\t");
				System.out.print(prodlist.get(i).get("PDT_NAME") + "      \t");
				System.out.print(prodlist.get(i).get("PRICE") +"\t");
				System.out.print(prodlist.get(i).get("PDT_EX"));
				System.out.println();
			}
			System.out.println("========================================================");
			System.out.println("구입할 상품 번호");
			System.out.print("입력>");

			PRODNO = ScanUtil.nextInt();
			Map<String, Object> prname = orderDao.selectprname(PRODNO);
			
			PRODNAME = prname.get("PDT_NAME");
			
			break;
		}
		
		int result = 0;
			System.out.println("구입할 상품 개수");
			int PDTNUM = ScanUtil.nextInt();
			
			for(int i = 0; i < PDTNUM; i++){
			Map<String, Object> param = new HashMap<>();
			param.put("PDT_NAME",PRODNAME);
			param.put("STR_NO", STORENO);
			param.put("ORD_NO", ORDNO);
			

			result = orderDao.prord(param);
			
			
			
			}
			

			
			if(0 < result){
				System.out.println("계속 주문하시겠습니까?");
				System.out.println("1.계속하기 2.주문끝내기");
				int in = ScanUtil.nextInt();
				if(in == 1){
				return View.STORECHOICE;
				}else if(in == 2){
					while(1 == 1){
					int t = 2;
					Map<String, Object> par = new HashMap<>();
						par.put("ORD_STA", t);
						orderDao.updateord(par);
						break;
					}
					

				
					while(true){
						List<Map<String, Object>> memcardmoney = cardDao.memcardmoney();
						
						int mon = Integer.parseInt(String.valueOf((memcardmoney.get(0).get("MONEY"))));
						
						Map<String, Object> sel = cardDao.sel();

						sels = Integer.parseInt(String.valueOf(sel.get("PRICE")));
						
						int ok = Integer.parseInt(String.valueOf(sel.get("PRICE")));
						
						if(ok > mon){
							System.out.println("잔액이 부족합니다."); 
							while(true){
								Map<String, Object> param = new HashMap<>();
								
								param.put("ORD_NO", ORDNO);

								orderDao.deleteprordmon(param);
								break;
								}
							
							while(true){
								Map<String, Object> param = new HashMap<>();
								
								param.put("ORD_NO", ORDNO);

								orderDao.deleteordmon(param);
								break;
								}

							
							return View.USERMAIN;
						}else if(ok < mon){
						
						List<Map<String, Object>> cardselect = cardDao.cardselect();
						
						Map<String, Object> param = new HashMap<>();

						param.put("MONEY", sels);
						
						cardDao.payment(param);
						
						
						break;
						}
					}
					
					
					
					List<Map<String, Object>> ordsum = orderDao.ordsum();

					System.out.println("==============================================================");
					System.out.println("주문번호  아이디\t결제일        \t 결제카드명  \t픽업 방법    \t상품명     \t가격");
					System.out.println("==============================================================");
					for(int i = 0; i < ordsum.size(); i++){
						int Num = Integer.parseInt(String.valueOf(ordsum.get(i).get("ORD_SLT")));
						System.out.print(ordsum.get(i).get("ORD_NO") + "    ");
						System.out.print(ordsum.get(i).get("ID") + "    ");
						System.out.print(BoardService.sDate.format(ordsum.get(i).get("ORD_DATE")) + "    ");
						System.out.print(ordsum.get(i).get("CARD_NAME") + "    ");
						if(Num == 1){
							System.out.print("take out  ");
						}else if(Num == 2){
							System.out.print("매장 식사  ");
						}else{
							System.out.print("맞지 않는 방법을 고르셨습니다.");
						}
						System.out.print(ordsum.get(i).get("PDT_NAME") + "    ");
						System.out.print(ordsum.get(i).get("PRICE") + "    ");
						System.out.println();
					}
					System.out.println("==============================결제 완료==========================");
					System.out.println("결제 금액 : " + sels + "원 입니다");	
					System.out.println("==============================================================");
					return View.USERMAIN;
				}else{
				System.out.println("주문 실패");
				return View.BOARD_INSERT_FORM;
				}
			}
			return View.BOARD_INSERT_FORM;
	}
	
	//관리자 주문관리
	public int adminord(){
		while(true){
		List<Map<String, Object>> prord_list = orderDao.selectprordlist();

		System.out.println("==================================");
		System.out.println("매장 이름                    상품명                       주문번호");
		System.out.println("==================================");
		for(int i = 0; i < prord_list.size(); i++){
			
			System.out.print(prord_list.get(i).get("STR_NAME") + "   \t");
			System.out.print(prord_list.get(i).get("PDT_NAME") + "       \t");
			System.out.print(prord_list.get(i).get("ORD_NO"));

			System.out.println();
		}
		System.out.println("===================================");
		System.out.println("1.주문정보 조회  2. 주문 완료 처리  3.돌아가기");
		System.out.print("입력>");
		
		int input = 0;
		input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:
			return View.ADMIN_ORD_INFO;
		case 2:
			return View.ADMIN_ORD_RECEIPT;
		case 3:
			return View.ADMINMAIN;
			
		}
		}
	}	
	
	//주문정보 조회
	public int ordinfo(){
	
	System.out.println("조회할 주문번호");
	int ordno = util.ScanUtil.nextInt();
		
	Map<String, Object> ordinfo = orderDao.ordinfo(ordno);
	
	Object ORD_NO = ordinfo.get("ORD_NO");
	Object ID = ordinfo.get("ID");
	Object ORD_DATE = ordinfo.get("ORD_DATE");
	Object CARD_NO = ordinfo.get("CARD_NO");

	
	int ORD_SLT = Integer.parseInt(String.valueOf(ordinfo.get("ORD_SLT")));
	int ORD_STA = Integer.parseInt(String.valueOf(ordinfo.get("ORD_STA")));
	
	System.out.println("=============================================");
	System.out.println("주문번호:\t"+ORD_NO+"\t");
	System.out.println("아이디 :\t"+ID+"\t");
	System.out.println("결제일 :\t"+BoardService.sDate.format(ORD_DATE));
	System.out.println("결제수단 :\t"+CARD_NO+"\t");
	if(ORD_SLT == 1){
	System.out.println("픽업방법 :\t"+"TAKE OUT"+"\t");
	}
	if(ORD_SLT == 2){
		System.out.println("픽업방법 :\t"+"매장식사"+"\t");
		}
	if(ORD_STA == 1){
		System.out.println("주문 상태 :\t 삭제 대기중인 주문 ");
		}
	if(ORD_STA == 2){
		System.out.println("주문 상태 :\t 주문 대기중");
		}
	if(ORD_STA == 3){
		System.out.println("주문 상태 :\t 주문 처리 완료 ");
		}
	System.out.println("=============================================");
	System.out.println("1.목록으로");
	int input = ScanUtil.nextInt();
	switch (input) {
	case 1:
		return View.ADMIN_ORD;	

	}
	return View.ADMIN_ORD_INFO;
	}
	
	//주문접수
	
	public int receipt(){
		
		System.out.println("접수할 주문 번호 ");
		int receipt = util.ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("P.ORD_NO", receipt);
		
		orderDao.receipt(param);
		

		System.out.println("주문 번호가 "+receipt+"번인 주문번호 주문(들)을 접수하였습니다.");
		return View.ADMIN_ORD;
	}
	
	
	
	
}
	

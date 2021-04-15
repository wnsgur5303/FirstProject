package service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.StoreDao;
import util.ScanUtil;
import util.View;

public class StoreService {

	private StoreService(){}
	private static StoreService instance;
	public static StoreService getInstance(){
		if(instance == null){
			instance = new StoreService();
		}
		return instance;
	}
	public static Object del;
	public static Object name;
	public static Object add;
	public static Object call;
	public static Object openclose;
	public static Object strno;
	public static Object pricedate;
	private StoreDao storeDao = StoreDao.getInstance();
	
	
	public int store_list() {
		while(true){

			List<Map<String, Object>> store_list = storeDao.selectstorelist();

			System.out.println("=====================================================================");
			System.out.println("매장번호\t매장연락처\t\t매장이름\t\t  매장주소\t\t운영시간");
			System.out.println("=====================================================================");
			for(int i = 0; i < store_list.size(); i++){
				System.out.print(store_list.get(i).get("STR_NO") + "\t");
				System.out.print(store_list.get(i).get("STR_CAL") + "\t");
				System.out.print(store_list.get(i).get("STR_NAME") + "         \t");
				System.out.print(store_list.get(i).get("STR_ADD")+ "\t");
				System.out.print(store_list.get(i).get("STR_DATE") + "\t");
				System.out.println();
			}
			System.out.println("=====================================================================");
			System.out.println("1.매장 정보 조회 \t2.매장 등록 \t3.돌아가기");
			System.out.print("입력>");

			int input = ScanUtil.nextInt();

			switch (input) {
			case 1:
				return View.STORE_VIEW;
			case 2:
				return View.STORE_INSERT_FORM;
			case 3:
				return View.ADMINMAIN;
				
			}
		}
	}


	public int store_view() {
		
	
		System.out.println("조회할 매장 번호");
		System.out.print("입력>");
		int input = util.ScanUtil.nextInt();
		
		Map<String, Object> store = storeDao.SelectStore(input);
		
		Object STR_NO = store.get("STR_NO");
		Object STR_CAL = store.get("STR_CAL");
		Object STR_NAME = store.get("STR_NAME");
		Object STR_ADD = store.get("STR_ADD");
		Object STR_DATE = store.get("STR_DATE");
		
		System.out.println("=====================================================================");
		System.out.println("매장 번호 :  "+STR_NO);
		System.out.println("매장 연락처 : "+STR_CAL);
		System.out.println("지점명 :    "+STR_NAME);
		System.out.println("매장 주소 :  "+STR_ADD);
		System.out.println("운영 시간 :  "+STR_DATE);
		System.out.println("=====================================================================");
		System.out.println("1.목록으로 2.삭제  3.수정");
		int num = ScanUtil.nextInt();
		switch (num) {
		case 1:
			if(OrderService.count == 1){
			return View.STOREORD;
			}
			else{
				return View.STORE_LIST;
			}
		case 2:
			del = STR_NO;
			return View.STORE_DELETE;
			
		case 3:
			call = STR_CAL;
			name = STR_NAME;
			add = STR_ADD;
			openclose= STR_DATE;
			strno = STR_NO;
			return View.STORE_UPDATE;
		}
		
		return View.STORE_VIEW;
	}


	public int store_delete() {
		Map<String, Object> param = new HashMap<>();
		
		param.put("STR_NO", del);

		storeDao.deleteStore(param);
		
		return View.STORE_LIST;		
	}


	public int store_update() {
		String strcal;
		String stradd;
		String strdate;
		String ph_pattern = "[0-9]{4}-[0-9]{4}";
		String add1_pattern = "([가-힣]{0,})\\s([가-힣]{0,})\\s([가-힣0-9]{0,})\\s([0-9]{0,})";
		String time_pattern = "[0-9]{2}:[0-9]{2}~[0-9]{2}:[0-9]{2}";
		System.out.println("========== 게시글수정 ==========");
		
		System.out.print("연락처>"+call+"\n");
		System.out.println("매장 연락처(ex.xxxx-xxxx) >");
		ph:while(true){
			strcal = ScanUtil.nextLine();
			if(strcal.matches(ph_pattern)){
				break ph;
			}else{
				System.out.println("올바른 연락처가 아닙니다 ('-'포함 하여 작성))");
				System.out.println("매장 연락처(ex.xxxx-xxxx) >");
			}
		}
		System.out.print("지점명>"+name+"\n");
		String strname = ScanUtil.nextLine();
		System.out.print("매장주소>"+add+"\n");
		System.out.println("매장 주소(ex.대전시 중구 대종로 486) >");
		add:while(true){
			stradd = ScanUtil.nextLine();
				if(stradd.matches(add1_pattern)){
					break add;
				}else{
					System.out.println("올바른 주소가 아닙니다. (공백 포함, 도로명 주소 양식에 맞춰 바르게 작성)");
					System.out.println("매장 주소(ex.대전시 중구 대종로 486) >");
				}
			}
		System.out.print("운영시간>"+openclose+"\n");
		System.out.println("운영 시간(ex.09:00~20:00) >");
		time:while(true){
			strdate = ScanUtil.nextLine();
				if(strdate.matches(time_pattern)){
					break time;
				}else{
					System.out.println("올바른 운영시간이 아닙니다. ('~'포함 24시 표기 양식에 맞춰 바르게 작성)");
					System.out.println("운영 시간(ex.09:00~20:00) >");
				}
			}
				
		
		Map<String, Object> param = new HashMap<>();
		param.put("STR_CAL", strcal);
		param.put("STR_NAME", strname);
		param.put("STR_ADD", stradd);
		param.put("STR_DATE", strdate);
		storeDao.updateStore(param);
		
		
		return View.STORE_LIST;
	}


	public int store_insert() {
		String strcal;
		String stradd;
		String strdate;
		String ph_pattern = "[0-9]{4}-[0-9]{4}";
		String add1_pattern = "([가-힣]{0,})\\s([가-힣]{0,})\\s([가-힣0-9]{0,})\\s([0-9]{0,})";
		String time_pattern = "[0-9]{2}:[0-9]{2}~[0-9]{2}:[0-9]{2}";
		System.out.println("========== 매장정보 입력 ==========");
		System.out.println("매장 연락처(ex.xxxx-xxxx) >");
		ph:while(true){
			strcal = ScanUtil.nextLine();
			if(strcal.matches(ph_pattern)){
				break ph;
			}else{
				System.out.println("올바른 연락처가 아닙니다 ('-'포함 하여 작성))");
				System.out.println("매장 연락처(ex.xxxx-xxxx) >");
			}
		}
		System.out.println("지점명>");
		String strname = ScanUtil.nextLine();
		System.out.println("매장 주소(ex.대전시 중구 대종로 486) >");
		add:while(true){
			stradd = ScanUtil.nextLine();
				if(stradd.matches(add1_pattern)){
					break add;
				}else{
					System.out.println("올바른 주소가 아닙니다. (공백 포함, 도로명 주소 양식에 맞춰 바르게 작성)");
					System.out.println("매장 주소(ex.대전시 중구 대종로 486) >");
				}
			}
		System.out.println("운영 시간(ex.09:00~20:00) >");
		time:while(true){
			strdate = ScanUtil.nextLine();
				if(strdate.matches(time_pattern)){
					break time;
				}else{
					System.out.println("올바른 운영시간이 아닙니다. ('~'포함 24시 표기 양식에 맞춰 바르게 작성)");
					System.out.println("운영 시간(ex.09:00~20:00) >");
				}
			}
		
		Map<String, Object> param = new HashMap<>();
		param.put("STR_CAL", strcal);
		param.put("STR_NAME", strname);
		param.put("STR_ADD", stradd);
		param.put("STR_DATE", strdate);

		int result = storeDao.insertstore(param);
		
		if(0 < result){
			System.out.println("매장 추가 완료");
		}else{
			System.out.println("매장 추가 실패");
			return View.STORE_INSERT_FORM;
			}
		return View.STORE_LIST;
	}
	
	public int salse(){
		List<Map<String, Object>> store_list = storeDao.selectstorelist();

		System.out.println("=============================================================");
		System.out.println("매장번호\t매장연락처\t\t매장이름\t\t  매장주소\t\t운영시간");
		System.out.println("=============================================================");
		for(int i = 0; i < store_list.size(); i++){
			System.out.print(store_list.get(i).get("STR_NO") + "\t");
			System.out.print(store_list.get(i).get("STR_CAL") + "\t");
			System.out.print(store_list.get(i).get("STR_NAME") + "         \t");
			System.out.print(store_list.get(i).get("STR_ADD")+ "\t");
			System.out.print(store_list.get(i).get("STR_DATE") + "\t");
			System.out.println();
		}
		System.out.println("=============================================================");
		System.out.print("매장번호 입력>");

		strno = util.ScanUtil.nextInt();
		
		Map<String, Object> storename = storeDao.storename();
		
		Object STR_NAME = storename.get("STR_NAME");
		
		System.out.println("매장이름 : "+STR_NAME);
		
		System.out.println("1.매출 총합 2.날짜별 총합 3.돌아가기");
		int choice = util.ScanUtil.nextInt();
		if(choice == 1){
		Map<String, Object> salse1 = storeDao.storesalse();
		
		Object sumprice = salse1.get("PRICE");
		
		System.out.print("총 매출액: ");
		System.out.println(sumprice);
		}
		else if(choice == 2){
			System.out.println("1.일별 매출  2.월별 매출  3.연도별 매출");
			int input = ScanUtil.nextInt();
			switch(input){
			case 1 : 
			System.out.println("날짜입력 ex)20201010 >");
			pricedate = util.ScanUtil.nextInt();
			
			Map<String, Object> datesalse = storeDao.storedateprice();
			
			Object sumprice = datesalse.get("PRICE");
			
			
			System.out.print("매출액: ");
			System.out.println(sumprice);
			break;
			case 2 :
			System.out.println("날짜입력 ex)202010");
			pricedate = util.ScanUtil.nextInt();
			
			Map<String, Object> monthsalse = storeDao.storemonthprice();
			
			Object sumprice2 = monthsalse.get("PRICE");
			
			System.out.print("매출액: ");
			System.out.println(sumprice2);
			break;
			case 3 :
				System.out.println("연도 입력 ex)2020");
				pricedate = util.ScanUtil.nextInt();
				
				Map<String, Object> yearsalse = storeDao.storeyearprice();
				
				Object sumprice3 = yearsalse.get("PRICE");
				
				System.out.print("매출액: ");
				System.out.println(sumprice3);
				break;
			}
		}else{
		return View.ADMINMAIN;
		}
		return View.STORE_SALES;
	}
}
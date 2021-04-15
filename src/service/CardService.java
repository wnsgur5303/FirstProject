package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.BoardDao;
import dao.CardDao;

public class CardService {

	
	
	private CardService(){}
	private static CardService instance;
	public static CardService getInstance() {
		if(instance == null){
			instance = new CardService();
		}
		return instance;
	}
	
	
	
	private static CardDao cardDao = CardDao.getInstance();

	
	public static int cardno;
	
	
	public static int card_list(){
		
		while(true){
	
			List<Map<String, Object>> cardList = cardDao.cardList();
			System.out.println("=============================================");
			System.out.println("번호\t이름\t\t잔액");
			System.out.println("=============================================");
			for(int i = 0; i < cardList.size(); i++){
				System.out.print(cardList.get(i).get("CARD_NO") + "\t");
				System.out.print(cardList.get(i).get("CARD_NAME") + "\t");
				System.out.print(cardList.get(i).get("MONEY") + "\t");
				System.out.println();
			}
			System.out.println("=============================================");
			List<Map<String, Object>> settingcard = cardDao.settingcard();
			Object membercard = settingcard.get(0).get("CARD_NO");
			System.out.println("사용중인 카드:" + membercard);
			System.out.println("=============================================");
			System.out.println("1.카드변경 2.카드등록 3.카드삭제 4.카드충전 5.뒤로가기");
			System.out.print("입력>");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:
			return View.CARD_SET;
		case 2:
			return View.CARD_CREATE;
		case 3:

			return View.CARD_DEL;
		case 4:
			return View.CARD_MONEY;
		case 5:
			return View.USERMAIN;
		}
		return View.CARD;
		}
	}
	
	public int card_change(){//카드변경

		
		System.out.println("========== 카드변경 ==========");
		
		System.out.println("사용할 카드 번호>");
		int cardsetno = ScanUtil.nextInt();
		cardno = cardsetno;
		
		
		List<Map<String, Object>> cardList = cardDao.cardList();
		
		for(int i = 0; i < cardList.size(); i++){
			int ok =  Integer.parseInt(String.valueOf(cardList.get(i).get("CARD_NO")));
			if(ok == cardsetno){
				
				List<Map<String, Object>> cardselect = cardDao.cardselect();

				Map<String, Object> param = new HashMap<>();
				param.put("CARD_NO", cardsetno);
				
				int update = cardDao.cardchange(param);
				return View.CARD;
			}else{
				System.out.println("맞는 카드가 없습니다.");
				return View.CARD;
			}
		}
		return View.CARD;
		
}
	
	public int card_del(){//카드삭제
		
		
			System.out.println("삭제할 카드번호");
			int dlecard = ScanUtil.nextInt();

			Map<String, Object> param = new HashMap<>();
			
			param.put("CARD_NO", dlecard);

			cardDao.deletecard(param);
			
			return View.CARD;		
		}
	
	public int card_money(){
		
		
		System.out.println("충전할 카드 이름>");
		String cardname = ScanUtil.nextLine();
		System.out.println("충전할 금액 >");
		int money = ScanUtil.nextInt();
		
		List<Map<String, Object>> cardselect = cardDao.cardselect();

		Map<String, Object> param = new HashMap<>();
		param.put("MONEY", money);
		param.put("CARD_NAME", cardname);
		
		int update = cardDao.cardcharge(param);
		
		return View.CARD;
		}
	
	public int card_insert(){
		System.out.println("========== 카드작성  ==========");
		
		
		System.out.print("카드명칭 > ");
		String cardname = ScanUtil.nextLine();
		Map<String, Object> cname = cardDao.selectCard(cardname);

		if(cname == null){
			System.out.println("중복되지 않은 아이디");
		}else{
			System.out.println("중복된 아이디 입니다.");
			return View.CARD_CREATE;
		}
		
		System.out.println();
		Map<String, Object> param = new HashMap<>();
		
		param.put("CARD_NAME",cardname);

		int update = cardDao.createcard(param);
		
		if(0 < update){
			System.out.println("카드생성 성공");
			return View.CARD;
		}else{
			System.out.println("카드생성 실패");
			return View.CARD;
			}
		}
	
	
}

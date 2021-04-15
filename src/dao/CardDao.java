package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import service.BoardService;
import service.CardService;
import service.OrderService;
import service.StoreService;
import util.JDBCUtil;

public class CardDao {

	private CardDao(){}
	private static CardDao instance;
	public static CardDao getInstance() {
		if(instance == null){
		instance = new CardDao();
	}
	return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public int createcard(Map<String, Object> p){//카드생성
		String sql = "INSERT INTO CARD VALUES ((select nvl(max(CARD_NO), 0) + 1 from CARD), '"+Controller.id+"', ?, 0)";
		
		List<Object> param = new ArrayList<>();

		param.add(p.get("CARD_NAME"));
		
		
		return jdbc.update(sql,param);
		
	}
	
	public List<Map<String, Object>> cardList(){//카드 리스트
	String sql = "select * from CARD WHERE ID = '"+Controller.id+"'";
	return jdbc.selectList(sql);
	
	}
	
	public List<Map<String, Object>> cardmoney(){//카드 잔액
		String sql = "select MONEY from CARD WHERE ID = '"+Controller.id+"'";
		return jdbc.selectList(sql);
		
		}
	
	public List<Map<String, Object>> settingcard(){//사용중인 카드
		String sql = "select CARD_NO FROM MEMBER WHERE ID = '"+Controller.id+"'";
		return jdbc.selectList(sql);
		
		}
	
	
	public List<Map<String, Object>> cardselect(){//카드탐색
		String sql = "SELECT CARD_NO FROM CARD WHERE ID = '"+Controller.id+"' AND CARD_NO ="+CardService.cardno+"";
		return jdbc.selectList(sql);
		
		}
	

	
	public int cardchange(Map<String, Object> p){//카드변경
		String sql = "UPDATE MEMBER SET CARD_NO = ? WHERE ID = '"+Controller.id+"' ";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("CARD_NO"));
		
		
		return jdbc.update(sql,param);
		
	}
	
	public int deletecard(Map<String, Object> p){//카드삭제
		String sql = "DELETE CARD WHERE CARD_NO = ? AND ID = '"+Controller.id+"'";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("CARD_NO"));

		return jdbc.update(sql,param);
		
	}
	
	public int cardcharge(Map<String, Object> p){//카드충전
		String sql = "UPDATE CARD SET MONEY = MONEY + ? WHERE CARD_NAME = ? and ID = '"+Controller.id+"' ";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		
		param.add(p.get("MONEY"));
		param.add(p.get("CARD_NAME"));
		
		
		return jdbc.update(sql,param);
		
	}
	
	public int payment (Map<String, Object> p){//카드 결제 
		String sql = "UPDATE CARD SET MONEY = MONEY - ? WHERE CARD_NO = (SELECT CARD_NO FROM ORD WHERE ORD_NO = "+OrderService.ORDNO+") and ID = '"+Controller.id+"' ";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		
		param.add(p.get("MONEY"));
		
		
		return jdbc.update(sql,param);
		
	}
	
	public Map<String, Object> sel(){//날짜별 매출
		String sql = "SELECT PRICE FROM (SELECT sum(PRICE) PRICE FROM PR_ORD PD,PRODUCT PT, ORD OD WHERE PD.PDT_NAME = PT.PDT_NAME AND PD.ORD_NO =OD.ORD_NO AND OD.ORD_STA = 2 AND OD.ORD_NO = "+OrderService.ORDNO+")";

		return jdbc.selectOne(sql);

	}

	public Map<String, Object> selectCard(String cardname) {
		String sql = "SELECT * from CARD WHERE CARD_NAME = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(cardname);
	
		return jdbc.selectOne(sql,param);
	}
	public List<Map<String, Object>> memcardmoney(){//현재 카드 잔액
		String sql = "select MONEY from CARD WHERE CARD_NO = (SELECT CARD_NO FROM ORD WHERE ORD_NO = "+OrderService.ORDNO+")";
		
		return jdbc.selectList(sql);
	}
}
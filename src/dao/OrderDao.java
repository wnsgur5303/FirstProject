package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import service.BoardService;
import service.OrderService;
import util.JDBCUtil;


public class OrderDao {

	private OrderDao(){}
	private static OrderDao instance;
	public static OrderDao getInstance(){
		if(instance == null){
			instance = new OrderDao();
		}
		return instance;
	}
	
	
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public int insertord(Map<String, Object> po){//주문생성
		String sql = "INSERT INTO ORD VALUES ((select nvl(max(ORD_NO), 0) + 1 from ORD), (SELECT ID FROM MEMBER where ID = '"+Controller.id+"')"
				     +",(SELECT TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/DD')) FROM dual)"
				     + ",(SELECT CARD_NO FROM MEMBER where ID ='"+Controller.id+"'), ? ,?)";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(po.get("ORD_SLT"));
		param.add(po.get("ORD_STA"));
		
		return jdbc.update(sql,param);
		
	}

		public List<Map<String, Object>> ordno() {//주문번호 조회
			String sql = "select MAX(ORD_NO) ORD_NO from ORD WHERE ORD_STA = 1 ORDER BY ORD_NO DESC";
			return jdbc.selectList(sql);
		}
	
	
	public int prord(Map<String, Object> p) {
		String sql = "INSERT INTO PR_ORD (PR_OD_NO, PDT_NAME, STR_NO, ORD_NO)"
					+ " VALUES ((select nvl(max(PR_OD_NO), 0) + 1 from PR_ORD), ?, ?, ? )";
		
		List<Object> param = new ArrayList<>();
		param.add(p.get("PDT_NAME"));
		param.add(p.get("STR_NO"));
		param.add(p.get("ORD_NO"));
	
		
		return jdbc.update(sql,param);
	}
	
	public Map<String, Object> selectprname(int pRODNO){//상품이름을 번호로 조회
		String sql = "SELECT PDT_NAME FROM PRODUCT WHERE PDT_NO = ?";
		
		List<Object> params = new ArrayList<>();
		params.add(pRODNO);
	return jdbc.selectOne(sql,params);
	
	
	}
	
	
	public Map<String, Object> selectord(String userId){//주문 조회
		String sql = "SELECT ORD_NO FROM MEMBER WHERE ID = ? AND ORD_STA = 1";
		
		List<Object> params = new ArrayList<>();
		params.add(userId);
	return jdbc.selectOne(sql,params);
	
	
	}
	
	
	public int updateord(Map<String, Object> p){//주문요청
		String sql = "UPDATE ORD SET ORD_STA = ? WHERE ORD_NO = "+OrderService.ORDNO+"";
		
		List<Object> par = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		par.add(p.get("ORD_STA"));

		return jdbc.update(sql,par);
		
	}
	

	public int deleteord(Map<String, Object> p){//실패된 주문삭제
		String sql = "DELETE ORD WHERE ORD_STA = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("ORD_STA"));

		return jdbc.update(sql,param);
	}
	
	
	public List<Map<String, Object>> selectprordlist() {//상품주문 조회
		String sql = "select p.pr_od_no pr_od_no,p.PDT_NAME PDT_NAME,(SELECT STR_NAME FROM STORE WHERE STR_NO = p.STR_NO) STR_NAME,p.ORD_NO ORD_NO, o.ORD_SLT ORD_SLT, o.ORD_STA ORD_STA "
					 +"from PR_ORD P ,ORD O "
					 +"where P.ORD_NO = O.ORD_NO"
					 +" AND O.ORD_STA = 2";
		return jdbc.selectList(sql);
	}
	
	
	public Map<String, Object> ordinfo(int ordno){//주문 정보 조회
		String sql = "SELECT * FROM ORD WHERE ORD_NO = ?";
		
		List<Object> ordparam = new ArrayList<>();
		ordparam.add(ordno);
	return jdbc.selectOne(sql,ordparam);
	
	
	}
	
	
	public int receipt(Map<String, Object> p){//주문접수
		String sql = "UPDATE ORD SET ORD_STA = 3 WHERE ORD_NO = ?";
		
		List<Object> receipt = new ArrayList<>();
		receipt.add(p.get("P.ORD_NO"));
		
		
		return jdbc.update(sql,receipt);
		
	}
	
	public List<Map<String, Object>> ordsum(){//명세서
		String sql = "SELECT * FROM "
				+"(SELECT OD.ORD_NO ORD_NO, OD.ID ID, OD.ORD_DATE ORD_DATE,"
				+" (SELECT CARD_NAME FROM CARD WHERE CARD_NO = (SELECT CARD_NO FROM MEMBER WHERE ID = '"+Controller.id+"')) CARD_NAME,"
				+" OD.ORD_SLT ORD_SLT,PR.PDT_NAME PDT_NAME,(SELECT PRICE FROM PRODUCT WHERE PDT_NAME = PR.PDT_NAME) PRICE"
				+" FROM ORD OD, PR_ORD PR "
				+" WHERE OD.ORD_NO = PR.ORD_NO "
				+" AND OD.ORD_NO = "+OrderService.ORDNO+")";

		return jdbc.selectList(sql);

	}
	public int deleteordmon(Map<String, Object> p){//실패된 주문삭제
		String sql = "DELETE ORD WHERE ORD_NO = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("ORD_NO"));

		return jdbc.update(sql,param);
	}
	
	public int deleteprordmon(Map<String, Object> p){//실패된 주문삭제
		String sql = "DELETE PR_ORD WHERE ORD_NO = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("ORD_NO"));

		return jdbc.update(sql,param);
	}
}

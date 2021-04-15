package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import service.BoardService;
import service.OrderService;
import service.StoreService;
public class StoreDao {

	private StoreDao(){}
	private static StoreDao instance;
	public static StoreDao getInstance(){
		if(instance == null){
			instance = new StoreDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectstorelist() {
		String sql = "select * from STORE";
		
		return jdbc.selectList(sql);
	}
	
	public Map<String, Object> SelectStore(int input) {
		String sql = "select * from STORE WHERE STR_NO = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(input);
		
		return jdbc.selectOne(sql,param);
	}

	public int deleteStore(Map<String, Object> p) {
		String sql = "DELETE STORE WHERE STR_NO = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("STR_NO"));

		return jdbc.update(sql,param);
	}

	public int updateStore(Map<String, Object> p) {
		String sql = "UPDATE STORE SET STR_CAL = ? , STR_NAME = ?, STR_ADD = ?, STR_DATE = ? WHERE STR_NO = ("+StoreService.strno+")";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("STR_CAL"));
		param.add(p.get("STR_NAME"));
		param.add(p.get("STR_ADD"));
		param.add(p.get("STR_DATE"));
		
		return jdbc.update(sql,param);
	}

	public int insertstore(Map<String, Object> p) {
		String sql = "INSERT INTO STORE (STR_NO, STR_CAL, STR_NAME, STR_ADD, STR_DATE)"
					+ " VALUES ((select nvl(max(STR_NO), 0) + 1 from STORE), ?,  ?, ?, ?)";
		
		List<Object> param = new ArrayList<>();

		param.add(p.get("STR_CAL"));
		param.add(p.get("STR_NAME"));
		param.add(p.get("STR_ADD"));
		param.add(p.get("STR_DATE"));
		
		return jdbc.update(sql,param);
	}
	
	public List<Map<String, Object>> selectstoreadd() {
		String sql = "select * from STORE WHERE STR_ADD LIKE '%"+OrderService.STRADD+"%'";
		
		return jdbc.selectList(sql);
	}
	
	
	public List<Map<String, Object>> selectprodlist() {//상품조회
		String sql = "select * from PRODUCT";
		
		return jdbc.selectList(sql);
	}

	public Map<String, Object> storesalse(){//총 매출
		String sql = "SELECT PRICE FROM (SELECT sum(price) PRICE FROM PRODUCT PD, PR_ORD PO where PD.PDT_NAME = PO.PDT_NAME AND PO.STR_NO = "+StoreService.strno+")";
	
		return jdbc.selectOne(sql);

	}
	
	
	public Map<String, Object> storedateprice(){//날짜별 매출
		String sql = 	"SELECT PRICE FROM ("
						+"SELECT SUM(PRICE) PRICE"
						+" FROM PRODUCT PD, PR_ORD PO , ORD OD"
						+" where PD.PDT_NAME = PO.PDT_NAME "
						+" AND PO.STR_NO = "+StoreService.strno+""
						+" AND PO.ORD_NO = OD.ORD_NO"
						+" AND OD.ORD_DATE = TO_DATE('"+StoreService.pricedate+"','YYYYMMDD'))";

		return jdbc.selectOne(sql);

	}
	public Map<String, Object> storemonthprice(){//날짜별 매출
		String sql = 	"SELECT PRICE FROM ("
						+"SELECT SUM(PRICE) PRICE"
						+" FROM PRODUCT PD, PR_ORD PO , ORD OD"
						+" where PD.PDT_NAME = PO.PDT_NAME "
						+" AND PO.STR_NO = "+StoreService.strno+""
						+" AND PO.ORD_NO = OD.ORD_NO"
						+" AND OD.ORD_DATE >= TO_DATE('"+StoreService.pricedate+"01','YYYYMMDD')"
						+" AND OD.ORD_DATE <= TO_DATE('"+StoreService.pricedate+"31','YYYYMMDD'))";

		return jdbc.selectOne(sql);

	}
	public Map<String, Object> storeyearprice(){//날짜별 매출
		String sql = 	"SELECT PRICE FROM ("
						+"SELECT SUM(PRICE) PRICE"
						+" FROM PRODUCT PD, PR_ORD PO , ORD OD"
						+" where PD.PDT_NAME = PO.PDT_NAME "
						+" AND PO.STR_NO = "+StoreService.strno+""
						+" AND PO.ORD_NO = OD.ORD_NO"
						+" AND OD.ORD_DATE >= TO_DATE('"+StoreService.pricedate+"0101','YYYYMMDD')"
						+" AND OD.ORD_DATE <= TO_DATE('"+StoreService.pricedate+"1231','YYYYMMDD'))";

		return jdbc.selectOne(sql);

	}
	public Map<String, Object> storename(){
		String sql = 	"SELECT STR_NAME FROM STORE WHERE STR_NO = "+StoreService.strno+"";

		return jdbc.selectOne(sql);

	}
	
}

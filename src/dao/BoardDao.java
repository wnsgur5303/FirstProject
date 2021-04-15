package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.BoardService;
import util.JDBCUtil;

public class BoardDao {
	private BoardDao(){}
	private static BoardDao instance;
	public static BoardDao getInstance(){
		if(instance == null){
			instance = new BoardDao();
		}
		return instance;
	}
		
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectBoardList(){//공지사항 탐색
		String sql = "SELECT POST_NO, POST_NAME, POST_CONTENT, POST_DATE "
					+ "FROM NOTICE "
					+ "ORDER BY POST_NO DESC";
		
		return jdbc.selectList(sql);
	}
	
	public int deleteNOTICE(Map<String, Object> p){//공지글 삭제
		String sql = "DELETE NOTICE WHERE POST_NO = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("POST_NO"));

		return jdbc.update(sql,param);
	}
	
	public int insertNOTICE(Map<String, Object> p){//공지글 입력
		String sql = "INSERT INTO NOTICE VALUES ((select nvl(max(POST_NO), 0) + 1 from NOTICE), ?,?, ?, (SELECT TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/DD')) FROM dual))";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("ID"));
		param.add(p.get("POST_NAME"));
		param.add(p.get("POST_CONTENT"));
		
		return jdbc.update(sql,param);
	}
	
	public Map<String, Object> SelectNOTICE(int post){
		String sql = "select * from NOTICE WHERE POST_NO = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(post);
		
		return jdbc.selectOne(sql,param);
	}
	
	
	public int updateNOTICE(Map<String, Object> p){//공지글 수정
		String sql = "UPDATE NOTICE SET POST_NAME = ? , POST_CONTENT = ? WHERE POST_NO = ("+BoardService.postno+")";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("POST_NAME"));
		param.add(p.get("POST_CONTENT"));
		
		return jdbc.update(sql,param);		
	}
	

	
}

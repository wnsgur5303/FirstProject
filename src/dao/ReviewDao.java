package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import service.BoardService;
import service.ReviewService;
import util.JDBCUtil;

public class ReviewDao {

	private ReviewDao(){}
	private static ReviewDao instance;
	public static ReviewDao getInstance(){
		if(instance == null){
			instance = new ReviewDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public List<Map<String, Object>> selectReviewList() {
		String sql = "SELECT POST_NO,  POST_NAME, POST_CONTENT, POST_DATE, SCORE, STORE.STR_NAME "
					+ "FROM POST, STORE "
					+ "WHERE POST.STR_NO = STORE.STR_NO "
					+ "ORDER BY POST_NO DESC";

		return jdbc.selectList(sql);
	}

	public Map<String, Object> SelectReview(int post) {
		String sql = "SELECT POST_NO, ID, POST_NAME, POST_CONTENT, POST_DATE, SCORE, STORE.STR_NAME "
					+ "FROM POST, STORE "
					+ "WHERE POST.STR_NO = STORE.STR_NO "
					+ "AND POST_NO = ? "
					+ "ORDER BY POST_NO DESC";
		List<Object> param = new ArrayList<>();
		param.add(post);
	
		return jdbc.selectOne(sql,param);
	}

	public int deleteReview(Map<String, Object> p) {
		String sql = "DELETE POST WHERE POST_NO = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("POST_NO"));

		return jdbc.update(sql,param);
	}

	public int insertReview(Map<String, Object> p) {
		String sql = "INSERT INTO POST(POST_NO, ID,POST_NAME, POST_CONTENT, POST_DATE, SCORE, STR_NO) "
				+ "VALUES ((select nvl(max(POST_NO), 0) + 1 from POST),'"+Controller.id+"', ?, ?, (SELECT TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/DD')) FROM dual), ?, ?)";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("POST_NAME"));
		param.add(p.get("POST_CONTENT"));
		param.add(p.get("SCORE"));
		param.add(p.get("STR_NO"));
		
		return jdbc.update(sql,param);
	}

	public List<Map<String, Object>> selectPostNo() {
		String sql = "select ID from MEMBER WHERE ID = '"+Controller.id+"'";
		
		return jdbc.selectList(sql);
	}

	public int updateReview(Map<String, Object> p) {
		String sql = "UPDATE POST SET POST_NAME = ? , POST_CONTENT = ?, SCORE = ? WHERE POST_NO = ("+ReviewService.postno+")";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("POST_NAME"));
		param.add(p.get("POST_CONTENT"));
		param.add(p.get("SCORE"));
		
		return jdbc.update(sql,param);
	}

	public Map<String, Object> SelectComment(int post) {
		String sql = "select * from COM WHERE POST_NO = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(post);
		
		return jdbc.selectOne(sql,param);
	}

	public int insertComment(Map<String, Object> p) {
		String sql = "INSERT INTO COM VALUES ((select nvl(max(COM_NO), 0) + 1 from COM), '"+ReviewService.post+"','"+Controller.id+"', ?, (SELECT TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/DD')) FROM dual))";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("COM"));
		
		return jdbc.update(sql,param);
	}

	public int deleteComment(Map<String, Object> p) {
		String sql = "DELETE COM WHERE POST_NO = ?";

		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("POST_NO"));

		return jdbc.update(sql,param);
	}

	public int updateComment(Map<String, Object> p) {
		String sql = "UPDATE COM SET COM = ? WHERE POST_NO = ("+ReviewService.postno+")";
		
		List<Object> param = new ArrayList<>();//list 타입의 param 을 만들고 넣어줌
		param.add(p.get("COM"));
		
		return jdbc.update(sql,param);
	}



	public Map<String, Object> selectpostno(String id) {
		String sql = "select POST_NO from POST WHERE ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(id);
	
		return jdbc.selectOne(sql,param);
	}

	public Map<String, Object> SelectCommentfordel(int postno) {
		String sql = "select * from COM WHERE POST_NO = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(postno);
		
		return jdbc.selectOne(sql,param);
	}

	
}

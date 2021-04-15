package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import service.BoardService;
import service.StoreService;
import util.JDBCUtil;

public class UserDao {

	private UserDao(){}
	private static UserDao instance;
	public static UserDao getInstance() {
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public int insertUser(Map<String, Object> p) {//회원가입
		String sql = "INSERT INTO MEMBER(MEM_NAME, ID, PASSWORD, MEM_ADD, MEM_CAL, MEM_STA) "
					+ "VALUES (?, ?, ?, ?, ?, 1)";
		
		List<Object> param = new ArrayList<>();
		param.add(p.get("MEM_NAME"));
		param.add(p.get("ID"));
		param.add(p.get("PASSWORD"));
		param.add(p.get("MEM_ADD"));
		param.add(p.get("MEM_CAL"));
		
		return jdbc.update(sql, param);
	}

	public Map<String, Object> selectUser(String userId, String password) {//로그인
		String sql = "SELECT ID, MEM_STA "
					+ "FROM MEMBER "
					+ "WHERE ID = ? "
					+ "AND PASSWORD = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	
	public List<Map<String, Object>> selectNAMEList(){//이름찾기
		String sql = "select MEM_NAME from MEMBER WHERE ID = '"+Controller.id+"'";
		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectIDList(){//회원번호
		String sql = "select ID from MEMBER WHERE ID = '"+Controller.id+"'";
		
		return jdbc.selectList(sql);
	}
	
	
	public Map<String, Object> selectID(String userId){//아이디 조회
		String sql = "SELECT * from MEMBER WHERE ID = ?";
		
		List<Object> params = new ArrayList<>();
		params.add(userId);
	
		return jdbc.selectOne(sql,params);
	}
	
	
	public List<Map<String, Object>> selectmemlist() {
		String sql = "select * from MEMBER";
		
		return jdbc.selectList(sql);
	}

	
	public int deletemem(String userid) {
		String sql = "UPDATE MEMBER SET MEM_STA = 0 WHERE ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(userid);	
		
		return jdbc.update(sql, param);
	}


	public Map<String, Object> selectmem(String id) {
		String sql = "select * from MEMBER WHERE ID = '"+Controller.id+"'";
		
		return jdbc.selectOne(sql);
	}
	
//	public int deletememdata() {
//		String sql = "DELETE MEMBER WHERE ID = '"+Controller.id+"'";	
//		
//		return jdbc.update(sql);
//	}


	public int updatememdata(Map<String, Object> p) {
		String sql = "UPDATE MEMBER SET MEM_NAME = ? , MEM_CAL = ?, MEM_ADD = ? WHERE ID = '"+Controller.id+"'";
		
		List<Object> param = new ArrayList<>();
		param.add(p.get("MEM_NAME"));
		param.add(p.get("MEM_CAL"));
		param.add(p.get("MEM_ADD"));
		
		return jdbc.update(sql,param);
	}

	public int offmemdata(String id) {
		String sql = "UPDATE MEMBER SET MEM_STA = 0 WHERE ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(id);	
		
		return jdbc.update(sql, param);
	}

	public int onmemdata(String id) {
		String sql = "UPDATE MEMBER SET MEM_STA = 1 WHERE ID = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(id);	
		
		return jdbc.update(sql, param);
		
	}


}

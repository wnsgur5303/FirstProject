package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.ReviewDao;
import dao.UserDao;

public class UserService {

	private UserService(){}
	private static UserService instance;
	public static UserService getInstance() {
		if(instance == null){
			instance = new UserService();
		}
		return instance;
	}
	public static Object name;
	public static Object contact;
	public static Object house;
	public static int postno = 0;
	public static String memid = null;
	private UserDao userDao = UserDao.getInstance();
	private ReviewDao reviewDao = ReviewDao.getInstance();

	
	public int join(){
		String userId;//아이디

		String password;//비밀번호

		String userName;//이름

		String address;//주소

		String call;//전화번호
	
		System.out.println("=============== 회원가입 ===============");
		String expID = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
		String exppass = "^[a-zA-Z0-9.-]{2,11}$";
		String add1_pattern = "([가-힣]{0,}[시|도]{1})\\s([가-힣]{0,}[군|구]{1})\\s([가-힣]{0,}[면|동]{1})";
		String ph_pattern = "[0-9]{3}-[0-9]{4}-[0-9]{4}";
		
		System.out.println("아이디>");
		userId = ScanUtil.nextLine();
		if(userId.matches(expID)){	
		}else{
			System.out.println("잘못 입력된 아이디 입니다.");
			System.out.println("아이디는 영문으로 시작하며 대소문자를 구분합니다, '_'를 제외한 특수문자 사용 불가, 영문과 숫자의 조합 5 ~ 12자");
			return View.JOIN;
		}
			
		Map<String, Object> selectID = userDao.selectID(userId);
		if(selectID == null){
			System.out.println("중복되지 않은 아이디");
		}else{
			System.out.println("중복된 아이디 입니다.");
			return View.JOIN;	
		}
		System.out.println("비밀번호>");
		pass:while(true){
		password = ScanUtil.nextLine();
			if(password.matches(exppass)){
				break pass;
			}else{
				System.out.println("비밀번호는 2자 이상 11자 이하로 작성해야 합니다.");
				System.out.println("비밀번호>");
			}
		}
		System.out.println("이름>");
		userName = ScanUtil.nextLine();
		System.out.println("주소(ex.대전시 동구 가오동) >");
		add:while(true){
		address = ScanUtil.nextLine();
			if(address.matches(add1_pattern)){
				break add;
			}else{
				System.out.println("올바른 주소가 아닙니다. (시|도   군|구   면|동  띄어쓰기로 구분하여 양식에 맞춰 작성.) ");
				System.out.println("주소(ex.대전시 동구 가오동) >");
			}
		}
		System.out.println("연락처(ex.xxx-xxxx-xxxx) >");
		ph:while(true){
			call = ScanUtil.nextLine();
			if(call.matches(ph_pattern)){
				break ph;
			}else{
				System.out.println("올바른 연락처가 아닙니다 ('-'포함하여 작성))");
				System.out.println("연락처(ex.xxx-xxxx-xxxx) > ");
			}
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("MEM_NAME", userName);
		param.put("ID", userId);
		param.put("PASSWORD", password);
		param.put("MEM_ADD", address);
		param.put("MEM_CAL", call);
		
		int result = userDao.insertUser(param);
		
		if(0 < result){
			System.out.println("★★★회원가입 성공★★★");
		}else{
			System.out.println("★★★회원가입 실패★★★");		
		}
		
		return View.HOME;
		
	}
	public int login() {
		System.out.println("=============== 로그인 ===============");
		System.out.println("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호>");
		String password = ScanUtil.nextLine();
		Map<String, Object> user = userDao.selectUser(userId, password);
		
		if(user == null){
			System.out.println("★★★아이디 혹은 비밀번호를 잘못 입력하셨습니다.★★★");
			System.out.println();
		}else{
			int memsta = Integer.parseInt(String.valueOf(user.get("MEM_STA")));
			if(memsta == 0){
				System.out.println("!!!!!!비활성화 된 아이디 입니다!!!!!!");
				System.out.println("===================================");
				System.out.println("1.돌아가기\t2.비활성화 해제");
				System.out.print("입력>");

				int input = ScanUtil.nextInt();

				switch (input) {
				case 1:
					return View.HOME;
				case 2:
					break;
				}
			}
			System.out.println("★★★로그인 성공★★★");
			
			Controller.LoginUser = user;
			memid = userId;
			Controller.id = userId;
			userDao.onmemdata(Controller.id);
			
			if(userId.equals("admin")){
				 System.out.print("관리자 시스템 접속");
				  for (int i = 0; i < 30; i++) {
				   System.out.print("▶");
				   try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				  }
				  System.out.println();
				return View.ADMINMAIN;	
			}else{
				 System.out.print("회원 시스템 접속");
				  for (int i = 0; i < 30; i++) {
				   System.out.print("▶");
				   try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				  }
				  System.out.println();
				return View.USERMAIN;
			}			
		}
		return View.HOME;
	}
	
	public int mem_list() {
		
		while(true){

			List<Map<String, Object>> memlist = userDao.selectmemlist();

			System.out.println("======================================================");
			System.out.println("회원이름\t아이디\t주소\t\t연락처                \t활성상태");
			System.out.println("======================================================");
			for(int i = 0; i < memlist.size(); i++){

				System.out.print(memlist.get(i).get("MEM_NAME") + "\t");
				System.out.print(memlist.get(i).get("ID") + "\t");
				System.out.print(memlist.get(i).get("MEM_ADD") + "\t");
				System.out.print(memlist.get(i).get("MEM_CAL") + "\t");
				System.out.print(memlist.get(i).get("MEM_STA") + "\t");
				System.out.println();
				
			}
			System.out.println("======================================================");
			System.out.println("1.회원 비활성화\t2.돌아가기");
			System.out.print("입력>");

			int input = ScanUtil.nextInt();

			switch (input) {
			case 1:
				return View.MEM_DELETE;
			case 2:
				return View.ADMINMAIN;
			}
			return View.MEM_LIST;
			}
	}


	public int mem_delete() {
		System.out.println("비활성화할 회원 아이디>");
		String userid = ScanUtil.nextLine();
		userDao.deletemem(userid);
		if(userid.equals("admin")){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!error: 관리자 계정은 선택할 수 없습니다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return View.MEM_LIST;			
					
	}
	public int user_profile() {
		while(true){
		
		Map<String, Object> mem = userDao.selectmem(Controller.id);
		
		Object MEM_NAME = mem.get("MEM_NAME");
		Object ID = mem.get("ID");
		Object MEM_ADD = mem.get("MEM_ADD");
		Object MEM_CAL = mem.get("MEM_CAL");
		
		System.out.println("=============================================");
		System.out.println("이름 :\t"+MEM_NAME+"\t");
		System.out.println("아이디 :\t"+ID+"\t");
		System.out.println("주소 :\t"+MEM_ADD+"\t");
		System.out.println("연락처 :\t"+MEM_CAL+"\t");
		System.out.println("=============================================");
		System.out.println();
		System.out.println("1.목록으로  2.계정 비활성화  3.수정");
		int input = ScanUtil.nextInt();
		switch (input) {
			case 1:
				return View.USERMAIN;
			case 2:
				return View.USER_MEM_DELETE;
			case 3:
				name = MEM_NAME;
				contact = MEM_CAL;
				house = MEM_ADD;
				return View.USER_MEM_UPDATE;
		}
		return View.USER_PROFILE;
		}
	}
	public int user_mem_delete() {
		userDao.offmemdata(Controller.id);
//		Map<String, Object> user = reviewDao.selectpostno(Controller.id);
//		if(user == null){
//			 userDao.deletememdata();
//		}else{
//			postno = Integer.parseInt(String.valueOf(user.get("POST_NO")));
//			System.out.println("=============================================");
//			Map<String, Object> comment = reviewDao.SelectCommentfordel(postno);
//			if(comment != null){
//				Map<String, Object> param = new HashMap<>();
//				param.put("POST_NO", postno);
//				
//				reviewDao.deleteComment(param);
//				reviewDao.deleteReview(param);
//				
//				userDao.deletememdata();
//				System.out.println("탈퇴 완료, 이용해주셔서 감사합니다.");
//				return View.HOME;		
//			}else{
//				Map<String, Object> param = new HashMap<>();
//				param.put("POST_NO", postno);
//				
//				reviewDao.deleteReview(param);
//				
//				userDao.deletememdata();
//				System.out.println("탈퇴 완료, 이용해주셔서 감사합니다.");
//				return View.HOME;
//			}
//		}
		System.out.println("비활성화 완료, 이용해주셔서 감사합니다.");
		return View.HOME;
	}
	public int user_mem_update() {
		String contact2;
		String house2;
	
		System.out.println("========== 프로필 수정 ==========");
		String add1_pattern = "([가-힣]{0,}[시|도]{1})\\s([가-힣]{0,}[군|구]{1})\\s([가-힣]{0,}[면|동]{1})";
		String ph_pattern = "[0-9]{3}-[0-9]{4}-[0-9]{4}";
		
		System.out.println("이름>\n"+name+"\n");
		String name2 = ScanUtil.nextLine();
		System.out.print("이전 연락처>\n"+contact+"\n");
		System.out.println("연락처> //(ex.xxx-xxxx-xxxx)");
		ph:while(true){
			contact2 = ScanUtil.nextLine();
			if(contact2.matches(ph_pattern)){
				break ph;
			}else{
				System.out.println("올바른 연락처가 아닙니다 ('-'포함 하여 작성))");
				System.out.println("연락처> //(ex.xxx-xxxx-xxxx)");
			}
		}
		System.out.print("이전 주소>\n"+house+"\n");
		System.out.println("주소>  //(ex.대전시 동구 가오동)");
		add:while(true){
		house2 = ScanUtil.nextLine();
			if(house2.matches(add1_pattern)){
				break add;
			}else{
				System.out.println("올바른 주소가 아닙니다. (시|도, 군|구, 면|동  띄어쓰기 양식에 맞춰 바르게 작성.) ");
				System.out.println("주소>  //(ex.대전시 동구 가오동)");
			}
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("MEM_NAME", name2);
		param.put("MEM_CAL", contact2);
		param.put("MEM_ADD", house2);
		
		userDao.updatememdata(param);
		
		
		return View.USER_PROFILE;
	}

	
}

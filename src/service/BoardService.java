package service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.BoardDao;
import dao.UserDao;

public class BoardService {
	
	private BoardService(){}
	private static BoardService instance;
	public static BoardService getInstance() {
		if(instance == null){
			instance = new BoardService();
		}
		return instance;
	}
	
	public static Object postno;
	public static Object name;
	public static Object content;
	static SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
	
	private static BoardDao boardDao = BoardDao.getInstance();
	private static UserDao userDao = dao.UserDao.getInstance();

	public static int board_list(){
		//조회. dao.
		
		while(true){

		List<Map<String, Object>> boardList = boardDao.selectBoardList();

		System.out.println("=============================================");
		System.out.println("번호\t제목                                       \t작성일");
		System.out.println("=============================================");
		for(int i = 0; i < boardList.size(); i++){
			System.out.print(boardList.get(i).get("POST_NO") + "\t");
			System.out.print(boardList.get(i).get("POST_NAME") + "   \t");
			System.out.print(sDate.format(boardList.get(i).get("POST_DATE")));
			System.out.println();
		}
		System.out.println("=============================================");
		System.out.println("1.조회\t2.등록\t3.돌아가기");
		System.out.print("입력>");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.BOARD_VIEW;
		case 2:
			return View.BOARD_INSERT_FORM;
		case 3:
			return View.ADMINMAIN;
		}
		return View.BOARD_LIST;
		}
	}
	public int board_view(){
		
		System.out.println("조회할 게시판 번호");
		int post = util.ScanUtil.nextInt();
		
		Map<String, Object> NOTICE = boardDao.SelectNOTICE(post);
		
		Object POST_NO = NOTICE.get("POST_NO");
		Object ID = NOTICE.get("ID");
		Object POST_NAME = NOTICE.get("POST_NAME");
		Object POST_CONTENT = NOTICE.get("POST_CONTENT");
		Object POST_DATE = NOTICE.get("POST_DATE");
		
		System.out.println("=============================================");
		System.out.println("번호 :\t"+POST_NO+"\t");
		System.out.println("제목 :\t"+POST_NAME+"\t");
		System.out.println("작성자  :\t"+ID+"\t");
		System.out.println("작성일 :\t"+sDate.format(POST_DATE));
		System.out.print("내용 :\t");
		
		System.out.print(POST_CONTENT+"\t");
		System.out.println();
		
		System.out.println("=============================================");
		System.out.println("1.목록으로 2.삭제  3.수정");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.BOARD_LIST;	
		case 2:
			postno = POST_NO;
			return View.BOARD_DELETE;

		case 3:
			name=POST_NAME;
			content=POST_CONTENT;
			postno = POST_NO;
			return View.UPDATE_NOTICE;
		}
		return View.BOARD_LIST;

	}

		
		
		public int board_insert(){
			System.out.println("========== 게시글작성 ==========");
			System.out.print("제목>");
			String postname = ScanUtil.nextLine();
			System.out.print("내용>");
			String content = ScanUtil.nextLine();
						
			Map<String, Object> param = new HashMap<>();
			param.put("ID", Controller.id);
			param.put("POST_NAME", postname);
			param.put("POST_CONTENT", content);

			int result = boardDao.insertNOTICE(param);
			
			if(0 < result){
				System.out.println("게시글 작성 성공");
				return View.BOARD_LIST;
			}else{
				System.out.println("게시글 작성 실패");
				return View.BOARD_INSERT_FORM;
				}
			}
		

		public int board_delete(){
	
			Map<String, Object> param = new HashMap<>();
			param.put("POST_NO", postno);

			boardDao.deleteNOTICE(param);
			
			return View.BOARD_LIST;		
	}
		
		public int board_update(){
			System.out.println("========== 게시글수정 ==========");
			
			System.out.print("제목>\n"+name+"\n");
			String name2 = ScanUtil.nextLine();
			System.out.print("내용>\n"+content+"\n");
			String content2 = ScanUtil.nextLine();

			Map<String, Object> param = new HashMap<>();
			param.put("POST_NAME", name2);
			param.put("POST_CONTENT", content2);
			
			boardDao.updateNOTICE(param);
	
			return View.BOARD_LIST;
			}
		
	
		public int user_board_list() {
				
			while(true){

				List<Map<String, Object>> boardList = boardDao.selectBoardList();

				System.out.println("=============================================");
				System.out.println("번호\t제목                                       \t작성일");
				System.out.println("=============================================");
				for(int i = 0; i < boardList.size(); i++){
					System.out.print(boardList.get(i).get("POST_NO") + "\t");
					System.out.print(boardList.get(i).get("POST_NAME") + "   \t");
					System.out.print(sDate.format(boardList.get(i).get("POST_DATE")));
					System.out.println();
				}
			System.out.println("=============================================");
			System.out.println("1.조회\t2.돌아가기");
			System.out.print("입력>");

			int input = ScanUtil.nextInt();

			switch (input) {
			case 1:
				return View.USER_BOARD_VIEW;
			case 2:
				return View.USERMAIN;
			}
			
			}
		}
		public int user_board_view() {
			
			System.out.println("조회할 게시판 번호");
			int post = util.ScanUtil.nextInt();
			
			Map<String, Object> NOTICE = boardDao.SelectNOTICE(post);
			
			Object POST_NO = NOTICE.get("POST_NO");
			Object POST_NAME = NOTICE.get("POST_NAME");
			Object POST_CONTENT = NOTICE.get("POST_CONTENT");
			Object POST_DATE = NOTICE.get("POST_DATE");
			
			System.out.println("=============================================");
			System.out.println("번호 :\t"+POST_NO+"\t");
			System.out.println("제목 :\t"+POST_NAME+"\t");
			System.out.println("작성일 :\t"+sDate.format(POST_DATE));
			System.out.print("내용 :\t");
			
			System.out.print(POST_CONTENT+"\t");
			System.out.println();
			
			System.out.println("=============================================");
			System.out.println("1.목록으로 ");
			System.out.print("입력>");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
			return View.USER_BOARD_LIST;
			}
			return View.USER_BOARD_VIEW;
		
		}
		
	}

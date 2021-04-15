package service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.ReviewDao;
import dao.StoreDao;

public class ReviewService {

	private ReviewService(){}
	private static ReviewService instance;
	public static ReviewService getInstance() {
		if(instance == null){
			instance = new ReviewService();
		}
		return instance;
	}
	public static int post;
	public static Object name;
	public static Object content;
	public static Object postno;
	static SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
	private static ReviewDao reviewDao = ReviewDao.getInstance();
	private static StoreDao storeDao = StoreDao.getInstance();
	
	
	public int review_list() {
		
		while(true){

		List<Map<String, Object>> reviewList = reviewDao.selectReviewList();

		System.out.println("=============================================");
		System.out.println("번호\t제목\t\t\t작성일");
		System.out.println("=============================================");
		for(int i = 0; i < reviewList.size(); i++){
			System.out.print(reviewList.get(i).get("POST_NO") + "\t");
			System.out.print(reviewList.get(i).get("POST_NAME") + "\t");
			System.out.print("\t"+sDate.format(reviewList.get(i).get("POST_DATE")));
			System.out.println();
		}
		System.out.println("=============================================");
		System.out.println("1.조회\t2.등록\t3.돌아가기");
		System.out.println("입력>");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.REVIEW_VIEW;
		case 2:
			return View.REVIEW_INSERT_FORM;
		case 3:
			return View.USERMAIN;
		}
		return View.REVIEW_LIST;
		}
	}


	public int review_view() {
		System.out.println("조회할 리뷰 번호");
		post = util.ScanUtil.nextInt();
		
		Map<String, Object> review = reviewDao.SelectReview(post);
		
		Object POST_NO = review.get("POST_NO");
		Object ID = review.get("ID");
		Object POST_NAME = review.get("POST_NAME");
		Object POST_CONTENT = review.get("POST_CONTENT");
		Object POST_DATE = review.get("POST_DATE");
		Object SCORE = review.get("SCORE");
		Object STR_NAME = review.get("STR_NAME");
		
		System.out.println("=============================================");
		System.out.println("번호 :\t"+POST_NO+"\t");
		System.out.println("방문매장 :\t"+STR_NAME+"\t");		
		System.out.println("제목 :\t"+POST_NAME+"\t");
		System.out.println("작성일 :\t"+sDate.format(POST_DATE));
		System.out.println("작성자 :\t"+ID+"\t");	
		System.out.println("평점 :\t"+SCORE+"\t");
		System.out.print("내용 :\t");
		
		System.out.print(POST_CONTENT+"\t");
		System.out.println();
		
		System.out.println("=============================================");
		Map<String, Object> comment = reviewDao.SelectComment(post);
		if(comment != null){
		
			System.out.println("남겨주신 리뷰에 대한 댓글이 있습니다.");
		Object COM_NO = comment.get("COM_NO");
		Object COM_POST_NO = comment.get("POST_NO");
		Object COM_ID = comment.get("ID");
		Object COM = comment.get("COM");
		Object COM_DATE = comment.get("COM_DATE");
		
		System.out.println("댓글 번호 :\t"+COM_NO+"\t");
		System.out.println("작성자 :\t"+COM_ID+"\t");
		System.out.println("작성일 :\t"+sDate.format(COM_DATE));
		System.out.print("내용 :\t");
		
		System.out.print(COM+"\t");
		System.out.println();
		
		System.out.println("=============================================");
		System.out.println("1.목록으로 2.삭제  3.수정");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.REVIEW_LIST;
		
		case 2:
			postno = POST_NO;
			if(ID.equals(Controller.id)){
				return View.USER_REVIEW_COM_DELETE;
				
			}else{
				System.out.println("본인이 작성한 글만 삭제 할 수 있습니다.");
				return View.REVIEW_LIST;
			}
			
		case 3:
			name=POST_NAME;
			content=POST_CONTENT;
			postno = POST_NO;
			return View.REVIEW_UPDATE;
		}
		return View.REVIEW_LIST;
		
		}else{
			System.out.println("등록된 댓글이 없습니다.");
			System.out.println("=============================================");
			System.out.println("1.목록으로 2.삭제  3.수정");
			System.out.println("입력>");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				return View.REVIEW_LIST;
				
			case 2:
				postno = POST_NO;
				if(ID.equals(Controller.id)){
					return View.REVIEW_DELETE;				
				}else{
					System.out.println("본인이 작성한 글만 삭제 할 수 있습니다.");
					return View.REVIEW_LIST;
				}
				
			case 3:
				name=POST_NAME;
				content=POST_CONTENT;
				postno = POST_NO;
				if(ID.equals(Controller.id)){
					return View.REVIEW_UPDATE;
				}else{
					System.out.println("본인이 작성한 글만 수정 할 수 있습니다.");
					return View.REVIEW_LIST;
				}
			}	
			return View.REVIEW_LIST;
			
		}
	
	}


	public int review_insert() {
		String score;
		String score_pattern = "[0-5]{1}";
		System.out.println("========== 리뷰작성 ==========");

		List<Map<String, Object>> store_list = storeDao.selectstorelist();

		System.out.println("=========================================================");
		System.out.println("매장번호\t매장연락처\t\t매장이름\t\t  매장주소\t운영시간");
		System.out.println("=========================================================");
		for(int i = 0; i < store_list.size(); i++){
			System.out.print(store_list.get(i).get("STR_NO") + "\t");
			System.out.print(store_list.get(i).get("STR_CAL") + "\t");
			System.out.print(store_list.get(i).get("STR_NAME") + "         \t");
			System.out.print(store_list.get(i).get("STR_ADD")+ "\t");
			System.out.print(store_list.get(i).get("STR_DATE") + "\t");
			System.out.println();
		}
		System.out.println("=========================================================");

		System.out.println("방문한 매장번호를 입력해 주세요>");
		int num = ScanUtil.nextInt();
		System.out.println("제목>");
		String title = ScanUtil.nextLine();
		System.out.println("내용>");
		String content = ScanUtil.nextLine();
		System.out.println("평점(5점 만점) >");
		howscore:while(true){
			score = ScanUtil.nextLine();
			if(score.matches(score_pattern)){
				break howscore;
			}else{
				System.out.println("0~5 사이의 정수를 입력해주세요");
				System.out.println("평점(5점 만점)>");
			}
		}	
		
		Map<String, Object> param = new HashMap<>();
		param.put("POST_NAME", title);
		param.put("POST_CONTENT", content);
		param.put("SCORE", score);
		param.put("STR_NO", num);

		int result = reviewDao.insertReview(param);
		
		if(0 < result){
			System.out.println("리뷰 작성 성공");
			return View.REVIEW_LIST;
		}else{
			System.out.println("리뷰 작성 실패");
			return View.REVIEW_INSERT_FORM;
			}
	}


	public int review_delete() {
		Map<String, Object> param = new HashMap<>();
		
		param.put("POST_NO", postno);

		reviewDao.deleteReview(param);
		
		return View.REVIEW_LIST;		
	}


	public int review_update() {
		String score;
		String score_pattern = "[0-5]{1}";
		System.out.println("========== 리뷰 수정 ==========");
		System.out.print("제목>\n"+name+"\n");
		String title = ScanUtil.nextLine();
		System.out.print("내용>\n"+content+"\n");
		String content = ScanUtil.nextLine();
		System.out.print("평점 (5점 만점) >");
		howscore:while(true){
			score = ScanUtil.nextLine();
			if(score.matches(score_pattern)){
				break howscore;
			}else{
				System.out.println("0~5 사이의 정수를 입력해주세요");
				System.out.println("평점 (5점 만점)>");
			}
		}	
		
		
		Map<String, Object> param = new HashMap<>();
		param.put("POST_NAME", title);
		param.put("POST_CONTENT", content);
		param.put("SCORE", score);
		reviewDao.updateReview(param);
		
		
		return View.REVIEW_LIST;
	}


	public int admin_review_list() {
		while(true){

			List<Map<String, Object>> reviewList = reviewDao.selectReviewList();

			System.out.println("=============================================");
			System.out.println("번호\t제목\t\t\t작성일");
			System.out.println("=============================================");
			for(int i = 0; i < reviewList.size(); i++){
				System.out.print(reviewList.get(i).get("POST_NO") + "\t");
				System.out.print(reviewList.get(i).get("POST_NAME") + "\t");
				System.out.print("\t"+sDate.format(reviewList.get(i).get("POST_DATE")));
				System.out.println();
			}
			System.out.println("=============================================");
			System.out.println("1.조회\t2.돌아가기");
			System.out.println("입력>");

			int input = ScanUtil.nextInt();

			switch (input) {
			case 1:
				return View.ADMIN_REVIEW_VIEW;
			case 2:
				return View.ADMINMAIN;
			}
			return View.ADMIN_REVIEW_LIST;
			}
	}


	public int admin_review_view() {
		
		System.out.println("조회할 리뷰 번호");
		post = util.ScanUtil.nextInt();
		
		Map<String, Object> review = reviewDao.SelectReview(post);
		
		Object POST_NO = review.get("POST_NO");
		Object ID = review.get("ID");
		Object POST_NAME = review.get("POST_NAME");
		Object POST_CONTENT = review.get("POST_CONTENT");
		Object POST_DATE = review.get("POST_DATE");
		Object SCORE = review.get("SCORE");
		
		System.out.println("=============================================");
		System.out.println("번호 :\t"+POST_NO+"\t");
		System.out.println("제목 :\t"+POST_NAME+"\t");
		System.out.println("작성자 :\t"+ID+"\t");
		System.out.println("평점 :\t"+SCORE+"\t");
		System.out.println("작성일 :\t"+sDate.format(POST_DATE));
		System.out.print("내용 :\t");
		
		System.out.print(POST_CONTENT+"\t");
		System.out.println();
		
		System.out.println("=============================================");
		Map<String, Object> comment = reviewDao.SelectComment(post);
		
		if(comment != null){
		
			System.out.println("남겨주신 리뷰에 대한 댓글이 있습니다.");
		Object COM_NO = comment.get("COM_NO");
		Object COM_POST_NO = comment.get("POST_NO");
		Object COM_ID = comment.get("ID");
		Object COM = comment.get("COM");
		Object COM_DATE = comment.get("COM_DATE");
		
		System.out.println("댓글 번호 :\t"+COM_NO+"\t");
		System.out.println("작성자 :\t"+COM_ID+"\t");
		System.out.println("작성일 :\t"+sDate.format(COM_DATE));
		System.out.print("내용 :\t");
		
		System.out.print(COM+"\t");
		System.out.println();
		
		System.out.println("=============================================");
		System.out.println("1.목록으로 2.댓글삭제 3.댓글수정");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.ADMIN_REVIEW_LIST;
		
		case 2:
			postno = POST_NO;
			return View.COMMENT_DELETE;
			
		case 3:	
			postno = POST_NO;
			content=COM;
			return View.COMMENT_UPDATE;
		}
		return View.ADMIN_REVIEW_LIST;
		
		} else{
			System.out.println("등록된 댓글이 없습니다.");
			System.out.println("=============================================");
			System.out.println("1.목록으로 2.댓글등록 ");
			System.out.println("입력>");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1:
				return View.ADMIN_REVIEW_LIST;
			
			case 2:
				return View.COMMENT_INSERT;			
			}	
			
			return View.ADMIN_REVIEW_VIEW;
		}
		
	}


	public int comment_insert() {
		System.out.println("========== 댓글작성 ==========");
		System.out.print("내용>");
		String content = ScanUtil.nextLine();
		
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("COM", content);
		
		

		int result = reviewDao.insertComment(param);
		
		if(0 < result){
			System.out.println("댓글 작성 성공");
			return View.ADMIN_REVIEW_LIST;
		}else{
			System.out.println("댓글 작성 실패");
			return View.COMMENT_INSERT;
			}
	}


	public int comment_delete() {
		Map<String, Object> param = new HashMap<>();
		
		param.put("POST_NO", postno);

		int update = reviewDao.deleteComment(param);
		
		return View.ADMIN_REVIEW_LIST;		
	}


	public int comment_update() {
		System.out.println("========== 댓글 수정 ==========");
		

		System.out.print("내용>\n"+content+"\n");
		String content = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("COM", content);

		int update = reviewDao.updateComment(param);
		
		
		return View.ADMIN_REVIEW_LIST;
	}


	public int user_review_com_delete() {
		Map<String, Object> param = new HashMap<>();
		
		param.put("POST_NO", postno);

		reviewDao.deleteComment(param);
		reviewDao.deleteReview(param);
		return View.REVIEW_LIST;		
	}

}
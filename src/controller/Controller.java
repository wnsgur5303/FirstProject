package controller;

import java.util.Map;

import service.BoardService;
import service.CardService;
import service.OrderService;
import service.ReviewService;
import service.StoreService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {
		
		new Controller().start();
	}
	
	public static String id = null;
	public static Map<String, Object> LoginUser;
	
	private UserService userService = UserService.getInstance();
	private BoardService boardService = BoardService.getInstance();
	private StoreService storeService = StoreService.getInstance();
	private OrderService orderService = OrderService.getInstance();
	private ReviewService reviewService = ReviewService.getInstance();
	private CardService cardService = CardService.getInstance();
	
	private void start() {
		int view = View.HOME;
		
		while (true) {
			switch (view) {
			case View.HOME: view = home(); break;
			case View.LOGIN: view = userService.login(); break;
			case View.JOIN: view = userService.join(); break;
			case View.ADMINMAIN: view = adminmain(); break;
			case View.USERMAIN: view = usermain(); break;
			
			case View.MEM_LIST: view = userService.mem_list(); break;
			case View.MEM_DELETE: view = userService.mem_delete(); break;

			case View.BOARD_LIST: view = boardService.board_list(); break;
			case View.BOARD_VIEW: view = boardService.board_view(); break;
			case View.BOARD_INSERT_FORM: view = boardService.board_insert(); break;
			case View.BOARD_DELETE: view = boardService.board_delete(); break;
			case View.UPDATE_NOTICE: view = boardService.board_update(); break;
			
			
			case View.STORE_LIST: view = storeService.store_list(); break;
			case View.STORE_VIEW: view = storeService.store_view(); break;
			case View.STORE_INSERT_FORM: view = storeService.store_insert(); break;
			case View.STORE_DELETE: view = storeService.store_delete(); break;
			case View.STORE_UPDATE: view = storeService.store_update(); break;
			
			case View.ORD: view = orderService.ord(); break;		
			case View.STOREORD: view = orderService.storeOrd(); break;	
			case View.STOREORDADD: view = orderService.storeselect(); break;	
			case View.STORESELECT: view = orderService.storeselect(); break;	
			case View.STORECHOICE: view = orderService.storechoice(); break;
			case View.ADMIN_ORD_RECEIPT: view = orderService.receipt(); break;
			case View.STORE_SALES: view = storeService.salse(); break;
			
			case View.ADMIN_ORD: view = orderService.adminord(); break;		
			case View.ADMIN_ORD_INFO: view = orderService.ordinfo(); break;	
			
			case View.USER_BOARD_LIST: view = boardService.user_board_list(); break;
			case View.USER_BOARD_VIEW: view = boardService.user_board_view(); break;
			
			case View.REVIEW_LIST: view = reviewService.review_list(); break;
			case View.REVIEW_VIEW: view = reviewService.review_view(); break;
			case View.REVIEW_INSERT_FORM: view = reviewService.review_insert(); break;
			case View.REVIEW_DELETE: view = reviewService.review_delete(); break;
			case View.REVIEW_UPDATE: view = reviewService.review_update(); break;
			
			case View.ADMIN_REVIEW_LIST: view = reviewService.admin_review_list(); break;
			case View.ADMIN_REVIEW_VIEW: view = reviewService.admin_review_view(); break;
			case View.COMMENT_INSERT: view = reviewService.comment_insert(); break;
			case View.COMMENT_DELETE: view = reviewService.comment_delete(); break;
			case View.COMMENT_UPDATE: view = reviewService.comment_update(); break;
			case View.USER_REVIEW_COM_DELETE: view = reviewService.user_review_com_delete(); break;
			
			case View.USER_PROFILE: view = userService.user_profile(); break;
			case View.USER_MEM_DELETE: view = userService.user_mem_delete(); break;
			case View.USER_MEM_UPDATE: view = userService.user_mem_update(); break;
			
			case View.CARD: view = cardService.card_list(); break;
			case View.CARD_SET: view = cardService.card_change(); break;
			case View.CARD_CREATE: view = cardService.card_insert(); break;
			case View.CARD_DEL: view = cardService.card_del(); break;
			case View.CARD_MONEY: view = cardService.card_money(); break;
			}
		}
	}
	
	


	private int home() {
		try {
			Thread.sleep(70);
			System.out.println("☆☆☆☆☆■■■■■■☆☆■■■■■■■■■■☆☆☆☆☆☆☆☆☆■☆☆☆☆☆☆☆☆☆☆☆■■■■■■■■■■☆☆☆☆■■■■■■■☆☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆☆☆■■☆☆☆");
			Thread.sleep(70); 
			System.out.println("☆☆☆☆■■■■■■■☆☆■■■■■■■■■■☆☆☆☆☆☆☆☆■■■☆☆☆☆☆☆☆☆☆☆■■■■■■■■■■■☆☆☆■■☆☆☆■■■☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆☆■■■☆☆☆☆■■☆☆☆☆☆■■☆☆☆☆☆☆☆☆☆☆☆■■☆■■☆☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆■■■☆☆☆■■☆☆☆☆■■■☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆■■☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆☆■■■☆☆☆☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆☆■■☆☆☆☆☆■■☆☆☆☆☆■■☆☆☆☆■■■☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆☆☆■■■☆☆☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆☆☆☆■■☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆☆■■☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆■■☆☆☆☆☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆☆☆☆■■■☆☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆☆☆■■■■■■■■■■■☆☆☆☆☆☆■■■■■■■■■☆☆☆☆☆■■■■■☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■■■☆☆☆☆☆☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆☆☆☆☆■■■☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆☆■■■■■■■■■■■■■☆☆☆☆☆■■■■■■■■■■☆☆☆☆■■■■■☆☆☆☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆■■☆☆☆☆☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆■■☆☆☆☆■■■■☆☆☆☆☆☆■■☆☆☆☆☆☆■■☆☆☆☆☆☆☆☆☆☆☆■■☆☆☆☆■■☆☆☆☆☆☆■■☆☆☆☆■■☆☆☆■■■☆☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆■■☆☆☆☆■■■☆☆☆☆☆☆■■☆☆☆☆☆■■☆☆☆☆☆☆☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆■■■☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆■■☆☆☆☆☆☆☆");
			Thread.sleep(70);
			System.out.println("☆☆■■☆☆☆☆■■■☆☆☆☆☆☆■■☆☆☆☆■■☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆■■☆☆■■☆☆☆☆☆☆☆☆■■☆☆■■☆☆☆■■■☆☆☆☆☆☆■■☆☆☆☆☆■■☆☆☆☆■■☆☆☆☆☆☆☆■■☆☆☆☆☆");	
			Thread.sleep(70);
			System.out.println("☆☆☆☆■■■■■☆☆☆☆☆☆☆☆■■☆☆☆■■☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆■■☆■■☆☆☆☆☆☆☆☆☆■■☆■■■■■■■■☆☆☆☆☆☆☆☆■■■■■☆☆☆☆☆☆■■☆☆☆☆☆☆☆☆☆☆■■☆☆");
			Thread.sleep(70);
} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("---------------------------------------");
		System.out.println("1.로그인     \t2.회원가입    \t3.나가기");
		System.out.println("---------------------------------------");
		System.out.println("입력>");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1: return View.LOGIN;
		case 2: return View.JOIN;
		case 3:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
		}
		return View.HOME;
		
	}
	
	
	public static int adminmain(){
		
		System.out.println("===========================관리자 메뉴===========================");
		System.out.println("1.주문관리 2.매출조회 3.회원관리 4.매장관리 5.공지사항관리 6.리뷰게시판관리 7.로그아웃");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:		
			return View.ADMIN_ORD;
		case 2:		
			return View.STORE_SALES;
		case 3:
			return View.MEM_LIST;
		case 4:
			return View.STORE_LIST;
		case 5:
			return View.BOARD_LIST;
		case 6:
			return View.ADMIN_REVIEW_LIST;
		case 7:
			LoginUser = null;
			return View.HOME;
		}
		return View.ADMINMAIN;
	}
	

	private int usermain() {
		
		System.out.println("==============안녕하세요, ★STARBUK★  입니다.==============");
		System.out.println("1.사이렌오더 2.내 정보  3.공지사항  4.리뷰게시판  5.카드관리  6.로그아웃");
		System.out.print("입력>");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:		
			return View.ORD;
		case 2:		
			return View.USER_PROFILE;
		case 3:
			return View.USER_BOARD_LIST;
		case 4:
			return View.REVIEW_LIST;
		case 5:
			return View.CARD;
		case 6:
			LoginUser = null;
			return View.HOME;
		}
		return View.USERMAIN;
	}

}

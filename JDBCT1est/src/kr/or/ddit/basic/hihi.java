package kr.or.ddit.basic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.JDBCUtil;
import kr.or.ddit.util.JDBCUtil3;

/*
	회원정보를 관리하는 프로그램을 작성하는데 
	아래의 메뉴를 모두 구현하시오. (CRUD기능 구현하기)
	(DB의 MYMEMBER테이블을 이용하여 작업한다.)
	
	* 자료 삭제는 회원ID를 입력 받아서 삭제한다.
	
	예시메뉴)
	----------------------
		== 작업 선택 ==
		1. 자료 입력			---> insert
		2. 자료 삭제			---> delete
		3. 자료 수정			---> update
		4. 전체 자료 출력	---> select
		5. 작업 끝.
	----------------------
	 
	   
// 회원관리 프로그램 테이블 생성 스크립트 
create table mymember(
    mem_id varchar2(8) not null,  -- 회원ID
    mem_name varchar2(100) not null, -- 이름
    mem_tel varchar2(50) not null, -- 전화번호
    mem_addr varchar2(128),    -- 주소
    CONSTRAINT MYMEMBER_PK PRIMARY KEY (mem_id)
);

*/
public class hihi {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in); 
	
	/**
	 * 메뉴를 출력하는 메서드
	 */
	public void displayMenu(){
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 새글 작성");
		System.out.println("  2. 자료 삭제");
		System.out.println("  3. 자료 수정");
		System.out.println("  4. 전체 목록 출력");
		System.out.println("  5. 검색");
		System.out.println("  6. 작업 끝.");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
	
	/**
	 * 프로그램 시작메서드
	 */
	public void start(){
		int choice;
		do{
			displayMenu(); //메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch(choice){
				case 1 :  // 새글 작성
					insertjdbc();
					break;
				case 2 :  // 자료 삭제
					deletejdbc();
					break;
				case 3 :  // 자료 수정
					updatejdbc();
					break;
				case 4 :  // 전체 목록 출력
					displayAll();
					break;
				case 5 :  //검색
					selectjdbc();
					break;
				case 6 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
				default :
					System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		}while(choice!=6);
	}
	
	
	/*
	 * 게시글 검색하는 메서드
	 */
	private void selectjdbc() {
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println(" 번호\t제 목\t작 성 자\t작성날짜\t내용");
			System.out.println("----------------------------------------");
			
			try {
				conn = JDBCUtil3.getConnection();
				
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery("select * from jdbc_board");
				
				while(rs.next()) {
					String board_no  = rs.getString("board_no");
					String board_title  = rs.getString("board_title");
					String board_writer = rs.getString("board_writer");
					String board_date = rs.getString("board_date");
					String board_content = rs.getString("board_content");
					System.out.println(board_no + "\t" + board_title + "\t" + board_writer + "\t" + board_date + "\t" + board_content);		
				}
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			} finally {
				JDBCUtil.disConnect(conn, stmt, pstmt, rs);
			}
			
			boolean chk = false;
			String board_no = null;
			
			do {
				System.out.println();
				System.out.println("상세검색할 게시글 번호를 입력하세요.");
				System.out.print("게시글 번호 >> ");
				board_no  = scan.next();
				
				chk = checkMember(board_no);
				if(chk == false) {
					System.out.println("게시글 번호가" + board_no  + "인 게시글이 없습니다.");
					System.out.println("다시 입력해 주세요.");
				}
			}while(chk == false);
			
			try {
				conn = JDBCUtil3.getConnection();
				
				String sql = "select *" + 
						   " from jdbc_board" + 
						   " where board_no=?"; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board_no);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					board_no  = rs.getString("board_no");
					String board_title  = rs.getString("board_title");
					String board_writer = rs.getString("board_writer");
					String board_date = rs.getString("board_date");
					String board_content = rs.getString("board_content");
					System.out.println("게시글 번호:" + board_no + "\n" + "제목:" + board_title + "\n"+"작성자:" + board_writer + "\n"+"날짜:" + board_date + "\n"+"내용:" + board_content);		
				}
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			} finally {
				JDBCUtil.disConnect(conn, stmt, pstmt, rs);
			}
			
		}
	
		
	

	/*
	 * 게시글을 삭제하는 메서드 (입력받은 게시글 번호를 이용하여 삭제한다.)
	 */
	private void deletejdbc() {
		System.out.println();
		System.out.println("삭제할 게시글 번호를 입력하세요.");
		System.out.print("게시글 번호 >> ");
		String board_no = scan.next();
		
		try {
			
			conn = JDBCUtil3.getConnection();
			
			String sql = "delete from jdbc_board where board_no=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board_no);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(board_no + "번 게시글 삭제 작업 성공");
			}else {
				System.out.println(board_no + "번 게시글 삭제 작업 실패");
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
	}
	

	/*
	 * 게시글을 수정하는 메서드
	 */
	private void updatejdbc() {
		
		boolean chk = false;
		String board_no = null;
		
		do {
			System.out.println();
			System.out.println("수정할 게시글 번호를 입력하세요.");
			System.out.print("게시글 번호 >> ");
			board_no  = scan.next();
			
			chk = checkMember(board_no);
			
			if(chk == false) {
				System.out.println("게시글 번호가" + board_no  + "인 게시글이 없습니다.");
				System.out.println("다시 입력해 주세요.");
			}
		}while(chk == false);
		
		System.out.print("제목 >>");
		String jdbc_title = scan.next();
		
		System.out.print("작성자 >>");
		String jdbc_writer = scan.next();
		
	
		System.out.print("내용>>");
		String jdbc_content = scan.nextLine();
		
		try {

			conn = JDBCUtil3.getConnection();
			
			String sql = "update jdbc_board" 
			          + " set board_title  = ?" 
					  + "    ,board_writer  = ?" 
			          + "	 ,board_content = ?" 
					  + "where board_no  = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jdbc_title);
			pstmt.setString(2, jdbc_writer);
			pstmt.setString(3, jdbc_content);
			pstmt.setString(4, board_no);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(board_no + "번 게시글 수정 작업 성공");
			}else {
				System.out.println(board_no + "번 게시글 수정 작업 실패");
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println(board_no + "번 게시글 수정 작업에 실패하였습니다.");
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
	}
	

	/*
	 * 전체 게시글 출력하는 메서드
	 */
	private void displayAll() {
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println(" 번호\t제 목\t작 성 자\t작성날짜\t내용");
		System.out.println("----------------------------------------");
		
		try {
			conn = JDBCUtil3.getConnection();
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from jdbc_board");
			
			while(rs.next()) {
				String board_no  = rs.getString("board_no");
				String board_title  = rs.getString("board_title");
				String board_writer = rs.getString("board_writer");
				String board_date = rs.getString("board_date");
				String board_content = rs.getString("board_content");
				System.out.println(board_no + "\t" + board_title + "\t" + board_writer + "\t" + board_date + "\t" + board_content);		
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/*
	 * 게시글을 추가하기 위한 메서드
	 */
	private void insertjdbc() {
		
		//중복검사 생략
		
		System.out.print("제목 >>");
		String jdbc_title = scan.next();
		
		System.out.print("작성자 >>");
		String jdbc_writer = scan.next();
		
		scan.nextLine(); //입력버퍼 비우기
		System.out.print("내용>>");
		String jdbc_content = scan.nextLine();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "insert into jdbc_board (board_no , board_title , board_writer ,board_date, board_content )"
						+"values (board_seq.nextval,?,?,sysdate,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jdbc_title);
			pstmt.setString(2, jdbc_writer);
			pstmt.setString(3, jdbc_content);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("제목 : "  + jdbc_title + "게시글 등록 성공");
			}else {
				System.out.println("게시글 등록 실패");
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/*
	 * 회원 ID를 이용하여 회원이 있는지 알려주는 메서드
	 * @param memId
	 * @return 존재하면  true, 없으면 false
	 */
	private boolean checkMember(String board_no) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql ="select count(*) cnt from jdbc_board where board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board_no);

			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			if(cnt > 0) {
				chk = true;
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
		
		return chk;
	}

	public static void main(String[] args) {
		new hihi().start(); 
	}
}


 




package kr.or.ddit.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.JDBCUtil3;

public class BoardDaoImpl implements IBoardDao {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public int insertBoard(Connection conn, BoardVO mv) throws SQLException {

		int cnt = 0;
		try {
			String sql = "insert into jdbc_board (board_no,board_title,board_writer,board_date,board_content) "
					+ "values (board_seq.nextval,?,?,sysdate,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getBoardTitle());
			pstmt.setString(2, mv.getBoardWriter());
			pstmt.setString(3, mv.getBoardContent());

			cnt = pstmt.executeUpdate();

		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}

		return cnt;
	}

	@Override
	public boolean checkBoard(Connection conn, int boardNo) throws SQLException {

		boolean chk = false;
		try {
			String sql = "select count(*) as cnt from jdbc_board where board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			// pstmt.setInt(parameterIndex, x);

			rs = pstmt.executeQuery();

			int cnt = 0;
			while (rs.next()) {
				cnt = rs.getInt("cnt");
			}

			if (cnt > 0) {
				chk = true;
			}
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}

		return chk;
	}

	@Override
	public List<BoardVO> getAllBoardList(Connection conn) throws SQLException {

		List<BoardVO> boardList = new ArrayList<BoardVO>();

		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from jdbc_board");

			while (rs.next()) {
				BoardVO mv = new BoardVO();

				int boardNo = rs.getInt("board_no");
				String boadrTitle = rs.getString("board_title");
				String boardWriter = rs.getString("board_writer");
				String boardDate = rs.getString("board_date");
				String boardContent = rs.getString("board_content");

				mv.setBoardNo(boardNo);
				mv.setBoardTitle(boadrTitle);
				mv.setBoardWriter(boardWriter);
				mv.setBoardDate(boardDate);
				mv.setBoardContent(boardContent);

				boardList.add(mv);
			}
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}
		return boardList;
	}

	@Override
	public int updateBoard(Connection conn, BoardVO mv) throws SQLException {

		int cnt = 0;
		try {
			String sql = "update jdbc_board  " + "set       board_title = ?, board_date=sysdate, board_content = ? "
					+ "where  board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getBoardTitle());
			pstmt.setString(2, mv.getBoardContent());
			pstmt.setInt(3, mv.getBoardNo());

			cnt = pstmt.executeUpdate();
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}

		return cnt;
	}

	@Override
	public int deleteBoard(Connection conn, int boardNo) throws SQLException {

		int cnt = 0;
		try {
			String sql = "delete  " + "from   jdbc_board " + "where  board_no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			cnt = pstmt.executeUpdate(); // return값이 int값
			// execute()의 반환값 => result set(sql이 select문일때)
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}

		return cnt;
	}

	@Override
	public List<BoardVO> getSearchBoard(Connection conn, BoardVO mv) throws SQLException {

		List<BoardVO> boardList = new ArrayList<>();

		try {
			String sql = "select * from jdbc_board where 1=1 ";

			if (mv.getBoardTitle() != null && !mv.getBoardTitle().equals("")) {
				sql += " and board_title = ? ";
			}
			if (mv.getBoardWriter() != null && !mv.getBoardWriter().equals("")) {
				sql += " and board_writer = ? ";
			}
			if (mv.getBoardContent() != null && !mv.getBoardContent().equals("")) {
				sql += " and board_content like '%' || ? || '%' ";

			}

			pstmt = conn.prepareStatement(sql);

			int index = 1;

			if (mv.getBoardTitle() != null && !mv.getBoardTitle().equals("")) {
				pstmt.setString(index++, mv.getBoardTitle());
			}
			if (mv.getBoardWriter() != null && !mv.getBoardWriter().equals("")) {
				pstmt.setString(index++, mv.getBoardWriter());
			}
			if (mv.getBoardContent() != null && !mv.getBoardContent().equals("")) {
				pstmt.setString(index++, mv.getBoardContent());
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO mv2 = new BoardVO();
				mv2.setBoardNo(rs.getInt("board_no"));
				mv2.setBoardTitle(rs.getString("board_title"));
				mv2.setBoardWriter(rs.getString("board_writer"));
				mv2.setBoardDate(rs.getString("board_date"));
				mv2.setBoardContent(rs.getString("board_content"));

				boardList.add(mv2);
			}

		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, rs);
		}

		return boardList;
	}
}

package kr.or.ddit.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.JDBCUtil3;

public class BoardServiceImpl implements IBoardService {

	private IBoardDao boardDao;
	private Connection conn;

	public BoardServiceImpl() {
		boardDao = new BoardDaoImpl();
	}

	@Override
	public int insertBoard(BoardVO mv) {

		int cnt = 0;

		try {
			conn = JDBCUtil3.getConnection();
			cnt = boardDao.insertBoard(conn, mv);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}

		return cnt;
	}

	@Override
	public boolean checkBoard(int boardNo) {

		boolean chk = false;

		try {
			conn = JDBCUtil3.getConnection();
			chk = boardDao.checkBoard(conn, boardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}

		return chk;
	}

	@Override
	public List<BoardVO> getAllBoardList() {

		List<BoardVO> boardList = new ArrayList<BoardVO>();

		try {
			conn = JDBCUtil3.getConnection();
			boardList = boardDao.getAllBoardList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return boardList;
	}

	@Override
	public int updateBoard(BoardVO mv) {

		int cnt = 0;

		try {
			conn = JDBCUtil3.getConnection();
			cnt = boardDao.updateBoard(conn, mv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

	@Override
	public int deleteBoard(int boardNo) {

		int cnt = 0;

		try {
			conn = JDBCUtil3.getConnection();
			cnt = boardDao.deleteBoard(conn, boardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

	@Override
	public List<BoardVO> getSearchBoard(BoardVO mv) {

		List<BoardVO> boardList = new ArrayList<>();

		try {
			conn = JDBCUtil3.getConnection();
			boardList = boardDao.getSearchBoard(conn, mv);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return boardList;
	}
}

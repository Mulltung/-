package kr.or.ddit.board.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.vo.BoardVO;

public class BoardDaoImpl implements IBoardDao{

	
	private static IBoardDao boardDao;
	
	
	private BoardDaoImpl() {
		
	}
	
	public static IBoardDao getInstance() {
		if(boardDao == null) {
			boardDao = new BoardDaoImpl();
		}
		return boardDao;
	}
	
	
	@Override
	public int insertBoard(SqlMapClient smc, BoardVO mv) throws SQLException {
		int cnt = 0;
		
		Object obj = smc.insert("board.insertBoard", mv);
		
		if(obj == null) {
			cnt = 1;
		}
		
		return cnt;
	}

	@Override
	public boolean checkBoard(SqlMapClient smc, int boardNo) throws SQLException {
		boolean chk = false;
		
		int cnt = (int) smc.queryForObject("board.getBoard", boardNo);
		
		if(cnt > 0) {
			chk = true;
		}
		return chk; 
	}

	@Override
	public List<BoardVO> getAllBoardList(SqlMapClient smc) throws SQLException {
		
		List<BoardVO> boardList = smc.queryForList("board.getBoardAll");
		
		
	
		return boardList;
	}

	@Override
	public int updateBoard(SqlMapClient smc, BoardVO mv) throws SQLException {
		int cnt = 0;	
		
		cnt = smc.update("board.updateBoard", mv);
		return cnt;
	}

	@Override
	public int deleteBoard(SqlMapClient smc, int boardNo) throws SQLException {
		int cnt = smc.delete("board.deleteBoard",boardNo);

		return cnt;
	}

	@Override
	public List<BoardVO> getSearchBoard(SqlMapClient smc, BoardVO mv) throws SQLException {
		
		List<BoardVO> boardList = smc.queryForList("board.getSearchBoard", mv);

		
		return boardList;
	}
}
		
	



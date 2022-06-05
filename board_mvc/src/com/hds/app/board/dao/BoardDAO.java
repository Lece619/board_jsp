package com.hds.app.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hds.app.board.vo.BoardVO;
import com.hds.app.mybatis.config.MyBatisConfig;

public class BoardDAO {
	SqlSessionFactory sessionFactory = MyBatisConfig.getSqlsession_f();
	SqlSession sqlSession;
	
	public BoardDAO() {
		sqlSession = sessionFactory.openSession(true);
	}
	
	//게시글 목록
	public List<BoardVO> getList(int startRow, int endRow) {
		Map<String,Integer> pageMap = new HashMap<>();
		pageMap.put("startRow", startRow);
		pageMap.put("endRow", endRow);
		
		return sqlSession.selectList("Board.getList",pageMap);
	}
	
	//게시글 전체 개수
	public int getTotal() {
		return sqlSession.selectOne("Board.getTotal");
	}
}

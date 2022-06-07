package com.hds.app.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hds.app.action.Action;
import com.hds.app.action.ActionForward;
import com.hds.app.board.dao.BoardDAO;
import com.hds.app.files.dao.FilesDAO;
import com.hds.app.files.vo.FilesVO;

public class BoardViewOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		ActionForward forward = new ActionForward();
		BoardDAO bDao = new BoardDAO();
		FilesDAO fDao = new FilesDAO();
		
		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		//조회수 1증가시키기
		bDao.updateReadCount(boardNum);

		req.setAttribute("board",bDao.getDetail(boardNum));
		req.setAttribute("files", fDao.getFiles(boardNum));
		
		
		forward.setRedirect(false);
		forward.setPath("/app/board/boardView.jsp");
		
		return forward;
	}

}

package com.hds.app.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hds.app.action.Action;
import com.hds.app.action.ActionForward;
import com.hds.app.board.dao.BoardDAO;
import com.hds.app.board.vo.BoardVO;
import com.hds.app.files.dao.FilesDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		/*req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");*/
		
		String saveFolder = "C:\\jsp_jinho\\workspace2\\board_jsp\\board_mvc\\WebContent\\app\\upload";
		int fileSize = 1024 * 1024 * 5;  //5메가 바이트.
		
		BoardDAO bDao = new BoardDAO();
		BoardVO board = new BoardVO();
		FilesDAO fDao = new FilesDAO();
		ActionForward forward = new ActionForward();
		
		
		MultipartRequest multi = null;
//		multi = new MultipartRequest(req, 경로, 파일의 크기, 인코딩방식, new DefaultFileRenamePolicy());
		multi = new MultipartRequest(req, saveFolder, fileSize,"UTF-8", new DefaultFileRenamePolicy());
		
		board.setBoardTitle(multi.getParameter("boardTitle"));
		board.setBoardContent(multi.getParameter("boardContent"));
		board.setBoardId(multi.getParameter("boardId"));
		bDao.insertBoard(board);
		//당장 boardNum을 모르기 때문에 현재 Seq를 받아오는 sql문을 board sql에 추가.
		fDao.insertFile(multi, bDao.getSeq());
		
		forward.setRedirect(true);
		forward.setPath(req.getContextPath() + "/board/BoardListOk.bo");
		
		return forward;
	}

}

package com.hds.app.board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hds.app.action.Action;
import com.hds.app.action.ActionForward;
import com.hds.app.board.dao.BoardDAO;
import com.hds.app.files.dao.FilesDAO;
import com.hds.app.files.vo.FilesVO;

public class BoardDeleteOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String saveFolder = "C:\\jsp_jinho\\workspace2\\board_jsp\\board_mvc\\WebContent\\app\\upload";
		BoardDAO bDao = new BoardDAO();
		FilesDAO fDao = new FilesDAO();
		ActionForward forword = new ActionForward();
		
		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		
		for(FilesVO file : fDao.getFiles(boardNum)) {
			File f = new File(saveFolder,file.getFileName());
			System.out.println(file.getFileName());
			if(f.exists()) {
				System.out.println("들어옴");
				f.delete();
			}
		}
		
		fDao.deleteFiles(boardNum);
		bDao.deleteBoard(boardNum);
		
		forword.setRedirect(false);
		forword.setPath("/board/BoardList.bo");
		
		return forword;
	}

}

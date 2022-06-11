package com.hds.app.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hds.app.action.ActionForward;

public class BoardFrontController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	//get post 두방식 모두 이 매소드를 사용해준다.  URI 분석!
	protected void doProcess(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
	//URL 분석  정보가 있는 페이지.  
	//URI ->  정확한 정보  =>ContextPath 를
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		ActionForward forward = new ActionForward();
		if(command.equals("/board/BoardListOk.bo")) {
			try {
				forward = new BoardListOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/board/BoardList.bo 이동 중 오류  : " + e);
			}
			//로그인 성공시 게시판 이동 
		}else if(command.equals("/board/BoardList.bo")) {
			try {
				forward = new BoardListOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/board/BoardList.bo 이동 중 오류  : " + e);
			}
		}else if(command.equals("/board/BoardWrite.bo")){
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/board/boardWrite.jsp");
			
		}else if(command.equals("/board/BoardWriteOk.bo")){
			try {
				forward = new BoardWriteOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			//게시글 상세보기
		}else if(command.equals("/board/BoardViewOk.bo")) {
			try {
				forward = new BoardViewOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			//첨부파일 다운로드/board/FileDownloadOk.bo
		}else if(command.equals("/board/FileDownload.bo")) {
			try {
				forward = new FileDownloadOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("파일 다운중 문제 생김");
				e.printStackTrace();
			}
			//글 삭제
		}else if(command.equals("/board/BoardDeleteOk.bo")) {
			try {
				forward = new BoardDeleteOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
			//수정
		}else if(command.equals("/board/BoardModify.bo")) {
				try {
					forward = new BoardModify().execute(req, resp);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
		}else if(command.equals("/board/BoardModifyOk.bo")) {
			try {
				forward = new BoardModifyOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		///////////////Q*****************댓글부분*************
		else if(command.equals("/board/BoardReplyListOk.bo")) {
			try {
				forward = new BoardReplyListOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}else if(command.equals("/board/BoardReplyWriteOk.bo")) {
			try {
				forward = new BoardReplyWriteOk().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/board/BoardReplyModifyOk.bo")) {
			try {
				forward = new BoardReplyModifyOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}else if(command.equals("/board/BoardReplyDeleteOk.bo")) {
			try {
				forward = new BoardReplyDeleteOk().execute(req, resp);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	
		
		
		if(forward != null) {
			//Redirect 가 true 면 정보를 붙일 필요없이 그대로 보내주고 
			if(forward.isRedirect()) {
				resp.sendRedirect(forward.getPath());
				
				//아니라면 디스패처에 정보를 붙여서 보내준다.
			}else {
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);
			}
		}
	}
}

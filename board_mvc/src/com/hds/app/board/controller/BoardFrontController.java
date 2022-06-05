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
		System.out.println(command);
		if(command.equals("/board/BoardListOk.bo")) {
			try {
				forward = new BoardListOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/board/BoardList.bo 이동 중 오류  : " + e);
			}
			//로그인 성공시 게시판 이동 
		}else if(command.equals("/board/BoardList.bo")) {
			System.out.println("들어옴");
			try {
				forward = new BoardListOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/board/BoardList.bo 이동 중 오류  : " + e);
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

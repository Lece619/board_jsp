package com.hds.app.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hds.app.action.ActionForward;

//서블릿의 역할을 해줘야한다.
public class MemberFrontController extends HttpServlet{
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
		
		//이프나 스위치 ->원하는 컨트롤러로 보내준다.
		if(command.equals("/member/MemberCheckIdOk.me")) {
			try {
				forward = new MemberCheckIdOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("아이디 중복검사 오류 "+ e);
			}
		}else if(command.equals("/member/MemberJoinOk.me")) {
			
		}
		
		if(forward != null) {
			if(forward.isRedirect()) {
				resp.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);
			}
		}
	}
}

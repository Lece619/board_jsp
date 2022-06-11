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
		//아이디 체크시 *.me 이 서블릿에 들어오게되고 커맨드분석
		//아이디 체크
		if(command.equals("/member/MemberCheckIdOk.me")) {
			try {
				forward = new MemberCheckIdOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("아이디 중복검사 오류 "+ e);
			}
			//회원가입
		}else if(command.equals("/member/MemberJoinOk.me")) {
			try {
				forward = new MemberJoinOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("회원가입 오류" + e);
			}
		}else if(command.equals("/member/MemberLoginOk.me")) {
			try {
				forward = new MemberLoginOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("로그인 오류" + e);
			}
			//단순 페이지 이동이라 OK가 안들어감
		}else if(command.equals("/member/MemberLogin.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/member/login.jsp");
		}else if(command.equals("/member/MemberJoin.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/member/join.jsp");
		}else if(command.equals("/member/MemberLogoutOk.me")) {
			try {
				forward = new MemberLogoutOk().execute(req, resp);
			} catch (Exception e) {
				System.out.println("로그아웃 오류" + e);
			}
		}
		//아이디 찾기
		else if(command.equals("/member/MemberFindIdOk.me")) {
			
			try {
				forward = new MemberFindIdOk().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/member/MemberFindId.me")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/member/findId.jsp");
		}
		
		if(forward != null) {
			//Redirect 가 true 면 정보를 붙일 필요없이 그대로 보내주고 
			if(forward.isRedirect()) {
				resp.sendRedirect(forward.getPath());
				//아니라면 디스패처에 정보를 붙여서 보내준다.
			}else {
				//디스패쳐에 경로를 넣을때!! ContextPath는 이미 들어가있어서 forward패스만 추가하는것.
				RequestDispatcher dispatcher = req.getRequestDispatcher(forward.getPath());
				dispatcher.forward(req, resp);
			}
		}
	}
}

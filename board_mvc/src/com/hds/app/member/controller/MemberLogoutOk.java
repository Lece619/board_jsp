package com.hds.app.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hds.app.action.Action;
import com.hds.app.action.ActionForward;

public class MemberLogoutOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		ActionForward forward = new ActionForward();

		//session 내용을 전부 지움
		session.invalidate();
		
		//되도록 보낼때 내용을 다지우고
		forward.setRedirect(true);
		forward.setPath(req.getContextPath()+"/member/MemberLogin.me");
		
		return forward;
	}
	
}

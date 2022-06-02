package com.hds.app.member.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hds.app.action.Action;
import com.hds.app.action.ActionForward;
import com.hds.app.member.dao.MemberDAO;

//추상 클래스로 만들고 
public class MemberCheckIdOk implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String memberId = req.getParameter("memberId");
		MemberDAO dao = new MemberDAO();
		JSONObject obj = new JSONObject();
		PrintWriter out = resp.getWriter();
		
		//중복일때 true
		if(dao.checkId(memberId)) {
			obj.put("status","not-ok");
		}else {
			obj.put("status", "ok");
		}
		//바디위치에 Parsing이 된다.
		out.println(obj.toJSONString());
		out.close();
		return null;
	}
}

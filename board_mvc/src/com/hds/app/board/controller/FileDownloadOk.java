package com.hds.app.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hds.app.action.Action;
import com.hds.app.action.ActionForward;

public class FileDownloadOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String saveFolder = "C:\\jsp_jinho\\workspace2\\board_jsp\\board_mvc\\WebContent\\app\\upload";
		String fileName = req.getParameter("fileName");
		boolean check = true;
		ActionForward forward = new ActionForward();
		
		//바이트로 직접 가져오고 내보낸다.
		InputStream in = null;
		OutputStream out = null;
		
		File file = null;
		//saveFolder(상위경로), fileName(하위경로)
		try {
			file = new File(saveFolder,fileName);
			in = new FileInputStream(file);
		} catch (Exception e) {
			check = false;
			e.printStackTrace();
		}
		
		resp.setContentType("application/ocatat-stream"); //파일 다운로드를 위한 전송할 데이터 타입 설정
		resp.setHeader("Content-Description","JSP Generated Data");//헤더에 담을 데이터의 내용에 대한 설명 수정
		
		if(check) {
			try {
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
																	//filename="filename";
				resp.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
				resp.setHeader("Content-Type", "application/ocatat-stream;charset=utf-8");
				resp.setHeader("Content-length", "" + file.length());
				//보낼설정 후 보내야한다.
				
				out = resp.getOutputStream();
				byte[] b = new byte[(int)(file.length())];
				int data = 0;
				
				while((data=in.read(b)) > 0) {
					out.write(b, 0, data); // 바이트 배열에 하나씩 넣기.
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(in != null) {
						in.close();
					}
					if(out != null) {
						out.close();
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
			
		}
		
		/*forward.setRedirect(true);
		forward.setPath("/app/board/boardView.jsp");
		*/
		return null;
	}

}

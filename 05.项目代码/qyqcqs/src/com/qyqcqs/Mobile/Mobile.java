package com.qyqcqs.Mobile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class Mobile extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	public void output(HttpServletResponse response,String jsonStr){
		if(jsonStr == null){
			JSONObject jo = new JSONObject();
			jo.put("error", "1");
			jo.put("msg", "暂不可用请联系管理员!");
			jsonStr = jo.toJSONString();
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/plain; chartset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer;
		
		try {
			writer = response.getWriter();
			writer.print(jsonStr);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.qyqcqs.Mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.database.ConnectionDB;

public class Login extends Mobile {

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
		//获取传入参数
		String loginname =request.getParameter("loginname");
		if(null == loginname || loginname.equals("")){
			output(response,null);
			return ;
		}
		//查询用户个人信息
		ConnectionDB conn = new ConnectionDB();
		String sql = "select user_id from user_system where user_mobile like ? or user_email like ? or user_pinyin like ?";
		String params[] = new String[3];
		
		params[0]=loginname;
		params[1]=loginname;
		params[2]=loginname;
		List l = conn.query(sql, params);
		if(null == l|| l.size()==0){
			Map error = new HashMap();
			error.put("error", "2");
			error.put("msg", "用户名不存在");
			output(response,JSONObject.toJSONString(error));
			return ;
		}
		Map m = (Map) l.get(0);
		
		output(response,JSONObject.toJSONString(m));
	}

}

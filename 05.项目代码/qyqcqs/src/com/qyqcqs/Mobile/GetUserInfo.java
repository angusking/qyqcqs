package com.qyqcqs.Mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.database.ConnectionDB;

public class GetUserInfo extends Mobile {

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
		//获取用户ID
		String userid = request.getParameter("user_id");
		if(null == userid || userid.equals("")){
			output(response,null);
			return ;
		}
		//查询用户详细信息
		ConnectionDB conn = new ConnectionDB();
		String sql = "select a.hospital_name,b.dept_name,c.user_name,c.user_title,c.user_major,d.user_mobile,c.user_sex,d.user_pinyin,e.balance "+
					"from hospital a,dept b,user c,user_system d,account e "+
					"where d.user_id=c.user_id and a.hospital_id=d.hospital_id and e.user_id=c.user_id "+
					"and b.dept_id=d.dept_id and c.user_id = ? ";
		String params[] = new String[1];
		params[0] = userid;
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

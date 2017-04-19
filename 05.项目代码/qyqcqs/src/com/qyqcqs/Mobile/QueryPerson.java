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

public class QueryPerson extends Mobile {

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取传入参数
		String user_id = request.getParameter("user_id");
		if (null == user_id || user_id.equals("")) {
			output(response, null);
			return;
		}
		ConnectionDB conn = new ConnectionDB();
		String sql = "select a.user_name,c.hospital_name,d.dept_name "
				+ "from user a,user_system b,hospital c,dept d "
				+ "where a.user_id=b.user_id and b.dept_id=d.dept_id "
				+ "and c.hospital_id=d.hospital_id and a.user_id= ?";
		String params[] = new String[1];
		params[0]=user_id;
		List l = conn.query(sql, params);
		if(null == l|| l.size()==0){
			Map error = new HashMap();
			error.put("error", "3");
			error.put("msg", "用户查询出错");
			output(response,JSONObject.toJSONString(error));
			return ;
		}
		Map m = (Map) l.get(0);
		output(response,JSONObject.toJSONString(m));
	}

}

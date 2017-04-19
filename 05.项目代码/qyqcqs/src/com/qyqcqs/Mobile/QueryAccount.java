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
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;

public class QueryAccount extends Mobile {

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

		// 获取传入参数
		Map param = Utils.getValidateParam(request);
		
		String user_id = request.getParameter("user_id");
		
		String sql = "select b.account_record_id record_id,date_format(b.activity_time,'%m月%d日') date,c.order_name,b.money "+
				"from account a,account_record b,orders c "+
				"where a.account_id = b.account_id and a.user_id = ? "+
				"and c.order_id = b.order_id";
		
		// 查询个人账户
		ConnectionDB conn = new ConnectionDB();
		String params[] = new String[1];
		params[0] = user_id;
		List l = conn.query(sql, params);
		if(null == l|| l.size()==0){
			Map error = new HashMap();
			error.put("error", "9");
			error.put("msg", "账户查询失败");
			output(response,JSONObject.toJSONString(error));
			return ;
		}
		output(response,JSONObject.toJSONString(l));
	}

}

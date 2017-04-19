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
import com.qyqcqs.common.Constant;
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;

public class QueryCurrentOrder extends Mobile {

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
		Map param = Utils.getValidateParam(request);
		//校验参数
		Map ending = Utils.toCheckParam(Constant.QUERY_CURRENT_ORDER,
				Constant.QUERY_CURRENT_ORDER_DESC, param);
		if (!(Boolean) ending.get("success")) {
			output(response,JSONObject.toJSONString(ending));
			return ;
		}
		String user_id = request.getParameter("user_id");

		
		ConnectionDB conn = new ConnectionDB();
		String sql = "select a.order_id,a.order_name from orders a,order_detail b "+
					"where a.post_doctor_id = ? and "+
					"a.order_id = (select max(order_id) from orders where post_doctor_id = a.post_doctor_id) "+
					"and a.order_id = b.order_id and b.order_end_time is null";
		String params[] = new String[1];
		params[0] = user_id;
		List l = conn.query(sql, params);
		if(null == l|| l.size()==0){
			Map error = new HashMap();
			error.put("error", "8");
			error.put("msg", "暂无未处理订单");
			output(response,JSONObject.toJSONString(error));
			return ;
		}
		Map m = (Map) l.get(0);
		output(response,JSONObject.toJSONString(m));
	}

}

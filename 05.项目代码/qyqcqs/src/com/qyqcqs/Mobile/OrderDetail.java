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
import com.qyqcqs.common.Constant;
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;

public class OrderDetail extends Mobile {

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
				//校验参数
				Map ending = Utils.toCheckParam(Constant.QUERY_ORDER_DETAIL,
						Constant.QUERY_ORDER_DETAIL_DESC, param);
				if (!(Boolean) ending.get("success")) {
					output(response,JSONObject.toJSONString(ending));
					return ;
				}
				
				String order_id = request.getParameter("order_id");
				//查询订单详细信息
				ConnectionDB conn = new ConnectionDB();
				String sql = "select a.*,b.order_description,c.user_name "+
							 "from orders a,order_detail b,user c "+
							 "where a.order_id=b.order_id and c.user_id=a.post_doctor_id and a.order_id = ?";
				String params[] = new String[1];
				params[0] = order_id;
				List l = conn.query(sql, params);
				if(null == l|| l.size()==0){
					Map error = new HashMap();
					error.put("error", "5");
					error.put("msg", "订单不存在");
					output(response,JSONObject.toJSONString(error));
					return ;
				}
				Map m = (Map) l.get(0);
				output(response,JSONObject.toJSONString(m));
	}

}

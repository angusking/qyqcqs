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
import com.qyqcqs.EaseMob.AddFriend;
import com.qyqcqs.common.Constant;
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;

public class TakeOrder extends Mobile {

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
		Map ending = Utils.toCheckParam(Constant.TAKE_ORDER,
				Constant.TAKE_ORDER_DESC, param);
		if (!(Boolean) ending.get("success")) {
			output(response,JSONObject.toJSONString(ending));
			return ;
		}
		
		String order_id = request.getParameter("order_id");
		String user_id = request.getParameter("user_id");
		String post_doctor_id = request.getParameter("post_doctor_id");
		
		//数据库添加接单信息
		ConnectionDB conn = new ConnectionDB();
		String sql = "update orders set take_doctor_id = ?, order_take_time = now() where order_id = ? ";
		String params[] = new String[2];
		params[0] = user_id;
		params[1] = order_id;
		int result = conn.executeUpdate(sql, params);
		if(result!=1){
			Map error = new HashMap();
			error.put("error", "6");
			error.put("msg", "接单出错");
			output(response,JSONObject.toJSONString(error));
			return ;
		}else{
			//给两个人添加好友
			Boolean addRe = AddFriend.addFriend(user_id, post_doctor_id);
			if(!addRe){
				Map error = new HashMap();
				error.put("error", "6");
				error.put("msg", "接单出错");
				output(response,JSONObject.toJSONString(error));
			}else{
				Map re = new HashMap();
				re.put("msg", "success");
				output(response,JSONObject.toJSONString(re));
			}
			return ;
		}
	}

}

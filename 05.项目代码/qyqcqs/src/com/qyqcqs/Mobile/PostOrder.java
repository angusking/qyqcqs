package com.qyqcqs.Mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.common.Constant;
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;


public class PostOrder extends Mobile {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取传入参数
		Map param = Utils.getValidateParam(request);
		//校验参数
		Map ending = Utils.toCheckParam(Constant.POST_ORDER,
				Constant.POST_ORDER_DESC, param);
		if (!(Boolean) ending.get("success")) {
			output(response,JSONObject.toJSONString(ending));
			return ;
		}
		
		String user_id = request.getParameter("user_id");
		String dis_type = request.getParameter("dis_type");
		String order_name = request.getParameter("order_name");
		String order_description = request.getParameter("order_description");
		String file_id = request.getParameter("file_id");
		String money = request.getParameter("money");

		// 查询执行新增的存储过程
		ConnectionDB conn = new ConnectionDB();
		
		String params[] = new String[6];
		params[0] = order_name;
		params[1] = user_id;
		params[2] = dis_type;
		params[3] = order_description;
		params[4] = file_id;
		params[5] = money;
		int l = 0;
		Object code = conn.excuteQuery("{call post_order(?,?,?,?,?,?,?)}",params,7,java.sql.Types.VARCHAR);
		l = Integer.valueOf(code.toString());
		
		if (l!=0) {
			Map error = new HashMap();
			error.put("error", "3");
			error.put("msg", "异常3");
			output(response, JSONObject.toJSONString(error));
			return;
		}else{
			Map result = new HashMap();
			result.put("msg", "success");
			output(response, JSONObject.toJSONString(result));
		}
	}
}

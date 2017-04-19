package com.qyqcqs.Mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.EaseMob.AddFriend;
import com.qyqcqs.common.Constant;
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;

public class EvaluateOrder extends Mobile {

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
				Map ending = Utils.toCheckParam(Constant.Evaluate_ORDER,
						Constant.Evaluate_ORDER_DESC, param);
				if (!(Boolean) ending.get("success")) {
					output(response,JSONObject.toJSONString(ending));
					return ;
				}
				
				String order_id = request.getParameter("order_id");
				String user_id = request.getParameter("user_id");
				String evaluation_level = request.getParameter("evaluation_level");
				String evaluation_detail = request.getParameter("evaluation_detail");
				String speed_level = request.getParameter("speed_level");
				String professional_level = request.getParameter("professional_level");
				String patience_level = request.getParameter("patience_level");
				String practical_level = request.getParameter("practical_level");
				
				//数据库添加订单评价信息
				ConnectionDB conn = new ConnectionDB();
				String params[] = new String[8];
				params[0] = order_id;
				params[1] = user_id;
				params[2] = evaluation_level;
				params[3] = evaluation_detail;
				params[4] = speed_level;
				params[5] = professional_level;
				params[6] = patience_level;
				params[7] = practical_level;
				int l = 0;
				Object code = conn.excuteQuery("{call evaluate_order(?,?,?,?,?,?,?,?,?)}",params,9,java.sql.Types.VARCHAR);
				l = Integer.valueOf(code.toString());
				if(l!=0){
					Map error = new HashMap();
					error.put("error", "7");
					error.put("msg", "评价出错");
					output(response,JSONObject.toJSONString(error));
					return ;
				}else{
					HashMap<String, String> re = new HashMap<String,String>();
					re.put("msg", "操作完成");
					output(response,JSONObject.toJSONString(re));
				}
	}

}

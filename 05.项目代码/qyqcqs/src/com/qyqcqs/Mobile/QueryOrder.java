package com.qyqcqs.Mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.common.Constant;
import com.qyqcqs.common.Utils;
import com.qyqcqs.database.ConnectionDB;

public class QueryOrder extends Mobile {

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
				String order_id = request.getParameter("order_id");
				String post_doctor_id = request.getParameter("post_doctor_id");
				String acp_doctor_id = request.getParameter("acp_doctor_id");
				String dis_type = request.getParameter("dis_type");
				StringBuffer sb = new StringBuffer("");
				sb.append("select order_id, (select dic_param from dictionary where dic_code = 'disease_type' and dic_value = a.disease_type) disease_type, "+
				"order_name,money from orders a where 1=1 and take_doctor_id is null ");
				List pl = new ArrayList();
				int index = 0;
				if(null!=order_id&&!"".equals(order_id)){
					sb.append("and order_id = ?");
					pl.add(index, order_id);
					index++;
				}
				if(null!=post_doctor_id&&!"".equals(post_doctor_id)){
					sb.append("and post_doctor_id = ?");
					pl.add(index, post_doctor_id);
					index++;
				}
				if(null!=acp_doctor_id&&!"".equals(acp_doctor_id)){
					sb.append("and acp_doctor_id = ?");
					pl.add(index, acp_doctor_id);
					index++;
				}
				if(null!=dis_type&&!"".equals(dis_type)){
					sb.append("and disease_type = ?");
					pl.add(index, dis_type);
					index++;
				}
				// 查询用户个人信息
				ConnectionDB conn = new ConnectionDB();
				String sql = sb.toString();
				String params[] = new String[pl.size()];
				for(int i=0;i<pl.size();i++){
					params[i]=(String) pl.get(i);
				}
				List l = conn.query(sql, params);
				if(null == l|| l.size()==0){
					Map error = new HashMap();
					error.put("error", "4");
					error.put("msg", "未查询到数据");
					output(response,JSONObject.toJSONString(error));
					return ;
				}
				output(response,JSONObject.toJSONString(l));
	}

}

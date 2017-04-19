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

public class GetDiseaseType extends Mobile {

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
				ConnectionDB conn = new ConnectionDB();
				String sql = "select dic_value,dic_param "+
"from dictionary where dic_code='disease_type' and length(dic_value)=3 "+ 
"and dic_value like 'A%'";
				String params[] = new String[1];
				List l = conn.query(sql, null);
				if(null == l|| l.size()==0){
					Map error = new HashMap();
					error.put("error", "4");
					error.put("msg", "疾病类型查询出错");
					output(response,JSONObject.toJSONString(error));
					return ;
				}
				Map m = (Map) l.get(0);
				output(response,JSONObject.toJSONString(m));
	}

}

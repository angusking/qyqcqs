package com.qyqcqs.Mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.EaseMob.EaseMob;
import com.qyqcqs.database.ConnectionDB;

public class GetFriends extends Mobile {

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
		//根据用户Id获取服务器上用户好友列表
		String username = request.getParameter("user_id");
		if(null == username ||username.equals("")){
			output(response,null);
			return ;
		}
		//向环信发送请求，获取好友列表
		JSONObject jo = EaseMob.getFriends(username);
		List ids = (List) jo.get("data");
		List friendsList = new ArrayList();
		Map friend = new HashMap();
		//根据返回数据，查询好友数据
		ConnectionDB db = new ConnectionDB();
		String sql = "select a.hospital_name,b.dept_name,c.user_name,c.user_title "+
					"from hospital a,dept b,user c,user_system d "+
					"where d.user_id=c.user_id and a.hospital_id=d.hospital_id "+
					"and b.dept_id=d.dept_id and c.user_id= ? ";
		String params[] = new String[1];
		for(int i=0;i<ids.size();i++){
			friend.clear();
			try{
				params[0] = ids.get(i).toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			//将查询到的数据添加到好友list中			
			friendsList.addAll(db.query(sql, params));
		}
		
		//返回好友列表
		output(response,JSONObject.toJSONString(friendsList));
	}

}

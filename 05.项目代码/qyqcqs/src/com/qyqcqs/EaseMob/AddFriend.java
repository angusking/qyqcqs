package com.qyqcqs.EaseMob;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.common.HttpRequest;

public class AddFriend extends EaseMob {
	public static Boolean addFriend(String owner_username,String friend_username){
		//请求好友列表地址
		String url = "https://a1.easemob.com/angusking/jiankangtaiyuan/users/"+owner_username+"/contacts/users/"+friend_username;
		//添加token
		String token = "Bearer "+getToken();
		Map<String,String> pro =new HashMap<String,String>();
		pro.put("Authorization", token);
		pro.put("Content-Type"," application/json");
		String result = HttpRequest.easePost(url,null, pro, "");		
		//返回数据
		JSONObject jo = JSONObject.parseObject(result);
		String sr = jo.get("entities").toString();
		JSONArray j = JSONArray.parseArray(sr);
		String s = j.get(0).toString();
		JSONObject rrr = JSONObject.parseObject(s);
		return (Boolean) rrr.get("activated");
	}
}

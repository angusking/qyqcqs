package com.qyqcqs.EaseMob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.common.HttpRequest;

public class EaseMob {

	//获取环信服务端token
	public static String getToken(){
        
        //请求参数 ease需要的
        JSONObject jo = new JSONObject();
        jo.put("grant_type", "client_credentials");
        jo.put("client_id", "YXA6IMBUwB_GEeaFrW3GtHEoOA");
        jo.put("client_secret", "YXA6tqTGh1nMJN8Exw67XTi4KbrxkjQ");
        String url = "https://a1.easemob.com/angusking/jiankangtaiyuan/token";
        
        //设置http参数
        Map<String,String> pro =new HashMap<String,String>();
        pro.put("Content-Type"," application/json");
        String result = HttpRequest.easePost(url, null, pro, jo.toJSONString());
        
		JSONObject responseJO = JSONObject.parseObject(result);
		
		return responseJO.getString("access_token");
	}
	
	public static JSONObject getFriends(String username){
		//请求好友列表地址
		String url = "https://a1.easemob.com/angusking/jiankangtaiyuan/users/"+username+"/contacts/users";
		//添加token
		String token = "Bearer "+getToken();
		Map<String,String> pro =new HashMap<String,String>();
		pro.put("Authorization", token);
		String result = HttpRequest.easeGet(url, pro, "");		
		//返回数据
		return JSONObject.parseObject(result);
	}
}

package com.qyqcqs.EaseMob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qyqcqs.common.HttpRequest;

public class AddUser extends EaseMob {
	public static Boolean addUser(String username,String password){
		//请求好友列表地址
		String url = "https://a1.easemob.com/angusking/jiankangtaiyuan/users";
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
	public static List addUsers(List users){
		//请求好友列表地址
		String url = "https://a1.easemob.com/angusking/jiankangtaiyuan/users";
		//请求参数 ease需要的
        String ja = JSONArray.toJSONString(users,SerializerFeature.DisableCircularReferenceDetect);
		//添加token
		String token = "Bearer "+getToken();
		Map<String,String> pro =new HashMap<String,String>();
		pro.put("Authorization", token);
		pro.put("Content-Type"," application/json");
		String result = HttpRequest.easePost(url,null, pro, ja);		
		//返回数据
		JSONObject jo = JSONObject.parseObject(result);
		String sr = jo.get("entities").toString();
		JSONArray j = JSONArray.parseArray(sr);
		List resultList = new ArrayList();
		for(int i=0;i<j.size();i++){
			JSONObject ro = j.getJSONObject(i);
			resultList.add(ro.get("activated"));
		}
		String s = j.get(0).toString();
		JSONObject rrr = JSONObject.parseObject(s);
		return resultList;
	}
}

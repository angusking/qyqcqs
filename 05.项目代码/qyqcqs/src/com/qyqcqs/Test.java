package com.qyqcqs;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qyqcqs.EaseMob.AddFriend;
import com.qyqcqs.EaseMob.EaseMob;
import com.qyqcqs.common.HttpRequest;

public class Test {
	 public static void main(String[] args) {
	        //发送 GET 请求
//	        String s=HttpRequest.sendGet("http://localhost:8080/qyqcqs/servlet/Register", "userid=111");
//	        System.out.println(s);
	        
	        //发送 POST 请求
	       // String sr=HttpRequest.sendPost("http://localhost:8080/qyqcqs/servlet/Register", "userid=111");
	       
//			JSONObject jo = AddFriend.addFriend("1", "2");
//			String sr = jo.get("entities").toString();
//			JSONArray j = JSONArray.parseArray(sr);
//			String s = j.get(0).toString();
//			JSONObject rrr = JSONObject.parseObject(s);
	        System.out.println(AddFriend.addFriend("1", "2"));
	    }
}

package com.qyqcqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.qyqcqs.EaseMob.AddUser;

public class InitUsers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, String>> l = new ArrayList<Map<String,String>>();
		//因为数据大小限制，所以只能较少数量
		for(int i=141;i<=148;i++){
			HashMap<String, String> m = new HashMap<String,String>();
			m.put("username", String.valueOf(i));
			m.put("password", String.valueOf(1));
			
			l.add(m);
		}
		List rl = AddUser.addUsers(l);
		System.out.println(rl.toString());
	}

}

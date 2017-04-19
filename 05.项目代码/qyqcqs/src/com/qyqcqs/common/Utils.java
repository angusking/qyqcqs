package com.qyqcqs.common;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class Utils {
	/**
	 * 获取通过验证的参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map getValidateParam(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map param = new HashMap();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			param.put(name, WebUtils.scriptFilter(request.getParameter(name)));
		}
		return param;
	}
	/**
	 * 
	 * 校验参数
	 * 
	 * **/
	public static Map toCheckParam(String[] needParam,String[] needParamDesc,Map param) {
		Map result = new HashMap();
		result.put("success", true);
		String key;
		Object value;
		for (int i = 0; i < needParam.length; i++) {
			key = needParam[i];
			value = param.get(key);
			if(value == null || "".equals(value) ){
				result.put("msg", "请传入"+needParamDesc[i]+"对应的参数");
				result.put("success", false);
				result.put("appcode", "error01");
				break;
			}
		}
		return result;
	}
}

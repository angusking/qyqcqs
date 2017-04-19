package com.qyqcqs.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public abstract class WebUtils  {
	
	public static boolean isAjax(HttpServletRequest request){
		return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
	} 
	
	public static String removeSpecString(String str) throws Exception {
		if (str == null) {
			return str;
		}
		String regEx = "[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s\\/]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String removeSpecStringSigle(String str) throws Exception {
		if (str == null) {
			return str;
		}
		String regEx = "[\\[\\]<>\\s\\/]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String scriptFilter(String str){
		if(str!=null&&!"".equals(str)){
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
			Pattern p = Pattern.compile(regEx_script);
			Matcher m = p.matcher(str);
			return m.replaceAll(" ").trim();
		}else{
			return str;
		}
	}
	
	/**
	 * 
	 * @Description: write json
	 * @param object
	 * @param response
	 * @throws IOException
	 */
	public static void write(Object object, HttpServletResponse response) throws IOException {
		String result = "";
		if (object instanceof Map || object instanceof List){
			result = JSON.toJSONString(object);
		} else if (object instanceof String || object instanceof StringBuffer || object instanceof StringBuilder  ){
			result = object.toString();
		} else {
			result = JSON.toJSONString(object);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
	}
}

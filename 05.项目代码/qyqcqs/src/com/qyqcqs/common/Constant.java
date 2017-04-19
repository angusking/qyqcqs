package com.qyqcqs.common;

public interface Constant {
	/*发单需要参数*/
	String[] POST_ORDER = {"user_id","dis_type","order_name","order_description","money"};
	/*发单参数对应中文*/
	String[] POST_ORDER_DESC = {"用户ID","疾病科室","订单名称","订单描述","金额"};
	/*查询单子需要参数*/
	String[] QUERY_ORDER = {"dis_type"};
	/*查询单子参数对应中文*/
	String[] QUERY_ORDER_DESC = {"疾病科室"};
	/*查询订单详情*/
	String[] QUERY_ORDER_DETAIL = {"order_id"};
	/*查询订单详情对应中文*/
	String[] QUERY_ORDER_DETAIL_DESC = {"订单ID"};
	/*接单*/
	String[] TAKE_ORDER = {"user_id","order_id"};
	/*接单中文*/
	String[] TAKE_ORDER_DESC = {"用户ID","订单ID"};
	/*评价*/
	String[] Evaluate_ORDER = {"user_id","order_id","evaluation_level","evaluation_detail","speed_level","professional_level","practical_level"};
	/*评价中文*/
	String[] Evaluate_ORDER_DESC = {"用户ID","订单ID","评价等级","评价详情","答复速度","专业程度","实用程度"};
	/*评价*/
	String[] QUERY_CURRENT_ORDER = {"user_id"};
	/*评价中文*/
	String[] QUERY_CURRENT_ORDER_DESC = {"用户ID"};
}

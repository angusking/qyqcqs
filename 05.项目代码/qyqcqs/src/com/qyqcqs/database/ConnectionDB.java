package com.qyqcqs.database;

import java.sql.CallableStatement;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
/** 
 * 数据库连接类 
 * 说明:封装了 无参，有参，存储过程的调用 
 * @author iflytek 
 * 
 */  
public class ConnectionDB {  
  
    /** 
     * 数据库驱动类名称 
     */  
    private static final String DRIVER = "com.mysql.jdbc.Driver";  
  
    /** 
     * 连接字符串 
     */  
    private static final String URLSTR = "jdbc:mysql://101.201.47.110:3306/qyqcqs?useUnicode=true&characterEncoding=utf8&useSSL=true";  
  
    /** 
     * 用户名 
     */  
    private static final String USERNAME = "qyqcqs_db";  
  
    /** 
     * 密码 
     */  
    private static final String USERPASSWORD = "DB_619Project";  
  
    /** 
     * 创建数据库连接对象 
     */  
    private Connection connnection = null;  
  
    /** 
     * 创建PreparedStatement对象 
     */  
    private PreparedStatement preparedStatement = null;  
      
    /** 
     * 创建CallableStatement对象 
     */  
    private CallableStatement callableStatement = null;  
  
    /** 
     * 创建结果集对象 
     */  
    private ResultSet resultSet = null;  
  
    static {  
        try {  
            // 加载数据库驱动程序  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
            System.out.println("加载驱动错误");  
            System.out.println(e.getMessage());  
        }  
    }  
  
    /** 
     * 建立数据库连接 
     * @return 数据库连接 
     */  
    public Connection getConnection() {  
        try {  
            // 获取连接  
            connnection = DriverManager.getConnection(URLSTR, USERNAME,  
                    USERPASSWORD);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return connnection;  
    }  
  
    public List query(String sql,String[] params){
    	List reList = new ArrayList();
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	connnection = this.getConnection();  
    	try{
    		
    		pstmt = connnection.prepareStatement(sql);
    		if (params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setString(i + 1, params[i]);
				}
			}
    		rs = pstmt.executeQuery();
    		  // 结果集列数  
            int columnCount = 0;  
                // 获得结果集列数  
            columnCount = rs.getMetaData().getColumnCount();  

    		while (rs.next()) {
				Map map = new HashMap();
				for (int i = 1; i <= columnCount; i++) {  
                    map.put(rs.getMetaData().getColumnLabel(i), rs.getString(i));  
                }

				reList.add(map);
			}
    	}catch(SQLException e){
    		System.out.println(e.getMessage());
    	}finally{
    		 closeAll();  
    	};
    	
    	return reList;
    }
    /** 
     * insert update delete SQL语句的执行的统一方法 
     * @param sql SQL语句 
     * @param params 参数数组，若没有参数则为null 
     * @return 受影响的行数 
     */  
    public int executeUpdate(String sql, Object[] params) {  
        // 受影响的行数  
        int affectedLine = 0;  
          
        try {  
            // 获得连接  
            connnection = this.getConnection();  
            // 调用SQL   
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 参数赋值  
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 执行  
            affectedLine = preparedStatement.executeUpdate();  
  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // 释放资源  
            closeAll();  
        }  
        return affectedLine;  
    }  
    /** 
     * 存储过程带有一个输出参数的方法 
     * @param sql 存储过程语句 
     * @param params 参数数组 
     * @param outParamPos 输出参数位置 
     * @param SqlType 输出参数类型 
     * @return 输出参数的值 
     */  
     public Object excuteQuery(String sql, Object[] params,int outParamPos, int SqlType) {  
        Object object = null;  
        connnection = this.getConnection();  
        try {  
            // 调用存储过程  
            callableStatement = connnection.prepareCall(sql);  
              
            // 给参数赋值  
            if(params != null) {  
                for(int i = 0; i < params.length; i++) {  
                    callableStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 注册输出参数  
            callableStatement.registerOutParameter(outParamPos, SqlType);  
              
            // 执行  
            callableStatement.execute();  
              
            // 得到输出参数  
            object = callableStatement.getObject(outParamPos);  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // 释放资源  
            closeAll();  
        }  
          
        return object;  
    }  
  
    /** 
     * 关闭所有资源 
     */  
    private void closeAll() {  
        // 关闭结果集对象  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        // 关闭PreparedStatement对象  
        if (preparedStatement != null) {  
            try {  
                preparedStatement.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
          
        // 关闭CallableStatement 对象  
        if (callableStatement != null) {  
            try {  
                callableStatement.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        // 关闭Connection 对象  
        if (connnection != null) {  
            try {  
                connnection.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }     
    }  
}  
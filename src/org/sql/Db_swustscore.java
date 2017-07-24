package org.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.SwustDb;

public class Db_swustscore {
	static Connection conn;
	static Statement st;
	
	public static Connection getconnection(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/db_swustscore?useUnicode=true&characterEncoding=utf-8&useSSL=false";
			String username = "root";
			String password = "lian1998";
			
			con = DriverManager.getConnection(url,username,password);
			System.out.println("数据连接成功");
		}catch(Exception e){
			System.out.println("获取连接失败" + e.getMessage());
		}
		return con;
	}
	
	public static void insert(ArrayList<SwustDb> list){
		conn = getconnection();
		try{
			String sql = "INSERT INTO db_swustscore(name,num,grade,classchar,score,gdp)"
					+ "VALUES(?,?,?,?,?,?)";
			PreparedStatement prem = conn.prepareStatement(sql);
			SwustDb temp = null;
			for(int i=0;i<list.size();i++){
				temp = list.get(i);
				prem.setString(1, temp.getClassName());
				prem.setString(2, temp.getClassNum());
				prem.setString(3, temp.getClassGrade());
				prem.setString(4, temp.getClassScore());
				prem.setString(5, temp.getClassScore());
				prem.setString(6, temp.getClassGDP());
				
				System.out.println("插入了" + prem.executeUpdate() + "个成绩");
			}
			
			
			prem.close();
			
			conn.close();

		}catch(Exception e){
			System.out.println("插入失败" + e.getMessage());
		}
	}
	
	public static boolean query(String classname){
		// 同样先要获取连接，即连接到数据库
		conn = getconnection();
		try{
			// 查询数据的sql语句
			String sql = "select * from db_swustscore";
			// 创建用于执行静态sql语句的Statement对象，st属局部变量
			st = conn.createStatement();
			// 执行sql查询语句，返回查询数据的结果集
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){// 判断是否还有下一个数据
				// 根据字段名获取相应的值
				if(classname.equals(rs.getString("name"))){
					return true;
				}
			}
			//释放所连接的数据库及资源
			st.close();
			conn.close();
		}catch(SQLException e){
			System.out.println("查询数据失败");
		}
		return false;
	}
}

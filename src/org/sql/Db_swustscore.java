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
			System.out.println("�������ӳɹ�");
		}catch(Exception e){
			System.out.println("��ȡ����ʧ��" + e.getMessage());
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
				
				System.out.println("������" + prem.executeUpdate() + "���ɼ�");
			}
			
			
			prem.close();
			
			conn.close();

		}catch(Exception e){
			System.out.println("����ʧ��" + e.getMessage());
		}
	}
	
	public static boolean query(String classname){
		// ͬ����Ҫ��ȡ���ӣ������ӵ����ݿ�
		conn = getconnection();
		try{
			// ��ѯ���ݵ�sql���
			String sql = "select * from db_swustscore";
			// ��������ִ�о�̬sql����Statement����st���ֲ�����
			st = conn.createStatement();
			// ִ��sql��ѯ��䣬���ز�ѯ���ݵĽ����
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){// �ж��Ƿ�����һ������
				// �����ֶ�����ȡ��Ӧ��ֵ
				if(classname.equals(rs.getString("name"))){
					return true;
				}
			}
			//�ͷ������ӵ����ݿ⼰��Դ
			st.close();
			conn.close();
		}catch(SQLException e){
			System.out.println("��ѯ����ʧ��");
		}
		return false;
	}
}

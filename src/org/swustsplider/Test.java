package org.swustsplider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import org.sql.Db_swustscore;
public class Test {
	public static void main(String []args) throws IOException{
		Html html = new Html();
		String url;
		Scanner in = new Scanner(System.in);
		//�ֶ��ڿ���̨�������ϿƼ���ѧ����
		url = in.nextLine();
		//url = "https://matrix.dean.swust.edu.cn/acadmicManager/index.cfm?event=studentProfile:courseMark";
		//ÿ����Ҫ�ֶ����� �Ϸ�url ���߰�ע��ȡ������Scanner��ע�͡�
		Connection connection = Db_swustscore.getconnection();
		html.getProbince("indexde", url, "cellbord");
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//html.getProbince("indexde", url, "img");
	}
}

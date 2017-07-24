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
		//手动在控制台输入西南科技大学教务处
		url = in.nextLine();
		//url = "https://matrix.dean.swust.edu.cn/acadmicManager/index.cfm?event=studentProfile:courseMark";
		//每次需要手动输入 上方url 或者把注释取消，将Scanner类注释。
		Connection connection = Db_swustscore.getconnection();
		html.getProbince("indexde", url, "cellbord");
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//html.getProbince("indexde", url, "img");
	}
}

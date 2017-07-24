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
		url = in.nextLine();
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

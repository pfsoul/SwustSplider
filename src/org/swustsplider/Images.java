package org.swustsplider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Images {
	public static void downImages(String filepath,String imgurl) throws UnsupportedEncodingException{
		//图片url中的前面部分：例如“http://images.csdn.net/”
		String beforeUrl = imgurl.substring(0,imgurl.lastIndexOf("/")+1);
		//图片url中的后面部分：例如“20150529/PP6A7429.jpg”
		String fileName = imgurl.substring(imgurl.lastIndexOf("/")+1);
		//编码之后的fileName，空格会变成字符‘+’
		String newFileName = URLEncoder.encode(fileName,"UTF-8");
		//把编码之后的fileName中的字符"+”，替换为UTF-8的空格表示：“%20”
		newFileName = newFileName.replaceAll("\\+", "\\%20");
		//编码之后的url
		imgurl = beforeUrl + newFileName;
		
		try{
			//创建文件目录
			File files = new File(filepath);
			if(!files.exists()){
				files.mkdirs();//即使不存在父目录也创建
			}
			//获取下载地址
			URL url = new URL(imgurl);
			//链接网络地址
			HttpURLConnection connnection = (HttpURLConnection)url.openConnection();
			//获取链接的输出流
			InputStream is = connnection.getInputStream();
			//创建文件，fileName为编码之前的文件名
			File file = new File(filepath + "/" + fileName);
			System.out.println(file);
			//根据输入流写入文件
			FileOutputStream out = new FileOutputStream(file);
			
			int i = 0;
			while((i = is.read()) != -1){
				out.write(i);
			}
			out.close();
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

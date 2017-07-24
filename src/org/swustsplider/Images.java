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
		//ͼƬurl�е�ǰ�沿�֣����硰http://images.csdn.net/��
		String beforeUrl = imgurl.substring(0,imgurl.lastIndexOf("/")+1);
		//ͼƬurl�еĺ��沿�֣����硰20150529/PP6A7429.jpg��
		String fileName = imgurl.substring(imgurl.lastIndexOf("/")+1);
		//����֮���fileName���ո�����ַ���+��
		String newFileName = URLEncoder.encode(fileName,"UTF-8");
		//�ѱ���֮���fileName�е��ַ�"+�����滻ΪUTF-8�Ŀո��ʾ����%20��
		newFileName = newFileName.replaceAll("\\+", "\\%20");
		//����֮���url
		imgurl = beforeUrl + newFileName;
		
		try{
			//�����ļ�Ŀ¼
			File files = new File(filepath);
			if(!files.exists()){
				files.mkdirs();//��ʹ�����ڸ�Ŀ¼Ҳ����
			}
			//��ȡ���ص�ַ
			URL url = new URL(imgurl);
			//���������ַ
			HttpURLConnection connnection = (HttpURLConnection)url.openConnection();
			//��ȡ���ӵ������
			InputStream is = connnection.getInputStream();
			//�����ļ���fileNameΪ����֮ǰ���ļ���
			File file = new File(filepath + "/" + fileName);
			System.out.println(file);
			//����������д���ļ�
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

package org.swustsplider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.*;
import org.jsoup.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sql.Db_swustscore;

import db.SwustDb;

import java.io.IOException;

public class Html {
	ArrayList<SwustDb> list = new ArrayList();
	//����url�������ȡ��ҳ�ı�
	public Document getHtmlTextByUrl(String url){
		Document doc = null;
		//System.out.println("1");
		try{
			int i = (int) (Math.random()*1000);
			while(i != 0){
				i--;
			}
			/*
			 * ʹ��ʱ����Ҫ�Լ������Լ���cookie��
			 */
			Map<String,String> cookies = null;
			cookies = new HashMap<String,String>();
			cookies.put("UM_distinctid", "15d064da968c5-0fd8cfa5cb8f47-36624308-144000-15d064da969142");
			cookies.put("iPlanetDirectoryPro", "AQIC5wM2LY4SfcxYUSHvyCEgXVHOFBAivuIrwUke7s3CCsU%3D%40AAJTSQACMDE%3D%23");
			cookies.put("SSO", "TGC%2D75fbd0e7f185a01d033a0c3f9fea4a20eab8cb3fbb229b706e78e025dc49bc3b149b16da56e8bcf9f60d2d92c767ad016651b3a2a2b64bd234f0c1c1ce6b1e9940594164e59b1804ed075ab4dc2a399556fe06a01fe00758298e5148bdb06516f90dc72eb52dbc9904da2ac1ca473a0a736d4fdceacd4d07a92d1863c97344df7fe908851ee20fd15b0cd0e93e3a03cf0b5b6075c22a1a8b6474a7c6b7e54ec23b9b80a399eebb5d23da8f71b494423fad158de4935e7ecd465027ab006657adf02098686d6c2a606ff1133390d7e610fcd4ca83ef0f5d176fb4617ca65dd2da7308c2877004250fb30af5bab599ee391438895b0b0a9d2c");
			doc = (Document) Jsoup.connect(url).userAgent("Mozilla").cookies(cookies).timeout(30000).post();
		}catch(IOException e){
			e.printStackTrace();
			try{
				doc = (Document) Jsoup.connect(url).timeout(5000000).get();
			}catch(IOException el){
				el.printStackTrace();
			}
		}
		return doc;
	}
	//���ݱ���·����ȡ��ҳ�ı�����������ھ�ͨ��url�������ȡ
	public Document getHtmlTextByPath(String name,String url){
		String path = "D:/Html/" + name + ".html";
		Document doc = null;
		File input = new File(path);    //inputΪ��ʱhtml�ļ�
		String urlcat = url;
		try{
			doc = (Document) Jsoup.parse(input,"GBK");  //��input�ļ�������һ���µ��ļ�
			if(!((Element) doc).children().isEmpty()){
				//doc = null;
				System.out.println("�Ѿ�����");
			}
		}catch(IOException e){
			System.out.println("�ļ�δ�ҵ������ڴ������ȡ...");
			doc = this.getHtmlTextByUrl(url);
			//���ұ����ڱ���
			this.Save_Html(url,name);
		}
		
		return doc;
	}
	
	public void Save_Html(String url,String name){
		try{
			name = name+".html";
			File dest = new File("D:/Html/"+name);  //Ŀ���ļ�
			//�����ֽ�������
			InputStream is;
			//�ֽ������
			FileOutputStream fos = new FileOutputStream(dest);
			
			URL temp = new URL(url);
			is = temp.openStream();
			
			//Ϊ�ֽ��������ӻ���
			BufferedInputStream bis = new BufferedInputStream(is);
			//Ϊ�ֽ�������ӻ���
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			int length;
			
			byte[] bytes = new byte[1024*20];
			while((length = bis.read(bytes, 0 ,bytes.length)) != -1){
				fos.write(bytes, 0, bytes.length);
			}
			bos.close();
			fos.close();
			bis.close();
			is.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Elements getEleByClass(Document doc,String className){
		Elements elements = null;
		elements = (Elements) doc.select(className);
		return elements;   //�˴����ص�������tr�ļ���
	}
	
	public ArrayList getProbince(String name,String url, String type) throws IOException{
		File file = new File("C:/Users/15pr/Desktop/demo.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		ArrayList result = new ArrayList();
		String classtype = "tr." + type +"er";
		//�������ϻ�ȡ��ҳ
		Document doc2 = this.getHtmlTextByUrl(url);
		//�ӱ��ػ�ȡ��ҳ�����û����������ϻ�ȡ
		//Document doc2 = this.getHtmlTextByPath(name, url);
		Db_swustscore dbAction = new Db_swustscore();
		if(doc2 != null){
			
			if(type.equals("img")){
				try {
					this.image(doc2);
				} catch (UnsupportedEncodingException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				return null;
			}
			Elements es = this.getEleByClass(doc2, classtype);
			for(Element e : es){              //����ѭ��ÿ��Ԫ�أ�Ҳ����һ��tr
				if(e != null){
					int count = 1;
					String str = "";
					for(Element ec : e.children()){      //һ��trԪ�ص�td��td�ڰ���a��ǩ
						
						String contant = ec.text();
						//System.out.println(contant);
						if(!contant.equals("")){
							str = str + contant + ",";
							count ++;
						}
						
						
						if(count == 7){
							String ch[] = str.split(",");
							SwustDb db = new SwustDb();
							db.setClassName(ch[0]);
							db.setClassNum(ch[1]);
							db.setClassGrade(ch[2]);
							db.setClassChar(ch[3]);
							db.setClassScore(ch[4]);
							db.setClassGDP(ch[5]);
							if(dbAction.query(ch[0])){
								continue;
							}
							list.add(db);
							output.write(str);
						}
						
						/*String[] prv = new String[4];    //��ݵ���Ϣ��ԭ����url(��ǰurl)  ���� ����url ����
						//System.out.println(ec);
						//if(ec.children().first() != null){ //td��Ϊ��
							//ԭ����url
							prv[0] = url;
							//�����Ϣ
							//System.out.println(ec.children().first().ownText());
							prv[1] = ec.children().first().ownText();
							if(prv[1].equals(""));
							System.out.println(prv[1]);
							output.write(prv[1]);
							//���url��ַ
							String ownurl =ec.children().first().attr("abs:href");
							//��Ϊ����ӱ��ػ�ȡ�Ļ������Ϊ����url,���Ա�����һ�δ������ϵ�url,��֤url�����
							prv[2] = ownurl;
							//System.out.println(prv[2]);
							//����
							prv[3] = type;
							//��������ݼ���list
							result.add(prv);*/
						//}
						
					}
					output.write("\r\n");
				}
			}
		}
		output.close();
		dbAction.insert(list);
		return result ;
	}
	
	public void image(Document doc) throws UnsupportedEncodingException{
		Elements elements = doc.getElementsByTag("img");
		String filepath = "D:/Html/Images";
		for(Element element : elements){
			//��ȡÿ��img��ǩ�ϵ�src���Ե����ݣ�ͼƬ��ַ����abs����ʾ����·��
			String imgSrc = element.attr("abs:src");
			//����ͼƬ�ļ������Եı���Ӳ������
			Images images = new Images();
			System.out.println("��������ͼƬ" + imgSrc);
			images.downImages(filepath, imgSrc);
			System.out.println("ͼƬ�������" + imgSrc);
		}
		System.out.println("��������" + elements.size() + "���ļ�����ȥ�أ�");
	}
}

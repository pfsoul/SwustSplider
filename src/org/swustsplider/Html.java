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
			Map<String,String> cookies = null;
			cookies = new HashMap<String,String>();
			cookies.put("UM_distinctid", "15d064da968c5-0fd8cfa5cb8f47-36624308-144000-15d064da969142");
			cookies.put("iPlanetDirectoryPro", "AQIC5wM2LY4SfcxYUSHvyCEgXVHOFBAivuIrwUke7s3CCsU%3D%40AAJTSQACMDE%3D%23");
			cookies.put("SSO", "TGC%2D301160bcda674c8ef17dc5435ac5fe71996a7b0ed752bce6a3f54a981028a7b33e8e7a35081215a95efbb619c0f005cd99ab0f671d64edba85543861cec49f8e349c37f5591eb68f234e4b8431944afb913e2f8e39a1b88a88afc300745c7a74d105352bfcf90ffb900d428edaee77f6425e2d4b8b3f9f8ae3985168ecce3d5459309c65948cfb89d0173e1fca702a1aef5ca06ec4801437053cdb91ef3b3b8f62b55b2dbcfe4bed02cb90578db8decffa59985a7b5b1a7b298fe450f97b6874555ba33bdf566f1772c5b62a37528bbca5dfb6ad56b9eba6b5f82885d118ea0195b0ca8e72c475150683394f3a80c8d2702cfed44affe255");
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
		Db_swustscore db = new Db_swustscore();
		db.insert(list);
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

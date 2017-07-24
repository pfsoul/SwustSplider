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
	//根据url从网络获取网页文本
	public Document getHtmlTextByUrl(String url){
		Document doc = null;
		//System.out.println("1");
		try{
			int i = (int) (Math.random()*1000);
			while(i != 0){
				i--;
			}
			/*
			 * 使用时候需要自己输入自己的cookie。
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
	//根据本地路径获取网页文本，如果不存在就通过url从网络获取
	public Document getHtmlTextByPath(String name,String url){
		String path = "D:/Html/" + name + ".html";
		Document doc = null;
		File input = new File(path);    //input为此时html文件
		String urlcat = url;
		try{
			doc = (Document) Jsoup.parse(input,"GBK");  //将input文件解析成一个新的文件
			if(!((Element) doc).children().isEmpty()){
				//doc = null;
				System.out.println("已经存在");
			}
		}catch(IOException e){
			System.out.println("文件未找到，正在从网络获取...");
			doc = this.getHtmlTextByUrl(url);
			//并且保存在本地
			this.Save_Html(url,name);
		}
		
		return doc;
	}
	
	public void Save_Html(String url,String name){
		try{
			name = name+".html";
			File dest = new File("D:/Html/"+name);  //目的文件
			//接受字节输入流
			InputStream is;
			//字节输出流
			FileOutputStream fos = new FileOutputStream(dest);
			
			URL temp = new URL(url);
			is = temp.openStream();
			
			//为字节输入流加缓冲
			BufferedInputStream bis = new BufferedInputStream(is);
			//为字节输出流加缓冲
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
		return elements;   //此处返回的是所有tr的集合
	}
	
	public ArrayList getProbince(String name,String url, String type) throws IOException{
		File file = new File("C:/Users/15pr/Desktop/demo.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		ArrayList result = new ArrayList();
		String classtype = "tr." + type +"er";
		//从网络上获取网页
		Document doc2 = this.getHtmlTextByUrl(url);
		//从本地获取网页，如果没有则从网络上获取
		//Document doc2 = this.getHtmlTextByPath(name, url);
		Db_swustscore dbAction = new Db_swustscore();
		if(doc2 != null){
			
			if(type.equals("img")){
				try {
					this.image(doc2);
				} catch (UnsupportedEncodingException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				return null;
			}
			Elements es = this.getEleByClass(doc2, classtype);
			for(Element e : es){              //依次循环每个元素，也就是一个tr
				if(e != null){
					int count = 1;
					String str = "";
					for(Element ec : e.children()){      //一个tr元素的td，td内包含a标签
						
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
						
						/*String[] prv = new String[4];    //身份的信息，原来的url(当前url)  名称 现在url 类型
						//System.out.println(ec);
						//if(ec.children().first() != null){ //td不为空
							//原来的url
							prv[0] = url;
							//身份信息
							//System.out.println(ec.children().first().ownText());
							prv[1] = ec.children().first().ownText();
							if(prv[1].equals(""));
							System.out.println(prv[1]);
							output.write(prv[1]);
							//身份url地址
							String ownurl =ec.children().first().attr("abs:href");
							//因为如果从本地获取的话，会成为本地url,所以保留第一次从网络上的url,保证url不会空
							prv[2] = ownurl;
							//System.out.println(prv[2]);
							//级别
							prv[3] = type;
							//将所有身份加入list
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
			//获取每个img标签上的src属性的内容，图片地址，加abs：表示绝对路径
			String imgSrc = element.attr("abs:src");
			//下载图片文件到电脑的本地硬盘上来
			Images images = new Images();
			System.out.println("正在下载图片" + imgSrc);
			images.downImages(filepath, imgSrc);
			System.out.println("图片下载完毕" + imgSrc);
		}
		System.out.println("共下载了" + elements.size() + "个文件（不去重）");
	}
}

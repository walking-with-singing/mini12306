package Reptile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Test_Reptile.FetchFreeIps_test;



public class FetchFreeIps {
	FetchData fd;
	public FetchFreeIps() {
		fd=new FetchData();
	}
	public List<HttpHost> getProxys(String uri)
	{
		int len=uri.length();
		List<HttpHost> list=new ArrayList<>();
			/*
			get.setURI(new URI("https://www.kuaidaili.com/free/"));
			response=client.execute(get);
			entity=response.getEntity();
			System.out.println(EntityUtils.toString(entity,"UTF-8"));
			*/
			Document doc;
			for(int i=2;i<3;i++)
			{
				uri=uri.substring(0,len);
				uri+="inha/"+i+"/";
				//获取proxy
				String html=fd.getContent(uri);
				doc = Jsoup.parse(html);
				Elements table=doc.select("table.table-bordered");
				Elements trs=table.select("tr");
				for(Element tr:trs)
				{
					Elements tdip=tr.select("[data-title=\"IP\"]");
					Elements tdpost=tr.select("[data-title=\"PORT\"]");
					//System.out.println(tdip.text()+"\t"+tdpost.text());
					String ip=tdip.text();
					if(ip.length()==0)
						continue;
					int	port = Integer.valueOf(tdpost.text());
					HttpHost proxy=new HttpHost(ip,port);
					list.add(proxy);
				}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
				
			System.out.println("筛选前ip数："+list.size());	
			//筛选proxy
			CloseableHttpClient client=HttpClients.createDefault();
			HttpGet get=new HttpGet("https://www.baidu.com/");
			for(int i=0;i<list.size();i++)
			{
				HttpHost proxy=list.get(i);
				RequestConfig config=RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).setProxy(proxy).build();
				get.setConfig(config);
				try {
					CloseableHttpResponse response=client.execute(get);
					System.out.println("连接代理成功！请求状态码："+response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode()!=200)
						list.remove(i--);
					else
					{
						HttpEntity entity=response.getEntity();
						System.out.println(EntityUtils.toString(entity,"UTF-8"));
					}
				} catch (ClientProtocolException e) {
					System.out.println(0);
					e.printStackTrace();
				} catch (IOException e) {
					list.remove(i--);					
				}
			}

			

		return list;
	}
}

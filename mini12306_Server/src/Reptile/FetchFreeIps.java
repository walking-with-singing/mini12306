package Reptile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class FetchFreeIps {
	Logger logger=LogManager.getLogger();
	FetchData fd;
	public FetchFreeIps() {
		fd=new FetchData();
	}
	public List<HttpHost> getProxys()
	{
		String uri="http://www.xiladaili.com/gaoni/";
		int len=uri.length();
		List<HttpHost> list=new ArrayList<>();
			Document doc = null;
			for(int i=0;i<=0;i++)
			{
				uri=uri.substring(0,len);
				uri+=i+"/";
				//获取proxy
				//String html=fd.getContent(uri);
				try {
					doc = Jsoup.connect(uri).get();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Elements table=doc.select("table.fl-table");
				Elements trs=table.select("tr");
				for(Element tr:trs)
				{
					Elements tds=tr.select("td");
					if(tds.isEmpty())
						continue;
					String text=tds.get(0).text();
					String[] str=text.split(":");
					String ip=str[0];
					int	port = Integer.valueOf(str[1]);
					HttpHost proxy=new HttpHost(ip,port);
					list.add(proxy);
				}

	
			}
				
			logger.debug("筛选前ip数："+list.size());	
			//筛选proxy
			CloseableHttpClient client=HttpClients.createDefault();
			HttpGet get=new HttpGet("http://www.kongfz.com/");
			get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
			for(int i=0;i<list.size();i++)
			{
				HttpHost proxy=list.get(i);
				RequestConfig config=RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).setProxy(proxy).build();
				get.setConfig(config);
				try {
					CloseableHttpResponse response=client.execute(get);
					logger.debug("连接代理成功！请求状态码："+response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode()!=200)
						list.remove(i--);
					else
					{
						logger.info("成功"+proxy);
						//HttpEntity entity=response.getEntity();
						//logger.debug(EntityUtils.toString(entity,"UTF-8"));
						/*get.setURI(new URI("http://www.google.com.hk/"));
						response=client.execute(get);
						logger.debug("帆樯成功？！请求状态码："+response.getStatusLine().getStatusCode());
						entity=response.getEntity();
						logger.debug(EntityUtils.toString(entity,"UTF-8"));*/
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
//http://www.google.com.hk/
//https://home.firefoxchina.cn/
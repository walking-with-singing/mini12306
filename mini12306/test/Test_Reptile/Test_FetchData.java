package Test_Reptile;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import Reptile.FetchData;
import jdk.incubator.http.HttpHeaders;

public class Test_FetchData {
	FetchData fd=new FetchData();
	//@Test
	public void testSetTrainnumAndSchedule() 
	{
		fd.setTrainnumAndSchedule("https://kyfw.12306.cn/otn/resources/js/query/train_list.js?scriptVersion=1.0");
	}//'322734'
	/*//@Test
	public void testSetTrain()
	{
		fd.setTrain("https://kyfw.12306.cn/otn/resources/js/query/train_list.js?scriptVersion=1.0");
	}*/
	//@Test
	public void testSetStation()
	{
		fd.setStation("https://kyfw.12306.cn/otn/resources/js/framework/station_name.js");
	}
	//@Test
	public void testSetTrainLine()
	{
		fd.setTrainLine("https://kyfw.12306.cn/otn/czxx/queryByTrainNo");
	}
	//@Test
	public void test()
	{
		int[] str=new int[7];
		for(int s:str)
		{
			System.out.println(s);
		}
	}
	//@Test
	public void testSetTrainBasePrice()
	{
		fd.setTrainBasePrice("https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice");
	}
	@Test
	public void testSetPrice()
	{
		//String content=fd.getContent("https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no=4p0000G33803&from_station_no=02&to_station_no=06&seat_types=OM9&train_date=2019-07-18");
		//System.out.println(content);
		fd.setPrice("https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//@Test
	public void testHttpHost()
	{
		CloseableHttpClient client=HttpClients.createDefault();
		CloseableHttpResponse response=null;
		HttpGet get=new HttpGet("https://www.baidu.com/");
		 HttpHost proxy = new HttpHost("219.159.38.207", 56210);
	        RequestConfig config = RequestConfig.custom()
	        		.setSocketTimeout(10000).setConnectTimeout(10000).
					setProxy(proxy).build();

	get.setConfig(config);
		try {
			response=client.execute(get);
			System.out.println(response.getStatusLine().getStatusCode());
			HttpEntity entity=response.getEntity();
			System.out.println(EntityUtils.toString(entity,"UTF-8"));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//{"name":"User-Agent","value":"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0"}

}

/*http://61.164.39.69:53281
http://47.94.104.204:8118
http://121.232.148.156:9000*/
//x-forwarded-for
//client-ip

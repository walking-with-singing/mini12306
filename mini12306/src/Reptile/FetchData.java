package Reptile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import DatabaseAccess.DatabaseAccess;
import ORM.Schedule;
import ORM.Station;
import ORM.Train;
import ORM.TrainLine;
import ORM.Trainnum;
import Regex.Regex;

public class FetchData {
	private static final Logger logger=LogManager.getLogger();
	CloseableHttpClient client;
	CloseableHttpResponse response=null;
	HttpGet get;
	HttpEntity entity=null;
	DatabaseAccess dao;
	int ex=6015;
	List<HttpHost> ips=new ArrayList<>();
	int temp=0;
	public FetchData() {
		dao=new DatabaseAccess();
		client=HttpClients.createDefault();
		get=new HttpGet();
		ips.add(new HttpHost("219.159.38.207",56210));
		ips.add(new HttpHost("200.192.156.24",30534));
		ips.add(new HttpHost("118.250.3.42",8060));
		ips.add(new HttpHost("186.167.50.19",44245));
	}
	
	public String getContent(String uri)
	{
		String content = null;
		try {
			get.setURI(new URI(uri));
			get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
			response=client.execute(get);
			logger.error(response.getStatusLine().getStatusCode());
			while(response.getStatusLine().getStatusCode()!=200)
			{
				System.out.println(ex+"\t"+uri);
				System.out.println(response.getStatusLine().getStatusCode()+"-----------403异常");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client=HttpClients.createDefault();
				get=new HttpGet(uri);
				RequestConfig requestConfig = RequestConfig.custom().setProxy(ips.get(temp++)).build();
				get.setConfig(requestConfig);
				System.out.println("成功更换到第"+temp+"个ip"+get.getConfig().getProxy());
				response=client.execute(get);
			}
			entity=response.getEntity();
			content=EntityUtils.toString(entity,"UTF8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(uri);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return content;
	}
	
	public String getJson(String content)
	{
		int i;
	    for(i=0;i<100;i++)
	    {
	    	if(content.charAt(i)=='{')
	    	{
	    		break;
	    	}
	    }
	    content=content.substring(i, content.length());
	    return content;
	}
	/*
	public void setTrain(String uri) {
		String content=getContent(uri);
		String json=getJson(content);
		//map<date,dateObj>
		JSONObject jsonObj=JSON.parseObject(json);
		//Collection<dateObj>
		Collection<Object> dateCol=jsonObj.values();
		
		for(Object dateOb:dateCol)
		{
			//Collection<codeArray>-----map<code,codeArray>
			Collection<Object> codeCol=((JSONObject)dateOb).values();
			for(Object codeOb:codeCol)
			{
				//codeArray
				JSONArray jarr=(JSONArray)codeOb;
				for(Object ob:jarr)
				{
					JSONObject job=(JSONObject)ob;
					String train_no=job.getString("train_no");
					Train train=dao.selectFormTrain(train_no);
					if(train==null)
						dao.insertIntoTrain(train_no);		
				}
			}
			
		}
	}
	*/
	public void setTrainnumAndSchedule(String uri)
	{
		String content=getContent(uri);
		String json=getJson(content);
		//map<date,dateObj>
		JSONObject jsonObj=JSON.parseObject(json);
		for(Entry<String,Object> entry:jsonObj.entrySet())
		{
			Pattern pattern;
			Matcher matcher;
			String dateStr=entry.getKey();
			Date date=Date.valueOf(dateStr);
			//map<code,codeArray>
			Object dateOb=entry.getValue();
			//Collection<codeArray>-----map<code,codeArray>
			Collection<Object> codeCol=((JSONObject)dateOb).values();
			for(Object codeOb:codeCol)
			{
				//codeArray
				JSONArray jarr=(JSONArray)codeOb;
				for(Object ob:jarr)
				{
					JSONObject job=(JSONObject)ob;
					String train_no=job.getString("train_no");
					String station_train_code=job.getString("station_train_code");
					String code="",begin="",end="";
					String temp="";
					for(int j=0;j<station_train_code.length();j++)
					{
						char c=station_train_code.charAt(j);
						if(c!='('&&c!=')'&&c!='-')
						{
							temp+=c;
						}
						else
						{
							switch(c)
							{
								case '(':
									code=temp;
									temp="";
									break;
								case '-':
									begin=temp;
									temp="";
									break;
								case ')':
									end=temp;
									break;
							}
						}
						
					}
					pattern=Pattern.compile(code);
					matcher=pattern.matcher(train_no);
					if(matcher.find())
					{
						Trainnum trainnum=dao.selectFromTrainnum(train_no);
						if(trainnum==null)
							dao.insertIntoTrainnum( train_no, begin, end, code);
						if(dao.selectFromSchedule(train_no, date)==null)
							dao.insertIntoSchedule(train_no, date);
					}	
				}
			}
			
		}
	}

	public void setStation(String uri)
	{
		String content = getContent(uri);
		String pattern="@[a-z]+\\|[\u4E00-\u9FA5]+\\|[A-Z]+\\|[a-z]+\\|[a-z]+\\|[0-9]+";
		Regex regex=new Regex();
		List<String> list=regex.getGroups(content, pattern);
		for(String str:list)
		{
			String[] cons=str.split("\\|");
			Station station=dao.selectFromStation(cons[2]);
			if(station==null)
				dao.insertIntoStation(Integer.valueOf(cons[5]), cons[2], cons[1], cons[4]);
		}
	}
	
	public void setTrainLine(String uri)
	{
		int uriLength=uri.length();
		List<Trainnum> list=dao.selectAllFromTrainnum();		
		for(int ex=2927;ex<=2936;ex++)
		{
			Trainnum trainnum=list.get(ex);
			String train_no =trainnum.getTrain_no();// "65000C70120H";
			String from_station_telecode=dao.selectCodeFromStation(trainnum.getBegin());// "SZQ";
			String to_station_telecode = dao.selectCodeFromStation(trainnum.getEnd());//"GGQ";
			
			Date depart_date=dao.selectSubFromSchedule(train_no).get(0).getDate();
			uri=uri.substring(0, uriLength);
			uri+="?"+"train_no="+train_no
					+"&from_station_telecode="+from_station_telecode
					+"&to_station_telecode="+to_station_telecode
					+"&depart_date="+depart_date;
			String json=getContent(uri);
			System.out.println(uri);
			if(json.charAt(0)!='{')
			{
				list.add(trainnum);
				System.out.println(ex+"/"+list.size());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ex-=2;
				//System.out.println("ex-2:"+ex);
				continue;
				//System.exit(0);
			}
			JSONArray jarr=JSON.parseObject(json).getJSONObject("data").getJSONArray("data");
			String station_name;
			String arrive_time=null;
			String start_time=null;
			int station_no;
			for(int i=0;i<jarr.size();i++)
			{
				JSONObject obj=jarr.getJSONObject(i);
				station_name=obj.getString("station_name");
				arrive_time=obj.getString("arrive_time");
				if(arrive_time.length()<5)
					arrive_time=null;
				start_time=obj.getString("start_time");
				if(start_time.length()<5)
					start_time=null;
				station_no=Integer.valueOf(obj.getString("station_no"));
				TrainLine trainLine=dao.selectFromTrainLine(train_no, station_name);
				if(trainLine==null)
					dao.insertIntoTrainLine(train_no, station_name, arrive_time, start_time, station_no,-1);
			}
			
		}
	}
	public void setTrainBasePrice(String uri)
	{
		int uriLen=uri.length();
		List<Schedule> list=dao.foeSetPrice();
		for(Schedule s:list)
		{
			String train_no=s.getTrain_no();
			String from_station_no="01";
			String to_station_no="02";
			String seat_types="123469OMP";
			Date train_date=s.getDate();
			uri=uri.substring(0,uriLen);
			uri+="?"+"train_no="+train_no
					+"&from_station_no="+from_station_no
					+"&to_station_no="+to_station_no
					+"&seat_types="+seat_types
					+"&train_date="+train_date;
			String json=getContent(uri);
			JSONObject obj=JSON.parseObject(json).getJSONObject("data");
			String[] strPrices=new String[11];
			double[] prices = new double[11];
			// A9, P, M, O, A6, A4, A3, A2, A1, WZ, MIN
			strPrices[0]=obj.getString("A9");
			strPrices[1]=obj.getString("P");
			strPrices[2]=obj.getString("M");
			strPrices[3]=obj.getString("O");
			strPrices[4]=obj.getString("A6");
			strPrices[5]=obj.getString("A4");
			strPrices[6]=obj.getString("A3");
			strPrices[7]=obj.getString("A2");
			strPrices[8]=obj.getString("A1");
			strPrices[9]=obj.getString("WZ");
			strPrices[10]=obj.getString("MIN");
			for(int i=0;i<strPrices.length;i++)
			{
				String temp=strPrices[i];
				if(temp==null)
					continue;
				else
					prices[i]=Double.valueOf(temp.substring(1,temp.length()));
			}
			if(dao.selectFormTrain(train_no)==null)
				dao.insertIntoTrain(train_no, prices);
		}
	}
	public void setPrice(String uri)
	{
		int uriLen=uri.length();
		List<Schedule> list=dao.foeSetPrice();
		for(;ex<=list.size();ex++)
		{
			Schedule s=list.get(ex);
			String train_no=s.getTrain_no();
			String from_station_no="01";
			String to_station_no="03";
			String seat_types="123469OMP";
			Date train_date=s.getDate();
			uri=uri.substring(0,uriLen);
			uri+="?"+"train_no="+train_no
					+"&from_station_no="+from_station_no
					+"&to_station_no="+to_station_no
					+"&seat_types="+seat_types
					+"&train_date="+train_date;
			uri.trim();
			String json=getContent(uri);
			//System.out.println("yes");
			JSONObject obj;
			try {
				obj = JSON.parseObject(json).getJSONObject("data");
			} catch (Exception e) {
				
				System.out.println(ex+"\t"+uri);
				System.out.println(response.getStatusLine().getStatusCode()+"-----------JSON格式异常");
				ex--;
				//list.add(s);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}
			String[] strPrices=new String[11];
			double[] prices = new double[11];
			// A9, P, M, O, A6, A4, A3, A2, A1, WZ, MIN
			try {
				strPrices[0]=obj.getString("A9");
				strPrices[1]=obj.getString("P");
				strPrices[2]=obj.getString("M");
				strPrices[3]=obj.getString("O");
				strPrices[4]=obj.getString("A6");
				strPrices[5]=obj.getString("A4");
				strPrices[6]=obj.getString("A3");
				strPrices[7]=obj.getString("A2");
				strPrices[8]=obj.getString("A1");
				strPrices[9]=obj.getString("WZ");
				strPrices[10]=obj.getString("MIN");
			} catch (Exception e) {
				System.out.println(ex+"\t"+uri);
				System.out.println(response.getStatusLine().getStatusCode()+"-----------空指针异常");
				System.out.println(json);
				ex--;
				//list.add(s);
				/*try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				continue;
			}
			for(int i=0;i<strPrices.length;i++)
			{
				String temp=strPrices[i];
				if(temp==null)
					continue;
				else
					prices[i]=Double.valueOf(temp.substring(1,temp.length()));
			}
			dao.setPrice(prices, train_no);
		}
	}
}


package testReptile;
import static org.junit.Assert.*;

import java.util.List;


import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import Reptile.FetchData;
import Reptile.FetchFreeIps;


public class TestFetchFreeIps {
	Logger logger=LogManager.getLogger();
	FetchData fd=new FetchData();
	FetchFreeIps fip=new FetchFreeIps();
	@Test
	public void getProxys()
	{
		List<HttpHost> list=fip.getProxys();
		logger.debug("检测后可用ip数："+list.size());
		for(HttpHost proxy:list)
		{
			logger.debug(proxy);
		}
	}
	//@Test
	public void test()
	{
		assertEquals(1,1);
	}
	//@Test
	public void test2()
	{
		logger.trace("trace");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		logger.fatal("fatal");
	}
}

 
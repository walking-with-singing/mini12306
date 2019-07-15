package Test_Reptile;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import DatabaseAccess.DatabaseAccess;
import Reptile.FetchData;
import Reptile.FetchFreeIps;


public class FetchFreeIps_test {
	Logger logger=LogManager.getLogger("myFile");
	FetchData fd=new FetchData();
	FetchFreeIps fip=new FetchFreeIps();
	@Ignore
	@Test(timeout=1000)
	public void getProxys()
	{
		while(true);
	}
	@Ignore
	@Test
	public void test()
	{
		assertEquals(1,1);
	}
	@Test
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

 
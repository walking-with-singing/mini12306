package testDatabaseAccess;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import databaseAccess.DAO;
import orm.Schedule;

public class TestDAO {
	DAO dao=new DAO();
	protected Logger logger=LogManager.getLogger();
	@Test
	public void testGetSchedule()
	{
		List<Schedule> scl=dao.getSchedule("010000623202");
		for(Schedule s:scl)
		{
			logger.debug(s.getDate());
		}
	}
}

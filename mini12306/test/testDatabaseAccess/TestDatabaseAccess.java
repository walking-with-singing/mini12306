package testDatabaseAccess;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

import Reptile.FetchData;
import databaseAccess.DatabaseAccess;
import orm.Train;

public class TestDatabaseAccess {
	DatabaseAccess dao=new DatabaseAccess();
	FetchData fd=new FetchData();
	@Ignore
	@Test
	public void testInsertIntoTrainnum() throws SQLException
	{
		//dao.insertIntoTrainnum( "4", "1", "1", "10",new Date(System.currentTimeMillis()));
	}
	@Ignore
	@Test
	public void testInsertIntoTrain() throws SQLException
	{
		//System.out.println(dao.insertIntoTrain("3", "2"));
	}
	@Ignore
	@Test
	public void testSelectFormTrain()
	{
		Train train=dao.selectFormTrain("4");
		System.out.println(train);
	}
	@Ignore
	@Test
	public void tSelectFromSchedule()
	{
		System.out.println(dao.selectFromSchedule("8e000C860440", Date.valueOf("2019-06-23"))==null);
	}
	@Ignore
	@Test
	public void tInsertIntoTrainLine()
	{
		dao.insertIntoTrainLine("6", "1", null, null, 0, 0);
	}

}

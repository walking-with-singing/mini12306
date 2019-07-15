package Test_DatabaseAccess;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import org.junit.Test;

import DatabaseAccess.DatabaseAccess;
import ORM.Train;
import Reptile.FetchData;

public class Test_DatabaseAccess {
	DatabaseAccess dao=new DatabaseAccess();
	//FetchData fd=new FetchData();
	
	//@Test
	public void testInsertIntoTrainnum() throws SQLException
	{
		//dao.insertIntoTrainnum( "4", "1", "1", "10",new Date(System.currentTimeMillis()));
	}
	
	//@Test
	public void testInsertIntoTrain() throws SQLException
	{
		//System.out.println(dao.insertIntoTrain("3", "2"));
	}
	
	//@Test
	public void testSelectFormTrain()
	{
		Train train=dao.selectFormTrain("4");
		System.out.println(train);
	}
	//@Test
	public void tSelectFromSchedule()
	{
		System.out.println(dao.selectFromSchedule("8e000C860440", Date.valueOf("2019-06-23"))==null);
	}
	//@Test
	public void tInsertIntoTrainLine()
	{
		Time t=null;
		dao.insertIntoTrainLine("6", "1", null, null, 0, 0);
	}

}

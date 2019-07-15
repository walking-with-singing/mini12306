package DatabaseAccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ORM.Schedule;
import ORM.Station;
import ORM.Train;
import ORM.TrainLine;
import ORM.Trainnum;

public class DatabaseAccess {
	
	Connection c;
	
	String sTrain="SELECT t1.*,t2.station_name,t2.arrive_time,t2.station_no,s.date FROM trainline t1 , trainline t2 , schedule s" + 
			"where t1.train_no=t2.train_no and t1.train_no=s.train_no\r\n" + 
			"and t1.station_name=?" + 
			"and t2.station_name=?" + 
			"and t1.station_no<t2.station_no" + 
			"and date=?";
	
	
	//sql语句--插入
	String insertIntoTrainnum="insert into trainnum values(?,?,?,?)";
	String insertIntoTrain="insert into train  values(?,?,?,?,?,?,?,?,?,?,?,?)";
	String inserIntoStation="insert into station values(?,?,?,?)";
	String inserIntoTrainLine="insert into trainline values(?,?,?,?,?,?)";
	String insertIntoSchedule="insert into schedule values(?,?)";
	//sql语句--查询
	String selectFromTrain="select * from train where train_no=?";
	String selectFromTrainnum="select * from trainnum where train_no=?";
	String selectFromStation="select * from station where code=?";
	String selectFromTrainLine="select * from trainline where train_no=? and station_name=?";
	String selectAllFromTrainnum="select * from trainnum";
	String selectCodeFromStation="SELECT code FROM station where name=?";
	String selectFromSchedule="select * from schedule where train_no=? and date=?";
	String selecSubtFronSchedule="select * from schedule where train_no=?";
	String forSetPrice="SELECT * FROM schedule group by train_no order by train_no";
	//更新
	String setPrice="update price set A9=?, P=?, M=?, O=?, A6=?, A4=?, A3=?, A2=?, A1=?, WZ=?, MIN=? where station_no=3 and train_no=?";
	//ps--插入
	PreparedStatement psITrainnum;
	PreparedStatement psITrain;
	PreparedStatement psIStation;
	PreparedStatement psITrainLine;
	PreparedStatement psISchedule;
	//ps--查询
	PreparedStatement psSTrain;
	PreparedStatement psSTrainnum;
	PreparedStatement psSStation;
	PreparedStatement psSTrainLine;
	PreparedStatement psSATrainnum;
	PreparedStatement psSCodeStation;
	PreparedStatement psSSchedule;
	PreparedStatement psSSSubchedule;
	PreparedStatement psForSetPrice;
	//更新
	PreparedStatement psUsetPrice;
	//
	Statement statement;
	//构造方法
	public DatabaseAccess()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				c = DriverManager
				        .getConnection(
				                "jdbc:mysql://127.0.0.1:3306/mini12306?characterEncoding=UTF-8",
				                "root", "123456");
				//预编译--插入
		        psITrainnum=c.prepareStatement(insertIntoTrainnum);
		        psITrain=c.prepareStatement(insertIntoTrain);
		        psIStation=c.prepareStatement(inserIntoStation);
		        psITrainLine=c.prepareStatement(inserIntoTrainLine);
		        psISchedule=c.prepareStatement(insertIntoSchedule);
		        //预编译--查询
		        psSTrain=c.prepareStatement(selectFromTrain);
		        psSTrainnum=c.prepareStatement(selectFromTrainnum);
		        psSStation=c.prepareStatement(selectFromStation);
		        psSTrainLine=c.prepareStatement(selectFromTrainLine);
		        psSATrainnum=c.prepareStatement(selectAllFromTrainnum);
		        psSCodeStation=c.prepareStatement(selectCodeFromStation);
		        psSSchedule=c.prepareStatement(selectFromSchedule);
		        psSSSubchedule=c.prepareStatement(selecSubtFronSchedule);
		        psForSetPrice=c.prepareStatement(forSetPrice);
		        //预编译--更新
		        psUsetPrice=c.prepareStatement(setPrice);
		        //
		        statement=c.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}	
	//向车次表（trainnum）中插入数据
	public boolean insertIntoTrainnum(String train_no,String begin,String end,String code)
	{
		int uc=0;
		try {
			psITrainnum.setString(1, train_no);
			psITrainnum.setString(2, begin);
			psITrainnum.setString(3, end);
			psITrainnum.setString(4, code);
			uc=psITrainnum.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uc>0;
	}
	//向列车表（train）中插入数据
	public boolean insertIntoTrain(String train_no,double[] prices)
	{
		int uc = 0;
			try {
				psITrain.setString(1, train_no);
				psITrain.setDouble(2, prices[0]);
				psITrain.setDouble(3, prices[1]);
				psITrain.setDouble(4, prices[2]);
				psITrain.setDouble(5, prices[3]);
				psITrain.setDouble(6, prices[4]);
				psITrain.setDouble(7, prices[5]);
				psITrain.setDouble(8, prices[6]);
				psITrain.setDouble(9, prices[7]);
				psITrain.setDouble(10, prices[8]);
				psITrain.setDouble(11, prices[9]);
				psITrain.setDouble(12, prices[10]);
				uc=psITrain.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return uc>0;
	}
	//向车站（station）插入数据
	public boolean insertIntoStation(int count,String code,String name,String abb)
	{
		int uc=0;
		
		try {
			psIStation.setInt(1,count);
			psIStation.setString(2,code);
			psIStation.setString(3,name);
			psIStation.setString(4,abb);
			uc=psIStation.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uc>1;
	}
	//向路线表（trainline）插入数据
	public boolean insertIntoTrainLine(String train_no,String station_name,String arrive_time,String start_time,int station_no,int addPrice)
	{
		int uc=0;
		
		try {
			psITrainLine.setString(1,train_no);
			psITrainLine.setString(2,station_name);
			psITrainLine.setString(3,arrive_time);
			psITrainLine.setString(4,start_time);
			psITrainLine.setInt(5, station_no);
			psITrainLine.setInt(6, addPrice);
			uc=psITrainLine.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uc>1;
	}	
	//向车次时间安排表（schedule）插入数据
	public boolean insertIntoSchedule(String train_no,Date date)
	{
		int uc=0;
		try {
			psISchedule.setString(1, train_no);
			psISchedule.setDate(2, date);
			uc=psISchedule.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	//查询列车表（train）
 	public Train selectFormTrain(String train_no)
	{
		Train train = null;
		
		try {
			psSTrain.setString(1, train_no);
			ResultSet rs=psSTrain.executeQuery();
			if(rs.next())
			{
				train=new Train();
				train.setTrain_no(rs.getString(1));
				train.setA9(rs.getDouble(2));
				train.setP(rs.getDouble(3));
				train.setM(rs.getDouble(4));
				train.setO(rs.getDouble(5));
				train.setA6(rs.getDouble(6));
				train.setA4(rs.getDouble(7));
				train.setA3(rs.getDouble(8));
				train.setA2(rs.getDouble(9));
				train.setA1(rs.getDouble(10));
				train.setWZ(rs.getDouble(11));
				train.setMIN(rs.getDouble(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return train;
	}
	//查询车次表（trainnum）
	public Trainnum selectFromTrainnum(String train_no)
	{
		Trainnum trainnum = null;
		try {
			psSTrainnum.setString(1, train_no);
			ResultSet rs=psSTrainnum.executeQuery();
			if(rs.next())
			{
				trainnum=new Trainnum();
				trainnum.setTrain_no(rs.getString(1));
				trainnum.setBegin(rs.getString(2));
				trainnum.setEnd(rs.getString(3));
				trainnum.setCode(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trainnum;
	}
	//查询车次表（trainnum--全部
	public List<Trainnum> selectAllFromTrainnum()
	{
		List<Trainnum> list=new ArrayList<Trainnum>();
		Trainnum trainnum = null;
		try {
			ResultSet rs=psSATrainnum.executeQuery();
			while(rs.next())
			{
				trainnum=new Trainnum();
				trainnum.setTrain_no(rs.getString(1));
				trainnum.setBegin(rs.getString(2));
				trainnum.setEnd(rs.getString(3));
				trainnum.setCode(rs.getString(4));
				list.add(trainnum);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//查询车站表（station）
	public Station selectFromStation(String code)
	{
		Station station=null;
		try {
			psSStation.setString(1, code);
			ResultSet rs=psSStation.executeQuery();
			if(rs.next())
			{
				station=new Station();
				station.setCount(rs.getInt(1));
				station.setCode(rs.getString(2));
				station.setName(rs.getString(3));
				station.setAbb(rs.getString(4));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return station;
	}
	//查询车站表（station）---code
	public String selectCodeFromStation(String name)
	{
		String code = null;
		try {
			psSCodeStation.setString(1, name);
			ResultSet rs=psSCodeStation.executeQuery();
			if(rs.next())
			{
				code=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	//查询路线表（trainline）
	public TrainLine selectFromTrainLine(String train_no,String station_name)
	{
		TrainLine trainLine=null;
		try {
			psSTrainLine.setString(1,train_no);
			psSTrainLine.setString(2,station_name);
			ResultSet rs=psSTrainLine.executeQuery();
			if(rs.next())
			{
				trainLine=new TrainLine();
				trainLine.setTrain_no(rs.getString(1));
				trainLine.setStation_name(rs.getString(2));
				trainLine.setArrive_time(rs.getString(3));
				trainLine.setStart_time(rs.getString(4));
				trainLine.setStation_no(rs.getInt(5));
				trainLine.setAddPrice(rs.getInt(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trainLine;
	}
	//查询车次时间安排表（trainline）
	public Schedule selectFromSchedule(String train_no,Date date)
	{
		Schedule schedule = null;
		try {
			psSSchedule.setString(1, train_no);
			psSSchedule.setDate(2, date);
			ResultSet rs=psSSchedule.executeQuery();
			if(rs.next())
			{
				schedule=new Schedule();
				schedule.setTrain_no(rs.getString(1));
				schedule.setDate(rs.getDate(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return schedule;
	}
	//查询车次时间安排表（trainline）---获取时间集合
	public List<Schedule> selectSubFromSchedule(String train_no)
	{
		List<Schedule> list=new ArrayList<>();
		Schedule schedule=null;
		try {
			psSSSubchedule.setString(1, train_no);
			ResultSet rs=psSSSubchedule.executeQuery();
			while(rs.next())
			{
				schedule=new Schedule();
				schedule.setTrain_no(rs.getString(1));
				schedule.setDate(rs.getDate(2));
				list.add(schedule);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//定制
	public int update(String sql)
	{
		int uc=0;
		try {
			uc = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc;
	}
	public ResultSet select(String sql)
	{
		ResultSet rs=null;
		try {
			rs=statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public List<Schedule> foeSetPrice()
	{
		List<Schedule> list=new ArrayList<>();
		Schedule s=null;
		try {
			ResultSet rs=psForSetPrice.executeQuery();
			while(rs.next())
			{
				s=new Schedule();
				s.setTrain_no(rs.getString(1));
				s.setDate(rs.getDate(2));
				list.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public void setPrice(double[] prices,String train_no)
	{
		try {
			psUsetPrice.setDouble(1, prices[0]);
			psUsetPrice.setDouble(2, prices[1]);
			psUsetPrice.setDouble(3, prices[2]);
			psUsetPrice.setDouble(4, prices[3]);
			psUsetPrice.setDouble(5, prices[4]);
			psUsetPrice.setDouble(6, prices[5]);
			psUsetPrice.setDouble(7, prices[6]);
			psUsetPrice.setDouble(8, prices[7]);
			psUsetPrice.setDouble(9, prices[8]);
			psUsetPrice.setDouble(10, prices[9]);
			psUsetPrice.setDouble(11, prices[10]);
			psUsetPrice.setString(12, train_no);
			psUsetPrice.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

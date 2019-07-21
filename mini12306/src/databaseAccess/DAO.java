package databaseAccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import orm.AvailableTrain;
import orm.ReTickets;
import orm.Schedule;
import orm.Ticket;
import orm.TicketPrice;
import orm.TimeAndPrice;
import orm.Train;
import orm.TrainLine;
import orm.User;

public class DAO {
	//Tool
	private Logger logger=LogManager.getLogger();
	
	Connection c;
	
	String getReTicket="SELECT min(A9), min(P),min(M),min(O),min(A6),min(A4),min(A3),min(A2),min(A1),min(WZ),min(MIN) FROM remainingseats\r\n" + 
			"where \r\n" + 
			"train_no=?\r\n" + 
			"and date=?\r\n" + 
			"and station_no >= \r\n" + 
			"(select station_no from time_price where train_no=? and station_name=?)\r\n" + 
			"and station_no <= \r\n" + 
			"(select station_no from time_price where train_no=? and  station_name=?)";
	String getTicketPrice="SELECT sum(A9),sum(P),sum(M),sum(O),sum(A6),sum(A4),sum(A3),sum(A2),sum(A1),sum(WZ),sum(MIN)\r\n" + 
			"FROM time_price\r\n" + 
			"where \r\n" + 
			"train_no=?\r\n" + 
			"and station_no >= \r\n" + 
			"(select station_no from time_price where train_no=? and station_name=?)\r\n" + 
			"and station_no <= \r\n" + 
			"(select station_no from time_price where train_no=? and  station_name=?) ";
	String getUser="SELECT * FROM user where name=?";
	String getTicket="SELECT * FROM ticket where user_name=? and train_no=? and date=?";
	String getUserOrders="SELECT * FROM ticket where user_name=?";
	String getTransFer="SELECT *\r\n" + 
			"FROM availabletrain a1,availabletrain a2\r\n" + 
			"where a1.to_station_name=a2.from_station_name\r\n" + 
			"and a1.to_arrive_time<a2.from_start_time\r\n" + 
			"and a1.train_no!=a2.train_no\r\n"+
			"and a2.date=?\r\n" + 
			"and a1.date=?\r\n" + 
			"and a1.from_station_name like ?\r\n" + 
			"and a2.to_station_name like ?\r\n" + 
			"and a1.to_station_name not like ?\r\n" + 
			"and a2.from_station_name not like ?";
	String getSureMT="SELECT *\r\n" + 
			"FROM availabletrain a1,availabletrain a2\r\n" + 
			"where a1.to_station_name=a2.from_station_name\r\n" + 
			"and a1.train_no!=a2.train_no\r\n"+
			"and a1.to_station_name=?\r\n" + 
			"and a1.to_arrive_time<a2.from_start_time\r\n" + 
			"and a2.date=?\r\n" + 
			"and a1.date=?\r\n" + 
			"and a1.from_station_name like ?\r\n" + 
			"and a2.to_station_name like ?";
	String getTimeAndPrices="SELECT * FROM time_price\r\n" + 
			"where train_no=?\r\n"+
			"order by station_no";
	String getTrain="select * from train where train_no=?";
	String getSchedules="select * from schedule where train_no=? order by date";
	String getAllTimeAndPrice="SELECT * FROM time_price";
	String saveTicket="insert into ticket values(?,?,?,?,?,?,?,?,?,?,?,?)";
	String saveUser="insert into user (name,password) values(?,?)";
	String sAvailableTrain = "SELECT *  FROM availabletrain  "
			+"where from_station_name like ? "
			+"and to_station_name like ? " 
			+"and date=?";
	String saveTrain="insert into train values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String saveTimeAndPrice="insert into time_price values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String saveSchedule="insert into schedule values(?,?)";
	String updateTrain="update train set  code=?, A9=?, P=?, M=?, O=?, A6=?, A4=?, A3=?, A2=?, A1=?, WZ=?, MIN=?\r\n" + 
			"where train_no=?";
	String updateTimeAndPrice="update  time_price set  station_name=?, arrive_time=?, start_time=?, A9=?, P=?, M=?, O=?, A6=?, A4=?, A3=?, A2=?, A1=?, WZ=?, MIN=?\r\n" + 
			"where train_no=?\r\n" + 
			"and station_no=?";
//	String updateSchedule="";
	
	PreparedStatement psUpdateTrain;
	PreparedStatement psUpdateTimeAndPrice;
//	PreparedStatement psUpdateSchedule;
	PreparedStatement psSaveTimeAndPrice;
	PreparedStatement psSaveSchedule;
	PreparedStatement psSaveTrain;
	PreparedStatement psGetAllTimeAndPrice;
	PreparedStatement psGetSchedules;
	PreparedStatement psGetTrain;
	PreparedStatement psGetTimeAndPrices;
	PreparedStatement psAvailableSTrain;
	PreparedStatement psGetReTicket;
	PreparedStatement psGetTicketPrice;
	PreparedStatement psGetUser;
	PreparedStatement psSaveUser;
	PreparedStatement psGetTicket;
	PreparedStatement psGetUserOrders;
	PreparedStatement psSaveTicket;
	PreparedStatement psGetTransFer;
	PreparedStatement psGetSureMT;
	
	public DAO() {	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mini12306?characterEncoding=UTF-8","root", "123456");
			
			psAvailableSTrain=c.prepareStatement(sAvailableTrain);
			psGetReTicket=c.prepareStatement(getReTicket);
			psGetTicketPrice=c.prepareStatement(getTicketPrice);
			psGetUser=c.prepareStatement(getUser);
			psSaveUser=c.prepareStatement(saveUser);
			psGetTicket=c.prepareStatement(getTicket);
			psGetUserOrders=c.prepareStatement(getUserOrders);
			psSaveTicket=c.prepareStatement(saveTicket);
			psGetTransFer=c.prepareStatement(getTransFer);
			psGetSureMT=c.prepareStatement(getSureMT);
			psGetTimeAndPrices=c.prepareStatement(getTimeAndPrices);
			psGetTrain=c.prepareStatement(getTrain);
			psGetSchedules=c.prepareStatement(getSchedules);
			psGetAllTimeAndPrice=c.prepareStatement(getAllTimeAndPrice);
			psSaveTrain=c.prepareStatement(saveTrain);
			psSaveTimeAndPrice=c.prepareStatement(saveTimeAndPrice);
			psSaveSchedule=c.prepareStatement(saveSchedule);
			psUpdateTrain=c.prepareStatement(updateTrain);
			psUpdateTimeAndPrice=c.prepareStatement(updateTimeAndPrice);
//			psUpdateSchedule=c.prepareStatement(updateSchedule);
			
		} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        
	}
	//与事物有关的方法
	public boolean beginTransactionModel()
	{
			try {
				c.setAutoCommit(false);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}
	public boolean commit()
	{
		try {
			c.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean rollback() 
	{
		try {
			c.rollback();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean endTransactionModel()
	{
			try {
				c.setAutoCommit(true);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}
	//
	public boolean updateTrain(Train train)
	{
		int uc=0;
		int i=1;
		try {
			psUpdateTrain.setString(i++,train.getCode() );
			psUpdateTrain.setInt(i++, train.getA9());
			psUpdateTrain.setInt(i++, train.getP());
			psUpdateTrain.setInt(i++,train.getM() );
			psUpdateTrain.setInt(i++, train.getO());
			psUpdateTrain.setInt(i++, train.getA6());
			psUpdateTrain.setInt(i++, train.getA4());
			psUpdateTrain.setInt(i++, train.getA3());
			psUpdateTrain.setInt(i++, train.getA2());
			psUpdateTrain.setInt(i++, train.getA1());
			psUpdateTrain.setInt(i++, train.getWZ());
			psUpdateTrain.setInt(i++, train.getMIN());
			psUpdateTrain.setString(i++, train.getTrain_no());
			uc=psUpdateTrain.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public boolean updateTimeAndPrice(TimeAndPrice timeAndPrice)
	{
		int uc=0;
		int i=1;
		try {
			psUpdateTimeAndPrice.setString(i++, timeAndPrice.getStation_name());
			psUpdateTimeAndPrice.setTime(i++, timeAndPrice.getArrive_time());
			psUpdateTimeAndPrice.setTime(i++, timeAndPrice.getStart_time());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getA9());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getP());
			psUpdateTimeAndPrice.setInt(i++,timeAndPrice.getM() );
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getO());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getA6());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getA4());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getA3());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getA2());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getA1());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getWZ());
			psUpdateTimeAndPrice.setInt(i++, timeAndPrice.getMIN());
			psUpdateTimeAndPrice.setString(i++, timeAndPrice.getTrain_no());
			psUpdateTimeAndPrice.setInt(i++,timeAndPrice.getStation_no() );
			uc=psUpdateTimeAndPrice.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public boolean saveTimeAndPrice(TimeAndPrice timeAndPrice)
	{
		int uc=0;
		int i=1;
		try {
			psSaveTimeAndPrice.setString(i++, timeAndPrice.getTrain_no());
			psSaveTimeAndPrice.setInt(i++,timeAndPrice.getStation_no() );
			psSaveTimeAndPrice.setString(i++, timeAndPrice.getStation_name());
			psSaveTimeAndPrice.setTime(i++, timeAndPrice.getArrive_time());
			psSaveTimeAndPrice.setTime(i++, timeAndPrice.getStart_time());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getA9());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getP());
			psSaveTimeAndPrice.setInt(i++,timeAndPrice.getM() );
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getO());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getA6());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getA4());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getA3());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getA2());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getA1());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getWZ());
			psSaveTimeAndPrice.setInt(i++, timeAndPrice.getMIN());
			uc=psSaveTimeAndPrice.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public boolean saveSchedule(Schedule schedule)
	{
		int uc=0;
		int i=1;
		try {
			psSaveSchedule.setString(i++, schedule.getTrain_no());
			psSaveSchedule.setDate(i++,schedule.getDate() );
			uc=psSaveSchedule.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public boolean saveTrain(Train train)
	{
		int uc=0;
		int i=1;
		try {
			psSaveTrain.setString(i++, train.getTrain_no());
			psSaveTrain.setString(i++,train.getCode() );
			psSaveTrain.setInt(i++, train.getA9());
			psSaveTrain.setInt(i++, train.getP());
			psSaveTrain.setInt(i++,train.getM() );
			psSaveTrain.setInt(i++, train.getO());
			psSaveTrain.setInt(i++, train.getA6());
			psSaveTrain.setInt(i++, train.getA4());
			psSaveTrain.setInt(i++, train.getA3());
			psSaveTrain.setInt(i++, train.getA2());
			psSaveTrain.setInt(i++, train.getA1());
			psSaveTrain.setInt(i++, train.getWZ());
			psSaveTrain.setInt(i++, train.getMIN());
			uc=psSaveTrain.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public List<Schedule> getSchedule(String train_no)
	{
		List<Schedule> list = new ArrayList<>();
		try {
			psGetSchedules.setString(1, train_no);
			ResultSet rs=psGetSchedules.executeQuery();
			while(rs.next())
			{
				Schedule sche=new Schedule();
				int i=1;
				sche.setTrain_no(rs.getString(i++));
				sche.setDate(rs.getDate(i++));
				list.add(sche);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public Train getTrain(String train_no)
	{
		Train train = null;
		try {
			psGetTrain.setString(1, train_no);
			ResultSet rs=psGetTrain.executeQuery();
			if(rs.next())
			{
				train=new Train();
				int i=1;
				train.setTrain_no(rs.getString(i++));
				train.setCode(rs.getString(i++));
				train.setA9(rs.getInt(i++));
				train.setP(rs.getInt(i++));
				train.setM(rs.getInt(i++));
				train.setO(rs.getInt(i++));
				train.setA6(rs.getInt(i++));
				train.setA4(rs.getInt(i++));
				train.setA3(rs.getInt(i++));
				train.setA2(rs.getInt(i++));
				train.setA1(rs.getInt(i++));
				train.setWZ(rs.getInt(i++));
				train.setMIN(rs.getInt(i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return train;		
	}
	public List<TimeAndPrice> getAllTimeAndPrices()
	{
		List<TimeAndPrice> list = new ArrayList<>();
		try {
			ResultSet rs=psGetAllTimeAndPrice.executeQuery();
			while(rs.next())
			{
				TimeAndPrice timeAndPrice=new TimeAndPrice();
				timeAndPrice.setTrain_no(rs.getString(1));
				timeAndPrice.setStation_no(rs.getInt(2));
				timeAndPrice.setStation_name(rs.getString(3));
				timeAndPrice.setArrive_time(rs.getTime(4));
				timeAndPrice.setStart_time(rs.getTime(5));
				timeAndPrice.setA9(rs.getInt(6));
				timeAndPrice.setP(rs.getInt(7));
				timeAndPrice.setM(rs.getInt(8));
				timeAndPrice.setO(rs.getInt(9));
				timeAndPrice.setA6(rs.getInt(10));
				timeAndPrice.setA4(rs.getInt(11));
				timeAndPrice.setA3(rs.getInt(12));
				timeAndPrice.setA2(rs.getInt(13));
				timeAndPrice.setA1(rs.getInt(14));
				timeAndPrice.setWZ(rs.getInt(15));
				timeAndPrice.setMIN(rs.getInt(16));
				list.add(timeAndPrice);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<TimeAndPrice> getTimeAndPrices(String train_no)
	{
		List<TimeAndPrice> list = new ArrayList<>();
		try {
			psGetTimeAndPrices.setString(1, train_no);
			ResultSet rs=psGetTimeAndPrices.executeQuery();
			while(rs.next())
			{
				TimeAndPrice timeAndPrice=new TimeAndPrice();
				timeAndPrice.setTrain_no(rs.getString(1));
				timeAndPrice.setStation_no(rs.getInt(2));
				timeAndPrice.setStation_name(rs.getString(3));
				timeAndPrice.setArrive_time(rs.getTime(4));
				timeAndPrice.setStart_time(rs.getTime(5));
				timeAndPrice.setA9(rs.getInt(6));
				timeAndPrice.setP(rs.getInt(7));
				timeAndPrice.setM(rs.getInt(8));
				timeAndPrice.setO(rs.getInt(9));
				timeAndPrice.setA6(rs.getInt(10));
				timeAndPrice.setA4(rs.getInt(11));
				timeAndPrice.setA3(rs.getInt(12));
				timeAndPrice.setA2(rs.getInt(13));
				timeAndPrice.setA1(rs.getInt(14));
				timeAndPrice.setWZ(rs.getInt(15));
				timeAndPrice.setMIN(rs.getInt(16));
				list.add(timeAndPrice);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<AvailableTrain> getSureMT(String mid,Date date,String fromStation,String toStation)
	{
		List<AvailableTrain> list = new ArrayList<>();
		try {
			psGetSureMT.setString(1, mid);
			psGetSureMT.setDate(2, date);
			psGetSureMT.setDate(3, date);
			psGetSureMT.setString(4, fromStation);
			psGetSureMT.setString(5, toStation);
			ResultSet rs=psGetSureMT.executeQuery();
			while(rs.next())
			{
				AvailableTrain aTrain1=new AvailableTrain();
				aTrain1.setCode(rs.getString(1));
				aTrain1.setTrain_no(rs.getString(2));
				aTrain1.setFrom_station_name(rs.getString(3));
				aTrain1.setFrom_arrive_time(rs.getTime(4));
				aTrain1.setFrom_start_time(rs.getTime(5));
				aTrain1.setFrom_station_no(rs.getInt(6));
				aTrain1.setTo_station_name(rs.getString(7));
				aTrain1.setTo_arrive_time(rs.getTime(8));
				aTrain1.setTo_station_no(9);
				aTrain1.setDate(rs.getDate(10));
				list.add(aTrain1);
				//
				AvailableTrain aTrain2=new AvailableTrain();
				aTrain2.setCode(rs.getString(11));
				aTrain2.setTrain_no(rs.getString(12));
				aTrain2.setFrom_station_name(rs.getString(13));
				aTrain2.setFrom_arrive_time(rs.getTime(14));
				aTrain2.setFrom_start_time(rs.getTime(15));
				aTrain2.setFrom_station_no(rs.getInt(16));
				aTrain2.setTo_station_name(rs.getString(17));
				aTrain2.setTo_arrive_time(rs.getTime(18));
				aTrain2.setTo_station_no(19);
				aTrain2.setDate(rs.getDate(20));
				list.add(aTrain2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<AvailableTrain> getTransfer(String fromStation,String toStation,Date date)
	{
		List<AvailableTrain> list = new ArrayList<>();
		try {
			psGetTransFer.setDate(1, date);
			psGetTransFer.setDate(2, date);
			psGetTransFer.setString(3, fromStation);
			psGetTransFer.setString(4, toStation);
			psGetTransFer.setString(5, fromStation);
			psGetTransFer.setString(6, toStation);
			ResultSet rs=psGetTransFer.executeQuery();
			while(rs.next()&&list.size()<=20)
			{
				AvailableTrain aTrain1=new AvailableTrain();
				aTrain1.setCode(rs.getString(1));
				aTrain1.setTrain_no(rs.getString(2));
				aTrain1.setFrom_station_name(rs.getString(3));
				aTrain1.setFrom_arrive_time(rs.getTime(4));
				aTrain1.setFrom_start_time(rs.getTime(5));
				aTrain1.setFrom_station_no(rs.getInt(6));
				aTrain1.setTo_station_name(rs.getString(7));
				aTrain1.setTo_arrive_time(rs.getTime(8));
				aTrain1.setTo_station_no(9);
				aTrain1.setDate(rs.getDate(10));
				list.add(aTrain1);
				//
				AvailableTrain aTrain2=new AvailableTrain();
				aTrain2.setCode(rs.getString(11));
				aTrain2.setTrain_no(rs.getString(12));
				aTrain2.setFrom_station_name(rs.getString(13));
				aTrain2.setFrom_arrive_time(rs.getTime(14));
				aTrain2.setFrom_start_time(rs.getTime(15));
				aTrain2.setFrom_station_no(rs.getInt(16));
				aTrain2.setTo_station_name(rs.getString(17));
				aTrain2.setTo_arrive_time(rs.getTime(18));
				aTrain2.setTo_station_no(19);
				aTrain2.setDate(rs.getDate(20));
				list.add(aTrain2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<AvailableTrain> getAvailableTrains(String fromStation,String toStation,Date date)
	{
		List<AvailableTrain> list = new ArrayList<>();
		try {
			psAvailableSTrain.setString(1, fromStation);
			psAvailableSTrain.setString(2, toStation);
			psAvailableSTrain.setDate(3, date);
			ResultSet rs=psAvailableSTrain.executeQuery();
			while(rs.next())
			{
				AvailableTrain aTrain=new AvailableTrain();
				aTrain.setCode(rs.getString(1));
				aTrain.setTrain_no(rs.getString(2));
				aTrain.setFrom_station_name(rs.getString(3));
				aTrain.setFrom_arrive_time(rs.getTime(4));
				aTrain.setFrom_start_time(rs.getTime(5));
				aTrain.setFrom_station_no(rs.getInt(6));
				aTrain.setTo_station_name(rs.getString(7));
				aTrain.setTo_arrive_time(rs.getTime(8));
				aTrain.setTo_station_no(9);
				aTrain.setDate(rs.getDate(10));
				list.add(aTrain);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public void getReTickets(AvailableTrain aTrain)
	{
		try {
			psGetReTicket.setString(1, aTrain.getTrain_no());
			psGetReTicket.setDate(2,aTrain.getDate());
			psGetReTicket.setString(3, aTrain.getTrain_no());
			psGetReTicket.setString(4, aTrain.getFrom_station_name());
			psGetReTicket.setString(5, aTrain.getTrain_no());
			psGetReTicket.setString(6, aTrain.getTo_station_name());
			ResultSet rs=psGetReTicket.executeQuery();
			if(rs.next())
			{
				//logger.debug("RsTickets结果不为空");
				ReTickets rt=new ReTickets();
				rt.setA9(rs.getInt(1));
				rt.setP(rs.getInt(2));
				rt.setM(rs.getInt(3));
				rt.setO(rs.getInt(4));
				rt.setA6(rs.getInt(5));
				rt.setA4(rs.getInt(6));
				rt.setA3(rs.getInt(7));
				rt.setA2(rs.getInt(8));
				rt.setA1(rs.getInt(9));
				rt.setWZ(rs.getInt(10));
				rt.setMIN(rs.getInt(11));
				aTrain.setReTickets(rt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getTicketPrice(AvailableTrain aTrain)
	{
		try {
			psGetTicketPrice.setString(1, aTrain.getTrain_no());
			psGetTicketPrice.setString(2, aTrain.getTrain_no());
			psGetTicketPrice.setString(3, aTrain.getFrom_station_name());
			psGetTicketPrice.setString(4, aTrain.getTrain_no());
			psGetTicketPrice.setString(5, aTrain.getTo_station_name());
			ResultSet rs=psGetTicketPrice.executeQuery();
			if(rs.next())
			{
				logger.debug("RsTickets结果不为空");
				TicketPrice ticketPrice=new TicketPrice();
				ticketPrice.setA9(rs.getInt(1));
				ticketPrice.setP(rs.getInt(2));
				ticketPrice.setM(rs.getInt(3));
				ticketPrice.setO(rs.getInt(4));
				ticketPrice.setA6(rs.getInt(5));
				ticketPrice.setA4(rs.getInt(6));
				ticketPrice.setA3(rs.getInt(7));
				ticketPrice.setA2(rs.getInt(8));
				ticketPrice.setA1(rs.getInt(9));
				ticketPrice.setWZ(rs.getInt(10));
				ticketPrice.setMIN(rs.getInt(11));
				aTrain.setTicketPrice(ticketPrice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public User getUser(String name)
	{
		User user=null;
		try {
			psGetUser.setString(1, name);
			ResultSet rs=psGetUser.executeQuery();
			if(rs.next())
			{
				user=new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	public boolean saveUser(String name,String password)
	{
		//int uc=0;
		try {
			psSaveUser.setString(1, name);
			psSaveUser.setString(2,password);
			return psSaveUser.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Ticket getTicket(String user_name,String train_no,Date date)
	{
		Ticket ticket=null;
		try {
			psGetTicket.setString(1, user_name);
			psGetTicket.setString(2, train_no);
			psGetTicket.setDate(3, date);
			ResultSet rs=psGetTicket.executeQuery();
			if(rs.next())
			{
				ticket=new Ticket();
				ticket.setOrder_time(rs.getLong(1));
				ticket.setUser_name(rs.getString(2));
				ticket.setTrain_no(rs.getString(3));
				ticket.setCode(rs.getString(4));
				ticket.setDate(rs.getDate(5));
				ticket.setFrom_station_name(rs.getString(6));
				ticket.setFrom_arrive_time(rs.getTime(7));
				ticket.setFrom_start_time(rs.getTime(8));
				ticket.setTo_station_name(rs.getString(9));
				ticket.setTo_arrive_time(rs.getTime(10));
				ticket.setSeat_type(rs.getString(11));
				ticket.setPrice(rs.getDouble(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticket;
	}
	public boolean saveTicket(Ticket ticket)
	{
		int uc=0;
		try {
			psSaveTicket.setLong(1,ticket.getOrder_time());
			psSaveTicket.setString(2,ticket.getUser_name());
			psSaveTicket.setString(3,ticket.getTrain_no());
			psSaveTicket.setString(4,ticket.getCode());
			psSaveTicket.setDate(5,ticket.getDate());
			psSaveTicket.setString(6,ticket.getFrom_station_name());
			psSaveTicket.setTime(7,ticket.getFrom_arrive_time());
			psSaveTicket.setTime(8,ticket.getFrom_start_time());
			psSaveTicket.setString(9,ticket.getTo_station_name());
			psSaveTicket.setTime(10,ticket.getTo_arrive_time());
			psSaveTicket.setString(11,ticket.getSeat_type());
			psSaveTicket.setDouble(12,ticket.getPrice());
			uc=psSaveTicket.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
		return uc>0;
	}
	public List<Ticket> getUserOrders(String user_name)
	{
		List<Ticket> list=new ArrayList<>();
		try {
			psGetUserOrders.setString(1, user_name);
			ResultSet rs=psGetUserOrders.executeQuery();
			while(rs.next())
			{
				Ticket ticket=new Ticket();
				ticket.setOrder_time(rs.getLong(1));
				ticket.setUser_name(rs.getString(2));
				ticket.setTrain_no(rs.getString(3));
				ticket.setCode(rs.getString(4));
				ticket.setDate(rs.getDate(5));
				ticket.setFrom_station_name(rs.getString(6));
				ticket.setFrom_arrive_time(rs.getTime(7));
				ticket.setFrom_start_time(rs.getTime(8));
				ticket.setTo_station_name(rs.getString(9));
				ticket.setTo_arrive_time(rs.getTime(10));
				ticket.setSeat_type(rs.getString(11));
				ticket.setPrice(rs.getDouble(12));
				list.add(ticket);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
		
	
}



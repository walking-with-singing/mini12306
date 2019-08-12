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
import orm.Manager;
import orm.ReTickets;
import orm.Schedule;
import orm.SeatMap;
import orm.Ticket;
import orm.TicketPrice;
import orm.TimeAndPrice;
import orm.Train;
import orm.User;

public class DAO {
	//Tool
	private Logger logger=LogManager.getLogger();
	String seatType;
	String op="-";
	Connection c;
	
	String getOrders="select * from ticket where user_name=?";
	String getReTicket="SELECT min(A9), min(P),min(M),min(O),min(A6),min(A4),min(A3),min(A2),min(A1),min(WZ),min(MIN) FROM remainingseats\r\n" + 
			"where \r\n" + 
			"train_no=?\r\n" + 
			"and date=?\r\n" + 
			"and station_no >= \r\n" + 
			"(select station_no from time_price where train_no=? and station_name=?)\r\n" + 
			"and station_no < \r\n" + 
			"(select station_no from time_price where train_no=? and  station_name=?)";
	String getTicketPrice="SELECT sum(A9),sum(P),sum(M),sum(O),sum(A6),sum(A4),sum(A3),sum(A2),sum(A1),sum(WZ),sum(MIN)\r\n" + 
			"FROM time_price\r\n" + 
			"where \r\n" + 
			"train_no=?\r\n" + 
			"and station_no >= \r\n" + 
			"(select station_no from time_price where train_no=? and station_name=?)\r\n" + 
			"and station_no < \r\n" + 
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
	String getAllUser="SELECT * FROM user";
	String getSeatMapByName="select * from seatMap where name=?";
	String getManager="select * from manager where id=?";
	String saveTicket="insert into ticket values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
	String updateRemainingseats="update remainingseats set "+seatType+"="+seatType+op+"1\r\n" + 
			"where train_no=? \r\n" + 
			"and station_no>=?\r\n" + 
			"and station_no <?\r\n"+
			"and date=?";
	String addRemainingseats="insert into remainingseats values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	String deleteTicket="delete from ticket where user_name=? and train_no=? and date=?";
	String deleteUser="delete from user where name=?";
	String deleteTrain="delete from train where train_no=?";
	
	PreparedStatement psDeleteTrain;
	PreparedStatement psDeleteUser;
	PreparedStatement psDeleteTicket;
	PreparedStatement psUpdateTrain;
	PreparedStatement psUpdateTimeAndPrice;
	PreparedStatement psUpdateRemainingseats;
	PreparedStatement psAddRemainingseats;
	PreparedStatement psSaveTimeAndPrice;
	PreparedStatement psSaveSchedule;
	PreparedStatement psSaveTrain;
	PreparedStatement psGetManager;
	PreparedStatement psGetSeatMapByName;
	PreparedStatement psGetAllUser;
	PreparedStatement psGetAllTimeAndPrice;
	PreparedStatement psGetSchedules;
	PreparedStatement psGetTrain;
	PreparedStatement psGetTimeAndPrices;
	PreparedStatement psGetOrders;
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
			psAddRemainingseats=c.prepareStatement(addRemainingseats);
			psUpdateTrain=c.prepareStatement(updateTrain);
			psUpdateTimeAndPrice=c.prepareStatement(updateTimeAndPrice);
			psUpdateRemainingseats=c.prepareStatement(updateRemainingseats);
			psGetSeatMapByName=c.prepareStatement(getSeatMapByName);
			psGetOrders=c.prepareStatement(getOrders);
			psDeleteTicket=c.prepareStatement(deleteTicket);
			psGetAllUser=c.prepareStatement(getAllUser);
			psDeleteUser=c.prepareStatement(deleteUser);
			psGetManager=c.prepareStatement(getManager);
			psDeleteTrain=c.prepareStatement(deleteTrain);
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
	public boolean deleteTrain(String train_no)
	{
		int uc=0;
		try {
			psDeleteTrain.setString(1, train_no);
			uc=psDeleteTrain.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
 	public boolean deleteTicket(Ticket ticket)
	{
		int uc=0;
		try {
			int i=1;
			psDeleteTicket.setString(i++, ticket.getUser_name());
			psDeleteTicket.setString(i++, ticket.getTrain_no());
			psDeleteTicket.setDate(i++, ticket.getDate());
			uc=psDeleteTicket.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public boolean deleteUser(User user)
	{
		int uc=0;
		try {
			int i=1;
			psDeleteUser.setString(i++, user.getName());
			uc=psDeleteUser.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
	public boolean updateRemainingseats(Ticket ticket,String op)
	{
		int uc=0;
		//更新余票
		int i=1;
		try {
			seatType=this.getSeatMapByName(ticket.getSeat_type()).getCode();
			String updateRemainingseats="update remainingseats set "+seatType+"="+seatType+op+"1\r\n" + 
					"where train_no=? \r\n" + 
					"and station_no>=?\r\n" + 
					"and station_no <?\r\n"+
					"and date=?";
			psUpdateRemainingseats=c.prepareStatement(updateRemainingseats);
			logger.debug("seatType:"+seatType);
			psUpdateRemainingseats.setString(i++, ticket.getTrain_no());
			psUpdateRemainingseats.setInt(i++, ticket.getFrom_station_no());
			psUpdateRemainingseats.setInt(i++, ticket.getTo_station_no());
			psUpdateRemainingseats.setDate(i++, ticket.getDate());
			uc=psUpdateRemainingseats.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uc>0;
	}
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
	
	public boolean addRemainingseats(Train train,Schedule schedule,TimeAndPrice timeAndPrice)
	{
		int uc=0;
		try {
			int i=1;
			psAddRemainingseats.setString(i++, train.getTrain_no());
			psAddRemainingseats.setDate(i++, schedule.getDate());
			psAddRemainingseats.setString(i++,timeAndPrice.getStation_name());
			psAddRemainingseats.setInt(i++, timeAndPrice.getStation_no());
			psAddRemainingseats.setInt(i++, train.getA9());
			psAddRemainingseats.setInt(i++, train.getP());
			psAddRemainingseats.setInt(i++, train.getM());
			psAddRemainingseats.setInt(i++, train.getO());
			psAddRemainingseats.setInt(i++, train.getA6());
			psAddRemainingseats.setInt(i++, train.getA4());
			psAddRemainingseats.setInt(i++, train.getA3());
			psAddRemainingseats.setInt(i++, train.getA2());
			psAddRemainingseats.setInt(i++, train.getA1());
			psAddRemainingseats.setInt(i++, train.getWZ());
			psAddRemainingseats.setInt(i++, train.getMIN());
			uc=psAddRemainingseats.executeUpdate();
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
	public boolean saveTicket(Ticket ticket)
	{
			int uc=0;
				try {
					//保存车票
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
					psSaveTicket.setInt(13, ticket.getCarriage_no());
					psSaveTicket.setInt(14, ticket.getSeat_no());
					psSaveTicket.setInt(15,ticket.getFrom_station_no());
					psSaveTicket.setInt(16,ticket.getTo_station_no());
					uc=psSaveTicket.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return uc>0;
	}
	public SeatMap getSeatMapByName(String name)
	{
		SeatMap seatMap=null;
		try {
			psGetSeatMapByName.setString(1, name);
			ResultSet rs=psGetSeatMapByName.executeQuery();
			while(rs.next())
			{
				seatMap=new SeatMap();
				seatMap.setCode(rs.getString(1));
				seatMap.setName(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seatMap;
	}
	public List<User> getAllUser()
	{
		List<User> list = new ArrayList<>();
		try {
			ResultSet rs=psGetAllUser.executeQuery();
			while(rs.next())
			{
				User user=new User();
				int i=1;
				user.setName(rs.getString(i++));
				user.setPassword(rs.getString(i++));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<Ticket> getOrders(String user_name)
	{
		List<Ticket> list = new ArrayList<>();
		try {
			psGetOrders.setString(1, user_name);
			ResultSet rs=psGetOrders.executeQuery();
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
				ticket.setCarriage_no(rs.getInt(13));
				ticket.setSeat_no(rs.getInt(14));
				ticket.setFrom_station_no(rs.getInt(15));
				ticket.setTo_station_no(rs.getInt(16));
				list.add(ticket);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
				aTrain1.setTo_station_no(rs.getInt(9));
				aTrain1.setDate(rs.getDate(10));
				this.getReTickets(aTrain1);
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
				aTrain2.setTo_station_no(rs.getInt(19));
				aTrain2.setDate(rs.getDate(20));
				this.getReTickets(aTrain2);
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
				this.getReTickets(aTrain1);
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
				this.getReTickets(aTrain2);
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
				aTrain.setTo_station_no(rs.getInt(9));
				aTrain.setDate(rs.getDate(10));
				this.getReTickets(aTrain);
				list.add(aTrain);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
	public Manager getManager(int id)
	{
		Manager m=null;
		try {
			psGetManager.setInt(1, id);
			ResultSet rs=psGetManager.executeQuery();
			while(rs.next())
			{
				m=new Manager();
				m.setId(rs.getInt(1));
				m.setPassword(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	public AvailableTrain getReTickets(AvailableTrain aTrain)
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
		return aTrain;
	}
	public TicketPrice getTicketPrice(AvailableTrain aTrain)
	{
		TicketPrice ticketPrice=null;
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
				ticketPrice=new TicketPrice();
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
		return ticketPrice;
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
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
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
				ticket.setCarriage_no(rs.getInt(13));
				ticket.setSeat_no(rs.getInt(14));
				ticket.setFrom_station_no(rs.getInt(15));
				ticket.setTo_station_no(rs.getInt(16));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticket;
	}
		
	
	public int[] getCarriage(String seatType,ReTickets reTickets)
	{
		int[] a=new int[2];
		int rest=0;
		String code=this.getSeatMapByName(seatType).getCode();
		switch(code)
		{
			case "A9":
				rest=reTickets.getA9();
				a[0]=(5*32-rest)/32+1;
				a[1]=(5*32-rest)%32+1;
				break;
			case "P":
				rest=reTickets.getP();
				a[0]=(5*32-rest)/32+1;
				a[1]=(5*32-rest)%32+1;
				break;
			case "M":
				rest=reTickets.getM();
				a[0]=(5*32-rest)/32+1;
				a[1]=(5*32-rest)%32+1;
				break;
			case "O":
				rest=reTickets.getO();
				a[0]=(5*32-rest)/32+1;
				a[1]=(5*32-rest)%32+1;
				break;
			case "A6":
				rest=reTickets.getA6();
				a[0]=(5*66-rest)/32+1;
				a[1]=(5*66-rest)%32+1;
				break;
			case "A4":
				rest=reTickets.getA4();
				a[0]=(6*118-rest)/32+1;
				a[1]=(6*118-rest)%32+1;
				break;
			case "A3":
				rest=reTickets.getA3();
				a[0]=(6*118-rest)/32+1;
				a[1]=(6*118-rest)%32+1;
				break;
			case "A2":
				rest=reTickets.getA2();
				a[0]=(6*118-rest)/32+1;
				a[1]=(6*118-rest)%32+1;
				break;
			case "A1":
				rest=reTickets.getA1();
				a[0]=(6*118-rest)/32+1;
				a[1]=(6*118-rest)%32+1;
				break;
			case "WZ":
				rest=reTickets.getWZ();
				a[0]=(6*118-rest)/32+1;
				a[1]=(6*118-rest)%32+1;
				break;
			case "MIN":
				rest=reTickets.getMIN();
				a[0]=(6*118-rest)/32+1;
				a[1]=(6*118-rest)%32+1;
				break;
		}
		return a;
	}
}



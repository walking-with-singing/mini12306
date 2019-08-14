package netToolset;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
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
	private static Logger logger = LogManager.getLogger();
	private static Socket socket;
	private static ObjectInputStream objectInput;
	private static ObjectOutputStream objectOutput;

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		DAO.socket = socket;
		try {
			objectInput = new ObjectInputStream(socket.getInputStream());
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			logger.trace("Request就绪");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 与事物有关的方法
	public boolean beginTransactionModel() {
		logger.trace("调用beginTransactionModel");
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("beginTransactionModel");
			objectOutput.flush();
			logger.trace("beginTransactionModel数据传输完毕");
			flag = objectInput.readBoolean();
			logger.trace("beginTransactionModel取得返回值flag："+flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean commit() {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("commit");
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean rollback() {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("rollback");
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean endTransactionModel() {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("endTransactionModel");
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	//
	public boolean deleteTrain(String train_no) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("deleteTrain");
			objectOutput.writeUTF(train_no);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteTicket(Ticket ticket) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("deleteTicket");
			objectOutput.writeObject(ticket);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteUser(User user) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("deleteUser");
			objectOutput.writeObject(user);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateRemainingseats(Ticket ticket, String op) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("updateRemainingseats");
			objectOutput.writeObject(ticket);
			objectOutput.writeUTF(op);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateTrain(Train train) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("updateTrain");
			objectOutput.writeObject(train);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateTimeAndPrice(TimeAndPrice timeAndPrice) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("updateTimeAndPrice");
			objectOutput.writeObject(timeAndPrice);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean addRemainingseats(Train train, Schedule schedule, TimeAndPrice timeAndPrice) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("addRemainingseats");
			objectOutput.writeObject(train);
			objectOutput.writeObject(schedule);
			objectOutput.writeObject(timeAndPrice);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveTimeAndPrice(TimeAndPrice timeAndPrice) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("saveTimeAndPrice");
			objectOutput.writeObject(timeAndPrice);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveSchedule(Schedule schedule) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("saveSchedule");
			objectOutput.writeObject(schedule);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveTrain(Train train) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("saveTrain");
			objectOutput.writeObject(train);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveUser(String name, String password) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("saveUser");
			objectOutput.writeUTF(name);
			objectOutput.writeUTF(password);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean saveTicket(Ticket ticket) {
		boolean flag = false;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("saveTicket");
			objectOutput.writeObject(ticket);
			objectOutput.flush();
			flag = objectInput.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public SeatMap getSeatMapByName(String name) {
		SeatMap seatMap = null;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getSeatMapByName");
			objectOutput.writeUTF(name);
			objectOutput.flush();
			seatMap=(SeatMap) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seatMap;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		List<User> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getAllUser");
			objectOutput.flush();
			list = (List<User>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Ticket> getOrders(String user_name) {
		List<Ticket> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getOrders");
			objectOutput.writeUTF(user_name);
			objectOutput.flush();
			list = (List<Ticket>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Schedule> getSchedule(String train_no) {
		List<Schedule> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getSchedule");
			objectOutput.writeUTF(train_no);
			objectOutput.flush();
			list = (List<Schedule>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Train getTrain(String train_no) {
		Train train = null;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getTrain");
			objectOutput.writeUTF(train_no);
			objectOutput.flush();
			train = (Train) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return train;
	}

	@SuppressWarnings("unchecked")
	public List<TimeAndPrice> getAllTimeAndPrices() {
		List<TimeAndPrice> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getAllTimeAndPrices");
			objectOutput.flush();
			list = (List<TimeAndPrice>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TimeAndPrice> getTimeAndPrices(String train_no) {
		List<TimeAndPrice> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getTimeAndPrices");
			objectOutput.writeUTF(train_no);
			objectOutput.flush();
			list = (List<TimeAndPrice>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableTrain> getSureMT(String mid, Date date, String fromStation, String toStation) {
		List<AvailableTrain> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getSureMT");
			objectOutput.writeUTF(mid);
			objectOutput.writeObject(date);
			objectOutput.writeUTF(fromStation);
			objectOutput.writeUTF(toStation);
			objectOutput.flush();
			list = (List<AvailableTrain>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableTrain> getTransfer(String fromStation, String toStation, Date date) {
		List<AvailableTrain> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getTransfer");
			objectOutput.writeUTF(fromStation);
			objectOutput.writeUTF(toStation);
			objectOutput.writeObject(date);
			objectOutput.flush();
			list = (List<AvailableTrain>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<AvailableTrain> getAvailableTrains(String fromStation, String toStation, Date date) {
		List<AvailableTrain> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getAvailableTrains");
			objectOutput.writeUTF(fromStation);
			objectOutput.writeUTF(toStation);
			objectOutput.writeObject(date);
			objectOutput.flush();
			list = (List<AvailableTrain>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Ticket> getUserOrders(String user_name) {
		List<Ticket> list = new ArrayList<>();
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getUserOrders");
			objectOutput.writeUTF(user_name);
			objectOutput.flush();
			list = (List<Ticket>) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Manager getManager(int id) {
		Manager m = null;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getManager");
			objectOutput.writeInt(id);
			objectOutput.flush();
			m = (Manager) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}

	public AvailableTrain getReTickets(AvailableTrain aTrain) {
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getReTickets");
			objectOutput.writeObject(aTrain);
			objectOutput.flush();
			aTrain = (AvailableTrain) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aTrain;
	}

	public TicketPrice getTicketPrice(AvailableTrain aTrain) {
		TicketPrice ticketPrice = null;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getTicketPrice");
			objectOutput.writeObject(aTrain);
			objectOutput.flush();
			logger.trace("getTicketPrice参数传输完毕");
			ticketPrice = (TicketPrice) objectInput.readObject();
			aTrain.setTicketPrice(ticketPrice);
			logger.trace("getTicketPrice对象读取完毕传输完毕"+aTrain.getTicketPrice().getA9());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticketPrice;
	}

	public  User getUser(String name) {

		User user = null;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getUser");
			objectOutput.writeUTF(name);
			objectOutput.flush();
			user = (User) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	public Ticket getTicket(String user_name, String train_no, Date date) {
		Ticket ticket = null;
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getTicket");
			objectOutput.writeUTF(user_name);
			objectOutput.writeUTF(train_no);
			objectOutput.writeObject(date);
			objectOutput.flush();
			ticket = (Ticket) objectInput.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticket;
	}

	public int[] getCarriage(String seatType, ReTickets reTickets) {
		int[] a = new int[2];
		try {
			objectOutput.writeUTF(Protocol.database.toString());
			objectOutput.writeUTF("getCarriage");
			objectOutput.writeUTF(seatType);
			objectOutput.writeObject(reTickets);
			objectOutput.flush();
			a[0] = objectInput.readInt();
			a[1] = objectInput.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

}

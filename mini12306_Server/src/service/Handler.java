package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseAccess.DAO;
import orm.AvailableTrain;
import orm.ReTickets;
import orm.Schedule;
import orm.Ticket;
import orm.TicketPrice;
import orm.TimeAndPrice;
import orm.Train;
import orm.User;

public class Handler implements Runnable {
	protected Logger logger = LogManager.getLogger();
	DAO dao = new DAO();
	boolean isRemoteAvailable;
	Socket socket;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;

	public Handler(Socket socket) {
		this.socket = socket;
		isRemoteAvailable = true;
		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (isRemoteAvailable) {
			try {
				logger.trace("等待消息传来");
				while (objectInput.available() == 0)
					;
				// 开始一轮信息交流
				logger.trace("开始一轮信息交流");
				headl(objectInput.readUTF());
				// 一轮信息交流结束
				logger.trace("一轮信息交流结束");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void headl(String protocol) {
		switch (protocol) {
		case "database":
			handlDatabase();
			break;
		case "close":
			isRemoteAvailable = false;
			break;
		default:
			break;
		}
	}

	private void handlDatabase() {
		logger.trace("进入handlDatabase");
		try {
			String method = objectInput.readUTF();
			logger.trace("method：" + method);
			switch (method) {
			case "beginTransactionModel":
				objectOutput.writeBoolean(dao.beginTransactionModel());
				break;
			case "commit":
				objectOutput.writeBoolean(dao.commit());
				break;
			case "rollback":
				objectOutput.writeBoolean(dao.rollback());
				break;
			case "endTransactionModel":
				objectOutput.writeBoolean(dao.endTransactionModel());
				break;
			case "deleteTrain":
				objectOutput.writeBoolean(dao.deleteTrain(objectInput.readUTF()));
				break;
			case "deleteTicket":
				objectOutput.writeBoolean(dao.deleteTicket((Ticket) objectInput.readObject()));
				break;
			case "deleteUser":
				objectOutput.writeBoolean(dao.deleteUser((User) objectInput.readObject()));
				break;
			case "updateRemainingseats":
				objectOutput.writeBoolean(
						dao.updateRemainingseats((Ticket) objectInput.readObject(), objectInput.readUTF()));
				break;
			case "updateTrain":
				objectOutput.writeBoolean(dao.updateTrain((Train) objectInput.readObject()));
				break;
			case "updateTimeAndPrice":
				objectOutput.writeBoolean(dao.updateTimeAndPrice((TimeAndPrice) objectInput.readObject()));
				break;
			case "addRemainingseats":
				objectOutput.writeBoolean(dao.addRemainingseats((Train) objectInput.readObject(),
						(Schedule) objectInput.readObject(), (TimeAndPrice) objectInput.readObject()));
				break;
			case "saveTimeAndPrice":
				objectOutput.writeBoolean(dao.saveTimeAndPrice((TimeAndPrice) objectInput.readObject()));
				break;
			case "saveSchedule":
				objectOutput.writeBoolean(dao.saveSchedule((Schedule) objectInput.readObject()));
				break;
			case "saveTrain":
				objectOutput.writeBoolean(dao.saveTrain((Train) objectInput.readObject()));
				break;
			case "saveUser":
				objectOutput.writeBoolean(dao.saveUser(objectInput.readUTF(), objectInput.readUTF()));
				break;
			case "saveTicket":
				objectOutput.writeBoolean(dao.saveTicket((Ticket) objectInput.readObject()));
				break;
			case "getSeatMapByName":
				objectOutput.writeObject(dao.getSeatMapByName(objectInput.readUTF()));
				break;
			case "getAllUser":
				objectOutput.writeObject(dao.getAllUser());
				break;
			case "getOrders":
				objectOutput.writeObject(dao.getOrders(objectInput.readUTF()));
				break;
			case "getSchedule":
				objectOutput.writeObject(dao.getSchedule(objectInput.readUTF()));
				break;
			case "getTrain":
				objectOutput.writeObject(dao.getTrain(objectInput.readUTF()));
				break;
			case "getAllTimeAndPrices":
				objectOutput.writeObject(dao.getAllTimeAndPrices());
				break;
			case "getTimeAndPrices":
				objectOutput.writeObject(dao.getTimeAndPrices(objectInput.readUTF()));
				break;
			case "getSureMT":
				objectOutput.writeObject(dao.getSureMT(objectInput.readUTF(), (Date) objectInput.readObject(),
						objectInput.readUTF(), objectInput.readUTF()));
				break;
			case "getTransfer":
				objectOutput.writeObject(
						dao.getTransfer(objectInput.readUTF(), objectInput.readUTF(), (Date) objectInput.readObject()));
				break;
			case "getAvailableTrains":
				objectOutput.writeObject(dao.getAvailableTrains(objectInput.readUTF(), objectInput.readUTF(),
						(Date) objectInput.readObject()));
				break;
			case "getUserOrders":
				objectOutput.writeObject(dao.getUserOrders(objectInput.readUTF()));
				break;
			case "getManager":
				objectOutput.writeObject(dao.getManager(objectInput.readInt()));
				break;
			case "getReTickets":
				objectOutput.writeObject(dao.getReTickets((AvailableTrain) objectInput.readObject()));
				break;
			case "getTicketPrice":
				TicketPrice price = dao.getTicketPrice((AvailableTrain) objectInput.readObject());
				logger.trace("getTicketPrice" + price.getA9());
				objectOutput.writeObject(price);
				break;
			case "getUser":
				objectOutput.writeObject(dao.getUser(objectInput.readUTF()));
				break;
			case "getTicket":
				objectOutput.writeObject(
						dao.getTicket(objectInput.readUTF(), objectInput.readUTF(), (Date) objectInput.readObject()));
				break;
			case "getCarriage":
				int[] carr = dao.getCarriage(objectInput.readUTF(), (ReTickets) objectInput.readObject());
				objectOutput.writeInt(carr[0]);
				objectOutput.writeInt(carr[1]);
				break;
			}
			objectOutput.flush();
			logger.trace("输出流刷新完毕");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

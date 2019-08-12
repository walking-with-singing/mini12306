package orm;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class AvailableTrain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String train_no;
	private String from_station_name;
	private Time from_arrive_time;
	private Time from_start_time;
	private int from_station_no;
	private String to_station_name;
	private Time to_arrive_time;
	private int to_station_no;
	private Date date;
	private ReTickets reTickets;
	private TicketPrice ticketPrice;
	
	public TicketPrice getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(TicketPrice ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public ReTickets getReTickets() {
		return reTickets;
	}
	public void setReTickets(ReTickets reTickets) {
		this.reTickets = reTickets;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getFrom_station_name() {
		return from_station_name;
	}
	public void setFrom_station_name(String from_station_name) {
		this.from_station_name = from_station_name;
	}
	public Time getFrom_arrive_time() {
		return from_arrive_time;
	}
	public void setFrom_arrive_time(Time from_arrive_time) {
		this.from_arrive_time = from_arrive_time;
	}
	public Time getFrom_start_time() {
		return from_start_time;
	}
	public void setFrom_start_time(Time from_start_time) {
		this.from_start_time = from_start_time;
	}
	
	public int getFrom_station_no() {
		return from_station_no;
	}
	public void setFrom_station_no(int from_station_no) {
		this.from_station_no = from_station_no;
	}
	public String getTo_station_name() {
		return to_station_name;
	}
	public void setTo_station_name(String to_station_name) {
		this.to_station_name = to_station_name;
	}
	public Time getTo_arrive_time() {
		return to_arrive_time;
	}
	public void setTo_arrive_time(Time to_arrive_time) {
		this.to_arrive_time = to_arrive_time;
	}
	public int getTo_station_no() {
		return to_station_no;
	}
	public void setTo_station_no(int to_station_no) {
		this.to_station_no = to_station_no;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "code:"+code
				+"\ttrain_no:"+train_no
				+"\tfrom_station_name:"+from_station_name
				+"\tfrom_arrive_time:"+from_arrive_time
				+"\tfrom_start_time:"+from_start_time
				+"\tfrom_station_no:"+from_station_no
				+"\tto_station_name:"+to_station_name
				+"\tto_arrive_time:"+to_arrive_time
				+"\tto_station_no:"+to_station_no
				+"\tdate:"+date;
	}
	
}

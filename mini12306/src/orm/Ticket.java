package orm;

import java.sql.Date;
import java.sql.Time;

public class Ticket {
	private long order_time;
	private String user_name;
	private String train_no;
	private String code; 
	private Date date;
	private String from_station_name;
	private Time from_arrive_time;
	private Time from_start_time;
	private String to_station_name;
	private Time to_arrive_time;
	private String seat_type;
	private double price;
	
	
	public long getOrder_time() {
		return order_time;
	}
	public void setOrder_time(long order_time) {
		this.order_time = order_time;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getSeat_type() {
		return seat_type;
	}
	public void setSeat_type(String seat_type) {
		this.seat_type = seat_type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "user_name:"+user_name+"\ttrain_no"+train_no+"\tdate"+date;
	}
}

package orm;


public class TrainLine { 
	private String train_no;
	private String station_name; 
	private String arrive_time;
	private String start_time;
	private int station_no;
	
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public int getStation_no() {
		return station_no;
	}
	public void setStation_no(int i) {
		this.station_no = i;
	}
	
	
}

package ORM;

import java.sql.Date;

public class Schedule {
	private String train_no;
	private Date date;
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "train_no:"+train_no+"\tdate:"+date;
	}
}

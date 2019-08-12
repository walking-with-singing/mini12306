package orm;

import java.io.Serializable;

public class Trainnum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String begin;
	private String end;
	private String train_no;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "code:"+code+"\tbegin:"+begin+"\tend:"+end+"\ttrain_no:"+train_no;
	}
	
}

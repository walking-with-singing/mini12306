package orm;

import java.io.Serializable;

public class TrainMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String train_no;
	private String name;
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

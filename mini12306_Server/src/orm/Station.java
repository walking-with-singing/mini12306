package orm;

import java.io.Serializable;

public class Station implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private String code;
	private String name;
	private String abb;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbb() {
		return abb;
	}
	public void setAbb(String abb) {
		this.abb = abb;
	}
	
	
}

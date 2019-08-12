package orm;

import java.io.Serializable;

public class Price implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String train_no;
	private String station_no;
	private double A9;
	private double P;
	private double M;
	private double O;
	private double A6;
	private double A4;
	private double A3;
	private double A2;
	private double A1;
	private double WZ;
	private double MIN;
	
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getStation_no() {
		return station_no;
	}
	public void setStation_no(String station_no) {
		this.station_no = station_no;
	}
	public double getA9() {
		return A9;
	}
	public void setA9(double a9) {
		A9 = a9;
	}
	public double getP() {
		return P;
	}
	public void setP(double p) {
		P = p;
	}
	public double getM() {
		return M;
	}
	public void setM(double m) {
		M = m;
	}
	public double getO() {
		return O;
	}
	public void setO(double o) {
		O = o;
	}
	public double getA6() {
		return A6;
	}
	public void setA6(double a6) {
		A6 = a6;
	}
	public double getA4() {
		return A4;
	}
	public void setA4(double a4) {
		A4 = a4;
	}
	public double getA3() {
		return A3;
	}
	public void setA3(double a3) {
		A3 = a3;
	}
	public double getA2() {
		return A2;
	}
	public void setA2(double a2) {
		A2 = a2;
	}
	public double getA1() {
		return A1;
	}
	public void setA1(double a1) {
		A1 = a1;
	}
	public double getWZ() {
		return WZ;
	}
	public void setWZ(double wZ) {
		WZ = wZ;
	}
	public double getMIN() {
		return MIN;
	}
	public void setMIN(double mIN) {
		MIN = mIN;
	}

}

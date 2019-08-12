package uiTable;

import orm.TimeAndPrice;

public class AdaptTimeAndPrice implements TableData{

	private TimeAndPrice timeAndPrice;
	public AdaptTimeAndPrice() {
		// TODO Auto-generated constructor stub
	}
	public AdaptTimeAndPrice(TimeAndPrice timeAndPrice) {
		this.timeAndPrice=timeAndPrice;
	}
	public TimeAndPrice getTimeAndPrice() {
		return timeAndPrice;
	}

	public void setTimeAndPrice(TimeAndPrice timeAndPrice) {
		this.timeAndPrice = timeAndPrice;
	}

	@Override
	public Object getA() {
		// TODO Auto-generated method stub
		return timeAndPrice.getTrain_no();
	}

	@Override
	public Object getB() {
		// TODO Auto-generated method stub
		return timeAndPrice.getStation_no();
	}

	@Override
	public Object getC() {
		// TODO Auto-generated method stub
		return timeAndPrice.getStation_name();
	}

	@Override
	public Object getD() {
		// TODO Auto-generated method stub
		return timeAndPrice.getArrive_time();
	}

	@Override
	public Object getE() {
		// TODO Auto-generated method stub
		return timeAndPrice.getStart_time();
	}

	@Override
	public Object getF() {
		if(timeAndPrice.getA9()!=0)
			return timeAndPrice.getA9();
		else
			return "--";
	}

	@Override
	public Object getG() {
		if(timeAndPrice.getP()!=0)
			return timeAndPrice.getP();
		else
			return "--";
	}

	@Override
	public Object getH() {
		if(timeAndPrice.getM()!=0)
			return timeAndPrice.getM();
		else
			return "--";
	}

	@Override
	public Object getI() {
		if(timeAndPrice.getO()!=0)
			return timeAndPrice.getO();
		else
			return "--";
	}

	@Override
	public Object getJ() {
		if(timeAndPrice.getA6()!=0)
			return timeAndPrice.getA6();
		else
			return "--";
	}

	@Override
	public Object getK() {
		if(timeAndPrice.getA4()!=0)
			return timeAndPrice.getA4();
		else
			return "--";
	}

	@Override
	public Object getL() {
		if(timeAndPrice.getA3()!=0)
			return timeAndPrice.getA3();
		else
			return "--";
	}

	@Override
	public Object getM() {
		if(timeAndPrice.getA2()!=0)
			return timeAndPrice.getA2();
		else
			return "--";
	}

	@Override
	public Object getN() {
		if(timeAndPrice.getA1()!=0)
			return timeAndPrice.getA1();
		else
			return "--";
	}

	@Override
	public Object getO() {
		if(timeAndPrice.getWZ()!=0)
			return timeAndPrice.getWZ();
		else
			return "--";
	}

	@Override
	public Object getP() {
		if(timeAndPrice.getMIN()!=0)
			return timeAndPrice.getMIN();
		else
			return "--";
	}

	@Override
	public Object getQ() {
		// TODO Auto-generated method stub
		return null;
	}


}

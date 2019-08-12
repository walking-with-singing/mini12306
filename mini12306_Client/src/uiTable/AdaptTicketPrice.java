package uiTable;

import orm.AvailableTrain;
import orm.TicketPrice;

public class AdaptTicketPrice implements TableData{
	private AvailableTrain aTrain;
	private TicketPrice ticketPrice;
	public AvailableTrain getaTrain() {
		return aTrain;
	}

	public void setaTrain(AvailableTrain aTrain) {
		this.aTrain = aTrain;
		this.ticketPrice=aTrain.getTicketPrice();
	}

	@Override
	public Object getA() {
		return "";
	}

	@Override
	public Object getB() {
		return "";
	}

	@Override
	public Object getC() {
		return "";
	}

	@Override
	public Object getD() {
		if(ticketPrice.getA9()!=0)
			return ticketPrice.getA9();
		else
			return "";
	}

	@Override
	public Object getE() {
		if(ticketPrice.getP()!=0)
			return ticketPrice.getP();
		else
			return "";
	}

	@Override
	public Object getF() {
		if(ticketPrice.getM()!=0)
			return ticketPrice.getM();
		else
			return "";
	}

	@Override
	public Object getG() {
		if(ticketPrice.getO()!=0)
			return ticketPrice.getO();
		else
			return "";
	}

	@Override
	public Object getH() {
		if(ticketPrice.getA6()!=0)
			return ticketPrice.getA6();
		else
			return "";
	}

	@Override
	public Object getI() {
		if(ticketPrice.getA4()!=0)
			return ticketPrice.getA4();
		else
			return "";
	}

	@Override
	public Object getJ() {
		if(ticketPrice.getA3()!=0)
			return ticketPrice.getA3();
		else
			return "";
	}

	@Override
	public Object getK() {
		if(ticketPrice.getA2()!=0)
			return ticketPrice.getA2();
		else
			return "";
	}

	@Override
	public Object getL() {
		if(ticketPrice.getA1()!=0)
			return ticketPrice.getA1();
		else
			return "";
	}

	@Override
	public Object getM() {
		if(ticketPrice.getWZ()!=0)
			return ticketPrice.getWZ();
		else
			return "";
	}

	@Override
	public Object getN() {
		if(ticketPrice.getMIN()!=0)
			return ticketPrice.getMIN();
		else
			return "";
	}

	@Override
	public Object getO() {
		return "";
	}

	@Override
	public Object getP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getQ() {
		// TODO Auto-generated method stub
		return null;
	}

}

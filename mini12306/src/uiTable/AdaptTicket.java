package uiTable;

import java.sql.Date;

import orm.Ticket;

public class AdaptTicket implements TableData{
	private Ticket ticket;
	public AdaptTicket() {
		// TODO Auto-generated constructor stub
	}
	public AdaptTicket(Ticket ticket) {
		this.ticket=ticket;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	@Override
	public Object getA() {
		// TODO Auto-generated method stub
		return new Date(ticket.getOrder_time());
	}

	@Override
	public Object getB() {
		// TODO Auto-generated method stub
		return ticket.getCode();
	}

	@Override
	public Object getC() {
		// TODO Auto-generated method stub
		return ticket.getDate();
	}

	@Override
	public Object getD() {
		// TODO Auto-generated method stub
		return ticket.getFrom_station_name();
	}

	@Override
	public Object getE() {
		// TODO Auto-generated method stub
		return ticket.getFrom_arrive_time();
	}

	@Override
	public Object getF() {
		// TODO Auto-generated method stub
		return ticket.getFrom_start_time();
	}

	@Override
	public Object getG() {
		// TODO Auto-generated method stub
		return ticket.getTo_station_name();
	}

	@Override
	public Object getH() {
		// TODO Auto-generated method stub
		return ticket.getTo_arrive_time();
	}

	@Override
	public Object getI() {
		// TODO Auto-generated method stub
		return ticket.getSeat_type();
	}

	@Override
	public Object getJ() {
		// TODO Auto-generated method stub
		return ticket.getPrice();
	}

	@Override
	public Object getK() {
		// TODO Auto-generated method stub
		return ticket.getCarriage_no();
	}

	@Override
	public Object getL() {
		// TODO Auto-generated method stub
		return ticket.getSeat_no();
	}

	@Override
	public Object getM() {
		// TODO Auto-generated method stub
		return "¸ÄÇ©";
	}

	@Override
	public Object getN() {
		// TODO Auto-generated method stub
		return "ÍËÆ±";
	}

	@Override
	public Object getO() {
		// TODO Auto-generated method stub
		return null;
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

package uiTable;

import orm.Schedule;

public class AdaptSchedule implements TableData{
	private Schedule schedule;
	public AdaptSchedule() {
		// TODO Auto-generated constructor stub
	}
	public AdaptSchedule(Schedule sche) {
		this.schedule=sche;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule sche) {
		this.schedule = sche;
	}

	@Override
	public Object getA() {
		// TODO Auto-generated method stub
		return schedule.getTrain_no();
	}

	@Override
	public Object getB() {
		// TODO Auto-generated method stub
		return schedule.getDate();
	}

	@Override
	public Object getC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getD() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getE() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getG() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getH() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getJ() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getN() {
		// TODO Auto-generated method stub
		return null;
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

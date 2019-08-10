package uiTable;


import orm.AvailableTrain;
import orm.ReTickets;

public class AdaptAvaTrain implements TableData{

	private AvailableTrain aTrain;
	private ReTickets reTickets;
	public AvailableTrain getaTrain() {
		return aTrain;
	}
	public void setaTrain(AvailableTrain aTrain) {
		this.aTrain = aTrain;
		this.reTickets=aTrain.getReTickets();
	}

	@Override
	public Object getA() {
		return aTrain.getCode();
	}

	@Override
	public Object getB() {
		return aTrain.getFrom_station_name()+"--"+aTrain.getTo_station_name();
	}

	@Override
	public Object getC() {
		return aTrain.getFrom_start_time()+"--"+aTrain.getTo_arrive_time();
	}

	@Override
	public Object getD() {
		if(reTickets.getA9()!=0)
			return reTickets.getA9();
		else
			return "--";
	}

	@Override
	public Object getE() {
		if(reTickets.getP()!=0)
			return reTickets.getP();
		else
			return "--";
	}

	@Override
	public Object getF() {
		if(reTickets.getM()!=0)
			return reTickets.getM();
		else
			return "--";
	}

	@Override
	public Object getG() {
		if(reTickets.getO()!=0)
			return reTickets.getO();
		else
			return "--";
	}

	@Override
	public Object getH() {
		if(reTickets.getA6()!=0)
			return reTickets.getA6();
		else
			return "--";
	}

	@Override
	public Object getI() {
		if(reTickets.getA4()!=0)
			return reTickets.getA4();
		else
			return "--";
	}

	@Override
	public Object getJ() {
		if(reTickets.getA3()!=0)
			return reTickets.getA3();
		else
			return "--";
	}

	@Override
	public Object getK() {
		if(reTickets.getA2()!=0)
			return reTickets.getA2();
		else
			return "--";
	}

	@Override
	public Object getL() {
		if(reTickets.getA1()!=0)
			return reTickets.getA1();
		else
			return "--";
	}

	@Override
	public Object getM() {
		if(reTickets.getWZ()!=0)
			return reTickets.getWZ();
		else
			return "--";
	}

	@Override
	public Object getN() {
		if(reTickets.getMIN()!=0)
			return reTickets.getMIN();
		else
			return "--";
	}
	@Override
	public Object getO() {
		// TODO Auto-generated method stub
		return "Ô¤¶¨";
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

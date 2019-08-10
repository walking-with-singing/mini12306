package uiTable;

import orm.Train;

public class AdaptTrain implements TableData{
	private Train train;
	public AdaptTrain() {
		// TODO Auto-generated constructor stub
	}
	public AdaptTrain(Train train) {
		this.train=train;
	}
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	@Override
	public Object getA() {
		// TODO Auto-generated method stub
		return train.getTrain_no();
	}

	@Override
	public Object getB() {
		// TODO Auto-generated method stub
		return train.getCode();
	}

	@Override
	public Object getC() {
		if(train.getA9()!=0)
			return train.getA9();
		else
			return "--";

	}

	@Override
	public Object getD() {
		if(train.getP()!=0)
			return train.getP();
		else
			return "--";
	}

	@Override
	public Object getE() {
		if(train.getM()!=0)
			return train.getM();
		else
			return "--";
	}

	@Override
	public Object getF() {
		if(train.getO()!=0)
			return train.getO();
		else
			return "--";
	}

	@Override
	public Object getG() {
		if(train.getA6()!=0)
			return train.getA6();
		else
			return "--";
	}

	@Override
	public Object getH() {
		if(train.getA4()!=0)
			return train.getA4();
		else
			return "--";
	}

	@Override
	public Object getI() {
		if(train.getA3()!=0)
			return train.getA3();
		else
			return "--";
	}

	@Override
	public Object getJ() {
		if(train.getA2()!=0)
			return train.getA2();
		else
			return "--";
	}

	@Override
	public Object getK() {
		if(train.getA1()!=0)
			return train.getA1();
		else
			return "--";
		}

	@Override
	public Object getL() {
		if(train.getWZ()!=0)
			return train.getWZ();
		else
			return "--";
	}

	@Override
	public Object getM() {
		if(train.getMIN()!=0)
			return train.getMIN();
		else
			return "--";
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

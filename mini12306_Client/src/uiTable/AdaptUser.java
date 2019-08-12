package uiTable;

import orm.User;

public class AdaptUser implements TableData{
	private User user;
	
	public AdaptUser(User user) {
		this.user=user;
	}
	@Override
	public Object getA() {
		// TODO Auto-generated method stub
		return user.getName();
	}
	@Override
	public Object getB() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}
	@Override
	public Object getC() {
		// TODO Auto-generated method stub
		return "×¢Ïú";
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}

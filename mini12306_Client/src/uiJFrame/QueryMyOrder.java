package uiJFrame;


import orm.User;
import uiJPanel.QueryMyOrderPanel;

public class QueryMyOrder extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryMyOrder(User user) {
		super(new QueryMyOrderPanel(user));
		this.setVisible(true);
		// TODO Auto-generated constructor stub
	}

}

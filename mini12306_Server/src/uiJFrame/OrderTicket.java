package uiJFrame;

import orm.AvailableTrain;
import orm.User;
import uiJPanel.OrderTicketPanel;

public class OrderTicket extends MyFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderTicket(AvailableTrain aTrain,User user) {
		super(new OrderTicketPanel(aTrain,user));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}

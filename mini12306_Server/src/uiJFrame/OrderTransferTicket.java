package uiJFrame;

import orm.AvailableTrain;
import orm.User;
import uiJPanel.OrderTransferTicketPanel;

public class OrderTransferTicket extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderTransferTicket(AvailableTrain[] aTrain,User user) {
		super(new OrderTransferTicketPanel(aTrain,user));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}

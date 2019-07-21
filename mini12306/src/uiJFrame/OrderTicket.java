package uiJFrame;

import orm.AvailableTrain;
import uiJPanel.OrderTicketPanel;

public class OrderTicket extends MyFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderTicket(AvailableTrain aTrain) {
		super(new OrderTicketPanel(aTrain));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}

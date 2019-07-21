package uiJFrame;

import orm.AvailableTrain;
import uiJPanel.OrderTransferTicketPanel;

public class OrderTransferTicket extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderTransferTicket(AvailableTrain[] aTrain) {
		super(new OrderTransferTicketPanel(aTrain));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}

package uiJFrame;

import orm.AvailableTrain;
import orm.Ticket;
import orm.User;
import uiJPanel.ReOrderTicketPanel;

public class ReOrderTicket extends MyFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReOrderTicket(AvailableTrain aTrain,Ticket ticket,User user) {
		super(new ReOrderTicketPanel(aTrain,ticket,user));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}

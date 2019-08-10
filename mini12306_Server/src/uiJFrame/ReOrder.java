package uiJFrame;

import orm.Ticket;
import orm.User;
import uiJPanel.ReOrderPanel;

public class ReOrder extends MyFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReOrder(Ticket ticket,User user) {
		super(new ReOrderPanel(ticket,user));
		this.setVisible(true);
	}

}

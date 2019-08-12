package uiJFrame;

import orm.User;
import uiJPanel.UserHomePanel;

public class UserHome extends MyFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserHome(User user) {
		super(new UserHomePanel(user));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

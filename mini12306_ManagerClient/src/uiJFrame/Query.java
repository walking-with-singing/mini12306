package uiJFrame;

import orm.User;
import uiJPanel.QueryPanel;

public class Query extends MyFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -37319014685686319L;
	public Query(User user) {
		super(new QueryPanel(user));
		this.setVisible(true);
	}
	
}

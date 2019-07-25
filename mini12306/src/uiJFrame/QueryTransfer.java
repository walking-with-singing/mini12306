package uiJFrame;

import orm.User;
import uiJPanel.QueryTransferPanel;

public class QueryTransfer extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryTransfer(User user) {
		super(new QueryTransferPanel(user));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}

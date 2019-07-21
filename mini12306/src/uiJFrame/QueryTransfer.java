package uiJFrame;

import uiJPanel.QueryTransferPanel;

public class QueryTransfer extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryTransfer() {
		super(new QueryTransferPanel());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}

package uiJFrame;

import uiJPanel.ManagerUserPanel;

public class ManagerUser extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManagerUser() {
		super(new ManagerUserPanel());
		this.setVisible(true);
	}

}

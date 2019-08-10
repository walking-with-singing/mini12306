package uiJFrame;

import orm.Manager;
import uiJPanel.ManagerHomePanel;

public class ManagerHome extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManagerHome(Manager manager) {
		super(new ManagerHomePanel(manager));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}

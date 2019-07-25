package uiJFrame;

import uiJPanel.ManagerSignInPanel;

public class ManagerSignIn extends MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManagerSignIn() {
		super(new ManagerSignInPanel());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}

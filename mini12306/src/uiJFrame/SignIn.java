package uiJFrame;

import uiJPanel.SignInPanel;

public class SignIn extends MyFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SignIn() {
		super(new SignInPanel());
		this.setVisible(true);
	}
	
}

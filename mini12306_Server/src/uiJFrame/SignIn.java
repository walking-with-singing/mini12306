package uiJFrame;

import uiJPanel.SignInPanel;

public class SignIn extends MyFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SignIn() {
		super(new SignInPanel());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}

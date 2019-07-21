package uiJPanel;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseAccess.DAO;

public abstract class MyPanel {
	//Tool
	protected DAO dao=new DAO();
	protected Logger logger=LogManager.getLogger();
	public  JPanel getNorth() {
		JPanel panel=new JPanel();
		return panel;
	}
	public JPanel getSouth() {
		JPanel panel=new JPanel();
		return panel;
	}

	public JPanel getWest() {
		JPanel panel=new JPanel();
		return panel;
	}

	public JPanel getEast() {
		JPanel panel=new JPanel();
		return panel;
	}

	public JPanel getCenter() {
		JPanel panel=new JPanel();
		return panel;
	}
	protected abstract void addListener();
}

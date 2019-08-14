package uiJPanel;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import netToolset.DAO;

public abstract class MyPanel {
	protected JFrame frame;
	//Tool
	protected DAO dao=new DAO();
	protected Logger logger=LogManager.getLogger();
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	protected final int width=dScreen.width;
	protected final int height=dScreen.height;
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
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}

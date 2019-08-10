package uiJFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uiJPanel.MyPanel;

public abstract class MyFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//创建logger
	private Logger logger=LogManager.getLogger();
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int width=dScreen.width;
	private final int height=dScreen.height;
	MyPanel myPanel;
	public MyFrame(MyPanel myPanel) {
		logger.info("width:"+width+"\theight:"+height);
		this.myPanel=myPanel;
		myPanel.setFrame(this);
		//非全屏时尺寸和位置
		this.setSize(width/2,height/2 );
		this.setLocationRelativeTo(null);
		//初始即全屏
		this.setExtendedState(MAXIMIZED_BOTH);
		//默认关闭窗口设置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//开始布局
		this.setLayout(new BorderLayout());
		this.add(myPanel.getNorth(),BorderLayout.NORTH);
		this.add(myPanel.getSouth(),BorderLayout.SOUTH);
		this.add(myPanel.getWest(),BorderLayout.WEST);
		this.add(myPanel.getEast(),BorderLayout.EAST);
		this.add(myPanel.getCenter(),BorderLayout.CENTER);
		this.setVisible(true);
		
	}
	
}

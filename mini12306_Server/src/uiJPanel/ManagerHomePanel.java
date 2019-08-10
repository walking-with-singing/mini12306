package uiJPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import orm.Manager;
import uiJFrame.ManagerTrainNumber;
import uiJFrame.ManagerUser;

public class ManagerHomePanel extends MyPanel {
	Manager manager;
	JButton getUsers=new JButton("用户管理");
	JButton getTrain_num=new JButton("车次管理");
	public ManagerHomePanel(Manager manager) {
		this.manager=manager;
		addListener();
	}
		@Override
	public JPanel getWest() {
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(getUsers);
		panel.add(getTrain_num);
		return panel;
	
	}
	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		getUsers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getUsers();
			}
		});
		getTrain_num.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getTrain_num();
			}
		});
	}
	public void getUsers()
	{
		new ManagerUser();
	}
	public void getTrain_num()
	{
		new ManagerTrainNumber();
	}
}

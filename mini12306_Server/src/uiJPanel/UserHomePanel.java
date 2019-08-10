package uiJPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import orm.User;
import uiJFrame.Query;
import uiJFrame.QueryMyOrder;

public class UserHomePanel extends MyPanel {
	User user;
	JButton buy=new JButton("¹ºÆ±");
	JButton getMyOrder=new JButton("ÎÒµÄ¶©µ¥");
	public UserHomePanel(User user) {
		this.user=user;
		addListener();
	}
	@Override
	public JPanel getWest() {
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(buy);
		panel.add(getMyOrder);
		return panel;
	
	}
	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		buy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buy();
			}
		});
		getMyOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getMyOrder();
			}
		});
	}
	private void buy()
	{
		new Query(user);
	}
	private void getMyOrder()
	{
		new QueryMyOrder(user);
	}
}

package uiJPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import orm.Ticket;
import orm.User;
import uiJFrame.ReOrder;
import uiTable.AdaptTicket;
import uiTable.MyTableModel;
import uiTable.QueryMyOrderTableModel;

public class QueryMyOrderPanel extends MyPanel {
	User user;
	JPanel center=new JPanel();
	JButton update=new JButton("刷新");
	MyTableModel tableModel=new QueryMyOrderTableModel();
	JTable table=new JTable(tableModel);
	JScrollPane jsPanel=new JScrollPane(table);
	public QueryMyOrderPanel(User user) {
		this.user=user;
		tableModel.setTableDatas(dao.getOrders(user.getName()));
		addListener();
	}
	@Override
	public JPanel getNorth() {
		JPanel panel =new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(update);
		return panel;
	}
	@Override
	public JPanel getCenter() {
		center.setLayout(new BorderLayout());
		center.add(jsPanel);
		return center;
	}
	
	@Override
	protected void addListener() {
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(e.getValueIsAdjusting()) return;
				int column=table.getSelectedColumn();
				int row=table.getSelectedRow();
				if(column==13)
				{
					removeOrder(row);
				}
				else if(column==12)
				{
					reOrder(row);
				}
			}
		});
	}
	private void update() {
		tableModel.setTableDatas(dao.getOrders(user.getName()));
		table.updateUI();
	}
	private void removeOrder(int row)
	{
		int op=JOptionPane.showConfirmDialog(center, "确定退票？");
		if(JOptionPane.OK_OPTION==op)
		{
			Ticket ticket=((AdaptTicket)tableModel.getTableDatas().get(row)).getTicket();
			dao.beginTransactionModel();
			if(dao.deleteTicket(ticket)&&dao.updateRemainingseats(ticket, "+"))
			{
				dao.commit();
				JOptionPane.showMessageDialog(center, "退票成功！");
				tableModel.getTableDatas().remove(row);
				table.updateUI();
			}
			else
			{
				dao.rollback();
				JOptionPane.showMessageDialog(center, "退票失败，请稍后重试。");
			}
			dao.endTransactionModel();
		}
		
	}
	private void reOrder(int row)
	{
		int op=JOptionPane.showConfirmDialog(center, "确定改签？");
		if(JOptionPane.OK_OPTION==op)
		{ 
			Ticket ticket=((AdaptTicket)tableModel.getTableDatas().get(row)).getTicket();
			new ReOrder(ticket,user);
		}
	}
}

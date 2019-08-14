package uiJPanel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import netToolset.DAO;
import orm.AvailableTrain;
import orm.Ticket;
import orm.User;
import uiJFrame.ReOrderTicket;
import uiTable.AdaptAvaTrain;
import uiTable.AdaptTicketPrice;
import uiTable.MyTableModel;
import uiTable.QueryTableModel;

public class ReOrderPanel extends MyPanel {
	User user;
	Ticket ticket;
	JPanel center=new JPanel();
	
	MyTableModel tableModel=new QueryTableModel();
	JTable table=new JTable(tableModel);
	JScrollPane jsPanel=new JScrollPane(table);
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int width=dScreen.width;
	//Tool
	private DAO dao=new DAO();
	private Logger logger=LogManager.getLogger();
	//构造方法
	public ReOrderPanel(Ticket ticket,User user) {
		this.ticket=ticket;
		this.user=user;
		// 设置列宽度
        table.getColumnModel().getColumn(1).setPreferredWidth(width/10);
        table.getColumnModel().getColumn(2).setPreferredWidth(width/10);
        JXDatePicker datepick = new JXDatePicker(new Date(System.currentTimeMillis()));
        JOptionPane.showMessageDialog(center, datepick, "请选择乘车日期", 1);
        tableModel.setTableDatas(dao.getAvailableTrains(ticket.getFrom_station_name(), ticket.getTo_station_name(),new Date(datepick.getDate().getTime())));
        addListener();
	}
	@Override
	public JPanel getCenter() {
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(jsPanel,BorderLayout.CENTER);
		return panel;
	}

	protected void addListener()
	{
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) 
				{
					logger.debug("检测到被修改");
					return;//检查是否正在被修改！
				}
				int row=table.getSelectedRow();
				int column=table.getSelectedColumn();
				if(tableModel.getTableDatas().get(row).getClass()==AdaptAvaTrain.class)
				{
					logger.debug("row:"+row+"\ttableModel.getTableDatas().size():"+tableModel.getTableDatas().size());
					if(column==14)
						order(row);
					else if(row==tableModel.getTableDatas().size()-1||tableModel.getTableDatas().get(row+1).getClass()!=AdaptTicketPrice.class)
						showPrice(row);
					else 
						hidePrice(row);

				}
				else
				{
					
				}
			}
			
		});
	}	
	private void showPrice(int row)
	{
		AvailableTrain aTrain=((AdaptAvaTrain)tableModel.getTableDatas().get(row)).getaTrain();
		dao.getTicketPrice(aTrain);
		AdaptTicketPrice atp=new AdaptTicketPrice();
		atp.setaTrain(aTrain);
		tableModel.getTableDatas().add(row+1,atp);
		table.updateUI();
	}
	private void hidePrice(int row)
	{
		tableModel.getTableDatas().remove(row+1);
		table.updateUI();
	}
	private void order(int row)
	{
		logger.debug("改签："+row);
		AvailableTrain aTrain=((AdaptAvaTrain)tableModel.getTableDatas().get(row)).getaTrain();
		if(aTrain.getTicketPrice()==null)
		{
			dao.getTicketPrice(aTrain);
		}
		new ReOrderTicket(aTrain,ticket,user);
	}
	
}

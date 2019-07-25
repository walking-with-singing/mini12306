package uiJPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import databaseAccess.DAO;
import orm.AvailableTrain;
import orm.User;
import uiJFrame.OrderTransferTicket;
import uiTable.AdaptAvaTrain;
import uiTable.AdaptTicketPrice;
import uiTable.Airline;
import uiTable.QueryTableModel;
import uiTable.TableData;

public class QueryTransferPanel extends MyPanel {
	User user;
	JButton submit=new JButton("查询");
	JTextField iFrom=new JTextField(20);
	JTextField iTo=new JTextField(20);
	JTextField iMiddle=new JTextField(20);
	JXDatePicker datepick = new JXDatePicker(new Date(System.currentTimeMillis()));
	
	QueryTableModel tableModel=new QueryTableModel();
	JTable table=new JTable(tableModel);
	JScrollPane jsPanel=new JScrollPane(table);
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int width=dScreen.width;
	//Tool
	private DAO dao=new DAO();
	private Logger logger=LogManager.getLogger();
	//构造方法
	public QueryTransferPanel(User user) {
		this.user=user;
		// 设置列宽度
        table.getColumnModel().getColumn(1).setPreferredWidth(width/10);
        table.getColumnModel().getColumn(2).setPreferredWidth(width/10);

		addListener();
	}
	@Override
	public JPanel getNorth() {
		JPanel panel=new JPanel();
//		panel.setLayout(new GridLayout(1,7));
		JLabel tForm =new JLabel("出发地");
		JLabel tTo =new JLabel("目的地");
		JLabel tMiddle=new JLabel("指定换乘站");
		JLabel tDate =new JLabel("目的地");
		panel.add(tForm);
		panel.add(iFrom);
		panel.add(tTo);
		panel.add(iTo);
		panel.add(tMiddle);
		panel.add(iMiddle);
		panel.add(tDate);
		panel.add(datepick);
		panel.add(submit);
		return panel;
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
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitQuery();
			}
		});
		
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
					if(column==14)
						order(row);
					else if(tableModel.getTableDatas().get(row+1).getClass()!=AdaptTicketPrice.class)
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
	private void submitQuery()
	{
		String fromStation=iFrom.getText();
		String toStation=iTo.getText();
		Date date=new Date(datepick.getDate().getTime());
		fromStation=fromStation+"%";
		toStation=toStation+"%";
		String mid=iMiddle.getText();
		List<AvailableTrain> aTrains;
		if(mid.length()==0)
			aTrains=dao.getTransfer(fromStation, toStation, date);
		else
			aTrains=dao.getSureMT(mid, date, fromStation, toStation);
		for(AvailableTrain aTrain:aTrains)
			dao.getReTickets(aTrain);
		tableModel.setTableDatas(aTrains);
		List<TableData> l=tableModel.getTableDatas();
		for(int i=0,c=1;i<l.size();i++,c++)
		{
			if(c==2)
			{
				tableModel.getTableDatas().add(++i,new Airline());
				c=0;
			}		
		}
		table.updateUI();	
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
		logger.debug("预定："+row);
		AvailableTrain[] aTrain=new AvailableTrain[2];
		if(row-1<0||tableModel.getTableDatas().get(row-1).getClass()==Airline.class)
		{
			aTrain[0]=((AdaptAvaTrain)tableModel.getTableDatas().get(row)).getaTrain();
			while(tableModel.getTableDatas().get(++row).getClass()!=AdaptAvaTrain.class);
			aTrain[1]=((AdaptAvaTrain)tableModel.getTableDatas().get(row)).getaTrain();
		}
		else
		{
			aTrain[1]=((AdaptAvaTrain)tableModel.getTableDatas().get(row)).getaTrain();
			while(tableModel.getTableDatas().get(--row).getClass()!=AdaptAvaTrain.class);
			aTrain[0]=((AdaptAvaTrain)tableModel.getTableDatas().get(row)).getaTrain();
		}
		for(AvailableTrain a:aTrain)
		{
			if(a.getTicketPrice()==null)
			{
				dao.getTicketPrice(a);
			}
		}

		new OrderTransferTicket(aTrain,user);
	}


}

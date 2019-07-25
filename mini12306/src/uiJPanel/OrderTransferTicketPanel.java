package uiJPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databaseAccess.DAO;
import orm.AvailableTrain;
import orm.Ticket;
import orm.TicketPrice;
import orm.User;

public class OrderTransferTicketPanel extends MyPanel {
	AvailableTrain[] aTrain;
	User user;
	TicketPrice[] ticketPrice=new TicketPrice[2];
	JPanel center=new JPanel();
	JComboBox<String> seatTypes1=new JComboBox<>();
	JComboBox<String> seatTypes2=new JComboBox<>();
	JButton submit=new JButton("提交订单");
	//Tool
	private DAO dao=new DAO();
	private Logger logger=LogManager.getLogger();
	public OrderTransferTicketPanel(AvailableTrain[] aTrain,User user) {
		this.user=user;
		this.aTrain=aTrain;
		ticketPrice[0]=aTrain[0].getTicketPrice();
		ticketPrice[1]=aTrain[1].getTicketPrice();
		addListener();
	}
 	@Override
	public JPanel getNorth() {
		JPanel panel =new JPanel();
		panel.setLayout(new GridLayout(4,3));
		String message1=aTrain[0].getDate()+"\t"+aTrain[0].getCode()+"次\t"
				+aTrain[0].getFrom_station_name()+"站（"+aTrain[0].getFrom_start_time()+"开)"
				+"----"+aTrain[0].getTo_station_name()+"站（"+aTrain[0].getTo_arrive_time()+"到)";
		
		String message2=aTrain[1].getDate()+"\t"+aTrain[1].getCode()+"次\t"
				+aTrain[1].getFrom_station_name()+"站（"+aTrain[1].getFrom_start_time()+"开)"
				+"----"+aTrain[1].getTo_station_name()+"站（"+aTrain[1].getTo_arrive_time()+"到)";
		
		String warn="温馨提示：同一用户同一乘车日期同一车次只能购买一张车票。";
		JLabel label1=new JLabel(message1);
		JLabel label2=new JLabel(message2);
		JLabel labelW=new JLabel(warn);
		
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(label1);
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(label2);
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(labelW);
		panel.add(new JLabel());
		
		return panel;
	}
	@Override
	public JPanel getCenter() {
//		"商务座", "特等座", "一等座", "二等座", "高级软卧", "软卧", "硬卧", "软座", "硬座", "无座", "其他","备注"
		if(ticketPrice[0].getA9()!=0)
			seatTypes1.addItem("商务座 ("+ticketPrice[0].getA9()+")");
		if(ticketPrice[0].getP()!=0)
			seatTypes1.addItem("特等座 ("+ticketPrice[0].getP()+")");
		if(ticketPrice[0].getM()!=0)
			seatTypes1.addItem("一等座 ("+ticketPrice[0].getM()+")");
		if(ticketPrice[0].getO()!=0)
			seatTypes1.addItem("二等座 ("+ticketPrice[0].getO()+")");
		if(ticketPrice[0].getA6()!=0)
			seatTypes1.addItem("高级软卧 ("+ticketPrice[0].getA6()+")");
		if(ticketPrice[0].getA4()!=0)
			seatTypes1.addItem("软卧 ("+ticketPrice[0].getA4()+")");
		if(ticketPrice[0].getA3()!=0)
			seatTypes1.addItem("硬卧 ("+ticketPrice[0].getA3()+")");
		if(ticketPrice[0].getA2()!=0)
			seatTypes1.addItem("软座 ("+ticketPrice[0].getA2()+")");
		if(ticketPrice[0].getA1()!=0)
			seatTypes1.addItem("硬座 ("+ticketPrice[0].getA1()+")");
		if(ticketPrice[0].getWZ()!=0)
			seatTypes1.addItem("无座 ("+ticketPrice[0].getWZ()+")");
		if(ticketPrice[0].getMIN()!=0)
			seatTypes1.addItem("其他 ("+ticketPrice[0].getMIN()+")");
		if(ticketPrice[1].getA9()!=0)
			seatTypes2.addItem("商务座 ("+ticketPrice[1].getA9()+")");
		if(ticketPrice[1].getP()!=0)
			seatTypes2.addItem("特等座 ("+ticketPrice[1].getP()+")");
		if(ticketPrice[1].getM()!=0)
			seatTypes2.addItem("一等座 ("+ticketPrice[1].getM()+")");
		if(ticketPrice[1].getO()!=0)
			seatTypes2.addItem("二等座 ("+ticketPrice[1].getO()+")");
		if(ticketPrice[1].getA6()!=0)
			seatTypes2.addItem("高级软卧 ("+ticketPrice[1].getA6()+")");
		if(ticketPrice[1].getA4()!=0)
			seatTypes2.addItem("软卧 ("+ticketPrice[1].getA4()+")");
		if(ticketPrice[1].getA3()!=0)
			seatTypes2.addItem("硬卧 ("+ticketPrice[1].getA3()+")");
		if(ticketPrice[1].getA2()!=0)
			seatTypes2.addItem("软座 ("+ticketPrice[1].getA2()+")");
		if(ticketPrice[1].getA1()!=0)
			seatTypes2.addItem("硬座 ("+ticketPrice[1].getA1()+")");
		if(ticketPrice[1].getWZ()!=0)
			seatTypes2.addItem("无座 ("+ticketPrice[1].getWZ()+")");
		if(ticketPrice[1].getMIN()!=0)
			seatTypes2.addItem("其他 ("+ticketPrice[1].getMIN()+")");
		JLabel setSeatType1=new JLabel(aTrain[0].getFrom_station_name()+"站"+aTrain[0].getTo_station_name()+"站"+"席别：");
		JLabel setSeatType2=new JLabel(aTrain[1].getFrom_station_name()+"站"+aTrain[1].getTo_station_name()+"站"+"席别：");
		
		center.setLayout(new GridLayout(4,1));
		//第0行
		JPanel row0=new JPanel();
		row0.add(setSeatType1);
		row0.add(seatTypes1);
		//第1行
		JPanel row1 = new JPanel();
		row1.add(setSeatType2);
		row1.add(seatTypes2);
		//空行
		JPanel row2 = new JPanel();
		//第3行
		JPanel row3 = new JPanel();
		row3.add(submit);
		//所有行添加进center
		center.add(row2);
		center.add(row0);
		center.add(row1);
		center.add(row3);
		return center;
	}
	@Override
	protected void addListener() {
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				submit();
			}
		});
		
	}
	private void submit()
	{
		//转站前车票
		Ticket ticket1=new Ticket();
		ticket1.setOrder_time(System.currentTimeMillis());
		ticket1.setUser_name(user.getName());
		ticket1.setTrain_no(aTrain[0].getTrain_no());
		ticket1.setCode(aTrain[0].getCode());
		ticket1.setDate(aTrain[0].getDate());
		ticket1.setFrom_station_name(aTrain[0].getFrom_station_name());
		ticket1.setFrom_station_no(aTrain[0].getFrom_station_no());
		ticket1.setFrom_arrive_time(aTrain[0].getFrom_arrive_time());
		ticket1.setFrom_start_time(aTrain[0].getFrom_start_time());
		ticket1.setTo_station_name(aTrain[0].getTo_station_name());
		ticket1.setTo_station_no(aTrain[0].getTo_station_no());
		ticket1.setTo_arrive_time(aTrain[0].getTo_arrive_time());
		String typeAndprice=(String) seatTypes1.getSelectedItem();
		logger.debug("typeAndprice:"+typeAndprice);
		String[] tp=typeAndprice.split("\\(");
		String type=tp[0].trim();
		String price=tp[1].substring(0, tp[1].length()-1);
		logger.debug("type:"+type+"\tprice:"+price);
		ticket1.setSeat_type(type);
		ticket1.setPrice(Double.valueOf(price));
		int[] carriageAndSeat_no=dao.getCarriage(type, aTrain[0].getReTickets());
		ticket1.setCarriage_no(carriageAndSeat_no[0]);
		ticket1.setSeat_no(carriageAndSeat_no[1]);
		//转站后车票
		Ticket ticket2=new Ticket();
		ticket2.setOrder_time(System.currentTimeMillis());
		ticket2.setUser_name(user.getName());
		ticket2.setTrain_no(aTrain[1].getTrain_no());
		ticket2.setCode(aTrain[1].getCode());
		ticket2.setDate(aTrain[1].getDate());
		ticket2.setFrom_station_name(aTrain[1].getFrom_station_name());
		ticket2.setFrom_station_no(aTrain[1].getFrom_station_no());
		ticket2.setFrom_arrive_time(aTrain[1].getFrom_arrive_time());
		ticket2.setFrom_start_time(aTrain[1].getFrom_start_time());
		ticket2.setTo_station_name(aTrain[1].getTo_station_name());
		ticket2.setTo_station_no(aTrain[1].getTo_station_no());
		ticket2.setTo_arrive_time(aTrain[1].getTo_arrive_time());
		String typeAndprice2=(String) seatTypes2.getSelectedItem();
		logger.debug("typeAndprice:"+typeAndprice);
		String[] tp2=typeAndprice2.split("\\(");
		String type2=tp2[0].trim();
		String price2=tp2[1].substring(0, tp2[1].length()-1);
		logger.debug("type:"+type+"\tprice:"+price);
		ticket2.setSeat_type(type2);
		ticket2.setPrice(Double.valueOf(price2));
		int[] carriageAndSeat_no2=dao.getCarriage(type2, aTrain[1].getReTickets());
		ticket2.setCarriage_no(carriageAndSeat_no2[0]);
		ticket2.setSeat_no(carriageAndSeat_no2[1]);
		//事物模式尝试存
		if(dao.beginTransactionModel())
		{
			if(dao.getTicket(ticket1.getUser_name(),ticket1.getTrain_no(),ticket1.getDate())==null)
			{
				if(dao.getTicket(ticket2.getUser_name(),ticket2.getTrain_no(),ticket2.getDate())==null)
				{
					if(dao.saveTicket(ticket1)&&dao.updateRemainingseats(ticket1,"-")&&dao.saveTicket(ticket2)&&dao.updateRemainingseats(ticket2,"-")&&dao.commit())
					{
						JOptionPane.showMessageDialog(center, "预定成功！");
						
					}
					else
					{
						JOptionPane.showMessageDialog(center, "预定失败，请稍后重试。");
						dao.rollback();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(center, "预定失败，"+aTrain[1].getCode()+"车次该日期您已有预定。");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(center, "预定失败，"+aTrain[0].getCode()+"车次该日期您已有预定。");
			}
			dao.endTransactionModel();
		}
		else
		{
			JOptionPane.showMessageDialog(center, "事物模式开启失败");
		}
				
	
			

	}
}

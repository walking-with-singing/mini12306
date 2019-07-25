package uiJPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import orm.AvailableTrain;
import orm.Ticket;
import orm.TicketPrice;
import orm.User;

public class ReOrderTicketPanel extends MyPanel {
	User user;
	AvailableTrain aTrain;
	TicketPrice ticketPrice;
	JPanel center=new JPanel();
	JComboBox<String> seatTypes=new JComboBox<>();
	JButton submit=new JButton("提交订单");
	Ticket oldTicket;
	public ReOrderTicketPanel(AvailableTrain aTrain,Ticket ticket,User user) {
		this.aTrain=aTrain;
		this.oldTicket=ticket;
		this.ticketPrice=aTrain.getTicketPrice();
		this.user=user;
		addListener();
	}
	@Override
	public JPanel getNorth() {
		JPanel panel =new JPanel();
		String message=aTrain.getDate()+"\t"+aTrain.getCode()+"次\t"
				+aTrain.getFrom_station_name()+"站（"+aTrain.getFrom_start_time()+"开)"
				+"----"+aTrain.getTo_station_name()+"站（"+aTrain.getTo_arrive_time()+"到)";
		String warn="温馨提示：同一用户同一乘车日期同一车次只能购买一张车票。";
		JLabel lable1=new JLabel(message);
		JLabel lable2=new JLabel(warn);
		panel.add(lable1);
		panel.add(lable2);
		return panel;
	}
	@Override
	public JPanel getCenter() {
//		"商务座", "特等座", "一等座", "二等座", "高级软卧", "软卧", "硬卧", "软座", "硬座", "无座", "其他","备注"
		if(ticketPrice.getA9()!=0)
			seatTypes.addItem("商务座 ("+ticketPrice.getA9()+")");
		if(ticketPrice.getP()!=0)
			seatTypes.addItem("特等座 ("+ticketPrice.getP()+")");
		if(ticketPrice.getM()!=0)
			seatTypes.addItem("一等座 ("+ticketPrice.getM()+")");
		if(ticketPrice.getO()!=0)
			seatTypes.addItem("二等座 ("+ticketPrice.getO()+")");
		if(ticketPrice.getA6()!=0)
			seatTypes.addItem("高级软卧 ("+ticketPrice.getA6()+")");
		if(ticketPrice.getA4()!=0)
			seatTypes.addItem("软卧 ("+ticketPrice.getA4()+")");
		if(ticketPrice.getA3()!=0)
			seatTypes.addItem("硬卧 ("+ticketPrice.getA3()+")");
		if(ticketPrice.getA2()!=0)
			seatTypes.addItem("软座 ("+ticketPrice.getA2()+")");
		if(ticketPrice.getA1()!=0)
			seatTypes.addItem("硬座 ("+ticketPrice.getA1()+")");
		if(ticketPrice.getWZ()!=0)
			seatTypes.addItem("无座 ("+ticketPrice.getWZ()+")");
		if(ticketPrice.getMIN()!=0)
			seatTypes.addItem("其他 ("+ticketPrice.getMIN()+")");
		JLabel setSeatType=new JLabel("席别：");
		center.add(setSeatType,BorderLayout.CENTER);
		center.add(seatTypes,BorderLayout.CENTER);
		return center;
	}
	@Override
	public JPanel getSouth() {
		JPanel panel =new JPanel();
		panel.add(submit);
		return panel;
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

	protected void submit() {
		Ticket ticket=new Ticket();
		ticket.setOrder_time(System.currentTimeMillis());
		ticket.setUser_name(user.getName());
		ticket.setTrain_no(aTrain.getTrain_no());
		ticket.setCode(aTrain.getCode());
		ticket.setDate(aTrain.getDate());
		ticket.setFrom_station_name(aTrain.getFrom_station_name());
		ticket.setFrom_station_no(aTrain.getFrom_station_no());
		ticket.setFrom_arrive_time(aTrain.getFrom_arrive_time());
		ticket.setFrom_start_time(aTrain.getFrom_start_time());
		ticket.setTo_station_name(aTrain.getTo_station_name());
		ticket.setTo_station_no(aTrain.getTo_station_no());
		ticket.setTo_arrive_time(aTrain.getTo_arrive_time());
		String typeAndprice=(String) seatTypes.getSelectedItem();
		logger.debug("typeAndprice:"+typeAndprice);
		String[] tp=typeAndprice.split("\\(");
		String type=tp[0].trim();
		String price=tp[1].substring(0, tp[1].length()-1);
		logger.debug("type:"+type+"\tprice:"+price);
		ticket.setSeat_type(type);
		ticket.setPrice(Double.valueOf(price));
		int[] carriageAndSeat_no=dao.getCarriage(type, aTrain.getReTickets());
		ticket.setCarriage_no(carriageAndSeat_no[0]);
		ticket.setSeat_no(carriageAndSeat_no[1]);
		if(dao.beginTransactionModel())
		{				
			boolean delete=dao.deleteTicket(oldTicket)&&dao.updateRemainingseats(oldTicket, "+");
			if(dao.getTicket(ticket.getUser_name(),ticket.getTrain_no(),ticket.getDate())==null)
			{
				if(delete&&dao.saveTicket(ticket)&&dao.updateRemainingseats(ticket,"-")&&dao.commit())
				{
					JOptionPane.showMessageDialog(center, "改签成功！");
				}
				else
				{
					dao.rollback();
					JOptionPane.showMessageDialog(center, "改签失败，请稍后重试。");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(center, "预定失败，当车次该日期您已有预定。");
			}
			dao.endTransactionModel();
		}

	}
	
}

package uiJPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import orm.Schedule;
import orm.TimeAndPrice;
import orm.Train;
import uiTable.AdaptSchedule;
import uiTable.AdaptTimeAndPrice;
import uiTable.AdaptTrain;
import uiTable.MyTableModel;
import uiTable.ScheduleTableModel;
import uiTable.TimeAndPriceTableModel;
import uiTable.TrainTableModel;


public class ManagerTrainNumberPanel extends MyPanel {
	int timeAndPriceTableSize=0;
	JLabel trainSize=new JLabel("共0条记录");
	JLabel timeAndPriceSize=new JLabel("共0条记录");
	JLabel scheduleSize=new JLabel("共0条记录");
	JButton submit=new JButton("查询");
	JTextField iTrain_no=new JTextField(20);
	JRadioButton queryAll=new JRadioButton("查看全部车次");
	JRadioButton query=new JRadioButton("查看指定车次");
	JRadioButton update=new JRadioButton("修改");
	JRadioButton add=new JRadioButton("添加");
	JRadioButton remove=new JRadioButton("删除");
	ButtonGroup buttonGroup=new ButtonGroup();
	JPanel center=new JPanel();
	Set<Integer> TrainEditedIndexs=new HashSet<>();
	Set<Integer> TimeAndPriceEditedIndexs=new HashSet<>();
	Set<Integer> ScheduleEditedIndexs=new HashSet<>();
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int width=dScreen.width;
	//列车表格
	MyTableModel mTrain=new TrainTableModel();
	JTable tTrain=new JTable(mTrain);
	JScrollPane jspTrain=new JScrollPane(tTrain);
	//车站价格表格
	MyTableModel mTimeAndPrice=new TimeAndPriceTableModel();
	JTable tTimeAndPrice=new JTable(mTimeAndPrice);
	JScrollPane jspTimeAndPrice=new JScrollPane(tTimeAndPrice);
	//日程表格
	MyTableModel mSchedule=new ScheduleTableModel();
	JTable tSchedule=new JTable(mSchedule);
	JScrollPane jspSchedule=new JScrollPane(tSchedule);
	//构造方法
	public ManagerTrainNumberPanel() {
		queryAll.setSelected(true);
		// 设置列宽度
        tTimeAndPrice.getColumnModel().getColumn(0).setPreferredWidth(width/12);
		iTrain_no.setEditable(false);
		buttonGroup.add(queryAll);
		buttonGroup.add(query);
		buttonGroup.add(update);
		buttonGroup.add(add);
		buttonGroup.add(remove);
		addListener();
		//table.setModel(arg0);
	}
	@Override
	public JPanel getNorth() {
		JPanel panel=new JPanel();
		JLabel tTrain_no=new JLabel("车次完整编号：");
		panel.add(tTrain_no);
		panel.add(iTrain_no);
		panel.add(queryAll);
		panel.add(query);
		panel.add(update);
		panel.add(add);
		panel.add(remove);
		panel.add(submit);
		return panel;
	}
	@Override
	public JPanel getCenter() {
		center.setLayout(new GridBagLayout());
		GridBagConstraints gc=new GridBagConstraints();
		gc.weightx=1;
		gc.gridwidth=0;
		gc.fill=GridBagConstraints.BOTH;
		
		gc.weighty=0.2;
		center.add(trainSize,gc);
		gc.weighty=0.5;
		center.add(jspTrain,gc);
		gc.weighty=0.2;
		center.add(timeAndPriceSize,gc);
		gc.weighty=5;
		center.add(jspTimeAndPrice,gc);
		gc.weighty=0.2;
		center.add(scheduleSize,gc);
		gc.weighty=5;
		center.add(jspSchedule,gc);
		return center;
	}
	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(queryAll.isSelected())
				{
					submitQueryAll();
				}
				else if(query.isSelected())
				{
					submitQuery();
				}
				else if(update.isSelected())
				{
					submitUpdate();
				}
				else if(add.isSelected())
				{
					submitAdd();
				}
				else if(remove.isSelected())
				{
					submitRemove();
				}
			}
			
		});
		queryAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectQueryAll();
			}
		});
		query.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectQuery();
			}
		});
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectUpdate();
			}
		});
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectAdd();
			}
		});
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectRemove();
			}
		});
		
		mTrain.addTableModelListener(new TableModelListener() {		
			@Override
			public void tableChanged(TableModelEvent e) {
				updateTrain();
			}
		});
		mTimeAndPrice.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				updateTimeAndPrice();
			}
		});
		mSchedule.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				updateSchedule();
			}
		});
		
	}
	private void submitQueryAll()
	{
		logger.debug("查询全部车次");
		List<TimeAndPrice> tpl=dao.getAllTimeAndPrices();
		mTimeAndPrice.setTableDatas(tpl);
		tTimeAndPrice.updateUI();
		updateLabelSize();
	}
	private void submitQuery()
	{
		logger.debug("查询指定车次");
		String train_no=iTrain_no.getText();
		Train train=dao.getTrain(train_no);
		List<Train> tl=new ArrayList<>();
		if(train!=null)
			tl.add(train);
		List<TimeAndPrice> tpl=dao.getTimeAndPrices(train_no);
		List<Schedule> scl=dao.getSchedule(train_no);
		mTrain.setTableDatas(tl);
		mTimeAndPrice.setTableDatas(tpl);
		mSchedule.setTableDatas(scl);
		tTrain.updateUI();
		tTimeAndPrice.updateUI();
		tSchedule.updateUI();
		updateLabelSize();
		logger.debug("");
		
	}
	private void submitUpdate()
	{
		if(dao.beginTransactionModel())
		{
			boolean flage=true;
			for(int i:TrainEditedIndexs)
			{
				logger.debug("TrainEditedIndexs:"+i);
				Train train=((AdaptTrain)mTrain.getTableDatas().get(i)).getTrain();
				if(train.getTrain_no()!=null)
					flage=dao.updateTrain(train);
				if(!flage)
					break;
			}
			if(flage)
			{
				for(int i:TimeAndPriceEditedIndexs)
				{
					TimeAndPrice timeAndPrice=((AdaptTimeAndPrice)mTimeAndPrice.getTableDatas().get(i)).getTimeAndPrice();
					if(timeAndPrice.getTrain_no()!=null&&timeAndPrice.getStation_no()!=0)
					{
						if(timeAndPrice.getStation_no()<=timeAndPriceTableSize)
						{
							flage=dao.updateTimeAndPrice(timeAndPrice);
						}
						else
						{
							flage=dao.saveTimeAndPrice(timeAndPrice);
						}
					}
					if(!flage)
						break;
				}
			}
			if(flage&&dao.commit())
			{
				JOptionPane.showMessageDialog(center, "数据修改成功！");
				TimeAndPriceEditedIndexs.clear();
				TrainEditedIndexs.clear();
				ScheduleEditedIndexs.clear();
			}
			else
			{
				dao.rollback();
				JOptionPane.showMessageDialog(center, "数据修改失败，请稍后重试。");
			}
			dao.endTransactionModel();
		}
	}
	private void submitAdd()
	{
		Train train=((AdaptTrain)mTrain.getTableDatas().get(0)).getTrain();
		if(train.getTrain_no()==null)
		{
			JOptionPane.showMessageDialog(center, "列车完整编号不能为空。");
			return;
		}
		if(dao.beginTransactionModel())
		{
			boolean flage=dao.saveTrain(train);
			if(flage)
			{
				for(int i:TimeAndPriceEditedIndexs)
				{
					TimeAndPrice timeAndPrice=((AdaptTimeAndPrice)mTimeAndPrice.getTableDatas().get(i)).getTimeAndPrice();
					if(timeAndPrice.getTrain_no()!=null&&timeAndPrice.getStation_no()!=0)
						flage=dao.saveTimeAndPrice(timeAndPrice);
					if(!flage)
						break;
				}
			}

			if(flage)
			{
				for(int i:ScheduleEditedIndexs)
				{
					logger.debug("ScheduleEditedIndexs:"+i);
					Schedule schedule=((AdaptSchedule)mSchedule.getTableDatas().get(i)).getSchedule();
					if(schedule.getTrain_no()!=null&&schedule.getDate()!=null)
						flage=dao.saveSchedule(schedule);
					if(!flage)
						break;
				}
			}
			if(flage)
			{
				List<Schedule> schedules=dao.getSchedule(train.getTrain_no());
				List<TimeAndPrice> timeAndPrices=dao.getTimeAndPrices(train.getTrain_no());
				for(Schedule schedule:schedules)
					for(TimeAndPrice timeAndPrice:timeAndPrices)
					{
						flage=dao.addRemainingseats(train, schedule, timeAndPrice);
						if(!flage)
							break;
					}
			}

			if(flage&&dao.commit())
			{
				JOptionPane.showMessageDialog(center, "数据保存成功！");
				TimeAndPriceEditedIndexs.clear();
				TrainEditedIndexs.clear();
				ScheduleEditedIndexs.clear();
			}
			else
			{
				dao.rollback();
				JOptionPane.showMessageDialog(center, "数据保存失败，请稍后重试。");
			}
			dao.endTransactionModel();
		}

	}
	private void submitRemove()
	{
		int op=JOptionPane.showConfirmDialog(center, "该操作无法撤销，确定要删除该车次及其相关信息吗？");
		if(op==JOptionPane.OK_OPTION)
		{
			String train_no=iTrain_no.getText();
			if(dao.deleteTrain(train_no))
				JOptionPane.showMessageDialog(center, "删除成功！");
			else
				JOptionPane.showMessageDialog(center, "删除失败。");
		}

	}
	private void selectQueryAll()
	{
		logger.debug("queryAll");
		iTrain_no.setEditable(false);
		submit.setText("查询");
		//清空三个表格
		mTrain.getTableDatas().clear();
		mTimeAndPrice.getTableDatas().clear();
		mSchedule.getTableDatas().clear();
		//刷新
		tTrain.updateUI();
		tTimeAndPrice.updateUI();
		tSchedule.updateUI();
		updateLabelSize();
	}
	private void selectQuery()
	{
		logger.debug("query");
		iTrain_no.setEditable(true);
		submit.setText("查询");
	}
	private void selectUpdate()
	{
		TimeAndPriceEditedIndexs.clear();
		TrainEditedIndexs.clear();
		ScheduleEditedIndexs.clear();
		logger.debug("update");
		iTrain_no.setEditable(false);
		submit.setText("修改");
	}
	private void selectAdd()
	{
		logger.debug("add");
		iTrain_no.setEditable(false);
		submit.setText("保存");
		//清空三个表格
		mTrain.getTableDatas().clear();
		mTimeAndPrice.getTableDatas().clear();
		mSchedule.getTableDatas().clear();
		//提供待填写的行
		mTrain.getTableDatas().add(new AdaptTrain(new Train()));
		for(int i=0;i<50;i++)
			mTimeAndPrice.getTableDatas().add(new AdaptTimeAndPrice(new TimeAndPrice()));
		for(int i=0;i<30;i++)
			mSchedule.getTableDatas().add(new AdaptSchedule(new Schedule()));
		//刷新
		tTrain.updateUI();
		tTimeAndPrice.updateUI();
		tSchedule.updateUI();
		updateLabelSize();
	}
	private void selectRemove() {
		logger.debug("selectRemove");
		iTrain_no.setEditable(true);
		submit.setText("删除");
		//清空三个表格
		mTrain.getTableDatas().clear();
		mTimeAndPrice.getTableDatas().clear();
		mSchedule.getTableDatas().clear();
		//刷新
		tTrain.updateUI();
		tTimeAndPrice.updateUI();
		tSchedule.updateUI();
		updateLabelSize();
	}
	private void updateTrain()
	{
		int row=tTrain.getEditingRow();
		logger.debug("tTrain表格章第"+row+"行被修改。");
		if(row>=0)
			TrainEditedIndexs.add(row);
		if(add.isSelected())
		{
			String train_no=(String) mTrain.getValueAt(row, 0);
			for(int i=0;i<mTimeAndPrice.getTableDatas().size();i++)
			{
				if(train_no!=null)
					mTimeAndPrice.setValueAt(train_no, i, 0);
			}
			for(int i=0;i<mSchedule.getTableDatas().size();i++)
			{
				if(train_no!=null)
					mSchedule.setValueAt(train_no, i, 0);
			}
		}
	}
	private void updateTimeAndPrice()
	{
		int row=tTimeAndPrice.getEditingRow();
		logger.debug("tTimeAndPrice表格章第"+row+"行被修改。");	
		if(row>=0)
			TimeAndPriceEditedIndexs.add(row);
		if(row==mTimeAndPrice.getTableDatas().size()-1)
		{
			mTimeAndPrice.getTableDatas().add(new AdaptTimeAndPrice(new TimeAndPrice()));
			tTimeAndPrice.updateUI();
		}
	}
	private void updateSchedule() 
	{
		int row=tSchedule.getEditingRow();
		logger.debug("tSchedule表格章第"+row+"行被修改。");
		if(row>=0)
			ScheduleEditedIndexs.add(row);
		if(row==mSchedule.getTableDatas().size()-1)
		{
			mSchedule.getTableDatas().add(new AdaptSchedule(new Schedule()));
			tSchedule.updateUI();
		}
	}
	private void updateLabelSize()
	{
		int size=mTrain.getTableDatas().size();
		String str1="共"+size+"条记录";
		size=mTimeAndPrice.getTableDatas().size();
		timeAndPriceTableSize=size;
		String str2="共"+size+"条记录";
		size=mSchedule.getTableDatas().size();
		String str3="共"+size+"条记录";
		
		trainSize.setText(str1);
		timeAndPriceSize.setText(str2);
		scheduleSize.setText(str3);
	}
}

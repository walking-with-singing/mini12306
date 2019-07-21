package uiTable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import orm.AvailableTrain;
import orm.TimeAndPrice;
import orm.Train;

public class TimeAndPriceTableModel extends MyTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String[] columnNames= {"车次完整编号", "车站编号","车站名","到达时间", "出发时间", "商务座", "特等座", "一等座", "二等座", "高级软卧", "软卧", "硬卧", "软座", "硬座", "无座", "其他"};
	public TimeAndPriceTableModel() {
		super(columnNames);
	}
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void setValueAt(Object obj, int row, int column) {
		super.setValueAt(obj, row, column);
		TimeAndPrice timeAndPrice= ((AdaptTimeAndPrice)tableDatas.get(row)).getTimeAndPrice();
		String data=(String)obj;
		switch(column)
		{
			case 0:
				timeAndPrice.setTrain_no(data);
				break;
			case 1:
				timeAndPrice.setStation_no(Integer.valueOf(data));
				break;
			case 2:
				timeAndPrice.setStation_name(data);
				break;
			case 3:
				timeAndPrice.setArrive_time(Time.valueOf(data));
				break;
			case 4:
				timeAndPrice.setStart_time(Time.valueOf(data));
				break;
				
			case 5:
				timeAndPrice.setA9(Integer.valueOf(data));				
				break;
			case 6:
				timeAndPrice.setP(Integer.valueOf(data));
				break;
			case 7:
				timeAndPrice.setM(Integer.valueOf(data));
				break;
			case 8:
				timeAndPrice.setO(Integer.valueOf(data));
				break;
			case 9:
				timeAndPrice.setA6(Integer.valueOf(data));
				break;
			case 10:
				timeAndPrice.setA4(Integer.valueOf(data));
				break;
			case 11:
				timeAndPrice.setA3(Integer.valueOf(data));
				break;
			case 12:
				timeAndPrice.setA2(Integer.valueOf(data));
				break;
			case 13:
				timeAndPrice.setA1(Integer.valueOf(data));
				break;
			case 14:
				timeAndPrice.setWZ(Integer.valueOf(data));
				break;
			case 15:
				timeAndPrice.setMIN(Integer.valueOf(data));
				break;
		}
		fireTableCellUpdated(row, column);
	}
	@Override
	public void setTableDatas(List datas) {
		tableDatas.clear();
		for(Object timeAndPrice:datas)
		{
			AdaptTimeAndPrice a=new  AdaptTimeAndPrice();
			a.setTimeAndPrice((TimeAndPrice) timeAndPrice);
			tableDatas.add(a);
		}	
	}
}

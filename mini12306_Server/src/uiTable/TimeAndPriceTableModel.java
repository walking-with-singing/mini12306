package uiTable;

import java.sql.Time;
import java.util.List;

import orm.TimeAndPrice;

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
		Time time = null;
		int price = 0;
		try {
			if(column==3||column==4)
				time=Time.valueOf(data);
			else if(column>=5)
				price=Integer.valueOf(data);
		} catch (NumberFormatException e) {
			return;
		} catch (IllegalArgumentException e)
		{
			return;
		}
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
				timeAndPrice.setArrive_time(time);
				break;
			case 4:
				timeAndPrice.setStart_time(time);
				break;
				
			case 5:
				timeAndPrice.setA9(price);				
				break;
			case 6:
				timeAndPrice.setP(price);
				break;
			case 7:
				timeAndPrice.setM(price);
				break;
			case 8:
				timeAndPrice.setO(price);
				break;
			case 9:
				timeAndPrice.setA6(price);
				break;
			case 10:
				timeAndPrice.setA4(price);
				break;
			case 11:
				timeAndPrice.setA3(price);
				break;
			case 12:
				timeAndPrice.setA2(price);
				break;
			case 13:
				timeAndPrice.setA1(price);
				break;
			case 14:
				timeAndPrice.setWZ(price);
				break;
			case 15:
				timeAndPrice.setMIN(price);
				break;
		}
		fireTableCellUpdated(row, column);
	}
	@Override
	public void setTableDatas(List<?> datas) {
		tableDatas.clear();
		for(Object timeAndPrice:datas)
		{
			AdaptTimeAndPrice a=new  AdaptTimeAndPrice();
			a.setTimeAndPrice((TimeAndPrice) timeAndPrice);
			tableDatas.add(a);
		}	
	}
}

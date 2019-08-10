package uiTable;

import java.sql.Date;
import java.util.List;


import orm.Schedule;

public class ScheduleTableModel extends MyTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String[] columnNames= {"车次完整编号","日期" };
	public ScheduleTableModel() {
		super(columnNames);
	}
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void setValueAt(Object obj, int row, int column) {
		Schedule Schedule= ((AdaptSchedule)tableDatas.get(row)).getSchedule();
		String data=(String)obj;
		Date date = null;
		try {
			if(column==1)
				date=Date.valueOf(data);
		} catch (NumberFormatException e) {
			return;
		} catch (IllegalArgumentException e)
		{
			return;
		}
		switch(column)
		{
			case 0:
				Schedule.setTrain_no(data);
				break;
			case 1:
				Schedule.setDate(date);
				break;
		}
        fireTableCellUpdated(row, column);
	}
	@Override
	public void setTableDatas(List<?> datas) {
		tableDatas.clear();
		for(Object sche:datas)
		{
			AdaptSchedule a=new  AdaptSchedule();
			a.setSchedule(((Schedule)sche));
			tableDatas.add(a);
		}	
	}

}

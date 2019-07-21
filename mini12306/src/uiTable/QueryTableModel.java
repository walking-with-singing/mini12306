package uiTable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import orm.AvailableTrain;

public class QueryTableModel extends MyTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String[] columnNames= {"车次", "出发站--到达站", "出发时间--到达时间", "商务座", "特等座", "一等座", "二等座", "高级软卧", "软卧", "硬卧", "软座", "硬座", "无座", "其他","备注"};
	public QueryTableModel() {
		super(columnNames);
	}
	@Override
	public void setTableDatas(List datas) {
		tableDatas.clear();
		for(Object aTrain:datas)
		{
			AdaptAvaTrain a=new  AdaptAvaTrain();
			a.setaTrain((AvailableTrain) aTrain);
			tableDatas.add(a);
		}	
	}
}

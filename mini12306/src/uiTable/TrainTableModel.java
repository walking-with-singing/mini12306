package uiTable;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import orm.AvailableTrain;
import orm.Train;

public class TrainTableModel extends MyTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnNames= {"车次完整编号","车次简单编号", "商务座", "特等座", "一等座", "二等座", "高级软卧", "软卧", "硬卧", "软座", "硬座", "无座", "其他"};
	//创建logger
	private Logger logger=LogManager.getLogger();
	public TrainTableModel() {
		super.columnNames=columnNames;
	}
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void setValueAt(Object obj, int row, int column) {
		Train train= ((AdaptTrain)tableDatas.get(row)).getTrain();
		String data=(String)obj;
		switch(column)
		{
			case 0:
				train.setTrain_no(data);
				break;
			case 1:
				train.setCode(data);
				break;
			case 2:
				train.setA9(Integer.valueOf(data));				
				break;
			case 3:
				train.setP(Integer.valueOf(data));
				break;
			case 4:
				train.setM(Integer.valueOf(data));
				break;
			case 5:
				train.setO(Integer.valueOf(data));
				break;
			case 6:
				train.setA6(Integer.valueOf(data));
				break;
			case 7:
				train.setA4(Integer.valueOf(data));
				break;
			case 8:
				train.setA3(Integer.valueOf(data));
				break;
			case 9:
				train.setA2(Integer.valueOf(data));
				break;
			case 10:
				train.setA1(Integer.valueOf(data));
				break;
			case 11:
				train.setWZ(Integer.valueOf(data));
				break;
			case 12:
				train.setMIN(Integer.valueOf(data));
				break;
		}
		fireTableCellUpdated(row, column);
	}
	@Override
	public void setTableDatas(List datas) {
		tableDatas.clear();
		for(Object train:datas)
		{
			AdaptTrain a=new  AdaptTrain();
			a.setTrain((Train) train);
			tableDatas.add(a);
		}	
	}
	
}

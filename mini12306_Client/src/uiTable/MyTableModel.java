package uiTable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class MyTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnNames= {};
	protected List<TableData> tableDatas=new ArrayList<>();
	protected Logger logger=LogManager.getLogger();
	public MyTableModel(String[] columnNames) {
		this.columnNames=columnNames;
	}
	public abstract void setTableDatas(List<?> datas);
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return tableDatas.size();
	}
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		TableData tableData=tableDatas.get(rowIndex);
		switch(colIndex)
		{
			case 0:
				return tableData.getA();
			case 1:
				return tableData.getB();
			case 2:
				return tableData.getC();
			case 3:
				return tableData.getD();
			case 4:
				return tableData.getE();
			case 5:
				return tableData.getF();
			case 6:
				return tableData.getG();
			case 7:
				return tableData.getH();
			case 8:
				return tableData.getI();
			case 9:
				return tableData.getJ();
			case 10:
				return tableData.getK();
			case 11:
				return tableData.getL();
			case 12:
				return tableData.getM();
			case 13:
				return tableData.getN();
			case 14:
				return tableData.getO();
			case 15:
				return tableData.getP();
			case 16:
				return tableData.getQ();
		}
		return "*";
	}
	public List<TableData> getTableDatas() {
		return tableDatas;
	}

}

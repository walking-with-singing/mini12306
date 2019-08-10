package uiTable;

import java.util.List;

import orm.Ticket;

public class QueryMyOrderTableModel extends MyTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String[] columnNames= {"下单日期", "车次", "乘车日期", "出发站", "列车进站时间", "列车发车时间", "目的站", "列车到达时间", "座位类别", "价格", "车厢号", "座位号","改签","退票"};
	public QueryMyOrderTableModel() {
		// TODO Auto-generated constructor stub
		super(columnNames);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void setTableDatas(List<?> datas) {
		tableDatas.clear();
		for(Ticket ticket:(List<Ticket>)datas)
			tableDatas.add(new AdaptTicket(ticket));
	}

}

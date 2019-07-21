package testUi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import orm.Train;
import uiJFrame.ManagerTrainNumber;
import uiTable.AdaptTrain;
import uiTable.Airline;
import uiTable.MyTableModel;
import uiTable.TimeAndPriceTableModel;
import uiTable.TrainTableModel;

public class TestManagerTrainNumber {
	//创建logger
	private static Logger logger=LogManager.getLogger();
	public static void main(String[] args) {		
		new ManagerTrainNumber();
		//testTable();
	}
	
	public static void testTable()
	{
		String[] head= {"A","B"};
		String[][] data= {{"11","12"},{"21","22"}};
		JTable table=new JTable(data,head);
//		//车站价格表格
//		MyTableModel model=new TrainTableModel();
//		JTable table1=new JTable(model);
//		model.getTableDatas().add(new AdaptTrain(new Train()));
//		model.getTableDatas().add(new Airline());
//		logger.debug("tableDatas.size："+model.getTableDatas().size());
		JScrollPane jspTimeAndPrice=new JScrollPane(table);
		JButton submit=new JButton("查询");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				logger.debug(table.getEditingRow());
			}
		});
		JFrame f=new JFrame();
		//获取屏幕分辨率
		final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
		final int width=dScreen.width;
		final int height=dScreen.height;
		//非全屏时尺寸和位置
		f.setSize(width/2,height/2 );
		f.setLocationRelativeTo(null);
		//初始即全屏
		f.setExtendedState(f.MAXIMIZED_BOTH);
		//默认关闭窗口设置
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		//开始布局
		f.setLayout(new BorderLayout());
		f.add(table,BorderLayout.CENTER);
		f.add(submit,BorderLayout.NORTH);
		f.setVisible(true);
		
		
	}
}

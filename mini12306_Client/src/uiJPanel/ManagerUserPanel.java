package uiJPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import orm.User;
import uiTable.AdaptUser;
import uiTable.MyTableModel;
import uiTable.UserTableModel;

public class ManagerUserPanel extends MyPanel {
	JLabel userSize=new JLabel("共0条记录");
	JButton submit=new JButton("查询");
	JTextField iUser_name=new JTextField(20);
	JPanel center=new JPanel();
	JRadioButton queryAll=new JRadioButton("查看全部用户");
	JRadioButton query=new JRadioButton("查看指定用户");
	ButtonGroup buttonGroup=new ButtonGroup();
	//用户表格
	MyTableModel mUser=new UserTableModel();
	JTable tUser=new JTable(mUser);
	JScrollPane jspTrain=new JScrollPane(tUser);
	//构造方法
	public ManagerUserPanel() {
		queryAll.setSelected(true);
		iUser_name.setEditable(false);
		buttonGroup.add(queryAll);
		buttonGroup.add(query);
		addListener();
	}
	@Override
	public JPanel getNorth() {
		JPanel panel=new JPanel();
		JLabel tUser_name=new JLabel("用户名：");
		panel.add(tUser_name);
		panel.add(iUser_name);
		panel.add(queryAll);
		panel.add(query);
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
		
		gc.weighty=1;
		center.add(userSize,gc);
		gc.weighty=10;
		center.add(jspTrain,gc);
		return center;
	}
	@Override
	protected void addListener() {
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
		tUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row=tUser.getSelectedRow();
				int column=tUser.getSelectedColumn();
				if(column==2)
				{
					User user=((AdaptUser)mUser.getTableDatas().get(row)).getUser();
					int op=JOptionPane.showConfirmDialog(center, "确定注销账号\""+user.getName()+"\"？");
					if(op==JOptionPane.OK_OPTION)
						logout(row,user);
				}
			}
		});
	}
	private void submitQueryAll()
	{
		mUser.setTableDatas(dao.getAllUser());
		tUser.updateUI();
		updateLabelSize();
	}
	private void submitQuery()
	{
		String user_name=iUser_name.getText();
		User user=dao.getUser(user_name);
		List<User> tl=new ArrayList<>();
		if(user!=null)
			tl.add(user);
		mUser.setTableDatas(tl);
		tUser.updateUI();
		updateLabelSize();
		
	}
	private void selectQueryAll()
	{
		iUser_name.setEditable(false);
	}
	private void selectQuery()
	{
		iUser_name.setEditable(true);
	}
	private void logout(int row,User user)
	{
		if(dao.deleteUser(user))
		{
			JOptionPane.showMessageDialog(center, "删除成功！");
			mUser.getTableDatas().remove(row);
			tUser.updateUI();
			updateLabelSize();
		}
		else
			JOptionPane.showMessageDialog(center, "删除失败，请稍后重试。");
	}
	private void updateLabelSize()
	{
		int size=mUser.getTableDatas().size();
		String str1="共"+size+"条记录";
		userSize.setText(str1);

	}

}

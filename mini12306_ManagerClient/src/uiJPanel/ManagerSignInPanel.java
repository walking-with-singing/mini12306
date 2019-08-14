package uiJPanel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import netToolset.DAO;
import orm.Manager;
import uiJFrame.ManagerHome;

public class ManagerSignInPanel extends MyPanel {
	JPanel center=new JPanel();
	JTextField id_input=new JTextField(20);
	JPasswordField password=new JPasswordField(20);
	JButton submit=new JButton("登录");
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int screenWidth=dScreen.width;
	private final int screenHeight=dScreen.height;
	//Tool
	private DAO dao=new DAO();
	public ManagerSignInPanel() {
		 addListener();
	}
	@Override
	public JPanel getCenter() {
		center.setLayout(null);
		JLabel tid =new JLabel("管理员id：");
		JLabel tpassword =new JLabel("密码：");
		//设置绝对位置
		tid.setBounds(screenWidth/100*45, screenHeight/100*30,screenWidth/100*15,screenHeight/100*3);
		id_input.setBounds(screenWidth/100*50, screenHeight/100*30,screenWidth/100*15,screenHeight/100*3);
		tpassword.setBounds(screenWidth/100*45, screenHeight/100*35, screenWidth/100*15,screenHeight/100*3);
		password.setBounds(screenWidth/100*50, screenHeight/100*35, screenWidth/100*15,screenHeight/100*3);
		submit.setBounds(screenWidth/100*50, screenHeight/100*50, screenWidth/100*10,screenHeight/100*5);
		center.add(tid);
		center.add(id_input);
		center.add(tpassword);
		center.add(password);
		center.add(submit);
		return center;
	}

	protected void addListener()
	{
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitSignIn();
			}
		});
		
	}
	
	private void submitSignIn()
	{
		
		int id=Integer.valueOf(id_input.getText());
		String password=new String(this.password.getPassword());
		Manager manager=dao.getManager(id);
		if(manager==null)
		{
			JOptionPane.showMessageDialog(center,"管理员“"+id+"”不存在!");
			return;
		}
		String truePassword=manager.getPassword();
		if(!password.equals(truePassword))
		{
			JOptionPane.showMessageDialog(center,"或密码不正确，请重新输入。");
			return;
		}
		else 
		{
			new ManagerHome(manager);
			this.frame.dispose();
		}		
	}

}

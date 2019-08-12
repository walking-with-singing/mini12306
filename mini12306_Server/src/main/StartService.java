package main;



import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.Server;

public class StartService {
	private static Logger logger=LogManager.getLogger();
	public static void main(String[] args) {
		try {
			new Thread(new Server(8086)).start();
			logger.trace("服务器已启动，端口：" + 8086);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "服务器启动失败，端口：" + 8086);
			e.printStackTrace();
		}
	}
}

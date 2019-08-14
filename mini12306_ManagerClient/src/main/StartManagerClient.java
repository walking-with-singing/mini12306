package main;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import netToolset.DAO;
import uiJFrame.ManagerSignIn;



public class StartManagerClient {
   
	public static void main(String[] args) {
		try {
			Socket socket=new Socket("127.0.0.1",8086);
			DAO.setSocket(socket);
			new ManagerSignIn();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

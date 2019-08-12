package main;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import netToolset.Protocol;
import netToolset.DAO;
import uiJFrame.SignIn;



public class StartClient {
   
	public static void main(String[] args) {
		try {
			Socket socket=new Socket("127.0.0.1",8086);
			DAO.setSocket(socket);
			new SignIn();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

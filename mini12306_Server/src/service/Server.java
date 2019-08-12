package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements Runnable {
	protected static Logger logger = LogManager.getLogger();
	private final ServerSocket serverSocket;
	private final ExecutorService pool;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		pool = Executors.newCachedThreadPool();
	}

	@Override
	public void run() { // run the service
		try {
			while (true) {
				if (serverSocket.isClosed()) {
					logger.trace("有服务器关闭，端口:" + serverSocket.getLocalPort());
					return;
				}
				Socket socket = serverSocket.accept();
				pool.execute(new Handler(socket));
				logger.trace("客户端已连接");
			}
		} catch (IOException ex) {
			pool.shutdown();
		}
	}
}

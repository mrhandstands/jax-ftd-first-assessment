package com.cooksys.ftd.assessment.filesharing.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;

public class Server implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(Server.class);

	private ExecutorService executor;
	private ServerSocket serverSocket;

	private FileDao fileDao;
	private UserDao userDao;

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(670)) {
			while (true) {
				Socket socket = serverSocket.accept();
				ClientHandler handler = this.createClientHandler(socket);
				this.executor.execute(handler);
			}
		} catch (IOException e) {
			this.log.error("The server encountered a fatal error while listening for more connections. Shutting down after error log.", e);
		}
	}

	public ClientHandler createClientHandler(Socket socket) throws IOException {
		ClientHandler handler = new ClientHandler();

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		handler.setReader(reader);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		handler.setWriter(writer);

		handler.setFileDao(fileDao);
		handler.setUserDao(userDao);

		return handler;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}

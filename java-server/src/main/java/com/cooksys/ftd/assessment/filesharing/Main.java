package com.cooksys.ftd.assessment.filesharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;
import com.cooksys.ftd.assessment.filesharing.server.Server;

public class Main {
	private static Logger log = LoggerFactory.getLogger(Main.class);

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/file_storage";
	private static String username = "root";
	private static String password = "bondstone";

	public static void main(String[] args) throws JAXBException, ClassNotFoundException {

		log.info("connecting to sql...");
		
		Class.forName(driver); // register jdbc driver class
		ExecutorService executor = Executors.newCachedThreadPool(); // initialize thread pool
																
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			log.info("...sql connected");
			
			Server server = new Server();
			server.setExecutor(executor);

			FileDao fileDao = new FileDao();
			fileDao.setConn(conn);
			server.setFileDao(fileDao);

			UserDao userDao = new UserDao();
			userDao.setConn(conn);
			server.setUserDao(userDao);

			Future<?> serverFuture = executor.submit(server); // start server
																// (asynchronously)

			serverFuture.get(); // blocks until server's #run() method is done
								// (aka the server shuts down)

		} catch (SQLException | InterruptedException | ExecutionException e) {
			log.error("An error occurred during server startup. Shutting down after error log.", e);
		} finally {
			executor.shutdown(); // shutdown thread pool (see inside of try
									// block for blocking call)
		}
	}
}
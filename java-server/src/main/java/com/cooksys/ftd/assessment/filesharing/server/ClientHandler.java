package com.cooksys.ftd.assessment.filesharing.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;
import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class ClientHandler implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private BufferedReader reader;
	private PrintWriter writer;
	
	private FileDao fileDao;
	private UserDao userDao;

	@Override
	public void run() {
		
		log.debug("Started a connection");
		try {
			String echo = this.reader.readLine();
			log.debug("{}", echo);
			if (echo.startsWith("{\"user\":")) {
				Map<String, Object> prop = new HashMap<String, Object>(2);
				prop.put("eclipselink.media-type", "application/json");
				JAXBContext jc = JAXBContext.newInstance(new Class[] { User.class }, prop);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				StringReader json = new StringReader(echo);
				User user = (User) unmarshaller.unmarshal(json);
				log.debug("User: {}", user);
				if (!userDao.checkUsernameExists(user.getUsername())) {
					log.debug("Return register user result: {}", userDao.createUser(user));
					log.debug("User created successfully.");
					//this.writer.write("Hey There");
				} else {
					log.debug("Error, user already exists.");
				}
			}
//			if (echo.startsWith("{\"login\":")) {
//				log.debug("Got inside login)");
//				Map<String, Object> prop = new HashMap<String, Object>(2);
//				prop.put("eclipselink.media-type", "application/json");
//				JAXBContext jc = JAXBContext.newInstance(new Class[] { User.class }, prop);
//				Unmarshaller unmarshaller = jc.createUnmarshaller();
//				StringReader json = new StringReader(echo);
//				User user = (User) unmarshaller.unmarshal(json);
//				log.debug("User: {}", user);
//				if (userDao.checkUserExists(user)) {
//					this.writer.write("true");
//					log.debug("Return register user result: {}", userDao.createUser(user));
//					log.debug("User created successfully.");
//				} else {
//					log.debug("Error, wrong user or pass.");
//				}
//			}
			
		} catch (IOException e) {
			log.error("There was an issue with the connection {}", e);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
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
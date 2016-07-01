package com.cooksys.ftd.assessment.filesharing.model.api;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.db.User;
import com.cooksys.ftd.assessment.filesharing.server.ClientHandler;

public class LoginUser {
	
	private Logger log = LoggerFactory.getLogger(ClientHandler.class);

	public int login(User user) throws JAXBException {
		
			log.info("got into the login if");
			Map<String, Object> prop = new HashMap<String, Object>(2);
			prop.put("eclipselink.media-type", "application/json");
			JAXBContext jc = JAXBContext.newInstance(new Class[] { User.class }, prop);
			Marshaller marshaller = jc.createMarshaller();
//			StringReader json = new StringReader(echo);
//			 = (User) marshaller.unmarshal(json);
			log.debug("User: {}", user);
//			if (userDao.checkUsernameExists(user.getUsername())) {
//				log.debug("Return register user result: {}", userDao.createUser(user));
//				log.debug("User created successfully.");
//			}
//		}
			return 0;
	}
	
}

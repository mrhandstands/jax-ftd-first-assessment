package com.cooksys.ftd.assessment.filesharing.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.Main;
import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class UserDao extends AbstractDao {
	private static Logger log = LoggerFactory.getLogger(Main.class);

	public int createUser(User user) throws SQLException {
		String sql = "insert into user (username, password) values (?, ?)";
		PreparedStatement stmt = this.getConn().prepareStatement(sql);
		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getPassword());

		int flag = stmt.executeUpdate();
		if (flag == 1) {
			log.info("User created successfully");

			sql = "select @@IDENTITY";
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.info("\nuser_id = {},\n username:\n {}, password: {}", rs.getInt(1), user.getUsername(),
						user.getPassword());
				return rs.getInt(1);
			}
			return -1;
		}
		return -1;
	}

	public boolean checkUsernameExists(String username) throws SQLException {
		String sql = "select * from user";
		PreparedStatement stmt = this.getConn().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			if (username.equals(rs.getString("username"))) {
				return true;
			}
		}
		return false;
	}

	public boolean checkUserExists(User user) throws SQLException {
		String sql = "select * from user";
		PreparedStatement stmt = this.getConn().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			if (user.getUsername().equals(rs.getString("username"))
					&& user.getPassword().equals(rs.getString("password"))) {
				return true;
			}
		}
		return false;
	}

	public String hashPass(String username) throws SQLException {
		String sql = "select password from user where username = ?";
		PreparedStatement stmt = this.getConn().prepareStatement(sql);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return rs.getString(3);
		return null;
	}

}
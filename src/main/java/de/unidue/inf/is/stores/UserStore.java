package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.interfaces.IUser;
import de.unidue.inf.is.utils.DBUtil;

public final class UserStore implements Closeable, IUser {
	private static UserStore instance;

	private Connection connection;
	private boolean complete;

	public UserStore() throws StoreException {
		try {
			connection = DBUtil.getExternalConnection("babble");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	public static UserStore getInstanse() {
		if (instance == null)
			instance = new UserStore();
		return instance;
	}

	public void addUser(User userToAdd) throws StoreException {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into user (firstname, lastname) values (?, ?)");
			// preparedStatement.setString(1, userToAdd.getFirstname());
			// preparedStatement.setString(2, userToAdd.getLastname());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	public void complete() {
		complete = true;
	}

	@Override
	public void close() throws IOException {
		if (connection != null) {
			try {
				if (complete) {
					connection.commit();
				} else {
					connection.rollback();
				}
			} catch (SQLException e) {
				throw new StoreException(e);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new StoreException(e);
				}
			}
		}
	}

	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String username) throws StoreException {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM dbp40.babbleuser WHERE username = ?");
			preparedStatement.setString(1, username);
			ResultSet set = preparedStatement.executeQuery();
			set.next();
			User user = new User(set.getString(1), set.getString(4), set.getString(3), set.getString(2));
			return user;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean getFollow(User user1, User user2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBlock(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM dbp40.blocks WHERE blocker = ? AND blockee = ?");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, Application.getUser().getUsername());
			ResultSet set = preparedStatement.executeQuery();
			// set.next();
			if (set.next()) {
				return set.getString(3);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public int follow(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM dbp40.follows WHERE follower = ? AND followee = ?");
			preparedStatement.setString(1, Application.getUser().getUsername());
			preparedStatement.setString(2, user.getUsername());
			ResultSet set = preparedStatement.executeQuery();
			// set.next();
			if (set.next()) {
				PreparedStatement preparedStatement2 = connection
						.prepareStatement("DELETE FROM dbp40.follows WHERE follower = ? AND followee = ?");
				preparedStatement2.setString(1, Application.getUser().getUsername());
				preparedStatement2.setString(2, user.getUsername());
				preparedStatement2.executeUpdate();
				return 0;
			} else {
				PreparedStatement preparedStatement2 = connection
						.prepareStatement("Insert into dbp40.follows (follower, followee) VALUES (?,?)");
				preparedStatement2.setString(1, Application.getUser().getUsername());
				preparedStatement2.setString(2, user.getUsername());
				preparedStatement2.executeUpdate();
				return 1;
			}
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public int block(User user, String reason) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM dbp40.blocks WHERE blocker = ? AND blockee = ?");
			preparedStatement.setString(1, Application.getUser().getUsername());
			preparedStatement.setString(2, user.getUsername());
			ResultSet set = preparedStatement.executeQuery();
			// set.next();
			if (set.next()) {
				PreparedStatement preparedStatement2 = connection
						.prepareStatement("DELETE FROM dbp40.blocks WHERE blocker = ? AND blockee = ?");
				preparedStatement2.setString(1, Application.getUser().getUsername());
				preparedStatement2.setString(2, user.getUsername());
				preparedStatement2.executeUpdate();
				return 0;
			} else {
				PreparedStatement preparedStatement2 = connection
						.prepareStatement("Insert into dbp40.blocks (blocker, blockee, reason) VALUES (?,?,?)");
				preparedStatement2.setString(1, Application.getUser().getUsername());
				preparedStatement2.setString(2, user.getUsername());
				preparedStatement2.setString(3, reason);
				preparedStatement2.executeUpdate();
				return 1;
			}
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public ArrayList<Babble> getTimeline(User user) {
		try {
			String statement = "Select * from dbp40.babble b full join (SELECT * FROM dbp40.likesbabble WHERE username = ?)"
					+ " l on l.babble = b.id full join (SELECT * from dbp40.rebabble where username = ?)"
					+ " r on r.babble = l.babble WHERE (creator = ?) OR (not creator = ? and l.username = ?) "
					+ "OR (not creator = ? and not l.username = ? and r.username = ?) ORDER BY b.created DESC";

			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getUsername());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setString(5, user.getUsername());
			preparedStatement.setString(6, user.getUsername());
			preparedStatement.setString(7, user.getUsername());
			preparedStatement.setString(8, user.getUsername());
			ResultSet set = preparedStatement.executeQuery();
			ArrayList<Babble> babbles = new ArrayList<>();
			// ID TEXT CREATED CREATOR USERNAME BABBLE TYPE CREATED USERNAME BABBLE CREATED
			while (set.next()) {
				Babble babble = new Babble(set.getInt(1), set.getString(2), set.getString(4), set.getString(3));
				if (set.getString(4).equals(user.getUsername()))
					babble.setType("babble");
				else if (set.getString(11) != null)
					babble.setType("rebabble");
				else
					babble.setType("like");

				babbles.add(babble);
			}

			return babbles;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

}

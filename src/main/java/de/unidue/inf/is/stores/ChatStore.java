package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unidue.inf.is.application.Application;
import de.unidue.inf.is.domain.Message;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.interfaces.IMessage;
import de.unidue.inf.is.utils.DBUtil;

public final class ChatStore implements Closeable, IMessage {
	private static ChatStore instance;

	private Connection connection;
	private boolean complete;

	public ChatStore() throws StoreException {
		try {
			connection = DBUtil.getExternalConnection("babble");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	public static ChatStore getInstanse() {
		if (instance == null)
			instance = new ChatStore();
		return instance;
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
	public ArrayList<Message> getMessages(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM dbp40.babblemessage WHERE (sender = ? and recipient = ?)"
							+ " OR (sender = ? and recipient = ?) ORDER BY created ASC");
			// ID TEXT CREATED SENDER RECIPIENT
			preparedStatement.setString(1, Application.getUser().getUsername());
			preparedStatement.setString(2, user.getUsername());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, Application.getUser().getUsername());
			ResultSet set = preparedStatement.executeQuery();
			ArrayList<Message> messages = new ArrayList<>();
			while (set.next()) {
				Message message = new Message(set.getString(1), set.getString(2), set.getString(3), set.getString(4),
						set.getString(5));
				messages.add(message);
			}

			return messages;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean sendMessage(Message message) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO dbp40.babblemessage (text, created, sender, recipient) VALUES(?,CURRENT_TIMESTAMP,?,?)");
			preparedStatement.setString(1, message.getText());
			// preparedStatement.setString(3, babble.getCreated());
			preparedStatement.setString(2, message.getSender());
			preparedStatement.setString(3, message.getRecipiant());
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}
}

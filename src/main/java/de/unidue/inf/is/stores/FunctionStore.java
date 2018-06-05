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
import de.unidue.inf.is.interfaces.IFunc;
import de.unidue.inf.is.utils.DBUtil;

public final class FunctionStore implements Closeable, IFunc {
	private static FunctionStore instance;

	private Connection connection;
	private boolean complete;

	public FunctionStore() throws StoreException {
		try {
			connection = DBUtil.getExternalConnection("babble");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	public static FunctionStore getInstanse() {
		if (instance == null)
			instance = new FunctionStore();
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
	public ArrayList<Babble> search(String text) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM dbp40.babble WHERE text LIKE ?");
			preparedStatement.setString(1, "%" + text + "%");
			ResultSet set = preparedStatement.executeQuery();
			ArrayList<Babble> babbles = new ArrayList<>();
			while (set.next()) {
				Babble babble = new Babble(set.getInt(1), set.getString(2), set.getString(4), set.getString(3));
				babbles.add(addMissingInfo(babble));
			}

			return babbles;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public Babble getBabble(String id) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * from dbp40.babble where id = ?");
			preparedStatement.setString(1, id);
			ResultSet set = preparedStatement.executeQuery();
			if (set.next()) {
				Babble babble = new Babble(set.getInt(1), set.getString(2), set.getString(4), set.getString(3));
				return addMissingInfo(babble);
			}
			return null;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public Babble addMissingInfo(Babble babble) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT COUNT(babble) from dbp40.likesbabble where type = 'like' group by babble having babble = ?");
			preparedStatement.setInt(1, babble.getId());
			ResultSet set = preparedStatement.executeQuery();
			if (set.next()) {
				babble.setUpvotes(set.getInt(1));
			}

			PreparedStatement preparedStatement2 = connection.prepareStatement(
					"SELECT COUNT(babble) from dbp40.likesbabble where type = 'dislike' group by babble having babble = ?");
			preparedStatement2.setInt(1, babble.getId());
			ResultSet set2 = preparedStatement2.executeQuery();
			if (set2.next()) {
				babble.setDownvotes(set2.getInt(1));
			}

			PreparedStatement preparedStatement3 = connection
					.prepareStatement("select count(*) from dbp40.rebabble group by babble having babble = ?");
			preparedStatement3.setInt(1, babble.getId());
			try (ResultSet set3 = preparedStatement3.executeQuery()) {
				if (set3.next()) {
					babble.setRebabbles(set3.getInt(1));
				}
			} catch (SQLException e) {
				;
			}
			return babble;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean createBabble(Babble babble) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO dbp40.babble (text, created, creator) VALUES(?,CURRENT_TIMESTAMP,?)");
			preparedStatement.setString(1, babble.getText());
			// preparedStatement.setString(3, babble.getCreated());
			preparedStatement.setString(2, babble.getCreator());
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public int getLike(Babble babble, User user) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select username, type, babble from dbp40.likesbabble where username = ? and babble = ?");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setInt(2, babble.getId());
			ResultSet set = preparedStatement.executeQuery();
			if (set.next()) {
				if (set.getString(2).contains("dis"))
					return -1;
				else
					return 1;
			}

			return 0;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean getRebabbled(Babble babble, User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select username, babble from dbp40.rebabble where username = ? and babble = ?");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setInt(2, babble.getId());
			ResultSet set = preparedStatement.executeQuery();
			if (set.next())
				return true;
			return false;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean like(Babble babble) {
		try {
			User user = Application.getUser();
			int getwhatsup = getLike(babble, user);
			if (getwhatsup == 0 || getwhatsup == -1) {
				if (getwhatsup == -1) {
					PreparedStatement preparedStatement = connection.prepareStatement(
							"DELETE FROM dbp40.likesbabble WHERE username = ? and babble = ? and type = ?");

					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setInt(2, babble.getId());
					preparedStatement.setString(3, "dislike");
					preparedStatement.executeUpdate();
				}
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO dbp40.likesbabble VALUES(?,?,?,CURRENT_TIMESTAMP)");

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setInt(2, babble.getId());
				preparedStatement.setString(3, "like");
				preparedStatement.executeUpdate();
				return true;
			} else if (getwhatsup == 1) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"DELETE FROM dbp40.likesbabble WHERE username = ? and babble = ? and type = ?");

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setInt(2, babble.getId());
				preparedStatement.setString(3, "like ");
				preparedStatement.executeUpdate();
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean dislike(Babble babble) {
		try {
			User user = Application.getUser();
			int getwhatsup = getLike(babble, user);
			if (getwhatsup == 0 || getwhatsup == 1) {
				if (getwhatsup == 1) {
					PreparedStatement preparedStatement = connection.prepareStatement(
							"DELETE FROM dbp40.likesbabble WHERE username = ? and babble = ? and type = ?");

					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setInt(2, babble.getId());
					preparedStatement.setString(3, "like");
					preparedStatement.executeUpdate();
				}
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO dbp40.likesbabble VALUES(?,?,?,CURRENT_TIMESTAMP)");

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setInt(2, babble.getId());
				preparedStatement.setString(3, "dislike");
				preparedStatement.executeUpdate();
				return true;
			} else if (getwhatsup == -1) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"DELETE FROM dbp40.likesbabble WHERE username = ? and babble = ? and type = ?");

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setInt(2, babble.getId());
				preparedStatement.setString(3, "dislike ");
				preparedStatement.executeUpdate();
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean Rebabble(Babble babble) {
		try {
			User user = Application.getUser();
			if (!getRebabbled(babble, user)) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("INSERT INTO dbp40.rebabble VALUES(?,?,CURRENT_TIMESTAMP)");

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setInt(2, babble.getId());
				preparedStatement.executeUpdate();
				return true;
			} else {
				PreparedStatement preparedStatement = connection
						.prepareStatement("DELETE FROM dbp40.rebabble WHERE username = ? and babble = ?");

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setInt(2, babble.getId());
				preparedStatement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public boolean deleteBabble(Babble babble) {
		try {
			User user = Application.getUser();
			if (!babble.getCreator().equals(user.getUsername()))
				return false;
			PreparedStatement preparedStatement = connection.prepareStatement(
					"DELETE FROM dbp40.Babble WHERE id = ?");

			preparedStatement.setInt(1, babble.getId());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	@Override
	public ArrayList<Babble> getTop5() {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from dbp40.babble INNER JOIN "
					+ "(select babble, count(*) as ttt from dbp40.likesbabble where type = 'like'"
					+ "group by babble ORDER BY ttt desc fetch first 5 rows only) ON babble = id "
					+ "ORDER BY ttt DESC");
			ResultSet set = preparedStatement.executeQuery();
			ArrayList<Babble> babbles = new ArrayList<>();
			while (set.next()) {
				Babble babble = new Babble(set.getInt(1), set.getString(2), set.getString(4), set.getString(3));
				babbles.add(addMissingInfo(babble));
			}

			return babbles;
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}
}

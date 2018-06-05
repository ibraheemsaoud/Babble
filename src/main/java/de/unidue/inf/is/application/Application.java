package de.unidue.inf.is.application;

import java.util.ArrayList;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.Message;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.interfaces.IFunc;
import de.unidue.inf.is.interfaces.IMessage;
import de.unidue.inf.is.interfaces.IUser;
import de.unidue.inf.is.stores.ChatStore;
import de.unidue.inf.is.stores.FunctionStore;
import de.unidue.inf.is.stores.UserStore;

public final class Application implements IUser, IFunc, IMessage {

	private static Application instance;
	private static User user;

	/**
	 * Implementation of the Singleton pattern.
	 *
	 * @return
	 */
	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}

	public static User getUser() {
		if (user == null) {
			// TODO this is probably problematic.
			user = new User("student_1", "student", "Man's not hot", "Student");
		}
		return user;
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
	public User getUser(String username) {
		return UserStore.getInstanse().getUser(username);
	}

	@Override
	public boolean getFollow(User user1, User user2) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getBlock(User user) {
		return UserStore.getInstanse().getBlock(user);
	}

	@Override
	public int follow(User user) {
		return UserStore.getInstanse().follow(user);
	}

	@Override
	public int block(User user, String reason) {
		return UserStore.getInstanse().block(user, reason);
	}

	@Override
	public ArrayList<Babble> getTimeline(User user) {
		return UserStore.getInstanse().getTimeline(user);
	}

	@Override
	public ArrayList<Babble> search(String text) {
		return FunctionStore.getInstanse().search(text);
	}

	@Override
	public Babble addMissingInfo(Babble babble) {
		return FunctionStore.getInstanse().addMissingInfo(babble);
	}

	@Override
	public boolean createBabble(Babble babble) {
		return FunctionStore.getInstanse().createBabble(babble);
	}

	@Override
	public int getLike(Babble babble, User user) {
		return FunctionStore.getInstanse().getLike(babble, user);
	}

	@Override
	public boolean getRebabbled(Babble babble, User user) {
		return FunctionStore.getInstanse().getRebabbled(babble, user);
	}

	@Override
	public boolean like(Babble babble) {
		return FunctionStore.getInstanse().like(babble);
	}

	@Override
	public boolean dislike(Babble babble) {
		return FunctionStore.getInstanse().dislike(babble);
	}

	@Override
	public boolean Rebabble(Babble babble) {
		return FunctionStore.getInstanse().Rebabble(babble);
	}

	@Override
	public boolean deleteBabble(Babble babble) {
		return FunctionStore.getInstanse().deleteBabble(babble);
	}

	@Override
	public Babble getBabble(String id) {
		return FunctionStore.getInstanse().getBabble(id);
	}

	@Override
	public ArrayList<Babble> getTop5() {
		return FunctionStore.getInstanse().getTop5();
	}

	@Override
	public ArrayList<Message> getMessages(User user) {
		return ChatStore.getInstanse().getMessages(user);
	}

	@Override
	public boolean sendMessage(Message message) {
		return ChatStore.getInstanse().sendMessage(message);
	}
}
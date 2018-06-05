package de.unidue.inf.is.interfaces;

import java.util.ArrayList;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.User;

public interface IUser {
	public boolean register(User user);

	public boolean login(User user);

	public User getUser(String username);

	public boolean getFollow(User user1, User user2);

	public String getBlock(User user);

	public ArrayList<Babble> getTimeline(User user);

	public int follow(User user);

	public int block(User user, String reason);
}
package de.unidue.inf.is.interfaces;

import java.util.ArrayList;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.User;

public interface IFunc {
	public Babble getBabble(String id);

	public ArrayList<Babble> search(String text);

	public Babble addMissingInfo(Babble babble);

	public boolean createBabble(Babble babble);

	public int getLike(Babble babble, User user);

	public boolean getRebabbled(Babble babble, User user);

	public boolean like(Babble babble);

	public boolean dislike(Babble babble);

	public boolean Rebabble(Babble babble);

	public boolean deleteBabble(Babble babble);

	public ArrayList<Babble> getTop5();
}
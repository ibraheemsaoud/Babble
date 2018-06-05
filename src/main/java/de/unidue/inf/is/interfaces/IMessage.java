package de.unidue.inf.is.interfaces;

import java.util.ArrayList;

import de.unidue.inf.is.domain.Message;
import de.unidue.inf.is.domain.User;

public interface IMessage {
	public ArrayList<Message> getMessages(User user);

	public boolean sendMessage(Message message);
}
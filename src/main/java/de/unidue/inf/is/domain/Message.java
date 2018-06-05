package de.unidue.inf.is.domain;

public final class Message {

	private String text;
	private String id;
	private String timeStamp;
	private String sender;
	private String Recipiant;

	public Message() {
	}

	public Message(String id, String text, String timeStamp, String sender, String recipiant) {
		this.setId(id);
		this.setText(text);
		this.setTimeStamp(timeStamp);
		this.setSender(sender);
		this.setRecipiant(recipiant);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipiant() {
		return Recipiant;
	}

	public void setRecipiant(String recipiant) {
		Recipiant = recipiant;
	}

}
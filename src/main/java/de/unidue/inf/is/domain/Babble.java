package de.unidue.inf.is.domain;

public final class Babble {

	private String text;
	private String creator;
	private int id;
	private String created;
	private int upvotes;
	private int downvotes;
	private int rebabbles;
	private String type;

	public Babble() {
	}

	public Babble(int id, String text, String creator, String created) {
		this.setId(id);
		this.setText(text);
		this.setCreator(creator);
		this.setCreated(created);
		upvotes = 0;
		downvotes = 0;
		rebabbles = 0;
		type = "babble";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}

	public int getRebabbles() {
		return rebabbles;
	}

	public void setRebabbles(int rebabbles) {
		this.rebabbles = rebabbles;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
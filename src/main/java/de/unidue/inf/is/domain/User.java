package de.unidue.inf.is.domain;

public final class User {

	private String username;
	private String profilePicture;
	private String status;
	private String name;

	/*
	 * @param
	 * 
	 */
	public User(String username, String profilePicture, String status, String name) {
		this.setUsername(username);
		this.setProfilePicture(profilePicture);
		this.setStatus(status);
		this.setName(name);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
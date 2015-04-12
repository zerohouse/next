package me.model.database;

import next.database.annotation.Column;
import next.database.annotation.Key;

public class UserLike {

	@Key
	private String id;
	@Key
	@Column(function = "INDEX")
	private String email;
	private String url;
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

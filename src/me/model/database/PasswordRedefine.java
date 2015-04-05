package me.model.database;

import next.database.annotation.Key;
import next.setting.Setting;

public class PasswordRedefine {
	@Key
	private String email;
	private String key;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLink() {
		return String.format("http://%s/api/passwordRedefine/%s/%s", Setting.get("url"), email, key);
	}
}

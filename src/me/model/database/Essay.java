package me.model.database;

import java.util.Date;

import next.database.annotation.Column;
import next.database.annotation.Key;
import next.mapping.annotation.DateFormat;

public class Essay {

	@Key(AUTO_INCREMENT = true)
	private Integer id;
	@Column(function = "INDEX", hasDefaultValue = false)
	private String key;
	private String email;
	private String head;
	@Column(DATA_TYPE = "TEXT", hasDefaultValue = false)
	private String body;
	@DateFormat("yyyy.M.d.h.m")
	private Date date;

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "Essay [id=" + id + ", key=" + key + ", email=" + email + ", head=" + head + ", body=" + body + ", date=" + date + "]";
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}

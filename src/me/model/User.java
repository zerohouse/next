package me.model;

import next.database.annotation.Key;
import next.database.annotation.Table;

@Table
public class User {

	@Key(AUTO_INCREMENT = true)
	private Integer id;
	private String nickName;
	private Integer age;


	public User(Integer id, String nickName, Integer age) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}

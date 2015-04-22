package me.model.test;

import java.util.ArrayList;
import java.util.List;

import me.model.User;
import next.database.annotation.testdata.Insert;
import next.database.annotation.testdata.InsertList;
import next.database.annotation.testdata.TestData;

@TestData
public class Tests {
	
	@InsertList
	private List<User> users;
	
	@Insert
	private User user;

	public Tests() {
		users = new ArrayList<User>();
		User user = new User(null, "ab", 1);
		users.add(user);
		users.add(user);
		users.add(user);
		users.add(user);
		users.add(user);
		this.user = user;
	}

	

}

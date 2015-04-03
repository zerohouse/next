package me.logic.matching;

import java.util.ArrayList;
import java.util.List;

import me.matching.MatchingUsers;
import me.model.database.User;

import org.junit.Test;

public class MatchingUsersTest {

	@Test
	public void test() {
		List<User> men = new ArrayList<User>();
		for(int i=0;i<50;i++){
			User user = new User();
			Double a = (Math.random()*10) + 20;
			user.setAge(a.intValue());
			user.setGender(0);
			men.add(user);
		}
		List<User> women = new ArrayList<User>();
		for(int i=0;i<48;i++){
			User user = new User();
			Double a = (Math.random()*10) + 20;
			user.setAge(a.intValue());
			user.setGender(1);
			women.add(user);
		}
		
		MatchingUsers users = new MatchingUsers(men, women);
		System.out.println(users.matchedUsers());
	}
}

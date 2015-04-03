package me.launcher;

import java.util.ArrayList;
import java.util.List;

import me.matching.MatchedUsers;
import me.matching.MatchingUsers;
import me.model.database.User;
import next.database.DAO;

public class Matcher {
	public static void main(String[] args) throws Exception {
		DAO dao = new DAO();
		List<User> human = dao
				.getRecordsByClass(
						User.class,
						"SELECT * FROM User left join TestResult ON User.User_email = TestResult.TestResult_userEmail WHERE User.User_authEmail = 1 and User.User_gender !=0");
		List<User> men = new ArrayList<User>();
		List<User> women = new ArrayList<User>();
		human.forEach(each -> {
			if (each.getGender() == 1) {
				men.add(each);
				return;
			}
			if (each.getGender() == 2)
				women.add(each);
		});

		MatchingUsers mu = new MatchingUsers(men, women);
		List<MatchedUsers> matched = mu.matchedUsers();
		matched.forEach(match -> {
			dao.insert(match.getMatching());
		});
		dao.commitAndReturn();
	}

}

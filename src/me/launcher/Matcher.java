package me.launcher;

import java.util.ArrayList;
import java.util.List;

import me.logic.matching.MatchedUsers;
import me.logic.matching.MatchingUsers;
import me.model.database.User;
import next.database.DAO;

public class Matcher {
	public static void main(String[] args) throws Exception {
		DAO dao = new DAO();
		List<User> human = dao
				.getRecordsByClass(User.class, "SELECT * FROM User left join TestResult ON User.User_email = TestResult.TestResult_userEmail");
		List<User> men = new ArrayList<User>();
		List<User> women = new ArrayList<User>();
		human.forEach(each -> {
			if (each.getGender() == 0) {
				men.add(each);
				return;
			}
			women.add(each);
		});

		MatchingUsers user = new MatchingUsers(men, women);
		List<MatchedUsers> matched = user.matchedUsers();
		matched.forEach(match -> {
			dao.insert(match.getMatching());
		});
		dao.commitAndReturn();
	}

}

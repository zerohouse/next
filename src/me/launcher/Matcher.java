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
		List<User> human = dao.getRecordsByClass(User.class, "SELECT * FROM User WHERE User_gender !=0");
		List<User> men = new ArrayList<User>();
		List<User> women = new ArrayList<User>();

		List<String> test = new ArrayList<String>();
		
		test.add("leehyuk@begin.again");
		test.add("jobin@begin.again");
		test.add("dongmin@begin.again");
		test.add("taehee@begin.again");
		test.add("sumin@begin.again");
		test.add("songe@begin.again");

		human.forEach(each -> {
			if (test.contains(each.getEmail()))
				return;
			if (each.getGender() == 1) {
				men.add(each);
				return;
			}
			if (each.getGender() == 2)
				women.add(each);
		});

		MatchingUsers mu = new MatchingUsers(men, women, dao);
		List<MatchedUsers> m = mu.getMatcheds();
		System.out.println(m);
		m.forEach(mm -> {
			dao.insert(mm.getMatching());
		});

		dao.commitAndClose();
	}

}

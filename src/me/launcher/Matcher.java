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
		human.forEach(each -> {
			if (each.getGender() == 1) {
				men.add(each);
				return;
			}
			if (each.getGender() == 2)
				women.add(each);
		});

		MatchingUsers mu = new MatchingUsers(men, women, dao);
		List<MatchedUsers> m = mu.getMatcheds();
		m.forEach(mm->{
			dao.insert(mm.getMatching());
		});
		dao.commitAndClose();
	}

}

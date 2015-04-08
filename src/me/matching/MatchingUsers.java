package me.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.matching.factor.Factor;
import me.model.database.Matching;
import me.model.database.User;
import next.database.DAO;

public class MatchingUsers {

	private List<User> men;
	private List<User> women;
	List<Matching> alreadyMatched;

	public MatchingUsers(List<User> men, List<User> women, List<Matching> alreadyMatched) {
		this.men = men;
		this.women = women;
		this.alreadyMatched = alreadyMatched;
	}

	public MatchingUsers(List<User> men, List<User> women, DAO dao) {
		this.men = men;
		this.women = women;

		men.forEach(man -> {
			man.defineFactors(dao);
		});
		women.forEach(woman -> {
			woman.defineFactors(dao);
		});
		alreadyMatched = dao.getRecordsByClass(Matching.class, "SELECT Matching_man, Matching_woman FROM Matching");
	}

	private int getPoint(User key, User person) {
		int point = 0;
		for (Map.Entry<String, Factor> elem : key.getFactors().entrySet()) {
			Factor f = person.getFactors().get(elem.getKey());
			if (f == null)
				continue;
			point += elem.getValue().getPoint(f);
		}
		return point;
	}

	private User getHighest(User user) {
		List<User> men;
		if (user.getGender() == 1) {
			men = women;
		} else {
			men = this.men;
		}
		User result = null;
		int point = -5000;
		for (int i = 0; i < men.size(); i++) {
			int newPoint = getPoint(men.get(i), user);
			if (point > newPoint)
				continue;
			point = newPoint;
			result = men.get(i);
		}
		return result;
	}

	public List<MatchedUsers> getMatcheds() {
		List<MatchedUsers> result = new ArrayList<MatchedUsers>();
		List<User> men = this.men.size() > women.size() ? this.men : women;
		List<User> other = this.men.size() <= women.size() ? this.men : women;
		List<User> otherBackup = new ArrayList<User>();
		otherBackup.addAll(other);
		while (!men.isEmpty()) {
			Double d = Math.random() * men.size() * 0.99;
			User user = men.get(d.intValue());
			User selected = getHighest(user);
			result.add(new MatchedUsers(user, selected));
			men.remove(user);
			other.remove(selected);
			if(other.isEmpty())
				other.addAll(otherBackup);
		}
		return result;
	}
}

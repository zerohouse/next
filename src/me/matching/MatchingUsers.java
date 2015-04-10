package me.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
			man.defineUserFactors();
		});
		women.forEach(woman -> {
			woman.defineFactors(dao);
			woman.defineUserFactors();
		});
		alreadyMatched = dao.getRecordsByClass(Matching.class, "SELECT Matching_man, Matching_woman FROM Matching");

		System.out.println(alreadyMatched);
	}

	private int getPoint(User key, User person) {

		int point = 0;
		for (Map.Entry<String, Factor> elem : key.getFactors().entrySet()) {
			Factor f = person.getFactors().get(elem.getKey());
			if (f == null)
				continue;
			point += elem.getValue().getPoint(f);
			System.out.println(String.format("%s %s %s %s %d", key, f.getClass().getSimpleName(), person, f.getClass().getSimpleName(), point));
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

		men.forEach(man -> {
			int point = 0;
			for (int i = 0; i < other.size(); i++) {
				point += getPoint(man, other.get(i));
			}
			man.setPoint(point);
		});

		Collections.sort(men, new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				if (o1.getPoint().equals(o2.getPoint()))
					return 0;
				return o1.getPoint() < o2.getPoint() ? 1 : -1;
			}

		});

		int max = other.size();
		int i = 0;
		while (men.size() != other.size()) {
			if (i == max)
				i = 0;
			other.add(other.get(i));
			i++;
		}
		while (!men.isEmpty()) {
			User user = men.get(0);
			User selected = getHighest(user);
			if (selected == null) {
				men.remove(user);
				continue;
			}
			MatchedUsers mu = new MatchedUsers(user, selected);
			if (has(alreadyMatched, mu)) {
				other.remove(selected);
				continue;
			}
			result.add(mu);
			men.remove(user);
			other.remove(selected);
		}
		return result;
	}

	public static boolean has(List<Matching> list, MatchedUsers mu) {
		if (list == null)
			return false;
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getMan().equals(mu.getMan().getEmail()))
				continue;
			if (!list.get(i).getWoman().equals(mu.getWoman().getEmail()))
				continue;
			return true;
		}
		return false;
	}
}

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

	public MatchingUsers(List<User> men, List<User> women) {
		super();
		this.men = men;
		this.women = women;
	}

	public List<MatchedUsers> matchedUsers() {
		DAO dao = new DAO();
		men.forEach(man -> {
			man.defineFactors(dao);
		});

		women.forEach(woman -> {
			woman.defineFactors(dao);
		});
		dao.commit();

		men.forEach(man -> {
			Integer point = man.getFactors().size() * 20;
			if (man.getAge() != null && man.getAge() != 0) {
				point += 10;
			}
			for (int i = 0; i < women.size(); i++) {
				point += getPoint(man, women.get(i));
			}
			man.setPoint(point);
		});

		Collections.sort(men, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				Integer i1 = o1.getPoint();
				Integer i2 = o2.getPoint();
				return (i1 > i2 ? -1 : (i1 == i2 ? 0 : 1));
			}
		});

		List<MatchedUsers> result = new ArrayList<MatchedUsers>();

		List<Matching> already = dao.getRecordsByClass(Matching.class, "SELECT Matching_man, Matching_woman FROM Matching");
		dao.close();
		men.forEach(man -> {
			Integer point = -1000;
			Integer newPoint;
			User matched = null;
			for (int i = 0; i < women.size(); i++) {
				newPoint = getPoint(man, women.get(i));
				if (man.getAge() != null && man.getAge() != 0 && women.get(i).getAge() != null && women.get(i).getAge() != 0) {
					point += 10;
					point -= Math.abs(man.getAge() - women.get(i).getAge());
				}
				if(already.contains(new Matching(man.getEmail(), women.get(i).getEmail(), null)))
					continue;
				if (point < newPoint) {
					point = newPoint;
					matched = women.get(i);
				}
			}
			if (matched == null)
				return;
			MatchedUsers matchedUsers = new MatchedUsers(man, matched);
			women.remove(matched);
			result.add(matchedUsers);
		});

		return result;
	}

	private int getPoint(User man, User woman) {
		int point = 0;
		for (Map.Entry<String, Factor> elem : man.getFactors().entrySet()) {
			Factor f = woman.getFactors().get(elem.getKey());
			if (f == null)
				continue;
			point += elem.getValue().getPoint(f);
		}
		return point;
	}
}

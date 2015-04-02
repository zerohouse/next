package me.logic.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.model.database.User;

public class MatchingUsers {

	private List<User> men;
	private List<User> women;

	public MatchingUsers(List<User> men, List<User> women) {
		super();
		this.men = men;
		this.women = women;
	}

	public List<MatchedUsers> matchedUsers() {
		men.forEach(man -> {
			Integer point = 0;
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

		men.forEach(man -> {
			Integer point = -1000;
			Integer newPoint;
			User matched = null;
			for (int i = 0; i < women.size(); i++) {
				newPoint = getPoint(man, women.get(i));
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
		Mbti mbti = new Mbti(man);
		Age age = new Age(man.getAge());
		return mbti.getPoint(woman) * 5 + age.getPoint(woman.getAge());
	}

}

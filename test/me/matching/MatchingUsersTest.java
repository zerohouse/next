package me.matching;

import java.util.ArrayList;
import java.util.List;

import me.matching.factor.Enneagram;
import me.matching.factor.LoveType;
import me.matching.factor.Mbti;
import me.model.database.Matching;
import me.model.database.User;

import org.junit.Test;

public class MatchingUsersTest {

	@Test
	public void test() {
		List<User> men = new ArrayList<User>();
		List<User> women = new ArrayList<User>();
		for (int i = 0; i < 1445; i++) {
			User user = new User();
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(1);
			user.setAuthEmail(true);
			user.setPassword("12345");
			user.setEmail(String.format("man%d@uss.com", i));
			user.putFactor("MBTI", new Mbti(Mbti.getRand()));
			user.putFactor("EnneaGram", new Enneagram("M" + Enneagram.getRand()));
			user.putFactor("LoveType", new LoveType(LoveType.getRand()));
			men.add(user);
		}
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setAuthEmail(true);
			user.setPassword("12345");
			user.setEmail(String.format("woman%d@uss.com", i));
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(2);
			user.putFactor("MBTI", new Mbti(Mbti.getRand()));
			user.putFactor("EnneaGram", new Enneagram("F" + Enneagram.getRand()));
			user.putFactor("LoveType", new LoveType(LoveType.getRand()));
			women.add(user);
		}
		List<Matching> al = new ArrayList<Matching>();
		// al.add(new Matching(men.get(1), women.get(2)));
		// al.add(new Matching(men.get(2), women.get(2)));
		// al.add(new Matching(men.get(3), women.get(2)));
		// al.add(new Matching(men.get(4), women.get(2)));
		MatchingUsers mu = new MatchingUsers(men, women, al);
		List<MatchedUsers> m = mu.getMatcheds();
		System.out.println(m);
		System.out.println(m.size());
	}

}

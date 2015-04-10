package me.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		al.add(new Matching(men.get(1), women.get(2)));
		al.add(new Matching(men.get(1), women.get(3)));
		al.add(new Matching(men.get(3), women.get(2)));
		al.add(new Matching(men.get(4), women.get(2)));
		System.out.println(al.contains(new Matching(women.get(3), men.get(1))));
		
		
		//MatchingUsers mu = new MatchingUsers(men, women, al);
		//List<MatchedUsers> m = mu.getMatcheds();
		//System.out.println(m);
		//System.out.println(m.size());
	}

	@Test
	public void sorttest() {
		List<Integer> a = new ArrayList<Integer>();
		a.add(1);
		a.add(25);
		a.add(23);
		a.add(33);
		a.add(5);
		a.add(65);
		a.add(7);
		System.out.println(a);
		Collections.sort(a, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1.equals(o2))
					return 0;
				return o1 < o2 ? 1 : -1;
			}

		});
		System.out.println(a);
	}

}


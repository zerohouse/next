package me.launcher;

import java.util.ArrayList;
import java.util.List;

import me.matching.factor.Enneagram;
import me.matching.factor.LoveType;
import me.matching.factor.Mbti;
import me.model.database.Matching;
import me.model.database.TestResult;
import me.model.database.User;
import next.database.DAO;
import next.database.maker.PackageCreator;

public class DBLauncher {
	public static void main(String[] args) throws Exception {
		PackageCreator.createTable(true, "me.model.database");
		insertTestData();
	}

	static void insertTestData() {
		DAO dao = new DAO();
		
		List<String> women = new ArrayList<String>(); 
		women.add("taehee@begin.again");
		women.add("sumin@begin.again");
		women.add("songe@begin.again");
		
		List<String> men = new ArrayList<String>(); 
		men.add("leehyuk@begin.again");
		men.add("jobin@begin.again");
		men.add("dongmin@begin.again");
		
		men.forEach(man->{
			women.forEach(woman->{
				Matching m = new Matching();
				m.setMan(man);
				m.setWoman(woman);
				dao.insert(m);
			});
		});
		
		for (int i = 0; i < 3; i++) {
			User user = new User();
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(1);
			user.setAuthEmail(true);
			user.setPassword("begin");
			user.setEmail(men.get(i));
			TestResult result = new TestResult();
			result.setName("MBTI");
			result.setResult(Mbti.getRand());
			result.setUserEmail(user.getEmail());
			dao.insert(result);
			TestResult result2 = new TestResult();
			result2.setName("EnneaGram");
			result2.setResult("M" + Enneagram.getRand());
			result2.setUserEmail(user.getEmail());
			dao.insert(result2);
			TestResult result3 = new TestResult();
			result3.setName("LoveType");
			result3.setResult(LoveType.getRand());
			result3.setUserEmail(user.getEmail());
			dao.insert(result3);
			dao.insert(user);
		}
		for (int i = 0; i < 3; i++) {
			User user = new User();
			user.setAuthEmail(true);
			user.setPassword("begin");
			user.setEmail(women.get(i));
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(2);
			TestResult result = new TestResult();
			result.setName("MBTI");
			result.setResult(Mbti.getRand());
			result.setUserEmail(user.getEmail());
			dao.insert(result);
			TestResult result2 = new TestResult();
			result2.setName("EnneaGram");
			result2.setResult("F" + Enneagram.getRand());
			result2.setUserEmail(user.getEmail());
			dao.insert(result2);
			TestResult result3 = new TestResult();
			result3.setName("LoveType");
			result3.setResult(LoveType.getRand());
			result3.setUserEmail(user.getEmail());
			dao.insert(result3);
			dao.insert(user);
		}
		dao.commitAndClose();
	}
}

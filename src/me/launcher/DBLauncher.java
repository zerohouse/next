package me.launcher;

import me.matching.factor.Enneagram;
import me.matching.factor.LoveType;
import me.matching.factor.Mbti;
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
		for (int i = 0; i < 50; i++) {
			User user = new User();
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(1);
			user.setAuthEmail(true);
			user.setPassword("12345");
			user.setEmail(String.format("man%d@uss.com", i));
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
		for (int i = 0; i < 48; i++) {
			User user = new User();
			user.setAuthEmail(true);
			user.setPassword("12345");
			user.setEmail(String.format("woman%d@uss.com", i));
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

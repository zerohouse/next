package me.launcher;

import me.matching.factor.Enneagram;
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
			user.setEmail(String.format("man%d@uss.com", i));
			TestResult result = new TestResult();
			result.setName("MBTI");
			result.setResult(Mbti.getRand());
			result.setUserEmail(user.getEmail());
			dao.insert(result);
			TestResult result2 = new TestResult();
			result2.setName("ENNEA");
			result2.setResult(Enneagram.getRand());
			result2.setUserEmail(user.getEmail());
			dao.insert(result2);
			dao.insert(user);
		}
		for (int i = 0; i < 48; i++) {
			User user = new User();
			user.setAuthEmail(true);
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
			result2.setName("ENNEA");
			result2.setResult(Enneagram.getRand());
			result2.setUserEmail(user.getEmail());
			dao.insert(result2);
			dao.insert(user);
		}
		dao.commitAndClose();
	}
}

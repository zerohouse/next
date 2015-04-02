package me.launcher;

import me.logic.matching.Mbti;
import me.model.database.TestResult;
import me.model.database.User;
import next.database.DAO;
import next.database.maker.PackageCreator;

public class DBLauncher {
	public static void main(String[] args) throws Exception {
		PackageCreator.createTable(true, "me.model.database");
		insertTestData();
	}

	private static void insertTestData() {
		DAO dao = new DAO();
		for (int i = 0; i < 50; i++) {
			User user = new User();
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(0);
			user.setId("man" + i);
			TestResult result = new TestResult();
			result.setName("MBTI");
			result.setResult(Mbti.getRandMbti());
			result.setUserId(user.getId());
			dao.insert(result);
			dao.insert(user);
		}
		for (int i = 0; i < 48; i++) {
			User user = new User();
			user.setMbti(Mbti.getRandMbti());
			user.setId("woman" + i);
			Double a = (Math.random() * 10) + 20;
			user.setAge(a.intValue());
			user.setGender(1);
			TestResult result = new TestResult();
			result.setName("MBTI");
			result.setResult(Mbti.getRandMbti());
			result.setUserId(user.getId());
			dao.insert(result);
			dao.insert(user);
		}
		dao.commitAndReturn();
	}
}

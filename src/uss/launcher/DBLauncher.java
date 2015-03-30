package uss.launcher;

import next.database.DAO;
import next.database.maker.PackageCreator;
import uss.model.database.User;

public class DBLauncher {
	public static void main(String[] args) throws Exception {
		PackageCreator.createTable(true, "uss.model.database");
		insertTestData();
	}

	private static void insertTestData() {
		DAO dao = new DAO();
		User user = new User();
		user.setNickName("abc");
		dao.insert(user);
	}
}
